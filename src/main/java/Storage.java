import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages reading and writing tasks to the local file system.
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the task file and reconstructs the TaskList.
     * @return A list of tasks populated from the file.
     * @throws ShamitException If the file format is corrupted.
     */
    public ArrayList<Task> load() throws ShamitException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File f = new File(filePath);

        // Handle case where file doesn't exist
        if (!f.exists()) {
            throw new ShamitException("File not found");
        }

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
                    continue;
                }

                if (isDone) {
                    task.markDone();
                }
                loadedTasks.add(task);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new ShamitException("Error parsing the save file.");
        }
        return loadedTasks;
    }

    /**
     * Writes the current list of tasks to the text file.
     * @param tasks The list of tasks to be saved.
     * @throws IOException If the file cannot be written to disk.
     */
    public void save(TaskList tasks) throws IOException {
        File f = new File(filePath);
        File directory = f.getParentFile();

        // Handle missing directory case
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter fw = new FileWriter(f)) {
            for (Task t : tasks.getTasks()) {
                fw.write(t.toFileString() + System.lineSeparator());
            }
        }
    }
}