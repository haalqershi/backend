package com.hesham.backend.filter;

import com.hesham.backend.security.SecurityConstants;
import com.hesham.backend.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private TokenProvider tokenProvider;

    // this method everytime a request comes in, it happens only once per request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){ // option we don't do anything because is sent before any request to collection information about the server
            response.setStatus(HttpStatus.OK.value());
        }else{

            // we get the authorization header
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // if the header is null or it does not start with the token prefix (bearer)
            // then we know it's not our authorization header
            if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)){
                filterChain.doFilter(request, response);// we let the request go through
                return;
            }

            // otherwise, we get the token
            // remove the bearer word from the token by removing the length of prefix which is bearer
            String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());

            // get the username from the token
            String username = tokenProvider.getSubject(token);

            // check if the username is valid
            if(tokenProvider.isValidToken(username, token)){
                // get the authorities from the token
                List<GrantedAuthority> authorityList = tokenProvider.getAllAuthorities(token);

                // get the authentication
                Authentication authentication =  tokenProvider.getAuthentication(request, authorityList, username);

                // set the authentication at the spring security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else{
                // if not valid we must
                // clear the context
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
