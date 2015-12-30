package zohaibhussain.com.criminalintent.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zohaibhussain.com.criminalintent.model.database.CrimeBaseHelper;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab != null){
            return sCrimeLab;
        }
        sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        mCrimes = new ArrayList<Crime>();
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if(crime.getID().equals(id))
                return crime;
        }
        return null;
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }
}
