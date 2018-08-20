package com.pf.mock;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class SqliteManager {
    private static SqliteManager mInstance = new SqliteManager();
    private JdbcTemplate jdbcTemplate;
    private SqliteManager() {
//        jdbcTemplate = new JdbcTemplate();
        try {
            System.out.println("jdbc:sqlite:" + getUrl());
            DriverManagerDataSource dataSource = new SingleConnectionDataSource();
            dataSource.setDriverClassName("org.sqlite.JDBC");
            dataSource.setUrl("jdbc:sqlite:" + getUrl());
//            dataSource.setAutoCommit(true);
//            dataSource.setSuppressClose(true);
//            dataSource.setAutoCommit(true);
//            dataSource.setLoginTimeout(1000);
            jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqliteManager getInstance() {
        return mInstance;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public String getUrl() {
        return Config.ROOT_DIR + File.separator + "mock.db";
    }
}
