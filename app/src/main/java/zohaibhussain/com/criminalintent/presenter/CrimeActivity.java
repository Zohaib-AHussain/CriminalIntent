package zohaibhussain.com.criminalintent.presenter;

import android.support.v4.app.Fragment;

import zohaibhussain.com.criminalintent.presenter.Base.SingleFragmentActivity;
import zohaibhussain.com.criminalintent.view.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
