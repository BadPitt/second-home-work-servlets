package ru.innopolis.course3.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.innopolis.course3.UserDetailsImpl;
import ru.innopolis.course3.models.user.UserRepository;

import java.io.Serializable;

/**
 * @author Danil Popov
 */
@Component
public class AuthenticationUserService
        implements UserDetailsService, Serializable {

    transient private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserDetailsByName(username);
    }
}
