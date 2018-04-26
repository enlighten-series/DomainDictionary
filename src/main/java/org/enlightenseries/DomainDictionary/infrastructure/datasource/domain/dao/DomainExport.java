package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainExport {
  private Long id;
  private Domain domain;
  private DomainMetaUser domainMetaUser;
}
