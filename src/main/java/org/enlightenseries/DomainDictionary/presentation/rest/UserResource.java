package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

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
        UserDto ud = new UserDto(username);
        return ud;
      }
    ).orElseThrow(() ->  new ApplicationException("User could not be found"));
  }

  @PostMapping("/user")
  private ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto newUser) throws Exception {
    User user = newUser.getUser();

    user = this.userService.register(user, newUser.getPassword());
    UserDto createdUserDto = new UserDto(user);

    return ResponseEntity.ok().body(createdUserDto);
  }
}
