package com.example.dynamicbeanrefresh;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.TargetSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicDataSource implements TargetSource {
    private static final Logger logger = Logger.getLogger(DynamicDataSource.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    private Map<String,DataSource> targets = Collections.synchronizedMap(new HashMap<String,DataSource>());

    private DataSource createDataSource(String password) {
        logger.info("Creating a new DataSource with password ["+ password+"]");
        DataSource dataSource = DataSourceBuilder
                .create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();

        return dataSource;
    }

    private String retrieveDbPassword() {
        //TODO: Get the password from remote url.  Mocking this with a filesystem.
        try {
            return FileUtils.readFileToString(new File("/tmp/test"),null);
        } catch (IOException e) {
            logger.error("Unable to read file /tmp/test");
        }
        return "";
    }


    @Override
    public Class<?> getTargetClass() {
        return DataSource.class;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    public Object getTarget() throws Exception {
        String password = retrieveDbPassword();

        if(targets.containsKey(password)){
            return targets.get(password);
        }

        DataSource newDataSource = createDataSource(password);
        targets.put(password,newDataSource);
        //TODO: need to clear up the old DataSources
        return newDataSource;
    }

    @Override
    public void releaseTarget(Object o) throws Exception {

    }
}
