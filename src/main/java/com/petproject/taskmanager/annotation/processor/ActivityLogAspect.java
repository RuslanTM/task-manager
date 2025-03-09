package com.petproject.taskmanager.annotation.processor;

import com.petproject.taskmanager.annotation.ActivityLog;
import com.petproject.taskmanager.data.enums.UserActivityType;
import com.petproject.taskmanager.dto.TaskDto;
import com.petproject.taskmanager.service.UserActivityService;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ActivityLogAspect {

    private final UserActivityService userActivityService;

    @Around("@annotation(com.petproject.taskmanager.annotation.ActivityLog)") // Intercepts methods with @ActivityLog
    public Object logActivity(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ActivityLog annotation = method.getAnnotation(ActivityLog.class);

        String activityName = annotation.activityName();
        String description = annotation.description();

        // Log the start of activity
        userActivityService.createUserActivity(activityName, UserActivityType.INITIALIZE, description);
        log.info("User Activity Started: {}", description);

        try {
            // Proceed with the method execution
            Object result = joinPoint.proceed();

            if (result instanceof TaskDto taskDto) {
                description = "Task id: " + taskDto.id();
            }

            // Log success
            userActivityService.createUserActivity(activityName, UserActivityType.FINISH, description);
            log.info("User Activity Completed: {}", description);
            return result;

        } catch (Exception e) {
            // Log failure if an exception occurs
            userActivityService.createUserActivity(activityName, UserActivityType.ERROR, "Failed: " + description);
            log.error("User Activity Failed: {}", description, e);
            throw e;
        }
    }
}
