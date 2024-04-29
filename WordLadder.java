package comp.Box;

import java.util.*;

/*
"The time complexity of this algorithm is O(M^2 * N), where M is the length of each word and N is the total number of words in the dictionary.
Let me break it down:
 For each word, we perform the following operations:

We iterate over the length of the word, which is M. This takes O(M) time.
(because creating a new string is 𝑂(𝑀) and it's done M times for each character change), and N is the number of words in the dictionary.
  beginWord
     /   \
    /     \
  word1  word2   <- transformations of beginWord
    |       |
  word3  endWord  <- transformation of word2 is the endWord
 */
public class WordLadder {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        // If M is length of each word, and N is total number of words
        // We iterater over lengh of all words and substring is another so M2
        // O(M2 * N)

        // https://leetcode.com/problems/word-ladder/discuss/40717/Another-accepted-Java-solution-(BFS)
        // Lookup time faster in set - Constant lookups
        Set<String> set = new HashSet<>(wordList);

        if(!set.contains(endWord)) return 0;

        //Queue for BFS
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(beginWord);
        visited.add(beginWord);

        int level = 1;

        while(!queue.isEmpty()){
            // Get queue size and process to avoid adding null elements
            int size = queue.size();
            for(int i=0; i<size; i++)
            {
                String str = queue.poll();
                if(str.equals(endWord)) return level;
                // If not ,Modify str's each character (so word distance is 1)
                for(int j=0; j<str.length(); j++)
                {
                    // Java immutable
                    char[] chars= str.toCharArray();
                    for(char c = 'a' ; c <= 'z'; c++)
                    {
                        chars[j] = c;
                        String word = new String(chars);
                        // if dict contains word and not in visited
                        if(set.contains(word) && !visited.contains(word)){
                            queue.add(word);
                            visited.add(word);
                        }
                    }
                }
            }
            // We are moving to the next level
            level++;
        }
        return 0;
    }

}
