package comp.Box;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopKPractice {

    public static List<Integer> findTopK(String rootDir, int k) throws FileNotFoundException {
        List<Integer> result = new ArrayList<>();
        // Create a min heap to store the top K largest integers
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Create a queue to perform BFS traversal of the file system
        Queue<File> queue = new LinkedList<>();
        queue.offer(new File(rootDir));

        while (!queue.isEmpty()){
            File current = queue.poll();

            if(current.isDirectory()){
                // If the current file is a directory, add all its subdirectories and files to the queue
                File[] subFiles = current.listFiles();
                if(subFiles !=null) queue.addAll(Arrays.asList(subFiles));
                else{
                    // If the current file is a regular file, read the integers from it
                    Scanner scanner = new Scanner(current);
                    while(scanner.hasNext()){
                        int num = scanner.nextInt();
                        if(minHeap.size() < k){ // If the min heap has less than K elements, add the current integer
                        minHeap.offer(num);
                        }
                        else if(num > minHeap.peek()){
                            // If the min heap is full and the current integer is greater than the smallest element,
                            // remove the smallest element and add the current integer
                            minHeap.remove();
                            minHeap.add(num);
                        }
                    }
                }
            }
        }

        result.addAll(minHeap);
        Collections.reverse(result);
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String rootDir = "/path/to/root/directory";
        int k = 3;
        List<Integer> topK = findTopK(rootDir, k);
        System.out.println("Top " + k + " largest integers: " + topK);
    }
}
