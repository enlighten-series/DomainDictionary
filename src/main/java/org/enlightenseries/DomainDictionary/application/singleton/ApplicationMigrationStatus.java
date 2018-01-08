package org.enlightenseries.DomainDictionary.application.singleton;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Data
public class ApplicationMigrationStatus {
  private boolean nowGeneratingExportFile;

  public ApplicationMigrationStatus() {
    this.nowGeneratingExportFile = false;
  }
}
