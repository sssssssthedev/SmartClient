package ro.sssssssthedev.AntiCheat.utils;

public final class LogUtil {

    public LogUtil() throws Exception {
        throw new Exception("You cannot register utility classes");
    }

    public static void log(final String log) {
        System.out.println("[Overflow]: " + log);
    }
}
