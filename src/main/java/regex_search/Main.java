package regex_search;

class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: regex_search <pattern> <string>");
            return;
        }
        // first argument is regex pattern
        String pattern = args[0];

        // second argument is string to be matched
        String stringToMatch = args[1];

        PatternMatcher parser = new PatternMatcher(pattern, stringToMatch);
        System.out.println(parser.matches());
    }
}
