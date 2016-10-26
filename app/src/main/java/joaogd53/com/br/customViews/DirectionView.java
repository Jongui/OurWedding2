package joaogd53.com.br.customViews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class DirectionView extends RelativeLayout implements View.OnClickListener {

    private String query;

    public DirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void onClick(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + this.query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        this.getContext().startActivity(mapIntent);
    }
}
