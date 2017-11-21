package org.enlightenseries.DomainDictionary.application.usecase;

import org.apache.ibatis.exceptions.PersistenceException;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class ApplicationMigration {
  MetadataRepository metadataRepository;

  public ApplicationMigration(
    MetadataRepository _metadataRepository
  ) {
    this.metadataRepository = _metadataRepository;
  }

  @PostConstruct
  public void checkApplicationMetadata() {
    System.out.println("ApplicationMigration.checkApplicationMetadata");

    try {
      Metadata majorVersion = this.metadataRepository.findByKey("major_version");

      // TODO: バージョン確認とマイグレーション処理
    } catch (Exception e) {
      // TODO: 例外内容がテーブル未作成であることを判断
      this.initializeDatabase();
    }
  }

  @Transactional
  private void initializeDatabase() {
    this.metadataRepository.createTable();

    // 初期データ作成
    Metadata majorVersion = new Metadata();
    majorVersion.setKey("major_version");
    majorVersion.setValue("9");
    Metadata minorVersion = new Metadata();
    minorVersion.setKey("minor_version");
    minorVersion.setValue("8");
    Metadata patchVersion = new Metadata();
    patchVersion.setKey("patch_version");
    patchVersion.setValue("7");

    this.metadataRepository.registor(majorVersion);
    this.metadataRepository.registor(minorVersion);
    this.metadataRepository.registor(patchVersion);
  }

}
