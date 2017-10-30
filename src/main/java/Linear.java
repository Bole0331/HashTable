import java.util.*;

public class Linear extends HashTable {

    private int batchNum;

    private Set<String> special;

    public Linear(int bucketSize, int bucketNum, int batchNum) {
        this.bucketSize = bucketSize;
        this.bucketNum = bucketNum;
        this.batchNum = batchNum;
        currentSize = 0;
        tableSize = (long)bucketSize * (long)bucketNum;
        buckets = new ArrayList<>(bucketNum);
        for ( int i = 0; i < bucketNum; i++ ) {
            Set<String> set = new HashSet<>();
            buckets.add(set);
        }
        special = new HashSet<>();
    }

    public int add(String key) {
        int hash = Math.abs(key.hashCode()) % bucketNum;
        for ( int i = hash; i < Math.min(hash+batchNum,bucketNum); i++ ) {
            if ( buckets.get(i).size() < bucketSize) {
                currentSize += 1;
                buckets.get(i).add(key);
                return 1;
            }
        }
        int slot = -1;
        for ( int i = Math.min(hash+batchNum,bucketNum); i < Math.min(hash+batchNum*2,bucketNum); i++ ) {
            if ( buckets.get(i).size() < bucketSize ) {
                slot = i;
                break;
            }
        }
        if ( slot != -1 ) {
            for ( int i = hash + batchNum - 1; i >= hash; i-- ) {
                Set<String> set = buckets.get(i);
                for ( String ele : set ) {
                    int tmp_hash = Math.abs(ele.hashCode()) % bucketNum;
                    if ( slot - tmp_hash < batchNum ) {
                        currentSize += 1;
                        buckets.get(i).remove(ele);
                        buckets.get(slot).add(ele);
                        buckets.get(i).add(key);
                        return 1;
                    }
                }
            }
        }
        if ( special.size() < bucketSize ) {
            currentSize += 1;
            special.add(key);
            return 1;
        }
        return -1;
    }

    public int get(String key) {
        if ( special.contains(key) ) return 2;
        return 1;
    }

    public double utilization() {
        return currentSize * 1.0 / tableSize;
    }

}
