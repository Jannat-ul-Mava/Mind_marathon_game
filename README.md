# 🧠 Mind Marathon

A comprehensive JavaFX-based quiz game application featuring multiple categories, difficulty levels, and a complete user progression system with achievements, shop, and profile management.

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

## 📋 Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [How to Play](#how-to-play)
- [Project Structure](#project-structure)
- [Game Mechanics](#game-mechanics)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### 🎮 Core Gameplay
- **Multiple Quiz Categories**: Science, Art, Geography, History, Puzzle, and Sports
- **Three Difficulty Levels**: Easy, Medium, and Epic (Hard)
- **5 Questions per Quiz**: Quick, engaging quiz sessions
- **Timed Challenges**: 30-second timer per question
- **Real-time Scoring**: Earn 2 coins per correct answer

### 👤 User Management
- **Secure Authentication**: Sign up and login with password protection
- **Password Recovery**: PIN-based password reset system
- **Profile Customization**: 
  - Choose from 5 pre-loaded avatars
  - Upload custom profile images
  - Edit name, age, and password
- **Persistent Data**: All user progress saved locally

### 🏆 Progression System
- **Lives System**: 
  - Start with 3 lives
  - Regenerate 1 life every 3 minutes (max 5 lives)
  - Lose a life for wrong answers or timeout
- **Coins Economy**: 
  - Earn coins by answering correctly
  - Spend coins in the shop
- **Hints System**: 
  - Purchase hints (10 coins each)
  - Get helpful clues during quizzes
- **Achievements**: 
  - Problem-Solver Medal (100 points)
  - Skilled Analyst Medal (500 points)
  - Sage of Wisdom Medal (1000 points)
  - Progress bar tracking

### 🛍️ Shop System
- **Buy Hints**: 10 coins each
- **Buy Extra Lives**: 10 coins each
- **Real-time Inventory Updates**

### 📊 Statistics & History
- **Game History**: Track all completed quizzes
- **Performance Metrics**: 
  - Difficulty level played
  - Category
  - Date and time
  - Pass/Fail status
  - Score achieved
- **Achievement Progress**: Visual progress tracking with medals

### 🎨 UI/UX Features
- **Modern Interface**: Clean, colorful, and intuitive design
- **Smooth Animations**: Button hover effects and transitions
- **Custom Title Bar**: Fully customized window controls
- **Responsive Layout**: Maximized fullscreen experience
- **Visual Feedback**: 
  - Green for correct answers
  - Red for incorrect answers
  - Star ratings based on performance

## 🖼️ Screenshots

*(Add screenshots of your application here)*

```
screenshots/
├── login.png           # Login page
├── menu.png            # Main menu
├── category.png        # Category selection
├── difficulty.png      # Difficulty selection
├── quiz.png            # Quiz in action
├── result.png          # Results page
├── achievements.png    # Achievements page
├── shop.png            # Shop page
└── history.png         # Game history
```

## 🛠️ Technologies Used

- **Java 17+**: Core programming language
- **JavaFX 21**: GUI framework
- **CSS**: Custom styling
- **File I/O**: Local data persistence
- **Java Time API**: Life regeneration system

## 📥 Installation

### Prerequisites

- Java JDK 17 or higher
- JavaFX SDK 21 or higher
- Maven or Gradle (optional, for dependency management)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/mind-marathon.git
   cd mind-marathon
   ```

2. **Set up JavaFX**
   - Download JavaFX SDK from [openjfx.io](https://openjfx.io/)
   - Extract to a location on your computer
   - Add JavaFX library to your IDE

3. **Configure IDE (IntelliJ IDEA example)**
   ```
   File → Project Structure → Libraries → + → Java
   Navigate to JavaFX lib folder and select all JAR files
   ```

4. **Add VM options**
   ```
   --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
   ```

5. **Add Resources**
   - Place all image resources in `src/main/resources/com/example/mind_marathon_project/`
   - Place MCQ files in `src/main/resources/com/example/mcqs/`

6. **Run the application**
   ```bash
   # From your IDE, run Welcome_page.java
   # Or compile and run from command line
   javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls Welcome_page.java
   java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls Welcome_page
   ```

## 🎯 How to Play

### Getting Started

1. **Launch the Application**: Run `Welcome_page.java`
2. **Sign Up**: Create a new account with username, age, password, and 4-digit PIN
3. **Login**: Enter your credentials to access the main menu

### Playing a Quiz

1. **Select Play** from the main menu
2. **Choose a Category**: Science, Art, Geography, History, Puzzle, or Sports
3. **Select Difficulty**: Easy, Medium, or Epic
4. **Answer Questions**: 
   - Read the question carefully
   - You have 30 seconds per question
   - Use a hint if needed (costs coins)
   - Select your answer
5. **View Results**: Check your score, stars, and coins earned

### Managing Your Profile

- **Edit Info**: Update name, age, or password
- **Change Avatar**: Choose from pre-loaded options or upload custom image
- **View Achievements**: Track your progress and unlock medals
- **Check History**: Review all past quiz attempts

### Using the Shop

- **Purchase Hints**: Buy hints to use during quizzes (10 coins each)
- **Buy Lives**: Purchase extra lives to keep playing (10 coins each)

## 📁 Project Structure

```
mind-marathon-project/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/mind_marathon_project/
│       │       ├── Welcome_page.java           # Entry point
│       │       ├── Login_page.java             # Sign up
│       │       ├── Login_page2.java            # Login
│       │       ├── ForgotPasswordPage.java     # Password recovery
│       │       ├── Menu_page.java              # Main menu
│       │       ├── Interest_page.java          # Category selection
│       │       ├── gameLevel.java              # Difficulty selection
│       │       ├── QuizPage.java               # Quiz gameplay
│       │       ├── Result_page.java            # Results display
│       │       ├── Achievements_page.java      # Achievements tracker
│       │       ├── game_history.java           # Game history
│       │       ├── ShopStop_page.java          # Shop
│       │       ├── Edit_page.java              # Profile editor
│       │       ├── info_page.java              # Info/About
│       │       ├── exit_page.java              # Exit confirmation
│       │       ├── Player.java                 # Player data model
│       │       ├── Question.java               # Question data model
│       │       ├── GameRecord.java             # Game record model
│       │       ├── UserSession.java            # Session management
│       │       └── CustomTitleBar.java         # Custom window controls
│       └── resources/
│           └── com/example/mind_marathon_project/
│               ├── *.png                       # Image assets
│               └── mcqs/
│                   ├── science.txt
│                   ├── art.txt
│                   ├── geography.txt
│                   ├── history.txt
│                   ├── puzzle.txt
│                   └── sport&GK.txt
├── user_data.txt                               # User database (auto-generated)
├── {username}_history.txt                      # Game history files (auto-generated)
├── README.md
└── LICENSE
```

## 🎮 Game Mechanics

### Scoring System

- **Correct Answer**: +2 coins
- **Wrong Answer**: -1 life (no coins)
- **Timeout**: -1 life (no coins)
- **Achievement Points**: 10 points per correct answer (for medal progress)

### Lives System

- **Starting Lives**: 3
- **Maximum Lives**: 5
- **Regeneration**: 1 life every 3 minutes
- **Loss Conditions**: Wrong answer or timeout

### Coin Economy

- **Starting Coins**: 10 (first-time sign up)
- **Earning**: 2 coins per correct answer
- **Spending**: 
  - Hint: 10 coins
  - Extra Life: 10 coins

### Question Format

Questions are stored in text files with the following format:
```
Easy
("Question text", "Option 1", "Option 2", "Option 3", "Option 4", "Hint text", "Correct Answer")

Medium
("Question text", "Option 1", "Option 2", "Option 3", "Option 4", "Hint text", "Correct Answer")

Hard
("Question text", "Option 1", "Option 2", "Option 3", "Option 4", "Hint text", "Correct Answer")
```

### Data Persistence

User data is stored in `user_data.txt`:
```
Name:username
Password:password
PIN:1234
Age:25
Coins:50
Lives:3
TotalScore:150
Hints:2
LastLifeRegen:2026-01-30T12:30:00
ProfileImage:/path/to/image.png
---
```

Game history is stored in `{username}_history.txt`:
```
Easy,Science,30-01-26 14:30,Pass,5
Medium,History,30-01-26 15:00,Fail,3
```

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Ideas for Contributions

- Add more quiz categories
- Implement multiplayer mode
- Add sound effects and background music
- Create a leaderboard system
- Add difficulty-based rewards
- Implement daily challenges
- Add more achievement types
- Create a mobile version

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/yourusername)

## 🙏 Acknowledgments

- JavaFX community for excellent documentation
- OpenJFX for the framework
- All contributors and testers

## 📞 Contact

For questions, suggestions, or issues:
- **Email**: your.email@example.com
- **GitHub Issues**: [Create an issue](https://github.com/yourusername/mind-marathon/issues)
- **LinkedIn**: [Your LinkedIn](https://linkedin.com/in/yourprofile)

---

**Enjoy the Mind Marathon! Test your knowledge and challenge yourself!** 🎯🧠🏆
#   M i n d _ m a r a t h o n _ g a m e  
 