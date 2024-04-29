package comp.Box.database;

import java.util.HashSet;
import java.util.Set;

/*
Set, Get, Delete, Count: These operations are delegated to the Memory instance specific to the transaction.

Transaction Class: "Each Transaction object represents a single transaction block. It contains its own Memory instance,
allowing it to keep changes isolated from the main memory and other transactions until those changes are committed.
This isolation is key to supporting transactional integrity and rollback capabilities.
 The class also tracks which keys have been deleted during the transaction, enabling accurate state reversal on rollbacks


  Each Transaction having its own Memory instance supports the ACID properties expected in transactional systems, particularly Atomicity and Isolation.
  Changes made in a transaction do not affect the global state until they are committed, preventing inconsistent states during transaction execution."


  Why a DeletedSet: "In each transaction, tracking which keys have been deleted is crucial for properly handling rollbacks and commits.
  The deletedSet in each transaction helps to quickly determine which keys have been affected during the transaction without scanning the entire data set."

  Rollbacks: "On a rollback, we can efficiently restore the original state by reverting changes for only those keys recorded in the deletedSet.
  This is more efficient than tracking all possible changes because deletions are typically fewer than the total number of operations."


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
