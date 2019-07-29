package mobileiron.com.mobileirontest.ui.Search;

import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import mobileiron.com.mobileirontest.data.DataCallback;
import mobileiron.com.mobileirontest.data.DataManager;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;
import mobileiron.com.mobileirontest.ui.BasePresenter;
import mobileiron.com.mobileirontest.util.ConnectivityUtils;

/**
 * Created by Selva on 4/28/2018.
 */

public class SearchPresenter extends BasePresenter implements SearchContract.Presenter,DataCallback {

    private static final String TAG = SearchPresenter.class.getName();
    private Context mContext;
    private WeakReference<SearchContract.View> mView;

    public SearchPresenter(Context context, SearchContract.View view) {
        super();
        this.mContext = context;
        mView = new WeakReference<SearchContract.View>(view);
    }

    @Override
    public void searchTweets(String wordToSearch) {

        if(ConnectivityUtils.isNetworkConnected(mContext)) {
            searchTweetsOnline(wordToSearch);
        }
        else {
            loadOfflineSearchResults(wordToSearch);
        }
    }

    private void searchTweetsOnline(final String searchKey) {
        DataManager.getInstance(mContext).searchTweetsOnline(this, searchKey);
    }

    private void loadOfflineSearchResults(String searchKey) {
        DataManager.getInstance(mContext).searchTweetsOffline(this, searchKey);
    }

    private void saveOfflineSearchResults(List<SearchResult> searchResults) {
        DataManager.getInstance(mContext).saveTweetsforOfflineSearch(this,searchResults);
    }

    @Override
    public void onSearchResultsReceived(String key, List<SearchResult> searchResults) {
        Log.d(TAG, "onSearchResultsReceived - searchResults size :" + searchResults.size());
        if(isViewAlive()) {
            mView.get().displaySearchResults(searchResults);
        }
        if(searchResults.size() > 0)
            saveOfflineSearchResults(searchResults);
    }

    @Override
    public void onSearchResultsSaved() {
        Log.d(TAG, "onSearchResultsSaved ");
        if(isViewAlive()) {
            mView.get().displaySearchResultsSaved();
        }
    }

    @Override
    public void onOperationError() {
        Log.d(TAG, "onOperationError ");
        if(isViewAlive()) {
            mView.get().displayOperationError();
        }
    }

    @Override
    protected void cleanUpUIResources() {
        Log.d(TAG, "cleanUpUIResources ");
        mView = null;
    }

    private boolean isViewAlive(){
        return  mView != null && mView.get() != null;
    }
}
