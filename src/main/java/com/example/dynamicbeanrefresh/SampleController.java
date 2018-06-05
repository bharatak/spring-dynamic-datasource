package com.example.dynamicbeanrefresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
public class SampleController {

    @Autowired
    private DataSource newDataSource;

    @RequestMapping("/ds")
    public String getDataSourceDetails(){

        //TODO: RUn it secondtime and you see that the connection cannot be obtained due to changed password.
        // TODO: So, it works!!
        try {
            newDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("%s",newDataSource.hashCode());
    }

}
