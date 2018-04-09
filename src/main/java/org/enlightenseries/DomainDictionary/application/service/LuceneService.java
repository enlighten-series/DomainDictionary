package org.enlightenseries.DomainDictionary.application.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.enlightenseries.DomainDictionary.application.exception.ApplicationException;
import org.enlightenseries.DomainDictionary.domain.model.domain.Domain;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneService {

  private Directory directory;
  private Analyzer analyzer;

  public LuceneService(
    Directory directory,
    Analyzer analyzer
  ) {
    this.directory = directory;
    this.analyzer = analyzer;
  }

  private static String DOC_FIELD_ID = "id";
  private static String DOC_FIELD_NAME = "name";
  private static String DOC_FIELD_DESCRIPTION = "description";
  private static String DOC_FIELD_EXISTENTIAL = "existential";
  private static String DOC_FIELD_FORMAT = "format";

  public void regist(Domain newdata) throws Exception {
    if (this.existDomain(newdata.getId())) {
      throw new ApplicationException("このドメインはすでにインデックスに登録されています。");
    }
    registOne(newdata);
  }

  private void registOne(Domain newdata) throws IOException {
    IndexWriter iwriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));

    Document doc = new Document();
    doc.add(new Field(DOC_FIELD_ID, newdata.getId().toString(), StringField.TYPE_STORED));
    doc.add(new Field(DOC_FIELD_NAME, newdata.getName(), TextField.TYPE_STORED));
    doc.add(new Field(DOC_FIELD_DESCRIPTION, newdata.getDescription(), TextField.TYPE_STORED));
    doc.add(new Field(DOC_FIELD_EXISTENTIAL, newdata.getExistential(), TextField.TYPE_STORED));
    doc.add(new Field(DOC_FIELD_FORMAT, newdata.getFormat(), TextField.TYPE_STORED));
    iwriter.addDocument(doc);

    iwriter.close();
  }

  public List<Long> search(String keyword) throws Exception {

    DirectoryReader ireader;
    try {
      ireader = DirectoryReader.open(directory);
    } catch(IndexNotFoundException e) {
      // インデックスは空であるため、ドキュメントなしと同等
      return new ArrayList<>();
    }

    IndexSearcher isearcher = new IndexSearcher(ireader);

    // クエリの組み立て
    Query query = structQuery(keyword);

    // 検索実行
    ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
    List<Long> domainIds = new ArrayList();

    for (int i = 0; i < hits.length; i++) {
      Document hitDoc = isearcher.doc(hits[i].doc);
      IndexableField id = hitDoc.getField(DOC_FIELD_ID);
      if (id != null) {
        domainIds.add(Long.parseLong(id.stringValue()));
      }
    }

    ireader.close();

    return domainIds;
  }

  /**
   * 複数のフィールドに対して検索するQueryを形成する
   * @param keyword
   * @return
   * @throws ParseException
   */
  private Query structQuery(String keyword) throws ParseException {

    // 複数フィールドに対して検索するには、フィールド毎に生成したQueryをBooleanQueryに集める
    BooleanQuery.Builder container = new BooleanQuery.Builder();

    QueryParser parser;
    Query query;

    // ドメイン名に対するQuery
    parser = new QueryParser(DOC_FIELD_NAME, analyzer);
    query = parser.parse(keyword);
    container.add(query, BooleanClause.Occur.SHOULD);

    // 説明に対するQuery
    parser = new QueryParser(DOC_FIELD_DESCRIPTION, analyzer);
    query = parser.parse(keyword);
    container.add(query, BooleanClause.Occur.SHOULD);

    // 存在理由に対するQuery
    parser = new QueryParser(DOC_FIELD_EXISTENTIAL, analyzer);
    query = parser.parse(keyword);
    container.add(query, BooleanClause.Occur.SHOULD);

    // フォーマットに対するQuery
    parser = new QueryParser(DOC_FIELD_FORMAT, analyzer);
    query = parser.parse(keyword);
    container.add(query, BooleanClause.Occur.SHOULD);

    return container.build();
  }

  public boolean existDomain(Long id) throws Exception {
    DirectoryReader ireader;
    try {
      ireader = DirectoryReader.open(directory);
    } catch(IndexNotFoundException e) {
      // インデックスは空であるため、ドキュメントなしと同等
      return false;
    }

    IndexSearcher isearcher = new IndexSearcher(ireader);

    // クエリの組み立て
    Query query = new TermQuery(new Term(DOC_FIELD_ID, id.toString()));

    // 検索実行
    return isearcher.count(query) > 0;
  }

  public void update(Domain newdata) throws Exception {

    DirectoryReader ireader;
    try {
      ireader = DirectoryReader.open(directory);
    } catch(IndexNotFoundException e) {
      // インデックスは空であるため、ドキュメントなしと同等
      throw new ApplicationException("このドメインはまだインデックスに登録されていません。");
    }

    try {
      Term target = new Term(DOC_FIELD_ID, newdata.getId().toString());

      IndexSearcher isearcher = new IndexSearcher(ireader);
      ScoreDoc[] hits = isearcher.search(new TermQuery(target), 1).scoreDocs;
      Document subject = isearcher.doc(hits[0].doc);

      IndexWriter iwriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));

      subject.removeField(DOC_FIELD_NAME);
      subject.removeField(DOC_FIELD_DESCRIPTION);
      subject.removeField(DOC_FIELD_EXISTENTIAL);
      subject.removeField(DOC_FIELD_FORMAT);
      subject.add(new Field(DOC_FIELD_NAME, newdata.getName(), TextField.TYPE_STORED));
      subject.add(new Field(DOC_FIELD_DESCRIPTION, newdata.getDescription(), TextField.TYPE_STORED));
      subject.add(new Field(DOC_FIELD_EXISTENTIAL, newdata.getExistential(), TextField.TYPE_STORED));
      subject.add(new Field(DOC_FIELD_FORMAT, newdata.getFormat(), TextField.TYPE_STORED));

      iwriter.updateDocument(target, subject);

      iwriter.close();
    } catch (IOException e) {
      throw e;
    } finally {
      ireader.close();
    }
  }

  public void delete(Long id) throws Exception {
    try {
      Term target = new Term(DOC_FIELD_ID, id.toString());

      IndexWriter iwriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));
      iwriter.deleteDocuments(target);

      iwriter.close();
    } catch (IOException e) {
      throw e;
    }
  }

}
