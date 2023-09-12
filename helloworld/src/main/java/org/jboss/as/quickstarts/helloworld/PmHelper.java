package org.jboss.as.quickstarts.helloworld;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

public interface PmHelper {

    static <T> T usePM(FunctionWithException<PersistenceManager, T> fn) {
        PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory("hello-world");
        //@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName="hello-world")
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            return fn.apply(pm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pm.close();
        }

    }

    static void populateDb(PersistenceManager em) {
        Transaction tx = em.currentTransaction();
        try {
            tx.begin();
            HelloWorld hw1 = new HelloWorld(1, "Hello");
            HelloWorld hw2 = new HelloWorld(2, "Bonjour");
            em.makePersistent(hw1);
            em.makePersistent(hw2);
            tx.commit();
        } finally {
            if (tx.isActive()) tx.rollback();
        }
    }

    static <T> T usePopulatedDb(FunctionWithException<PersistenceManager, T> fn) {
        usePM(pm -> {
            populateDb(pm);
            return null;
        });
        return usePM(fn::apply);
    }

}
