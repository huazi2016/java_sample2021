package com.huazi.jdemo.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/01/13
 * Time: 10:22
 */
public class Rank extends LitePalSupport {
    public int id;
    public int coinCount;
    public int level;
    public int rank;
    public int userId;
    public String username;
}
