package org.enlightenseries.DomainDictionary.application.service;

import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.domain.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  private UserRepository userRepository;

  public UserService(
    UserRepository _userRepository
  ) {
    this.userRepository = _userRepository;
  }

  /**
   * ログイン中のユーザを取得する
   * @return
   */
  public String getLoginUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();

    if (authentication.getPrincipal() instanceof UserDetails) {
      UserDetails ud = (UserDetails) authentication.getPrincipal();
      return ud.getUsername();
    } else if (authentication.getPrincipal() instanceof String) {
      return (String) authentication.getPrincipal();
    } else {
      return null;
    }
  }

  /**
   * ユーザを新規登録する
   * TODO: テスト作成
   * @param newUser
   * @param plainPassword
   * @return
   */
  public User createNewUser(User newUser, String plainPassword) throws ApplicationException {

    if (findByUsername(newUser.getUsername()) != null) {
      throw new ApplicationException("同じ名前のユーザがすでに存在しています。違う名前を指定してください。");
    }

    newUser.setPassword(passwordEncoder.encode(plainPassword));
    this.userRepository.register(newUser);

    return newUser;
  }

  public User findByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }
}
