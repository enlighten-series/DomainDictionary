package org.enlightenseries.DomainDictionary.application.usecase;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.enlightenseries.DomainDictionary.application.singleton.ApplicationMigrationStatus;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class ApplicationMigration {

  private final String exportOutputDirectioryPath = "./data";
  private final String exportDomainFileName = "export.csv";

  private MetadataRepository metadataRepository;
  private DomainRepository domainRepository;

  private ApplicationMigrationStatus applicationMigrationStatus;

  public ApplicationMigration(
    MetadataRepository _metadataRepository,
    DomainRepository _domainRepository,
    ApplicationMigrationStatus _applicationMigrationStatus
  ) {
    this.metadataRepository = _metadataRepository;
    this.domainRepository = _domainRepository;

    this.applicationMigrationStatus = _applicationMigrationStatus;
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
    majorVersion.setValue("0");
    Metadata minorVersion = new Metadata();
    minorVersion.setKey("minor_version");
    minorVersion.setValue("2");
    Metadata patchVersion = new Metadata();
    patchVersion.setKey("patch_version");
    patchVersion.setValue("2");

    this.metadataRepository.register(majorVersion);
    this.metadataRepository.register(minorVersion);
    this.metadataRepository.register(patchVersion);
  }

  /**
   * エクスポートファイルの生成を開始する
   */
  @Async("generatingExportFileExecutor")
  public void generatingExportFile() throws InterruptedException, IOException {
    applicationMigrationStatus.setNowGeneratingExportFile(true);

    // 各Repositoryにファイルへのエクスポートを依頼する（大量データ処理はインフラに依存するため）
    domainRepository.export(exportOutputDirectioryPath + "/" + exportDomainFileName);

    TimeUnit.SECONDS.sleep(10);

    applicationMigrationStatus.setNowGeneratingExportFile(false);
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
  public Date getExportFileGeneratedDate() {
    // TODO: ファイルの存在チェック＋ファイルの作成日時取得
    return null;
  }

}
