package joaogd53.com.br.ourwedding;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import joaogd53.com.br.adapter.GuestsFoldingCellAdapter;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;

/**
 * Created by root on 26/08/16.
 */
public class GuestsFragment extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_guests,
                container, false);
//        final GuestsFoldingCellAdapter adapter = new GuestsFoldingCellAdapter(this.getActivity(),
//                OurWeddingApp.getInstance().getGuests());
        listView = (ListView) rootView.findViewById(R.id.guestsListView);
        new GuestAsyncTask(this.getActivity(), rootView).execute();
//        GuestAsyncTask task = new GuestAsyncTask(this.getActivity(), rootView);
//        task.execute();
//        listView.setAdapter(adapter);
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

    private class GuestAsyncTask extends AsyncTask<Void, Void, Void>{
        private Context context;
        private View view;
        private List<Guest> guests;

        protected GuestAsyncTask(Context context, View view){
            this.context = context;
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.guests = OurWeddingApp.getInstance().getGuests();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            try {
                GuestsFoldingCellAdapter adapter = new GuestsFoldingCellAdapter(this.context,
                        this.guests);
                listView.setAdapter(adapter);
            } catch (Exception ex){
                Toast.makeText(this.context, view.getResources().getString(R.string.guests_not_found), Toast.LENGTH_LONG).show();
            }
        }
    }

}
