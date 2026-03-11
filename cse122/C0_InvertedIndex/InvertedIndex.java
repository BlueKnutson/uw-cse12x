import java.util.*;

public class InvertedIndex {
    public static void main(String[] args) {
        List<Media> docs = List.of(
            new Book("Mistborn", List.of("Brandon Sanderson"),
                     new Scanner("Epic fantasy worldbuildling content")),
            new Book("Farenheit 451", List.of("Ray Bradbury"),
                     new Scanner("Realistic \"sci-fi\" content")),
            new Book("The Hobbit", List.of("J.R.R. Tolkein"),
                     new Scanner("Epic fantasy quest content"))
        );
        
        Map<String, Set<Media>> result = createIndex(docs);
        System.out.println(docs);
        System.out.println();
        System.out.println(result);
    }

    // TODO: Write and document your createIndex method here
    public static Map<String, Set<Media>> createIndex(List<Media> docs) {
        Map<String, Set<String>> mediaMap = new TreeMap<>();

        for(Media media: docs){
            Set<String> content = new HashSet<>();
            content = media.getContent();

            for(String word: content){
                if(!mediaMap.contains(word)){
                    mediaMap.put(word, media.toString());
                }
                else{
                    mediaMap.put(word, mediaMap.get(word)+", "+media.toString());
                }
            }
           


        }
        return mediaMap;
    }
}
