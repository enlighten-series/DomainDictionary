package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.service.RelationService;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.RelationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RelationResource {

  private RelationService relationService;

  public RelationResource(
    RelationService _relationService
  ) {
    this.relationService = _relationService;
  }

  @PostMapping("/relations")
  public ResponseEntity<RelationDto> addNewRelation(@RequestBody @Valid RelationDto relationDto) throws Exception {
    Relation newRelation = relationService.createNewRelation(relationDto.getSource(), relationDto.getDestination());
    relationDto.setId(newRelation.getId().toString());

    return ResponseEntity.created(new URI("/api/relations/" + relationDto.getId())).body(relationDto);
  }

  @DeleteMapping("/relations/{id}")
  public ResponseEntity<Relation> deleteRelation(@PathVariable String id) {
    UUID targetId = UUID.fromString(id);

    Relation deleted = relationService.deleteRelation(targetId);

    return ResponseEntity.ok().body(deleted);
  }

}
