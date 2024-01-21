import java.io.File;
import java.util.*;


public class LadderGame {
    private HashMap<Integer, ArrayList<String>> myDictionary;

    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public void play(String start, String end) {
        // Verify that the start/end words are the same length before starting
        if (start.length() != end.length()) {
            System.out.println("Word Lengths are not the Same");
            return;
        }

        // Verify that the start/end words are in the dictionary themselves
//        if (!isInDictionary(start) || !isInDictionary(end)) {
//            System.out.println("Start or End word not in the dictionary");
//            return;
//        }

        // Create an initial ladder with the start word
        WordInfo initialWordInfo = new WordInfo(start, 0);

        // Create an instance of your custom Queue class
        Queue<WordInfo> partialSolutionQueue = new Queue<>();

        // Add the initial ladder to the partial solution queue
        partialSolutionQueue.enqueue(initialWordInfo);

        // While the queue is not empty and the word ladder is not complete
        while (!partialSolutionQueue.isEmpty()) {
            // Remove the first item from the queue (current shortest partial ladder)
            WordInfo currentWordInfo = partialSolutionQueue.dequeue();
            String lastWord = currentWordInfo.getWord();

            // Get one-away words using the provided function
            ArrayList<String> oneAwayWords = oneAway(lastWord, true);

            // For each one-away word
            for (String word : oneAwayWords) {
                // If the word is equal to the end word, then the word ladder is complete
                if (word.equals(end)) {
                    // Display the solution
                    System.out.println(lastWord + " -> " + word + " : " + (currentWordInfo.getMoves() + 1) +
                            " Moves [" + currentWordInfo.getHistory() + " " + word + "] total enqueues " +
                            Queue.getTotalEnqueues());
                    return; // End the loop as a solution is found
                } else {
                    // Extend the current ladder by appending the new word
                    WordInfo newWordInfo = new WordInfo(word, currentWordInfo.getMoves() + 1,
                            currentWordInfo.getHistory() + " " + word);

                    // Add this new ladder to the queue
                    partialSolutionQueue.enqueue(newWordInfo);
                }
            }
        }

        // If a solution was not found, indicate that a solution couldn't be found
        System.out.println("No word ladder found.");
    }





    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        ArrayList<String> words = new ArrayList<>(myDictionary.get(word.length()));
        char[] mainWordSplit = word.toCharArray();
        ArrayList<String> wordsToRemove = new ArrayList<>();
        ArrayList<String> wordsToKeep = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            int totalDifs = 0;
            char[] currentWordSplit = words.get(i).toCharArray();

            for (int j = 0; j < word.length(); j++) {
                if (mainWordSplit[j] != currentWordSplit[j]) {
                    totalDifs++;
                }
            }

            if (totalDifs > 1) {
                wordsToRemove.add(words.get(i));
            }else {
                wordsToKeep.add(words.get(i));
            }
        }

        if (withRemoval) {
            myDictionary.get(word.length()).removeAll(wordsToKeep);
        }

        // Remove one-away words from the 'words' list
        words.removeAll(wordsToRemove);
        return words;
    }




    public void listWords(int length, int howMany) {
        List<String> toPrint = myDictionary.get(length).subList(0, Math.min(myDictionary.get(length).size(), howMany));
        for(String words : toPrint){
            System.out.println(words);
        }
    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            HashMap<Integer, ArrayList<String>> myDictionary = new HashMap<>();
            for (int i = 2; i <= longestWord; i++){
                myDictionary.put(i, new ArrayList<String>());
            }
            for (int i = 0; i < allWords.size(); i++){
                String currentWord = allWords.get(i);
                int lenOfCurrent = currentWord.length();
                myDictionary.get(lenOfCurrent).add(currentWord);
            }
            this.myDictionary = myDictionary;

        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }
}