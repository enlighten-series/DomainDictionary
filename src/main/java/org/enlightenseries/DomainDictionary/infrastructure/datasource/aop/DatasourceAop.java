package org.enlightenseries.DomainDictionary.infrastructure.datasource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DatasourceAop {

  @Before("execution(* org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.*Datasource.register(..)) ||"
    + "execution(* org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.*Datasource.update(..))")
  public void setDataSourceCommonMetadata(JoinPoint jp) {

    MethodSignature signature = (MethodSignature) jp.getSignature();
    Method method = signature.getMethod();
    String methodName = method.getName();

    System.out.println("execute method: " + method.getDeclaringClass().getSimpleName() + "." + method.getName());

  }

}
