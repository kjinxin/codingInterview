package doordash;

/*
optimal routes, best driver to choose routes
each driver has a maxium capacity

Example1:
Input:trips=[[2,1,5],[3,2,7]],capacity=4
Output:false

Example2:
Input:trips=[[2,1,3],[3,5,7]],capacity=4
Output:true

Example3:
Input:trips=[[2,1,5],[3,5,7]],capacity=3
Output:true

Example4:
Input:trips=[[3,2,7],[3,7,9],[8,3,9]],capacity=11
Output:true

Example5:
Input:trips=[[2,1,7],[6,2,3],[3,3,5]],capacity=8
Output:true

Example6:
Input:trips=[[2,1,7],[6,1,3],[3,3,5]],capacity=11
Output:true

Example7:
Input:trips=[[2,1,7],[6,1,5],[3,3,5]],capacity=10
Output:false

Example8:
Input:trips=[[2,1,7],[6,2,4],[3,4,5]],capacity=8
Output:true

Example9:
Input:trips=[[3,4,5],[2,1,7],[6,2,4]],capacity=8
Output:true

Example10:
Input:trips=[[2,1,7],[6,2,4],[3,4,5]],capacity=6
Output:false

first: capacity
second: start loc
third: end loc

how many locations: infinite location
trips: 100

1. sort the trip by start location
2. scan the trips
3. for each trip,
*/
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    class Trip {
        public int start, end, cap;
        public Trip(int start, int end, int cap) {
            this.start = start;
            this.end = end;
            this.cap = cap;
        }
    }
    static int addNumbers(int a, int b) {
        return a+b;
    }

    public boolean canDriverFinish(int[][] trips, int capacity) {
        List<Trip> tripList = new ArrayList<Trip>();
        PriorityQueue<Trip> pq = new PriorityQueue<Trip>((a, b) -> Integer.compare(a.end, b.end));
        for (int[] trip : trips) {
            tripList.add(new Trip(trip[1], trip[2], trip[0]));
        }
        // sort tripList by start
        tripList.sort((a, b) -> Integer.compare(a.start, b.start));
        int curCapacity = capacity;
        for (int i = 0; i < tripList.size(); i ++) {
            Trip trip = tripList.get(i);
            int curStart = trip.start;
            int curEnd = trip.end;
            // try to drop packages
            while(!pq.isEmpty() && pq.peek().end <= curStart) {
                curCapacity += pq.poll().cap;
            }
            if (curCapacity < trip.cap) {
                return false;
            } else {
                curCapacity -= trip.cap;
                pq.add(trip);
            }
        }
        return true;
    }

    public int minumCapacityRequired(int[][] trips) {
        List<Trip> tripList = new ArrayList<Trip>();
        PriorityQueue<Trip> pq = new PriorityQueue<Trip>((a, b) -> Integer.compare(a.end, b.end));
        for (int[] trip : trips) {
            tripList.add(new Trip(trip[1], trip[2], trip[0]));
        }
        // sort tripList by start
        tripList.sort((a, b) -> Integer.compare(a.start, b.start));
        int curCapacity = 0;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < tripList.size(); i ++) {
            Trip trip = tripList.get(i);
            int curStart = trip.start;
            int curEnd = trip.end;
            // try to drop packages
            while(!pq.isEmpty() && pq.peek().end <= curStart) {
                curCapacity -= pq.poll().cap;
            }

            curCapacity += trip.cap;
            res = Math.max(res, curCapacity);
            pq.add(trip);

        }
        return res;
    }

    public static void main(String[] args) {
        Solution so = new Solution();
        /*
        Example1:
Input:trips=[[2,1,5],[3,2,7]],capacity=4
Output:false

Example2:
Input:trips=[[2,1,3],[3,5,7]],capacity=4
        Example3:
Input:trips=[[2,1,5],[3,5,7]],capacity=3
Output:true

Example4:
Input:trips=[[3,2,7],[3,7,9],[8,3,9]],capacity=11
Output:true

Example5:
Input:trips=[[2,1,7],[6,2,3],[3,3,5]],capacity=8
Output:true

Example6:
Input:trips=[[2,1,7],[6,1,3],[3,3,5]],capacity=11
Output:true

Example7:
Input:trips=[[2,1,7],[6,1,5],[3,3,5]],capacity=10
Output:false

Example8:
Input:trips=[[2,1,7],[6,2,4],[3,4,5]],capacity=8
Output:true

Example9:
Input:trips=[[3,4,5],[2,1,7],[6,2,4]],capacity=8
Output:true

Example10:
Input:trips=[[2,1,7],[6,2,4],[3,4,5]],capacity=6
Output:false
*/
        System.out.println(so.canDriverFinish(new int[][]{{2,1,5},{3,2,7}}, 4) == false);
        System.out.println(so.canDriverFinish(new int[][]{{2,1,3},{3,5,7}}, 4) == true);
        System.out.println(so.canDriverFinish(new int[][]{{2,1,5},{3,5,7}}, 3) == true);
        System.out.println(so.canDriverFinish(new int[][]{{3,2,7},{3,7,9}, {8,3,9}}, 11) == true);
        System.out.println(so.canDriverFinish(new int[][]{{2,1,7},{6, 2, 3}, {3, 3, 5}}, 8) == true);
        // example 6
        System.out.println(so.canDriverFinish(new int[][]{{2,1,7},{6,1,3}, {3,3,5}}, 11) == true);
        System.out.println(so.canDriverFinish(new int[][]{{2,1,7},{6,1,5}, {3,3,5}}, 10) == false);
        // example 8
        System.out.println(so.canDriverFinish(new int[][]{{2,1,7},{6,2,4}, {3,4,5}}, 8) == true);
        System.out.println(so.canDriverFinish(new int[][]{{3,4,5},{2,1,7}, {6,2,4}}, 8) == true);
        System.out.println(so.canDriverFinish(new int[][]{{2,1,7},{6,2,4}, {3,4,5}}, 6) == false);


        // new test cases for follow up
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,5},{3,2,7}}));
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,3},{3,5,7}}) );
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,5},{3,5,7}}));
        System.out.println(so.minumCapacityRequired(new int[][]{{3,2,7},{3,7,9}, {8,3,9}}));
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,7},{6, 2, 3}, {3, 3, 5}}));
        // example 6
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,7},{6,1,3}, {3,3,5}}));
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,7},{6,1,5}, {3,3,5}}));
        // example 8
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,7},{6,2,4}, {3,4,5}}));
        System.out.println(so.minumCapacityRequired(new int[][]{{3,4,5},{2,1,7}, {6,2,4}}));
        System.out.println(so.minumCapacityRequired(new int[][]{{2,1,7},{6,2,4}, {3,4,5}}));
    }
}

