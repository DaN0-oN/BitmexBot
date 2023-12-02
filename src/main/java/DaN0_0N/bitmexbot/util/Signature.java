package DaN0_0N.bitmexbot.util;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Signature {
    public String getSignature(String secretKey, String message) {
        String signature = "";

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hashedBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexStringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            signature = hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return signature;
    }
}

