# Contributing to Mind Marathon

First off, thank you for considering contributing to Mind Marathon! It's people like you that make Mind Marathon such a great educational tool.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to [your.email@example.com].

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* **Use a clear and descriptive title**
* **Describe the exact steps to reproduce the problem**
* **Provide specific examples**
* **Describe the behavior you observed and what you expected**
* **Include screenshots if possible**
* **Include your environment details** (OS, Java version, JavaFX version)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

* **Use a clear and descriptive title**
* **Provide a detailed description of the suggested enhancement**
* **Explain why this enhancement would be useful**
* **List some examples of how it would be used**

### Adding New Quiz Questions

We welcome contributions of new quiz questions! Please ensure:

1. Questions are accurate and well-researched
2. Follow the existing format:
   ```
   ("Question text", "Option 1", "Option 2", "Option 3", "Option 4", "Hint text", "Correct Answer")
   ```
3. Place questions under appropriate difficulty level (Easy, Medium, Hard)
4. Provide helpful hints
5. Ensure questions are appropriate for all ages

### Pull Requests

* Fill in the required template
* Do not include issue numbers in the PR title
* Follow the Java coding style
* Include screenshots for UI changes
* Update documentation as needed
* Write meaningful commit messages

## Development Process

### Setting Up Development Environment

1. Fork the repo
2. Clone your fork
3. Set up JavaFX
4. Create a new branch (`git checkout -b feature/amazing-feature`)
5. Make your changes
6. Test thoroughly
7. Commit with clear messages
8. Push to your fork
9. Open a Pull Request

### Coding Standards

#### Java Code Style

* Use camelCase for variables and methods
* Use PascalCase for class names
* Use UPPER_SNAKE_CASE for constants
* Indent with 4 spaces
* Maximum line length: 120 characters
* Always use braces for if/else/for/while
* Add JavaDoc comments for public methods

Example:
```java
/**
 * Calculates the score based on correct answers.
 * 
 * @param correctAnswers Number of correct answers
 * @return The calculated score
 */
public int calculateScore(int correctAnswers) {
    return correctAnswers * POINTS_PER_CORRECT_ANSWER;
}
```

#### JavaFX Best Practices

* Separate UI code from business logic
* Use FXML when appropriate
* Follow MVC/MVP pattern
* Handle exceptions properly
* Always close resources (use try-with-resources)

### Testing

* Test all new features thoroughly
* Test on different screen resolutions
* Test on different operating systems if possible
* Verify data persistence works correctly
* Check for memory leaks

### Commit Messages

* Use present tense ("Add feature" not "Added feature")
* Use imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit first line to 72 characters
* Reference issues and pull requests

Example:
```
Add multiplayer mode feature

- Implement real-time quiz battles
- Add matchmaking system
- Create lobby interface
- Update documentation

Fixes #123
```

## Project Structure Guidelines

### Adding New Pages

1. Create new class extending `Application`
2. Use `CustomTitleBar` for consistency
3. Follow existing styling patterns
4. Update navigation in relevant classes
5. Add to documentation

### Adding New Features

1. Plan the feature architecture
2. Create necessary model classes
3. Implement business logic
4. Create/update UI components
5. Add data persistence if needed
6. Write documentation
7. Test thoroughly

### Resource Files

* Place images in `src/main/resources/com/example/mind_marathon_project/`
* Use PNG format for images
* Optimize image sizes
* Follow naming convention: `lowercase_with_underscores.png`

## Documentation

* Update README.md for new features
* Add code comments for complex logic
* Update PROJECT_DESCRIPTION.md if needed
* Create/update JavaDoc comments

## Questions?

Feel free to open an issue with the label "question" or contact the maintainers directly.

## Recognition

Contributors will be recognized in:
* README.md contributors section
* Release notes
* Project website (if applicable)

Thank you for contributing to Mind Marathon! 🎯🧠
