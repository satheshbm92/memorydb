package comp.Box.database;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemoryStoreTest {
    private MemoryStore db;

    @Before
    public void setUp() {
        db = new MemoryStore();
    }

    @Test
    public void testSetAndGet() {
        db.Set("name", 1);
        assertEquals("Should retrieve the value set for 'name'.", Integer.valueOf(1), db.Get("name"));
    }

    @Test
    public void testUnsetAndGet() {
        db.Set("name", 2);
        db.Delete("name");
        assertNull("Should return null for unset key.", db.Get("name"));
    }

    @Test
    public void testNumEqualTo() {
        db.Set("name1", 3);
        db.Set("name2", 3);
        assertEquals("Should count two keys with value 3.", Integer.valueOf(2), db.Count(3));
        assertEquals("Should count zero keys with value 99.", Integer.valueOf(0), db.Count(99));
    }

    @Test
    public void testBeginAndRollback() {
        db.Set("name", 4);
        db.BeginTransaction();
        db.Set("name", 5);
        assertEquals("Should get the updated value within the transaction.", Integer.valueOf(5), db.Get("name"));
        db.Rollback();
        assertEquals("Should revert to value before transaction.", Integer.valueOf(4), db.Get("name"));
    }

    @Test
    public void testBeginAndCommit() {
        db.Set("name", 4);
        db.BeginTransaction();
        db.Set("name", 5);
        db.Commit();
        assertEquals("Should keep the updated value after commit.", Integer.valueOf(5), db.Get("name"));
    }

    @Test
    public void testNestedTransactions() {
        db.Set("name", 4);
        db.BeginTransaction();
        db.Set("name", 5);
        db.BeginTransaction();
        db.Set("name", 6);
        db.Rollback();
        assertEquals("Should rollback to the previous transaction value.", Integer.valueOf(5), db.Get("name"));
        db.Commit();
        assertEquals("Should keep the remaining transaction value.", Integer.valueOf(5), db.Get("name"));
    }

//    @Test
//    public void testRollbackNoTransaction() {
//        db.Rollback();
//        assertEquals("Should print NO TRANSACTION when there's no transaction to rollback.", "NO TRANSACTION", outContent.toString().trim());
//    }
//
//    @Test
//    public void testCommitNoTransaction() {
//        db.Commit();
//        assertEquals("Should print NO TRANSACTION when there's no transaction to commit.", "NO TRANSACTION", outContent.toString().trim());
//    }

    // This test depends on having a way to capture standard output, such as by redirecting System.out before tests.
}
