package com.teamz.company.datastore.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.teamz.company.datastore.client.DataStoreClient;
import com.teamz.company.datastore.constants.DataStoreConstants;
import com.teamz.company.datastore.response.DataStoreResponse;
import com.teamz.company.datastore.service.DataStoreClientService;
import com.teamz.company.datastore.service.impl.DataStoreclientServiceImpl;

/**
 * DataStoreUtil - class provides an API to interact with Data store to perform CREATE, READ and
 * DELETE operation.
 * 
 * @author Saravanan Perumal
 *
 */
public class DataStoreUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataStoreUtil.class);

  private String filePath;

  DataStoreClientService clientService = new DataStoreclientServiceImpl();

  public DataStoreUtil(DataStoreClient client) {
    this.filePath = client.getFilePath();
  }

  /**
   * Create method - used to save data , when client do not want to provide an expire data time .
   * 
   * @param key unique id of the data
   * @param value should be a JSON format.
   * @return {@link DataStoreResponse}, returns a status to the client.
   */
  public DataStoreResponse create(String key, String value) {
    return create(key, value, DataStoreConstants.DEFAULT_TTL_VALUE);
  }

  /**
   * Create method - used to save data , when Client wants to use an expire time.
   * 
   * @param key unique id of the data and length should be 32.
   * @param value should be an JSON format.
   * @param ttl expire time of the data.
   * @return {@link DataStoreResponse} returns a status to the client
   */
  public DataStoreResponse create(String key, String value, int ttl) {
    LOGGER.debug("Entering into the create method to save the data.");
    if (!CommonUtil.isValidKey(key) || !CommonUtil.isValidData(value)) {
      return DataStoreResponse.builder().hasActionError(Boolean.TRUE)
          .message(DataStoreConstants.INVALID_KEY_MESSAGE).build();
    }
    DataStoreResponse response = clientService.insert(key, value, filePath, ttl);
    LOGGER.debug("Exiting from create method.");
    return response;
  }

  /**
   * Delete method - used to delete an data from DB
   * 
   * @param key unique id of the data and length should be 32.
   * @return {@link DataStoreResponse} returns a status to the clients
   */
  public DataStoreResponse delete(String key) {
    LOGGER.debug("Entering into the delete method to remove data.");
    if (!CommonUtil.isValidKey(key)) {
      return DataStoreResponse.builder().hasActionError(Boolean.TRUE)
          .message(DataStoreConstants.INVALID_KEY_MESSAGE).build();
    }
    DataStoreResponse reponse = clientService.delete(key, filePath);
    LOGGER.debug("Exiting from delete method.");
    return reponse;
  }

  /**
   * get method - used to get a value from DB.
   * 
   * @param key unique id of the data and length should be 32.
   * @return {@link DataStoreResponse} returns a data to the client
   */
  public DataStoreResponse get(String key) {
    LOGGER.debug("Entering into the get method to fetch a data.");
    if (!CommonUtil.isValidKey(key)) {
      return DataStoreResponse.builder().hasActionError(Boolean.TRUE)
          .message(DataStoreConstants.INVALID_KEY_MESSAGE).build();
    }
    DataStoreResponse response = clientService.get(key, filePath);
    LOGGER.debug("Exiting from get method.");
    return response;
  }
}
