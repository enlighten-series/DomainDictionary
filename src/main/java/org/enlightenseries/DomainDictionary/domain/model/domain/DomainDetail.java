package org.enlightenseries.DomainDictionary.domain.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.enlightenseries.DomainDictionary.domain.model.user.User;

public class DomainDetail extends Domain {

  public DomainDetail(Domain base) {
    super(
      base.getId(),
      base.getName(),
      base.getFormat(),
      base.getDescription(),
      base.getExistential(),
      base.getCreated(),
      base.getUpdated()
    );
  }

  @Setter
  @Getter
  private User createdBy;

  @Setter
  @Getter
  private User updatedBy;

}
