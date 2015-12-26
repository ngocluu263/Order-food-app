package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ozu94 on 12/13/2015.
 */
public class Ordering implements Serializable {

    private int numTable;
    private ArrayList<ListOrdering> lstOrdering = new ArrayList<ListOrdering>();
    private int totalPrice;

    public Ordering(int num, ArrayList<ListOrdering> list, int totalPrice) {
        super();
        this.numTable = num;
        this.lstOrdering = list;
        this.totalPrice = totalPrice;
    }

    public int getNumTable() {
        return numTable;
    }

    public ArrayList<ListOrdering> getLstOrdering() {
        return lstOrdering;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
