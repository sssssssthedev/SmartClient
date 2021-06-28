package net.sssssssthedev.SmartClient.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommitHelper {

    public static CommitHelper instance = new CommitHelper();

    public String getCommitID() throws IOException {
        ProcessBuilder builder = new ProcessBuilder( "git rev-parse HEAD");
        builder.directory( new File("C:\\Users\\sssssss\\Downloads\\mcp918\\"));
        builder.redirectErrorStream(true);
        Process proc = builder.start();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        return stdInput.readLine().substring(0, 7);
    }
}
