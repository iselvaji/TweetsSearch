package mobileiron.com.mobileirontest.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import mobileiron.com.mobileirontest.data.db.dao.SearchResultDao;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;

import static mobileiron.com.mobileirontest.util.AppConstants.DB_NAME;
import static mobileiron.com.mobileirontest.util.AppConstants.DB_VERSION;

/**
 * Created by Selva on 4/27/2018.
 */

@Database(entities = {SearchResult.class},version = DB_VERSION)
public abstract class MobileIronDatabase extends RoomDatabase {

    public abstract SearchResultDao getSearchResultDao();

    private static MobileIronDatabase mobileIronDatabase;

    public static MobileIronDatabase getInstance(Context context) {
        if (null == mobileIronDatabase) {
            mobileIronDatabase = buildDatabaseInstance(context);
        }
        return mobileIronDatabase;
    }

    private static MobileIronDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MobileIronDatabase.class, DB_NAME)
                .build();
    }

    public static void destroyInstance() {
        mobileIronDatabase = null;
    }
}
