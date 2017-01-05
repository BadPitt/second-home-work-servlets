package ru.innopolis.course3.models.user;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.models.DBException;

/**
 * @author Danil Popov
 */
public class UserDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(UserDaoChecker.class);

    public Object checkUserDao(ProceedingJoinPoint joinPoint) throws DBException {
        //checkArg(o);
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("User DAO exception", t);
            throw new DBException();
        }
        return retVal;
    }

    public void checkArg(Object o) throws DBException {
        if (o == null) {
            logger.error("User DAO null object exception");
            throw new DBException();
        }
    }
}
