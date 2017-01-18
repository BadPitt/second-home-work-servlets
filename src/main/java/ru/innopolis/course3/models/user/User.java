package ru.innopolis.course3.models.user;

import ru.innopolis.course3.models.BaseModel;
import ru.innopolis.course3.models.role.Role;

import java.util.List;

/**
 * POJO for user entity
 *
 * @see BaseModel
 * @author Danil Popov
 */
public class User extends BaseModel {//implements UserDetails {

    private int id;
    private String name;
    private String password;
    private boolean isActive;
    private long version;

    private List<Role> authorites;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<Role> getAuthorites() {
        return authorites;
    }

    public void setAuthorites(List<Role> authorites) {
        this.authorites = authorites;
    }


    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorites;
    }

    @Override
    public String getUsername() {
        return name;
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
    }*/
}
