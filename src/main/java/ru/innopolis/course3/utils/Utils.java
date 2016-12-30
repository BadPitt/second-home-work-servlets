package ru.innopolis.course3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 * Class for working with utils
 *
 * @author Danil Popov
 */
public final class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * Compares hash from DB with user password
     *
     * @param userPass user's password
     * @param hashFromBd user's password
     * @return true if user password's hash and hash from DB are equal
     */
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

    /**
     * Gets hash from pass
     *
     * @param pass user's password
     * @return {@code String} password's hash
     */
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

    /**
     * Formats date from {@code long} to {@code String}
     *
     * @param l {@code long date}
     * @return formatted {@code String date}
     */
    public static String getFormattedDate(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd, HH:mm");
        return sdf.format(l);
    }
}
