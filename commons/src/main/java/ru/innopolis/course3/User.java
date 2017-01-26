package ru.innopolis.course3;

import java.io.Serializable;
import java.util.List;

/**
 * POJO for user entity
 *
 * @see BaseModel
 * @author Danil Popov
 */
public class User extends BaseModel implements Serializable {

    private long id;
    private String name;
    private String password;
    private boolean isActive;
    private long version;

    private List<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
