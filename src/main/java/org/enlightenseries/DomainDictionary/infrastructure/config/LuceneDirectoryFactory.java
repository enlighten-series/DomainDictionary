package org.enlightenseries.DomainDictionary.infrastructure.config;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;

public class LuceneDirectoryFactory {

  public static Directory getOpenedInstance() throws IOException {
    return FSDirectory.open(FileSystems.getDefault().getPath("data", "lucene-index"));
  }
}
