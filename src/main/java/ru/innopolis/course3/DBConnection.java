package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by danil on 25/12/16.
 */
public class DBConnection {

    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver hasn\'t found", e);
            throw new RuntimeException();
        }
    }

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/publishingis?user=postgres&password=postgres";
    public static final String DATABASE_URL_TEST = "jdbc:postgresql://localhost:5432/publishingis_test?user=postgres&password=postgres";

    public static boolean isTest = false;

    private static final String CREATE_USER_TABLE = "CREATE TABLE P_USER" +
            "(USER_ID SERIAL NOT NULL PRIMARY KEY," +
            " NAME TEXT UNIQUE," +
            " PASSWORD TEXT," +
            " IS_ACTIVE BOOLEAN," +
            " IS_ADMIN BOOLEAN);";
    private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE ARTICLE" +
            "(ARTICLE_ID SERIAL NOT NULL PRIMARY KEY," +
            " TITLE TEXT," +
            " SOURCE TEXT," +
            " DATE BIGINT," +
            " USER_ID INTEGER" +
            " CONSTRAINT article_p_user_user_id_fk\n" +
            " REFERENCES P_USER (USER_ID) ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String CREATE_COMMENT_TABLE = "CREATE TABLE COMMENT (" +
            "  COMMENT_ID SERIAL PRIMARY KEY NOT NULL," +
            "  SOURCE TEXT," +
            "  DATE BIGINT," +
            "  ARTICLE_ID INTEGER," +
            "  USER_ID INTEGER," +
            "  FOREIGN KEY (USER_ID) REFERENCES P_USER (USER_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE," +
            "  FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLE (ARTICLE_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";
    private static final String DROP_ARTICLE_TABLE = "DROP TABLE ARTICLE;";
    private static final String DROP_USER_TABLE = "DROP TABLE P_USER;";
    private static final String DROP_COMMENT_TABLE = "DROP TABLE COMMENT;";

    public static Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(isTest ? DATABASE_URL_TEST : DATABASE_URL);
    }

    public static void createUserTable() {
        try (Connection connection = getDbConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_USER_TABLE);

        } catch (SQLException e) {
            logger.error("create USER table sql exception", e);
        }
    }

    public static void createArticleTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("create ARTICLE table sql exception", e);
        }
    }

    public static void dropArticleTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("drop ARTICLE table sql exception", e);
        }
    }

    public static void dropCommentTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_COMMENT_TABLE);

        } catch (SQLException e) {
            logger.error("drop COMMENT table sql exception", e);
        }
    }

    public static void dropUserTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_USER_TABLE);

        } catch (SQLException e) {
            logger.error("drop USER table sql exception", e);
        }
    }

    public static void createCommentTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_COMMENT_TABLE);

        } catch (SQLException e) {
            logger.error("create COMMENT table sql exception", e);
        }
    }
}
