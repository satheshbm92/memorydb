package comp.Box;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopKIntegers {
    public static List<Integer> findTopK(String rootDir, int k) throws FileNotFoundException {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        Queue<File> fileQueue = new LinkedList<>();
        fileQueue.offer(new File(rootDir));

        while (!fileQueue.isEmpty()) {
            File currentFile = fileQueue.poll();
            if (currentFile.isDirectory()) {
                for (File file : currentFile.listFiles()) {
                    fileQueue.offer(file);
                }
            } else {
                processFile(currentFile, minHeap, k);
            }
        }

        return new ArrayList<>(minHeap);
    }

    private static void processFile(File file, PriorityQueue<Integer> minHeap, int k) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            int num = Integer.parseInt(scanner.nextLine());
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }
        scanner.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String rootDir = "/path/to/root/directory";
        int k = 10;
        List<Integer> topK = findTopK(rootDir, k);
        System.out.println("Top " + k + " largest integers: " + topK);
    }
}