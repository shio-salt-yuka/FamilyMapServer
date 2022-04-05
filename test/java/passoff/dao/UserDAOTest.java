package passoff.dao;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDAOTest {
    private Database db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestUser = new User("yShiobara", "passwordYuka", "yuka@byu.edu",
                "Yuka", "Shiobara", "f", "Yuka_123A");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        uDao = new UserDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException, SQLException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        Assertions.assertNotNull(compareTest);
        Assertions.assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException, SQLException {
        uDao.insert(bestUser);
        Assertions.assertThrows(DataAccessException.class, ()-> uDao.insert(bestUser));
    }

    public void clear(){}

    @Test
    public void findPass() throws DataAccessException, SQLException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        Assertions.assertNotNull(compareTest);
        Assertions.assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException, SQLException {
        Assertions.assertNull(uDao.find(bestUser.getPersonID()));
    }

    @Test
    public void clearTest() throws DataAccessException, SQLException {
        uDao.insert(bestUser);
        uDao.clear();
        uDao.find(bestUser.getPersonID());
    }
}