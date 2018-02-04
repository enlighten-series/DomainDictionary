package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DomainUsecase {

  private UserService userService;
  private DomainService domainService;
  private DomainRepository domainRepository;

  public DomainUsecase(
    UserService _userService,
    DomainService _domainService,
    DomainRepository _domainRepository
  ) {
    this.userService = _userService;
    this.domainService = _domainService;
    this.domainRepository = _domainRepository;
  }

  public void register(Domain domain) {
    DomainDetail domainDetail = new DomainDetail(domain);

    // メタ情報付加
    Date now = new Date();
    domainDetail.setCreated(now);
    domainDetail.setUpdated(now);
    User sessionUser = this.userService.findByUsername(this.userService.getLoginUsername());
    domainDetail.setCreatedBy(sessionUser);
    domainDetail.setUpdatedBy(sessionUser);

    this.domainRepository.registerDomainDetail(domainDetail);
  }

  public void update(Long id, Domain domain) {
    DomainDetail domainDetail = new DomainDetail(domain);

    // メタ情報付加
    Date now = new Date();
    domainDetail.setUpdated(now);
    User sessionUser = this.userService.findByUsername(this.userService.getLoginUsername());
    domainDetail.setUpdatedBy(sessionUser);

    this.domainRepository.updateDomainDetail(id, domainDetail);
  }

}
