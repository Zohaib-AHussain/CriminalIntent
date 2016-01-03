package zohaibhussain.com.criminalintent.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

import zohaibhussain.com.criminalintent.R;
import zohaibhussain.com.criminalintent.model.Crime;
import zohaibhussain.com.criminalintent.ui.activities.Base.SingleFragmentActivity;
import zohaibhussain.com.criminalintent.ui.fragments.CrimeFragment;
import zohaibhussain.com.criminalintent.ui.fragments.CrimeListFragment;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this, crime.getID());
            startActivity(intent);
        }
        else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getID());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
