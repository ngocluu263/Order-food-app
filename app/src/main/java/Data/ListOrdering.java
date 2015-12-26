package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ozu94 on 12/13/2015.
 */
public class ListOrdering implements Serializable {

    private String name;
    private int number;
    private int price;

    public ListOrdering (String name, int num, int price) {
        super();
        this.name = name;
        this.number = num;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }
}


