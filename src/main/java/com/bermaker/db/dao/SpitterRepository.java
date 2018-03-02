/**
 * @Title: SpitterRepository.java
 * @Desc: TODO
 * @Package: com.bermaker.db.dao
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:54:59
 * @version 1.0
 */
package com.bermaker.db.dao;

import java.util.List;

import com.bermaker.db.domain.Spitter;

/**
 * ClassName: SpitterRepository
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:54:59
 * @version 1.0
 */
public interface SpitterRepository {

    long count();

    Spitter save(Spitter spitter);

    Spitter findOne(long id);

    Spitter findByUsername(String username);

    List<Spitter> findAll();

}
