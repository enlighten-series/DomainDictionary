package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.usecase.ApplicationMigration;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.ExportFileGeneratingStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class ApplicationMigrationResource {

  private ApplicationMigration applicationMigrationUsecase;

  public ApplicationMigrationResource(
    ApplicationMigration _applicationMigrationUsecase
  ) {
    this.applicationMigrationUsecase = _applicationMigrationUsecase;
  }

  @GetMapping("/application-migration/export/status")
  public ResponseEntity<ExportFileGeneratingStatus> getGenerateExportFileStatus() throws Exception {
    boolean nowGenerating = applicationMigrationUsecase.isGeneratingExportFile();
    Date latestGeneratedDate = applicationMigrationUsecase.getExportFileGeneratedDate();

    ExportFileGeneratingStatus status = new ExportFileGeneratingStatus(nowGenerating, latestGeneratedDate);

    return ResponseEntity.ok().body(status);
  }

  @PostMapping("/application-migration/export/generate")
  public ResponseEntity startGeneratingExportFile() throws Exception {
    applicationMigrationUsecase.generatingExportFile();

    return ResponseEntity.ok().build();
  }
}
