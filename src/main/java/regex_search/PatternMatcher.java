package regex_search;

import java.util.Arrays;
import java.util.List;

// match char to char
// . => any single char
// ? => 0|1 occurrences of following char
// * => 0+ occurrences of following char
// + => 1+ occurrences of following char
// notes: must match entire string
// don't worry about escaping characters
class PatternMatcher {
    private static final List<Character> specialChars = Arrays.asList('?', '*', '+');
    private String stringToMatch;
    private String pattern;
    private boolean[][] matches;

    // TODO: can create 1-D array instead of 2-D matrix
    // TODO: make patternMatcher static?
    PatternMatcher(String pattern, String stringToMatch) {
        this.stringToMatch = stringToMatch;
        this.pattern = pattern;
        this.matches = new boolean[pattern.length() + 1][stringToMatch.length() + 1];
        matches[0][0] = true;
    }

    boolean matches() {
        for (int i = 0; i < pattern.length(); ++i) {
            if (!checkPattern(i)) {
                return false;
            }
        }
        return matches[pattern.length()][stringToMatch.length()];
    }

    private boolean checkPattern(int patternIndex) {
        boolean foundMatch = false;

        // If current is special character, skip
        // It is assumed that a special character is always followed by another character
        // If a special character is the last character in the pattern, return false
        if (specialChars.contains(pattern.charAt(patternIndex))) {
            if (patternIndex < pattern.length() - 1) {
                matches[patternIndex + 1] = matches[patternIndex];
                return true;
            } else {
                return false;
            }
        }

        //TODO: can start string at later index? patternIndex?
        //TODO: instead of having buffer array for empty string, just return true at 0,0?
        // It is assumed that a special character is always followed by another character
        Character specialChar = getSpecialCharIfExists(patternIndex);
        for (int i = 0; i <= stringToMatch.length(); ++i) {
            boolean isMatch = processCharacter(patternIndex, i, specialChar);
            matches[patternIndex + 1][i] = isMatch;
            foundMatch |= isMatch;
        }
        return foundMatch;
    }

    private boolean charsMatchAndValidPrevState(int patternIndex, int stringIndex) {
        if (stringIndex == 0) return false;
        if (matches[patternIndex][stringIndex - 1]) {
            return charsMatch(patternIndex, stringIndex);
        }
        return false;
    }

    private boolean charsMatch(int patternIndex, int stringIndex) {
        if (stringIndex == 0) return false;
        return pattern.charAt(patternIndex) == '.' ||
                stringToMatch.charAt(stringIndex - 1) == pattern.charAt(patternIndex);
    }

    private boolean processCharacter(int patternIndex, int stringIndex, Character specialChar) {
        switch (specialChar) {
            case '?':
                // check for 0 or 1 instances of following char
                return checkForZeroInstances(patternIndex, stringIndex) ||
                        checkForOneInstance(patternIndex, stringIndex);
            case '*':
                // check for 0+ occurrences of following char
                return checkForZeroInstances(patternIndex, stringIndex) ||
                        checkForOneInstance(patternIndex, stringIndex) ||
                        checkForMultipleInstances(patternIndex, stringIndex);
            case '+':
                // check for 1+ occurrences of following char
                return checkForOneInstance(patternIndex, stringIndex) ||
                        checkForMultipleInstances(patternIndex, stringIndex);
            default:
                // if no special character, just check whether characters match
                return charsMatchAndValidPrevState(patternIndex, stringIndex);
        }
    }

    private boolean containsSpecialChar(int index) {
        return index > 0 && specialChars.contains(pattern.charAt(index - 1));
    }

    private Character getSpecialCharIfExists(int patternIndex) {
        if (containsSpecialChar(patternIndex)) {
            return pattern.charAt(patternIndex - 1);
        }
        // return null character
        return 0;
    }

    //TODO: how to handle string that ends in special character?
    //TODO: comparing empty string and empty pattern?
    private boolean checkForZeroInstances(int patternIndex, int stringIndex) {
        // TODO: will prev be true if prev is special character?
        // true if previous string index matched previous pattern
        // check same column, row above
        return matches[patternIndex][stringIndex];
    }

    private boolean checkForOneInstance(int patternIndex, int stringIndex) {
        // true if previous string index matched previous pattern, and current characters match
        return charsMatchAndValidPrevState(patternIndex, stringIndex);
    }

    //TODO: indexing is confusing if zero is reserved for empty string
    private boolean checkForMultipleInstances(int patternIndex, int stringIndex) {
        // true if previous char in string matched this pattern, and current string does as well
        //TODO: check this logic. stringIndex should be greater than 0 or greater than 1?
        boolean prevCharMatched = stringIndex > 0 && matches[patternIndex + 1][stringIndex - 1];
        return prevCharMatched && charsMatch(patternIndex, stringIndex);
    }
}
