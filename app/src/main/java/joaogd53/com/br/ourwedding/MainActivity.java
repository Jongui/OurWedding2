package joaogd53.com.br.ourwedding;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import joaogd53.com.br.customViews.CountDownView;
import joaogd53.com.br.dialog.ConfirmPresenceDialogFragment;
import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;
import joaogd53.com.br.sweetalertdialog.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private ImageView imgProfilePic;
    private TextView txtUserName;
    private TextView txtEmail;
    private CountDownView countDownView;
    //    private boolean loginRunning;
    private int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View view = nv.getHeaderView(0);
        imgProfilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
//        loginRunning = false;
        txtUserName = (TextView) view.findViewById(R.id.txtUserName);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        countDownView = (CountDownView) this.findViewById(R.id.countDownView);
        try {
            String frag = this.getIntent().getExtras().get("notificationFragment").toString();
            Log.d("notificationFragment", frag);
            currentFragment = Integer.parseInt(frag);
            Log.d("currentFragment", Integer.toString(this.currentFragment));
        } catch (NullPointerException ex) {
            if (this.currentFragment == 0)
                this.currentFragment = FragmentManagement.INIT_FRAGMENT;
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        nv.setNavigationItemSelectedListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();
        OurWeddingApp.getInstance().setContext(this);
    }

    @TargetApi(23)
    private void checkWriteExternalStoragePermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel(this.getResources().getString(R.string.external_data_permission),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.countDownView != null) {
            this.countDownView.countDownStop();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (this.countDownView != null) {
            this.countDownView.countDownStop();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (!loginRunning) {
//            loginRunning = true;
        this.connectUser();
//        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (this.countDownView != null) {
//            this.countDownView.countDownStart();
//        }
//        if (!loginRunning) {
//            loginRunning = true;
//            this.connectUser();
//        }
//    }

    private void connectUser() {
        mGoogleApiClient.connect();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
//            this.currentFragment = FragmentManagement.INIT_FRAGMENT;
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://joaogd53.com.br.ourwedding/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);

    }

    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            countDownView.countDownStart();
            FragmentManagement.getInstance().setActivity(this);
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            int returnCode = OurWeddingApp.getInstance().logInUser(acct.getIdToken(), acct.getEmail());
            switch (returnCode) {
                case 0:
                    Guest user = OurWeddingApp.getInstance().getUser();
                    try {
                        user.setPersonPhoto(acct.getPhotoUrl());
                        user.setPersonName(acct.getDisplayName());
                        OurWeddingApp.getInstance().updateUser(user);
                        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                        if (currentApiVersion >= Build.VERSION_CODES.M) {
                            this.checkWriteExternalStoragePermission();
                        }
                        ImageLoader il = new ImageLoader(this);
                        il.displayImage(user.getPersonPhoto().toString(), imgProfilePic);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    txtEmail.setText(user.getPersonEmail());
                    txtUserName.setText(user.getPersonName());
                    FragmentManagement.getInstance().callFragment(this.currentFragment, null);
                    break;
                case 1:
                    Intent intent = new Intent(this, QrCodeActivity.class);
                    intent.putExtra("USER_EMAIL", acct.getEmail());
                    intent.putExtra("PHOTO_URL", acct.getPhotoUrl().toString());
                    intent.putExtra("USER_NAME", acct.getDisplayName());
                    startActivity(intent);
                    break;
            }
            if (mProgressDialog != null) {
//                loginRunning = false;
                mProgressDialog.dismiss();
            }
//            updateUI(true);
        } else {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
//            updateUI(false);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count > 1) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStackImmediate(null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                this.currentFragment = FragmentManagement.MAIN_STORES;
                FragmentManagement.getInstance().callFragment(FragmentManagement.MAIN_STORES,
                        null);
            }
        } else if (id == R.id.mnu_honey_moon) {
            this.currentFragment = FragmentManagement.HONEY_MOON_FRAGMENT;
            FragmentManagement.getInstance().callFragment(this.currentFragment, null);
        } else if (id == R.id.mnu_guests) {
            this.currentFragment = FragmentManagement.GUESTS_FRAGMENT;
            FragmentManagement.getInstance().callFragment(this.currentFragment, null);
        } else if (id == R.id.mnu_credits) {
            this.currentFragment = FragmentManagement.CREDITS_FRAGMENT;
            FragmentManagement.getInstance().callFragment(this.currentFragment, null);
        } else if (id == R.id.mnu_home) {
            this.currentFragment = FragmentManagement.INIT_FRAGMENT;
            FragmentManagement.getInstance().callFragment(this.currentFragment, null);
        } else if (id == R.id.mnu_story) {
            this.currentFragment = FragmentManagement.STORY_FRAGMENT;
            FragmentManagement.getInstance().callFragment(this.currentFragment, null);
        } else if (id == R.id.mnu_confirm) {
            this.callConfirmationDialogFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callConfirmationDialogFragment() {
        DialogFragment cpdf = new ConfirmPresenceDialogFragment();
        String code = OurWeddingApp.getInstance().getUser().getCode();
        ((ConfirmPresenceDialogFragment) cpdf).setCode(code);
        cpdf.show(this.getFragmentManager(), "tag");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                this.callEmailIntent();
                break;
        }
    }

    private void callEmailIntent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] itemsNames = new CharSequence[4];
        for (int i = 0; i <= itemsNames.length; i++) {
            switch (i) {
                case 0:
                    itemsNames[i] = getResources().getString(R.string.app_complain);
                    break;
                case 1:
                    itemsNames[i] = getResources().getString(R.string.gift_bought);
                    break;
                case 2:
                    itemsNames[i] = getResources().getString(R.string.confirm_presence);
                    break;
                case 3:
                    itemsNames[i] = getResources().getString(R.string.confirm_absence);
                    break;
            }
        }
        builder.setTitle(getResources().getString(R.string.emailPopupTitle));
        builder.setSingleChoiceItems(itemsNames, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, itemsNames[position]);
                intent.setData(Uri.parse("mailto:joaogd53@gmail.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);
                if (position == 2) {
                    MainActivity.this.callConfirmationDialogFragment();
                }
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // Permission Denied
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(this.getResources().getString(R.string.permission_denied))
                            .setContentText(this.getResources().getString(R.string.pictures_not_show))
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
