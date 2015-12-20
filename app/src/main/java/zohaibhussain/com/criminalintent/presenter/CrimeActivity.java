package zohaibhussain.com.criminalintent.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

import zohaibhussain.com.criminalintent.presenter.Base.SingleFragmentActivity;
import zohaibhussain.com.criminalintent.view.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "zohaibhussain.com.criminalintent.presenter.crime_id";

    @Override
    protected Fragment createFragment() {
        return CrimeFragment.newInstance((UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID));
    }

    public static Intent newIntent(Context packageContext, UUID crimeID) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }
}
