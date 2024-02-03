package ma.oneshoot.oneshootmain.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
public class MultitenancyConfig {

    // Multi-Tenant LocalSessionFactoryBean that is used for multi-tenant sessions
    @Bean
    LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("ma.oneshoot.oneshootmain.tenant.db");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties());
        return localSessionFactoryBean;

    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.multiTenancy", "SCHEMA");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "NONE");
        return hibernateProperties;
    }
    // Non Multi-Tenant LocalSessionFactoryBean that is used to create schemas for tenants
    @Bean
    LocalSessionFactoryBean sessionFactorySchemaManager(DataSource dataSource){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("ma.oneshoot.oneshootmain.tenant.db");
        localSessionFactoryBean.setHibernateProperties(hibernatePropertiesSchemaManager());
        return localSessionFactoryBean;
    }

    private Properties hibernatePropertiesSchemaManager() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.multiTenancy", "NONE");
        return hibernateProperties;
    }
}
