// TODO: Write your implementation to CaesarKey here!

import java.util.HashSet;
import java.util.Set;

public class CaesarKey extends Substitution{


/*
 IllegalArgumentException if the given key meets any of the following cases:
Is null
Is the empty String
Contains a duplicate character
Any individual character falls outside the encodable range (< Cipher.MIN_CHAR or > Cipher.MAX_CHAR)
public 
*/
 
    public CaesarKey(String key){
        if(key == null || key.length() == 0){
            throw new IllegalArgumentException();
        }
        Set<Character> dupCheck = new HashSet<>();

        for(int i = 0; i<key.length(); i++){
            dupCheck.add(key.charAt(i));
            if((int)(key.charAt(i)) > MAX_CHAR || (int)(key.charAt(i))< MIN_CHAR){
                throw new IllegalArgumentException();
            }
        }

        if(dupCheck.size() != key.length()){
            throw new IllegalArgumentException();
        }

        String code = "";
        code += key;

        for (int j =0; j<TOTAL_CHARS; j++){
            if (!(code.contains(String.valueOf((char) MIN_CHAR+j)))){
                code+=(char) MIN_CHAR+j;
            }
        }
        setEncoding(code);
    }
}
