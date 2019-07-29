package mobileiron.com.mobileirontest.data;

import java.util.List;

import mobileiron.com.mobileirontest.data.network.IApiHelper;
import mobileiron.com.mobileirontest.data.db.IDatabaseHelper;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;

/**
 * Created by Selva on 4/28/2018.
 */

public interface IDataManager extends IApiHelper, IDatabaseHelper {

    void searchTweetsOnline(DataCallback dataCallback, String key);
    void searchTweetsOffline(DataCallback dataCallback, String key);
    void saveTweetsforOfflineSearch(DataCallback dataCallback, List<SearchResult> results);
}
