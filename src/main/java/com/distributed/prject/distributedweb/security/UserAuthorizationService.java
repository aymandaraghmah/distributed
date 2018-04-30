package com.distributed.prject.distributedweb.security;

import com.distributed.prject.distributedweb.exception.UserAuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.distributed.prject.distributedweb.model.User;
import com.distributed.prject.distributedweb.repository.UserRepository;
import com.distributed.prject.distributedweb.services.UserService;

@Service
public class UserAuthorizationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' is not found");
        }

        return user;
    }


    public User getAuthenticatedUser() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (authentication.getPrincipal().equals("anonymousUser"))
            return null;
        else {
            user = userRepository.findById((int) authentication.getPrincipal());

        }
        if (authentication == null) {
            throw new UserAuthorizationException("The user is not authorized");
        }

        return user;
    }

    public void setAuthenticatedUser(User user) {
        Authentication authentication = new
                UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());

        boolean isAuthenticated = userService.isAuthenticated(authentication);
        if (isAuthenticated) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}