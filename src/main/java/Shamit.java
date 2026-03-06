import java.io.IOException;
/**
 * Represents the main entry point and controller for the Shamit chatbot.
 * Orchestrates the UI, storage, and task list logic.
 */
public class Shamit {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes the chatbot and attempts to load tasks from storage.
     * @param filePath The path where the data file is stored.
     */
    public Shamit(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            //load tasks from the storage file
            tasks = new TaskList(storage.load());
        } catch (ShamitException e) {
            // handle cases where the file doesn't exist or is corrupted.
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main execution loop for the chatbot until the user exits.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                String commandWord = Parser.getCommandWord(fullCommand);

                if (commandWord.equals("bye")) {
                    isExit = true;
                    ui.showDismissal();
                } else if (commandWord.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (commandWord.equals("mark") || commandWord.equals("unmark")) {
                    handleUpdateStatus(fullCommand);
                    storage.save(tasks); // Save changes to the .txt file
                } else if (commandWord.equals("delete")) {
                    handleDelete(fullCommand);
                    storage.save(tasks);
                } else if (commandWord.equals("find")) {
                    handleFind(fullCommand);
                } else {
                    // Logic for adding Todo, Deadline, or Event
                    handleAddTask(fullCommand);
                    storage.save(tasks);
                }
            } catch (ShamitException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Critical error: Could not save tasks to file.");
            } finally {
                ui.showLine();
            }
        }
    }

    //Helper Methods

    /**
     * Handles the logic for adding a new task to the list.
     * @param fullCommand The raw user input string.
     * @throws ShamitException If the task format is invalid.
     */
    private void handleAddTask(String fullCommand) throws ShamitException {
        Task newTask = Parser.parseTask(fullCommand);
        tasks.addTask(newTask);
        ui.showTaskAdded(newTask, tasks.getSize());
    }

    private void handleDelete(String fullCommand) throws ShamitException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task removed = tasks.deleteTask(index);
        ui.showTaskDeleted(removed, tasks.getSize());
    }

    private void handleUpdateStatus(String fullCommand) throws ShamitException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        if (fullCommand.startsWith("mark")) {
            tasks.getTask(index).markDone();
            ui.showMarked(tasks.getTask(index));
        } else {
            tasks.getTask(index).unmarkDone();
            ui.showUnmarked(tasks.getTask(index));
        }
    }

    private void handleFind(String fullCommand) throws ShamitException {
        String keyword = Parser.parseKeyword(fullCommand);
        TaskList results = tasks.find(keyword);
        ui.showSearchResults(results);
    }

    public static void main(String[] args) {
        // Starting the application using the data folders path.
        new Shamit("data/shamit.txt").run();
    }
}