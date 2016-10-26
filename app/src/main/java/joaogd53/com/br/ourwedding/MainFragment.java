package joaogd53.com.br.ourwedding;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import joaogd53.com.br.adapter.StoresFoldingCellAdapter;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Store;

/**
 * Created by root on 22/08/16.
 */
public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_stores,
                container, false);
        ListView theListView = (ListView) rootView.findViewById(R.id.mainListView);
        // prepare elements to display
        final List<Store> items = OurWeddingApp.getInstance().getStores();
        final StoresFoldingCellAdapter adapter = new StoresFoldingCellAdapter(this.getActivity(), items);
        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });
        return rootView;
    }
}