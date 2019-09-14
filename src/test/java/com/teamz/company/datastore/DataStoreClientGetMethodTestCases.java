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

@RunWith(MockitoJUnitRunner.class)
public class DataStoreClientGetMethodTestCases {

  private static DataStoreClient client = null;

  private static DataStoreUtil util = null;

  @Before
  public void init() {
    try {
      client = DataStoreClient.getClient();
      util = new DataStoreUtil(client);
    } catch (DataStoreClientException e) {
    }
  }

  @Test
  public void test_getMethodForPositiveScenario() {
    util.create("abcdefghijklmnopqrFsstuvwxyzABCD",
        "{\r\n" + "  \"array\": [\r\n" + "    1,\r\n" + "    2,\r\n" + "    3\r\n" + "  ],\r\n"
            + "  \"boolean\": true,\r\n" + "  \"color\": \"#82b92c\",\r\n" + "  \"null\": null,\r\n"
            + "  \"number\": 123,\r\n" + "  \"object\": {\r\n" + "    \"a\": \"b\",\r\n"
            + "    \"c\": \"d\",\r\n" + "    \"e\": \"f\"\r\n" + "  },\r\n"
            + "  \"string\": \"Hello World\"\r\n" + "}");

    util.delete("abcdefghijklmnopqrFsstuvwxyzABCD");
    DataStoreResponse response = util.get("abcdefghijklmnopqrFsstuvwxyzABCD");
    assertEquals(true, response.hasActionError);
    assertEquals(DataStoreConstants.DATA_NOT_FOUND, response.message);
  }
}
