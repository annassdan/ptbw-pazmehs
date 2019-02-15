package tnc.at.brpl.models.administrator.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tnc.at.brpl.models.administrator.Roles;
import tnc.at.brpl.models.administrator.SysUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authsGranted;

    public CustomUserDetails(SysUser user) {
        this.username = user.getPengguna();
        this.password = user.getKatasandi();
//        this.authsGranted = translate(user.getDataHakAkses());
    }

    private Collection<? extends GrantedAuthority> translate(Set<Roles> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Roles role : roles) {
//            String name = role.getHakAkses().toString().toUpperCase();
//            authorities.add(new SimpleGrantedAuthority(name));
//        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
