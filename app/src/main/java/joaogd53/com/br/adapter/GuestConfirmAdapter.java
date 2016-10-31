package joaogd53.com.br.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.Guest;

/**
 * Created by root on 31/10/16.
 */

public class GuestConfirmAdapter extends BaseAdapter {

    private List<Guest> guestList;
    private Context context;

    public GuestConfirmAdapter(List<Guest> guestList, Context context) {
        this.context = context;
        this.guestList = guestList;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    @Override
    public int getCount() {
        return this.guestList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.guestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = vi.inflate(R.layout.guest_confirm_line, null);
        }
        final Guest guest = this.guestList.get(position);
        TextView txtUserName = (TextView) rootView.findViewById(R.id.txtUserName);
        txtUserName.setText(guest.getPersonName());
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spn_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.context, R.array.status,
                R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int newStatus = position + 1;
                guest.setStatus(newStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int spinnerStatus = guest.getStatus() - 1;
        spinner.setSelection(spinnerStatus);
        ImageView imgGuest = (ImageView) rootView.findViewById(R.id.imgGuest);
        try {
            ImageLoader il = new ImageLoader(this.context);
            il.displayImage(guest.getPersonPhoto().toString(), imgGuest);
        } catch (NullPointerException ex) {
            imgGuest.setImageResource(R.drawable.ic_menu_camera);
        }
        return rootView;
    }
}
