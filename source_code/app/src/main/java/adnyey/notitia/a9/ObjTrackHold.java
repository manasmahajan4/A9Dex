package adnyey.notitia.a9;

import java.util.ArrayList;

public class ObjTrackHold {

    private ArrayList<ArrayList<ObjTrack>> mainData;
    private ArrayList<String> parents;

    public ObjTrackHold(ArrayList<ArrayList<ObjTrack>> mainData, ArrayList<String> parents) {
        this.mainData = mainData;
        this.parents = parents;
    }

    public ObjTrackHold() {
    }

    public ArrayList<ArrayList<ObjTrack>> getMainData() {
        return mainData;
    }

    public void setMainData(ArrayList<ArrayList<ObjTrack>> mainData) {
        this.mainData = mainData;
    }

    public ArrayList<String> getParents() {
        return parents;
    }

    public void setParents(ArrayList<String> parents) {
        this.parents = parents;
    }
}
