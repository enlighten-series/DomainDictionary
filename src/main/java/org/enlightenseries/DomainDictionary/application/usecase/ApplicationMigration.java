package org.enlightenseries.DomainDictionary.application.usecase;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.enlightenseries.DomainDictionary.application.config.ApplicationDemoProperties;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.infrastructure.datasource.LuceneDatasource;
import org.enlightenseries.DomainDictionary.application.service.UserService;
import org.enlightenseries.DomainDictionary.application.singleton.ApplicationMigrationStatus;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainDetail;
import org.enlightenseries.DomainDictionary.domain.model.domain.DomainRepository;
import org.enlightenseries.DomainDictionary.domain.model.metadata.Metadata;
import org.enlightenseries.DomainDictionary.domain.model.metadata.MetadataRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelation;
import org.enlightenseries.DomainDictionary.domain.model.relation.DomainToRelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.relation.Relation;
import org.enlightenseries.DomainDictionary.domain.model.relation.RelationRepository;
import org.enlightenseries.DomainDictionary.domain.model.user.User;
import org.enlightenseries.DomainDictionary.domain.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@Service
public class ApplicationMigration {

  @Autowired
  private ApplicationDemoProperties applicationDemoProperties;

  private final String exportOutputDirectioryPath = "./data";
  private final String exportDomainFileName = "export.csv";

  private UserService userService;
  private LuceneDatasource luceneService;

  private MetadataRepository metadataRepository;
  private UserRepository userRepository;
  private DomainRepository domainRepository;
  private DomainToRelationRepository domainToRelationRepository;
  private RelationRepository relationRepository;

  private ApplicationMigrationStatus applicationMigrationStatus;

  private String currentMajorVersion;
  private String currentMinorVersion;
  private String currentPatchVersion;

  public ApplicationMigration(
    UserService _userService,
    LuceneDatasource _luceneService,
    MetadataRepository _metadataRepository,
    UserRepository _userRepository,
    DomainRepository _domainRepository,
    DomainToRelationRepository _domainToRelationRepository,
    RelationRepository _relationRepository,
    ApplicationMigrationStatus _applicationMigrationStatus
  ) {
    this.userService = _userService;
    this.luceneService = _luceneService;
    this.metadataRepository = _metadataRepository;
    this.userRepository = _userRepository;
    this.domainRepository = _domainRepository;
    this.domainToRelationRepository = _domainToRelationRepository;
    this.relationRepository = _relationRepository;

    this.applicationMigrationStatus = _applicationMigrationStatus;

    this.currentMajorVersion = "0";
    this.currentMinorVersion = "5";
    this.currentPatchVersion = "0";
  }

  @PostConstruct
  public void checkApplicationMetadata() {
    try {
      Metadata majorVersion = this.metadataRepository.findByKey("major_version");
      Metadata minorVersion = this.metadataRepository.findByKey("minor_version");
      Metadata patchVersion = this.metadataRepository.findByKey("patch_version");

      // TODO: バージョン確認とマイグレーション処理
    } catch (Exception e) {
      // TODO: 例外内容がテーブル未作成であることを判断
      this.initializeApplicationDatabase();
    }
  }

  private void initializeApplicationDatabase() {
    try {
      createTables();
      insertInitialData();
    } catch (ApplicationException e) {
      e.printStackTrace();
    }
  }

  private void createTables() {
    this.metadataRepository.createTable();
    this.userRepository.createTable();
    this.domainRepository.createTable();
    this.domainToRelationRepository.createTable();
    this.relationRepository.createTable();
  }

  private void insertInitialData() throws ApplicationException {
    // TODO: バージョン番号を定数取得（Gradleとか）
    Metadata majorVersion = new Metadata();
    majorVersion.setKey("major_version");
    majorVersion.setValue(currentMajorVersion);
    Metadata minorVersion = new Metadata();
    minorVersion.setKey("minor_version");
    minorVersion.setValue(currentMinorVersion);
    Metadata patchVersion = new Metadata();
    patchVersion.setKey("patch_version");
    patchVersion.setValue(currentPatchVersion);

    this.metadataRepository.register(majorVersion);
    this.metadataRepository.register(minorVersion);
    this.metadataRepository.register(patchVersion);

    // デフォルトユーザ登録
    User defaultUser = new User();
    defaultUser.setUsername("admin");
    this.userService.createNewUser(defaultUser, "admin");

    if (applicationDemoProperties.isInitdata()) {
      insertDemoData();
    }
  }

  private void insertDemoData() {
    User demoRegistUser = this.userService.findByUsername("admin");

    Domain sample00 = new Domain();
    sample00.setName("★DomainDictionaryのつかいかた");
    sample00.setDescription("# 基本\n" +
      "業務用語・情報・ドメインを項目として登録したり、関連づけたりすることができます。\r\n" +
      "# 使い方\n" +
      "ログインしない場合は、閲覧のみ可能です。ドメインを追加したり、関連づけたりするには、ログインを行います。\n" +
      "# ログイン\n" +
      "画面右上のまるいボタンからログインメニューが表示できます。\n\n" +
      "デフォルトユーザは `admin`/`admin` です。\n\n" +
      "「サインアップ」から新規ユーザを作成することもできます。");
    sample00.setExistential("# なぜこれを作ったか\n" +
      "我が社のような伝統的オフラインSIerにおいて、その担当業務の情報をチーム内で維持管理し、育てていくために使用します。\n\n" +
      "うちのような会社で働く人は、なんでもできるような道具を効率的に使用することが難しいです。そのため、できることを制限することによってそれを使用する人の意識を育てる意味で機能設計をしています。");
    sample00.setFormat("");
    DomainDetail sample00Detail = new DomainDetail(sample00);
    sample00Detail.setCreatedBy(demoRegistUser);
    sample00Detail.setUpdatedBy(demoRegistUser);

    Domain sample01 = new Domain();
    sample01.setName("要件定義");
    sample01.setDescription("システムの要件を定義する。だいたい要件は固まらない。");
    sample01.setExistential("ウォーターフォール開発プロセスにおける工程の１つ。");
    sample01.setFormat("");
    DomainDetail sample01Detail = new DomainDetail(sample01);
    sample01Detail.setCreatedBy(demoRegistUser);
    sample01Detail.setUpdatedBy(demoRegistUser);

    Domain sample02 = new Domain();
    sample02.setName("外部設計");
    sample02.setDescription("データモデル、業務画面設計などをする。この段階でも要件は固まらない。");
    sample02.setExistential("ウォーターフォール開発プロセスにおける工程の１つ。");
    sample02.setFormat("");
    DomainDetail sample02Detail = new DomainDetail(sample02);
    sample02Detail.setCreatedBy(demoRegistUser);
    sample02Detail.setUpdatedBy(demoRegistUser);

    Domain sample03 = new Domain();
    sample03.setName("ウォーターフォール");
    sample03.setDescription("システム開発プロセスとして良く知られる要件");
    sample03.setExistential("ウォーターフォール開発プロセスにおける工程の１つ。");
    sample03.setFormat("");
    DomainDetail sample03Detail = new DomainDetail(sample03);
    sample03Detail.setCreatedBy(demoRegistUser);
    sample03Detail.setUpdatedBy(demoRegistUser);

    domainRepository.registerDomainDetail(sample00Detail);
    domainRepository.registerDomainDetail(sample01Detail);
    domainRepository.registerDomainDetail(sample02Detail);
    domainRepository.registerDomainDetail(sample03Detail);
    try {
      luceneService.regist(sample00Detail);
      luceneService.regist(sample01Detail);
      luceneService.regist(sample02Detail);
      luceneService.regist(sample03Detail);
    } catch(Exception e) {
      System.out.println("デモデータのインデックス登録に失敗しました");
      e.printStackTrace();
    }

    Relation relation1_3 = new Relation();
    DomainToRelation dr1_3_1 = new DomainToRelation(sample01Detail.getId(), relation1_3.getId());
    DomainToRelation dr1_3_3 = new DomainToRelation(sample03Detail.getId(), relation1_3.getId());

    Relation relation2_3 = new Relation();
    DomainToRelation dr2_3_2 = new DomainToRelation(sample02Detail.getId(), relation2_3.getId());
    DomainToRelation dr2_3_3 = new DomainToRelation(sample03Detail.getId(), relation2_3.getId());

    relationRepository.register(relation1_3);
    domainToRelationRepository.register(dr1_3_1);
    domainToRelationRepository.register(dr1_3_3);
    relationRepository.register(relation2_3);
    domainToRelationRepository.register(dr2_3_2);
    domainToRelationRepository.register(dr2_3_3);
  }

  /**
   * エクスポートファイルの生成を開始する
   */
  @Async("generatingExportFileExecutor")
  public void generatingExportFile() throws Exception {
    applicationMigrationStatus.setNowGeneratingExportFile(true);

    String exportFilePath = exportOutputDirectioryPath + "/" + exportDomainFileName;
    File exportFile = new File(exportFilePath);

    try (BufferedWriter bw = new BufferedWriter(
      new OutputStreamWriter(new FileOutputStream(exportFile), StandardCharsets.UTF_8))) {
      exportFile.createNewFile();
      CSVPrinter printer = CSVFormat.RFC4180.print(bw);

      // バージョン番号出力
      printer.printRecord("Version",
        currentMajorVersion,
        currentMinorVersion,
        currentPatchVersion
      );

      // 各Repositoryにファイルへのエクスポートを依頼する（大量データ処理はインフラに依存するため）
      domainRepository.export(printer);
      relationRepository.export(printer);
      domainToRelationRepository.export(printer);
      userRepository.export(printer);

    } catch (Exception e) {
      throw e;
    } finally {
      applicationMigrationStatus.setNowGeneratingExportFile(false);
    }
  }

  public String getExportFilePath() {
    return exportOutputDirectioryPath + "/" + exportDomainFileName;
  }

  /**
   * エクスポートファイルが生成途中か否かを判断する
   *
   * @return 生成処理中ならtrue, 未作成／作成済み問わず処理をしていなければfalse
   */
  public boolean isGeneratingExportFile() {
    return applicationMigrationStatus.isNowGeneratingExportFile();
  }

  /**
   * 生成ずみのエクスポートファイルの作成日時を返す
   *
   * @return ファイルが存在しなければnullを返す
   */
  public Date getExportFileGeneratedDate() throws IOException {
    File exportFile = new File(exportOutputDirectioryPath + "/" + exportDomainFileName);

    if (!exportFile.exists()) {
      return null;
    }

    BasicFileAttributes attr = Files.readAttributes(exportFile.toPath(), BasicFileAttributes.class);

    return Date.from(attr.lastModifiedTime().toInstant());
  }

  /**
   * 独自例外をロールバック対象に含めるよう、rollbackForを指定する
   * @param importFile
   * @throws Exception
   */
  @Transactional(rollbackFor = Exception.class)
  public void startImport(MultipartFile importFile) throws Exception {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(importFile.getInputStream(), StandardCharsets.UTF_8))) {

      CSVParser parser = CSVFormat
        .RFC4180
        .withIgnoreEmptyLines(true)
        .withIgnoreSurroundingSpaces(true)
        .parse(br);

      String majorVersion = "";
      String minorVersion = "";
      String patchVersion = "";

      for(CSVRecord firstRecord : parser) {
        majorVersion = firstRecord.get(1);
        minorVersion = firstRecord.get(2);
        patchVersion = firstRecord.get(3);
        break;
      }

      if (majorVersion.equals("0")
        && minorVersion.equals("2")
        && patchVersion.equals("2")) {
        import_0_2_X(parser);
        return;
      }

      if (majorVersion.equals("0")
        && minorVersion.equals("3")
        && patchVersion.equals("4")) {
        import_0_3_X(parser);
        return;
      }

      if (majorVersion.equals("0")
        && minorVersion.equals("4")
        && patchVersion.equals("2")) {
        import_0_4_X(parser);
        return;
      }

      throw new ApplicationException("バージョン " +
        majorVersion + "." +
        minorVersion + "." +
        patchVersion + " のエクスポートファイルをインポートすることはできません。Githubのバージョンアップ手順を確認してください。");

    } catch (IOException e) {
      throw e;
    }
  }

  private void import_0_2_X(CSVParser parser) throws Exception {
    domainRepository.import_0_2_X(parser);
    relationRepository.import_0_2_X(parser);
    domainToRelationRepository.import_0_2_X(parser);
  }

  private void import_0_3_X(CSVParser parser) throws Exception {
    domainRepository.import_0_3_X(parser);
    relationRepository.import_0_3_X(parser);
    domainToRelationRepository.import_0_3_X(parser);
  }

  private void import_0_4_X(CSVParser parser) throws Exception {
    domainRepository.import_0_4_X(parser);
    relationRepository.import_0_4_X(parser);
    domainToRelationRepository.import_0_4_X(parser);
    userRepository.import_0_4_X(parser);
  }

}
