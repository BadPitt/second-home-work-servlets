package ru.innopolis.course3.models.role;

import ru.innopolis.course3.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danil Popov
 */
@Entity(name = "user_roles")
public class RoleEntity implements BaseEntity {//implements GrantedAuthority {

    private int id;
    private String name;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "role")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*@Override
    public String getAuthority() {
        return name;
    }*/

    public static List<Role> getRoles(List<RoleEntity> entities) {
        List<Role> roles = new ArrayList<>();
        for (RoleEntity entity: entities) {
            Role role = new Role();
            role.setId(entity.getId());
            role.setName(entity.getName());
            roles.add(role);
        }
        return roles;
    }

    public static List<RoleEntity> getRoleEntities(List<Role> roles) {
        List<RoleEntity> entities = new ArrayList<>();
        for (Role role: roles) {
            RoleEntity entity = new RoleEntity();
            entity.setId(role.getId());
            entity.setName(role.getName());
            entities.add(entity);
        }
        return entities;
    }
}
