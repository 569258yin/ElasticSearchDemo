package es.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:datasource.properties")
public class MybatisConfig implements TransactionManagementConfigurer {

    private static final String PREFERED_TEST_QUERY = "select 1";

    @Value("${jdbc.driverClass}")
    private String driverClass;
    @Value("${jdbc.jdbcUrl}")
    private String jdbcUrl;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.minPoolSize}")
    private String minPoolSize;
    @Value("${jdbc.initialPoolSize}")
    private String initialPoolSize;
    @Value("${jdbc.maxPoolSize}")
    private String maxPoolSize;
    @Value("${jdbc.maxIdleTime}")
    private String maxIdleTime;
    @Value("${jdbc.acquireIncrement}")
    private String acquireIncrement;
    @Value("${jdbc.maxStatements}")
    private String maxStatements;
    @Value("${jdbc.idleConnectionTestPeriod}")
    private String idleConnectionTestPeriod;
    @Value("${jdbc.acquireRetryAttempts}")
    private String acquireRetryAttempts;
    @Value("${jdbc.testConnectionOnCheckout}")
    private String testConnectionOnCheckout;

    @Bean(destroyMethod = "close")
    public ComboPooledDataSource batisDataSource() {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMinPoolSize(NumberUtils.toInt(minPoolSize));
        dataSource.setInitialPoolSize(NumberUtils.toInt(initialPoolSize));
        dataSource.setMaxPoolSize(NumberUtils.toInt(maxPoolSize));
        dataSource.setMaxIdleTime(NumberUtils.toInt(maxIdleTime));
        dataSource.setAcquireIncrement(NumberUtils.toInt(acquireIncrement));
        dataSource.setMaxStatements(NumberUtils.toInt(maxStatements));
        dataSource.setIdleConnectionTestPeriod(NumberUtils.toInt(idleConnectionTestPeriod));
        dataSource.setAcquireRetryAttempts(NumberUtils.toInt(acquireRetryAttempts));
        dataSource.setTestConnectionOnCheckout(BooleanUtils.toBoolean(testConnectionOnCheckout));
        dataSource.setPreferredTestQuery(PREFERED_TEST_QUERY);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(batisDataSource());
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*/*.xml"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "mybatis")
    public DataSourceTransactionManager batisTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(batisDataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return batisTransactionManager();
    }
}
