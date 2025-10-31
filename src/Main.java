import service.TaskManager;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner sc = new Scanner(System.in);
        int option;

        do{
            System.out.println("\n==== TO-DO LIST ====");
            System.out.println("1. Adicionar tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Marcar como concluída");
            System.out.println("4. Remover tarefa");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            option = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch(option){
                case 1:
                    System.out.println("Descrição: ");
                    String desc = sc.nextLine();
                    taskManager.addTask(desc);
                    break;
                case 2:
                    taskManager.listTasks();
                    break;
                case 3:
                    System.out.println("Id da tarefa: ");
                    int idDone = sc.nextInt();
                    taskManager.markDone(idDone);
                    break;
                case 4:
                    System.out.println("Id da tarefa: ");
                    int idRemove = sc.nextInt();
                    taskManager.removeTasks(idRemove);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }while(option != 0);
            sc.close();
    }
}