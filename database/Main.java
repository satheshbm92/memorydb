package comp.Box.database;

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
        db.Commit();
        System.out.println("Get a after commit: " + db.Get("a"));  // Output: 30

        db.BeginTransaction();
        db.Set("b", 40);
        System.out.println("Get b in transaction: " + db.Get("b"));  // Output: 40
        db.Rollback();
        System.out.println("Get b after rollback: " + db.Get("b"));  // Output: NULL

        System.out.println("NUMEQUALTO 10: " + db.Count(10));  // Output: 1
        System.out.println("NUMEQUALTO 30: " + db.Count(30));  // Output: 1
        System.out.println("NUMEQUALTO 40: " + db.Count(40));  // Output: 0
    }
}
