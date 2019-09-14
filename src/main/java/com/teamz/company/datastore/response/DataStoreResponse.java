package com.teamz.company.datastore.response;

import lombok.Builder;

/**
 * DataStoreResponse - response object of datastore
 * 
 * @author Saravanan Perumal
 *
 */
@Builder
public class DataStoreResponse {

  public String key;

  public String value;

  public String message;

  public boolean hasActionError;
}
