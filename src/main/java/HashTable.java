import java.util.*;

public abstract class HashTable {

    protected long tableSize;
    protected long currentSize;
    protected int bucketSize;
    protected int bucketNum;

    protected List<Set<String>> buckets;

    public abstract int add(String key);

    public abstract int get(String key);

    public abstract double utilization();

}
