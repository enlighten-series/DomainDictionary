package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.service.SearchService;
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
  private SearchService searchService;

  public DomainUsecase(
    UserService _userService,
    DomainService _domainService,
    DomainRepository _domainRepository,
    SearchService _searchService
  ) {
    this.userService = _userService;
    this.domainService = _domainService;
    this.domainRepository = _domainRepository;
    this.searchService = _searchService;
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
    this.searchService.regist(domainDetail);

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
      domainDetail.setId(id); // ここでidをセットしないと、インデックスの基準になるIDがない。なんとかしないと。
      this.searchService.update(domainDetail);
    } catch (Exception e) {
      this.searchService.regist(domainDetail);
    }

  }

  public void delete(Long id) throws Exception {
    this.domainService.delete(id);

    // 全文検索から削除
    this.searchService.delete(id);
  }

  public List<DomainSummary> keywordSearch(String keyword) throws Exception {
    List<DomainSummary> result = new ArrayList<>();

    List<Long> searchedIds = searchService.search(keyword);

    searchedIds.forEach(id -> {
      result.add(domainRepository.findDomainSummaryBy(id));
    });

    return result;
  }

}
