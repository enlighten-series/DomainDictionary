package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.service.LuceneService;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainUsecaseTest {

  private DomainUsecase domainUsecase;

  @MockBean
  private UserService userServiceMock;

  @MockBean
  private DomainService domainServiceMock;

  @MockBean
  private DomainRepository domainRepositoryMock;

  @MockBean
  private LuceneService luceneServiceMock;

  @Before
  public void setup() {
    domainUsecase = spy(new DomainUsecase(
      userServiceMock,
      domainServiceMock,
      domainRepositoryMock,
      luceneServiceMock
    ));
  }

  @Test
  public void register() throws Exception {
    // then
    User loginuser = new User(1L, "tarou", "password");
    when(userServiceMock.findByUsername(any())).thenReturn(loginuser);
    Domain subject = new Domain(
      1L,
      "ドメイン",
      "フォーマット",
      "説明",
      "存在",
      new Date(),
      new Date());

    // do
    domainUsecase.register(subject);

    // expect
    verify(userServiceMock, times(1)).findByUsername(any());
    verify(domainServiceMock, times(0)).register(any());
    verify(domainRepositoryMock, times(1)).registerDomainDetail(any());
    verify(luceneServiceMock, times(1)).regist(any()); // TODO: 値スパイしないとテストの意味が・・・
  }

  @Test
  public void update() throws Exception {
    // then
    User registuser = new User(1L, "tarou", "password");
    User updateuser = new User(2L, "jirou", "password");
    when(userServiceMock.findByUsername(registuser.getUsername())).thenReturn(registuser);
    when(userServiceMock.findByUsername(updateuser.getUsername())).thenReturn(updateuser);

    // before do
    when(userServiceMock.getLoginUsername()).thenReturn(registuser.getUsername());
    Domain subject = new Domain(
      1L,
      "ドメイン",
      "フォーマット",
      "説明",
      "存在",
      new Date(),
      new Date());
    domainUsecase.register(subject);

    // do
    when(userServiceMock.getLoginUsername()).thenReturn(updateuser.getUsername());
    subject.setName("新しいドメイン");
    domainUsecase.update(subject.getId(), subject);

    // expect
    verify(userServiceMock, times(1)).findByUsername(registuser.getUsername());
    verify(domainRepositoryMock, times(1)).registerDomainDetail(any());
    verify(luceneServiceMock, times(1)).regist(any());    // TODO: 値スパイしないとテストの意味が・・・

    verify(userServiceMock, times(1)).findByUsername(updateuser.getUsername());
    verify(domainRepositoryMock, times(1)).updateDomainDetail(any(), any());
    verify(luceneServiceMock, times(1)).update(any());    // TODO: 値スパイしないとテストの意味が・・・
  }

  @Test
  public void delete() throws Exception {
    // then

    // do
    domainUsecase.delete(1L);

    // expect
    verify(domainServiceMock, times(1)).delete(1L);
    verify(luceneServiceMock, times(1)).delete(1L);
  }
}
