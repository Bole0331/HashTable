import java.util.*;

public class LoadGenerator {

    private Set<String> allRecords;

    private HashTable ht;

    private Random rand;

    public LoadGenerator(HashTable ht) {
        this.ht = ht;
        rand = new Random();
        allRecords = new HashSet<>();
    }

    public double getUtilization() {
        return ht.utilization();
    }

    private String getRandomKey() {
        while(true) {
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < 8; i++ ) {
                int c = rand.nextInt(10);
                sb.append(c);
            }
            if ( !allRecords.contains(sb.toString()) )
                return sb.toString();
        }
    }

    public void startAppend() {
        while(true) {
            String key = getRandomKey();
            if ( ht.add(key) == -1  ) break;
            allRecords.add(key);
        }
    }

    public double averageCost() {
        int sum = 0;
        for ( String key : allRecords )
            sum += ht.get(key);
        return 1.0 * sum / allRecords.size();
    }

}
