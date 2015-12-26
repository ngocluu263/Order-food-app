package com.example.ozu94.ordercoffee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import Adapter.TableAdapter;
import Data.ListWrapper;
import Data.Ordering;

/**
 * Created by Ozu94 on 12/19/2015.
 */
public class DelTableActivity extends AppCompatActivity {

    private static ListView lstDel;
    private static Button btnDel;
    private static TableAdapter adapter;
    private static ArrayList<Ordering> arrTables;
    private static ArrayList<Integer> arrDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_main);
        arrDel = new ArrayList<Integer>();

        ListWrapper dw = (ListWrapper) getIntent().getSerializableExtra("tablesOrdering");
        arrTables = dw.getList();
        lstDel = (ListView) findViewById(R.id.lstDel);
        adapter = new TableAdapter(this, arrTables);
        lstDel.setAdapter(adapter);

        btnDel = (Button) findViewById(R.id.btnDelTable);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(getApplicationContext(), GridViewActivity.class);

                for (int i = 0; i < lstDel.getAdapter().getCount(); i++) {
                    View viewItem = lstDel.getChildAt(i);
                    CheckBox chkDel = (CheckBox) viewItem.findViewById(R.id.chkDel);

                    if (chkDel.isChecked()) {

                        arrDel.add(i);
                    }
                }

                intents.putExtra("arrDelTable", arrDel);

                setResult(GridViewActivity.RESULT_CODE_DEL, intents);

                finish();
            }
        });
    }
}
