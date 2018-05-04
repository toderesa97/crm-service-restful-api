package workshop.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CryptoUtility {

    public static String getDigest(String function, String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(function);
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return String.format( "%064x", new BigInteger( 1, hash ));
    }

    public static String getRandomToken() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        StringBuilder rndToken = new StringBuilder();
        for (byte aByte : bytes) {
            rndToken.append(aByte);
        }
        return getDigest("SHA-256", rndToken.toString());
    }


}
