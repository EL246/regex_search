# regex_search

regex_search is a pattern matcher that checks whether a given string matches a given pattern.

## Usage

regex_search accepts two arguments, a pattern to be matched and a string. It returns true if the
pattern matches the entire string. Otherwise, it returns false.

```
java regex_search.Main <pattern> <string>
```

Where:
* `<pattern>` is mandatory, and is a string pattern
* `<string>` is mandatory, and is the string that is checked against the pattern
* `<pattern>` supports the following patterns and special characters: 

    * A non-special character in a pattern matches only that character.
    * The special-character `.` in the pattern matches any single character.
    * The special-character `?` in the pattern does not match any character, but
  indicates the following character in the pattern can match zero or one times.
    * The special-character `*` in the pattern does not match any character, but
  indicates the following character in the pattern can match zero or more times.
    * The special-character `+` in the pattern does not match anything, but
  indicates the following character in the pattern can match one or more times.

* it is assumed that a `?`, `*`, or `+` will always be followed by another character

Here are some sample messages:

`"ERROR: *." "ERROR: file not found"` should return true.
`"a.c" "abc"` should return true.
`"adc" "abc"` should return false.

Possible responses are `true` or `false`.

## Getting Started

To start the application, you can compile and run the source code. Run with -ea (-enableassertions) 
flag to enable assertions.

```
java -ea regex_search.Main <pattern> <string>
```

## Running the tests

JUnit testing framework was used for this project. To run the tests simply run the PatternMatcherTest.
Maven can be used for this, with the following commands:  

````
mvn clean
mvn test
````
