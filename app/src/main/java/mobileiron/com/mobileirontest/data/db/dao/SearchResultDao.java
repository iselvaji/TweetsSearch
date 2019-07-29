package mobileiron.com.mobileirontest.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import mobileiron.com.mobileirontest.data.db.entity.SearchResult;

/**
 * Created by Selva on 4/27/2018.
 */

@Dao
public interface SearchResultDao {

    @Insert
    void insert(SearchResult searchResult);

    @Query("SELECT * FROM searchresultshistory where searchkey LIKE  :searchkey")
    List<SearchResult> getSearchResultsByKey(String searchkey);

}
