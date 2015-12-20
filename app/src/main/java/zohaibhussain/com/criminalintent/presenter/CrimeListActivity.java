package zohaibhussain.com.criminalintent.presenter;

import android.support.v4.app.Fragment;
import zohaibhussain.com.criminalintent.presenter.Base.SingleFragmentActivity;
import zohaibhussain.com.criminalintent.view.CrimeListFragment;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
