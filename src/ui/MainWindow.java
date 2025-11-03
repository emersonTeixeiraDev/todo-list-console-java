package ui;

import model.Task;
import service.TaskManager;

import javax.swing.*;
import java.awt.*;

/**
 * Interface gráfica principal do aplicativo To-Do List.
 * Permite adicionar, editar, remover, concluir e filtrar tarefas.
 */
public class MainWindow extends JFrame {
    private final TaskManager manager;
    private final DefaultListModel<Task> listModel;
    private final JList<Task> taskList;
    private final JTextField taskInput;
    private final JComboBox<String> filterBox;

    /**
     * Construtor da janela principal.
     * @param manager instância do gerenciador de tarefas.
     */
    public MainWindow(TaskManager manager) {
        this.manager = manager;

        // --- Configuração básica da janela ---
        setTitle("✅ To-Do List - Versão Avançada");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Lista de tarefas ---
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // --- Campo de entrada e botão "Adicionar" ---
        taskInput = new JTextField();
        JButton addButton = new JButton("Adicionar");

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // --- Botões de ação ---
        JButton doneButton = new JButton("Concluir");
        JButton editButton = new JButton("Editar");
        JButton removeButton = new JButton("Remover");

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(doneButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);

        // --- Filtro de exibição ---
        filterBox = new JComboBox<>(new String[]{"Todas", "Pendentes", "Concluídas"});
        filterBox.addActionListener(e -> refreshList());

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(new JLabel("Filtro:"), BorderLayout.WEST);
        topPanel.add(filterBox, BorderLayout.CENTER);

        // --- Junta campo de texto e botões no mesmo painel inferior ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // --- Adiciona tudo ao frame ---
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Atualiza lista inicial ---
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

    /**
     * Atualiza a lista exibida de acordo com o filtro selecionado.
     */
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

    /**
     * Ponto de entrada da aplicação gráfica.
     */
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        SwingUtilities.invokeLater(() -> new MainWindow(manager).setVisible(true));
    }
}
