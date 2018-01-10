package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExportFileGeneratingStatus {
  private boolean nowGenerating;
  private Date latestGeneratedDate;
}
