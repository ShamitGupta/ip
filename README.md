# Shamit User Guide

**Shamit** is a powerful, yet simple command-line task manager that helps you keep track of your daily obligations. It categorizes tasks into three types and ensures your data is persisted safely between sessions.

---

## Quick Start

1.  **Requirement**: Ensure you have **Java 17** installed.
2.  **Download**: Get the latest `Shamit.jar` from our the GitHub releases.
3.  **Run**: Open your terminal, navigate to the folder where the JAR is located, and run:
    `java -jar Shamit.jar`

---

## Features

### 1. Adding a To-Do: `todo`
Adds a basic task to your list without any specific time requirements.
* **Format**: `todo DESCRIPTION`
* **Example**: `todo read a book`

### 2. Adding a Deadline: `deadline`
Adds a task that must be completed by a specific date or time.
* **Format**: `deadline DESCRIPTION /by DATE`
* **Example**: `deadline return book /by Sunday`

### 3. Adding an Event: `event`
Adds a task that occurs during a specific time period.
* **Format**: `event DESCRIPTION /from START /to END`
* **Example**: `event project meeting /from Mon 2pm /to 4pm`

### 4. Listing all tasks: `list`
Displays every task currently stored in your list, showing its status (Done/Not Done), task type, and any associated dates.
* **Format**: `list`

### 5. Marking/Unmarking tasks: `mark` / `unmark`
Updates the status of an existing task to completed or incomplete.
* **Format**: `mark INDEX` or `unmark INDEX`
* **Example**: `mark 1` (Marks the first task in the list as done)

### 6. Finding tasks: `find`
Searches for tasks that contain a specific keyword in their description.
* **Format**: `find KEYWORD`
* **Example**: `find book`

### 7. Deleting a task: `delete`
Removes a task from the list permanently.
* **Format**: `delete INDEX`
* **Example**: `delete 3`

### 8. Exiting: `bye`
Safely exits the program. Your list is automatically saved to the hard drive.
* **Format**: `bye`

---

## Data Management
Shamit automatically saves all your changes to a file located at `./data/shamit.txt` relative to the application's folder. This path is OS-independent and ensures your data moves with your application.

---

## FAQ

**Q: Where is my data saved?**
**A**: It is saved in a folder named `data` created in the same directory as your JAR file.

**Q: Can I search for multiple words?**
**A**: Yes, the `find` command will look for the exact sequence of characters you provide.