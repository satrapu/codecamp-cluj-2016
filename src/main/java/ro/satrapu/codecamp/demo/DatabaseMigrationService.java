package ro.satrapu.codecamp.demo;

import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Performs database migration via <a href="https://flywaydb.org/">Flyway</a>.
 * This bean is managing database transactions in order to avoid
 * <a href="https://github.com/flyway/flyway/issues/324">this Flyway issue</a>.
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DatabaseMigrationService {
  private static final String APPLICATION_PROPERTIES_FILE_NAME = "app.properties";
  private static final String JNDI_DATASOURCE_NAME = "jndi.datasource.name";

  @PostConstruct
  public void performDatabaseMigration() {
    //get the DataSource instance pointing to the underlying database
    Properties applicationProperties = getApplicationProperties();
    String dataSourceName = getDataSourceName(applicationProperties);
    DataSource dataSource = getDataSource(dataSourceName);

    // run database migration scripts
    Flyway flyway = new Flyway();
    flyway.setDataSource(dataSource);
    flyway.migrate();
  }

  private Properties getApplicationProperties() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(APPLICATION_PROPERTIES_FILE_NAME)) {
      Properties applicationProperties = new Properties();
      applicationProperties.load(inputStream);
      return applicationProperties;
    } catch (IOException e) {
      throw new RuntimeException("Unable to read application properties", e);
    }
  }

  private String getDataSourceName(Properties properties) {
    return (String) properties.get(JNDI_DATASOURCE_NAME);
  }

  private DataSource getDataSource(String dataSourceName) {
    try {
      Context context = new InitialContext();
      return (DataSource) context.lookup(dataSourceName);
    } catch (NamingException e) {
      throw new RuntimeException(String.format("Unable to load datasource using name: %s", dataSourceName), e);
    }
  }
}
