import java.text.SimpleDateFormat;
import java.util.Date;

public class Repository {
    private Commit head;
    private String repositoryName;
    private int repositorySize;

    /*
     * Create a new, empty repository with the specified name
     * 
     * If the name is null or empty, throw an IllegalArgumentException
     */
    public Repository(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException();
        }

        repositoryName = name;
    }

    /*
     * Return the ID of the current head of this repository.
     * 
     * If the head is null, return null
     */
    public String getRepoHead() {// 0(1){}
        if (head == null) {
            return null;
        }
        return head.id;
    }

    /*
     * Return the number of commits in the repository
     */
    public int getRepoSize() {
        return repositorySize;
    }

    /*
     * Return a string representation of this repository in the following format:
     * 
     * <name> - Current head: <head>
     * 
     * <head> should be the result of calling toString() on the head commit.
     * 
     * If there are no commits in this repository, instead return <name> - No
     * commits
     */
    public String toString() { // 0(1)
        if (head == null) {
            return (repositoryName + " - No commits");
        }
        return (repositoryName+" Current head: " + head.toString());
    }

    /*
     * Return true if the commit with ID targetId is in the repository, false if
     * not.
     * 
     * Throws an IllegalArgumentException if targetId is null
     * 
     * Note that all elements are unique. Therefore, it should not continue looping
     * unnecessarily once the element of interest is found.
     */
    public boolean contains(String targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException();
        }
        if (head == null) {
            return false;
        }
        Commit current = head;
        while (current != null) {
            if (current.id.equals(targetId)) {
                return true;
            }
            current = current.past;
        }
        return false;

    }

    /*
     * Return a string consisting of the String representations of the most recent n
     * commits in this repository, with the most recent first. Commits should be
     * separated by a newline (\n) character.
     * 
     * If there are fewer than n commits in this repository, return them all.
     * 
     * If there are no commits in this repository, return the empty string.
     * 
     * If n is non-positive, throw an IllegalArgumentException.
     */
    public String getHistory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
    
        String result = "";
        Commit curr = head;
    
        for (int i = 0; i < n && curr != null; i++) {
            if (!result.isEmpty()) { 
                result += "\n"; // Add newline between commits
            }
            result += curr.toString();
            curr = curr.past;
        }
    
        return result;
    }
    

    /*
     * Create a new commit with the given message, add it to this repository.
     * 
     * The new commit should become the new head of this repository, preserving the
     * history behind it.
     * 
     * Throws an IllegalArgumentException if message is null
     * 
     * Return the ID of the new commit.
     */
    public String commit(String message) {// 0(1)
        if (message == null) {
            throw new IllegalArgumentException();
        }
        if (head == null) {
            head = new Commit(message);
            repositorySize++;
            return head.id;
        }
        Commit temp = head;
        head = new Commit(message, temp);
        repositorySize++;
        return head.id;
    }
    /*
      * Remove the commit with ID targetId from this repository, maintaining the rest
      * of the history.
      * 
      * Throws an IllegalArgumentException if targetId is null
      * 
      * Returns true if the commit was successfully dropped, and false if there is no
      * commit that matches the given ID in the repository.
      * 
      * Note that all elements are unique. Therefore, it should not continue looping
      * unnecessarily once the element of interest is found.
      */

    public boolean drop(String targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException();
        }

        if (head == null) {
            return false;
        }

        if (head.id.equals(targetId)) {
            head = head.past;
            repositorySize--;
            return true;
        }

        Commit curr = head;
        while (curr.past != null) {
            if (curr.past.id.equals(targetId)) {
                curr.past = curr.past.past;
                repositorySize--;
                return true;
            }
            curr = curr.past;
        }
        return false;

    }

    /*
     * Takes all the commits in the other repository and moves them into this
     * repository, combining the two repository histories such that chronological
     * order is preserved. That is, after executing this method, this repository
     * should contain all commits that were from this and other, and the commits
     * should be ordered in timestamp order from most recent to least recent.
     * 
     * If the other repository is null, throw an IllegalArgumentException
     * 
     * If the other repository is empty, this repository should remain unchanged.
     * 
     * If this repository is empty, all commits in the other repository should be
     * moved into this repository.
     * 
     * At the end of this method's execution, other should be an empty repository in
     * all cases.
     * 
     * You should not construct any new Commit objects to implement this method. You
     * may however create as many references as you like.
     */
    public void synchronize(Repository other) {
        if (other == null) {
            throw new IllegalArgumentException();
        }
    
        // If the other repository is empty, do nothing.
        if (other.head == null) {
            return;
        }
    
        // If this repository is empty, take all commits from other.
        if (this.head == null) {
            this.head = other.head;
            this.repositorySize = other.repositorySize;
            other.head = null;
            other.repositorySize = 0;
            return;
        }
    
        // --- Step 1: Initialize pointers for merging ---
        Commit a = this.head;
        Commit b = other.head;
        Commit mergedHead = null;
        Commit mergedTail = null;
    
        // --- Step 2: Merge the two sorted lists in ascending order ---
        while (a != null && b != null) {
            Commit next;
            if (a.timeStamp <= b.timeStamp) { // Pick the earlier commit
                next = a;
                a = a.past;
            } else {
                next = b;
                b = b.past;
            }
    
            if (mergedHead == null) { 
                mergedHead = next;  // Set the first element as the head
            } else {
                mergedTail.past = next;  // Append to the merged list
            }
            mergedTail = next;  // Move tail forward
        }
    
        // Append the remaining commits from whichever list is not yet exhausted.
        if (a != null) {
            mergedTail.past = a;
        } else {
            mergedTail.past = b;
        }
    
        // Ensure the last commit in the list points to null (end of history)
        mergedTail.past = null;
    
        // --- Step 3: Update repository information ---
        this.head = mergedHead; // New head is the commit with the lowest timestamp
        this.repositorySize += other.repositorySize;
    
        // Clear the other repository.
        other.head = null;
        other.repositorySize = 0;
    }
    
    


    public static class Commit {
        private static int currentCommitID;
        public final long timeStamp;
        public final String id;
        public final String message;
        public Commit past;

        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        public Commit(String message) {
            this(message, null);
        }

        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
