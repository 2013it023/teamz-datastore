# File based key value datastore :: LocalDB

   This module allows client to store their data in preferred location, where it's running like in laptop or desktop etc. All the data's are stored in .txt file :page_facing_up:. <br/><br/>
   It provides a two different class to interact with teamz-datastore.
   <br/>
   1. **DataStoreClient.java:**
      - **getClient()** - allows client to create a localDB in default location<br/>
      - **getClient(String filePath)** - allows client to create a localDB in custom location<br/>
      <br/>
   2. **DataStoreUtil.java:** Which provieds an API's to the client to interact with datastore
      - **create(String key, String value, int ttl)** - allows client to store a data into the DB with expire time. <br/>
      - **create(String key, String value)** - allows client to store a data into the DB without any expire time.<br/>
      - **delete(String key)** - allows client to delete a data from DB based upon the key.<br/>
      - **get(String key)** - allows client to retrieve a data from DB based upon the key.<br/>
      <br/>
# How to use this module
This project completely developed by using Java and maven as a build tool. Java 8 is preferrable and we can run it on any OS. To use this module, Please follow the below steps. <br/>

We can use this project in two different ways.

   1. Clone and build a jar with help of maven commands
   2. Clone and add directly into your project build path.

Then create a client and interact with API's.  

Happy coding :blush: !!!
     
   
   




