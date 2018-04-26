package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.LuceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

  @Autowired
  private LuceneRepository luceneRepository;

  public List<Long> search(String keyword) throws Exception {
    return luceneRepository.search(keyword);
  }

  public void regist(Domain newdata) throws Exception {
    luceneRepository.regist(newdata);
  }

  public void update(Domain newdata) throws Exception {
    luceneRepository.update(newdata);
  }

  public void delete(Long id) throws Exception {
    luceneRepository.delete(id);
  }

}
