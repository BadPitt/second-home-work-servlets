package ru.innopolis.course3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Danil Popov
 */
public class MyPassEncoder implements PasswordEncoder {


    private static final Logger logger = LoggerFactory.getLogger(MyPassEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
            String hash = DatatypeConverter.printHexBinary(bytes); /* obtaining hashed pass */

            SecureRandom random = new SecureRandom();
            byte[] saltBytes = random.generateSeed(32);
            String salt = DatatypeConverter.printHexBinary(saltBytes);

            bytes = digest.digest((hash + salt).getBytes(StandardCharsets.UTF_8));
            hash = DatatypeConverter.printHexBinary(bytes); /* obtaining salted hash */

            return salt.concat(hash);

        } catch (NoSuchAlgorithmException e) {
            logger.error("there isn\'t crypto algorithm", e);
        }

        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String userHash = null;
        String salt = encodedPassword.substring(0,64);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
            userHash = DatatypeConverter.printHexBinary(bytes); /* obtaining hashed pass */

            bytes = digest.digest((userHash + salt).getBytes(StandardCharsets.UTF_8));
            userHash = DatatypeConverter.printHexBinary(bytes); /* obtaining salted hash */

            return encodedPassword.equals(salt + userHash);

        } catch (NoSuchAlgorithmException e) {
            logger.error("there isn\'t crypto algorithm", e);
        }

        return false;
    }
}
