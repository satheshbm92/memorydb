package comp.Box;

import java.util.concurrent.atomic.AtomicInteger;


/*
So what are atomic operations?
Atomic operations refer to reading variable values, modifying variable values, and saving variable values ​​as a whole operation.
That is, these actions are either completed at the same time or neither. The util.concurrent.atomic package

Count incrementing is not atomic, Read the value, Increment the value write the value

of Java provides a tool class for creating atomic type variables ,
which can simplify thread synchronization.

The AtomicInteger table can update the value of int atomically and can be used in applications (such as atomically incremented counters),
but cannot be used to replace Integer; it can extend Number, allowing tools and utilities that deal with opportunistic number classes to perform Unified access.

Common methods of the AtomicInteger class:
AtomicInteger(int initialValue): Create a new AtomicInteger with a given initial value
addAddGet(int dalta): Add the given value to the current value atomically
get(): Get the current value

Code example:
only Change the Bank class and the rest of the code is the same as the first example above.

Copy code
1  class Bank {
 2          private AtomicInteger account = new AtomicInteger(100 );
 3
4          public AtomicInteger getAccount() {
 5              return account;
 6          }
 7
8          public  void save( int money) {
 9              account.addAndGet(money);
 10          }
 11      }
Copy code
 */
public class Counter3 {

    // An atomic integer field
    private AtomicInteger atomicCount = new AtomicInteger(0);

    // Increments the count atomically and returns the new value
    public int incrementAndGet() {
        return atomicCount.incrementAndGet();
    }

    // Gets the current value
    public int getCount() {
        return atomicCount.get();
    }

    public static void main(String[] args) {
        Counter3 counter = new Counter3();

        // Simulate 5 threads each incrementing the counter
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("Incremented count: " + counter.incrementAndGet());
            }).start();
        }
    }
}
