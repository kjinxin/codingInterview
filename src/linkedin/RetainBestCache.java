package linkedin;

import java.util.*;
public class RetainBestCache<K, T extends RetainBestCache.Rankable> {
/* Constructor with a data source (assumed to be slow) and a cache size */
    private DataSource<K, T> ds;
    private int capacity;
    private TreeMap<Long, Queue<K>> rankMap;
    private Map<K, T> cache;
    public RetainBestCache(DataSource<K, T> ds, int entriesToRetain) {
        this.ds = ds;
        this.capacity = entriesToRetain;
        this.rankMap = new TreeMap();
        this.cache = new HashMap();
    }
/* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
* retrieves it from the data source. If the cache is full, attempt to cache the returned data,
* evicting the T with lowest rank among the ones that it has available
* If there is a tie, the cache may choose any T with lowest rank to evict.
*/
    public T get(K key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            T value = ds.get(key);
            cache.put(key, value);
            if (!rankMap.containsKey(value.getRank())) {
                rankMap.put(value.getRank(), new ArrayDeque());
            }
            rankMap.get(value.getRank()).add(key);
            if (cache.size() == this.capacity) {
                Long lowestRank = rankMap.firstKey();
                Queue<K> tmp = rankMap.get(lowestRank);
                K removeKey = tmp.poll();
                cache.remove(removeKey);
                if (tmp.isEmpty()) {
                    rankMap.remove(lowestRank);
                }
            }
            return value;
        }
 }
 public interface Rankable {
    long getRank();
     }
     public interface DataSource<K, T extends Rankable> {
    T get(K key);
    }
}