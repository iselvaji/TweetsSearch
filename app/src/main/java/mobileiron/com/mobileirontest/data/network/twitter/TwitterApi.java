package mobileiron.com.mobileirontest.data.network.twitter;

import java.util.List;

import io.reactivex.Observable;
import twitter4j.Status;

public interface TwitterApi {

  Observable<List<Status>> searchTweets(final String keyword);
  boolean canSearchTweets(final String keyword);
}
