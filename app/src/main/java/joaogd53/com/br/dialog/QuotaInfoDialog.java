package joaogd53.com.br.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Date;

import joaogd53.com.br.customViews.DatePickerEditText;
import joaogd53.com.br.formatters.Formaters;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.HoneyMoonGift;
import joaogd53.com.br.sweetalertdialog.SweetAlertDialog;

/**
 * Created by root on 30/09/16.
 */
public class QuotaInfoDialog extends DialogFragment {

    private HoneyMoonGift gift;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.quota_popup, null);
        Bundle bundle = this.getArguments();
        gift = OurWeddingApp.getInstance().findHoneyMoonGift(bundle.getInt("idHoneyMoonGift"));
        final DatePickerEditText txtDatePicker = (DatePickerEditText) view.findViewById(R.id.txtDatePicker);
        txtDatePicker.setActivity(this.getActivity());
        final EditText txtQuotaVlr = (EditText) view.findViewById(R.id.txtQuotaVlr);
        double doubleHint = 0.00;
        if (gift.getQuota() != 0) {
            doubleHint = gift.getTotalValue() / gift.getQuota();
        }
        txtQuotaVlr.setHint(Formaters.formatCurrency(doubleHint));
        builder.setView(view);
        builder.setView(view).setPositiveButton(R.string.btnSave, null);
        builder.setView(view).setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog ad = builder.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnSave = ad.getButton(AlertDialog.BUTTON_POSITIVE);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!txtDatePicker.validate()) {
                            return;
                        }
                        Double value = 0.00;
                        try {
                            value = Double.parseDouble(txtQuotaVlr.getText().toString());
                        } catch (NullPointerException ex) {
                            new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(view.getContext().getResources().getString(R.string.quota_error))
                                    .setContentText(view.getContext().getResources().getString(R.string.no_value_informed))
                                    .show();
                            return;
                        }
                        Date date = txtDatePicker.getDate();
                        int returnCode = OurWeddingApp.getInstance().buyQuota(gift, value, date);
                        if (returnCode == 0) {
                            new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(view.getContext().getResources().getString(R.string.quota_saved))
                                    .setContentText(view.getContext().getResources().getString(R.string.thanks_quota))
                                    .show();
                        } else {
                            new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(view.getContext().getResources().getString(R.string.quota_error))
                                    .show();
                        }
                        ad.dismiss();
                    }
                });
            }
        });
        return ad;
    }
}
