package joaogd53.com.br.customViews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by root on 13/09/16.
 */
public class InternetAddressView extends LinearLayout implements View.OnClickListener{

    private Uri uri;
    public InternetAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }
    public void setUri(Uri uri){
        this.uri = uri;
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, this.uri);
        view.getContext().startActivity(browserIntent);
    }
}
