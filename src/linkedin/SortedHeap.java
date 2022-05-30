package linkedin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class SortedHeap {
    public static void main(String[] args) {
        Map<Integer, Integer> mp = new HashMap();
        mp.put(1, 10);
        mp.put(2, 5);
        mp.put(3, 6);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(mp::get));

        pq.add(1);
        pq.add(2);
        pq.add(3);
        System.out.println(pq.peek());
        mp.put(1, 3);
        System.out.println(pq.poll());
        System.out.println(pq.peek());
    }
}
