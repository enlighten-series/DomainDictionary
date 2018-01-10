package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.Data;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;

import java.util.Date;
import java.util.List;

@Data
public class DomainDto {
  private Long id;
  private String name;
  private String format;
  private String description;
  private String existential;
  private List<RelatedDomainSummary> relatedDomains;
  private Date created;
  private Date updated;

  public DomainDto(Domain primary, List<RelatedDomainSummary> relatedDomains) {
    this.id = primary.getId();
    this.name = primary.getName();
    this.format = primary.getFormat();
    this.description = primary.getDescription();
    this.existential = primary.getExistential();
    this.relatedDomains = relatedDomains;
    this.created = primary.getCreated();
    this.updated = primary.getUpdated();
  }
}
