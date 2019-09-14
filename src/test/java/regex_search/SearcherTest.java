package regex_search;

import org.junit.Test;

import static org.junit.Assert.*;

public class SearcherTest {

    @Test
    public void parse() {
        Searcher p1 = new Searcher("abc", "abc");
        assert p1.parse();
        Searcher p2 = new Searcher("abcdefg","abc");
        assert !p2.parse();
        Searcher p3 = new Searcher("abc","abcdefg");
        assert !p3.parse();
        Searcher p4 = new Searcher("abd","abc");
        assert !p4.parse();
        Searcher p5 = new Searcher("a.c","a.c");
        assert p5.parse();
        Searcher p6 = new Searcher("a.c","abc");
        assert p6.parse();
        Searcher p7 = new Searcher("a?bc","abc");
        assert p7.parse();
        Searcher p8 = new Searcher("a?bc","ac");
        assert p8.parse();
        Searcher p9 = new Searcher("?aab","ab");
        assert p9.parse();
        Searcher p10b = new Searcher("E+.","ERR");
        assert p10b.parse();
        Searcher p10 = new Searcher("ERROR: *.","ERROR: file not found");
        assert p10.parse();
        Searcher p11 = new Searcher("ERROR: *.","WARNING: file not found");
        assert !p11.parse();
        Searcher p12 = new Searcher("test?", "test");
        assert p12.parse();
        Searcher p13 = new Searcher("test*.", "test");
        assert p13.parse();
        Searcher p14 = new Searcher("test.","test");
        assert !p14.parse();
        Searcher p15 = new Searcher("abc*.gh*.", "abcdefghijklmn");
        assert p15.parse();
        Searcher p16 = new Searcher("abc+def?ghij*k*.","abcdefghijklmn");
        assert p16.parse();
        Searcher p17 = new Searcher("","");
        assert p17.parse();
        Searcher p18 = new Searcher("*a+a","a");
        assert p18.parse();
    }
}