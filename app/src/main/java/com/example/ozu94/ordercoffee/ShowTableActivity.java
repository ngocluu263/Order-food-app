package com.example.ozu94.ordercoffee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.ListTable;
import Adapter.TableAdapter;
import Data.DataWrapper;
import Data.ListOrdering;
import Data.ListWrapper;
import Data.Ordering;

/**
 * Created by Ozu94 on 12/19/2015.
 */
public class ShowTableActivity extends AppCompatActivity {

    final Context context = this;
    private static ListView lvTables;
    private static ListTable adapter;
    private static ArrayList<Ordering> arrTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_main);

        ListWrapper dw = (ListWrapper) getIntent().getSerializableExtra("tablesOrdering");
        arrTables = dw.getList();
        lvTables = (ListView) findViewById(R.id.lstOrderTable);
        adapter = new ListTable(this, arrTables);
        lvTables.setAdapter(adapter);

        lvTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<ListOrdering> lstOrdering = arrTables.get(position).getLstOrdering();
                String msg = "";
                for (int i = 0; i < lstOrdering.size(); i++) {
                    msg += lstOrdering.get(i).getName() + ": " + lstOrdering.get(i).getNumber() + "\n";
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

                alertDialogBuilder.setTitle("Danh sách các món đã chọn");
                alertDialogBuilder
                    .setMessage(msg)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        }
                    });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });
    }
}
