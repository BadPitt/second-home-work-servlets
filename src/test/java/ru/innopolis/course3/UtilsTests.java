package ru.innopolis.course3;

import org.junit.Test;
import ru.innopolis.course3.utils.Utils;

import static org.junit.Assert.assertTrue;

/**
 * @author Danil Popov
 */
public class UtilsTests {

    @Test
    public void getHashTest() {
        String pass1 = "weakPass";
        String pass2 = new String("weakPass");
        String pass3 = "str0ng!*P@ssw0rd";

        String hash1 = Utils.getPassHash(pass1);
        String hash2 = Utils.getPassHash(pass2);
        String hash3 = Utils.getPassHash(pass3);

        assertTrue(hash1.equals(hash2));
        assertTrue(!hash1.equals(hash3));
    }

    @Test
    public void isPassEqualsTest() {

        String pass1 = "weakPass";
        String pass2 = new String("weakPass");
        String pass3 = "str0ng!*P@ssw0rd";

        String hash1 = Utils.getPassHash(pass1);
        String hash2 = Utils.getPassHash(pass2);
        String hash3 = Utils.getPassHash(pass3);

        assertTrue(Utils.isPassEquals(pass1, hash1));
        assertTrue(Utils.isPassEquals(pass1, hash2));
        assertTrue(!Utils.isPassEquals(pass3, hash2));
    }

    @Test
    public void getFormattedDateTest() {
        String testDate = "2016/12/27, 23:12";
        assertTrue(testDate.equals(Utils.getFormattedDate(1451246400000L)));
    }
}
