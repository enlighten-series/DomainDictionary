package org.enlightenseries.DomainDictionary;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
  private static ApplicationContext applicationContext = null;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    //Assign the ApplicationContext into a static variable
    this.applicationContext = applicationContext;
  }
}
