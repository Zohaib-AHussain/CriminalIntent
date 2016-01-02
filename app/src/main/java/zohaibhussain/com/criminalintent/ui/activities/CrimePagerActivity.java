package zohaibhussain.com.criminalintent.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import zohaibhussain.com.criminalintent.R;
import zohaibhussain.com.criminalintent.model.Crime;
import zohaibhussain.com.criminalintent.model.CrimeLab;
import zohaibhussain.com.criminalintent.ui.fragments.CrimeFragment;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "zohaibhussain.com.criminalintent.presenter.crime_id";

    @Bind(R.id.activity_crime_pager_view_pager)
    protected ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        ButterKnife.bind(this);
        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mCrimes= CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getID());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++){
            if (mCrimes.get(i).getID().equals(crimeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID crimeID){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

}
