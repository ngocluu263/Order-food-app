package com.example.ozu94.ordercoffee;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import Data.DataWrapper;
import Data.GridItem;
import Adapter.GridViewAdapter;
import Data.ListOrdering;
import Data.ListWrapper;
import Data.Ordering;

/**
 * Created by Ozu94 on 12/19/2015.
 */
public class GridViewActivity extends AppCompatActivity {

    private static GridView mGridView;
    private static GridViewAdapter mGridAdapter;
    private static ArrayList<GridItem> mGridData;
    private static ArrayList<Ordering> arrOrders;
    public static final int REQUEST_CODE_INPUT = 1;
    public static final int RESULT_CODE_ADD = 2;
    public static final int RESULT_CODE_DEL = 3;
    private int posSt = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_main);

        arrOrders = new ArrayList<Ordering>();
        mGridView = (GridView) findViewById(R.id.gridView);

        //Initialize with empty data
        mGridData = new ArrayList<>();

        GridItem item;
        for (int i = 0; i < 12; i++) {
            item = new GridItem();
            item.setTitle("Bàn số " + (i+1));
            mGridData.add(item);
        }
        mGridAdapter = new GridViewAdapter(this, R.layout.activity_table_item, mGridData);
        mGridView.setAdapter(mGridAdapter);

        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                posSt = position;
                Intent itn = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivityForResult(itn, REQUEST_CODE_INPUT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CODE_ADD) {

            DataWrapper dw = (DataWrapper) data.getSerializableExtra("listOrdering");
            ArrayList<ListOrdering> taskList = dw.getList();
            int totalPrice = data.getIntExtra("totalPrice", 0);
            Ordering item = new Ordering(posSt+1, taskList, totalPrice);
            arrOrders.add(item);
        }

        if (resultCode == RESULT_CODE_DEL) {
            ArrayList<Integer> arrDel = new ArrayList<Integer>();
            arrDel = data.getIntegerArrayListExtra("arrDelTable");
            ArrayList<Ordering> tmpOrdering = new ArrayList<Ordering>();
            for (int i = 0; i < arrDel.size(); i++) {
                int indexDel = arrDel.get(i);
                tmpOrdering.add(arrOrders.get(indexDel));
            }

            for (int i = 0; i < tmpOrdering.size(); i++) {
                for (int j = 0; j < arrOrders.size(); j++) {
                    if (arrOrders.get(j) == tmpOrdering.get(i)) {
                        arrOrders.remove(tmpOrdering.get(i));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.show_tables) {
            Intent itn = new Intent(getApplicationContext(), ShowTableActivity.class);
            itn.putExtra("tablesOrdering", new ListWrapper(arrOrders));
            startActivity(itn);
        }

        if (id == R.id.del_tables) {
            Intent itn = new Intent(getApplicationContext(), DelTableActivity.class);
            itn.putExtra("tablesOrdering", new ListWrapper(arrOrders));
            startActivityForResult(itn, REQUEST_CODE_INPUT);
        }

        return super.onOptionsItemSelected(item);
    }
}