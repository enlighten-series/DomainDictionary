package org.enlightenseries.DomainDictionary.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@ConfigurationProperties(prefix = "demo")
public class ApplicationDemoProperties {

  @Getter
  @Setter
  private boolean initdata;

}
