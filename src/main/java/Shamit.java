import java.util.Objects;
import java.util.ArrayList; // Used for dynamic task storage
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Shamit {
    private static final String DIRECTORY = "data";
    private static final String FILE_NAME = "shamit.txt";
    private static final Path FILE_PATH = Paths.get(DIRECTORY, FILE_NAME);

    public static void main(String[] args) {
        greet();


        ArrayList<Task> tasks = loadTasks();
        boolean isEnd = false;
        Scanner obj = new Scanner(System.in);

        while(!isEnd){
            String userInput = obj.nextLine();

            try {
                if(userInput.equals("bye")){
                    isEnd = true;
                    dismiss();
                } else if (userInput.equals("list")) {
                    display(tasks);
                } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                    updateStatus(userInput, tasks);
                    saveTasks(tasks);
                } else if (userInput.startsWith("delete")) {
                    deleteTask(userInput, tasks);
                    saveTasks(tasks);
                } else {
                    addTask(userInput, tasks);
                    saveTasks(tasks);
                }
            } catch (ShamitException e) {
                System.out.println(e.getMessage());
            }
        }
        obj.close();
    }

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            // Check if the directory exists, if not, create it
            File directory = new File(DIRECTORY);
            if (!directory.exists()) {
                directory.mkdir();
            }

            FileWriter fw = new FileWriter(FILE_PATH.toFile());
            for (Task t : tasks) {
                fw.write(t.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Oops! I couldn't save your tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = FILE_PATH.toFile();
        if (!f.exists()) return loadedTasks;

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                Task task;
                if (type.equals("T")) {
                    task = new ToDo(desc);
                } else if (type.equals("D")) {
                    task = new Deadline(desc, parts[3]);
                } else if (type.equals("E")) {
                    task = new Event(desc, parts[3], parts[4]);
                } else {
                    continue; // Skip unknown types
                }

                if (isDone) task.markDone();
                loadedTasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error reading the save file.");
        }
        return loadedTasks;
    }

    public static void greet(){
        System.out.println("Hello! I'm Shamit\nHow can I help you?");
    }

    public static void dismiss(){
        System.out.println("Bye, please come again!");
    }


    public static void addTask(String userInput, ArrayList<Task> tasks) throws ShamitException {
        if (userInput.startsWith("todo")) {
            if (userInput.trim().equals("todo")) {
                throw new ShamitException("Hold on! The description of a todo cannot be empty.");
            }
            String description = userInput.substring(5).trim();
            tasks.add(new ToDo(description));

        } else if (userInput.startsWith("deadline")) {
            if (userInput.trim().equals("deadline")) {
                throw new ShamitException("Hold on! The description of a deadline cannot be empty.");
            }
            if (!userInput.contains("/by")) {
                throw new ShamitException("Uh oh! A deadline needs a '/by' segment.");
            }

            String[] parts = userInput.substring(9).split("/by", 2);
            tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));

        } else if (userInput.startsWith("event")) {
            if (userInput.trim().equals("event")) {
                throw new ShamitException("Hold on! The description of an event cannot be empty.");
            }
            if (!userInput.contains("/from") || !userInput.contains("/to")) {
                throw new ShamitException("Uh oh! An event needs both '/from' and '/to' segments.");
            }

            String[] parts = userInput.substring(6).split("/from", 2);
            String description = parts[0].trim();
            String[] timeParts = parts[1].split("/to", 2);
            tasks.add(new Event(description, timeParts[0].trim(), timeParts[1].trim()));

        } else {
            throw new ShamitException("I'm sorry, but I don't know what that command means :-(");
        }

        System.out.println("Got it! I've added this task:\n  " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void deleteTask(String userInput, ArrayList<Task> tasks) throws ShamitException {
        if (userInput.trim().equals("delete")) {
            throw new ShamitException("Please specify which task number to delete.");
        }

        try {
            int taskNum = Integer.parseInt(userInput.split(" ")[1]);
            int index = taskNum - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new ShamitException("I can't find task #" + taskNum + ". You have " + tasks.size() + " tasks.");
            }

            Task removed = tasks.remove(index); // ArrayList handles the shift automatically
            System.out.println("Noted. I've removed this task:\n  " + removed);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new ShamitException("That's not a valid number!");
        }
    }

    public static void display(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Your list is currently empty!");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public static void updateStatus(String userInput, ArrayList<Task> tasks) throws ShamitException {
        try {
            String[] parts = userInput.split(" ");
            int taskNum = Integer.parseInt(parts[1]);
            int index = taskNum - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new ShamitException("Task #" + taskNum + " does not exist.");
            }

            if (userInput.startsWith("mark")) {
                tasks.get(index).markDone();
                System.out.println("Nice! I've marked this task as done:\n  " + tasks.get(index));
            } else {
                tasks.get(index).unmarkDone();
                System.out.println("OK, I've marked this task as not done yet:\n  " + tasks.get(index));
            }
        } catch (Exception e) {
            throw new ShamitException("Please provide a valid task number to mark/unmark.");
        }
    }
}