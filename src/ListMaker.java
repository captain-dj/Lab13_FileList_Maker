import java.io.*;
import java.util.ArrayList;

public class ListMaker {
    private static ArrayList<String> myArrList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = "";

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = SafeInput.getRegexString("[AaDdOoSsVvCcQq]", "Enter your choice: ");

            switch (choice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "V":
                    printList();
                    break;
                case "O":
                    openList();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    running = confirmQuit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the current list");
        System.out.println("Q - Quit");

        // Display current list
        System.out.println("Current List:");
        for (int i = 0; i < myArrList.size(); i++) {
            System.out.println((i + 1) + ". " + myArrList.get(i));
        }
    }

    private static void addItem() {
        String newItem = SafeInput.getRegexString(".+", "Enter item to add: ");
        myArrList.add(newItem);
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }

        System.out.println("Select the item number to delete:");
        int itemToDelete = SafeInput.getRangedInt(1, myArrList.size(), "Enter item number: ");
        myArrList.remove(itemToDelete - 1);
        needsToBeSaved = true;
    }

    private static void printList() {
        if (myArrList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        System.out.println("Current List:");
        for (int i = 0; i < myArrList.size(); i++) {
            System.out.println((i + 1) + ". " + myArrList.get(i));
        }
    }

    private static void openList() {
        if (needsToBeSaved) {
            boolean confirm = SafeInput.getYNConfirm("Current list is unsaved. Do you want to save before loading a new list?");
            if (confirm) {
                saveList();
            }
        }

        String fileName = SafeInput.getRegexString(".+\\.txt", "Enter the file name to open: ");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            myArrList.clear();
            while ((line = reader.readLine()) != null) {
                myArrList.add(line);
            }
            reader.close();
            needsToBeSaved = false;
            currentFileName = fileName;
            System.out.println("List loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }

    private static void saveList() {
        if (myArrList.isEmpty()) {
            System.out.println("Cannot save an empty list.");
            return;
        }

        String fileName;
        if (currentFileName.isEmpty()) {
            fileName = SafeInput.getRegexString(".+\\.txt", "Enter the file name to save: ");
        } else {
            fileName = currentFileName;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (String item : myArrList) {
                writer.write(item + "\n");
            }
            writer.close();
            needsToBeSaved = false;
            currentFileName = fileName;
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }

    private static void clearList() {
        if (!myArrList.isEmpty()) {
            boolean confirm = SafeInput.getYNConfirm("Are you sure you want to clear the list?");
            if (confirm) {
                myArrList.clear();
                needsToBeSaved = true;
                System.out.println("List cleared.");
            }
        } else {
            System.out.println("The list is already empty.");
        }
    }

    private static boolean confirmQuit() {
        if (needsToBeSaved) {
            boolean confirm = SafeInput.getYNConfirm("Current list is unsaved. Do you want to save before quitting?");
            if (confirm) {
                saveList();
            }
        }
        return SafeInput.getYNConfirm("Are you sure you want to quit?");
    }
}