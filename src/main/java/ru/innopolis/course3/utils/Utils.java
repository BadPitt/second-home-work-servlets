package ru.innopolis.course3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by danil on 25/12/16.
 */
public final class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static boolean isPassEquals(String userPass, String hashFromBd) {
        boolean isEquals = false;
        try {
            MessageDigest userDigest = MessageDigest.getInstance("SHA-256");
            byte[] userBytes = userDigest.digest(userPass.getBytes(StandardCharsets.UTF_8));
            String userHash = DatatypeConverter.printHexBinary(userBytes);
            isEquals = userHash.equals(hashFromBd);
        } catch (NoSuchAlgorithmException e) {
            logger.error("there isn\'t crypto algorithm", e);
        }
        return isEquals;
    }

    public static String getPassHash(String pass) {
        String hash = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            hash = DatatypeConverter.printHexBinary(bytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("there isn\'t crypto algorithm", e);
        }

        return hash;
    }

}
