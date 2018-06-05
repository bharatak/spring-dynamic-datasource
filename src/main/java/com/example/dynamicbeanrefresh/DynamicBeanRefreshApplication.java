package com.example.dynamicbeanrefresh;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.NoRepositoryBean;

import javax.sql.DataSource;

@SpringBootApplication
public class DynamicBeanRefreshApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicBeanRefreshApplication.class, args);
	}

	@Bean
	public DataSource newDataSource(DynamicDataSource dynamicDataSource){
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setInterfaces(DataSource.class);
		proxyFactoryBean.setTargetSource(dynamicDataSource);
		return (DataSource) proxyFactoryBean.getObject();
	}
}
