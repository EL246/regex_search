# regex_search

regex_search is a pattern matcher that checks whether a given string matches a given pattern.

## Usage

regex_search accepts two arguments, a pattern to be matched and a string:

```
<pattern> <string>
```

Where:
* `<pattern>` is mandatory, and is a string pattern
* `<string>` is mandatory, the string that matches the pattern
* `<pattern>` supports the following patterns and special characters: 

    * A non-special character in a pattern matches only that character.
    * The special-character `.` in the pattern matches any single character.
    * The special-character `?` in the pattern does not match any character, but
  indicates the following character in the pattern can match zero or one times.
    * The special-character `*` in the pattern does not match any character, but
  indicates the following character in the pattern can match zero or more times.
    * The special-character `+` in the pattern does not match anything, but
  indicates the following character in the pattern can match one or more times.



Here are some sample messages:

`"ERROR: *." "ERROR: file not found"` should return true.
`"a.c" "abc"` should return true.
`"adc" "abc"` should return false.

Possible responses codes are `true` or `false`.

## Getting Started

To start the application, you can compile and run the source code.


## Running the tests

JUnit testing framework was used for this project. To run the tests simply run the commands below in the project directory.
Maven should be [installed](https://maven.apache.org/download.cgi) for this to work. 

````
mvn clean
mvn test
````

# Design Rationale