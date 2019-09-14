package com.teamz.company.datastore.exception;

/**
 * DataStoreClientException - used to throw an exception when client cant create a file in local
 * 
 * @author Saravanan Perumal
 *
 */
public class DataStoreClientException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DataStoreClientException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
