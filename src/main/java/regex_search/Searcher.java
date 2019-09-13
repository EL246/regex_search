package regex_search;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

class Searcher {
    //TODO: include '.' ?
    private static final List<Character> specialChars = Arrays.asList('?', '*', '+');
    private String stringToMatch;
    private String pattern;

    Searcher(String pattern, String stringToMatch) {
        this.stringToMatch = stringToMatch;
        this.pattern = pattern;
    }

    boolean parse() {
        // match char to char // check for match
        // . => any single char //// skip next character
        // ? => 0|1 occurrences of following char //// check for '?' or 0/1 occurrences of next string
        // * => 0+ occurrences of following char ////
        // + => 1+ occurrences of following char ////

        // notes: must match entire string
        // don't worry about escaping characters

        Deque<Pair> queue = new ArrayDeque<>();
        Pair pair = new Pair(pattern, 0);
        queue.offer(pair);
        while (queue.size() > 0) {
            Pair p = queue.poll();
            if (checkPattern(p, queue)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPattern(Pair pair, Deque<Pair> queue) {
        int i = 0;
        int j = pair.getIndex();
        String pattern = pair.getPattern();
        while (i < pattern.length() && j < stringToMatch.length()) {
            char p = pattern.charAt(i);
            if (specialChars.contains(p)) {
                processSpecialCharacter(queue, i, j, pattern, p);
            }
            char s = stringToMatch.charAt(j);
            if (p == '.' || p == s) {
                i++;
                j++;
            } else {
                return false;
            }
        }
        if (pattern.equals("") && j >= stringToMatch.length()) return true;
        // check if ended before reaching end of pattern or end of string
        if(i < pair.getPattern().length() && specialChars.contains(pair.getPattern().charAt(i))) {
            processSpecialCharacter(queue, i, j, pattern, pair.getPattern().charAt(i));
        }
        return j >= stringToMatch.length() && i >= pair.getPattern().length();
    }

    private void processSpecialCharacter(Deque<Pair> queue, int i, int j, String pattern, char p) {
        switch (p) {
            case '?':
                // check for 0 or 1 instances of following char
                checkForZeroInstances(queue, i, j, pattern);
                checkForOneInstance(queue, i, j, pattern);
                break;
            case '*':
                // check for 0+ occurrences of following char
                checkForZeroInstances(queue, i, j, pattern);
                //TODO: is it necessary to check for one instance?
                //checkForOneInstance(queue, i, j, pattern);
                checkForMultipleInstances(queue, i, j, pattern);

                break;
            case '+':
                // check for 1+ occurrences of following char
                //checkForOneInstance(queue, i, j, pattern);
                checkForMultipleInstances(queue, i, j, pattern);
                break;
        }
    }

    //TODO: how to handle string that ends in special character?
    //TODO: comparing empty string and empty pattern?
    //TODO: do DFS rather than BFS
    private void checkForZeroInstances(Deque<Pair> queue, int i, int j, String pattern) {
        String pat = pattern.length() > i + 2 ? pattern.substring(i + 2) : "";
        queue.add(new Pair(pat, j));
    }

    private void checkForOneInstance(Deque<Pair> queue, int i, int j, String pattern) {
        String pat = pattern.length() > i + 1 ? pattern.substring(i + 1) : "";
        queue.add(new Pair(pat, j));
    }

    private void checkForMultipleInstances(Deque<Pair> queue, int i, int j, String pattern) {
        if (pattern.length() > i + 1) {
            char nextP = pattern.charAt(i + 1);
            String pat = "" + nextP + '*' + pattern.substring(i + 1);
            queue.add(new Pair(pat, j));
        }
    }
}
