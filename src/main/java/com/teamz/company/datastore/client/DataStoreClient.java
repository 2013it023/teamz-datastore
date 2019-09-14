package com.teamz.company.datastore.client;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.teamz.company.datastore.constants.DataStoreConstants;
import com.teamz.company.datastore.exception.DataStoreClientException;

/**
 * DataStoreClient - class allows client's to create instance to interact with datastore.
 * 
 * @author Saravanan Perumal
 *
 */
public class DataStoreClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataStoreClient.class);

  /**
   * filePath, local directory link
   */
  private String filePath;

  /**
   * getClient method allows client to create a instance and random DB file in user home directory.
   * 
   * @return DataStoreClient, instance with file path.
   * 
   * @throws DataStoreClientException, if any exception occurred while creating a file in user
   *         directory, then client will get DataStoreClientException
   */
  public static DataStoreClient getClient() throws DataStoreClientException {
    return getClient(createFilePath());
  }

  /**
   * getClient method allows client to create a instance and DB file based upon the client parameter
   * value.
   * 
   * @param filePath, user define DB file location.
   * 
   * @return DataStoreClient,instance with file path
   * 
   * @throws DataStoreClientException, if any exception occurred while creating a file in user
   *         directory, then client will get DataStoreClientException
   */
  public static DataStoreClient getClient(String filePath) throws DataStoreClientException {
    LOGGER.debug("Entering into the get-client method.");

    DataStoreClient client = null;
    try {
      client = createFile(filePath);
    } catch (IOException err) {
      LOGGER.error("Exception occured when trying to create DB.");
      throw new DataStoreClientException(err.getMessage(), err);
    }

    LOGGER.debug("Exiting from get-client method.");
    return client;
  }

  /**
   * createFilePath method used to construct a file path and returns.
   * 
   * @return filePath.
   */
  private static String createFilePath() {
    return String.valueOf(new StringBuilder().append(System.getProperty("user.home"))
        .append(File.separator).append("Documents").append(File.separator).append("Database"));
  }

  /**
   * createFile - used to create a file based upon method argument
   * 
   * @param filePath user defined file location.
   * @return DataStoreClient, to interact with data store
   * @throws IOException in case of directory or file can't be created.
   */
  private static DataStoreClient createFile(String filePath) throws IOException {
    LOGGER.debug("Entering into createFile method.");

    DataStoreClient dataStoreClient = new DataStoreClient();
    filePath += File.separator + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    File dir = new File(filePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    File file = new File(filePath, DataStoreConstants.DB_NAME);
    file.createNewFile();

    File keyDB = new File(filePath, DataStoreConstants.KEY_TABLE_NAME);
    keyDB.createNewFile();

    File deleteDB = new File(filePath, DataStoreConstants.DELETE_KEY_TABLE);
    deleteDB.createNewFile();

    dataStoreClient.filePath = filePath;

    LOGGER.debug("Exiting from createFile method.");
    return dataStoreClient;
  }

  /**
   * getFilePath - allows client to a take file path.
   * 
   * @return local file location.
   */
  public String getFilePath() {
    return this.filePath;
  }

}
