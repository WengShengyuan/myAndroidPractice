package database;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by Administrator on 2016/2/22.
 */
public class DbUtils {

    private volatile static DbUtils instance;

    public static DbUtils getInstance() {
        if (instance == null) {
            synchronized (DbUtils.class) {
                if (instance == null) {
                    instance = new DbUtils();
                }
            }
        }
        return instance;
    }

    private DbManager.DaoConfig daoConfig;
    private DbManager db;
    private DbUtils() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("test.db")
                        // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(2)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        db = x.getDb(daoConfig);
    }

    public DbManager getDb() {
        return db;
    }
}
