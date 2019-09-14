package com.teamz.company.datastore.response;

import lombok.Builder;

@Builder
public class DataStoreResponse {

  public String key;

  public String value;

  public String message;

  public boolean hasActionError;
}
