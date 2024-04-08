package adnyey.notitia.a9;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.ArrayList;

public class DFrag_VS_Selector extends DialogFragment {

    ArrayList<ArrayList<ObjListing>> data;
    private Context mContext;
    private Activity mActivity;
    private Window mWindow;
    DatabaseAccess databaseAccess;
    private RecyclerView mRecycler;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private boolean isLeft;
    private int currentCarID, frozenCarID;

    public DFrag_VS_Selector() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DFrag_VS_Selector newInstance(boolean isLeft, int currentCarID, int frozenCarID) {
        DFrag_VS_Selector frag = new DFrag_VS_Selector();
        Bundle args = new Bundle();
        args.putBoolean("ISLEFT", isLeft);
        args.putInt("CURRENTID", currentCarID);
        args.putInt("FROZENID", frozenCarID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_route, container);
        mContext = getContext();
        mActivity = getActivity();
        databaseAccess = DatabaseAccess.getInstance(mActivity);
        mWindow = getDialog().getWindow();
        try {
            mWindow.requestFeature(Window.FEATURE_NO_TITLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#263238")));
        isLeft = getArguments().getBoolean("ISLEFT");
        currentCarID = getArguments().getInt("CURRENTID");
        frozenCarID = getArguments().getInt("FROZENID");

        new Thread() {
            @Override
            public void run() {
                data = new ArrayList<>();
                synchronized (databaseAccess) {

                    try {

                        databaseAccess.open();

                        data.add(CursorConverter.extractObjListing(databaseAccess.getCarList(1)));
                        data.add(CursorConverter.extractObjListing(databaseAccess.getCarList(2)));
                        data.add(CursorConverter.extractObjListing(databaseAccess.getCarList(3)));
                        data.add(CursorConverter.extractObjListing(databaseAccess.getCarList(4)));
                        data.add(CursorConverter.extractObjListing(databaseAccess.getCarList(5)));

                    } finally {
                        databaseAccess.close();
                    }
                }

                try {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecycler = (RecyclerView) view.findViewById(R.id.tracksRecycler);

                            final Handler handler12 = new Handler();
                            handler12.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mLayoutManager = new StickyHeaderGridLayoutManager(2);
                                    mLayoutManager.setSpanSizeLookup(new StickyHeaderGridLayoutManager.SpanSizeLookup() {
                                        @Override
                                        public int getSpanSize(int section, int position) {
                                            switch (section) {
                                                default:
                                                    return 1;
                                            }
                                        }
                                    });

                                    // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
                                    // puts the removed item on the top of other views and it will be drawn above sticky header.
                                    // The only way to fix this, abandon remove animations :(
                                    mRecycler.setItemAnimator(new DefaultItemAnimator() {
                                        @Override
                                        public boolean animateRemove(RecyclerView.ViewHolder holder) {
                                            dispatchRemoveFinished(holder);
                                            return false;
                                        }
                                    });

                                    mRecycler.setLayoutManager(mLayoutManager);
                                    mRecycler.setNestedScrollingEnabled(false);
                                    mRecycler.setAdapter(new Adapter_Selection_VS(data, currentCarID, frozenCarID, isLeft, mContext, getTargetFragment()));

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

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
