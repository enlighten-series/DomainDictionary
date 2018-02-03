package org.enlightenseries.DomainDictionary.presentation.rest;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.presentation.rest.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
  public ResponseEntity<UserDto> getLoginUserData() throws ApplicationException {
    String loginUserName = userService.getLoginUsername();
    User loginUser = userService.findByUsername(loginUserName);

    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok().body(new UserDto(loginUser));
  }

  @PostMapping("/user")
  private ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto newUser) throws Exception {
    User user = newUser.convertToUser();

    user = this.userService.createNewUser(user, newUser.getPassword());
    UserDto createdUserDto = new UserDto(user);

    return ResponseEntity.ok().body(createdUserDto);
  }
}
