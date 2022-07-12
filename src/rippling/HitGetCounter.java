package rippling;

/*
#
# Metrics Counter
#
# Let's say you're building an API (using the language of your choice).
#  We want to have a local counter to help keep track of what's happening
#  on that server. Specifically we want keep track of the number of API
#  requests over the past five minutes, tracked on a per endpoint basis.
#
#  Note this is something we want to be able to put in production, so we want
#  to write testable code to verify functionality of it.
#
# Example:
#
#   "/some_endpoint/foo" =>  100 requests in the past 5 minutes
#
#   "/some_endpoint/bar" => 1000 requests in the past 5 minutes
#

hit('/bar', 1)
hit('/bar', 2)
hit('/bar/bar2', 3)

/bar
/bar/bar2
/bar/bar2/bar3

get('/bar', 300) -> 3




hit('/bar', 299)
get('/bar', 300) -> 2

get(endpoint, 400) =>

*/

import java.util.*;

public class HitGetCounter {

    private class Buffer {
        public int counter;
        public int[] buffer;
        public int lastTime;
        public int lastIndex;
        public int bufferSize;
        public Buffer(int size) {
            this.counter = 0;
            this.bufferSize = size;
            this.lastTime = 0;
            this.lastIndex = 0;
            this.buffer = new int[size];
        }

        public void hit(int currentTime) {
            // move the buffer ring
            // lastTime to currentTime
            // clean the time older than 5 mins
            moveBuffer(currentTime);
            if (currentTime >= lastTime) {
                counter ++;
                buffer[lastIndex] ++;
            }
        }

        public void moveBuffer(int currentTime) {
            if (currentTime < lastTime) {
                // no need to update the lastTime and lastIndex
                System.out.printf("moveBuffer Special %d %n", currentTime);
                int diff = lastTime - currentTime;
                if (diff < 300) {
                    // no need to handle
                    counter ++;
                    int prevIdx = (lastIndex - diff + bufferSize) % bufferSize;
                    buffer[prevIdx] ++;
                }
                return;
            }
            int diff = currentTime - lastTime;
            for (int i = lastIndex + 1; i <= lastIndex + diff; i ++) {
                counter -= buffer[i % bufferSize];
                buffer[i % bufferSize] = 0;
                // gurantee we clean the buffer at most once
                if (i % bufferSize == lastIndex) {
                    break;
                }
            }
            lastIndex = (lastIndex + diff) % bufferSize;
            lastTime = currentTime;
        }

        public int getCounter(int currentTime) {
            moveBuffer(currentTime);
            return counter;
        }
    }

    private Map<String, Buffer> bufferMap = new HashMap<String, Buffer>();


    public void hit(String endpoint, int currentTime) {
        String[] subEndPoints = parse(endpoint);
        for (String name : subEndPoints) {
            hitChild(name, currentTime);
        }
    }
    private String[] parse(String endpoint) {
        return endpoint.split("/");
    }
    public void hitChild(String endpoint, int currentTime) {
        if (!bufferMap.containsKey(endpoint)) {
            bufferMap.put(endpoint, new Buffer(300));
        }
        Buffer buffer = bufferMap.get(endpoint);
        buffer.hit(currentTime);
    }

    public int get(String endpoint, int currentTime) {
        if (!bufferMap.containsKey(endpoint)) {
            bufferMap.put(endpoint, new Buffer(300));
        }
        Buffer buffer = bufferMap.get(endpoint);
        return buffer.getCounter(currentTime);
    }

    public static void main(String[] args) {
        HitGetCounter so = new HitGetCounter();
        so.hit("/bar", 1);
        so.hit("/bar", 2);
        so.hit("/bar", 1);
        so.hit("/bar", 200);
        System.out.println(so.get("/bar", 200));
    }
}
