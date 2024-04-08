package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DFrag_Details extends DialogFragment {

    final static String CAR_TAG = "CAR_TAG";
    private int CAR_ID, CAR_FUEL, CAR_REFILL, CAR_NUM_UPGRADES, CAR_RANK_BASE, CAR_RANK_MAX;
    private ObjListing CAR_LISTING;
    private TextView CAR_BRAND, CAR_NAME, CAR_CLASS, SUM_UP, SUM_IMP, SUM_TOT;
    private ImageView CAR_IMG;
    private RecyclerView starRecycler, perfRecycler, upgradesRecycler, importRecycler;
    private int numStars[], numStarsRanks[], sumUp = 0, sumImp = 0;
    private ArrayList<int[]> upgrades;
    private Boolean isUpComplete = true, isImpIncomplete = false;
    private ArrayList<ObjPerf> perfData;
    private ArrayList<ObjPerfData> perfDataValues;
    private ArrayList<ObjImport> importData;
    private Context mContext;
    private Activity mActivity;
    private Window mWindow;
    private DatabaseAccess databaseAccess;
    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

    public DFrag_Details() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DFrag_Details newInstance(int title) {
        DFrag_Details frag = new DFrag_Details();
        Bundle args = new Bundle();
        args.putInt(CAR_TAG, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_details, container);
        mContext = getContext();
        mActivity = getActivity();
        databaseAccess = databaseAccess.getInstance(mActivity);
        mWindow = getDialog().getWindow();
        try {
            mWindow.requestFeature(Window.FEATURE_NO_TITLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#263238")));

        starRecycler = (RecyclerView) view.findViewById(R.id.starsRecycler);
        perfRecycler = (RecyclerView) view.findViewById(R.id.perfRecycler);
        upgradesRecycler = (RecyclerView) view.findViewById(R.id.upgradesRecycler);
        importRecycler = (RecyclerView) view.findViewById(R.id.importRecycler);
        CAR_BRAND = (TextView) view.findViewById(R.id.details_brand);
        CAR_NAME = (TextView) view.findViewById(R.id.details_name);
        CAR_IMG = (ImageView) view.findViewById(R.id.details_pic);
        CAR_CLASS = (TextView) view.findViewById(R.id.details_class);
        SUM_TOT = (TextView) view.findViewById(R.id.summary_total);
        SUM_UP = (TextView) view.findViewById(R.id.summary_upgrades);
        SUM_IMP = (TextView) view.findViewById(R.id.summary_import);

        new Thread() {
            @Override
            public void run() {

                perfDataValues = new ArrayList<>();
                importData = new ArrayList<>();
                upgrades = new ArrayList<>();

                CAR_ID = getArguments().getInt(CAR_TAG, 0);

                synchronized (databaseAccess) {
                    try {
                        databaseAccess.open();

                        CAR_LISTING = databaseAccess.getCar(CAR_ID);
                        numStars = new int[CAR_LISTING.getStars()];
                        numStarsRanks = new int[CAR_LISTING.getStars()];

                        perfDataValues = databaseAccess.getPerf(CAR_ID);
                        CAR_REFILL = databaseAccess.getRefill(CAR_ID);

                        switch (CAR_LISTING.getStars()) {
                            case 3:
                                CAR_FUEL = 6;
                                CAR_NUM_UPGRADES = 10;
                                break;
                            case 4:
                                CAR_FUEL = 5;
                                CAR_NUM_UPGRADES = 11;
                                break;
                            case 5:
                                CAR_FUEL = 4;
                                CAR_NUM_UPGRADES = 12;
                                break;
                            case 6:
                                CAR_FUEL = 3;
                                CAR_NUM_UPGRADES = 13;
                                break;
                        }

                        numStars = databaseAccess.getStarsList(CAR_LISTING.getId(), CAR_LISTING.getStars());
                        numStarsRanks = databaseAccess.getStarsRankList(CAR_LISTING.getId(), CAR_LISTING.getStars());
                        upgrades = databaseAccess.getUpgrades(CAR_ID, CAR_NUM_UPGRADES);
                        importData = databaseAccess.getImports(CAR_ID);

                    } finally {
                        databaseAccess.close();
                    }

                }

                CAR_RANK_BASE = numStarsRanks[0];
                CAR_RANK_MAX = numStarsRanks[numStarsRanks.length - 1];

                for (int[] temp : upgrades) {
                    for (int i = 0; i < temp.length; i++)
                        sumUp += temp[i] * 4;
                }

                for (int[] temp : upgrades) {
                    for (int i = 0; i < temp.length; i++)
                        if (temp[i] == 0)
                            isUpComplete = false;
                }

                for (ObjImport temp : importData) {
                    sumImp += temp.getCost() * temp.getQuantitiy() * 4;
                }

                perfData = new ArrayList<>();
                perfData.add(new ObjPerf(R.drawable.perf_top, "TOP SPEED", perfDataValues.get(0).getTop(), perfDataValues.get(1).getTop(), "#0.0"));
                perfData.add(new ObjPerf(R.drawable.perf_acceleration, "ACCELERATION", perfDataValues.get(0).getAccel(), perfDataValues.get(1).getAccel(), "#0.00"));
                perfData.add(new ObjPerf(R.drawable.perf_handling, "HANDLING", perfDataValues.get(0).getHand(), perfDataValues.get(1).getHand(), "#0.00"));
                perfData.add(new ObjPerf(R.drawable.perf_nos, "NITRO", perfDataValues.get(0).getNitro(), perfDataValues.get(1).getNitro(), "#0.00"));
                perfData.add(new ObjPerf(R.drawable.nav_star, "RANK", CAR_RANK_BASE, CAR_RANK_MAX, "#0"));
                perfData.add(new ObjPerf(R.drawable.perf_fuel, "FUEL", CAR_FUEL, CAR_FUEL, "#0"));
                perfData.add(new ObjPerf(R.drawable.perf_refuel, "REFILL TIME", CAR_REFILL, CAR_REFILL, "#0"));

                try {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            CAR_BRAND.setText(CAR_LISTING.getBrand());
                            CAR_NAME.setText(CAR_LISTING.getName());

                            switch (CAR_LISTING.getClasss()) {
                                case 1:
                                    CAR_CLASS.setText("D");
                                    break;
                                case 2:
                                    CAR_CLASS.setText("C");
                                    break;
                                case 3:
                                    CAR_CLASS.setText("B");
                                    break;
                                case 4:
                                    CAR_CLASS.setText("A");
                                    break;
                                case 5:
                                    CAR_CLASS.setText("S");
                                    break;
                            }

                            if (isUpComplete) {
                                SUM_UP.setText(NumberFormat.getIntegerInstance().format(sumUp));
                                SUM_TOT.setText(NumberFormat.getIntegerInstance().format(sumImp + sumUp));
                                SUM_IMP.setText(NumberFormat.getIntegerInstance().format(sumImp));
                            } else {
                                SUM_UP.setText("INCOMPLETE");
                                SUM_UP.setTextColor(Color.parseColor("#FF1744"));
                                SUM_TOT.setText("INCOMPLETE");
                                SUM_TOT.setTextColor(Color.parseColor("#FF1744"));
                                SUM_IMP.setText("INCOMPLETE");
                                SUM_IMP.setTextColor(Color.parseColor("#FF1744"));
                            }

                            try {
                                int id = mContext.getResources().getIdentifier(CAR_LISTING.getImg(), "drawable", mContext.getPackageName());
                                Glide.with(mContext)
                                        .load(id)
                                        .apply(options)
                                        .thumbnail(0.1f)
                                        .into(CAR_IMG);
                            } catch (NullPointerException e) {
                            }
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayoutManager LayOut = new LinearLayoutManager(mContext);
                                    LayOut.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    starRecycler.setLayoutManager(LayOut);
                                    starRecycler.setNestedScrollingEnabled(false);
                                    starRecycler.setAdapter(new Adapter_Stars(mContext, numStars, numStarsRanks));
                                }
                            }, 0);

                            final Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayoutManager LayOutU = new LinearLayoutManager(mContext);
                                    LayOutU.setOrientation(LinearLayoutManager.VERTICAL);
                                    upgradesRecycler.setLayoutManager(LayOutU);
                                    upgradesRecycler.setNestedScrollingEnabled(false);
                                    upgradesRecycler.setAdapter(new Adapter_UpEnclosing(mContext, upgrades));
                                }
                            }, 0);
                            final Handler handler11 = new Handler();
                            handler11.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayoutManager LayOutI = new LinearLayoutManager(mContext);
                                    LayOutI.setOrientation(LinearLayoutManager.VERTICAL);
                                    importRecycler.setLayoutManager(LayOutI);
                                    importRecycler.setNestedScrollingEnabled(false);
                                    importRecycler.setAdapter(new Adapter_Import(mContext, importData));
                                }
                            }, 0);
                            final Handler handler111 = new Handler();
                            handler111.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LinearLayoutManager LayOutperf = new LinearLayoutManager(mContext);
                                    LayOutperf.setOrientation(LinearLayoutManager.VERTICAL);
                                    perfRecycler.setLayoutManager(LayOutperf);
                                    perfRecycler.setNestedScrollingEnabled(false);
                                    perfRecycler.setAdapter(new Adapter_Perf(mContext, perfData, false));
                                }
                            }, 0);

                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_NoActionBar);
    }

}
