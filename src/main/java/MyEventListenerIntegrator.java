import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 * User: sam
 * Date: 8/19/12
 * Time: 5:33 PM
 */
public class MyEventListenerIntegrator implements Integrator {
    private static final Logger logger = Logger.getLogger(MyEventListenerIntegrator.class);

    @Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        registerListeners(serviceRegistry);
    }
    private void registerListeners(SessionFactoryServiceRegistry serviceRegistry) {
        logger.info("Registering UserSaveOrUpdateEventListener class as a SAVE_UPDATE event listener");
        final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
        eventListenerRegistry.appendListeners(EventType.SAVE_UPDATE, UserSaveOrUpdateEventListener.class);
    }

    @Override
    public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        registerListeners(serviceRegistry);
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        //Nothing to do ?
    }
}
