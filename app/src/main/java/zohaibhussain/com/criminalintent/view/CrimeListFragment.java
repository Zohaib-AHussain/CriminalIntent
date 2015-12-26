package zohaibhussain.com.criminalintent.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import zohaibhussain.com.criminalintent.R;
import zohaibhussain.com.criminalintent.model.Crime;
import zohaibhussain.com.criminalintent.model.CrimeLab;
import zohaibhussain.com.criminalintent.presenter.CrimeActivity;
import zohaibhussain.com.criminalintent.presenter.CrimeListActivity;
import zohaibhussain.com.criminalintent.presenter.CrimePagerActivity;
import zohaibhussain.com.criminalintent.utils.DateUtil;

/**
 * Created by zohaibhussain on 2015-12-15.
 */
public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;

    @Bind(R.id.crime_recycler_view)
    protected RecyclerView mCrimeRecyclerView;

    private CrimeAdapter mAdapter;
    private int mClickedCrimePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        ButterKnife.bind(this,view);
        updateUI();
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    protected class CrimeHolder extends RecyclerView.ViewHolder implements OnClickListener{

        @Bind(R.id.list_item_crime_title_text_view)
        protected TextView mTitleTextView;

        @Bind(R.id.list_item_crime_date_text_view)
        protected TextView mDateTextView;

        @Bind(R.id.list_item_solved_check_box)
        protected CheckBox mSolvedCheckBox;

        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle().toString());
            mDateTextView.setText(DateUtil.getFormattedDate(mCrime.getDate()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getID());
            mClickedCrimePosition = getAdapterPosition();
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyItemChanged(mClickedCrimePosition);
        }
    }

}
