package regex_search;

/**
 *
 *
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: regex_searcher <pattern> <string>");
        }
        // first argument is regex pattern
        String pattern = args[0];

        // second argument is string to be matched
        String stringToMatch = args[1];

        Searcher parser = new Searcher(pattern, stringToMatch);
        System.out.println(parser.parse());
    }
}
