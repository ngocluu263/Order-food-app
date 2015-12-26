package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ozu94 on 12/19/2015.
 */
public class ListWrapper implements Serializable {
    private ArrayList<Ordering> slist;

    public ListWrapper(ArrayList<Ordering> data) {
        this.slist = data;
    }

    public ArrayList<Ordering> getList() {
        return this.slist;
    }
}
