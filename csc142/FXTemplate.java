import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.scene.control.Alert.AlertType;
import java.util.*;
import java.time.*;

public class FXTemplate extends Application {

   private BorderPane root = new BorderPane();
   
   private Button runBtn;
   private TextArea display;
   private int outputCount = 0;
   private StringBuilder output = new StringBuilder(128);
   
   private Parent createContent() {
      root.setPrefSize(800, 600);
      root.setPadding(new Insets(10));

      display = new TextArea();      
      display.setPrefColumnCount(50);
      display.setWrapText(true);
      display.setStyle("""
                        -fx-font-family: monospace;
                        -fx-font-weight: bold;
                        -fx-font-size: 24;
                       """);
                       
      runBtn = new Button("Run");
      runBtn.setOnAction(
          e -> {
              run();
          });
                
      HBox buttonBox = new HBox(10);
      buttonBox.setPadding(new Insets(10));
      buttonBox.getChildren().addAll(runBtn);
      
      root.setTop(buttonBox);
      root.setCenter(display);
                
      return root;
   } // end createContent()
   
   @Override
   public void start(Stage stage) {
      stage.setTitle("FXTemplate"); 
      Scene scene = new Scene(createContent());
      stage.setScene(scene);        
      stage.show();
   } // end start()
   
   public void run() {

   }
   
   private Optional<String> getDialogText(String prompt){
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Dialog");
      dialog.setHeaderText(prompt);
      Optional<String> text = dialog.showAndWait();
      return text;
   }
   public String input(String prompt) {
      Optional<String> text = getDialogText(prompt);
      return text.orElse("");
   }
   public int inputInt(String prompt) {
      Optional<String> text = getDialogText(prompt);
      if(text.isPresent()) {
         try {
            return Integer.parseInt(text.get());
         } 
         catch (Exception e) { 
            showMessage(String.format("%s cannot be converted to an int", text.get()));
            return 0; }
      } else 
         return -1;
   } // end inputInt() 
   public double inputDouble(String prompt) {
      Optional<String> text = getDialogText(prompt);
      if (text.isPresent()) {
         try {
            return Double.parseDouble(text.get());
         } catch (Exception e) {
            showMessage(String.format("%s cannot be converted to an double",text.get()));
            return 0;
         }
      } else
         return -1;
   } // end inputDouble() 

   public void showMessage(String message) {
      Alert alert = new Alert(AlertType.INFORMATION, message);
      alert.showAndWait();
   } // end showMessage() 
   
   private void output(Object value) {
      String stringValue = String.valueOf(value);
      if (!stringValue.equals("")) {
         output.append(stringValue);
         updateOutput();
      } // end if
   } // end output() 

   private void outputln(Object value) {
      String stringValue = String.valueOf(value);
      if (!stringValue.equals("")) {
         output.append(stringValue).append("\n");
         updateOutput();
      } // end if
   } // end outputln()

   private void outputln() {
      output.append("\n");
      updateOutput();
   } // end outputln()

   private void updateOutput() {
      display.setText(output.toString());
      outputCount++;
   } // end updateOutput() 
     
   public static void main(String[] args) {
      launch(args);
   } // end main
   
} // end class