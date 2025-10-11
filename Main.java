import java.io.*;
import java.util.*;

public class ToDoList {
    private static List<String> tasks = new ArrayList<>();
    private static final String FILE_NAME = "tasks.txt";
    private static Scanner scanner = new Scanner(System.in);

    // Códigos de cor ANSI
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        loadTasks();
        boolean running = true;

        while (running) {
            System.out.println(YELLOW + "\n===== GERENCIADOR DE TAREFAS =====" + RESET);
            System.out.println("1. Adicionar tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Marcar tarefa como concluída");
            System.out.println("4. Excluir tarefa");
            System.out.println("5. Sair");

            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addTask();
                    break;
                case "2":
                    listTasks();
                    break;
                case "3":
                    completeTask();
                    break;
                case "4":
                    deleteTask();
                    break;
                case "5":
                    running = false;
                    saveTasks();
                    System.out.println(GREEN + "Saindo do programa..." + RESET);
                    break;
                default:
                    System.out.println(RED + "Opção inválida!" + RESET);
                    break;
            }
        }
    }

    private static void addTask() {
        System.out.print("Digite a nova tarefa: ");
        String task = scanner.nextLine();
        tasks.add(task + " [❌]");
        System.out.println(GREEN + "Tarefa adicionada!" + RESET);
        saveTasks();
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println(RED + "Nenhuma tarefa cadastrada!" + RESET);
        } else {
            System.out.println(YELLOW + "\nTAREFAS:" + RESET);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void completeTask() {
        listTasks();
        if (!tasks.isEmpty()) {
            System.out.print("Número da tarefa concluída: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < tasks.size()) {
                String task = tasks.get(index).replace("[❌]", "[✅]");
                tasks.set(index, task);
                System.out.println(GREEN + "Tarefa marcada como concluída!" + RESET);
                saveTasks();
            } else {
                System.out.println(RED + "Tarefa inválida!" + RESET);
            }
        }
    }

    private static void deleteTask() {
        listTasks();
        if (!tasks.isEmpty()) {
            System.out.print("Número da tarefa a excluir: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.remove(index);
                System.out.println(GREEN + "Tarefa excluída!" + RESET);
                saveTasks();
            } else {
                System.out.println(RED + "Tarefa inválida!" + RESET);
            }
        }
    }

    private static void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(RED + "Erro ao salvar tarefas!" + RESET);
        }
    }

    private static void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            // O arquivo pode não existir ainda — sem problema
        }
    }
}
