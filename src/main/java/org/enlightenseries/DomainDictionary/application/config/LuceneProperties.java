package org.enlightenseries.DomainDictionary.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lucene")
public class LuceneProperties {

  @Getter
  @Setter
  private int maxSearchCound = 100;

}
