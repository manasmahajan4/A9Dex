package adnyey.notitia.a9;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private ArrayList<ArrayList<ObjNav>> navData = new ArrayList<>();
    private ArrayList<ObjNav> navBase = new ArrayList<>();
    private RecyclerView mRecycler;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment T_CARS, T_ROUTES, T_CMP, T_NEWS, T_ABOUT, T_SETTINGS, T_GARAGE, T_SOURCE;
    private StartActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = StartActivity.this;

        setContentView(R.layout.default_layout);

        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.setUpdateFrom(UpdateFrom.JSON)
                .setButtonDoNotShowAgain(null)
                .setUpdateJSON("https://raw.githubusercontent.com/adnyey/A9Dex/master/update_changelog.json")
                .start();

        mRecycler = (RecyclerView) findViewById(R.id.navigationList);

        new Thread() {
            @Override
            public void run() {

                addFragments();
                setupNavRecycler();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayoutManager LayOut = new LinearLayoutManager(StartActivity.this);
                        LayOut.setOrientation(LinearLayoutManager.HORIZONTAL);
                        mRecycler.setLayoutManager(LayOut);
                        mRecycler.setAdapter(new Adapter_Nav(StartActivity.this, navBase));

                        mRecycler.addOnItemTouchListener(
                                new RecyclerItemClickListener(StartActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int position) {
                                        mFragmentTransaction = mFragmentManager.beginTransaction();
                                        switch (position) {
                                            case 0:
                                                mFragmentTransaction.replace(R.id.containerView, T_CARS);
                                                break;
                                            case 1:
                                                mFragmentTransaction.replace(R.id.containerView, T_GARAGE);
                                                break;
                                            case 2:
                                                mFragmentTransaction.replace(R.id.containerView, T_CMP);
                                                break;
                                            case 3:
                                                mFragmentTransaction.replace(R.id.containerView, T_ROUTES);
                                                break;
                                            case 4:
                                                mFragmentTransaction.replace(R.id.containerView, T_NEWS);
                                                break;
                                            case 5:
                                                mFragmentTransaction.replace(R.id.containerView, T_ABOUT);
                                                break;
                                            case 6:
                                                mFragmentTransaction.replace(R.id.containerView, T_SETTINGS);
                                                break;
                                            case 7:
                                                Intent intent = new Intent(Intent.ACTION_SEND);
                                                intent.setType("plain/text");
                                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adnyey.mail@gmail.com"});
                                                startActivity(Intent.createChooser(intent, ""));
                                                break;
                                            case 8:

                                                new Diag_Donate(StartActivity.this).show();

                                                break;
                                            case 9:
                                                mFragmentTransaction.replace(R.id.containerView, T_SOURCE);
                                                break;
                                        }
                                        mFragmentTransaction.commit();
                                    }
                                })
                        );
                    }
                });
            }
        }.start();


    }

    private void addFragments() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        T_CARS = new Fragment_Cars_Tab();
        T_ROUTES = new Fragment_Routes();
        T_CMP = new Fragment_Compare();
        T_NEWS = new Fragment_News();
        T_ABOUT = new Fragment_About();
        T_SETTINGS = new Fragment_Settings();
        T_GARAGE = new Fragment_Garage();
        T_SOURCE = new Fragment_Sources();

        if(mFragmentManager.isStateSaved())
            mFragmentTransaction.replace(R.id.containerView, T_CARS).commitAllowingStateLoss();
        else
            mFragmentTransaction.replace(R.id.containerView, T_CARS).commit();
    }


    private void setupNavRecycler() {

        navData = new ArrayList<ArrayList<ObjNav>>();

        navBase = new ArrayList<ObjNav>();

        navBase.add(new ObjNav("All Cars", R.drawable.nav_car));
        navBase.add(new ObjNav("My Garage", R.drawable.nav_garage));
        navBase.add(new ObjNav("Compare Cars", R.drawable.nav_cmp));
        navBase.add(new ObjNav("Best Routes", R.drawable.nav_route));
        navBase.add(new ObjNav("Game News", R.drawable.nav_news));
        navBase.add(new ObjNav("About", R.drawable.nav_info));
        navBase.add(new ObjNav("Settings", R.drawable.nav_settings));
        navBase.add(new ObjNav("Send Corrections", R.drawable.nav_bug));
        navBase.add(new ObjNav("Donate", R.drawable.nav_donate));
        navBase.add(new ObjNav("Sources", R.drawable.link));

        navData.add(navBase);

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.notif_exit), Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);

    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
}
