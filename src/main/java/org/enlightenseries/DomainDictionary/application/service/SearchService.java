package org.enlightenseries.DomainDictionary.application.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

  private Directory directory;
  private Analyzer analyzer;

  public SearchService(
    Directory directory,
    Analyzer analyzer
  ) {
    this.directory = directory;
    this.analyzer = analyzer;
  }

}
