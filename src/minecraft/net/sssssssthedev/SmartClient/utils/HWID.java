package net.sssssssthedev.SmartClient.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public final class HWID {

    /**
     * Gives a HWID I.E (350-30a-3ae-30e-304-3d6-37d-359-371-3e0-3d8-3e1-369-3b2-34a-314)
     * @return the fucking hwid
     * @throws NoSuchAlgorithmException some no algorithm exception
     * @throws UnsupportedEncodingException some unsupported exception
     */
    public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        StringBuilder s = new StringBuilder();
        final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
        final byte[] bytes = main.getBytes("UTF-8");
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        final byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        for (final byte b : md5) {
            s.append(Integer.toHexString((b & 0xFF) | 0x300), 0, 3);
            if (i != md5.length - 1) {
                s.append("-");
            }
            i++;
        }
        return s.toString();
    }

}
