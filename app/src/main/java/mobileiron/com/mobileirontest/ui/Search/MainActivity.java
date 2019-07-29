package mobileiron.com.mobileirontest.ui.Search;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobileiron.com.mobileirontest.R;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;
import mobileiron.com.mobileirontest.ui.Adapter.TweetsAdapter;
import mobileiron.com.mobileirontest.ui.BaseActivity;
import mobileiron.com.mobileirontest.util.ConnectivityUtils;

/**
 * Created by Selva on 4/27/2018.
 */
public class MainActivity extends BaseActivity implements SearchContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SearchPresenter mPresenter;

    @BindView(R.id.imageButtonSearch)
    ImageButton mImageButtonSearch;

    @BindView(R.id.editTextSearch)
    EditText mEditTextSearch;

    @BindView(R.id.recycler_view_tweets)
    RecyclerView mRecyclerViewTweets;

    @BindView(R.id.container)
    RelativeLayout mContainerLayout;

    @BindView(R.id.textviewNotweets)
    TextView mTextNoResults;

    @BindString(R.string.no_internet_connection)
    String msgNoInternetConnection;

    @BindString(R.string.no_tweets)
    String msgNoTweets;

    @BindString(R.string.msg_searching)
    String msgSearching;

    @BindString(R.string.err_empty_search)
    String msgEmptySearch;

    @BindString(R.string.err_operation)
    String msgOperationError;

    @BindString(R.string.msg_tweets_saved)
    String msgSaved;

    @BindString(R.string.msg_offline_search)
    String msgOfflineSerach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);

        mPresenter = new SearchPresenter(this,this);

        initUIElements();

    }

    private void initUIElements() {

        mRecyclerViewTweets.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewTweets.setLayoutManager(mLayoutManager);

        mRecyclerViewTweets.setAdapter(new TweetsAdapter(this, new LinkedList<SearchResult>()));
    }

    @OnClick(R.id.imageButtonSearch)
    public void onButtonClick(View view) {

        mTextNoResults.setVisibility(View.GONE);

        String wordToSearch = mEditTextSearch.getText().toString();
        if(! wordToSearch.isEmpty()) {

            if(! ConnectivityUtils.isNetworkConnected(this)) {
                showSnackBar(msgOfflineSerach);
            }
            Toast.makeText(MainActivity.this, msgSearching, Toast.LENGTH_LONG).show();
            mPresenter.searchTweets(wordToSearch);
        }
        else {
            showSnackBar(msgEmptySearch);
        }
    }

    private void showSnackBar(final String message) {
        Snackbar.make(mContainerLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displaySearchResults(List<SearchResult> resultsList) {
        Log.d(TAG," displaySearchResults : size " + resultsList.size());

        if(resultsList.size() == 0) {
            mTextNoResults.setVisibility(View.VISIBLE);
        }

        TweetsAdapter adapter = new TweetsAdapter(MainActivity.this, resultsList);
        mRecyclerViewTweets.setAdapter(adapter);
        mRecyclerViewTweets.invalidate();
    }

    @Override
    public void displaySearchResultsSaved() {
        showSnackBar(msgSaved);
    }

    @Override
    public void displayOperationError() {
        showSnackBar(msgOperationError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.cleanUpUIResources();
    }
}
