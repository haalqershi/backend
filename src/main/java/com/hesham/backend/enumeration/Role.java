package com.hesham.backend.enumeration;

import com.hesham.backend.security.UserAuthorities;

public enum Role {
    ROLE_USER(UserAuthorities.USER_AUTHORITIES),
    ROLE_ADMIN(UserAuthorities.ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities){
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
