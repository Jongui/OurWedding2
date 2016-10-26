package joaogd53.com.br.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.Guest;

/**
 * Created by root on 31/08/16.
 */
public class GuestContentAdapter extends BaseAdapter {
    private Guest guest;
    private Context context;

    public GuestContentAdapter(Context context, Guest guest) {
        this.context = context;
        this.guest= guest;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = vi.inflate(R.layout.guests_content_line_view, null);
        }
        TextView textView = (TextView) rootView.findViewById(R.id.lblGuestName);
        textView.setText(guest.getPersonName());
        ImageView imgHead = (ImageView) rootView.findViewById(R.id.imgUser);
        imgHead.setVisibility(View.INVISIBLE);
        return rootView;
    }
}
