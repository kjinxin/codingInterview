package databricks;

import java.time.Instant;
import java.util.*;

public class KVStoreQps<K, V> {
    private class Buffer {
        public int counter;
        public int[] buffer;
        public int lastIndex;
        public long lastTime;
        public int bufferSize;
        public Buffer(int bufferSize) {
            this.counter = 0;
            this.lastIndex = 0;
            this.lastTime = getCurrentSecond();
            this.bufferSize = bufferSize;
            this.buffer = new int[bufferSize];
        }
        public void update() {
            clearBuffer();
            counter ++;
            buffer[lastIndex] ++;
        }
        public void clearBuffer() {
            long curTime = getCurrentSecond();
            int diff = (int) (curTime - lastTime);
            for (int i = lastIndex + 1; i <= lastIndex + diff; i ++) {
                counter -= buffer[i % bufferSize];
                buffer[i % bufferSize] = 0;
                if (i % bufferSize == lastIndex) {
                    break;
                }
            }
            lastIndex = (lastIndex + diff) % bufferSize;
            lastTime = curTime;
        }
        public int getQps() {
            clearBuffer();
            return counter / bufferSize;
        }
    }
    private Map<K, V> KVStore;
    private Buffer getBuffer;
    private Buffer addBuffer;
    private boolean mockTime;
    private long mockTimeSecond;

    public long getMockTimeSecond() {
        return this.mockTimeSecond;
    }

    public void setMockTimeSecond(long mockTimeSecond) {
        this.mockTimeSecond = mockTimeSecond;
    }
    public long getCurrentSecond() {
        if (this.mockTime) {
            return getMockTimeSecond();
        } else {
            long timeMilli = Instant.now().toEpochMilli();
            return timeMilli / 1000;
        }
    }
    public KVStoreQps(int bufferSize, boolean mockTime) {
        this.mockTime = mockTime;
        KVStore = new HashMap<>();
        getBuffer = new Buffer(bufferSize);
        addBuffer = new Buffer(bufferSize);
    }
    public void add(K k, V v) {
        addBuffer.update();
        KVStore.put(k, v);
    }

    public V get(K k) {
        getBuffer.update();
        if (KVStore.containsKey(k)) {
            return KVStore.get(k);
        }
        return null;
    }

    public int getQps() {
        return getBuffer.getQps();
    }

    public int addQps() {
        return addBuffer.getQps();
    }

    public static void main(String[] args) {
        KVStoreQps<Integer, Integer> kvStoreQps = new KVStoreQps<>(300, true);
        kvStoreQps.setMockTimeSecond(0L);
        for (int i = 0; i < 600; i ++) {
            kvStoreQps.add(1, 1);
        }
        System.out.println(kvStoreQps.addQps());

        kvStoreQps.setMockTimeSecond(300L);

        System.out.println(kvStoreQps.addQps());
        for (int i = 0; i < 600; i ++) {
            kvStoreQps.add(1, 1);
        }
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(450L);
        for (int i = 0; i < 600; i ++) {
            kvStoreQps.add(1, 1);
        }
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(599L);
        for (int i = 0; i < 600; i ++) {
            kvStoreQps.add(1, 1);
        }
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(600L);
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(749L);
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(750L);
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(898L);
        System.out.println(kvStoreQps.addQps());
        kvStoreQps.setMockTimeSecond(899L);
        System.out.println(kvStoreQps.addQps());
    }
}
