package adnyey.notitia.a9;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.asksira.webviewsuite.WebViewSuite;

public class Fragment_News extends Fragment {
    WebViewSuite webViewSuite;
    String URL = "https://in-game-news.gameloft.com/game-selector/?from=A9HM";
    RelativeLayout butt_back, butt_refresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_news, container, false);

        webViewSuite= (WebViewSuite) view.findViewById(R.id.webViewSuite);
        butt_back = (RelativeLayout)view.findViewById(R.id.web_back);
        butt_refresh = (RelativeLayout)view.findViewById(R.id.web_refresh);

        butt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewSuite.goBackIfPossible();
            }
        });

        butt_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewSuite.refresh();
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                webViewSuite.startLoading(URL);
            }
        }, 0);
        return view;
    }


}
