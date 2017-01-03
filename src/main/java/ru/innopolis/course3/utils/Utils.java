package ru.innopolis.course3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
     * @param hashAndSaltFromDb {@code String[]} user's password hash and salt from DB
     * @return true if user password's hash and hash from DB are equal
     */
    public static boolean isPassEquals(String userPass, String[] hashAndSaltFromDb) {
        String userHash = null;
        String salt = hashAndSaltFromDb[1];

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(userPass.getBytes(StandardCharsets.UTF_8));
            userHash = DatatypeConverter.printHexBinary(bytes); /* obtaining hashed pass */

            bytes = digest.digest((userHash + salt).getBytes(StandardCharsets.UTF_8));
            userHash = DatatypeConverter.printHexBinary(bytes); /* obtaining salted hash */
        } catch (NoSuchAlgorithmException e) {
            logger.error("there isn\'t crypto algorithm", e);
        }

        return hashAndSaltFromDb[0].equals(userHash);
    }

    /**
     * Gets hash and salt from pass
     *
     * @param pass user's password
     * @return {@code String[]} which contains password's hash
     *          {@code String[0]} and salt {@code String[1]}
     */
    public static String[] getHashAndSaltArray(String pass) {
        String hash = null;
        String salt = null;
        String[] hashAndSalt = new String[2];

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            hash = DatatypeConverter.printHexBinary(bytes); /* obtaining hashed pass */

            SecureRandom random = new SecureRandom();
            byte[] saltBytes = random.generateSeed(32);
            salt = DatatypeConverter.printHexBinary(saltBytes);

            bytes = digest.digest((hash + salt).getBytes(StandardCharsets.UTF_8));
            hash = DatatypeConverter.printHexBinary(bytes); /* obtaining salted hash */
        } catch (NoSuchAlgorithmException e) {
            logger.error("there isn\'t crypto algorithm", e);
        }

        hashAndSalt[0] = hash;
        hashAndSalt[1] = salt;

        return hashAndSalt;
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
