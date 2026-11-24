import java.util.*;
import java.io.*;

public class Main {
    static LinkedList<String> document = new LinkedList<>();
    static Stack<LinkedList<String>> undoStack = new Stack<>();
    static Stack<LinkedList<String>> redoStack = new Stack<>();
    static final String FILE_NAME = "document.txt";

    static void saveState() {
        undoStack.push(new LinkedList<>(document));
        redoStack.clear();
    }

    // Add a new line
    static void addLine(Scanner sc) {
        System.out.print("Enter text: ");
        String line = sc.nextLine();
        saveState();
        document.add(line);
        System.out.println("Line added.");
    }

    // Delete last line
    static void deleteLastLine() {
        if (document.isEmpty()) {
            System.out.println("Document is empty.");
            return;
        }
        saveState();
        document.removeLast();
        System.out.println("Last line deleted.");
    }

    // Undo operation
    static void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        redoStack.push(new LinkedList<>(document));
        document = undoStack.pop();
        System.out.println("Undo done.");
    }

    // Redo operation
    static void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        undoStack.push(new LinkedList<>(document));
        document = redoStack.pop();
        System.out.println("Redo done.");
    }

    // Display document
    static void display() {
        if (document.isEmpty()) {
            System.out.println("Document is empty.");
            return;
        }
        System.out.println("Document:");
        int i = 1;
        for (String line : document) {
            System.out.println(i + ". " + line);
            i++;
        }
    }

    // Load document from file
    static void loadFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            document.clear();
            String line;
            while ((line = br.readLine()) != null) document.add(line);
            System.out.println("Document loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading document.");
        }
    }

    // Save document to file
    static void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String line : document) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Document saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving document.");
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFile();
        int choice = 0;
        do {
            System.out.println("\n===== UNDO/REDO TEXT EDITOR =====");
            System.out.println("1. Add Line");
            System.out.println("2. Delete Last Line");
            System.out.println("3. Undo");
            System.out.println("4. Redo");
            System.out.println("5. Display Document");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                if (!sc.hasNextLine()) {
                    choice = 6;
                    saveFile();
                    System.out.println("Exiting Text Editor.");
                    break;
                }
                System.out.println("Invalid option. Choose 1 to 6.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addLine(sc);
                    break;
                case 2:
                    deleteLastLine();
                    break;
                case 3:
                    undo();
                    break;
                case 4:
                    redo();
                    break;
                case 5:
                    display();
                    break;
                case 6:
                    saveFile();
                    System.out.println("Exiting Text Editor.");
                    break;
                default:
                    System.out.println("Invalid option. Choose 1 to 6.");
            }
        } while (choice != 6);
        sc.close();
    }
}
