import java.util.Scanner;

public class Ui {
    private Scanner scanner = new Scanner(System.in);

    public void showWelcome() { System.out.println("Hello! I'm Shamit\nHow can I help you?"); }
    public void showLine() { System.out.println("____________________________________________________________"); }
    public String readCommand() { return scanner.nextLine(); }
    public void showError(String message) { System.out.println(message); }
    public void showLoadingError() { System.out.println("No save file found. Starting fresh!"); }
    public void showDismissal() { System.out.println("Bye, please come again!"); }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + "." + tasks.getTask(i));
        }
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("Got it. I've added:\n  " + task + "\nNow you have " + size + " tasks.");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("Noted. I've removed:\n  " + task + "\nNow you have " + size + " tasks.");
    }

    public void showMarked(Task task) { System.out.println("Nice! Task marked as done:\n  " + task); }
    public void showUnmarked(Task task) { System.out.println("OK, task marked as not done:\n  " + task); }

    public void showSearchResults(TaskList tasks) {
        System.out.println("Here are the matching tasks in your list:");
        showTaskList(tasks);
    }
}