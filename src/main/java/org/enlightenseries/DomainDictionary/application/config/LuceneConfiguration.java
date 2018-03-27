package org.enlightenseries.DomainDictionary.application.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;

@Configuration
public class LuceneConfiguration {

  /**
   * TODO: Directoryはストレージと考えられるため、インフラ層のほうが適しているかもしれない
   * @return
   * @throws IOException
   */
  @Bean(name="directory")
  @ConditionalOnProperty(name="spring.profiles.active", havingValue = "prod", matchIfMissing=false)
  public Directory useFileSystemDirectory() throws IOException {
    return FSDirectory.open(FileSystems.getDefault().getPath("data", "lucene-index"));
  }

  /**
   * TODO: Directoryはストレージと考えられるため、インフラ層のほうが適しているかもしれない
   * @return
   */
  @Bean(name="directory")
  @ConditionalOnProperty(name="spring.profiles.active", havingValue = "dev", matchIfMissing=true)
  public Directory useInMemoryDirectory() {
    return new RAMDirectory();
  }

  @Bean
  public Analyzer analyzer() {
    return new JapaneseAnalyzer();
  }
}
