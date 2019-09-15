package regex_search;

class Main {
    public static void main(String[] args) {
        // Process example patterns and strings.
        test();

        // Process command line arguments.
        if (args.length != 2) {
            System.out.println("Usage: regex_search <pattern> <string>");
            return;
        }
        // First argument is the regex pattern.
        String pattern = args[0];

        // Second argument is the string to be matched.
        String stringToMatch = args[1];

        System.out.println(matches(pattern,stringToMatch));
    }

    private static boolean matches(String pattern, String stringToMatch) {
        return new PatternMatcher(pattern, stringToMatch).matches();
    }

    private static void test() {
        assert matches("abc", "abc");
        assert !matches("abcdefg", "abc");
        assert !matches("abc", "abcdefg");
        assert !matches("abd", "abc");
        assert matches("a.c", "a.c");
        assert matches("a.c", "abc");
        assert matches("a?bc", "abc");
        assert matches("a?bc", "ac");
        assert matches("?aab", "ab");
        assert matches("E+.", "ERR");
        assert matches("ERROR: *.", "ERROR: file not found");
        assert !matches("ERROR: *.", "WARNING: file not found");
        assert !matches("test?", "test");
        assert matches("test*.", "test");
        assert !matches("test.", "test");
        assert matches("abc*.gh*.", "abcdefghijklmn");
        assert matches("abc+def?ghij*k*.", "abcdefghijklmn");
        assert matches("", "");
        assert matches("*a+a", "a");
        assert !matches(".", "");
        assert matches("?.", "");
        assert !matches("test+", "test");
        assert !matches("", "a");
    }
}
