package comp.Box;

public class Grep1 {

    /*
    grep -r '500' /path/to/logs/ > results.txt
    -r: Recursive option, which tells grep to search recursively through all files and directories.

    tail -n 100 app.log | grep '500'
    tail -f app.log | grep '500'

    parallel --jobs 8 grep -H '500' {} ::: /path/to/logs/*.log > results.txt


    parallel --jobs 8 tells GNU Parallel to run up to 8 processes in parallel (adjust this value based on your CPU cores).

    grep -H '500' {} is the command to search for the pattern '500' in each log file, with -H printing the file name along with the matching lines.\


    {} ::: /path/to/logs/*.log tells GNU Parallel to run the command for each file matching the pattern /path/to/logs/*.log.

    > results.txt redirects the output to a file named results.txt.
     */
}
