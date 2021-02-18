package com.example.demowork1.database.greendao;

/**
 * 初始化、存放及获取DaoUtils
 */
public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance = new DaoUtilsStore();
    private CommonDaoUtils<User> mUserDaoUtils;

    public static DaoUtilsStore getInstance() {
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();
        UserDao _UserDao = mManager.getDaoSession().getUserDao();
        mUserDaoUtils = new CommonDaoUtils<>(User.class, _UserDao);
    }

    public CommonDaoUtils<User> getUserDaoUtils() {
        return mUserDaoUtils;
    }

}