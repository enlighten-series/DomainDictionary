package org.enlightenseries.DomainDictionary.presentation.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.enlightenseries.DomainDictionary.domain.model.user.User;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserDto {
  @NotNull
  private String username;
  @NotNull
  private String password;

  public UserDto(String username) {
    this.username = username;
  }

  public UserDto(User user) {
    username = user.getUsername();
  }

  public User getUser() {
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setPassword(password);

    return newUser;
  }
}
