package adnyey.notitia.a9;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment_Sources extends Fragment {

    ArrayList<ObjSource> dataSources;
    Context mContext;
    RecyclerView srcRec;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_sources, container, false);
        mContext = getContext();
        dataSources = new ArrayList<>();
        srcRec = (RecyclerView) view.findViewById(R.id.srcList);
        dataSources.add(new ObjSource("Asphalt 9: Legends", "https://play.google.com/store/apps/details?id=com.gameloft.android.ANMP.GloftA9HM&hl=en_IN"));
        dataSources.add(new ObjSource("asphalt9.info", "https://asphalt9.info/"));
        dataSources.add(new ObjSource("Google+ A9 Community", "https://plus.google.com/communities/110970432786835009503"));
        dataSources.add(new ObjSource("Naveed Akhtar", "https://www.youtube.com/channel/UC32LKV5mmzUp95F7XplfC2Q"));
        dataSources.add(new ObjSource("TWL Slayer", "https://www.youtube.com/channel/UCFgzE_nkXMWvIksMB3F58XA"));
        dataSources.add(new ObjSource("Elite_IvailoK", "https://www.youtube.com/channel/UC2VRig-FnTSw9WfZdfvPdLQ"));
        dataSources.add(new ObjSource("Elite_Bohy", "https://www.youtube.com/channel/UCnzguk1ovY8uz7CN6pyD_hQ"));
        dataSources.add(new ObjSource("RpM_Pulsar", "https://www.youtube.com/channel/UCyBaLAbpR7gZV9cEWy5wmcA"));
        dataSources.add(new ObjSource("三土（LMF大懶堂", "https://plus.google.com/111256037939470458578"));
        dataSources.add(new ObjSource("Abhishek Chauhan", "https://www.youtube.com/channel/UCIvuO7_nUkTYkcqSSLSILaQ"));
        dataSources.add(new ObjSource("AnonIIExt _", "https://www.youtube.com/channel/UClzs70mp6ojGJWonBPmzqZA"));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager LayOut = new LinearLayoutManager(mContext);
                LayOut.setOrientation(LinearLayoutManager.VERTICAL);
                srcRec.setLayoutManager(LayOut);
                srcRec.setNestedScrollingEnabled(false);
                srcRec.setAdapter(new Adapter_Source(mContext, dataSources));
            }
        }, 0);


        return view;
    }
}
