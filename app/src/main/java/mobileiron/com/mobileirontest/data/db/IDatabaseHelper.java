package mobileiron.com.mobileirontest.data.db;

import java.util.List;
import mobileiron.com.mobileirontest.data.DataCallback;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;

/**
 * Created by sm5 on 4/28/2018.
 */

public interface IDatabaseHelper {

    void searchTweetsOffline(DataCallback dataCallback, String searchKey);

    void saveTweetsforOfflineSearch(DataCallback dataCallback, List<SearchResult> results);
}
