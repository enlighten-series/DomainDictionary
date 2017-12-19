package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.Data;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;

import java.util.Date;
import java.util.List;

@Data
public class DomainDto {
  private Long id;
  private String name;
  private String format;
  private String description;
  private String existential;
  private List<DomainSummary> relatedDomains;
  private Date created;
  private Date updated;

  public DomainDto(Domain primary, List<DomainSummary> relatedDomains) {
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
