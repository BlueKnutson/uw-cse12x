// TODO: Write your implementation to Subsitution here!
import java.util.*;

public class Substitution extends Cipher {
    
    public String encoding;
    public Map<Character, Character> encodingMap = new HashMap<>();
    public Substitution(){
        
    }

    public Substitution(String encoding){
        
        this.setEncoding(encoding);
    }

    public void setEncoding(String encoding){
        if (encoding == null || encoding.length() != TOTAL_CHARS){
            throw new IllegalArgumentException();
        }
        
        Set<Character> dupCheck = new HashSet<>();

        for(int i = 0; i<encoding.length(); i++){
            dupCheck.add(encoding.charAt(i));
            if((int)(encoding.charAt(i)) > MAX_CHAR || (int)(encoding.charAt(i))< MIN_CHAR){
                throw new IllegalArgumentException();
            }
        }

        if(dupCheck.size() != encoding.length()){
            throw new IllegalArgumentException();
        }

        this.encoding = encoding;

        for( int j = 0; j < TOTAL_CHARS; j++){
            encodingMap.put((char) (MIN_CHAR+j), encoding.charAt(j));
        }




    }
// in between 65 and 95
    public String decrypt(String encryptedWord){
        if(encoding == null){
            throw new IllegalStateException();
        }    
        if(encryptedWord == null){
            throw new IllegalArgumentException();
        }    
        String word = "";

        for(int i = 0; i<encryptedWord.length(); i++){
            for(Character key:encodingMap.keySet()){
                Character currChar = encryptedWord.charAt(i);
                if(encodingMap.get(key) == currChar){
                    word += key;
                }
            }
        }

        return word;
    }

    public String encrypt(String word){
        if(encoding == null){
            throw new IllegalStateException();
        }
        if(word == null){
            throw new IllegalArgumentException ();
        }
        String encryptedWord = "";
        for(int i = 0; i<word.length(); i++){
            encryptedWord += encodingMap.get(word.charAt(i));
        }


        return encryptedWord;
    }

}