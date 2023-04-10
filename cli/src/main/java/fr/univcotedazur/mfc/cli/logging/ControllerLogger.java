package fr.univcotedazur.mfc.cli.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerLogger {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerLogger.class);
    private static final String PREFIX = "MFC:Commands:";

    @Pointcut("execution(public * fr.univcotedazur.mfc.cli.commands..*(..))")
    private void allCommandsMethods() {
    } // This enables to attach the pointcut to a method name we can reuse below

    @Before("allCommandsMethods()")
    public void logMethodNameAndParametersAtEntry(JoinPoint joinPoint) {
        LOG.info(PREFIX + joinPoint.getThis() + ":Called {}", joinPoint.getSignature().getName() + " " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "allCommandsMethods()", returning = "resultVal")
    public void logMethodReturningProperly(JoinPoint joinPoint, Object resultVal) {
        LOG.info(PREFIX + joinPoint.getThis() + ":Returned {}", joinPoint.getSignature().getName() + " with value " + resultVal);
    }

    @AfterThrowing(pointcut = "allCommandsMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        LOG.warn(PREFIX + joinPoint.getThis() + ":Exception from {}", joinPoint.getSignature().getName() + " with exception " + exception);
    }
}
