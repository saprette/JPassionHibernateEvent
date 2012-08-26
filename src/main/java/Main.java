import dao.BaseDao;
import dao.BaseDaoIml;
import exceptions.DaoException;
import model.User;
import org.apache.log4j.Logger;

/**
 * User: sam
 * Date: 8/15/12
 * Time: 11:37 AM
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    private static BaseDao<User, Integer> userDao;

    public static void main(String[] args) {
        try {
            performOperations();
        } catch (DaoException e) {
            logger.error("DaoException", e);
        }
    }

    private static void performOperations() throws DaoException {
        getUserDao().saveOrUpdate(new User("ImportantUser1", 10));
        getUserDao().saveOrUpdate(new User("ImportantUser2", 34));
        getUserDao().saveOrUpdate(new User("NotImportantUser", 10));
        getUserDao().saveOrUpdate(new User("NotImportantUser", 34));

        logger.info("Done creating users : ");

        logger.info("Users :");
        for (User p : getUserDao().findAll()) {
            logger.info(p);
        }

        logger.info("Done.");
    }

    public static BaseDao<User, Integer> getUserDao() {

        if (userDao == null) {
            createUserDao();
        }
        return userDao;
    }

    private static void createUserDao() {
        userDao = new BaseDaoIml<User, Integer>(User.class);
    }
}
