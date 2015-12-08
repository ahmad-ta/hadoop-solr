package com.lucidworks.hadoop.ingest;

import com.lucidworks.hadoop.io.LWDocument;
import com.lucidworks.hadoop.io.LWDocumentProvider;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;

import java.io.IOException;
import java.util.Map;

import static com.lucidworks.hadoop.utils.ConfigurationKeys.COLLECTION;

/**
 *
 *
 **/
public abstract class BaseHadoopIngest {

  private static final String UNKNOWN = "__unknown__";
  protected Configuration conf;

  public void configure(JobConf conf) {
    this.conf = conf;
    System.setProperty("java.awt.headless", "true");
    try {
      LWDocumentProvider.configure(conf);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void close() throws IOException {
  }

  public LWDocument createDocument() {
    return LWDocumentProvider.createDocument();
  }

  public LWDocument createDocument(String id, Map<String, String> metadata) {
    return LWDocumentProvider.createDocument(id, metadata);
  }

  /**
   * Return the target collection for this job
   */
  public final String getCollection() {
    return conf.get(COLLECTION, UNKNOWN);
  }

  /**
   * Get the AbstractJobFixture implementation for the subclass. The fixture
   * will define how this class is configured, and how it is cleaned up
   *
   * @see com.lucidworks.hadoop.ingest.AbstractJobFixture
   */
  public abstract AbstractJobFixture getFixture();

  public enum Counters {
    DOCS_PUT_FAILED, DOCS_ADDED, DOCS_CONVERT_FAILED,
  }
}
