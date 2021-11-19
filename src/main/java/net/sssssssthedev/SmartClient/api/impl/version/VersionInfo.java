package net.sssssssthedev.SmartClient.api.impl.version;

public class VersionInfo {

    public String name;
    public String version;
    public String build;
    public String commit;

    public VersionInfo(String name, String version, String build, String commit) {
        this.name = name;
        this.version = version;
        this.build = build;
        this.commit = commit;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public String getBuild() {
        return this.build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getCommit() {
        return this.commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}

