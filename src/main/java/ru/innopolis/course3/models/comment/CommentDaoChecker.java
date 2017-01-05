package ru.innopolis.course3.models.comment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.models.DBException;

/**
 * @author Danil Popov
 */
public class CommentDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(CommentDaoChecker.class);

    public Object checkCommentDao(ProceedingJoinPoint joinPoint) throws DBException {
        //checkArg(o);
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("Comment DAO exception", t);
            throw new DBException();
        }
        return retVal;
    }

    public void checkArg(Object o) throws DBException {
        if (o == null) {
            logger.error("Comment DAO null object exception");
            throw new DBException();
        }
    }
}
