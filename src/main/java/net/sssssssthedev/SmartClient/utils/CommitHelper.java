package net.sssssssthedev.SmartClient.utils;
import net.sssssssthedev.SmartClient.Main;

import java.io.*;
import java.util.Properties;

public class CommitHelper {

    public static CommitHelper instance = new CommitHelper();
    public String getCommitID() throws IOException {
        OsUtils.OSType osType = OsUtils.getOperatingSystemType();
        Properties properties = new Properties();
        switch (osType) {
            case Windows:
                try (InputStream is = getClass().getResourceAsStream("/git.properties")) {
                    properties.load(is);
                    return properties.toString().replace("{", "").replace("}", "").replace("git.commit.id.abbrev=", "");
                } catch (IOException io) {
                    io.printStackTrace();
                }
            case Linux:
                return "Linux";
            case MacOS:
                return "MacOS";
            case Other:
                return "Other";
        }
        return "";
    }
}
