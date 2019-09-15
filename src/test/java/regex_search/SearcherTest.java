package regex_search;

import org.junit.Test;

public class SearcherTest {

    @Test
    public void parse() {
        Searcher p1 = new Searcher("abc", "abc");
        assert p1.matches();
        Searcher p2 = new Searcher("abcdefg","abc");
        assert !p2.matches();
        Searcher p3 = new Searcher("abc","abcdefg");
        assert !p3.matches();
        Searcher p4 = new Searcher("abd","abc");
        assert !p4.matches();
        Searcher p5 = new Searcher("a.c","a.c");
        assert p5.matches();
        Searcher p6 = new Searcher("a.c","abc");
        assert p6.matches();
        Searcher p7 = new Searcher("a?bc","abc");
        assert p7.matches();
        Searcher p8 = new Searcher("a?bc","ac");
        assert p8.matches();
        Searcher p9 = new Searcher("?aab","ab");
        assert p9.matches();
        Searcher p10b = new Searcher("E+.","ERR");
        assert p10b.matches();
        Searcher p10 = new Searcher("ERROR: *.","ERROR: file not found");
        assert p10.matches();
        Searcher p11 = new Searcher("ERROR: *.","WARNING: file not found");
        assert !p11.matches();
        Searcher p12 = new Searcher("test?", "test");
        assert p12.matches();
        Searcher p13 = new Searcher("test*.", "test");
        assert p13.matches();
        Searcher p14 = new Searcher("test.","test");
        assert !p14.matches();
        Searcher p15 = new Searcher("abc*.gh*.", "abcdefghijklmn");
        assert p15.matches();
        Searcher p16 = new Searcher("abc+def?ghij*k*.","abcdefghijklmn");
        assert p16.matches();
        Searcher p17 = new Searcher("","");
        assert p17.matches();
        Searcher p18 = new Searcher("*a+a","a");
        assert p18.matches();
    }
}