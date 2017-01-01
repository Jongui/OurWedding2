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
import joaogd53.com.br.adapter.HoneyMoonFoldingCellAdapter;
import joaogd53.com.br.adapter.StoresFoldingCellAdapter;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;
import joaogd53.com.br.ourweddingapp.model.HoneyMoonGift;
import joaogd53.com.br.ourweddingapp.model.Store;

/**
 * Created by root on 26/08/16.
 */
public class HoneyMoonFragment extends Fragment {
    private ListView theListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_honey_moon,
                container, false);
        theListView = (ListView) rootView.findViewById(R.id.honeyMoonListView);
        new HoneyMoonAsyncTask(this.getActivity(), rootView).execute();
        // prepare elements to display
//        final List<HoneyMoonGift> items = OurWeddingApp.getInstance().getHoneyMoonGifts();
//        final HoneyMoonFoldingCellAdapter adapter = new HoneyMoonFoldingCellAdapter(this.getActivity(), items);
        // set elements to adapter
//        theListView.setAdapter(adapter);

        // set on click event listener to list view
//        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private class HoneyMoonAsyncTask extends AsyncTask<Void,Void, Void>{
        private Context context;
        private View view;
        private List<HoneyMoonGift> honeyMoonGifts;

        protected HoneyMoonAsyncTask(Context context, View view){
            this.context = context;
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.honeyMoonGifts = OurWeddingApp.getInstance().getHoneyMoonGifts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            try {
                final HoneyMoonFoldingCellAdapter adapter = new HoneyMoonFoldingCellAdapter(this.context, honeyMoonGifts);
                theListView.setAdapter(adapter);
                theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        adapter.registerToggle(pos);
                    }
                });
            } catch (Exception ex){
                Toast.makeText(this.context, view.getResources().getString(R.string.guests_not_found), Toast.LENGTH_LONG).show();
            }
        }
    }
}
