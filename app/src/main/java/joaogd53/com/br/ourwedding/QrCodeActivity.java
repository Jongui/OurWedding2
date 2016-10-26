package joaogd53.com.br.ourwedding;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.zxing.Result;

import java.util.List;

import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;
import joaogd53.com.br.sweetalertdialog.SweetAlertDialog;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by root on 24/09/16.
 */
public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    final private int MY_PERMISSIONS_REQUEST_CAMERA = 123;

    private ZXingScannerView mScannerView;
    private String userEmail;
    private String userName;
    private Uri personPhoto;
    private RelativeLayout qrcodeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Bundle extra = this.getIntent().getExtras();
        userEmail = extra.getString("USER_EMAIL");
        personPhoto = Uri.parse(extra.getString("PHOTO_URL"));
        userName = extra.getString("USER_NAME");
        qrcodeLayout = (RelativeLayout) this.findViewById(R.id.qrcode_layout);
    }

    public void qrScanner(View view) {
        boolean permission = this.checkCameraPermission();
        if (permission) {
            this.callReader();
        }
    }

    private void callReader() {
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }

    private boolean checkCameraPermission() {
        boolean ret = true;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ret = false;
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }

        return ret;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    this.callReader();
                } else {
                    // Permission Denied
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(this.getResources().getString(R.string.permission_denied))
                            .setContentText(this.getResources().getString(R.string.inser_manualy))
                            .show();

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void informCode(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.inform_code));
        builder.setMessage(this.getResources().getString(R.string.inform_code_2));
        final EditText editText = new EditText(view.getContext());
        builder.setView(editText);
        builder.setPositiveButton(this.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String code = editText.getText().toString().toUpperCase();
                code.toUpperCase();
                final List<Guest> invitationGuests = OurWeddingApp.getInstance().findInvitation(code);
                if (invitationGuests.size() == 0) {
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(view.getResources().getString(R.string.noInviteFound))
                            .setContentText(view.getResources().getString(R.string.contact_us_long))
                            .show();
                    return;
                }
                CharSequence[] names = new CharSequence[invitationGuests.size()];
                for (int i = 0; i < invitationGuests.size(); i++) {
                    names[i] = invitationGuests.get(i).getPersonName();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(view.getResources().getString(R.string.selectGuestName));
                builder.setSingleChoiceItems(names, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        Guest user = invitationGuests.get(selectedPosition);
                    }
                });
                builder.setPositiveButton(view.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        Guest user = invitationGuests.get(selectedPosition);
                        user.setPersonEmail(QrCodeActivity.this.userEmail);
                        user.setPersonPhoto(QrCodeActivity.this.personPhoto);
                        user.setPersonName(QrCodeActivity.this.userName);
                        OurWeddingApp.getInstance().updateUser(user);
//                        new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText(view.getResources().getString(R.string.sucess))
//                                .setContentText(view.getResources().getString(R.string.login_sucess))
//                                .show();
                        QrCodeActivity.this.finish();
                    }
                });
                final AlertDialog ad = builder.create();
                ad.show();
            }
        });
        final AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mScannerView.stopCamera();           // Stop camera on pause
        } catch (NullPointerException ex) {

        }
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        String code = rawResult.getText().toUpperCase();
        final List<Guest> invitationGuests = OurWeddingApp.getInstance().findInvitation(code);
        if (invitationGuests.size() == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(this.getString(R.string.noInviteFound))
                    .setContentText(this.getString(R.string.contact_us_long))
                    .show();
            return;
        }
        CharSequence[] names = new CharSequence[invitationGuests.size()];
        for (int i = 0; i < invitationGuests.size(); i++) {
            names[i] = invitationGuests.get(i).getPersonName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.selectGuestName));
        builder.setSingleChoiceItems(names, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                Guest user = invitationGuests.get(selectedPosition);
            }
        });
        builder.setPositiveButton(this.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                Guest user = invitationGuests.get(selectedPosition);
                user.setPersonEmail(QrCodeActivity.this.userEmail);
                user.setPersonPhoto(QrCodeActivity.this.personPhoto);
                user.setPersonName(QrCodeActivity.this.userName);
                OurWeddingApp.getInstance().updateUser(user);
                QrCodeActivity.this.finish();
            }
        });
        final AlertDialog ad = builder.create();
        ad.show();

        // show the scanner result into dialog box.
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();

        // If you would like to resume scanning, call this method below:
//         mScannerView.resumeCameraPreview(this);
    }


}