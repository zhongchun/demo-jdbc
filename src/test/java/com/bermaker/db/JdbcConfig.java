/**
 * @Title: JdbcConfig.java
 * @Desc: TODO
 * @Package: com.bermaker.db
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午3:44:24
 * @version 1.0
 */
package com.bermaker.db;

import javax.sql.DataSource;

//import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.bermaker.db.dao.SpitterRepository;
import com.bermaker.db.dao.SpittleRepository;
import com.bermaker.db.dao.impl.JdbcSpitterRepository;
import com.bermaker.db.dao.impl.JdbcSpittleRepository;

/**
 * ClassName: JdbcConfig
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午3:44:24
 * @version 1.0
 */
@Configuration
public class JdbcConfig {

    // @Bean
    // public BasicDataSource basicDataSource() {
    // BasicDataSource ds = new BasicDataSource();
    // ds.setDriverClassName("org.h2.Driver");
    // ds.setUrl("jdbc:h2:tcp://localhost/~/spitter");
    // ds.setInitialSize(5);
    // return ds;
    // }

    @Bean
    public DataSource dataSource() {
        // DriverManagerDataSource dataSource = null;
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScripts("classpath:db/schema.sql", "classpath:db/test-data.sql").build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SpitterRepository spitterRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcSpitterRepository(jdbcTemplate);
    }

    @Bean
    public SpittleRepository spittleRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcSpittleRepository(jdbcTemplate);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
