package model;

public class Task {
    private final int id;
    private String description;
    private boolean done;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.done = false;
    }

    public int getId() {return id;}
    public String getDescription() {return description;}
    public boolean isDone() {return done;}

    public void setDone(boolean done){
        this.done = done;
    }
    public void setDescription(String description){ this.description = description; }

    @Override
    public String toString() {
        return id + " - " + description + (done ? " [✔]" : " [ ]");
    }

}
