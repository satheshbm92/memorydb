package comp.Box.database;

import java.util.HashSet;
import java.util.Set;

/*
Set, Get, Delete, Count: These operations are delegated to the Memory instance specific to the transaction.
 */
public class Transaction {

    Memory memory; // Each transaction has its own memory instance
    Set<String> deletedSet;  // Tracks deleted keys within this transaction

    public Transaction(){
        this.memory = new Memory();
        this.deletedSet = new HashSet<>();
    }

    public boolean isDeleted(String key){
        return deletedSet.contains(key);
    }

    public Integer Get(String key){
        // Forward the get request to the transaction's memory
        return memory.Get(key);

    }

    public void Set(String key, Integer value){
        //Set a value within the transaction's memory
        memory.Set(key, value);
    }

    public void Delete(String key){
        // Mark the key as deleted and remove from the transaction's memory
        deletedSet.add(key);
        memory.Delete(key);
    }

    public Integer Count(Integer value){
        // Get the count of keys with the specified value within this transaction
        return memory.Count(value);
    }
}
