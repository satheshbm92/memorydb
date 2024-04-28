package comp.Box;

import java.util.*;

class Directory {
    List<Directory> subDirectories;
    List<List<Integer>> files;

    public Directory(List<Directory> subDirectories, List<List<Integer>> files) {
        this.subDirectories = subDirectories;
        this.files = files;
    }
}

public class ToK4 {

    public static List<Integer> findTopK(Directory root, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        Queue<Directory> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Directory current = queue.poll();
            // Traverse files in the current directory
            for (List<Integer> file : current.files) {
                for (int number : file) {
                    if (minHeap.size() < k) {
                        minHeap.add(number);
                    } else if (number > minHeap.peek()) {
                        minHeap.poll();
                        minHeap.add(number);
                    }
                }
            }
            // Add subdirectories to the queue to process them
            for (Directory subDir : current.subDirectories) {
                queue.add(subDir);
            }
        }

        List<Integer> result = new ArrayList<>(minHeap);
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    public static void main(String[] args) {
        Directory subDir1 = new Directory(
                new ArrayList<>(),
                Arrays.asList(Arrays.asList(1, 3, 5), Arrays.asList(10, 20))
        );
        Directory subDir2 = new Directory(
                new ArrayList<>(),
                Arrays.asList(Arrays.asList(6, 7, 8, 2))
        );
        Directory root = new Directory(
                Arrays.asList(subDir1, subDir2),
                new ArrayList<>()
        );

        List<Integer> topK = findTopK(root, 3);
        System.out.println(topK);
    }
}
