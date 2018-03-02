/**
 * @Title: JdbcSpittleRepositoryTest.java
 * @Desc: TODO
 * @Package: com.bermaker.db
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午7:39:19
 * @version 1.0
 */
package com.bermaker.db;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bermaker.db.dao.impl.JdbcSpittleRepository;
import com.bermaker.db.domain.Spitter;
import com.bermaker.db.domain.Spittle;

/**
 * ClassName: JdbcSpittleRepositoryTest
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午7:39:19
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
public class JdbcSpittleRepositoryTest {

    @Autowired
    JdbcSpittleRepository spittleRepository;

    @Test
    public void count() {
        assertEquals(15, spittleRepository.count());
    }

    @Test
    public void findRecent() {
        {
            List<Spittle> recent = spittleRepository.findRecent();
            assertRecent(recent, 10);
        }

        {
            List<Spittle> recent = spittleRepository.findRecent(5);
            assertRecent(recent, 5);
        }
    }

    @Test
    public void findOne() {
        Spittle thirteen = spittleRepository.findOne(13);
        assertEquals(13, thirteen.getId().longValue());
        assertEquals("Bonjour from Art!", thirteen.getMessage());
        assertEquals(1332682500000L, thirteen.getPostedTime().getTime());
        assertEquals(4, thirteen.getSpitter().getId().longValue());
        assertEquals("artnames", thirteen.getSpitter().getUsername());
        assertEquals("password", thirteen.getSpitter().getPassword());
        assertEquals("Art Names", thirteen.getSpitter().getFullName());
        assertEquals("art@habuma.com", thirteen.getSpitter().getEmail());
        assertTrue(thirteen.getSpitter().isUpdateByEmail());
    }

    @Test
    public void findBySpitterId() {
        List<Spittle> spittles = spittleRepository.findBySpitterId(4L);
        assertEquals(11, spittles.size());
        for (int i = 0; i < 11; i++) {
            assertEquals(15 - i, spittles.get(i).getId().longValue());
        }
    }

    @Test
    @Transactional
    public void save() {
        assertEquals(15, spittleRepository.count());
        Spitter spitter = spittleRepository.findOne(13).getSpitter();
        Spittle spittle = new Spittle(null, spitter, "Un Nuevo Spittle from Art", new Date());
        Spittle saved = spittleRepository.save(spittle);
        assertEquals(16, spittleRepository.count());
        assertNewSpittle(saved);
        assertNewSpittle(spittleRepository.findOne(16L));
    }

    @Test
    @Transactional
    public void delete() {
        assertEquals(15, spittleRepository.count());
        assertNotNull(spittleRepository.findOne(13));
        spittleRepository.delete(13L);
        assertEquals(14, spittleRepository.count());
        assertNull(spittleRepository.findOne(13));
    }

    /**
     * @Title: assertRecent
     * @Desc: TODO
     * @param recent
     * @param count
     */
    private void assertRecent(List<Spittle> recent, int count) {
        assertEquals(count, recent.size());
        long[] recentIds = new long[] { 3, 2, 1, 15, 14, 13, 12, 11, 10, 9 };
        for (int i = 0; i < count; i++) {
            assertEquals(recentIds[i], recent.get(i).getId().longValue());
        }
    }

    /**
     * @Title: assertNewSpittle
     * @Desc: TODO
     * @param spittle
     */
    private void assertNewSpittle(Spittle spittle) {
        assertEquals(16, spittle.getId().longValue());
    }

}
