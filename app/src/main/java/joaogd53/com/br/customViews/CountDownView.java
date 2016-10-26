package joaogd53.com.br.customViews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import joaogd53.com.br.ourwedding.R;

public class CountDownView extends LinearLayout implements View.OnClickListener {

    private Date finalDate;
    private TextView tvDay, tvHour, tvMinute, tvSecond;
    private LinearLayout linearLayout2;
    private Handler handler;
    private Runnable runnable;

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initUI() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            finalDate = sdf.parse("2017-01-14 19:30:00");
        } catch (ParseException e) {
            finalDate = new Date();
        }
        linearLayout2 = (LinearLayout) findViewById(R.id.countDownView);
        tvDay = (TextView) findViewById(R.id.txtTimerDay);
        tvHour = (TextView) findViewById(R.id.txtTimerHour);
        tvMinute = (TextView) findViewById(R.id.txtTimerMinute);
        tvSecond = (TextView) findViewById(R.id.txtTimerSecond);
        tvDay.setOnClickListener(this);
        tvHour.setOnClickListener(this);
        tvMinute.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
    }

    public void countDownStart() {
        this.initUI();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date currentDate = new Date();
                    if (!currentDate.after(finalDate)) {
                        long diff = finalDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        tvDay.setText("" + String.format("%02d", days));
                        tvHour.setText("" + String.format("%02d", hours));
                        tvMinute.setText("" + String.format("%02d", minutes));
                        tvSecond.setText("" + String.format("%02d", seconds));
                    } else {
                        linearLayout2.setVisibility(View.GONE);
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }


    public void countDownStop() {
        if (runnable != null)
            handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
//        this.setVisibility(INVISIBLE);
    }
}
