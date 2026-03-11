import java.util.*;
import java.util.Scanner;
public class Book implements Media, Comparable<Book>{

    private String title;
    private List<String> authors;
    private List<String> content;
    private List<Integer> ratings;

    public Book(String title, List<String> authors, Scanner contentScanner){
        this.title = title;
        this.authors = authors;
        this.content = new ArrayList<>();
        this.ratings = new ArrayList<>();

        while (contentScanner.hasNext()) {
            content.add(contentScanner.next());
        }        
    }

    public String getTitle(){
        return this.title;
    }

    /**
     * Gets all artists associated with this media.
     *
     * @return      A list of artists for this media.
     */
    public List<String> getArtists(){
        return this.authors;
    }

    /**
     * Adds a rating to this media.
     *
     * @param score     The score for the new rating. Should be non-negative.
     */
    public void addRating(int score){
        if(score >= 0){
            ratings.add(score);
        }
    }

    /**
     * Gets the number of times this media has been rated.
     *
     * @return      The number of ratings for this media.
     */
    public int getNumRatings(){
        return ratings.size();
    }

    /**
     * Gets the average (mean) of all ratings for this media.
     *
     * @return      The average (mean) of all ratings for this media. 
     *              If no ratings exist, returns 0.
     */
    public double getAverageRating(){

        if(ratings.isEmpty()){
            return 0;
        }

        double total = 0;
        for(double rate: ratings){
            total += rate;
        }
        double mean = total / ratings.size();

        return mean;
    }

    /**
     * Gets all of the content contained in this media.
     *
     * @ returns    The content stored in a List of strings. If there is no content, an empty list
     */
    public List<String> getContent(){
        return this.content;
    }

    /**
    * Produce a readable string representation. of this media
    *
    * If the media has zero ratings, the format will be:
    * "<title> by [<artists>]"
    * 
    * If the media has at least one review, the format will be:
    * "<title> by [<artists>]: <average rating> (<num ratings> ratings)"
    *
    * The average rating displayed will be rounded to at most two decimal places.
    *
    * @ returns     The appropriately formatted string representation
    */
    public String toString(){

        String result = this.title + " by " + this.authors;


        if(ratings.size() == 0){
            return result;
        }

        result += ": " + String.format("%.2f", getAverageRating()) + " (" + getNumRatings() + " ratings)";
        return result;
    }

    public int compareTo(Book other){
        if (this.getAverageRating()>other.getAverageRating()){
            return 1;
        }
        else if (this.getAverageRating()<other.getAverageRating()){
            return -1;
        }
        return 0;
    }
}