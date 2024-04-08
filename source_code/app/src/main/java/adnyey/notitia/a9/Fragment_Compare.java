package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.ArrayList;

public class Fragment_Compare extends Fragment implements MyContract {

    LinearLayout leftSelector, rightSelector;
    DatabaseAccess databaseAccess;
    private Context mContext;
    private Activity mActivity;
    private int CAR_ID[] = {11, 12}, OBTAINED_ID, CAR_FUEL[] = new int[2], CAR_REFILL[] = new int[2], CAR_NUM_UPGRADES[] = new int[2], CAR_RANK_BASE[] = new int[2], CAR_RANK_MAX[] = new int[2], CAR_STATS_MODE[] = {0, 0};
    private TextView CAR_BRAND[] = new TextView[2], CAR_NAME[] = new TextView[2], CAR_CLASS[] = new TextView[2], SUM_UP[] = new TextView[2], SUM_IMP[] = new TextView[2], SUM_TOT[] = new TextView[2];
    private int numStars[][] = new int[2][], numStarsRanks[][] = new int[2][], sumUp[] = {0, 0}, sumImp[] = {0, 0};
    private ArrayList<ObjPerf> perfData = new ArrayList<>();
    private ArrayList<ObjPerfData>[] perfDataValues = new ArrayList[2];
    private ArrayList<ObjImport>[] importData = new ArrayList[2];
    private ArrayList<int[]>[] upgrades = new ArrayList[2];
    private ObjListing[] CAR_LISTING = new ObjListing[2];
    private LinearLayout[] SWITCH_CAR_LEFT_OUT = new LinearLayout[2];
    private TextView[] SWITCH_CAR_LEFT_TEXT = new TextView[2];
    private LinearLayout[] SWITCH_CAR_RIGHT_OUT = new LinearLayout[2];
    private TextView[] SWITCH_CAR_RIGHT_TEXT = new TextView[2];
    private RecyclerView perfRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_compare, container, false);

        mContext = getContext();
        mActivity = getActivity();

        databaseAccess = DatabaseAccess.getInstance(mActivity);

        leftSelector = (LinearLayout) view.findViewById(R.id.leftSelector);
        rightSelector = (LinearLayout) view.findViewById(R.id.rightSelector);
        CAR_BRAND[0] = (TextView) view.findViewById(R.id.carBrandLeft);
        CAR_BRAND[1] = (TextView) view.findViewById(R.id.carBrandRight);
        CAR_NAME[0] = (TextView) view.findViewById(R.id.carNameLeft);
        CAR_NAME[1] = (TextView) view.findViewById(R.id.carNameRight);
        SWITCH_CAR_LEFT_OUT[0] = (LinearLayout) view.findViewById(R.id.left_stock_out);
        SWITCH_CAR_LEFT_OUT[1] = (LinearLayout) view.findViewById(R.id.left_max_out);
        SWITCH_CAR_LEFT_TEXT[0] = (TextView) view.findViewById(R.id.left_stock_text);
        SWITCH_CAR_LEFT_TEXT[1] = (TextView) view.findViewById(R.id.left_max_text);

        SWITCH_CAR_RIGHT_OUT[0] = (LinearLayout) view.findViewById(R.id.right_stock_out);
        SWITCH_CAR_RIGHT_OUT[1] = (LinearLayout) view.findViewById(R.id.right_max_out);
        SWITCH_CAR_RIGHT_TEXT[0] = (TextView) view.findViewById(R.id.right_stock_text);
        SWITCH_CAR_RIGHT_TEXT[1] = (TextView) view.findViewById(R.id.right_max_text);
        perfRecycler = (RecyclerView) view.findViewById(R.id.perfRecycler);

        CAR_NAME[0].setSelected(true);
        CAR_NAME[0].setSingleLine(true);
        CAR_NAME[0].setAllCaps(true);
        CAR_NAME[1].setSelected(true);
        CAR_NAME[1].setSingleLine(true);
        CAR_NAME[1].setAllCaps(true);
        CAR_BRAND[0].setSelected(true);
        CAR_BRAND[0].setSingleLine(true);
        CAR_BRAND[1].setSelected(true);
        CAR_BRAND[1].setSingleLine(true);
        CAR_BRAND[0].setAllCaps(true);
        CAR_BRAND[1].setAllCaps(true);

        new Thread() {
            @Override
            public void run() {
                try {

                    initData();
                    collectData();
                    // code runs in a thread
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (!isAdded()) {
                                return;
                            } else {
                                updateSwitchUI();
                                leftSelector.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SelectorInitiator(true, view);
                                    }
                                });

                                rightSelector.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SelectorInitiator(false, view);
                                    }
                                });

                                SWITCH_CAR_LEFT_OUT[0].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (CAR_STATS_MODE[0] != 0) {
                                            CAR_STATS_MODE[0] = 0;
                                            updateSwitchUI();
                                            updateForModeChange();
                                        }
                                    }
                                });

                                SWITCH_CAR_LEFT_OUT[1].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (CAR_STATS_MODE[0] != 1) {
                                            CAR_STATS_MODE[0] = 1;
                                            updateSwitchUI();
                                            updateForModeChange();
                                        }
                                    }
                                });

                                SWITCH_CAR_RIGHT_OUT[0].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (CAR_STATS_MODE[1] != 0) {
                                            CAR_STATS_MODE[1] = 0;
                                            updateSwitchUI();
                                            updateForModeChange();
                                        }
                                    }
                                });

                                SWITCH_CAR_RIGHT_OUT[1].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (CAR_STATS_MODE[1] != 1) {
                                            CAR_STATS_MODE[1] = 1;
                                            updateSwitchUI();
                                            updateForModeChange();
                                        }
                                    }
                                });

                                presentData();


                            }
                        }
                    });
                } catch (final NullPointerException ex) {
                    ex.printStackTrace();

                }
            }
        }.start();


        return view;
    }

    private void updateForModeChange(){

        try {

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    presentData();

                }
            });
        } catch (final NullPointerException ex) {
            ex.printStackTrace();

        }
    }

    private void presentData() {
        setupPerfData();

        try {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    CAR_NAME[0].setText(CAR_LISTING[0].getName());
                    CAR_NAME[1].setText(CAR_LISTING[1].getName());

                    CAR_BRAND[0].setText(CAR_LISTING[0].getBrand());
                    CAR_BRAND[1].setText(CAR_LISTING[1].getBrand());

                    final Handler handler111 = new Handler();
                    handler111.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager LayOutperf = new LinearLayoutManager(mContext);
                            LayOutperf.setOrientation(LinearLayoutManager.VERTICAL);
                            perfRecycler.setLayoutManager(LayOutperf);
                            perfRecycler.setNestedScrollingEnabled(false);
                            perfRecycler.setAdapter(new Adapter_Perf(mContext, perfData, true));
                        }
                    }, 0);

                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setupPerfData() {
        perfData = new ArrayList<>();

        perfData.add(new ObjPerf(R.drawable.perf_top, "TOP SPEED", perfDataValues[0].get(CAR_STATS_MODE[0]).getTop(), perfDataValues[1].get(CAR_STATS_MODE[1]).getTop(), "#0.0"));
        perfData.add(new ObjPerf(R.drawable.perf_acceleration, "ACCELERATION", perfDataValues[0].get(CAR_STATS_MODE[0]).getAccel(), perfDataValues[1].get(CAR_STATS_MODE[1]).getAccel(), "#0.00"));
        perfData.add(new ObjPerf(R.drawable.perf_handling, "HANDLING", perfDataValues[0].get(CAR_STATS_MODE[0]).getHand(), perfDataValues[1].get(CAR_STATS_MODE[1]).getHand(), "#0.00"));
        perfData.add(new ObjPerf(R.drawable.perf_nos, "NITRO", perfDataValues[0].get(CAR_STATS_MODE[0]).getNitro(), perfDataValues[1].get(CAR_STATS_MODE[1]).getNitro(), "#0.00"));
        int[] RANK_LEFT = new int[2];
        for (int i = 0; i < 2; i++) {
            if (CAR_STATS_MODE[i] == 0) {
                RANK_LEFT[i] = CAR_RANK_BASE[i];
            } else {
                RANK_LEFT[i] = CAR_RANK_MAX[i];
            }
        }
        perfData.add(new ObjPerf(R.drawable.nav_star, "RANK", RANK_LEFT[0], RANK_LEFT[1], "#0"));
        perfData.add(new ObjPerf(R.drawable.perf_fuel, "FUEL", CAR_FUEL[0], CAR_FUEL[1], "#0"));
        perfData.add(new ObjPerf(R.drawable.perf_refuel, "REFILL TIME", CAR_REFILL[0], CAR_REFILL[1], "#0"));
    }

    private void updateSwitchUI() {
        if (CAR_STATS_MODE[0] == 0) {
            SWITCH_CAR_LEFT_OUT[0].setBackgroundColor(getResources().getColor(R.color.leftCar));
            SWITCH_CAR_LEFT_TEXT[0].setTextColor(getResources().getColor(R.color.black));
            SWITCH_CAR_LEFT_OUT[1].setBackgroundColor(getResources().getColor(R.color.black));
            SWITCH_CAR_LEFT_TEXT[1].setTextColor(getResources().getColor(R.color.white));
        } else {
            SWITCH_CAR_LEFT_OUT[1].setBackgroundColor(getResources().getColor(R.color.leftCar));
            SWITCH_CAR_LEFT_TEXT[1].setTextColor(getResources().getColor(R.color.black));
            SWITCH_CAR_LEFT_OUT[0].setBackgroundColor(getResources().getColor(R.color.black));
            SWITCH_CAR_LEFT_TEXT[0].setTextColor(getResources().getColor(R.color.white));
        }

        if (CAR_STATS_MODE[1] == 0) {
            SWITCH_CAR_RIGHT_OUT[0].setBackgroundColor(getResources().getColor(R.color.rightCar));
            SWITCH_CAR_RIGHT_TEXT[0].setTextColor(getResources().getColor(R.color.black));
            SWITCH_CAR_RIGHT_OUT[1].setBackgroundColor(getResources().getColor(R.color.black));
            SWITCH_CAR_RIGHT_TEXT[1].setTextColor(getResources().getColor(R.color.white));
        } else {
            SWITCH_CAR_RIGHT_OUT[1].setBackgroundColor(getResources().getColor(R.color.rightCar));
            SWITCH_CAR_RIGHT_TEXT[1].setTextColor(getResources().getColor(R.color.black));
            SWITCH_CAR_RIGHT_OUT[0].setBackgroundColor(getResources().getColor(R.color.black));
            SWITCH_CAR_RIGHT_TEXT[0].setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void initData() {

        for (int i = 0; i < 2; i++) {
            perfDataValues[i] = new ArrayList<>();
            importData[i] = new ArrayList<>();
            upgrades[i] = new ArrayList<>();
            CAR_LISTING[i] = null;
        }
    }

    private void collectData() {
        synchronized (databaseAccess) {
            try {
                databaseAccess.open();

                for (int i = 0; i < 2; i++) {

                    CAR_LISTING[i] = databaseAccess.getCar(CAR_ID[i]);
                    numStars[i] = new int[CAR_LISTING[i].getStars()];
                    numStarsRanks[i] = new int[CAR_LISTING[i].getStars()];

                    perfDataValues[i] = databaseAccess.getPerf(CAR_ID[i]);
                    CAR_REFILL[i] = databaseAccess.getRefill(CAR_ID[i]);

                    switch (CAR_LISTING[i].getStars()) {
                        case 3:
                            CAR_FUEL[i] = 6;
                            CAR_NUM_UPGRADES[i] = 10;
                            break;
                        case 4:
                            CAR_FUEL[i] = 5;
                            CAR_NUM_UPGRADES[i] = 11;
                            break;
                        case 5:
                            CAR_FUEL[i] = 4;
                            CAR_NUM_UPGRADES[i] = 12;
                            break;
                        case 6:
                            CAR_FUEL[i] = 3;
                            CAR_NUM_UPGRADES[i] = 13;
                            break;
                    }

                    numStars[i] = databaseAccess.getStarsList(CAR_LISTING[i].getId(), CAR_LISTING[i].getStars());
                    numStarsRanks[i] = databaseAccess.getStarsRankList(CAR_LISTING[i].getId(), CAR_LISTING[i].getStars());
                    upgrades[i] = databaseAccess.getUpgrades(CAR_ID[i], CAR_NUM_UPGRADES[i]);
                    importData[i] = databaseAccess.getImports(CAR_ID[i]);
                }

            } finally {
                databaseAccess.close();
            }

            for (int i = 0; i < 2; i++) {
                CAR_RANK_BASE[i] = numStarsRanks[i][0];
                CAR_RANK_MAX[i] = numStarsRanks[i][numStarsRanks[i].length - 1];
            }


        }
    }

    private void SelectorInitiator(final boolean isLeft, final View view) {
        FragmentManager fm = getFragmentManager();
        DFrag_VS_Selector dialogFragment = DFrag_VS_Selector.newInstance(isLeft, isLeft ? CAR_ID[0] : CAR_ID[1], isLeft ? CAR_ID[1] : CAR_ID[0]);
        dialogFragment.show(fm, "fragment_selector_vs");
        fm.executePendingTransactions();
        dialogFragment.setTargetFragment(Fragment_Compare.this, 0);
        dialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //do whatever you want when dialog is dismissed
                final Handler handler12 = new Handler();
                handler12.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final boolean isUpdateFromDB;
                        if (isLeft) {
                            if (CAR_ID[0] != OBTAINED_ID) {
                                isUpdateFromDB = true;
                                CAR_ID[0] = OBTAINED_ID;
                            } else
                                isUpdateFromDB = false;
                        } else {
                            if (CAR_ID[1] != OBTAINED_ID) {
                                isUpdateFromDB = true;
                                CAR_ID[1] = OBTAINED_ID;
                            } else
                                isUpdateFromDB = false;
                        }
                            new Thread() {
                                @Override
                                public void run() {
                                    try {

                                        if(isUpdateFromDB) {
                                            initData();
                                            collectData();
                                        }

                                        mActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                presentData();

                                            }
                                        });
                                    } catch (final NullPointerException ex) {
                                        ex.printStackTrace();

                                    }
                                }
                            }.start();

                    }
                }, 0);

            }
        });
    }

    @Override
    public void methodToPassData(int currentSelectedID) {
        this.OBTAINED_ID = currentSelectedID;
    }
}
