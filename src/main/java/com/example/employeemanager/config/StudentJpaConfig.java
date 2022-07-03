package com.example.employeemanager.config;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;

//@Configuration
//@EnableJpaRepositories(basePackages = "com.example.employeemanager.repository")
//@EnableWebMvc
//@PropertySource("testing.properties")
//@EnableTransactionManagement
public class StudentJpaConfig {

//    @Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("org.h2.Driver");
////        dataSource.setUrl("jdbc:h2:mem:employeemanager");
////        dataSource.setUsername("sa");
////        dataSource.setPassword("sa");
//
//        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
//        return dbBuilder.setType(EmbeddedDatabaseType.H2).addScript("classpath:sql/schema.sql").build();
////        return dataSource;
//    }
////    @Bean
////    public JdbcTemplate jdbcTemplate(){
////        return new JdbcTemplate(dataSource()); //Dependency Injection只需要呼叫方法即可
////    }
////    @Bean
////    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
////        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
////        em.setDataSource(dataSource());
////        em.setPackagesToScan(new String[] { "com.example.employeemanager.model" });
////        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
////        return em;
////    }
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        //new 一個entity factory
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();//new一個實作JPA的vendor
//        vendorAdapter.setGenerateDdl(true);
//        vendorAdapter.setShowSql(true); //設定showSQL
//
//        factory.setDataSource(dataSource()); //設定datasource
//        factory.setJpaVendorAdapter(vendorAdapter); //設定Implement JPA vendor
//        factory.setPackagesToScan("com.example.employeemanager.model"); //設定Entity package位置
//
//        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
//        return factory;
//
//    }
//    @Bean
//    public PlatformTransactionManager transactionManager(){
//        EntityManagerFactory factory = entityManagerFactory().getObject();
//        return new JpaTransactionManager(factory);
//JpaTransactionManager需要注入EntityManagerFactory的Instance
//    }
//    @Bean
//    JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
//        final JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }

}