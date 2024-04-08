package adnyey.notitia.a9;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Fragment_Settings extends Fragment {

    RelativeLayout butt_lang;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_settings, container, false);
        mContext = getContext();
        butt_lang = (RelativeLayout)view.findViewById(R.id.butt_lang);

        butt_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
