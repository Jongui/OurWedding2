package joaogd53.com.br.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import joaogd53.com.br.customViews.DirectionView;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.Store;

public class StoresContentAdapter extends BaseAdapter {
    private Context context;
    private Store store;

    public StoresContentAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @Override
    public int getCount() {
        return 2;
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
                view = vi.inflate(R.layout.store_direction_view, null);
                ((DirectionView) view).setQuery(this.store.getName());
                break;
            default:
                view = vi.inflate(R.layout.store_info_line, null);
                TextView textView = (TextView) view.findViewById(R.id.content_name_view);
                break;
        }
        return view;
    }
}

