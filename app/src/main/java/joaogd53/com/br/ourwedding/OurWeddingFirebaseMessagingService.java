package joaogd53.com.br.ourwedding;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by root on 24/10/16.
 */
public class OurWeddingFirebaseMessagingService extends FirebaseMessagingService {

    private final static String TAG = "TAG";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        Map<String, String> data = remoteMessage.getData();
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        String fragment = (String) data.get("notificationFragment");
        Log.i(TAG, "onMessageReceived: title : "+title);
        Log.i(TAG, "onMessageReceived: message : "+message);
        Log.i(TAG, "onMessageReceived: imageUrl : "+fragment);

    }
}
