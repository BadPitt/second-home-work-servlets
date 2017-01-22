package ru.innopolis.course3.models.comment;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Danil Popov
 */
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    Iterable<CommentEntity> findByArticleId(Long articleId);
}
