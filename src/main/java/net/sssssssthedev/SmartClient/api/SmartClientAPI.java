package net.sssssssthedev.SmartClient.api;

import net.sssssssthedev.SmartClient.api.impl.version.VersionInfo;
import net.sssssssthedev.SmartClient.utils.CommitHelper;

import java.io.IOException;

public class SmartClientAPI {

    public VersionInfo versionInfo;

    public void load() throws IOException {
        versionInfo = new VersionInfo("SmartClient" , "Production", "1.2.8", CommitHelper.instance.getCommitID());
    }

}
