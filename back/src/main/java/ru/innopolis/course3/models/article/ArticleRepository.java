package ru.innopolis.course3.models.article;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Danil Popov
 */
public interface ArticleRepository
        extends PagingAndSortingRepository<ArticleEntity, Long> {
}
