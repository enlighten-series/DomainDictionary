package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.usecase.DomainUsecase;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.DomainDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DomainResource {

  DomainService domainService;
  DomainUsecase domainUsecase;

  public DomainResource(
    DomainService _domainService,
    DomainUsecase _domainUsecase
  ) {
    this.domainService = _domainService;
    this.domainUsecase = _domainUsecase;
  }

  @GetMapping("/domains")
  public List<Domain> getAllDomains() {
    return this.domainService.list();
  }

  @GetMapping("/domains/recent")
  public List<Domain> getRecentDomains() {
    return this.domainService.listRecent();
  }

  @GetMapping("/domains/{id}")
  public ResponseEntity<DomainDto> getDomain(@PathVariable Long id) {
    DomainDetail domain = this.domainService.findDomainDetailBy(id);

    if (domain != null) {
      List<RelatedDomainSummary> related = this.domainService.findRelatedDomains(id);
      DomainDto ret = new DomainDto(domain, related);
      return ResponseEntity.ok().body(ret);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/domains")
  public ResponseEntity<DomainDto> createDomain(@RequestBody @Valid DomainDto domainDto) throws Exception {
    Domain newDomain = domainDto.convertToDomain();

    DomainDetail detail = this.domainUsecase.register(newDomain);

    return ResponseEntity.created(new URI("/api/domain/" + detail.getId()))
      .body(new DomainDto(detail, null));
  }

  @PutMapping("/domains/{id}")
  public ResponseEntity<Domain> updateDomain(@PathVariable Long id, @RequestBody Domain domain) throws Exception {
    this.domainUsecase.update(id, domain);

    return ResponseEntity.ok()
      .body(domain);
  }

  @DeleteMapping("/domains/{id}")
  public ResponseEntity<Domain> deleteDomain(@PathVariable Long id) throws Exception {
    Domain domain = this.domainService.findBy(id);

    this.domainUsecase.delete(id);

    return ResponseEntity.ok()
      .body(domain);
  }

  @GetMapping("/domains/_search")
  public ResponseEntity<List<DomainSummary>> fullTextSearch(@RequestParam String keyword) throws Exception {
    List<DomainSummary> result = domainUsecase.keywordSearch(keyword);

    return ResponseEntity.ok().body(result);
  }
}
