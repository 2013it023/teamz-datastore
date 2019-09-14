package com.teamz.company.datastore.constants;

/**
 * DataStoreConstants - class which contains constants across used in data store module.
 * 
 * @author Saravanan Perumal
 *
 */
public class DataStoreConstants {

  // Common constants
  public static final int DEFAULT_TTL_VALUE = -1;
  public static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]*$";
  public static final String NEW_LINE = "\n";
  public static final String SEPARATOR = "::";

  // Table name
  public static final String DELETE_KEY_TABLE = "deletedKeyDB.txt";
  public static final String KEY_TABLE_NAME = "keyDB.txt";
  public static final String DB_NAME = "localDB.txt";

  // Messages used in API's response
  public static final String DATA_NOT_FOUND = "Data not found in table.";
  public static final String SUCCESS_DELETE_MESSAGE = "Data has been deleted successfully.";
  public static final String FAILURE_DELETE_MESSAGE = "Data already remvoed from table.";
  public static final String KEY_ALREADY_EXIST = "Key is already existing in Table.";
  public static final String SUCCESS_SAVE_MESSAGE = "Data stored into DB successfully!!";
  public static final String INVALID_KEY_MESSAGE =
      "The key length should be equal to 32 or the data is not a valid json. ";

  // Data indexes in Main Table
  public static final int KEY_INDEX = 0;
  public static final int DATA_INDEX = 1;
  public static final int SAVED_DATETIME_INDEX = 2;
  public static final int TTL_INDEX = 3;

}
