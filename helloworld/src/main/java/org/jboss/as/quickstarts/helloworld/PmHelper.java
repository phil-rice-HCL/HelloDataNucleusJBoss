package org.jboss.as.quickstarts.helloworld;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.naming.NamingException;

public interface PmHelper {

    static <T> T usePM(FunctionWithException<PersistenceManager, T> fn) {
        PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory("hello-world");
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            return fn.apply(pm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pm.close();
        }

    }

    static void populateDb(PersistenceManager em) throws NamingException {
//    UserTransaction ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
//        Transaction tx = em.currentTransaction();
//        tx.begin();
        HelloWorld hw1 = new HelloWorld(1, "Hello");
        HelloWorld hw2 = new HelloWorld(2, "Bonjour");
        em.makePersistent(hw1);
        em.makePersistent(hw2);
        em.flush();
//        tx.commit();
    }

    static <T> T usePopulatedDb(FunctionWithException<PersistenceManager, T> fn) {
        usePM(pm -> {
            populateDb(pm);
            return null;
        });
        return usePM(fn::apply);
    }

}
