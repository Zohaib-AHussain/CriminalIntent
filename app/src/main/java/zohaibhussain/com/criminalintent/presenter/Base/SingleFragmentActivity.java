package zohaibhussain.com.criminalintent.presenter.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import zohaibhussain.com.criminalintent.R;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment==null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, createFragment())
                    .commit();
        }
    }

    protected abstract Fragment createFragment();
}
