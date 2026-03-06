import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() { this.tasks = new ArrayList<>(); }
    public TaskList(ArrayList<Task> tasks) { this.tasks = tasks; }

    public void addTask(Task t) { tasks.add(t); }
    public Task deleteTask(int i) { return tasks.remove(i); }
    public int getSize() { return tasks.size(); }
    public Task getTask(int i) { return tasks.get(i); }
    public ArrayList<Task> getTasks() { return tasks; }

    public TaskList find(String keyword) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (Task t : tasks) {
            if (t.toString().contains(keyword)) filtered.add(t);
        }
        return new TaskList(filtered);
    }


}