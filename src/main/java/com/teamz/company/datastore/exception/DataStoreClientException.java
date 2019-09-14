package com.teamz.company.datastore.exception;

public class DataStoreClientException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DataStoreClientException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
