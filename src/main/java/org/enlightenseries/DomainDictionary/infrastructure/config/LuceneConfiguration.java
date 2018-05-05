package org.enlightenseries.DomainDictionary.infrastructure.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileSystems;

@Configuration
public class LuceneConfiguration {

  /**
   * Luceneのアナライザを返す。
   * 日本語アナライザを使用する。
   *
   * @return
   */
  @Bean
  public Analyzer analyzer() {
    return new JapaneseAnalyzer();
  }
}
