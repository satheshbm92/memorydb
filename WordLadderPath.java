package comp.Box;

import java.util.*;

/*
Therefore, the time complexity of the BFS traversal remains O(M^2 * N), where N is the total number of words in the dictionary.
 */
public class WordLadderPath {
    public List<String> findPath(String beginWord, String endWord, List<String> wordList) {
        // Create a set from the word list for faster lookups
        Set<String> set = new HashSet<>(wordList);

        // If the endWord is not in the set, no path exists
        if (!set.contains(endWord)) {
            return new ArrayList<>();
        }
        // Create a queue for BFS traversal and a set to track visited words
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        // Create a map to store the parent of each word during BFS traversal
        Map<String, String> parent = new HashMap<>();

        // Add the beginWord to the queue and mark it as visited
        queue.offer(beginWord);
        visited.add(beginWord);

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();

                // If the endWord is reached, construct and return the path
                if (word.equals(endWord)) {
                    return constructPath(beginWord, endWord, parent);
                }

                // Generate new words by modifying each character of the current word
                for (int j = 0; j < word.length(); j++) {
                    char[] chars = word.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[j] = c;
                        String newWord = new String(chars);

                        // If the new word is in the set and not visited, add it to the queue
                        // and mark it as visited. Also, update its parent in the map.
                        if (set.contains(newWord) && !visited.contains(newWord)) {
                            queue.add(newWord);
                            visited.add(newWord);
                            parent.put(newWord, word);
                        }
                    }
                }
            }
        }
        // If no path is found, return an empty list
        return new ArrayList<>();
    }

    /*
    In the constructPath method,
    we start from the endWord and traverse backwards using the parent map, adding each word encountered to the path list.
     */
    private List<String> constructPath(String beginWord, String endWord, Map<String, String> parent) {
        // Create a list to store the path
        List<String> path = new ArrayList<>();

        // Start from the endWord and traverse backwards using the parent map
        String current = endWord;
        path.add(current);

        while (!current.equals(beginWord)) {
            current = parent.get(current);
            path.add(current);
        }

        // Reverse the path to get the correct order from beginWord to endWord
        Collections.reverse(path);
        // Return the constructed path
        return path;
    }
}

/*
The set is created using Collections.newSetFromMap(new ConcurrentHashMap<>()) to make it thread-safe. This ensures that multiple threads can safely access and modify the set concurrently.
The queue is changed to ConcurrentLinkedQueue, which is a thread-safe implementation of the Queue interface. This allows multiple threads to safely enqueue and dequeue elements from the queue.
The visited set is also created using Collections.newSetFromMap(new ConcurrentHashMap<>()) to make it thread-safe. This ensures that multiple threads can safely add and check elements in the visited set.
The parent map is changed to ConcurrentHashMap, which is a thread-safe implementation of the Map interface.
This allows multiple threads to safely put and get elements from the parent map.
The condition visited.add(newWord) is used instead of !visited.contains(newWord) to atomically check and add the new word to the visited set.
This prevents multiple threads from adding the same word to the queue and visited set simultaneously.
 */
