public class Test {
    public static void main(String[] args) {
        Cache<Integer, String> cache = new Cache<Integer, String>(4);
        String a = "test 1";
        String b = "test 2";
        String c = "test 3";
        String d = "test 4";
        String e = "test 5";
        String f = "test 6";
        String g = "test 7";
        
        cache.addObject(a, a.hashCode());
        cache.addObject(b, b.hashCode());
        cache.addObject(c, c.hashCode());
        cache.addObject(d, d.hashCode());
        cache.addObject(e, e.hashCode());
        cache.addObject(f, f.hashCode());
        cache.addObject(g, g.hashCode());
        cache.addObject(e, e.hashCode());
        cache.addObject(a, a.hashCode());
        cache.printCache();
    }
}