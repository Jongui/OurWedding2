package joaogd53.com.br.ourweddingapp.thread;

import android.content.Context;

import java.net.URL;
import java.net.URLConnection;

public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;
    private Context context;
    public static final int LOGIN_SERVLET = 1;
    public static final int UPDATE_USER_SERVLET = 2;
    public static final int READ_QR_CODE_SERVLET = 3;
    public static final int GET_ALL_GUESTS_SERVLET = 4;
    public static final int GET_ALL_HONEY_MOON_GIFTS_SERVLET = 5;
    public static final int QUOTA_SAVE_SERVLET = 6;
    public static final int GET_LAST_STORIES_SERVLET = 7;
    public static final int GUEST_BY_ID_SERVLET = 8;
    public static final int GET_STORY_COMMENTS_SERVLET = 9;
    public static final int SAVE_COMMENT_RUNNABLE = 10;
    public static final int GET_ALL_STORIES_SERVLET = 11;
    public static final int UPDATE_GUESTS_SERLVET = 12;

    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public URLConnection connectionFactory(int servlet) {
        try {
            URL url = new URL("http://");
            switch (servlet) {
//                case LOGIN_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/LoginServlet");
//                    break;
//                case UPDATE_USER_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/UpdateUserServlet");
//                    break;
//                case READ_QR_CODE_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/ReadQRCodeServlet");
//                    break;
//                case GET_ALL_GUESTS_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/GetAllGuestsServer");
//                    break;
//                case GET_ALL_HONEY_MOON_GIFTS_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/GetAllGiftsServlet");
//                    break;
//                case QUOTA_SAVE_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/QuotaSaveServlet");
//                    break;
//                case GET_LAST_STORIES_SERVLET:
//                    url = new URL("http://ourweddingapplicati-env.us-east-1.elasticbeanstalk.com/GetLastStoryServlet");
//                    break;
                case LOGIN_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/LoginServlet");
                    break;
                case UPDATE_USER_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/UpdateUserServlet");
                    break;
                case READ_QR_CODE_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/ReadQRCodeServlet");
                    break;
                case GET_ALL_GUESTS_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/GetAllGuestsServer");
                    break;
                case GET_ALL_HONEY_MOON_GIFTS_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/GetAllGiftsServlet");
                    break;
                case QUOTA_SAVE_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/QuotaSaveServlet");
                    break;
                case GET_LAST_STORIES_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/GetLastStoryServlet");
                    break;
                case GUEST_BY_ID_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/GuestByIdServlet");
                    break;
                case GET_STORY_COMMENTS_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/GetStoryCommentsServlet");
                    break;
                case SAVE_COMMENT_RUNNABLE:
                    url = new URL("http://192.168.100.2:8080/OurWedding/SaveCommentServlet");
                    break;
                case GET_ALL_STORIES_SERVLET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/GetAllStoriesServlet");
                    break;
                case UPDATE_GUESTS_SERLVET:
                    url = new URL("http://192.168.100.2:8080/OurWedding/UpdateGuestsServlet");
                    break;

            }
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("content-type", "application/json; charset=utf-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            return connection;
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return null;
    }
}
