package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_About extends Fragment {

    TextView contact, version;
    ImageView tweet;
    ImageView discord;
    Button creative;
    Activity mActivity;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_about, container, false);

        mActivity = getActivity();
        mContext = getContext();
        new Thread() {
            @Override
            public void run() {


                try {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            contact=(TextView)view.findViewById(R.id.mail_click);
                            tweet = (ImageView)view.findViewById(R.id.butt_tweet);
                            discord = (ImageView)view.findViewById(R.id.butt_discord);
                            creative = (Button)view.findViewById(R.id.butt_creative);
                            version = (TextView)view.findViewById(R.id.version_txt);

                            contact.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("plain/text");
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adnyey.mail@gmail.com"});
                                    startActivity(Intent.createChooser(intent, ""));
                                }
                            });
                            //SOMETHING

                            tweet.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToUrl("https://twitter.com/Adnyey");
                                }
                            });

                            discord.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToUrl("https://discord.gg/aEhap7P");
                                }
                            });

                            creative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToUrl("https://creativecommons.org/licenses/by-nc/4.0/");
                                }
                            });

                            try {
                                PackageInfo pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
                                String version1 = pInfo.versionName;
                                version.setText(getResources().getString(R.string.version)+" "+version1);
                            } catch (Exception e) {
                            }


                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return view;
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
