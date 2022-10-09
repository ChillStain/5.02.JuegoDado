package com.gasull.carlos.DiceGame.Security;

import com.gasull.carlos.DiceGame.entity.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerPrincipal implements UserDetails {

    private Long userId;
    private String password;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    public PlayerPrincipal(Long userId, String password, String name, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    public static PlayerPrincipal build (Player player){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(player.getRole()));
        return new PlayerPrincipal(player.getIdPlayer(), player.getPassword(), player.getName(), authorityList);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public long getUserId(){return userId;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}