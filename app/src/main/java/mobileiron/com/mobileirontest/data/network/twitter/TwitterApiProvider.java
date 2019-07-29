package mobileiron.com.mobileirontest.data.network.twitter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import mobileiron.com.mobileirontest.util.AppConstants;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public final class TwitterApiProvider implements TwitterApi {

  private static final int MAX_TWEETS_PER_REQUEST = 20;
  private static final int API_RATE_LIMIT_EXCEEDED_ERROR_CODE = 88;
  private final Twitter twitterInstance;

  public TwitterApiProvider() {
    final Configuration configuration = createConfiguration();
    final TwitterFactory twitterFactory = new TwitterFactory(configuration);
    twitterInstance = twitterFactory.getInstance();
  }

  private Configuration createConfiguration() {
    final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(AppConstants.TWITTER_CONSUMER_KEY)
        .setOAuthConsumerSecret(AppConstants.TWITTER_CONSUMER_SECRET)
        .setOAuthAccessToken(AppConstants.TWITTER_ACCESS_TOKEN)
        .setOAuthAccessTokenSecret(AppConstants.TWITTER_ACCESS_TOKEN_SECRET);

    return configurationBuilder.build();
  }


  @Override
  public Observable<List<Status>> searchTweets(final String keyword) {
    return Observable.create(new ObservableOnSubscribe<List<Status>>() {
      @Override
      public void subscribe(ObservableEmitter<List<Status>> emitter) throws Exception {

        try {
          final Query query = new Query(keyword).count(MAX_TWEETS_PER_REQUEST);
          final QueryResult result = twitterInstance.search(query);
          emitter.onNext(result.getTweets());
          emitter.onComplete();
        } catch (TwitterException e) {
          emitter.onError(e);
        }

      }

    });
  }

  @Override
  public boolean canSearchTweets(final String keyword) {
    return (!keyword.trim().isEmpty());
  }

}
