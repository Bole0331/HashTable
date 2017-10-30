public class Test {

    public static void main(String[] argv) {
        HashTable ht = new Cuckoo(100,1000);
        LoadGenerator lg = new LoadGenerator(ht);
        lg.startAppend();
        System.out.println("Cuckoo Hashing Table");
        System.out.println("utilization: " + lg.getUtilization());
        System.out.println("average cost: " + lg.averageCost());

        System.out.println("-----------------------------------");

        HashTable _ht = new Linear(100,1000, 8);
        LoadGenerator _lg = new LoadGenerator(_ht);
        _lg.startAppend();
        System.out.println("Linear Hashing Table");
        System.out.println("utilization: " + _lg.getUtilization());
        System.out.println("average cost: " + _lg.averageCost());
    }
}
