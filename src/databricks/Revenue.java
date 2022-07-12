package databricks;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Revenue {
    private int id = 0;
    private Map<Integer, Integer> userRevenue;
    private TreeMap<Integer, Set<Integer>> rankedRevenue;
    public Revenue() {
        userRevenue = new HashMap();
        rankedRevenue = new TreeMap<>();
    }
    public int insert(int revenue) {
        int nId = id ++;
        userRevenue.put(nId, revenue);
        if (!rankedRevenue.containsKey(revenue)) {
            rankedRevenue.put(revenue, new HashSet<>());
        }
        rankedRevenue.get(revenue).add(nId);
        return nId;
    }

    public int insert(int revenue, int referId) {
        int nId = insert(revenue);
        int prevRevenue = userRevenue.get(referId);
        int newRevenue = prevRevenue + revenue;
        userRevenue.put(referId, newRevenue);
        rankedRevenue.get(prevRevenue).remove(referId);
        if (rankedRevenue.get(prevRevenue).isEmpty()) {
            rankedRevenue.remove(prevRevenue);
        }
        if (!rankedRevenue.containsKey(newRevenue)) {
            rankedRevenue.put(newRevenue, new HashSet<>());
        }
        rankedRevenue.get(newRevenue).add(referId);
        return nId;
    }

    public int[] getKCustomerRevenueAboveThreshold(int k, int threshold) {
        List<Integer> resList = new ArrayList<>();
        for (int i = 0; i < k; i ++) {
            //higherEntry(threshold)
            Map.Entry<Integer, Set<Integer>> entry = rankedRevenue.ceilingEntry(threshold);
            resList.addAll(entry.getValue());
            threshold = entry.getKey() + 1;
        }

        int[] res = new int[resList.size()];
        for(int i = 0; i < resList.size(); i ++) {
            res[i] = resList.get(i);
        }
        return res;
    }
    private void print(int[] res) {
        for (int i : res) {
            System.out.printf("%d ", i);
        }
        System.out.println();
    }
    public static void main(String[] args) {

        Revenue revenue = new Revenue();
        revenue.insert(10);
        revenue.insert(20, 0);
        revenue.insert(50, 1);
        revenue.insert(15);
        revenue.insert(16, 3);
        revenue.print(revenue.getKCustomerRevenueAboveThreshold(1, 15));
        revenue.print(revenue.getKCustomerRevenueAboveThreshold(2, 15));
    }
}
