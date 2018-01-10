package org.enlightenseries.DomainDictionary.application.usecase;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.enlightenseries.DomainDictionary.application.singleton.ApplicationMigrationStatus;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@Service
public class ApplicationMigration {

  private final String exportOutputDirectioryPath = "./data";
  private final String exportDomainFileName = "export.csv";

  private MetadataRepository metadataRepository;
  private DomainRepository domainRepository;

  private ApplicationMigrationStatus applicationMigrationStatus;

  private String currentMajorVersion;
  private String currentMinorVersion;
  private String currentPatchVersion;

  public ApplicationMigration(
    MetadataRepository _metadataRepository,
    DomainRepository _domainRepository,
    ApplicationMigrationStatus _applicationMigrationStatus
  ) {
    this.metadataRepository = _metadataRepository;
    this.domainRepository = _domainRepository;

    this.applicationMigrationStatus = _applicationMigrationStatus;

    this.currentMajorVersion = "0";
    this.currentMinorVersion = "2";
    this.currentPatchVersion = "2";
  }

  @PostConstruct
  public void checkApplicationMetadata() {
    try {
      Metadata majorVersion = this.metadataRepository.findByKey("major_version");

      // TODO: バージョン確認とマイグレーション処理
    } catch (Exception e) {
      // TODO: 例外内容がテーブル未作成であることを判断
      this.initializeApplicationDatabase();
    }
  }

  private void initializeApplicationDatabase() {
    createTables();
    insertInitialData();
  }

  private void createTables() {
    this.metadataRepository.createTable();
    this.domainRepository.createTable();
  }

  private void insertInitialData() {
    // TODO: バージョン番号を定数取得（Gradleとか）
    Metadata majorVersion = new Metadata();
    majorVersion.setKey("major_version");
    majorVersion.setValue(currentMajorVersion);
    Metadata minorVersion = new Metadata();
    minorVersion.setKey("minor_version");
    minorVersion.setValue(currentMinorVersion);
    Metadata patchVersion = new Metadata();
    patchVersion.setKey("patch_version");
    patchVersion.setValue(currentPatchVersion);

    this.metadataRepository.register(majorVersion);
    this.metadataRepository.register(minorVersion);
    this.metadataRepository.register(patchVersion);
  }

  /**
   * エクスポートファイルの生成を開始する
   */
  @Async("generatingExportFileExecutor")
  public void generatingExportFile() throws Exception {
    applicationMigrationStatus.setNowGeneratingExportFile(true);

    String exportFilePath = exportOutputDirectioryPath + "/" + exportDomainFileName;
    File exportFile = new File(exportFilePath);

    try (BufferedWriter bw = new BufferedWriter(
      new OutputStreamWriter(new FileOutputStream(exportFile), StandardCharsets.UTF_8))) {
      exportFile.createNewFile();
      CSVPrinter printer = CSVFormat.RFC4180.print(bw);

      // バージョン番号出力
      printer.printRecord("Version",
        currentMajorVersion,
        currentMinorVersion,
        currentPatchVersion
      );

      // 各Repositoryにファイルへのエクスポートを依頼する（大量データ処理はインフラに依存するため）
      domainRepository.export(printer);

    } catch (Exception e) {
      throw e;
    } finally {
      applicationMigrationStatus.setNowGeneratingExportFile(false);
    }
  }

  public String getExportFilePath() {
    return exportOutputDirectioryPath + "/" + exportDomainFileName;
  }

  /**
   * エクスポートファイルが生成途中か否かを判断する
   *
   * @return 生成処理中ならtrue, 未作成／作成済み問わず処理をしていなければfalse
   */
  public boolean isGeneratingExportFile() {
    return applicationMigrationStatus.isNowGeneratingExportFile();
  }

  /**
   * 生成ずみのエクスポートファイルの作成日時を返す
   *
   * @return ファイルが存在しなければnullを返す
   */
  public Date getExportFileGeneratedDate() throws IOException {
    File exportFile = new File(exportOutputDirectioryPath + "/" + exportDomainFileName);

    if (!exportFile.exists()) {
      return null;
    }

    BasicFileAttributes attr = Files.readAttributes(exportFile.toPath(), BasicFileAttributes.class);

    return Date.from(attr.lastModifiedTime().toInstant());
  }

}
