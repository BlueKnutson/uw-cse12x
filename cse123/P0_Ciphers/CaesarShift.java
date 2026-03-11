// TODO: Write your implementation to CaesarShift here!
import java.util.*;
public class CaesarShift extends Substitution{
    int shift;
    
    public CaesarShift(int shift){
        if(shift <= 0){
            throw new IllegalArgumentException();
        }
        this.shift = shift;
        Shift();
    }

    public void Shift(){
        Queue<Character> caesarQueue = new LinkedList<>();
        
        for(int l = 0; l<TOTAL_CHARS; l++){
            caesarQueue.add((char) (MIN_CHAR + l));
        }

        for(int j = 0; j<shift; j++){
            caesarQueue.add(caesarQueue.remove());
        }
        String encoding = "";
        for(Character currChar: caesarQueue){
            encoding += currChar;
        }
        setEncoding(encoding);
        
    }


}