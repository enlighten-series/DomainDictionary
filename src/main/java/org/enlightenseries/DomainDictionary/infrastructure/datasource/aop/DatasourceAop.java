package org.enlightenseries.DomainDictionary.infrastructure.datasource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class DatasourceAop {

  @Before("execution(* org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.*Datasource.register(..)) ||"
    + "execution(* org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.*Datasource.update(..))")
  public void setDataSourceCommonMetadata(JoinPoint jp) throws Throwable {

    MethodSignature signature = (MethodSignature) jp.getSignature();
    Method method = signature.getMethod();
    String className = method.getDeclaringClass().getSimpleName();
    String methodName = method.getName();

    Date now = new Date();
    Object[] args = jp.getArgs();
    Object dto;

    if (methodName.equals("register")) {

      dto = args[0];
      this.setCreatedToDto(dto, now);
      this.setUpdatedToDto(dto, now);

    } else if (methodName.equals("update")) {

      dto = args[1];
      this.setUpdatedToDto(dto, now);

    }

  }

  private void setCreatedToDto(Object dto, Date created) throws Throwable {
    Method setCreatedMethod = ReflectionUtils.findMethod(dto.getClass(), "setCreated", Date.class);
    if (setCreatedMethod != null) {
      setCreatedMethod.invoke(dto, created);
    }
  }

  private void setUpdatedToDto(Object dto, Date updated) throws Throwable {
    Method setUpdatedMethod = ReflectionUtils.findMethod(dto.getClass(), "setUpdated", Date.class);
    if (setUpdatedMethod != null) {
      setUpdatedMethod.invoke(dto, updated);
    }
  }

}
