import java.io.File;
import java.util.*;
import java.util.Queue;

public class LadderGame {
    private HashMap<Integer, ArrayList<String>> myDictionary;

    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public void play(String start, String end) {
        if(start.length() != end.length()){
            System.out.println("Word Lengths are not the Same");
            return;
        }
//        for(int i = 0; i < myDictionary.get(start.length()); i++){
//            if(start == myDictionary.get(start.length()[i])){
//
//            }
//        }
        WordInfo initialWordInfo = new WordInfo(start, 0);

        // Create a queue to store partial solutions
        Queue<WordInfo> partialSolutionQueue = new LinkedList<>();

        // Add the initial ladder to the partial solution queue
        partialSolutionQueue.enqueue(initialWordInfo);

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