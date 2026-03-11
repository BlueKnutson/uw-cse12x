import java.util.*;
import java.io.*;

public class FilesByRecursion extends DisplayFrame {

   public FilesByRecursion(String... prompts) {
      super(prompts);
   }
   
   public static void main(String[] args) {  
      DisplayFrame f = new FilesByRecursion("Enter root directory","Enter file extention");
      f.setTitle("Search File System");
      f.setField(0, "..\\Code2");
      f.setVisible(true);
      
   } // end main
   
   public void run() {
      String directory = getField(0);
      String type = getField(1);
      
      walkin(new File(directory), type);
      
      //String result = String.format("%s %s", getField(0), getField(1) );
      //addOutput(result);
   } // end run 
   
   public void walkin(File dir, String pattern) {
      File[] listFiles = dir.listFiles();
      File file = null;
      if(listFiles != null) {
         for(int i = 0; i < listFiles.length; i++) {
            file = listFiles[i];
            if(file.isDirectory()) {
               walkin(file, pattern);
            } // is a directory
            else {
               if(file.getName().endsWith(pattern)) {
                  addOutput(String.format("%s",file.toString()));
               }
            } // end else
         } // end for loop   
      } // not null     
   } // end walkin()
   
} // end class