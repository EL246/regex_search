package regex_search;

import org.junit.Test;

public class PatternMatcherTest {

    @Test
    public void matches() {
        PatternMatcher p1 = new PatternMatcher("abc", "abc");
        assert p1.matches();
        PatternMatcher p2 = new PatternMatcher("abcdefg","abc");
        assert !p2.matches();
        PatternMatcher p3 = new PatternMatcher("abc","abcdefg");
        assert !p3.matches();
        PatternMatcher p4 = new PatternMatcher("abd","abc");
        assert !p4.matches();
        PatternMatcher p5 = new PatternMatcher("a.c","a.c");
        assert p5.matches();
        PatternMatcher p6 = new PatternMatcher("a.c","abc");
        assert p6.matches();
        PatternMatcher p7 = new PatternMatcher("a?bc","abc");
        assert p7.matches();
        PatternMatcher p8 = new PatternMatcher("a?bc","ac");
        assert p8.matches();
        PatternMatcher p9 = new PatternMatcher("?aab","ab");
        assert p9.matches();
        PatternMatcher p10b = new PatternMatcher("E+.","ERR");
        assert p10b.matches();
        PatternMatcher p10 = new PatternMatcher("ERROR: *.","ERROR: file not found");
        assert p10.matches();
        PatternMatcher p11 = new PatternMatcher("ERROR: *.","WARNING: file not found");
        assert !p11.matches();
        PatternMatcher p12 = new PatternMatcher("test?", "test");
        assert p12.matches();
        PatternMatcher p13 = new PatternMatcher("test*.", "test");
        assert p13.matches();
        PatternMatcher p14 = new PatternMatcher("test.","test");
        assert !p14.matches();
        PatternMatcher p15 = new PatternMatcher("abc*.gh*.", "abcdefghijklmn");
        assert p15.matches();
        PatternMatcher p16 = new PatternMatcher("abc+def?ghij*k*.","abcdefghijklmn");
        assert p16.matches();
        PatternMatcher p17 = new PatternMatcher("","");
        assert p17.matches();
        PatternMatcher p18 = new PatternMatcher("*a+a","a");
        assert p18.matches();
        PatternMatcher p19 = new PatternMatcher(".","");
        assert !p19.matches();
        PatternMatcher p20 = new PatternMatcher("?.", "");
        assert p20.matches();
    }
}