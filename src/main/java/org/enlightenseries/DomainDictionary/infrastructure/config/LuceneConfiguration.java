package org.enlightenseries.DomainDictionary.infrastructure.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
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
   * ファイルにインデックスを作成するDictionaryを返す。
   * 本番モードで使用する。
   *
   * @return
   * @throws IOException
   */
  @Bean(name="directory")
  @ConditionalOnProperty(name="spring.profiles.active", havingValue = "prod")
  public Directory useFileSystemDirectory() throws IOException {
    return FSDirectory.open(FileSystems.getDefault().getPath("data", "lucene-index"));
  }

  /**
   * インメモリでインデックスを作成するDictionaryを返す。
   * 開発モードで使用する。
   *
   * @return
   */
  @Bean(name="directory")
  @ConditionalOnProperty(name="spring.profiles.active", havingValue = "dev")
  public Directory useInMemoryDirectory() {
    return new RAMDirectory();
  }

  @Bean(name="directory")
  @ConditionalOnMissingBean(name="directory")
  public Directory fallbackDirectory() {
    return new RAMDirectory();
  }

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
