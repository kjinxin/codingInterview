package databricks;
import java.util.*;
public class IPCIDR {
    public List<String> ipToCIDR(String ip, int n) {
        final List<String> answer = new ArrayList<>();
        long x = 0;

        // Step 1: Split the IP into parts by splitting at "."
        // Convert each part to the base of 256
        // Why 256? x.x.x.x = each x in the ip address
        // can be represented from 0 to 255 (2^8)
        // 0 is reserved to represent a subnet
        // 255 is reserved too
        // so technical range is from 1 to 254, just FYI
        final String[] parts = ip.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            x = x * 256 + Long.parseLong(parts[i]);
        }

        // Step 2: Generate IP addresses
        while (n > 0) {
            // Step 2a: Calculate count
            // 7 in binary: 00000111
            // 1s complement of 7: 1111000
            // 2s complement of 7: 1111001
            // 7 & (-7) = both bits should be 1
            // This helps us find how many ips can be created from them
            // 7 & (-7), AND both bits, so if both bits are 1, then the
            // resulting bit will be 1, else 0
            // So result of 7 & (-7): 00000001, only 1 IP can be derived from this
            long count = x & (-x);

            // To handle 0.0.0.0
            if (count == 0) {
                count = maxLowNum(n);
            }

            // Check if count is greater than n
            // We check this to ensure we generate only enough ips as mentioned in the problem
            while (count > n) {
                count /= 2;
            }
            answer.add(processOneCIDR(x, count));

            x = x + (int) count;
            // Reduce the number of IPs we've discovered in the CIDR block
            n = n - (int) count;
        }
        return answer;
    }

    private String processOneCIDR(long x, long count) {
        int d, c, b, a;
        // Step 1: Process the last part of the IP address
        // Keep discaarding it
        // Need to do this 4 times as the ip address is in 4 parts
        // Remember, we are processing from Right to Left
        d = (int) x & 255; // Process right side
        x >>= 8; // Discard the right side
        c = (int) x & 255; // Process right side
        x >>= 8; // Discard the right side
        b = (int) x & 255; // Process right side
        x >>= 8; // Discard the right side
        a = (int) x & 255; // Process right side
        x >>= 8; // Discard the right side

        int len = 0;
        // Step 2: While is to calculate how many counts are left
        // for example, 00001000 here the len will be 4.
        while(count > 0) {
            count /= 2;
            len++;
        }

        int mask = 32 - (len - 1);
        // 1. Think about 255.0.0.7 -> 11111111 00000000 00000000 00000111
        // The trailing numbers (00000111) do not have any zeroes
        // so 32 - (1 - 2^0) (no trailing zereos) = 32, 1 IP created with a mask of 32
        //
        // 2. Now let's look at the next IP, line number 43
        // 255.0.0.7 -> 11111111 00000000 00000000 00000111 (previous input from 1.)
        // 255.0.0.7 + 2^0 = 255.0.0.7 + 1 = 255.0.0.8 -> 11111111 00000000 00000000 00001000
        // Now you see there are three trailing zeroes (00001000), this means there are 2^3 = 8 possibilities
        // so 32 - (1 - 2^3) = 32 - (1 - 8) = 25, 8 IP created with a mask of 25
        // Only 1 more IP to figure out now
        //
        // 3. 255.0.0.8 + 2^3 = 255.0.0.8 + 8 = 255.0.0.16 -> 11111111 00000000 00000000 00010000
        // This has 4 trailing zeroes now, so 2^4=16 possibilities, but we only want 1, since that's how many IPs
        // are remaining to be figured.
        // This time the count = 1, in the while the length only increments by 1
        // 32 - (1 - 1) = 32, which is eventually the mask we want because we only need 1 IP.
        // Note: Try to remember, Bigger the mask, smaller the IP list

        // Just join the a.b.c.d/mask
        return new StringBuilder()
                .append(a)
                .append(".")
                .append(b)
                .append(".")
                .append(c)
                .append(".")
                .append(d)
                .append("/")
                .append(mask)
                .toString();
    }

    private int maxLowNum(int x) {
        int len = 0;
        while (x > 1) {
            x >>= 1;
            len++;
        }
        return 1 << len;
    }
}
