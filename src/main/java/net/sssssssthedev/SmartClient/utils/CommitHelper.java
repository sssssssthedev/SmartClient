package net.sssssssthedev.SmartClient.utils;
import net.sssssssthedev.SmartClient.Main;

import java.io.*;
import java.util.Properties;

public class CommitHelper {

    public static CommitHelper instance = new CommitHelper();
    public String getCommitID() throws IOException {
        Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/git.properties")) {
            properties.load(is);
            return properties.toString().replace("{", "").replace("}", "").replace("git.commit.id.abbrev=", "");
        } catch (IOException io) {
            io.printStackTrace();
        }
        return "null";
    }
}
