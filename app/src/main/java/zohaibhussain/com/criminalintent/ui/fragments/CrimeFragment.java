package zohaibhussain.com.criminalintent.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zohaibhussain.com.criminalintent.R;
import zohaibhussain.com.criminalintent.model.Crime;
import zohaibhussain.com.criminalintent.model.CrimeLab;
import zohaibhussain.com.criminalintent.utils.DateUtils;
import zohaibhussain.com.criminalintent.utils.PicUtils;

/**
 * Created by zohaibhussain on 2015-12-12.
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;
    private final Intent PICK_CONTACT_INTENT = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    private final Intent CAPTURE_IMAGE = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    private Crime mCrime;
    private File mPhotoFile;
    private Callbacks mCallbacks;

    public interface Callbacks{
         void onCrimeUpdated(Crime crime);
    }

    @Bind(R.id.crime_title)
    protected EditText mTitleField;

    @Bind(R.id.crime_date)
    protected Button mDateButton;

    @Bind(R.id.crime_solved)
    protected CheckBox mSolvedCheckBox;

    @Bind(R.id.crime_report)
    protected Button mReportButton;

    @Bind(R.id.crime_suspect)
    protected Button mSuspectButton;

    @Bind(R.id.crime_photo)
    protected ImageView mPhotoView;

    @Bind(R.id.crime_camera)
    protected ImageButton mPhotoButton;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID crimeID = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        ButterKnife.bind(this, v);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mCrime.setTitle(charSequence.toString());
                updateCrime(mCrime);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTitleField.setText(mCrime.getTitle());
        updateDate();
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
                updateCrime(mCrime);
            }
        });
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        setSuspectButtonText();
        if (!deviceContainsContactPicker())
            mSuspectButton.setEnabled(false);
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
        boolean canTakePhoto = mPhotoFile != null && CAPTURE_IMAGE.resolveActivity(getActivity().getPackageManager()) != null;
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            CAPTURE_IMAGE.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            mPhotoButton.setEnabled(true);
        }
        else {
            mPhotoButton.setEnabled(false);
        }
        updatePhotoView();

        return v;
    }

    private boolean deviceContainsContactPicker() {
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(PICK_CONTACT_INTENT, packageManager.MATCH_DEFAULT_ONLY)==null)
            return false;
        return true;
    }

    private void setSuspectButtonText() {
        if (mCrime.getSuspect() != null)
            mSuspectButton.setText(mCrime.getSuspect());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
            updateCrime(mCrime);
        }
        else if (requestCode == REQUEST_CONTACT && data != null){
            Uri contactUri = data.getData();
            String[] queryField = new String[]{ ContactsContract.Contacts.DISPLAY_NAME };
            Cursor c = getActivity().getContentResolver().query(contactUri, queryField, null,null,null);
            try{
                if (c.getCount()==0)
                    return;
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);
                setSuspectButtonText();
                updateCrime(mCrime);
            }finally {
                c.close();
            }
        }
        else if (requestCode == REQUEST_PHOTO){
            updatePhotoView();
            updateCrime(mCrime);
        }

    }

    private void updateDate() {
        mDateButton.setText(DateUtils.getFormattedDate(mCrime.getDate()));
    }

    @OnClick(R.id.crime_date)
    public void onClickDateButton(){
        FragmentManager fragmentManager = getFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
        dialog.show(fragmentManager, DIALOG_DATE);
    }

    @OnClick(R.id.crime_report)
    public void onClickReportButton(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
        i = Intent.createChooser(i, getString(R.string.send_report));
        startActivity(i);
    }

    @OnClick(R.id.crime_suspect)
    public void onClickSuspectButton(){
        startActivityForResult(PICK_CONTACT_INTENT, REQUEST_CONTACT);
    }

    @OnClick(R.id.crime_camera)
    public void onClickCrimeCameraButton(){
        startActivityForResult(CAPTURE_IMAGE, REQUEST_PHOTO);
    }

    public static CrimeFragment newInstance(UUID crimeID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    private String getCrimeReport(){
        String solvedString;
        if (mCrime.isSolved())
            solvedString = getString(R.string.crime_report_solved);
        else
            solvedString = getString(R.string.crime_report_unsolved);

        String dateString = DateUtils.getFormattedDate(mCrime.getDate());
        String suspect = mCrime.getSuspect();
        if (suspect == null)
            suspect = getString(R.string.crime_report_no_suspect);
        else
            suspect = getString(R.string.crime_report_suspect, suspect);

        String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);
        return report;

    }

    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        }
        else {
            Bitmap bitmap = PicUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private void updateCrime(Crime crime){
        CrimeLab.get(getActivity()).updateCrime(crime);
        mCallbacks.onCrimeUpdated(crime);
    }

    @Override
    public void onPause(){
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
