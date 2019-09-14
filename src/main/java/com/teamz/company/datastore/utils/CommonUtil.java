package com.teamz.company.datastore.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.teamz.company.datastore.constants.DataStoreConstants;

/**
 * CommonUtil contains commons methods which is used around this project module.
 * 
 * @author Saravanan Perumal
 *
 */
public class CommonUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

  /**
   * used to build a data for insertion.
   * 
   * @param ttl, expire time in seconds.
   * @param args, list of data's
   * @return data to be stored in DB.
   */
  public static String buildData(int ttl, String... args) {
    return new StringBuilder().append(args[0]).append(DataStoreConstants.SEPARATOR)
        .append(args[1].replace("\n", "").replace("\r", "")).append(DataStoreConstants.SEPARATOR)
        .append(args[2]).append(DataStoreConstants.SEPARATOR).append(ttl)
        .append(DataStoreConstants.NEW_LINE).toString();
  }

  /**
   * Used to validate the key.
   * 
   * @param key, unique id
   * @return true or false based on the condition.
   */
  public static boolean isValidKey(String key) {
    return Objects.nonNull(key) && key.length() == 32
        && key.matches(DataStoreConstants.ALPHANUMERIC_REGEX);
  }

  /**
   * used to validate the value.
   * 
   * @param value, data need to be stored in DB.
   * @return true or false based upon the condition.
   */
  public static boolean isValidData(String value) {
    if (Objects.isNull(value))
      return false;
    try {
      new JSONObject(value);
    } catch (JSONException exception) {
      try {
        new JSONArray(value);
      } catch (JSONException ex) {
        LOGGER.error("Exception occured while trying to parse the value from string to json.");
        return false;
      }
    }
    return true;
  }

  /**
   * To get current date time in epochsecond
   * 
   * @return epoch value.
   */
  public static long getEpocheSecond() {
    return Instant.now().getEpochSecond();
  }

  /**
   * used to return the main table location
   * 
   * @param filePath, directory link
   * @return full file path
   */
  public static String getDBFile(String filePath) {
    return filePath + File.separator + DataStoreConstants.DB_NAME;
  }

  /**
   * used to return the key file table location
   * 
   * @param filePath, directory link
   * @return full file path
   */
  public static String getKeyFile(String filePath) {
    return filePath + File.separator + DataStoreConstants.KEY_TABLE_NAME;

  }

  /**
   * used to return the delete key file location.
   * 
   * @param filePath , directory link
   * @return full file path
   */
  public static String getDeleteKeyFile(String filePath) {
    return filePath + File.separator + DataStoreConstants.DELETE_KEY_TABLE;
  }

  /**
   * used to save the data into respective location.
   * 
   * @param data, need to be saved in DB.
   * @param filePath, where we need to save
   * @throws IOException, throws when couldn't write a data into DB
   */
  public static void writeDataToDB(String data, String filePath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
    writer.append(data);
    writer.close();
  }
}
