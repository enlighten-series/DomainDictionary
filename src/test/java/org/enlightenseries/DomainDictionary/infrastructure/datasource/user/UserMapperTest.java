package org.enlightenseries.DomainDictionary.infrastructure.datasource.user;

import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  private String assertUsername = "saikou9901";
  private String assertPassword = "BCryptString";
  private String assertUsernameUpdate = assertUsername + "Update";
  private String assertPasswordUpdate = assertPassword + "Update";

  @Test
  public void insert() {
    // when
    User assertData = new User();
    assertData.setUsername(assertUsername);
    assertData.setPassword(assertPassword);

    // try
    userMapper.insert(assertData);
    User subject = userMapper.selectByUsername(assertUsername);

    // expect
    assertThat(subject.getUsername()).isEqualTo(assertUsername);
    assertThat(subject.getPassword()).isEqualTo(assertPassword);
    assertThat(subject.getId()).isNotNull();
  }

  @Test
  public void update() {
    // when
    User assertData = userMapper.selectByUsername(assertUsername);

    // try
    assertData.setPassword(assertPasswordUpdate);
    userMapper.update(assertData);
    User subject = userMapper.selectByUsername(assertUsername);

    // expect
    assertThat(subject.getUsername()).isEqualTo(assertUsername);
    assertThat(subject.getPassword()).isEqualTo(assertPasswordUpdate);
    assertThat(subject.getId()).isNotNull();
  }

  @Test
  public void delete() {
    // when
    String assertUsername = "saikou9901delete";
    String assertPassword = "BCryptStringdelete";
    User assertData = new User();
    assertData.setUsername(assertUsername);
    assertData.setPassword(assertPassword);
    userMapper.insert(assertData);
    assertData = userMapper.selectByUsername(assertUsername);

    // try
    userMapper.delete(assertData.getId());
    User subject = userMapper.selectByUsername(assertUsername);

    // expect
    assertThat(subject).isNull();
  }
}
