import java.util.*;

public class MultiCipher extends Cipher{
    List<Cipher> ciphers = new ArrayList<>();
    public MultiCipher(List<Cipher> ciphers){
        if(ciphers == null){
            throw new IllegalArgumentException();
        }
        this.ciphers = ciphers;


    }

    public String encrypt(String word){
        String encryptedWord = word;
        for (Cipher cipher: ciphers){
            encryptedWord = cipher.encrypt(encryptedWord);
        }
        return encryptedWord;
    }


    public String decrypt(String encryptedWord){
        String word = encryptedWord;
        Cipher currCipher = new Cipher();
        for(int i = 0; i < ciphers.size(); i++){
            currCipher = ciphers.get(ciphers.size()-i-1);
            word = currCipher.decrypt(word);
        }       
        return word;
    }   
}