package ru.innopolis.course3.models.comment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.models.DBException;

/**
 * @author Danil Popov
 */
@Aspect
public class CommentDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(CommentDaoChecker.class);

    @Pointcut("target(ru.innopolis.course3.models.comment.CommentDao)")
    public void checkMethods() {}

    @Around("checkMethods()")
    public Object checkCommentDao(ProceedingJoinPoint joinPoint) throws DBException {
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("Comment DAO exception", t);
            throw new DBException();
        }
        return retVal;
    }

    @Pointcut("execution(* " +
            "ru.innopolis.course3.models.comment.CommentDao.add(" +
            "ru.innopolis.course3.models.comment.Comment)) && args(o) || " +
            "execution(* " +
            "ru.innopolis.course3.models.comment.CommentDao.update(" +
            "ru.innopolis.course3.models.comment.Comment)) && args(o)")
    public void checkArg(Comment o) {}

    /* Will be invoked after around-method */
    @Before("checkArg(o)")
    public void checkCommentDaoArg(Comment o) throws DBException {
        if (o == null) {
            logger.error("Comment DAO null object exception");
            throw new DBException();
        }
    }
}
