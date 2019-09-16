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
        // TODO: modify code to not create a new object each time.
        return new PatternMatcher(pattern, stringToMatch).matches();
    }

    private static void test() {
        // Exact match and simple mismatch.
        assert matches("abc", "abc");
        assert !matches("abcdefg", "abc");
        assert !matches("abc", "abcdefg");
        assert !matches("abd", "abc");
        assert matches("a", "a");
        // Any-char matches.
        assert matches("a.c", "a.c");
        assert matches("a.c", "abc");
        // An optional pattern char matches with and without.
        assert matches("a?bc", "abc");
        assert matches("a?bc", "ac");
        // An optional char that can match is not forced to.
        assert matches("?aab", "ab");
        // Classic log searching.
        assert matches("E+.", "ERR");
        assert matches("ERROR: *.", "ERROR: file not found");
        assert !matches("ERROR: *.", "WARNING: file not found");
        // Special character at end is not valid
        assert !matches("test?", "test");
        assert !matches("test.", "test");
        assert !matches("test+", "test");
        // Empty strings match.
        assert matches("", "");
        // Empty string pattern does not match with non-empty strings
        assert !matches("", "a");
        assert !matches(".", "");
        //Optional character matches with empty string
        assert matches("?.", "");
        //Other
        assert matches("test*.", "test");
        assert matches("abc*.gh*.", "abcdefghijklmn");
        assert matches("abc+def?ghij*k*.", "abcdefghijklmn");
        assert matches("*a+a", "a");

    }
}
