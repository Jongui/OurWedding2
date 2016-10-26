package joaogd53.com.br.customViews;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import joaogd53.com.br.dialog.DatePicker;
import joaogd53.com.br.formatters.Formaters;
import joaogd53.com.br.ourwedding.R;

/**
 * Created by root on 01/10/16.
 */
public class DatePickerEditText extends EditText implements View.OnClickListener,
        DataSetFinished {

    private Activity activity;
    private DatePicker datePicker;
    private Date date;

    public DatePickerEditText(Context context) {
        super(context);
        this.setInputType(InputType.TYPE_NULL);
        this.setOnClickListener(this);
        this.datePicker = new DatePicker();
        this.datePicker.setDataSetFinished(this);
        this.date = new Date();
        this.setText(Formaters.formatDateToString(this.date, this.activity));
    }

    public DatePickerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setInputType(InputType.TYPE_NULL);
        this.setOnClickListener(this);
        this.datePicker = new DatePicker();
        this.datePicker.setDataSetFinished(this);
        this.date = new Date();
        this.setText(Formaters.formatDateToString(this.date, context));
    }

    public DatePickerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setInputType(InputType.TYPE_NULL);
        this.setOnClickListener(this);
        this.datePicker = new DatePicker();
        this.datePicker.setDataSetFinished(this);
        this.date = new Date();
        this.setText(Formaters.formatDateToString(this.date, context));
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean validate() {
        boolean ret = true;
        Date today = new Date();
        if (today.before(this.date)) {
            this.setError(activity.getResources().getString(R.string.date_in_the_future));
            ret = false;
        }
        return ret;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = this.activity.getFragmentManager();
        datePicker.show(fm, "datePicker");
    }

    @Override
    public void onDataSetFinished(DatePicker datePicker, Date datePicked) {
        this.date = datePicked;
        this.setText(Formaters.formatDateToString(this.date, this.activity));
    }
}
