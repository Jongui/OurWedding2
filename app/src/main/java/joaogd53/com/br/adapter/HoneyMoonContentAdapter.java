package joaogd53.com.br.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import joaogd53.com.br.customViews.DirectionView;
import joaogd53.com.br.customViews.InternetAddressView;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.HoneyMoonGift;

/**
 * Created by root on 13/09/16.
 */
public class HoneyMoonContentAdapter extends BaseAdapter {
    private Context context;
    private HoneyMoonGift gift;

    public HoneyMoonContentAdapter(Context context, HoneyMoonGift gift) {
        this.context = context;
        this.gift = gift;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            rootView = this.buildViewForPosition(position);
        }
        return rootView;
    }

    private View buildViewForPosition(int position) {
        View view;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (position) {
            case 0:
                view = vi.inflate(R.layout.store_info_line, null);
                TextView textView = (TextView) view.findViewById(R.id.content_name_view);
                textView.setText(this.gift.getDescription());
                break;
            case 1:
                view = vi.inflate(R.layout.place_direction_view, null);
                TextView textView1 = (TextView) view.findViewById(R.id.content_name_view);
                textView1.setText(view.getResources().getString(R.string.see_it_on_maps));
                ((DirectionView) view).setQuery(this.gift.getStreetAddress());
                break;
            default:
                view = vi.inflate(R.layout.gift_uri_view, null);
                ((InternetAddressView) view).setUri(this.gift.getInternetAddress());
                break;
        }
        return view;
    }
}
