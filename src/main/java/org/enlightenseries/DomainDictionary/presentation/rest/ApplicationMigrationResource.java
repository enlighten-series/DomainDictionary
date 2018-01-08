package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.usecase.ApplicationMigration;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.EmptyDto;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.ExportFileGeneratingStatus;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
  public ResponseEntity<EmptyDto> startGeneratingExportFile() throws Exception {
    applicationMigrationUsecase.generatingExportFile();

    return ResponseEntity.ok().body(new EmptyDto());
  }

  @GetMapping("/application-migration/export/download")
  public ResponseEntity<Resource> getSimpleExcel() throws Exception {
    String exportFilePath = this.applicationMigrationUsecase.getExportFilePath();

    File exportFile = new File(exportFilePath);
    Path absolutePath = Paths.get(exportFile.getAbsolutePath());

    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(absolutePath));

    return ResponseEntity.ok()
      .header("Content-disposition", "attachment;filename=" + exportFile.getName())
      .contentType(MediaType.parseMediaType("text/csv"))
      .contentLength(exportFile.length())
      .body(resource);
  }
}
