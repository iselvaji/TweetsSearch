package mobileiron.com.mobileirontest.data.db;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mobileiron.com.mobileirontest.data.DataCallback;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;

/**
 * Created by Selva on 4/28/2018.
 */

public class DatabaseHelper implements IDatabaseHelper {

    private Context mContext;
    private MobileIronDatabase database;

    public DatabaseHelper(Context context) {
        this.mContext = context;
        database = MobileIronDatabase.getInstance(mContext);
    }

    @Override
    public void searchTweetsOffline(final DataCallback dataCallback, final String searchKey) {
        subSearchTweetsOffline(dataCallback,searchKey).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<List<SearchResult>>() {

            @Override
            public void accept(List<SearchResult> results) throws Exception {
                dataCallback.onSearchResultsReceived(searchKey, results);
            }
        });
    }

    @Override
    public void saveTweetsforOfflineSearch(final DataCallback dataCallback, final List<SearchResult> results) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                for(SearchResult result : results) {
                    database.getSearchResultDao().insert(result);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                dataCallback.onSearchResultsSaved();
            }

            @Override
            public void onError(Throwable e) {
                dataCallback.onOperationError();
            }
        });
    }

    private Observable<List<SearchResult>> subSearchTweetsOffline(final DataCallback dataCallback, final String keyword) {
        return Observable.create(new ObservableOnSubscribe<List<SearchResult>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchResult>> emitter) throws Exception {
                try {
                    emitter.onNext(database.getSearchResultDao().getSearchResultsByKey(keyword));
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    dataCallback.onOperationError();
                }
            }
        });
    }
}
