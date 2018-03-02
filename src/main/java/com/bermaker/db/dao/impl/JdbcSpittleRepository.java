/**
 * @Title: JdbcSpittleRepository.java
 * @Desc: TODO
 * @Package: com.bermaker.db.dao.impl
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午3:26:51
 * @version 1.0
 */
package com.bermaker.db.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.bermaker.db.dao.SpittleRepository;
import com.bermaker.db.domain.Spitter;
import com.bermaker.db.domain.Spittle;

/**
 * ClassName: JdbcSpittleRepository
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午3:26:51
 * @version 1.0
 */
public class JdbcSpittleRepository implements SpittleRepository {

    private static final String SELECT_SPITTLE =
            "select sp.id, s.id as spitterId, s.username, s.password, s.fullname, s.email, s.updateByEmail, sp.message, sp.postedTime from Spittle sp, Spitter s where sp.spitter = s.id";
    private static final String SELECT_SPITTLE_BY_ID = SELECT_SPITTLE + " and sp.id=?";
    private static final String SELECT_SPITTLES_BY_SPITTER_ID =
            SELECT_SPITTLE + " and s.id=? order by sp.postedTime desc";
    private static final String SELECT_RECENT_SPITTLES = SELECT_SPITTLE + " order by sp.postedTime desc limit ?";

    private JdbcTemplate jdbcTemplate;

    /**
     * @Title: JdbcSpittleRepository
     * @Desc: TODO
     */
    public JdbcSpittleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @Title: count
     * @Desc:
     * @return
     * @see com.bermaker.db.dao.SpittleRepository#count()
     */
    @Override
    public long count() {
        return jdbcTemplate.queryForObject("select count(id) from Spittle", Long.class);
    }

    /**
     * @Title: findRecent
     * @Desc:
     * @return
     * @see com.bermaker.db.dao.SpittleRepository#findRecent()
     */
    @Override
    public List<Spittle> findRecent() {
        return findRecent(10);
    }

    /**
     * @Title: findRecent
     * @Desc:
     * @param count
     * @return
     * @see com.bermaker.db.dao.SpittleRepository#findRecent(int)
     */
    @Override
    public List<Spittle> findRecent(int count) {
        return jdbcTemplate.query(SELECT_RECENT_SPITTLES, new SpittleRowMapper(), count);
    }

    /**
     * @Title: findOne
     * @Desc:
     * @param id
     * @return
     * @see com.bermaker.db.dao.SpittleRepository#findOne(long)
     */
    @Override
    public Spittle findOne(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_SPITTLE_BY_ID, new SpittleRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Title: save
     * @Desc:
     * @param spittle
     * @return
     * @see com.bermaker.db.dao.SpittleRepository#save(com.bermaker.db.domain.Spittle)
     */
    @Override
    public Spittle save(Spittle spittle) {
        long spittleId = insertSpittleAndReturnId(spittle);
        return new Spittle(spittleId, spittle.getSpitter(), spittle.getMessage(), spittle.getPostedTime());
    }

    /**
     * @Title: findBySpitterId
     * @Desc:
     * @param spitterId
     * @return
     * @see com.bermaker.db.dao.SpittleRepository#findBySpitterId(long)
     */
    @Override
    public List<Spittle> findBySpitterId(long spitterId) {
        return jdbcTemplate.query(SELECT_SPITTLES_BY_SPITTER_ID, new SpittleRowMapper(), spitterId);
    }

    /**
     * @Title: delete
     * @Desc:
     * @param id
     * @see com.bermaker.db.dao.SpittleRepository#delete(long)
     */
    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from Spittle where id=?", id);
    }

    /**
     * @Title: insertSpittleAndReturnId
     * @Desc: TODO
     * @param spittle
     * @return
     */
    private long insertSpittleAndReturnId(Spittle spittle) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spittle");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("spitter", spittle.getSpitter().getId());
        args.put("message", spittle.getMessage());
        args.put("postedTime", spittle.getPostedTime());
        long spittleId = jdbcInsert.executeAndReturnKey(args).longValue();
        return spittleId;
    }

    private static final class SpittleRowMapper implements RowMapper<Spittle> {

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
        public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String message = rs.getString("message");
            Date postedTime = rs.getTimestamp("postedTime");
            long spitterId = rs.getLong("spitterId");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String fullName = rs.getString("fullname");
            String email = rs.getString("email");
            boolean updateByEmail = rs.getBoolean("updateByEmail");
            Spitter spitter = new Spitter(spitterId, username, password, fullName, email, updateByEmail);
            return new Spittle(id, spitter, message, postedTime);
        }

    }

}
