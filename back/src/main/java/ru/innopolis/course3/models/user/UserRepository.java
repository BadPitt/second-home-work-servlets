package ru.innopolis.course3.models.user;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Danil Popov
 */
public interface UserRepository extends CrudRepository<UserEntity, Long>,
                                        UserRepositoryCustom {
    UserEntity findByName(String name);
}
