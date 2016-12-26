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
    private boolean isAdmin;
    private boolean isActive;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean role) {
        this.isAdmin = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
