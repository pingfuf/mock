package com.pf.mock;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.File;

public class SqliteManager {
    private static SqliteManager mInstance = new SqliteManager();
    private JdbcTemplate jdbcTemplate;
    private SqliteManager() {
        System.out.println("jdbc:sqlite:" + getUrl());
        DriverManagerDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:" + getUrl());
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
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
