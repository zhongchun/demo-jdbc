/**
 * @Title: SpittleRepository.java
 * @Desc: TODO
 * @Package: com.bermaker.db.dao
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:52:36
 * @version 1.0
 */
package com.bermaker.db.dao;

import java.util.List;

import com.bermaker.db.domain.Spittle;

/**
 * ClassName: SpittleRepository
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:52:36
 * @version 1.0
 */
public interface SpittleRepository {

    long count();

    List<Spittle> findRecent();

    List<Spittle> findRecent(int count);

    Spittle findOne(long id);

    Spittle save(Spittle spittle);

    List<Spittle> findBySpitterId(long spitterId);

    void delete(long id);

}
