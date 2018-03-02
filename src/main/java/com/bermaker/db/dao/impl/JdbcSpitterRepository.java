/**
 * @Title: JdbcSpitterRepository.java
 * @Desc: TODO
 * @Package: com.bermaker.db.dao.impl
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:56:37
 * @version 1.0
 */
package com.bermaker.db.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.bermaker.db.dao.SpitterRepository;
import com.bermaker.db.domain.Spitter;

/**
 * ClassName: JdbcSpitterRepository
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:56:37
 * @version 1.0
 */
public class JdbcSpitterRepository implements SpitterRepository {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SPITTER =
            "insert into Spitter (username, password, fullname, email, updateByEmail) values (?, ?, ?, ?, ?)";

    private static final String SELECT_SPITTER =
            "select id, username, password, fullname, email, updateByEmail from Spitter";

    /**
     * @Title: JdbcSpitterRepository
     * @Desc: TODO
     */
    public JdbcSpitterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @Title: count
     * @Desc:
     * @return
     * @see com.bermaker.db.dao.SpitterRepository#count()
     */
    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(id) from Spitter", Long.class);
    }

    /**
     * @Title: save
     * @Desc:
     * @param spitter
     * @return
     * @see com.bermaker.db.dao.SpitterRepository#save(com.bermaker.db.domain.Spitter)
     */
    @Override
    public Spitter save(Spitter spitter) {
        Long id = spitter.getId();
        if (id == null) {
            long spitterId = insertSpitterAndReturnId(spitter);
            return new Spitter(spitterId, spitter.getUsername(), spitter.getPassword(), spitter.getFullName(),
                    spitter.getEmail(), spitter.isUpdateByEmail());
        } else {
            jdbcTemplate.update(
                    "update Spitter set username=?, password=?, fullname=?, email=?, updateByEmail=? where id=?",
                    spitter.getUsername(), spitter.getPassword(), spitter.getFullName(), spitter.getEmail(),
                    spitter.isUpdateByEmail(), id);
        }
        return spitter;
    }

    /**
     * @Title: insertSpitterAndReturnId
     * @Desc: TODO
     * @param spitter
     * @return
     */
    private long insertSpitterAndReturnId(Spitter spitter) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spitter");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("username", spitter.getUsername());
        args.put("password", spitter.getPassword());
        args.put("fullname", spitter.getFullName());
        args.put("email", spitter.getEmail());
        args.put("updateByEmail", spitter.isUpdateByEmail());
        long spitterId = jdbcInsert.executeAndReturnKey(args).longValue();
        return spitterId;
    }

    /**
     * @Title: findOne
     * @Desc:
     * @param id
     * @return
     * @see com.bermaker.db.dao.SpitterRepository#findOne(long)
     */
    @Override
    public Spitter findOne(long id) {
        return jdbcTemplate.queryForObject(SELECT_SPITTER + " where id=?", new SpitterRowMapper(), id);
    }

    /**
     * @Title: findByUsername
     * @Desc:
     * @param username
     * @return
     * @see com.bermaker.db.dao.SpitterRepository#findByUsername(java.lang.String)
     */
    @Override
    public Spitter findByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "select id, username, password, fullname, email, updateByEmail from Spitter where username=?",
                new SpitterRowMapper(), username);
    }

    /**
     * @Title: findAll
     * @Desc:
     * @return
     * @see com.bermaker.db.dao.SpitterRepository#findAll()
     */
    @Override
    public List<Spitter> findAll() {
        return jdbcTemplate.query(
                "select id, username, password, fullname, email, updateByEmail from Spitter order by id",
                new SpitterRowMapper());
    }

    @SuppressWarnings("unused")
    private void insertSpitter(Spitter spitter) {
        jdbcTemplate.update(INSERT_SPITTER, spitter.getUsername(), spitter.getPassword(), spitter.getFullName(),
                spitter.getEmail(), spitter.isUpdateByEmail());
    }

    private static final class SpitterRowMapper implements RowMapper<Spitter> {
        /**
         * @Title: mapRow
         * @Desc:
         * @param rs
         * @param rowNum
         * @return
         * @throws SQLException
         * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
         */
        @Override
        public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String fullName = rs.getString("fullname");
            String email = rs.getString("email");
            boolean updateByEmail = rs.getBoolean("updateByEmail");
            return new Spitter(id, username, password, fullName, email, updateByEmail);
        }
    }

}
