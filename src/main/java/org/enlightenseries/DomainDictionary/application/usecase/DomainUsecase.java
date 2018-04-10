package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.service.LuceneService;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DomainUsecase {

  private UserService userService;
  private DomainService domainService;
  private DomainRepository domainRepository;
  private LuceneService luceneService;

  public DomainUsecase(
    UserService _userService,
    DomainService _domainService,
    DomainRepository _domainRepository,
    LuceneService _luceneService
  ) {
    this.userService = _userService;
    this.domainService = _domainService;
    this.domainRepository = _domainRepository;
    this.luceneService = _luceneService;
  }

  public DomainDetail register(Domain domain) throws Exception {
    DomainDetail domainDetail = new DomainDetail(domain);

    // メタ情報付加
    Date now = new Date();
    domainDetail.setCreated(now);
    domainDetail.setUpdated(now);
    User sessionUser = this.userService.findByUsername(this.userService.getLoginUsername());
    domainDetail.setCreatedBy(sessionUser);
    domainDetail.setUpdatedBy(sessionUser);

    this.domainRepository.registerDomainDetail(domainDetail);

    // ドメインIDが付与されたあと、全文検索に追加
    this.luceneService.regist(domain);

    return domainDetail;
  }

  public void update(Long id, Domain domain) throws Exception {
    DomainDetail domainDetail = new DomainDetail(domain);

    // メタ情報付加
    Date now = new Date();
    domainDetail.setUpdated(now);
    User sessionUser = this.userService.findByUsername(this.userService.getLoginUsername());
    domainDetail.setUpdatedBy(sessionUser);

    this.domainRepository.updateDomainDetail(id, domainDetail);

    // 全文検索に更新、更新対象がない場合は追加
    try {
      this.luceneService.update(domain);
    } catch (Exception e) {
      this.luceneService.regist(domain);
    }

  }

  public void delete(Long id) throws Exception {
    this.domainService.delete(id);

    // 全文検索から削除
    this.luceneService.delete(id);
  }

  public List<DomainSummary> keywordSearch(String keyword) throws Exception {
    List<DomainSummary> result = new ArrayList<>();

    List<Long> searchedIds = luceneService.search(keyword);

    searchedIds.forEach(id -> {
      result.add(domainRepository.findDomainSummaryBy(id));
    });

    return result;
  }

}
