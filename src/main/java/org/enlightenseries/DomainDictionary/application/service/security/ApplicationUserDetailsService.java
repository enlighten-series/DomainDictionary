package org.enlightenseries.DomainDictionary.application.service.security;

import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.domain.model.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public ApplicationUserDetailsService(
    UserRepository _userRepository
  ) {
    this.userRepository = _userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      return null;
    }

    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ADMIN");

    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      grantedAuthorities
    );
  }

}
