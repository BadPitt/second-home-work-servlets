package ru.innopolis.course3;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Danil Popov
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
