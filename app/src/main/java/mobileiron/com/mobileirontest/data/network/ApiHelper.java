package mobileiron.com.mobileirontest.data.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mobileiron.com.mobileirontest.data.DataCallback;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;
import mobileiron.com.mobileirontest.data.network.twitter.TwitterApi;
import mobileiron.com.mobileirontest.data.network.twitter.TwitterApiProvider;
import twitter4j.Status;

/**
 * Created by Selva on 4/28/2018.
 */

public class ApiHelper implements IApiHelper {

    private static final String TAG = ApiHelper.class.getName();

    @Override
    public void searchTweetsOnline(final DataCallback dataCallback, final String searchKey) {

        Log.d(TAG, "search tweets with keyword " + searchKey);

        final TwitterApi mTwitterApi = new TwitterApiProvider();

        if (!mTwitterApi.canSearchTweets(searchKey)) {
            Log.d(TAG, "cannot search tweets - invalid keyword :" + searchKey);
            dataCallback.onOperationError();
            return;
        }

        mTwitterApi.searchTweets(searchKey).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<List<Status>>() {

                    @Override
                    public void accept(List<Status> statusList) throws Exception {

                        List<SearchResult> results = getSearchResult(searchKey, statusList);
                        dataCallback.onSearchResultsReceived(searchKey, results);

                    }
                });
    }


    private List<SearchResult> getSearchResult(String key, List<Status> StatusList) {

        List<SearchResult> results = new ArrayList<>();

        for(Status status : StatusList) {
            SearchResult searchResult = new SearchResult();
            searchResult.setKey(key);
            searchResult.setName(status.getUser().getName());
            searchResult.setContent(status.getText());
            searchResult.setImageuri(status.getUser().getProfileImageURL());

            results.add(searchResult);
        }

        return results;
    }
}
