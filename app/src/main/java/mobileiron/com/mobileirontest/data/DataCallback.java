package mobileiron.com.mobileirontest.data;

import java.util.List;

import mobileiron.com.mobileirontest.data.db.entity.SearchResult;


/**
 * Created by Selva on 4/29/2018.
 */

public interface DataCallback {

    void onSearchResultsReceived(String key, List<SearchResult> searchResults);

    void onSearchResultsSaved();

    void onOperationError();
}
