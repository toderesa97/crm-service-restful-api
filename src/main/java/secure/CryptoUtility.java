package secure;

import java.security.SecureRandom;

public class CryptoUtility {

    public static String getDigest(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt(10));
    }

    public static String getRandomToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        StringBuilder rndToken = new StringBuilder();
        for (byte aByte : bytes) {
            rndToken.append(aByte);
        }
        return rndToken.toString();
    }


    public static boolean check(String hashed, String password) {
        return BCrypt.checkpw(password, hashed);
    }
}
