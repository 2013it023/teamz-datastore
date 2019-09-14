package com.teamz.company.datastore;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.teamz.company.datastore.client.DataStoreClient;
import com.teamz.company.datastore.constants.DataStoreConstants;
import com.teamz.company.datastore.exception.DataStoreClientException;
import com.teamz.company.datastore.response.DataStoreResponse;
import com.teamz.company.datastore.utils.DataStoreUtil;


/**
 * DataStoreClientCreateMethodTestCases - used to test CREATE API
 * 
 * @author Saravanan Perumal
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DataStoreClientCreateMethodTestCases {

  private static DataStoreClient client = null;
  private static DataStoreUtil util = null;

  @Before
  public void init() {
    try {
      client = DataStoreClient.getClient();
      util = new DataStoreUtil(client);
    } catch (DataStoreClientException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * used to test a success scenario
   */
  @Test
  public void test_createMethod() {
    DataStoreResponse response = util.create("abcdefghijklmnopqrFsstuvwxyzABCD",
        "{\r\n" + "  \"array\": [\r\n" + "    1,\r\n" + "    2,\r\n" + "    3\r\n" + "  ],\r\n"
            + "  \"boolean\": true,\r\n" + "  \"color\": \"#82b92c\",\r\n" + "  \"null\": null,\r\n"
            + "  \"number\": 123,\r\n" + "  \"object\": {\r\n" + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\",\r\n" + "    \"e\": \"f\"\r\n" + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n" + "}");
    assertEquals(false, response.hasActionError);
    assertEquals(DataStoreConstants.SUCCESS_SAVE_MESSAGE, response.message);
  }

  /**
   * used to test a failure scenario when key already existing in a table.
   */
  @Test
  public void test_createMethodExitingKey() {
    DataStoreResponse response = util.create("abcdefghijklmnopqrFsstuvwxyzABCD",
        "{\r\n" + "  \"array\": [\r\n" + "    1,\r\n" + "    2,\r\n" + "    3\r\n" + "  ],\r\n"
            + "  \"boolean\": true,\r\n" + "  \"color\": \"#82b92c\",\r\n" + "  \"null\": null,\r\n"
            + "  \"number\": 123,\r\n" + "  \"object\": {\r\n" + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\",\r\n" + "    \"e\": \"f\"\r\n" + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n" + "}");
    assertEquals(true, response.hasActionError);
  }

  /**
   * used to test a create method without key
   */
  @Test
  public void test_createMethodWithoutKey() {
    DataStoreResponse response = util.create("",
        "{\r\n" + "  \"array\": [\r\n" + "    1,\r\n" + "    2,\r\n" + "    3\r\n" + "  ],\r\n"
            + "  \"boolean\": true,\r\n" + "  \"color\": \"#82b92c\",\r\n" + "  \"null\": null,\r\n"
            + "  \"number\": 123,\r\n" + "  \"object\": {\r\n" + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\",\r\n" + "    \"e\": \"f\"\r\n" + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n" + "}");
    assertEquals(true, response.hasActionError);
    assertEquals(DataStoreConstants.INVALID_KEY_MESSAGE, response.message);
  }

  /**
   * used to test a create method with improper data
   */
  @Test
  public void test_createMethodWithImproperData() {
    DataStoreResponse response = util.create("abcdefghijklmnopqrFsstuvwxyzABCD", "Hello World");
    assertEquals(true, response.hasActionError);
    assertEquals(DataStoreConstants.INVALID_KEY_MESSAGE, response.message);
  }

}
