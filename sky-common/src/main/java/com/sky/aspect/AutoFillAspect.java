package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

//自定义切面，用于实现公共字段自动填充
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

//    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
@Pointcut("execution(* com.sky..*Mapper.*(..)) && @annotation(com.sky.annotation.AutoFill)")

public void autoFillPointCut() {


    }
    //前置通知,在通知中进行公共字段自动填充
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始公共字段自动填充");

        //获取当前被拦截的方法上的注解对象（数据库操作类型）
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();//获取方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取注解对象
        OperationType operationType = autoFill.value() ;//获取数据库操作类型

        //获取到被拦截当前方法的参数列表
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        //用Object接受参数更有利于后期的功能扩展
        Object entity = args[0];

        //准备赋值数据
        LocalDateTime now= LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据当前不同的数据库操作类型，为对应的属性通过反射来赋值
        if(operationType == OperationType.INSERT){
            //插入操作，设置更新时间和创建时间以及更新人id和创建人id,为四个属性进行赋值
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射设置值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if(operationType == OperationType.UPDATE){
            //更新操作，设置更新时间以及更新人id，为两个属性进行赋值
            try {

                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射设置值

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}

