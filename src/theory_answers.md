## Theory

### 1) Which of the following activities cannot be automated?

-  Discussing testability issues

### 2) How do we describe a good unit test?

-  Fast, Repeatable, Self-validating, Timely, Isolated

### 3) When is it a good idea to use XPath selectors?

- All the above (When CSS or other selectors are not an option or would be brittle and hard to maintain,
When we need to find an element based on parent/child/sibling relationship
When an element is located deep within the HTML (or DOM) structure).

### 4) Describe the TDD process?

- Red -> Green -> Refactor. Write a test, it should fail. Write code that will satisfy this test, after the test passes, refactor the code. Write a new test and so on.

### 5) Write 2 test cases or scenarios for a String Calculator application, which has a method calculate() that takes a string of two numbers separated by a comma as input, and returns the sum.

- Given the input "2.6, 3.5" When the method calculate() is called Then I should see "6.1" as a result.
- Given the input "2, -3" When the method calculate() is called Then I should see "-1" as a result.
