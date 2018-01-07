package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class ApplicationMigration {
  MetadataRepository metadataRepository;
  DomainRepository domainRepository;

  public ApplicationMigration(
    MetadataRepository _metadataRepository,
    DomainRepository _domainRepository
  ) {
    this.metadataRepository = _metadataRepository;
    this.domainRepository = _domainRepository;
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
    Metadata majorVersion = new Metadata();
    majorVersion.setKey("major_version");
    majorVersion.setValue("9");
    Metadata minorVersion = new Metadata();
    minorVersion.setKey("minor_version");
    minorVersion.setValue("8");
    Metadata patchVersion = new Metadata();
    patchVersion.setKey("patch_version");
    patchVersion.setValue("7");

    this.metadataRepository.register(majorVersion);
    this.metadataRepository.register(minorVersion);
    this.metadataRepository.register(patchVersion);
  }

  /**
   * エクスポートファイルが生成途中か否かを判断する
   *
   * @return 生成処理中ならtrue, 未作成／作成済み問わず処理をしていなければfalse
   */
  public boolean isGeneratingExportFile() {
    // TODO: 別スレッドでの生成状態をチェック
    return false;
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
