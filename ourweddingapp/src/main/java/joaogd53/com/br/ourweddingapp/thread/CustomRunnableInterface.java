package joaogd53.com.br.ourweddingapp.thread;

import org.json.JSONException;

import java.io.IOException;
import java.net.URLConnection;

public interface CustomRunnableInterface extends Runnable {
    void writeOutput(URLConnection connection) throws IOException;

    void writeInput(URLConnection connection) throws IOException;
}
