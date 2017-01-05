package ru.innopolis.course3.models.user;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.models.DBException;

/**
 * @author Danil Popov
 */
@Aspect
public class UserDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(UserDaoChecker.class);

    @Pointcut("target(ru.innopolis.course3.models.user.UserDao)")
    public void checkMethods() {}

    @Around("checkMethods()")
    public Object checkUserDao(ProceedingJoinPoint joinPoint) throws DBException {
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("User DAO exception", t);
            throw new DBException();
        }
        return retVal;
    }

    @Pointcut("execution(* " +
            "ru.innopolis.course3.models.user.UserDao.add(" +
            "ru.innopolis.course3.models.user.User)) && args(o) ||" +
            "execution(* " +
            "ru.innopolis.course3.models.user.UserDao.update(" +
            "ru.innopolis.course3.models.user.User)) && args(o)")
    public void checkArg(User o) {}

    /* Will be invoked after around-method */
    @Before("checkArg(o)")
    public void checkUserDaoArg(User o) throws DBException {
        if (o == null) {
            logger.error("User DAO null object exception");
            throw new DBException();
        }
    }
}
