package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.springframework.stereotype.Service;

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
  public void getApplicationMetadata() {
    System.out.println("ApplicationMigration.getApplicationMetadata");

    Metadata majorVersion = this.metadataRepository.findByKey("major_version");
    System.out.println(majorVersion.toString());
  }

}
