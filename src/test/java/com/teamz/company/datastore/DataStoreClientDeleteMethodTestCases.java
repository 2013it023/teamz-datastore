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
 * DataStoreClientDeleteMethodTestCases - used to test a DELETE API
 * 
 * @author Saravanan Perumal
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DataStoreClientDeleteMethodTestCases {

  private static DataStoreClient client = null;

  private static DataStoreUtil util = null;

  @Before
  public void init() {
    try {
      client = DataStoreClient.getClient();
      util = new DataStoreUtil(client);
    } catch (DataStoreClientException e) {
      e.printStackTrace();
    }

  }

  /**
   * used to test a delete API with invalid key
   */
  @Test
  public void test_deleteMethodDeleteDataInvalidKey() {
    DataStoreResponse response = util.delete("");
    assertEquals(true, response.hasActionError);
  }

  @Test
  public void test_deleteMethodDeleteDataWithProperData() {
    util.create("abcdefghijklmnopqrFsstuvwxyzABCF",
        "{\r\n" + "  \"array\": [\r\n" + "    1,\r\n" + "    2,\r\n" + "    3\r\n" + "  ],\r\n"
            + "  \"boolean\": true,\r\n" + "  \"color\": \"#82b92c\",\r\n" + "  \"null\": null,\r\n"
            + "  \"number\": 123,\r\n" + "  \"object\": {\r\n" + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\",\r\n" + "    \"e\": \"f\"\r\n" + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n" + "}");
    DataStoreResponse response = util.delete("abcdefghijklmnopqrFsstuvwxyzABCF");
    assertEquals(false, response.hasActionError);
    assertEquals(DataStoreConstants.SUCCESS_DELETE_MESSAGE, response.message);
  }
}
