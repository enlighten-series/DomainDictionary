package org.enlightenseries.DomainDictionary.infrastructure.config;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.enlightenseries.DomainDictionary.ApplicationContextProvider;
import org.springframework.core.SpringProperties;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Arrays;

public class LuceneDirectoryFactory {

  private enum Profile{
    NO_CHECK,
    USE_FS_DIR,
    USE_RAM_DIR
  }

  private static Profile profile = Profile.NO_CHECK;
  private static FSDirectory _fsDir = null;
  private static RAMDirectory _ramDir = null;

  /**
   * 環境に合わせてLuceneのDirectoryを用意します。
   * 使用後はCloseの必要はありません。
   *
   * @return 環境に合わせたDirectory
   * @throws IOException
   */
  public static Directory getInstance() throws IOException {
    if (profile == Profile.NO_CHECK) {
      if (
        Arrays.stream(ApplicationContextProvider.getApplicationContext().getEnvironment().getActiveProfiles())
          .filter(s -> s.equals("prod"))
          .count() >= 1
        ) {
        profile = Profile.USE_FS_DIR;
      } else {
        profile = Profile.USE_RAM_DIR;
      }
    }

    if (profile == Profile.USE_FS_DIR) {
      return prepareFSDirectory();
    } else {
      return prepareRAMDirectory();
    }
  }

  private static FSDirectory prepareFSDirectory() throws IOException {
    if (_fsDir != null) {
      _fsDir.close();
    }
    _fsDir = FSDirectory.open(FileSystems.getDefault().getPath("data", "lucene-index"));

    return _fsDir;
  }

  private static RAMDirectory prepareRAMDirectory() {
    if (_ramDir == null) {
      _ramDir = new RAMDirectory();
    }
    return _ramDir;
  }
}
