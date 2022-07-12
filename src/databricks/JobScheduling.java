package databricks;
import java.util.*;
class Job {
    public int startTime, endTime, profit;
    public Job(int startTime, int endTime, int profit) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.profit = profit;
    }
}
class JobScheduling {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        // initialize jobs
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < startTime.length; i ++) {
            jobs.add(new Job(startTime[i], endTime[i], profit[i]));
        }

        // sort by startTime asc order
        jobs.sort((a, b) -> Integer.compare(a.startTime, b.startTime));

        return findMaxProfit(jobs);
    }

    private int findNextJob(int endTime, List<Job> jobs) {
        int l = 0, r = jobs.size();
        while(l < r) {
            int mid = (l + r) / 2;
            if (jobs.get(mid).startTime >= endTime) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
    private int findMaxProfit(List<Job> jobs) {
        // assume there is a dummy job with startTime = Integer.MAX_VALUE
        int[] dp = new int[jobs.size() + 1];
        // dp[i] = max{dp[i + 1], dp[k] + profit[i]} k is the first index that does not overlap with i

        for (int i = jobs.size() - 1; i >= 0; i --) {
            int nextIdx = findNextJob(jobs.get(i).endTime, jobs);
            dp[i] = Math.max(dp[i + 1], dp[nextIdx] + jobs.get(i).profit);
        }


        return dp[0];
    }
}
