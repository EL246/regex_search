package regex_search;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

class Searcher {
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

        Deque<Pair> stack = new ArrayDeque<>();
        Pair pair = new Pair(pattern, 0);
        stack.offer(pair);
        while (stack.size() > 0) {
            Pair p = stack.pop();
            if (checkPattern(p, stack)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPattern(Pair pair, Deque<Pair> stack) {
        int patternIndex = 0;
        int stringIndex = pair.getIndex();
        String pattern = pair.getPattern();
        while (patternIndex < pattern.length() && stringIndex < stringToMatch.length()) {
            char p = pattern.charAt(patternIndex);
            if (specialChars.contains(p)) {
                processSpecialCharacter(stack, patternIndex, stringIndex, pattern, p);
            }
            char s = stringToMatch.charAt(stringIndex);
            if (p == '.' || p == s) {
                patternIndex++;
                stringIndex++;
            } else {
                return false;
            }
        }
        if (pattern.equals("") && stringIndex >= stringToMatch.length()) return true;
        // check if ended before reaching end of pattern or end of string
        if (patternIndex < pair.getPattern().length() && specialChars.contains(pair.getPattern().charAt(patternIndex))) {
            processSpecialCharacter(stack, patternIndex, stringIndex, pattern, pair.getPattern().charAt(patternIndex));
        }
        return stringIndex >= stringToMatch.length() && patternIndex >= pair.getPattern().length();
    }

    private void processSpecialCharacter(Deque<Pair> stack, int i, int j, String pattern, char p) {
        switch (p) {
            case '?':
                // check for 0 or 1 instances of following char

                checkForZeroInstances(stack, i, j, pattern);
                checkForOneInstance(stack, i, j, pattern);
                break;
            case '*':
                // check for 0+ occurrences of following char
                checkForZeroInstances(stack, i, j, pattern);
                checkForMultipleInstances(stack, i, j, pattern);

                break;
            case '+':
                // check for 1+ occurrences of following char
                checkForMultipleInstances(stack, i, j, pattern);
                break;
        }
    }

    //TODO: how to handle string that ends in special character?
    //TODO: comparing empty string and empty pattern?
    private void checkForZeroInstances(Deque<Pair> stack, int i, int j, String pattern) {
        checkForInstance(stack, i, j, pattern, 2);
    }

    private void checkForOneInstance(Deque<Pair> stack, int i, int j, String pattern) {
        checkForInstance(stack, i, j, pattern, 1);
    }

    private void checkForInstance(Deque<Pair> stack, int i, int j, String pattern, int offset) {
        String pat = pattern.length() > i + offset ? pattern.substring(i + offset) : "";
        stack.push(new Pair(pat, j));
    }

    private void checkForMultipleInstances(Deque<Pair> stack, int i, int j, String pattern) {
        if (pattern.length() > i + 1) {
            char nextP = pattern.charAt(i + 1);
            String pat = "" + nextP + '*' + pattern.substring(i + 1);
            stack.push(new Pair(pat, j));
        }
    }
}
