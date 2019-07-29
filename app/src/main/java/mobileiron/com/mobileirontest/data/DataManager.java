package mobileiron.com.mobileirontest.data;

import android.content.Context;

import java.util.List;

import mobileiron.com.mobileirontest.data.db.IDatabaseHelper;
import mobileiron.com.mobileirontest.data.network.ApiHelper;
import mobileiron.com.mobileirontest.data.db.DatabaseHelper;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;
import mobileiron.com.mobileirontest.data.network.IApiHelper;

/**
 * Created by Selva on 4/28/2018.
 */

public class DataManager implements IDataManager {

    private static DataManager mDataManager = null;
    private IApiHelper mApiHelper = null;
    private IDatabaseHelper mDatabaseHelper = null;

    private DataManager(Context context) {
        mApiHelper = new ApiHelper();
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public static DataManager getInstance(Context context) {

        if(mDataManager == null) {
            mDataManager = new DataManager(context);
        }
        return mDataManager;
    }

    @Override
    public void searchTweetsOnline(DataCallback dataCallback, String key) {
        mApiHelper.searchTweetsOnline(dataCallback,key);
    }

    @Override
    public void searchTweetsOffline(DataCallback dataCallback, String key) {
        mDatabaseHelper.searchTweetsOffline(dataCallback,key);
    }

    @Override
    public void saveTweetsforOfflineSearch(DataCallback dataCallback, List<SearchResult> results) {
        mDatabaseHelper.saveTweetsforOfflineSearch(dataCallback,results);
    }
}
