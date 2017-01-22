package ru.innopolis.course3.models.user;

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
public class UserDaoChecker {

    private final Logger logger = LoggerFactory.getLogger(UserDaoChecker.class);

    @Pointcut("target(ru.innopolis.course3.models.user.UserRepository)")
    public void checkMethods() {}

    @Around("checkMethods()")
    public Object checkUserDao(ProceedingJoinPoint joinPoint) {
        Object retVal = null;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("User Repository exception", t);
            throw new DBException();
        }
        return retVal;
    }
}
