package comp.Box.database;

import java.util.LinkedList;
import java.util.Map;

/*
MemoryStore Class: "This is the main interface of our database system. It manages transactions, directing operations either to the most recent
transaction's memory or to the main memory depending on whether a transaction is active. It supports starting new transactions,
rolling back the most recent transaction, and committing all open transactions to the main memory.
 It's essentially the control center that integrates the transactional functionalities with the basic data operations provided by the Memory class."


 "The MemoryStore acts as the orchestrator. It decides where operations should be directed based on the transactional context.
 This design pattern allows us to extend the system easily, for example, by adding new types of transactions or supporting more
 complex querying operations without altering the underlying data handling logic."
 */
public class MemoryStore {
    Memory memory; // The main memory of the database
    LinkedList<Transaction> transactionList; // Tracks ongoing transaction

    public MemoryStore(){
        this.memory = new Memory();
        this.transactionList = new LinkedList<>();
    }

    public Integer Get(String key) {
        // Check if there's an active transaction and get from the latest one
        return this.IsTransacting() ? transactionList.getLast().Get(key) : memory.Get(key);
    }

    public void Set(String key, Integer value) {
        if (IsTransacting()) {
            transactionList.getLast().Set(key, value);
        } else {
            memory.Set(key, value);
        }
    }

    public void Delete(String key) {
        if (IsTransacting()) {
            transactionList.getLast().Delete(key);
        } else {
            memory.Delete(key);
        }
    }

    public Integer Count(Integer value) {
        return IsTransacting() ? transactionList.getLast().Count(value) : memory.Count(value);
    }

    public boolean IsTransacting() {
        return !transactionList.isEmpty();
    }

    public void BeginTransaction() {
        transactionList.add(new Transaction());
    }

    public void Commit() {
        if (!IsTransacting()) {
            System.out.println("NO TRANSACTION");
        } else {
            // Process each transaction from the oldest to the newest
            while (!transactionList.isEmpty()) {
                Transaction t = transactionList.removeFirst();

                // Apply each set operation in the transaction
                for (Map.Entry<String, Integer> entry : t.memory.map.entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    if (t.deletedSet.contains(key)) {
                        memory.Delete(key);  // If key was marked as deleted, delete it in main memory
                    } else {
                        memory.Set(key, value);  // Otherwise, set/update the key in the main memory
                    }
                }

                // Apply deletes for keys that were not set again after deletion
                for (String key : t.deletedSet) {
                    if (!t.memory.map.containsKey(key)) {
                        memory.Delete(key);
                    }
                }
            }
        }
    }

    public void Rollback() {
        if (!IsTransacting()) {
            System.out.println("NO TRANSACTION");
        } else {
            transactionList.removeLast();
        }
    }

}
