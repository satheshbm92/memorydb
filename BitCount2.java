package comp.Box;

/*
n = 5 , >>>
Iteration 1:
count = 0 + (5 & 1) = 0 + 1 = 1
n = 5 >>> 1 = 2 (Binary: 00000000000000000000000000000010)

Iteration 2:
count = 1 + (2 & 1) = 1 + 0 = 1
n = 2 >>> 1 = 1 (Binary: 00000000000000000000000000000001)

Iteration 3:
count = 1 + (1 & 1) = 1 + 1 = 2
n = 1 >>> 1 = 0 (Binary: 00000000000000000000000000000000)

Loop terminates as n becomes 0.


n = -5
Iteration 1:
count = 0 + (-5 & 1) = 0 + 1 = 1
n = -5 >>> 1 = 2147483645 (Binary: 01111111111111111111111111111101)

Iteration 2:
count = 1 + (2147483645 & 1) = 1 + 1 = 2
n = 2147483645 >>> 1 = 1073741822 (Binary: 00111111111111111111111111111110)

... (30 more iterations) ...

Iteration 32:
count = 31 + (1 & 1) = 31 + 1 = 32
n = 1 >>> 1 = 0 (Binary: 00000000000000000000000000000000)

Loop terminates as n becomes 0.

n = -5 >>
Iteration 1:
count = 0 + (-5 & 1) = 0 + 1 = 1
n = -5 >> 1 = -3 (Binary: 11111111111111111111111111111101)

Iteration 2:
count = 1 + (-3 & 1) = 1 + 1 = 2
n = -3 >> 1 = -2 (Binary: 11111111111111111111111111111110)

Iteration 3:
count = 2 + (-2 & 1) = 2 + 0 = 2
n = -2 >> 1 = -1 (Binary: 11111111111111111111111111111111)

Iteration 4:
count = 2 + (-1 & 1) = 2 + 1 = 3
n = -1 >> 1 = -1 (Binary: 11111111111111111111111111111111)

The loop continues infinitely because -1 >> 1 always results in -1 due to the sign bit being preserved.

 */
public class BitCount2 {

    public int hammingWeight(int n) {
        int count = 0;

        // Continue the loop until all bits are shifted out and the number becomes zero.
        while(n !=0){

            // Increment the count if the least significant bit (LSB) is 1.
            // (n & 1) will be 1 if the LSB of n is 1; otherwise, it will be 0
            count = count + (n & 1);

            // Unsigned right shift the bits of n by 1.
            // This will shift n's bits to the right and fill with zeros on the left regardless of the sign.
            // It ensures that the loop eventually terminates by making n zero after all bits are processed.
            n = n >>> 1;
        }
        return count;
    }
}
