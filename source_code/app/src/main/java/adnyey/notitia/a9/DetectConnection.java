package adnyey.notitia.a9;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mahajan-PC on 2017-11-02.
 */

public class DetectConnection {

    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasInternetAccess(Context context) {
        if (checkInternetConnection(context)) {
            Boolean success = false;
            try {
                URL url = new URL("https://wikipedia.org");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.connect();
                success = connection.getResponseCode() == 200;

                if(!success) {

                    url = new URL("https://baidu.com/");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.connect();
                    success = connection.getResponseCode() == 200;
                    return success;
                }
                else
                    return true;


            } catch (IOException e) {
                Log.e("NW", "Error checking internet connection", e);
            }
        } else {
            Log.d("NW", "No network available!");
        }
        return false;
    }
}
