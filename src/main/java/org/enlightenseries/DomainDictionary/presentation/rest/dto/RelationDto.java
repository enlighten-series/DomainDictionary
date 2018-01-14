package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RelationDto {
  private String id;
  @NotNull
  private Long source;
  @NotNull
  private Long destination;
}
