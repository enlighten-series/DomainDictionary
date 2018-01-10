package org.enlightenseries.DomainDictionary.presentation.rest.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessErrorDto {
  private String message;
}
