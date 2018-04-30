package org.enlightenseries.DomainDictionary.infrastructure.datasource.user;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.domain.model.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.UUID;

@Repository
public class UserDatasource implements UserRepository {

  private UserMapper userMapper;

  public UserDatasource(
    UserMapper _userMapper
  ) {
    this.userMapper = _userMapper;
  }

  @Override
  public User findByUsername(String username) {
    return userMapper.selectByUsername(username);
  }

  @Override
  public void register(User user) {
    userMapper.insert(user);
  }

  @Override
  public void update(User user) {
    userMapper.update(user);
  }

  @Override
  public void delete(Long id) {
    userMapper.delete(id);
  }

  @Override
  public void createTable() {
    userMapper.createTable();
  }

  @Override
  public void export(CSVPrinter printer) throws Exception {

    printer.printRecord("User start");

    userMapper.exportAll(context -> {
      User user = context.getResultObject();
      try {
        printer.printRecord(
          user.getId(),
          user.getUsername(),
          user.getPassword()
        );
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    printer.printRecord("User end");
  }

  @Override
  public void import_0_4_X(CSVParser parser) throws Exception {
    userMapper.deleteAllForImport();

    boolean proceed = false;
    for(CSVRecord record : parser) {
      if (!proceed) {
        if (record.get(0).equals("User start")) {
          proceed = true;
          continue;
        }
        throw new ApplicationException("Userの開始位置が見つかりませんでした。");
      }
      if (record.get(0).equals("User end")) {
        return;
      }

      User _new = new User(
        Long.valueOf(record.get(0)),
        record.get(1),
        record.get(2)
      );
      userMapper.insertForImport(_new);
    }

    throw new ApplicationException("Userの終了位置が見つかりませんでした。");
  }
}
