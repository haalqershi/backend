package com.hesham.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.hesham.backend.security.SecurityConstants.AUTHORITIES;
import static java.util.Arrays.stream;

@Component
public class TokenProvider {

    @Value("${secret.jwt}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal){
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(userPrincipal.getUsername())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }



    private String[] getUserClaims(UserPrincipal userPrincipal){
        List<String> auths = new ArrayList<>();
        for(GrantedAuthority grantedAuthority: userPrincipal.getAuthorities()){
            auths.add(grantedAuthority.getAuthority());
        }
        return auths.toArray(new String[0]);
    }

    public List<GrantedAuthority> getAllAuthorities(String jwtToken){
        String[] claims = getClaimsFromToken(jwtToken);

        // get each claim and create a new object of simplegrantedauthority and add it to the list
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String[] getClaimsFromToken(String jwtToken) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(jwtToken).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algo = HMAC512(secret);
            verifier = JWT.require(algo).withIssuer(SecurityConstants.PRODUCT_MANAGER).build();
        } catch (JWTVerificationException ex){
            throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    // set information for the user in the spring security context
    // tells spring this user is authenticated process its request
    public Authentication getAuthentication(HttpServletRequest request, List<GrantedAuthority> authorities, String username){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isValidToken(String username, String token){
        JWTVerifier jwtVerifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isExpiredToken(jwtVerifier, token);
    }

    private boolean isExpiredToken(JWTVerifier jwtVerifier, String token) {
        Date expirationDate = jwtVerifier.verify(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    public String getSubject(String token){
        JWTVerifier jwtVerifier = getJWTVerifier();
        return jwtVerifier.verify(token).getSubject();
    }

}
