package service;
import model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private final String FILE_PATH = "tasks.txt";

    public TaskManager() {
        loadTasks();
    }

    public void addTask(String description){
        int id = tasks.size()+1;
        tasks.add(new Task (id, description));
        saveTasks();
    }

    public void listTasks(){
        if(tasks.isEmpty()){
            System.out.println("There are no tasks in the system");
            return;
        }
        for(Task task : tasks){
            System.out.println(task);
        }
    }

    public void markDone(int id){
       for(Task task : tasks){
           if(task.getId() == id){
               task.setDone(true);
               saveTasks();
               return;
           }
       }
       System.out.println("Tarefa não encontrada");
    }

    public void removeTasks(int id){
        tasks.removeIf(task -> task.getId() == id);
        saveTasks();
    }

    private void saveTasks(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for(Task task : tasks){
                writer.write(task.getId() + ";" + task.getDescription() + ";" + task.isDone());
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println("Erro ao salvar tarefa" + e.getMessage());
        }
    }

    public void editTask(int id, String newDescription) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setDescription(newDescription);
                saveTasks();
                return;
            }
        }
    }

    public List<Task> getPendingTasks() {
        List<Task> pending = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isDone()) pending.add(t);
        }
        return pending;
    }

    public List<Task> getCompletedTasks() {
        List<Task> completed = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isDone()) completed.add(t);
        }
        return completed;
    }

    private void loadTasks(){
    File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try(BufferedReader reader = new BufferedReader( new FileReader(FILE_PATH))){
            String line;
            while((line = reader.readLine()) != null){
                String[] task = line.split(";");
                int id = Integer.parseInt(task[0]);
                String description = task[1];
                boolean done = Boolean.parseBoolean(task[2]);
                Task tarefa = new Task(id, description);
                tarefa.setDone(done);
                tasks.add(tarefa);
        }
        }catch(IOException e) {
            System.out.println("Erro ao carregar tarefas: " + e.getMessage());
        }
    }

    public List<Task> getTasks(){
        return tasks;
    }



}

