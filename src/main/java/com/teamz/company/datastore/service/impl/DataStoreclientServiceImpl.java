package com.teamz.company.datastore.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.teamz.company.datastore.constants.DataStoreConstants;
import com.teamz.company.datastore.response.DataStoreResponse;
import com.teamz.company.datastore.response.DataStoreResponse.DataStoreResponseBuilder;
import com.teamz.company.datastore.service.DataStoreClientService;
import com.teamz.company.datastore.utils.CommonUtil;

/**
 * DataStoreclientServiceImpl - class contains implementation level information about all the client
 * API's.
 * 
 * @author psa9ack
 *
 */
public class DataStoreclientServiceImpl implements DataStoreClientService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataStoreclientServiceImpl.class);

  private static long LAST_LINE_NUMBER = 0;

  @Override
  public DataStoreResponse insert(String key, String value, String filePath, int ttl) {

    if (isAlreadyExist(key, CommonUtil.getKeyFile(filePath))) {
      return DataStoreResponse.builder().hasActionError(Boolean.TRUE)
          .message(DataStoreConstants.KEY_ALREADY_EXIST).build();
    }

    DataStoreResponseBuilder response = DataStoreResponse.builder();
    String dbData =
        CommonUtil.buildData(ttl, key, value, String.valueOf(CommonUtil.getEpocheSecond()));

    try {
      saveIntoKeyTable(key, filePath);
      CommonUtil.writeDataToDB(dbData, CommonUtil.getDBFile(filePath));
      response.hasActionError(Boolean.FALSE).message(DataStoreConstants.SUCCESS_SAVE_MESSAGE);
    } catch (IOException e) {
      LOGGER.debug("Exception occured when trying to save the data into the DB");
      response.hasActionError(Boolean.TRUE).message(e.getMessage());
    }

    return response.build();
  }

  @Override
  public DataStoreResponse get(String key, String filePath) {

    if (isAlreadyExist(key, CommonUtil.getDeleteKeyFile(filePath))) {
      return DataStoreResponse.builder().hasActionError(Boolean.TRUE)
          .message(DataStoreConstants.DATA_NOT_FOUND).build();
    }

    int lineNumber = getLineNumber(key, filePath);

    String responseData = "";
    DataStoreResponseBuilder response = DataStoreResponse.builder();

    try (Stream<String> lines = Files.lines(Paths.get(CommonUtil.getDBFile(filePath)))) {
      responseData = lines.skip(lineNumber - 1).findFirst().get();
      String[] responseArray = responseData.split(DataStoreConstants.SEPARATOR);
      if (responseArray.length == 0 || isExpired(responseArray)) {
        response.hasActionError(Boolean.TRUE).message(DataStoreConstants.DATA_NOT_FOUND);
      } else {
        response.hasActionError(false).key(responseArray[0]).value(responseArray[1]);
      }
    } catch (IOException e) {
      LOGGER.error("Exception occured while trying to read data from table.");
      response.hasActionError(Boolean.TRUE).message(e.getMessage());
    }

    return response.build();
  }

  @Override
  public DataStoreResponse delete(String key, String filePath) {

    if (isAlreadyExist(key, CommonUtil.getDeleteKeyFile(key))) {
      return DataStoreResponse.builder().hasActionError(Boolean.TRUE)
          .message(DataStoreConstants.FAILURE_DELETE_MESSAGE).build();
    }

    DataStoreResponseBuilder response = DataStoreResponse.builder();

    try {
      CommonUtil.writeDataToDB(key + DataStoreConstants.NEW_LINE,
          CommonUtil.getDeleteKeyFile(filePath));
      response.hasActionError(Boolean.FALSE).message(DataStoreConstants.SUCCESS_DELETE_MESSAGE);
    } catch (IOException e) {
      LOGGER.debug("Exception occured while trying to remove a data from DB.");
      response.hasActionError(Boolean.TRUE).message(e.getMessage());
    }

    return response.build();
  }

  private void saveIntoKeyTable(String key, String filePath) throws IOException {
    LAST_LINE_NUMBER = LAST_LINE_NUMBER + 1;
    String keyData = new StringBuilder().append(key).append(DataStoreConstants.SEPARATOR)
        .append(LAST_LINE_NUMBER).append(DataStoreConstants.NEW_LINE).toString();
    CommonUtil.writeDataToDB(keyData, CommonUtil.getKeyFile(filePath));
  }

  private List<String> getKeys(String filePath) throws IOException {
    return Files.readAllLines(Paths.get(filePath));
  }

  private boolean isExpired(String[] responseArray) {
    return Long.valueOf(
        responseArray[DataStoreConstants.TTL_INDEX]) != DataStoreConstants.DEFAULT_TTL_VALUE
        && (CommonUtil.getEpocheSecond()
            - Long.valueOf(responseArray[DataStoreConstants.SAVED_DATETIME_INDEX])) > Long
                .valueOf(responseArray[DataStoreConstants.TTL_INDEX]);
  }

  private boolean isAlreadyExist(String key, String filePath) {
    List<CompletableFuture<String>> checkList = searchInKeyTable(key, filePath);
    for (int i = 0; i < checkList.size(); i++) {
      CompletableFuture<String> keyValue = checkList.get(i);
      try {
        return !keyValue.isCompletedExceptionally() && Objects.nonNull(keyValue.get());
      } catch (Exception exception) {
        LOGGER.debug("Exception occured while searching a key.");
      }
    }
    return false;
  }

  private CompletableFuture<String> searchKey(String key, List<String> subKeyList) {
    return CompletableFuture.supplyAsync(() -> subKeyList.stream()
        .filter(predicate -> predicate.contains(key)).findFirst().orElse(null));
  }

  private int getLineNumber(String key, String filePath) {
    List<CompletableFuture<String>> checkList = searchInKeyTable(key, filePath);
    for (int i = 0; i < checkList.size(); i++) {
      CompletableFuture<String> keyValue = checkList.get(i);
      try {
        if (!keyValue.isCompletedExceptionally()) {
          return Integer.valueOf(keyValue.get().split(DataStoreConstants.SEPARATOR)[1]);
        }
      } catch (Exception exception) {
        LOGGER.debug("Exception occured while searching a key.");
      }
    }
    return 0;
  }

  private List<CompletableFuture<String>> searchInKeyTable(String key, String filePath) {
    int size = 25;
    List<CompletableFuture<String>> checkList = new ArrayList<>();
    List<String> keyList = new ArrayList<>();
    try {
      keyList = getKeys(CommonUtil.getKeyFile(filePath));
    } catch (IOException e) {
      LOGGER.debug("Exception occured when trying to read the keys.");
    }

    for (int start = 0; start < keyList.size(); start += size) {
      checkList.add(searchKey(key, keyList.subList(start, Math.min(start + size, keyList.size()))));
    }
    return checkList;
  }
}
