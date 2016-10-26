package joaogd53.com.br.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

import joaogd53.com.br.customViews.DataSetFinished;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DataSetFinished dataSetFinished;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setDataSetFinished(DataSetFinished dataSetFinished) {
        this.dataSetFinished = dataSetFinished;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Date datePicked = c.getTime();
        dataSetFinished.onDataSetFinished(this, datePicked);
    }
}
