package joaogd53.com.br.ourwedding;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ramotion.foldingcell.FoldingCell;

import joaogd53.com.br.adapter.GuestsFoldingCellAdapter;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;

/**
 * Created by root on 26/08/16.
 */
public class GuestsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_guests,
                container, false);
        final GuestsFoldingCellAdapter adapter = new GuestsFoldingCellAdapter(this.getActivity(),
                OurWeddingApp.getInstance().getGuests());
        ListView listView = (ListView) rootView.findViewById(R.id.guestsListView);
        listView.setAdapter(adapter);
        // set on click event listener to list view
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                // toggle clicked cell state
//                ((FoldingCell) view).toggle(false);
//                // register in adapter that state for selected cell is toggled
//                adapter.registerToggle(pos);
//            }
//        });
        return rootView;
    }
}
