package comp.Box.database;

import java.util.HashMap;
import java.util.Map;

/*
Set: If a key already exists, the count of the old value is decreased before updating the key to the new value.
Delete: Decreases the count of the value associated with the key and removes the key from the map.
Count: Returns the number of keys that have the specified value.
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
