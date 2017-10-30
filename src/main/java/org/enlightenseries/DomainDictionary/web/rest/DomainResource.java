package org.enlightenseries.DomainDictionary.web.rest;

import org.enlightenseries.DomainDictionary.domain.Domain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DomainResource {

  @GetMapping("/domains")
  public List<Domain> getAll() {
    return Arrays.asList(
      new Domain(
        1L,
        "顧客番号",
        "123-456-789",
        "おきゃくさんごとにつく",
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
  }
}
