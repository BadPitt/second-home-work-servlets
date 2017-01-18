package ru.innopolis.course3.models.user;

import ru.innopolis.course3.models.BaseEntity;
import ru.innopolis.course3.models.role.Role;
import ru.innopolis.course3.models.role.RoleEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author Danil Popov
 */
@Entity(name = "p_user")
public class UserEntity implements BaseEntity {

    private int id;
    private String name;
    private String password;
    private int roleId;
    private boolean isActive;
    private long version;

    private List<RoleEntity> authorites;

    // TODO: change strategy to table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "enabled")
    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    @Column(name = "version")
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    //@ManyToMany(fetch = FetchType.LAZY)
    @ManyToMany
    @JoinTable(name = "user_to_roles", joinColumns = {
            @JoinColumn(name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id")})
    public List<RoleEntity> getAuthorites() {
        return authorites;
    }

    public void setAuthorites(List<RoleEntity> authorites) {
        this.authorites = authorites;
    }

    public static User getUser(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setAuthorites(RoleEntity.getRoles(entity.getAuthorites()));
        user.setIsActive(entity.getIsActive());
        user.setName(entity.getName());
        user.setPassword(entity.getPassword());
        user.setVersion(entity.getVersion());
        return user;
    }

    public static UserEntity getUserEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());
        entity.setAuthorites(RoleEntity.getRoleEntities(user.getAuthorites()));
        entity.setIsActive(user.getIsActive());
        entity.setVersion(user.getVersion());
        return entity;
    }
}