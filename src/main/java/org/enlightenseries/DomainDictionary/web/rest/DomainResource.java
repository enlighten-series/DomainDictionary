package org.enlightenseries.DomainDictionary.web.rest;

import org.enlightenseries.DomainDictionary.domain.Domain;
import org.enlightenseries.DomainDictionary.mapper.DomainMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
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
    /*
    return Arrays.asList(
      new Domain(
        1L,
        "顧客番号",
        "123-456-789",
        "おきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつく",
        "おもいつき"
      ),
      new Domain(
        2L,
        "顧客",
        "-",
        "顧客の情報を格納する",
        "いる！"
      )
    );
    */
    return this.domainMapper.selectAll();
  }

  @GetMapping("/domains/{id}")
  public ResponseEntity<Domain> getDomain(@PathVariable Long id) {
    Domain domain = null;

    if (id == 1L) {
      domain = new Domain(
        1L,
        "顧客番号",
        "123-456-789",
        "おきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつくおきゃくさんごとにつく",
        "おもいつき"
      );
    }
    if (id == 2L) {
      domain = new Domain(
        2L,
        "顧客",
        "-",
        "顧客の情報を格納する",
        "いる！"
      );
    }

    if (domain != null) {
      return ResponseEntity.ok().body(domain);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/domains")
  public ResponseEntity<Domain> createDomain(@RequestBody Domain domain) throws URISyntaxException {
    System.out.println("createDomain¥r¥n" + domain.toString());

    this.domainMapper.insert(domain);

    return ResponseEntity.created(new URI("/api/domain/" + domain.getId()))
      .body(domain);
  }

  @PutMapping("/domains/{id}")
  public ResponseEntity<Domain> updateDomain(@PathVariable Long id, @RequestBody Domain domain) throws URISyntaxException {
    System.out.println("updateDomain[" + id + "]¥r¥n" + domain.toString());

    return ResponseEntity.ok()
      .body(domain);
  }
}
