package joaogd53.com.br.dialog;

import android.app.DialogFragment;
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

    public ConfirmPresenceDialogFragment() {

    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.confirm_presence_dialog_fragment, container);
        List<Guest> guests = OurWeddingApp.getInstance().findInvitation(this.code);
        adapter = new GuestConfirmAdapter(guests, this.getActivity());
        ListView inviteGuestList = (ListView) view.findViewById(R.id.inviteGuestList);
        inviteGuestList.setAdapter(adapter);
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
}
