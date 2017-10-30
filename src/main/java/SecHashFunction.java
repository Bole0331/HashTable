public class SecHashFunction {

    public static long hashCode(String s) {
        long hash = 5381;
        for ( char c : s.toCharArray() ) {
            hash = ( ( hash << 5 ) + hash ) + c;
        }
        return hash;
    }

}
