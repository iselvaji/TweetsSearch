package mobileiron.com.mobileirontest.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobileiron.com.mobileirontest.R;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;
import mobileiron.com.mobileirontest.ui.Detail.DetailsActivity;

/**
 * Created by Selva on 4/27/2018.
 */
public final class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

  private static final String TAG = TweetsAdapter.class.getSimpleName();
  private final Context context;
  private final List<SearchResult> tweets;

  public TweetsAdapter(final Context context, final List<SearchResult> tweets) {
    this.context = context;
    this.tweets = tweets;
    Log.d(TAG, "tweets size " + tweets.size());
  }

  @Override
  public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

    final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tweet, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {

    SearchResult tweet = tweets.get(position);
    Picasso.with(context).load(tweet.getImageuri()).into(holder.imageViewProfile);
    holder.textViewName.setText(tweet.getName());
    holder.textViewMsg.setText(tweet.getContent());

  }

  @Override
  public int getItemCount() {
    return tweets.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.image_avatar) ImageView imageViewProfile;
    @BindView(R.id.textview_name) TextView textViewName;
    @BindView(R.id.textview_message) TextView textViewMsg;

    public ViewHolder(final View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

       SearchResult currentStatus = tweets.get(getAdapterPosition());

       Intent intent = new Intent(context, DetailsActivity.class);
       intent.putExtra("Tweet", currentStatus);
       context.startActivity(intent);
    }
  }
}

