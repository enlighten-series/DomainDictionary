package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.DomainDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DomainResource {
  DomainService domainService;

  public DomainResource(
    DomainService _domainService
  ) {
    this.domainService = _domainService;
  }

  @GetMapping("/domains")
  public List<Domain> getAllDomains() {
    return this.domainService.list();
  }

  @GetMapping("/domains/{id}")
  public ResponseEntity<DomainDto> getDomain(@PathVariable Long id) {
    Domain domain = this.domainService.findBy(id);

    if (domain != null) {
      List<RelatedDomainSummary> related = this.domainService.findRelatedDomains(id);
      DomainDto ret = new DomainDto(domain, related);
      return ResponseEntity.ok().body(ret);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/domains")
  public ResponseEntity<Domain> createDomain(@RequestBody Domain domain) throws URISyntaxException {
    this.domainService.register(domain);

    return ResponseEntity.created(new URI("/api/domain/" + domain.getId()))
      .body(domain);
  }

  @PutMapping("/domains/{id}")
  public ResponseEntity<Domain> updateDomain(@PathVariable Long id, @RequestBody Domain domain) {
    this.domainService.update(id, domain);

    return ResponseEntity.ok()
      .body(domain);
  }

  @DeleteMapping("/domains/{id}")
  public ResponseEntity<Domain> deleteDomain(@PathVariable Long id) throws Exception {
    Domain domain = this.domainService.findBy(id);

    this.domainService.delete(id);

    return ResponseEntity.ok()
      .body(domain);
  }
}
