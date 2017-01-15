package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.course3.models.DBException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Class for creating tables in DB
 * and getting connections;
 * If flag {@code isTest} equals true it means
 * that user want work with Test-DB, otherwise
 * connection will establish with Working-DB
 *
 * @author Danil Popov
 */
@Deprecated
public class DBConnection {

    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static final DBConnection dbConnection = new DBConnection();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver hasn\'t found", e);
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws DBException {
        createUserTable();
        createArticleTable();
        createCommentTable();
    }

    private static final String DATABASE_URL = "jdbc/publishingis";
    private static final String DATABASE_URL_TEST =
            "jdbc:postgresql://localhost:5432/publishingis_test?user=postgres&password=postgres";

    public static boolean isTest = false;

    private static final String CREATE_USER_TABLE = "CREATE TABLE P_USER" +
            "(" +
            " USER_ID SERIAL NOT NULL PRIMARY KEY," +
            " NAME TEXT UNIQUE NOT NULL," +
            " PASSWORD TEXT," +
            " SALT TEXT," +
            " IS_ACTIVE BOOLEAN," +
            " IS_ADMIN BOOLEAN," +
            " VERSION BIGINT" +
            " );";
    private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE ARTICLE" +
            "(" +
            " ARTICLE_ID SERIAL NOT NULL PRIMARY KEY," +
            " TITLE TEXT," +
            " SOURCE TEXT," +
            " DATE BIGINT," +
            " USER_ID INTEGER," +
            " UPDATE_DATE BIGINT," +
            " FOREIGN KEY (USER_ID) REFERENCES P_USER (USER_ID)" +
            " MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE" +
            " );";
    private static final String CREATE_COMMENT_TABLE = "CREATE TABLE COMMENT (" +
            "  COMMENT_ID SERIAL PRIMARY KEY NOT NULL," +
            "  SOURCE TEXT," +
            "  DATE BIGINT," +
            "  ARTICLE_ID INTEGER," +
            "  USER_ID INTEGER," +
            "  UPDATE_DATE BIGINT," +
            "  FOREIGN KEY (USER_ID) REFERENCES P_USER (USER_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE," +
            "  FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLE (ARTICLE_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";
    private static final String DROP_ARTICLE_TABLE = "DROP TABLE ARTICLE;";
    private static final String DROP_USER_TABLE = "DROP TABLE P_USER;";
    private static final String DROP_COMMENT_TABLE = "DROP TABLE COMMENT;";

    /**
     * Gets connection with DB or TestDB
     * according {@code isTest} flag
     *
     * @return {@code Connection} with DB
     * @throws SQLException when something goes wrong
     */
    public static Connection getDbConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            if (!isTest) {
                InitialContext initContext = new InitialContext();
                Context envContext = (Context)initContext.lookup("java:comp/env");
                DataSource ds = (DataSource) envContext.lookup(DATABASE_URL);
                conn = ds.getConnection();
            } else {
                conn = DriverManager.getConnection(DATABASE_URL_TEST);
            }

        } catch (NamingException e) {
            logger.error("get connection naming exception", e);
        }
        return conn;
    }

    /**
     * creates P_USER table in DB
     * @see DBConnection#CREATE_USER_TABLE
     */
    public static void createUserTable() {
        try (Connection connection = getDbConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_USER_TABLE);

        } catch (SQLException e) {
            logger.error("create USER table sql exception", e);
        }
    }

    /**
     * creates ARTICLE table in DB
     * @see DBConnection#CREATE_ARTICLE_TABLE
     */
    public static void createArticleTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("create ARTICLE table sql exception", e);
        }
    }


    /**
     * creates COMMENT table in DB
     * @see DBConnection#CREATE_COMMENT_TABLE
     */
    public static void createCommentTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_COMMENT_TABLE);

        } catch (SQLException e) {
            logger.error("create COMMENT table sql exception", e);
        }
    }

    /**
     * drops ARTICLE table in DB
     * @see DBConnection#DROP_ARTICLE_TABLE
     */
    public static void dropArticleTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("drop ARTICLE table sql exception", e);
        }
    }

    /**
     * drops COMMENT table in DB
     * @see DBConnection#DROP_COMMENT_TABLE
     */
    public static void dropCommentTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_COMMENT_TABLE);

        } catch (SQLException e) {
            logger.error("drop COMMENT table sql exception", e);
        }
    }

    /**
     * drops USER table in DB
     * @see DBConnection#DROP_USER_TABLE
     */
    public static void dropUserTable() {
        try (Connection conn = getDbConnection();
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_USER_TABLE);

        } catch (SQLException e) {
            logger.error("drop USER table sql exception", e);
        }
    }
}
