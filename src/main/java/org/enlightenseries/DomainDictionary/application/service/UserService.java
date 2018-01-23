package org.enlightenseries.DomainDictionary.application.service;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  /**
   * ログイン中のユーザを取得する
   * @return
   */
  public Optional<String> getLoginUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
      .map(authentication -> {
        if (authentication.getPrincipal() instanceof UserDetails) {
          UserDetails ud = (UserDetails) authentication.getPrincipal();
          return ud.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
          return (String) authentication.getPrincipal();
        }
        return null;
      });
  }
}
