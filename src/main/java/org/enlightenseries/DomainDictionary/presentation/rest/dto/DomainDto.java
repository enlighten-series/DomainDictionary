package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.domain.RelatedDomainSummary;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class DomainDto {
  private Long id;
  @NotNull
  private String name;
  private String format;
  @NotNull
  private String description;
  @NotNull
  private String existential;
  private List<RelatedDomainSummary> relatedDomains;
  private Date created;
  private String createdBy;
  private Date updated;
  private String updatedBy;

  public DomainDto(DomainDetail primary, List<RelatedDomainSummary> relatedDomains) {
    this.id = primary.getId();
    this.name = primary.getName();
    this.format = primary.getFormat();
    this.description = primary.getDescription();
    this.existential = primary.getExistential();
    this.relatedDomains = relatedDomains;
    this.created = primary.getCreated();
    this.updated = primary.getUpdated();

    if (primary.getCreatedBy() != null) {
      this.createdBy = primary.getCreatedBy().getUsername();
    }
    if (primary.getUpdatedBy() != null) {
      this.updatedBy = primary.getUpdatedBy().getUsername();
    }
  }

  public Domain convertToDomain() {
    Domain d = new Domain();
    d.setId(id);
    d.setName(name);
    d.setFormat(format);
    d.setDescription(description);
    d.setExistential(existential);
    d.setCreated(created);
    d.setUpdated(updated);
    return d;
  }
}
