package adnyey.notitia.a9;

import java.util.Comparator;

public class CustomComparator implements Comparator<ObjTrack> {

    @Override
    public int compare(ObjTrack o1, ObjTrack o2) {
        if(o1.getTime()<=o2.getTime())
            return 1;
        else
            return -1;
    }

}
