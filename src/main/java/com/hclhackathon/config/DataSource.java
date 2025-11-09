package com.hclhackathon.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;



public class DataSource {

	@Autowired
	private HikariDataSource dataSource;
	 
	 @Bean
	    public HikariDataSource dataSource() {
	        HikariConfig config = new HikariConfig();
	        config.setJdbcUrl("jdbc:postgresql://localhost:5432/hcldb");
	        config.setUsername("postgres");
	        config.setPassword("postgres");
	        config.setDriverClassName("org.postgresql.Driver");

	        // Hikari settings
	        config.setMinimumIdle(5);
	        config.setMaximumPoolSize(10);
	        config.setIdleTimeout(30000);
	        config.setMaxLifetime(1800000);
	        config.setConnectionTimeout(30000);
	        config.setAutoCommit(true);
	        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
	        config.setPoolName("HikariPool-1");

	        return new HikariDataSource(config);
	    }
	    

	    @PostConstruct
	    public void testConnection() throws SQLException {
	        try (Connection conn = dataSource.getConnection()) {
	            System.out.println("Autocommit: " + conn.getAutoCommit());
	            System.out.println("Isolation: " + conn.getTransactionIsolation());
	            System.out.println("Driver name: " + conn.getMetaData().getDriverName());
	            System.out.println("Database version: " + conn.getMetaData().getDatabaseProductVersion());
	        }
	    }
	    
	    @Bean
	    public Executor taskExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(5);
	        executor.setMaxPoolSize(10);
	        executor.setQueueCapacity(50);
	        executor.initialize();
	        return executor;
	    }
}
