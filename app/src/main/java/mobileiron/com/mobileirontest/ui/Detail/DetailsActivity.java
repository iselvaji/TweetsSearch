package mobileiron.com.mobileirontest.ui.Detail;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobileiron.com.mobileirontest.R;
import mobileiron.com.mobileirontest.data.db.entity.SearchResult;
import mobileiron.com.mobileirontest.ui.BaseActivity;

/**
 * Created by Selva on 4/27/2018.
 */

public class DetailsActivity extends BaseActivity implements DetailContract.View {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @BindView(R.id.image)
    ImageView mImage;

    @BindView(R.id.content)
    TextView mTextContent;

    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // bind the view using butterknife
        ButterKnife.bind(this);

        mPresenter = new DetailPresenter();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            SearchResult result = (SearchResult) extras.get("Tweet");
            Picasso.with(this).load(result.getImageuri()).into(mImage);
            mTextContent.setText(result.getContent());
            Log.d(TAG, "Display Twitter details screen");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.cleanUpUIResources();
    }
}
