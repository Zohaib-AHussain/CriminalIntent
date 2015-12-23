package zohaibhussain.com.criminalintent.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import butterknife.Bind;
import butterknife.ButterKnife;
import zohaibhussain.com.criminalintent.R;


public class DatePickerFragment extends android.support.v4.app.DialogFragment {

    private static final String ARG_DATE = "date";
    @Bind(R.id.dialog_date_date_picker) protected DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        ButterKnife.bind(v);
        Date dateToDisplay = (Date) getArguments().get(ARG_DATE);
        passDate(dateToDisplay);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }

    private void passDate(Date dateToDisplay) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(dateToDisplay);
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, month, day, null);
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
