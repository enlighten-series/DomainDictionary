package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.LuceneDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

  @Autowired
  private LuceneDatasource luceneDatasource;

  public List<Long> search(String keyword) throws Exception {
    return luceneDatasource.search(keyword);
  }

  public void regist(Domain newdata) throws Exception {
    luceneDatasource.regist(newdata);
  }

  public void update(Domain newdata) throws Exception {
    luceneDatasource.update(newdata);
  }

  public void delete(Long id) throws Exception {
    luceneDatasource.delete(id);
  }

}
