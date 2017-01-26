package ru.innopolis.course3.models.comment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.DBException;

/**
 * @author Danil Popov
 */
@Aspect
public class CommentDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(CommentDaoChecker.class);

    @Pointcut("target(CommentRepository)")
    public void checkMethods() {}

    @Around("checkMethods()")
    public Object checkCommentDao(ProceedingJoinPoint joinPoint) {
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("Comment DAO exception", t);
            throw new DBException();
        }
        return retVal;
    }
}
