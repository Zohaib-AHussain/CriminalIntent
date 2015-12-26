package zohaibhussain.com.criminalintent.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by zohaibhussain on 2015-12-25.
 */
public class DateUtil {

    public static String getFormattedDate(Date date){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        return dateFormat.format(date);
    }
}
