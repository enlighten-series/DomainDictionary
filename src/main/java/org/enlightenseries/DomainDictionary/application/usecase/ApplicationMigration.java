package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class ApplicationMigration {

  MetadataRepository metadataRepository;
  DomainRepository domainRepository;
  DomainToRelationRepository domainToRelationRepository;
  RelationRepository relationRepository;

  public ApplicationMigration(
    MetadataRepository _metadataRepository,
    DomainRepository _domainRepository,
    DomainToRelationRepository _domainToRelationRepository,
    RelationRepository _relationRepository
  ) {
    this.metadataRepository = _metadataRepository;
    this.domainRepository = _domainRepository;
    this.domainToRelationRepository = _domainToRelationRepository;
    this.relationRepository = _relationRepository;
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
    majorVersion.setValue("0");
    Metadata minorVersion = new Metadata();
    minorVersion.setKey("minor_version");
    minorVersion.setValue("3");
    Metadata patchVersion = new Metadata();
    patchVersion.setKey("patch_version");
    patchVersion.setValue("0");

    this.metadataRepository.register(majorVersion);
    this.metadataRepository.register(minorVersion);
    this.metadataRepository.register(patchVersion);
  }

}
