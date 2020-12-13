package com.developer.ayush.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceConfiguration {

	@Bean(name = "datasource")
	@ConfigurationProperties("database.datasource")
	@Primary
	public DataSource dataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mysql://localhost/sampledb?serverTimezone=UTC&autoReconnect=true");
		dataSourceBuilder.username("root");
//        dataSourceBuilder.password("root");
	 // Live DB Password
       dataSourceBuilder.password("ui876*^734fg");
    //	Testing DB Password
	
		return dataSourceBuilder.build();
	}

	@Bean(name = "tm1")
	@Primary
	@Autowired
	DataSourceTransactionManager tm1(@Qualifier("datasource") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}

}

