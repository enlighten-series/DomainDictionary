package org.enlightenseries.DomainDictionary.application.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userDetailsService")
public class ApplicationUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (!username.equals("admin")) {
      return null;
    }

    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ADMIN");

    return new org.springframework.security.core.userdetails.User(
      "admin",
      new BCryptPasswordEncoder().encode("password"),
      grantedAuthorities
    );
  }

}
