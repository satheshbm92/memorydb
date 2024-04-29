package comp.Box.database;

import java.util.HashMap;
import java.util.Map;

/*
Set: If a key already exists, the count of the old value is decreased before updating the key to the new value.
Delete: Decreases the count of the value associated with the key and removes the key from the map.
Count: Returns the number of keys that have the specified value.

x - 10
y - 20
z - 20

10 - 1
20 - 2

This class acts as the basic data storage unit of our system. It stores key-value pairs and also maintains a count of how many
times each value occurs across the keys. The class provides fundamental operations to set a value, retrieve a value, delete a key,
 and count occurrences of values. It's designed to ensure fast access and updates to data which is crucial for the performance of an in-memory database."

public void Set(String key, Integer value) throws DatabaseException {
    if(key == null || value == null) {
        throw new DatabaseException("Key or value cannot be null.");
    }
    // existing logic...
}

public void Rollback() throws TransactionException {
    if (!IsTransacting()) {
        throw new TransactionException("No transaction to rollback.");
    }
    // existing logic...
}

        public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }

    public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}

public class TransactionException extends DatabaseException {
    public TransactionException(String message) {
        super(message);
    }
}

Use synchronized methods or blocks: Ensure that methods which modify shared resources (like your in-memory data structures) are synchronized.
This prevents race conditions where two threads might modify the data simultaneously, leading to inconsistent states.
java
Copy code
public synchronized void Set(String key, Integer value) {
    // implementation details
}

Map<String, Integer> map = new ConcurrentHashMap<>();

2. Atomicity and Visibility

Ensure that changes made by one thread are visible to others.
 Java’s volatile variables and atomic classes can be used for these purposes.
 */
public class Memory {

    Map<String, Integer> map; // Stores the key value pair
    Map<Integer, Integer> countMap; // Tracks how many times each value occurs

    public Memory(){
        this.map = new HashMap<>();
        this.countMap = new HashMap<>();
    }

    public Integer Get(String key){
        return map.get(key); // Retrieve the value for the key
    }

    public void Set(String key, Integer value){
        // Update or add a key-value pair
        if(map.containsKey(key)){
            // Adjust the value count for the old value
            Integer oldValue = map.get(key);
            reduceCount(oldValue);
        }

        map.put(key, value);
        countMap.put(value, countMap.getOrDefault(value, 0) + 1);

    }

    public void Delete(String key){
        if(map.containsKey(key)){
            //Decrease the count of old value and remove the key
            Integer value = map.get(key);
            reduceCount(value);
            map.remove(key);
        }
    }

    public Integer Count(Integer value) {
        // Return how many keys have this specific value
        return countMap.getOrDefault(value, 0);
    }

    private void reduceCount(Integer value) {
        //Helper method to decrease the count of a value
        int count = countMap.getOrDefault(value, 0);
        if(count == 1){
            countMap.remove(value);
        }else if(count > 1){
            countMap.put(value, count - 1);
        }
    }
}
