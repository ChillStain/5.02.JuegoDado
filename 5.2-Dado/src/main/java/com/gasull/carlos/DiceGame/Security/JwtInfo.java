package com.gasull.carlos.DiceGame.Security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtInfo {

    private String token;
    private String bearer = "Bearer";
    private Long userID;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtInfo(String token, Long userID, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userID = userID;
        this.authorities = authorities;
    }

    public JwtInfo(String jwt, long userID) {
        this.token = token;
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
