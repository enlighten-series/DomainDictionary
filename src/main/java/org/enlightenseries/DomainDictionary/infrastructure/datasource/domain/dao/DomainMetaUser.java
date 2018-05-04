package org.enlightenseries.DomainDictionary.infrastructure.datasource.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainMetaUser {
  private Long id;
  private Long createdBy;
  private Long updatedBy;
}
