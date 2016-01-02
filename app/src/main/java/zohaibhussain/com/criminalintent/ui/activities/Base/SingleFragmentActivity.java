package zohaibhussain.com.criminalintent.ui.activities.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import zohaibhussain.com.criminalintent.R;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    
    
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
