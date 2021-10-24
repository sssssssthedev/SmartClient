package net.sssssssthedev.SmartClient.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommitHelper {

    public static CommitHelper instance = new CommitHelper();
    public String getCommitID() throws IOException {
        Runtime rt = Runtime.getRuntime();
        OsUtils.OSType osType = OsUtils.getOperatingSystemType();
        Process proc;
        BufferedReader stdInput;
        switch (osType) {
            case Windows:
                proc = rt.exec("curl -s -H \"Authorization: token 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\" -H\n" +
                        "\"Accept: application/vnd.github.VERSION.sha\" \"https://api.github.com/repos/sssssssthedev/SmartClient/commits/main\"");
                stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                return stdInput.readLine().substring(0, 7);
            case Linux:
                proc = rt.exec("curl -s -H \"Authorization: token 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\" -H\n" +
                        "\"Accept: application/vnd.github.VERSION.sha\" \"https://api.github.com/repos/sssssssthedev/SmartClient/commits/main\"");
                stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                return stdInput.readLine().substring(0, 7);
            case MacOS:
                return "MacOS";
            case Other:
                return "Other";
        }
        return "";
    }
}
