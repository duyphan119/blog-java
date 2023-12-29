package com.blog.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.models.CustomUserDetails;
import com.blog.models.Role;
import com.blog.models.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Cái địt" + username);
        Optional<User> resultUser = userService.findByEmail(username);
        if (resultUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = resultUser.get();

        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        Set<Role> roles = user.getRoles();

        for (Role accountRole : roles) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(accountRole.getName()));
        }
        return new CustomUserDetails(grantedAuthoritySet, user);
    }

}
