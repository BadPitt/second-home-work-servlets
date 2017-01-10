package ru.innopolis.course3.models.user;

import ru.innopolis.course3.models.BaseModel;

/**
 * POJO for user entity
 *
 * @see BaseModel
 * @author Danil Popov
 */
public class User extends BaseModel {

    private int id;
    private String name;
    private String password;
    private String salt;
    private boolean isAdmin;
    private boolean isActive;
    private long version;

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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean role) {
        this.isAdmin = role;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
