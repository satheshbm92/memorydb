package comp.Box.database;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemoryTest {
    private Memory memory;

    @Before
    public void setUp() {
        memory = new Memory();
    }

    @Test
    public void testSetAndGet() {
        memory.Set("key", 10);
        assertNotNull("Value should be set and retrievable", memory.Get("key"));
        assertEquals("Value retrieved should match value set", Integer.valueOf(10), memory.Get("key"));
    }

    @Test
    public void testUnset() {
        memory.Set("key", 20);
        memory.Delete("key");
        assertNull("Value should be null after being unset", memory.Get("key"));
    }

    @Test
    public void testNumEqualTo() {
        memory.Set("key1", 30);
        memory.Set("key2", 30);
        memory.Set("key3", 40);
        assertEquals("Should count two keys with value 30.", Integer.valueOf(2), memory.Count(30));
        assertEquals("Should count one key with value 40.", Integer.valueOf(1), memory.Count(40));
        assertEquals("Should count zero keys with value 50.", Integer.valueOf(0), memory.Count(50));
    }

    @Test
    public void testUpdateValue() {
        memory.Set("key", 100);
        memory.Set("key", 200);
        assertEquals("Value should be updated to new value", Integer.valueOf(200), memory.Get("key"));
        assertEquals("Old value count should be 0", Integer.valueOf(0), memory.Count(100));
        assertEquals("New value count should be 1", Integer.valueOf(1), memory.Count(200));
    }
}
