package comp.Box.card;

public class Search1 {


    /*
    grep -r '500' /path/to/logs/ > results.txt

    grep: The command for searching text patterns.
    -r: The recursive option, which tells grep to search recursively through all files and directories.
    '500': The pattern to search for, which is the error code 500 in this case.
    /path/to/logs/: The path to the directory containing the 100 log files.
    > results.txt: Redirects the output of grep (i.e., the matching lines) to a file named results.txt.


     parallel --jobs 8 grep -H '500' {} ::: /path/to/logs/*.log > results.txt

     parallel --jobs 8 tells GNU Parallel to run up to 8 processes in parallel (adjust this value based on your CPU cores).
     grep -H '500': This is the job that parallel is being asked to run in parallel. The grep command searches for the pattern '500',
     and the -H option tells grep to print the filename for each match.
    {}: In the context of parallel, {} is a placeholder that will be replaced by each input line.
    In this case, it represents each file path from the expanded wildcard pattern /path/to/logs/*.log.

    ::: This is a delimiter used by parallel to separate the command from the input arguments that follow.

     {} ::: /path/to/logs/*.log tells GNU Parallel to run the command for each file matching the pattern /path/to/logs/*.log.



     */
}

/*
1 - grep: The command used for searching text or regular expressions within files.
grep "error code 500" /path/to/logfile.log
grep "error code 500" /path/to/logs/*.log - Multiple files
grep "error code 500" /path/to/logs/*.log > results.txt - Output to a file



ls /path/to/logs/*.log | xargs -P 10 -I {} grep "error code 500" {} > results.txt

ls /path/to/logs/*.log: Lists all log files that match the pattern.
|: Pipes the list of files to xargs.
xargs -P 10 -I {}: xargs takes each file path output by ls, running up to 10 parallel grep commands.
grep "error code 500" {}: Each instance of grep searches through one of the files.
> results.txt: Outputs all matches into one file.
 */
