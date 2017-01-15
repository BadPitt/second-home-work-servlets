package ru.innopolis.course3.models.article;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.models.DBException;

/**
 * @author Danil Popov
 */
@Aspect
public class ArticleDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(ArticleDaoChecker.class);

    @Pointcut("target(ru.innopolis.course3.models.article.ArticleDao)")
    public void checkMethods() {}

    @Around("checkMethods()")
    public Object checkArticleDao(ProceedingJoinPoint joinPoint) throws DBException {
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("Article DAO exception", t);
            throw new DBException();
        }
        return retVal;
    }
}
