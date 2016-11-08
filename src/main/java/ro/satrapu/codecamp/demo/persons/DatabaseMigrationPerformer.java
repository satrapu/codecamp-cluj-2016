package ro.satrapu.codecamp.demo.persons;

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
 * In order to avoid <a href="https://github.com/flyway/flyway/issues/324">this Flyway issue</a>, this bean
 * is managing the database transactions.
 */
@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DatabaseMigrationPerformer {
  private static final String APPLICATION_CONFIG_FILE_NAME = "app.properties";
  private static final String PROPERTY_DATASOURCE_NAME = "datasource.name";

  @PostConstruct
  public void initialize() throws IOException, NamingException {
    //get the DataSource instance pointing to the underlying database
    Properties applicationProperties = getApplicationProperties();
    String dataSourceName = getDataSourceName(applicationProperties);
    Context context = new InitialContext();
    DataSource dataSource = getDataSource(context, dataSourceName);

    // run database migration scripts
    Flyway flyway = new Flyway();
    flyway.setDataSource(dataSource);
    flyway.migrate();
  }

  private Properties getApplicationProperties() throws IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(APPLICATION_CONFIG_FILE_NAME)) {
      Properties applicationProperties = new Properties();
      applicationProperties.load(inputStream);
      return applicationProperties;
    }
  }

  private String getDataSourceName(Properties properties) {
    return (String) properties.get(PROPERTY_DATASOURCE_NAME);
  }

  private DataSource getDataSource(Context context, String dataSourceName) throws NamingException {
    return (DataSource) context.lookup(dataSourceName);
  }
}
