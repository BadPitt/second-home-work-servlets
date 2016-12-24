package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author Danil Popov
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver hasn\'t found", e);
            throw new RuntimeException();
        }
    }

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/publishingis?user=postgres&password=postgres";

    private static final String CREATE_USER_TABLE = "CREATE TABLE P_USER\n" +
            "(USER_ID SERIAL NOT NULL PRIMARY KEY,\n" +
            "  NAME TEXT, PASSWORD TEXT, ROLE BIT);";
    private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE ARTICLE\n" +
            "(ARTICLE_ID SERIAL NOT NULL PRIMARY KEY,\n" +
            "  TITLE TEXT, SOURCE TEXT, DATE DATE, USER_ID INTEGER CONSTRAINT article_p_user_user_id_fk\n" +
            "REFERENCES P_USER (USER_ID) ON DELETE CASCADE ON UPDATE CASCADE);";


    public static void main(String[] args) {
        createUserTable();
        createArticleTable();
    }

    private static void createUserTable() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_USER_TABLE);

        } catch (SQLException e) {
            logger.error("create USER table sql exception", e);
        }
    }

    private static void createArticleTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("create ARTICLE table sql exception", e);
        }
    }
}
