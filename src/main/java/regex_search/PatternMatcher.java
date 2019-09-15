package regex_search;

import java.util.Arrays;
import java.util.List;

class PatternMatcher {
    private static final List<Character> SPECIAL_CHARS = Arrays.asList('?', '*', '+');
    final private String stringToMatch;
    final private String pattern;
    private boolean[] prevMatches;

    PatternMatcher(String pattern, String stringToMatch) {
        this.stringToMatch = stringToMatch;
        this.pattern = pattern;
        this.prevMatches = new boolean[stringToMatch.length() + 1];
        // Index 0 is for an empty string, two empty strings match at prevMatches[0].
        prevMatches[0] = true;
    }

    boolean matches() {
        for (int i = 0; i < pattern.length(); ++i) {
            if (!checkPattern(i)) {
                return false;
            }
        }
        return prevMatches[stringToMatch.length()];
    }

    private boolean checkPattern(int patternIndex) {
        boolean foundMatch = false;

        // If current is special character, prevMatches array stays the same.
        // It is assumed that a special character is always followed by another character.
        // If a special character is the last character in the pattern, return false.
        if (SPECIAL_CHARS.contains(pattern.charAt(patternIndex))) {
            return patternIndex < pattern.length() - 1;
        }

        // Create new boolean array for current pattern index.
        boolean[] curMatches = new boolean[stringToMatch.length() + 1];

        // It is assumed that a special character is always followed by another character.
        Character specialChar = getSpecialCharIfExists(patternIndex);

        for (int i = 0; i <= stringToMatch.length(); ++i) {
            boolean isMatch = processCharacter(patternIndex, i, specialChar, curMatches);
            curMatches[i] = isMatch;
            foundMatch |= isMatch;
        }
        // Update prevMatches array with new values.
        prevMatches = curMatches;
        return foundMatch;
    }

    private boolean charsMatchAndValidPrevState(int patternIndex, int stringIndex) {
        if (stringIndex == 0) return false;
        if (prevMatches[stringIndex - 1]) {
            return charsMatch(patternIndex, stringIndex);
        }
        return false;
    }

    private boolean charsMatch(int patternIndex, int stringIndex) {
        if (stringIndex == 0) return false;
        return pattern.charAt(patternIndex) == '.' ||
                stringToMatch.charAt(stringIndex - 1) == pattern.charAt(patternIndex);
    }

    private boolean processCharacter(int patternIndex, int stringIndex, Character specialChar, boolean[] curMatches) {
        switch (specialChar) {
            case '?':
                // Check for 0 or 1 instances of following char.
                return checkForZeroInstances(patternIndex, stringIndex) ||
                        checkForOneInstance(patternIndex, stringIndex);
            case '*':
                // Check for 0+ occurrences of following char.
                return checkForZeroInstances(patternIndex, stringIndex) ||
                        checkForOneInstance(patternIndex, stringIndex) ||
                        checkForMultipleInstances(patternIndex, stringIndex, curMatches);
            case '+':
                // Check for 1+ occurrences of following char.
                return checkForOneInstance(patternIndex, stringIndex) ||
                        checkForMultipleInstances(patternIndex, stringIndex, curMatches);
            default:
                // If not a special character, check whether characters match.
                return charsMatchAndValidPrevState(patternIndex, stringIndex);
        }
    }

    private boolean containsSpecialChar(int index) {
        return index > 0 && SPECIAL_CHARS.contains(pattern.charAt(index - 1));
    }

    private Character getSpecialCharIfExists(int patternIndex) {
        if (containsSpecialChar(patternIndex)) {
            return pattern.charAt(patternIndex - 1);
        }
        // Return null character if not special character
        return 0;
    }

    private boolean checkForZeroInstances(int patternIndex, int stringIndex) {
        // True if previous string index matched previous pattern.
        // Check same column in prevMatches.
        return prevMatches[stringIndex];
    }

    private boolean checkForOneInstance(int patternIndex, int stringIndex) {
        // True if previous string index matched previous pattern, and current characters match.
        return charsMatchAndValidPrevState(patternIndex, stringIndex);
    }

    private boolean checkForMultipleInstances(int patternIndex, int stringIndex, boolean[] curMatches) {
        // True if previous char in string matched this pattern, and current string does as well.
        boolean prevCharMatched = stringIndex > 0 && curMatches[stringIndex - 1];
        return prevCharMatched && charsMatch(patternIndex, stringIndex);
    }
}