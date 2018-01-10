package org.enlightenseries.DomainDictionary.application.usecase;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.application.singleton.ApplicationMigrationStatus;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

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
  private DomainToRelationRepository domainToRelationRepository;
  private RelationRepository relationRepository;

  private ApplicationMigrationStatus applicationMigrationStatus;

  private String currentMajorVersion;
  private String currentMinorVersion;
  private String currentPatchVersion;

  public ApplicationMigration(
    MetadataRepository _metadataRepository,
    DomainRepository _domainRepository,
    DomainToRelationRepository _domainToRelationRepository,
    RelationRepository _relationRepository,
    ApplicationMigrationStatus _applicationMigrationStatus
  ) {
    this.metadataRepository = _metadataRepository;
    this.domainRepository = _domainRepository;
    this.domainToRelationRepository = _domainToRelationRepository;
    this.relationRepository = _relationRepository;

    this.applicationMigrationStatus = _applicationMigrationStatus;

    this.currentMajorVersion = "0";
    this.currentMinorVersion = "3";
    this.currentPatchVersion = "0";
  }

  @PostConstruct
  public void checkApplicationMetadata() {
    try {
      Metadata majorVersion = this.metadataRepository.findByKey("major_version");
      Metadata minorVersion = this.metadataRepository.findByKey("minor_version");
      Metadata patchVersion = this.metadataRepository.findByKey("patch_version");

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
    this.domainToRelationRepository.createTable();
    this.relationRepository.createTable();
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

  /**
   * 独自例外をロールバック対象に含めるよう、rollbackForを指定する
   * @param importFile
   * @throws Exception
   */
  @Transactional(rollbackFor = Exception.class)
  public void startImport(MultipartFile importFile) throws Exception {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(importFile.getInputStream()))) {

      CSVParser parser = CSVFormat
        .RFC4180
        .withIgnoreEmptyLines(true)
        .withIgnoreSurroundingSpaces(true)
        .parse(br);

      String majorVersion = "";
      String minorVersion = "";
      String patchVersion = "";

      for(CSVRecord firstRecord : parser) {
        majorVersion = firstRecord.get(1);
        minorVersion = firstRecord.get(2);
        patchVersion = firstRecord.get(3);
        break;
      }

      if (!majorVersion.equals("0")
        || !minorVersion.equals("2")
        || !patchVersion.equals("2")) {
        throw new ApplicationException("現在、バージョン0.2.2以外のエクスポートファイルをインポートすることはできません");
      }

      // バージョンに合わせたインポートを実行
      import_0_2_X(parser);

    } catch (IOException e) {
      throw e;
    }
  }

  private void import_0_2_X(CSVParser parser) throws Exception {
    domainRepository.import_0_2_X(parser);
    relationRepository.import_0_2_X(parser);
    domainToRelationRepository.import_0_2_X(parser);
  }

}
