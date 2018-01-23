package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserResource {

  private UserService userService;

  public UserResource(
    UserService _userService
  ) {
    this.userService = _userService;
  }

  @GetMapping("/authentication")
  public UserDto getLoginUserData() throws ApplicationException {

    return userService.getLoginUser().map(
      username -> {
        UserDto ud = new UserDto();
        ud.setUsername(username);
        return ud;
      }
    ).orElseThrow(() ->  new ApplicationException("User could not be found"));
  }
}
