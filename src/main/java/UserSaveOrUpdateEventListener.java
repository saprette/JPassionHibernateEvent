import model.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import java.io.Serializable;

/**
 * User: sam
 * Date: 8/19/12
 * Time: 5:29 PM
 */
public class UserSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener {

    private static final Logger logger = Logger.getLogger(UserSaveOrUpdateEventListener.class);

    // Perform selective updating - save only important users which age is between 30 and 50
    public Serializable performSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException {
        if (event.getObject() instanceof User) {
            User user = (User) event.getObject();
            logger.info("Preparing to save or update user " + user.getUsername());

            if (user.getUsername().startsWith("NotImportantUser") || user.getAge() < 30 || user.getAge() > 50) {
                logger.info(user + " is not recorded.");
                return null;
            } else {
                logger.info(user + " is recorded.");
            }
        }
        return super.performSaveOrUpdate(event);
    }
}