package ru.innopolis.course3.models.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.innopolis.course3.User;
import ru.innopolis.course3.models.BaseEntity;
import ru.innopolis.course3.models.role.RoleEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * @author Danil Popov
 */
@Entity(name = "p_user")
public class UserEntity implements BaseEntity, UserDetails {

    private Long id;
    private String name;
    private String password;
    private boolean isActive;
    private long version;

    private List<RoleEntity> roles;

    // TODO: change strategy to table
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Column(name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_to_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id")})
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public static User getUser(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setRoles(RoleEntity.getRoles(entity.getRoles()));
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
        entity.setRoles(RoleEntity.getRoleEntities(user.getRoles()));
        entity.setIsActive(user.getIsActive());
        entity.setVersion(user.getVersion());
        return entity;
    }

    /* USER DETAILS SPRING SECURITY */

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Transient
    @Override
    public String getUsername() {
        return name;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }
}