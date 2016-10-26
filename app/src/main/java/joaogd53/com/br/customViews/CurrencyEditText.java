package joaogd53.com.br.customViews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 01/10/16.
 */
public class CurrencyEditText extends EditText {

    private Activity activity;


    public CurrencyEditText(Context context) {
        super(context);
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
