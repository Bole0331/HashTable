import java.util.*;

public class Cuckoo extends HashTable {

    public Cuckoo(int bucketSize, int bucketNum) {
        this.bucketSize = bucketSize;
        this.bucketNum = bucketNum;
        currentSize = 0;
        tableSize = (long)bucketSize * (long)bucketNum;
        buckets = new ArrayList<>(bucketNum);
        for ( int i = 0; i < bucketNum; i++ ) {
            Set<String> set = new HashSet<>();
            buckets.add(set);
        }
    }

    private boolean evict(String key) {
        int firstHash = Math.abs(key.hashCode()) % bucketNum;
        int secondHash = (int)(Math.abs(SecHashFunction.hashCode(key)) % bucketNum);
        for ( String ele : buckets.get(firstHash) ) {
            int _firstHash = Math.abs(ele.hashCode()) % bucketNum;
            int _secondHash = (int)(Math.abs(SecHashFunction.hashCode(ele)) % bucketNum);
            if ( firstHash == _firstHash && buckets.get(_secondHash).size() < bucketSize ) {
                currentSize += 1;
                buckets.get(_firstHash).remove(ele);
                buckets.get(_firstHash).add(key);
                buckets.get(_secondHash).add(ele);
                return true;
            }
        }
        for ( String ele : buckets.get(secondHash) ) {
            int _firstHash = Math.abs(ele.hashCode()) % bucketNum;
            int _secondHash = (int)(Math.abs(SecHashFunction.hashCode(ele)) % bucketNum);
            if ( secondHash == _firstHash && buckets.get(_secondHash).size() < bucketSize ) {
                currentSize += 1;
                buckets.get(_firstHash).remove(ele);
                buckets.get(_firstHash).add(key);
                buckets.get(_secondHash).add(ele);
                return true;
            }
        }
        return false;
    }

    public int add(String key) {
        int firstHash = Math.abs(key.hashCode()) % bucketNum;
        int secondHash = (int)(Math.abs(SecHashFunction.hashCode(key)) % bucketNum);
        if ( buckets.get(firstHash).size() < bucketSize ) {
            buckets.get(firstHash).add(key);
            currentSize += 1;
            return 1;
        }
        if ( buckets.get(secondHash).size() < bucketSize ) {
            buckets.get(secondHash).add(key);
            currentSize += 1;
            return 1;
        }
        if ( evict(key) ) return 1;
        return -1;
    }

    public int get(String key) {
        int hash = Math.abs(key.hashCode()) % bucketNum;
        if ( buckets.get(hash).contains(key) ) return 1;
        return 2;
    }

    public double utilization() {
        return currentSize * 1.0 / tableSize;
    }

}
