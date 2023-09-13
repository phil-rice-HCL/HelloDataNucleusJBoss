package org.jboss.as.quickstarts.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface PmHelper {
    Logger logger = LoggerFactory.getLogger(PmHelper.class);


    static <T> T usePM(FunctionWithException<PersistenceManager, T> fn) {
        PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory("hello-world");
        //@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName="hello-world")
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            return fn.apply(pm);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pm.close();
        }

    }

    static void populateDb() throws SQLException {
String connectionUrl = "jdbc:oracle:thin:@localhost:1521:xe";

try (Connection connection = DriverManager.getConnection(connectionUrl,"System", "admin");) {
try (Statement stmt = connection.createStatement()) {
try {
stmt.execute("DROP TABLE helloworld");
} catch (Exception e) {logger.info("Ignoring exception", e);}
stmt.execute("CREATE TABLE helloworld (messageId INTEGER, hellomessage VARCHAR(255))");
stmt.execute("INSERT INTO helloworld VALUES (1, 'Hello')");
stmt.execute("INSERT INTO helloworld VALUES (2, 'Bonjour')");
System.out.println("Populated DB");
try (ResultSet rs = stmt.executeQuery("SELECT * FROM helloworld")) {
while (rs.next()) {
System.out.println(rs.getString("hellomessage"));
}
}
}
}
    }

    static <T> T usePopulatedDb(FunctionWithException<PersistenceManager, T> fn) {
        try {
populateDb();
} catch (SQLException e) {
throw new RuntimeException(e);
}
        return usePM(fn);
    }

}
