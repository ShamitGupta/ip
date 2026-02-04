public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void mark_as_done(){ //use for mark statements
        this.isDone = true;
    }

    public void unmark_as_done(){ //use for unmark statements
        this.isDone = false;
    }

    //...
}
