package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.Data;

@Data
public class RelationDto {
  private Long source;
  private Long destination;
}
