package ui;

import model.Task;
import service.TaskManager;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final TaskManager manager;
    private final DefaultListModel<Task> listModel;
    private final JList<Task> taskList;
    private final JTextField taskInput;
    private final JComboBox<String> filterBox;

    public MainWindow(TaskManager manager) {
        this.manager = manager;

        setTitle("✅ To-Do List - Versão Avançada");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Lista de tarefas
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Campo de texto e botões
        taskInput = new JTextField();
        JButton addButton = new JButton("Adicionar");
        JButton doneButton = new JButton("Concluir");
        JButton removeButton = new JButton("Remover");
        JButton editButton = new JButton("Editar");

        // Filtro de exibição
        filterBox = new JComboBox<>(new String[]{"Todas", "Pendentes", "Concluídas"});
        filterBox.addActionListener(e -> refreshList());

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(doneButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(new JLabel("Filtro:"), BorderLayout.WEST);
        topPanel.add(filterBox, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(buttonsPanel, BorderLayout.PAGE_END);

        refreshList();

        // --- Ações dos botões ---
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

        removeButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                manager.removeTasks(selected.getId());
                refreshList();
            }
        });

        editButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                String newDesc = JOptionPane.showInputDialog(this,
                        "Editar descrição:",
                        selected.getDescription());
                if (newDesc != null && !newDesc.trim().isEmpty()) {
                    manager.editTask(selected.getId(), newDesc.trim());
                    refreshList();
                }
            }
        });
    }

    private void refreshList() {
        listModel.clear();

        String filter = (String) filterBox.getSelectedItem();
        java.util.List<Task> tasksToShow;

        if ("Pendentes".equals(filter)) {
            tasksToShow = manager.getPendingTasks();
        } else if ("Concluídas".equals(filter)) {
            tasksToShow = manager.getCompletedTasks();
        } else {
            tasksToShow = manager.getTasks();
        }

        for (Task t : tasksToShow) {
            listModel.addElement(t);
        }
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        SwingUtilities.invokeLater(() -> new MainWindow(manager).setVisible(true));
    }
}
