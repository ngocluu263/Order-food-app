package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ozu94 on 12/19/2015.
 */
public class DataWrapper implements Serializable {
    private ArrayList<ListOrdering> slist;

    public DataWrapper(ArrayList<ListOrdering> data) {
        this.slist = data;
    }

    public ArrayList<ListOrdering> getList() {
        return this.slist;
    }
}
