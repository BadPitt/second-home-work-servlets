package ru.innopolis.course3.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

/**
 * @author Danil Popov
 */
/*public class UserDetailsAdapter extends User {
    private final int id;

    *//*public UserDetailsAdapter(ru.innopolis.course3.models.user.User acct) {
        super(acct.getName(), acct.getPassword(), acct.getIsActive(),
                true, true, true, toAuthorities(acct.getAuthorityNames()));
        this.id = acct.getId();
    }*//*

    private static GrantedAuthority[] toAuthorities(Set<String> authNames) {
        GrantedAuthority[] auths = new GrantedAuthority[authNames.size()];
        int i = 0;
        for (String authName : authNames) {
            auths[i++] = new SimpleGrantedAuthority(authName);
        }
        return auths;
    }

    public int getId() {
        return id;
    }
}*/
