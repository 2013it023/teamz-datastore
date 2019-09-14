package com.teamz.company.datastore.service;

import com.teamz.company.datastore.response.DataStoreResponse;

/**
 * DataStoreClientService contains blue print of all API's, which is used to interact with Data
 * store.
 * 
 * @author Saravanan Perumal
 *
 */
public interface DataStoreClientService {

  /**
   * Inserting the data into DB.
   * 
   * @param key, unique id and maximum length is 32.
   * @param value, data need to be store in DB and it should be in JSON format, maximum size is
   *        16KB.
   * @param filePath, where the data need to stored.It's a local path.
   * @param ttl, life time of the data stored in DB. It should be in seconds
   * @return {@link DataStoreResponse}, returns the status of the processed data.
   */
  DataStoreResponse insert(String key, String value, String filePath, int ttl);

  /**
   * fetching the data from DB based upon the key.
   * 
   * @param key, unique id and maximum length is 32 bit.
   * @param filePath, from where the data need to retrieved. It should be a local path.
   * @return {@link DataStoreResponse} returns the value for particular key if exist's in DB.
   */
  DataStoreResponse get(String key, String filePath);

  /**
   * deleting the value from DB based upon the key.
   * 
   * @param key, unique id and maximum length is 32 bit.
   * @param filePath , To where we need to perform delete operation.
   */
  DataStoreResponse delete(String key, String filePath);
}
