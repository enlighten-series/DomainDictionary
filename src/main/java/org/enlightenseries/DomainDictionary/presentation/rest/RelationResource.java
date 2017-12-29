package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RelationResource {

  @PostMapping("/relations")
  public ResponseEntity<Relation> createRelation(@RequestBody String source, @RequestBody String destination) throws URISyntaxException {
    Relation newRelation = new Relation();

    return ResponseEntity.created(new URI("/api/relations/" + newRelation.getId().toString())).body(newRelation);
  }

}
