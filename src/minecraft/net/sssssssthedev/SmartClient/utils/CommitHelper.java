package net.sssssssthedev.SmartClient.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommitHelper {

    public static CommitHelper instance = new CommitHelper();

    public String getCommitID() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("git rev-parse HEAD");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        return stdInput.readLine().substring(0, 7);
    }
}
