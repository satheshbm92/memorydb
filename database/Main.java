package comp.Box.database;


/*

Efficiency: "I chose HashMap for storing key-value pairs because it offers average constant-time complexity for put, get, and remove operations.
 This is essential for an in-memory database where quick data retrieval and updates are critical for performance."
Use Case Alignment: "The nature of operations supported by our database, such as SET, GET, and UNSET, aligns well with the capabilities of a HashMap.
It inherently supports key-based lookup, which is exactly what we need for these operations."
 */
public class Main {
    public static void main(String[] args) {
        MemoryStore db = new MemoryStore();

        db.Set("a", 10);
        System.out.println("Get a: " + db.Get("a"));  // Output: 10

        db.BeginTransaction();
        db.Set("a", 20);
        System.out.println("Get a in transaction: " + db.Get("a"));  // Output: 20

        db.Rollback();
        System.out.println("Get a after rollback: " + db.Get("a"));  // Output: 10

        db.BeginTransaction();
        db.Set("a", 30);
        db.Set("c", 50);
        db.Commit();
        System.out.println("Get a after commit: " + db.Get("a"));  // Output: 30

        db.BeginTransaction();
        db.Set("b", 40);
        System.out.println("Get b in transaction: " + db.Get("b"));  // Output: 40
        db.BeginTransaction();
        db.Set("c", 50);
        db.Delete("c");
        db.Commit();

//        db.Rollback();
        System.out.println("Get b after rollback: " + db.Get("b"));  // Output: NULL

        System.out.println("NUMEQUALTO 10: " + db.Count(10));  // Output: 1
        System.out.println("NUMEQUALTO 30: " + db.Count(30));  // Output: 1
        System.out.println("NUMEQUALTO 40: " + db.Count(40));  // Output: 0
    }
}
