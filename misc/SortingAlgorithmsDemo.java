package edu.wvc;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.*;
import java.io.*;
import java.nio.file.Files;
import java.text.*;
import java.time.*;
import java.util.*;

public class SortingAlgorithmsDemo extends Application {

    private final BorderPane root = new BorderPane();

    private final Button runBtn = new Button("Run");
    private final Button clearBtn = new Button("Clear Output");
    private final Button printBtn = new Button("Print");
    private final TextArea display = new TextArea();
    private final Label status = new Label("Status");
    private final StringBuilder output = new StringBuilder(128);
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent content = new ClipboardContent();
    private final GridPane form = new GridPane();
    private final Canvas canvas = new Canvas(600, 600);
    private final SecureRandom random = new SecureRandom();
    private final Label instructions = new Label();
    private final NumberFormat currency = NumberFormat.getCurrencyInstance();

    // form variables declared
    private String appTitle;
    private Label[] labels;
    private TextField[] fields;
    private int rows;
    private int nextRow;

    private int outputCount;

    private final HBox buttonBox = new HBox(10);

    private Parent createContent() {
        root.setPrefSize(800, 600);
        root.setPadding(new Insets(10));

        display.setPrefColumnCount(50);
        display.setWrapText(true);
        display.setEditable(false);
        display.setStyle("""
                -fx-font-family: monospace;
                -fx-font-weight: bold;
                -fx-font-size: 18;
                """);
        runBtn.setOnAction(
                e -> {
                    try {
                        run();
                    } catch (Exception ex) {
                        println(ex);
                    }
                });
        clearBtn.setOnAction(e -> clearOutput());
        printBtn.setOnAction(
                e -> {
                    content.putString(display.getText());
                    clipboard.setContent(content);
                    String filename = appTitle + ".txt";
                    try {
                        writeToFile(display.getText(), new File(filename));
                        getHostServices().showDocument(filename);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
        sel.setOnAction(
                e -> {
                    if (sel.getValue().isBlank())
                        return;
                    root.setStyle("-fx-background-color:%s".formatted(sel.getValue()));
                });

        buttonBox.getChildren().addAll(runBtn, clearBtn, printBtn, sel);

        root.setTop(form);
        root.setCenter(display);
        root.setBottom(status);
        return root;
    } // end createContent()

    @Override
    public void start(Stage stage) {
        try {
            setup();
        } catch (Exception e) {
            println(e);
        }
        form.add(buttonBox, 1, nextRow++);
        stage.setTitle(appTitle);
        Scene scene = new Scene(createContent());
        stage.setScene(scene);
        stage.show();
    }

    // Replace "String" below with the object type that populates the ComboBox
    private final ObservableList<String> obl = FXCollections.observableArrayList();
    private final ComboBox<String> sel = new ComboBox<>(obl);

    private void setup() throws Exception {

        setFormPrompts("Enter number of array elements",
                "What value do you want to find after sorting?");
        appTitle = "App";
        obl.add("Bubble Sort");
        obl.add("Recursive Bubble Sort");
        obl.add("Selection Sort");
        obl.add("Merge Sort");
        obl.add("Quick Sort");
        sel.getSelectionModel().selectFirst();
    } // end setup

    // global variables
    long startTime = 0;
    long endTime = 0;
    int[] nums;

    private void run() throws Exception {
        int size = Integer.parseInt(getField(0));
        int key = Integer.parseInt(getField(1));
        String selection = sel.getValue();
        clearOutput();
        generateRandomIntArray(size);
        clickWatch();
        switch (selection) {
            case "Bubble Sort" -> bubbleSort(nums);
            case "Recursive Bubble Sort" -> bubbleSort(nums, nums.length);
            case "Selection Sort" -> selectionSort(nums);
            case "Merge Sort" -> mergeSort(nums, nums.length - 1);
            case "Quick Sort" -> quickSort(nums, 0, nums.length - 1);
        } // end switch()
        clickWatch();
        outputln(selection + " done.");
        int index = binarySearch(nums, key, 0, nums.length - 1);
        String result = "Value not found in the array";
        if (index != -1)
            result = String.format("Value %s found at index %d", key, index);
        outputln(result);
        //outputln(binarySearchR(nums, key, 0, nums.length-1));
    } // end run()

    // user defined helper methods
    public int binarySearch(int[] a, int key, int low, int high) {
        int index = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] < key) {
                low = mid + 1;
            } else if (a[mid] > key) {
                high = mid - 1;
            } else if (a[mid] == key) {
                index = mid;
                break;
            }
        } // end while
        return index;
    } // end binarySearch()

    public int binarySearchR(int[] a, int key, int low, int high) {
        int mid = (low + high) / 2;
        if (high < low) {
            return -1;
        }
        if (key == a[mid]) {
            return mid;
        } else if (key < a[mid]) {
            return binarySearchR(a, key, low, mid - 1);
        } else {
            return binarySearchR(a, key, mid + 1, high);
        }
    } // end binarySearchR()

    public void bubbleSort(int[] input) {
        int inputLength = input.length;
        int temp;
        boolean is_sorted;
        int counter = 0;
        for (int i = 0; i < inputLength; i++) {
            is_sorted = true;
            for (int j = 1; j < (inputLength - i); j++) {
                if (input[j - 1] > input[j]) {
                    temp = input[j - 1];
                    input[j - 1] = input[j];
                    input[j] = temp;
                    is_sorted = false;
                }
            }
            if (is_sorted) // is sorted? then break it, avoid useless loop.
                break;
            counter++;
        } // end outer for loop
        outputln("Outer loop iterations: " + counter);
    } // end bubbleSort

    // A function to implement bubble sort using recursion
    void bubbleSort(int[] a, int n) {
        if (n == 1) { // Base case
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            if (a[i] > a[i + 1]) {
// swap a[i], a[i+1]
                int temp = a[i];
                a[i] = a[i + 1];
                a[i + 1] = temp;
            }
        }
        bubbleSort(a, n - 1); // with array size of one less
    }

    public int[] selectionSort(int[] a) {
        int num = a.length;
        for (int i = 0; i < num - 1; i++) {
            int index = i;
            for (int j = i + 1; j < num; j++)
                if (a[j] < a[index])
                    index = j;
            int smallest = a[index];
            a[index] = a[i];
            a[i] = smallest;
        }
        return a;
    } // end selectionSort()

    private void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    } // end mergeSort()

    private void merge(int[] a, int[] l, int[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    } // end merge()

    private void quickSort(int a[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(a, begin, end);
            quickSort(a, begin, partitionIndex - 1);
            quickSort(a, partitionIndex + 1, end);
        }
    } // end quickSort()

    private int partition(int a[], int begin, int end) {
        int pivot = a[end];
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (a[j] <= pivot) {
                i++;
                int swapTemp = a[i];
                a[i] = a[j];
                a[j] = swapTemp;
            }
        }
        int swapTemp = a[i + 1];
        a[i + 1] = a[end];
        a[end] = swapTemp;
        return i + 1;
    } // end partition()

    private void clickWatch() {
        if (startTime == 0)
            startTime = System.nanoTime();
        else {
            endTime = System.nanoTime();
            outputln(String.format("Elapsed time: %d seconds",
                    (endTime - startTime) / 1_000_000_000));
            startTime = 0;
        }
    } // end clickWatch()

    private void generateRandomIntArray(int size) {
        nums = new int[size];
        for (int i = 0; i < size; i++)
            nums[i] = getRandom(size);
    } // end generateRandomIntArray()

    private String readFirstLineFromFile(String path) throws IOException {
        try (var fr = new FileReader(path); var br = new BufferedReader(fr)) {
            return br.readLine();
        }
    }

    private String getType(Object o) {
        return o.getClass().getSimpleName();
    }

    private void showJsonInBrowser(String json) {
        String jsonViewer = "https://codebeautify.org/jsonviewer?input=" + json;
        println(jsonViewer);
        getHostServices().showDocument(jsonViewer);
    }

    private Optional<String> getDialogText(String prompt) {
        var dialog = new TextInputDialog();
        dialog.setTitle("Dialog");
        dialog.setHeaderText(prompt);
        return dialog.showAndWait();
    }

    private String input(String prompt) {
        var text = getDialogText(prompt);
        return text.orElse("");
    }

    private int inputInt(String prompt) {
        try {
            return Integer.parseInt(input(prompt));
        } catch (Exception e) {
            return -1;
        }
    }

    private double inputDouble(String prompt) {
        try {
            return Double.parseDouble(input(prompt));
        } catch (Exception e) {
            return -1;
        }
    }

    private char inputChar(String prompt) {
        try {
            return input(prompt).charAt(0);
        } catch (Exception e) {
            return '.';
        }
    }

    private String[] getLinesFromFile(String fileName) {
        var lines = readListFromFile(fileName);
        return lines.toArray(new String[0]);
    }

    private ArrayList<String> readListFromFile(String fileName) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            lines = (ArrayList<String>) Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            println(String.format("%d lines read", lines.size()));
        } catch (IOException e) {
            showMessage(e.getMessage());
        }
        return lines;
    }

    private void writeToFile(String string, File file) throws IOException {
        try (var reader = new BufferedReader(new StringReader(string))) {
            try (var writer = new PrintWriter(new FileWriter(file))) {
                reader.lines().forEach(writer::println);
            }
        }
    }

    private String insertLineBreaks(String text, int max) {
        var sb = new StringBuilder(text);
        int i = 0;
        while ((i = sb.indexOf(" ", i + max)) != -1)
            sb.replace(i, i + 1, "\n");
        return sb.toString();
    }

    private void showMessage(String message) {
        var alert = new Alert(AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    private void output(Object value) {
        var stringValue = String.valueOf(value);
        if (stringValue.equals(""))
            return;
        output.append(stringValue);
        updateOutput();
    }

    private void outputln(Object value) {
        var stringValue = String.valueOf(value);
        if (stringValue.equals(""))
            return;
        output.append(stringValue).append("\n");
        updateOutput();
    }

    private void outputln() {
        output.append("\n");
        updateOutput();
    }

    private void updateOutput() {
        display.setText(output.toString());
        outputCount++;
    }

    private void clearOutput() {
        output.setLength(0);
        outputCount = 0;
        display.setText(output.toString());
    }

    private void clear() {
        for (var field : fields)
            field.setText("");
    }

    private void clearField(int index) {
        if (isValidIndex(index))
            fields[index].setText("");
    }

    private TextField getTextField(int index) {
        return isValidIndex(index) ? fields[index] : null;
    }

    private String getField(int index) {
        return isValidIndex(index) ? fields[index].getText() : "";
    }

    private void setField(int index, String value) {
        if (isValidIndex(index))
            fields[index].setText(value);
    }

    private void setLabel(int index, String value) {
        if (isValidIndex(index))
            labels[index].setText(value);
    }

    private void setFormInstructions(String value) {
        instructions.setText(value);
    }

    private void setFormPrompts(String... prompts) {
        createForm(prompts);
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < rows;
    }

    private void createForm(String... prompts) {
        createForm(Pos.CENTER_LEFT, HPos.RIGHT, prompts);
    }

    private void createForm(Pos alignment, HPos labelAlign, String[] prompts) {
        form.add(instructions, 1, 0);
        form.setAlignment(alignment);
        var column1 = new ColumnConstraints();
        column1.setHalignment(labelAlign);
        form.getColumnConstraints().add(column1);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));
        rows = prompts.length;
        nextRow = rows + 1;
        fields = new TextField[rows];
        labels = new Label[rows];

        for (int i = 1; i <= rows; i++) {
            String label = prompts[i - 1] + ":";
            fields[i - 1] = new TextField();
            if (label.startsWith("p-")) {
                label = label.substring(2);
                fields[i - 1] = new PasswordField();
            }
            labels[i - 1] = new Label(label);
            fields[i - 1].setMaxWidth(720);
            fields[i - 1].setPrefWidth(400);
            form.add(labels[i - 1], 0, i);
            form.add(fields[i - 1], 1, i);
        }
    } // end createForm

    private int getRandom(int max) {
        return random.nextInt(max);
    }

    private int getRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private void println() {
        System.out.println();
    }

    private void print(Object value) {
        System.out.print(value);
    }

    private void println(Object value) {
        System.out.println(value);
    }

    public static void main(String[] args) {
        launch(args);
    } // end main
} // end class
