package com.flashsuppressor.java.lab.security.entity;

import com.flashsuppressor.java.lab.entity.Customer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityCustomer implements UserDetails {

    private final String name;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive = true;

    public SecurityCustomer(String name, String password, List<SimpleGrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
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

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromCustomer(Customer customer){
        return new User(customer.getEmail(), customer.getPassword(),
                true,true,true,true,
                customer.getRole().getAuthorities());
    }
}
