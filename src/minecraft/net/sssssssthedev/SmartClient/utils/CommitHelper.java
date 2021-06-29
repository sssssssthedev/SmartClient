package net.sssssssthedev.SmartClient.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommitHelper {

    public static CommitHelper instance = new CommitHelper();
    public String getCommitID() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("curl -s -H \"Authorization: token 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\" -H\n" +
                "\"Accept: application/vnd.github.VERSION.sha\" \"https://api.github.com/repos/sssssssthedev/SmartClient/commits/main\"");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        return stdInput.readLine().substring(0, 7);
    }
}
