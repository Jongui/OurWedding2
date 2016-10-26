package joaogd53.com.br.ourweddingapp.thread;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by root on 24/09/16.
 */
public abstract class AbstractConnection implements CustomRunnableInterface {
    protected Activity context;
    protected int returnCode;
    protected ProgressDialog mProgressDialog;

    public AbstractConnection(Activity context) {
        this.context = context;
        this.mProgressDialog = new ProgressDialog(context);
    }

    public int getReturnCode() {
        return this.returnCode;
    }
}
