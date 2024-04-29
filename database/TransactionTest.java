package comp.Box.database;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction();
    }

    @Test
    public void testTransactionSetAndGet() {
        transaction.Set("transKey", 123);
        assertEquals("Should retrieve the value set in transaction", Integer.valueOf(123), transaction.Get("transKey"));
    }

    @Test
    public void testTransactionDelete() {
        transaction.Set("transKey", 456);
        transaction.Delete("transKey");
        assertNull("Value should be null after delete in transaction", transaction.Get("transKey"));
    }

    @Test
    public void testTransactionCount() {
        transaction.Set("transKey1", 789);
        transaction.Set("transKey2", 789);
        assertEquals("Should count two transactions with the same value", Integer.valueOf(2), transaction.Count(789));
        transaction.Delete("transKey1");
        assertEquals("Should count one transaction after delete", Integer.valueOf(1), transaction.Count(789));
    }

    @Test
    public void testIsDeleted() {
        transaction.Set("transKey", 101112);
        transaction.Delete("transKey");
        assertTrue("Should confirm the key is marked as deleted", transaction.isDeleted("transKey"));
    }
}
