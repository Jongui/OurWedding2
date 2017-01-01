package joaogd53.com.br.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import joaogd53.com.br.adapter.GuestConfirmAdapter;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;
import joaogd53.com.br.sweetalertdialog.SweetAlertDialog;

public class ConfirmPresenceDialogFragment extends DialogFragment implements View.OnClickListener {

    private String code;
    private GuestConfirmAdapter adapter;
    private ListView inviteGuestList;

    public ConfirmPresenceDialogFragment() {

    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.confirm_presence_dialog_fragment, container);
//        List<Guest> guests = OurWeddingApp.getInstance().findInvitation(this.code);
//        adapter = new GuestConfirmAdapter(guests, this.getActivity());
        inviteGuestList = (ListView) view.findViewById(R.id.inviteGuestList);
        new ConfirmPresenceAsyncTask(this.getActivity(), view).execute();
//        inviteGuestList.setAdapter(adapter);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                List<Guest> guests = adapter.getGuestList();
                OurWeddingApp.getInstance().updateGuests(guests);
                new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(view.getContext().getResources().getString(R.string.changes_saved))
                        .show();
                break;
            case R.id.btnCancel:
                break;
        }
        this.dismiss();
    }

    private class ConfirmPresenceAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context context;
        private View view;
        private List<Guest> guests;

        protected ConfirmPresenceAsyncTask(Context context, View view){
            this.context = context;
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... params) {
            guests = OurWeddingApp.getInstance().findInvitation(code);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            view.findViewById(R.id.inviteGuestList).setVisibility(View.VISIBLE);
            adapter = new GuestConfirmAdapter(guests, this.context);
            inviteGuestList.setAdapter(adapter);
        }
    }

}
