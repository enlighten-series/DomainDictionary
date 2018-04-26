package org.enlightenseries.DomainDictionary.application.usecase;

import org.enlightenseries.DomainDictionary.application.service.DomainService;
import org.enlightenseries.DomainDictionary.application.service.SearchService;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainSummary;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
  private SearchService searchServiceMock;

  @Before
  public void setup() {
    domainUsecase = spy(new DomainUsecase(
      userServiceMock,
      domainServiceMock,
      domainRepositoryMock,
      searchServiceMock
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
    verify(searchServiceMock, times(1)).regist(any()); // TODO: 値スパイしないとテストの意味が・・・
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
    verify(searchServiceMock, times(1)).regist(any());    // TODO: 値スパイしないとテストの意味が・・・

    verify(userServiceMock, times(1)).findByUsername(updateuser.getUsername());
    verify(domainRepositoryMock, times(1)).updateDomainDetail(any(), any());
    verify(searchServiceMock, times(1)).update(any());    // TODO: 値スパイしないとテストの意味が・・・
  }

  @Test
  public void delete() throws Exception {
    // then
    // no-op

    // do
    domainUsecase.delete(1L);

    // expect
    verify(domainServiceMock, times(1)).delete(1L);
    verify(searchServiceMock, times(1)).delete(1L);
  }

  @Test
  public void search() throws Exception {
    // then
    String keyword = "キーワード";
    List<Long> searchedIds = new ArrayList<>();
    searchedIds.add(1L);
    searchedIds.add(2L);
    when(searchServiceMock.search(keyword)).thenReturn(searchedIds);

    DomainSummary summary1 = new DomainSummary(1L, "ドメイン１");
    DomainSummary summary2 = new DomainSummary(2L, "ドメイン２");
    when(domainRepositoryMock.findDomainSummaryBy(summary1.getId())).thenReturn(summary1);
    when(domainRepositoryMock.findDomainSummaryBy(summary2.getId())).thenReturn(summary2);

    // do
    List<DomainSummary> subject = domainUsecase.keywordSearch(keyword);

    // expect method call
    verify(searchServiceMock, times(1)).search(keyword);
    verify(domainRepositoryMock, times(1)).findDomainSummaryBy(summary1.getId());
    verify(domainRepositoryMock, times(1)).findDomainSummaryBy(summary2.getId());

    // expect result
    assertThat(subject.size()).isEqualTo(2);
    assertThat(subject.get(0).getId()).isEqualTo(summary1.getId());
    assertThat(subject.get(0).getName()).isEqualTo(summary1.getName());
    assertThat(subject.get(1).getId()).isEqualTo(summary2.getId());
    assertThat(subject.get(1).getName()).isEqualTo(summary2.getName());
  }
}
