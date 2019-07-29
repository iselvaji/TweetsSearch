package mobileiron.com.mobileirontest.data.network;

import mobileiron.com.mobileirontest.data.DataCallback;

/**
 * Created by Selva on 4/28/2018.
 */

public interface IApiHelper {

    void searchTweetsOnline(DataCallback dataCallback, final String searchKey);
}
