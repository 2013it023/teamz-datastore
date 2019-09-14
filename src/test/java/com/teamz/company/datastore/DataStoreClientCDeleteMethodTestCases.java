package com.teamz.company.datastore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.teamz.company.datastore.client.DataStoreClient;
import com.teamz.company.datastore.exception.DataStoreClientException;
import com.teamz.company.datastore.response.DataStoreResponse;
import com.teamz.company.datastore.utils.DataStoreUtil;

@RunWith(MockitoJUnitRunner.class)
public class DataStoreClientCDeleteMethodTestCases {

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

	@Test
	public void test_deleteMethodDeleteData() {
		DataStoreResponse response = util.delete("");
	}
}
