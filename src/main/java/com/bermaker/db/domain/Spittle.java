/**
 * @Title: Spittle.java
 * @Desc: TODO
 * @Package: com.bermaker.db.domain
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:51:59
 * @version 1.0
 */
package com.bermaker.db.domain;

import java.util.Date;

/**
 * ClassName: Spittle
 * 
 * @Desc: TODO
 * @author: yuzhongchun(yuzhongchun@baidu.com)
 * @date: 2018年3月1日 下午2:51:59
 * @version 1.0
 */
public class Spittle {
    private final Long id;
    private final Spitter spitter;
    private final String message;
    private final Date postedTime;

    public Spittle(Long id, Spitter spitter, String message, Date postedTime) {
        this.id = id;
        this.spitter = spitter;
        this.message = message;
        this.postedTime = postedTime;
    }

    public Long getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getPostedTime() {
        return this.postedTime;
    }

    public Spitter getSpitter() {
        return this.spitter;
    }

}
