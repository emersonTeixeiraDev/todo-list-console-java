package ui;

import model.Task;
import service.TaskManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainWindow extends JFrame {
    private final TaskManager manager;
    private final DefaultListModel<Task> listModel;
    private final JList<Task> taskList;
    private final JTextField taskInput;

    public MainWindow(TaskManager manager) {
        this.manager = manager;

        setTitle("To-Do List - GUI Version");
        setSize(400,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //layout
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        taskInput = new JTextField();

        JButton addButton = new JButton("Adicionar");
        JButton doneButton = new JButton("Concluir");
        JButton removeTaskButton = new JButton("Remover");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInput,  BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(doneButton);
        buttonsPanel.add(removeTaskButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        refreshList();

        //ações
        addButton.addActionListener(e -> {
         String desc = taskInput.getText().trim();
         if (!desc.isEmpty()) {
             manager.addTask(desc);
             taskInput.setText("");
             refreshList();
         }
        });

        doneButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                manager.markDone(selected.getId());
                refreshList();
            }
        });

        removeTaskButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                manager.removeTasks(selected.getId());
                refreshList();
            }
        });
    }

    private void refreshList() {
        listModel.clear();
        for (Task t : manager.getTasks()) {
            listModel.addElement(t);
        }
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        SwingUtilities.invokeLater(() -> new MainWindow(manager).setVisible(true));
    }

}
