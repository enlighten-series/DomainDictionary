package org.enlightenseries.DomainDictionary.web.rest;

import org.enlightenseries.DomainDictionary.domain.Domain;
import org.enlightenseries.DomainDictionary.mapper.DomainMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DomainResource {

  DomainMapper domainMapper;

  public DomainResource(
    DomainMapper _domainMapper
  ) {
    this.domainMapper = _domainMapper;
  }

  @GetMapping("/domains")
  public List<Domain> getAllDomains() {
    return this.domainMapper.selectAll();
  }

  @GetMapping("/domains/{id}")
  public ResponseEntity<Domain> getDomain(@PathVariable Long id) {
    Domain domain = this.domainMapper.select(id);

    if (domain != null) {
      return ResponseEntity.ok().body(domain);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/domains")
  public ResponseEntity<Domain> createDomain(@RequestBody Domain domain) throws URISyntaxException {
    this.domainMapper.insert(domain);

    return ResponseEntity.created(new URI("/api/domain/" + domain.getId()))
      .body(domain);
  }

  @PutMapping("/domains/{id}")
  public ResponseEntity<Domain> updateDomain(@PathVariable Long id, @RequestBody Domain domain) throws URISyntaxException {
    this.domainMapper.update(id, domain);

    return ResponseEntity.ok()
      .body(domain);
  }
}
