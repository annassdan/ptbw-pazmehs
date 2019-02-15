package tnc.at.brpl.configurations;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.UUID;

public class BrplIdGenerator extends IdentityGenerator implements IdentifierGenerator {

//    private static Logger log = Logger.getLogger(BrplIdGenerator.class);

    org.slf4j.Logger logger = LoggerFactory.getLogger(BrplIdGenerator.class);

    @Override
    public Serializable generate(SessionImplementor sessionImplementor, Object o) throws HibernateException {

        Serializable id = sessionImplementor.getEntityPersister(null, o)
                .getClassMetadata().getIdentifier(o, sessionImplementor);  // read received id , if the id is attached on entity


//        logger.info(sessionImplementor.getEntityPersister(null, o).getClassMetadata().getIdentifierType().getReturnedClass().getName());

        // maybe we can recheck the id on database

        return (id == null || id.toString().length() == 0) ? UUID.randomUUID().toString() : id.toString();
    }


}
