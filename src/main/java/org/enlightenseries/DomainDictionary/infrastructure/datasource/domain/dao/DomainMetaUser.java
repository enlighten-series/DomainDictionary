package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao;

import lombok.Data;

@Data
public class DomainMetaUser {
  private Long id;
  private Long createdBy;
  private Long updatedBy;
}
