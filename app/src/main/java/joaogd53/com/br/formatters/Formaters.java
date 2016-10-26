package joaogd53.com.br.formatters;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 30/09/16.
 */
public class Formaters {
    public static String formatDateToString(Date date, Context context) {
        java.text.DateFormat df = DateFormat.getDateFormat(context);
        return df.format(date);
    }

    public static Date formatStringToDate(String date, Context context) throws ParseException {
        java.text.DateFormat df = DateFormat.getDateFormat(context);
        return df.parse(date);
    }

    public static String formatCurrency(double value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(value);
    }

}
