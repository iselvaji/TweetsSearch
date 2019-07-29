package mobileiron.com.mobileirontest.ui.Search;

import java.util.List;

import mobileiron.com.mobileirontest.data.db.entity.SearchResult;

/**
 * Created by Selva on 4/28/2018.
 */

public interface SearchContract {

    interface View {
        void displaySearchResults(List<SearchResult> resultsList);
        void displaySearchResultsSaved();
        void displayOperationError();
    }

    interface Presenter {
        void searchTweets(String wordToSearch);
    }

}
