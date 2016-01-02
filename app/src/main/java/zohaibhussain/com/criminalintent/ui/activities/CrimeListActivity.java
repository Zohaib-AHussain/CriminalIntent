package zohaibhussain.com.criminalintent.ui.activities;

import android.support.v4.app.Fragment;
import zohaibhussain.com.criminalintent.ui.activities.Base.SingleFragmentActivity;
import zohaibhussain.com.criminalintent.ui.fragments.CrimeListFragment;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
