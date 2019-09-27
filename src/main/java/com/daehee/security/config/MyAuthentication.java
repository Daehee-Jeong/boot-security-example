package com.daehee.security.config;

import com.daehee.security.user.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyAuthentication extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    long userId;
    User user;

    public MyAuthentication(String id, String password, List<GrantedAuthority> grantedAuthorities, User user) {
        super(id, password, grantedAuthorities);
        this.user = user;
    }
}
