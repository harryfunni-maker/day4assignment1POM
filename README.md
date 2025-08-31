# day4assignment1POM
day4assignment1POM

# Java Selenium POM Address Book Automation

## Project Structure
- `src/test/java/pages/AddressBookPage.java`: Page Object Model for Address Book
- `src/test/java/tests/AddressBookTests.java`: JUnit test cases for positive and negative scenarios

## How to Run
1. Install dependencies (Selenium, JUnit 5, WebDriverManager or provide chromedriver path)
2. Update login credentials in `AddressBookTests.java` to a valid user
3. Run tests with your IDE or:
   ```
   mvn test
   ```

## Notes
- Tests cover add, delete, and negative address book scenarios
- POM design pattern is used
- Hooks/annotations are implemented with JUnit
- Assertions are included for all cases
