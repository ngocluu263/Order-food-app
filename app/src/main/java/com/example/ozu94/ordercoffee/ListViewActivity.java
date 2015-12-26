package com.example.ozu94.ordercoffee;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import Adapter.QLSP;
import Adapter.FoodListAdapter;
import Data.DataWrapper;
import Data.FoodData;
import Data.ListOrdering;

/**
 * Created by Ozu94 on 12/13/2015.
 */
public class ListViewActivity extends AppCompatActivity {

    private static ListView mListView;
    private static Button btnOrder;
    private static ArrayList<FoodData> arrFoods;
    private static FoodListAdapter adapter;
    QLSP db = new QLSP(this);
    private static ArrayList<ListOrdering> lstOrdering;
    private String m_Text = "";
    final Context context = this;
    private int totalPrice = 0;
//    private static ArrayList<String> lstOrdering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main);
        mListView = (ListView) findViewById(R.id.listView);
        btnOrder = (Button) findViewById(R.id.btnDelTable);
//        lstOrdering = new ArrayList<ListOrdering>();
        lstOrdering = new ArrayList<ListOrdering>();

        try {
            String destPath = "/data/data/" + getPackageName() + "/databases/myFood.sqlite";
            CopyDB(getBaseContext().getAssets().open("myFood.sqlite"),
                    new FileOutputStream(destPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrFoods = new ArrayList<FoodData>();

        try {
            LoadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new FoodListAdapter(this,R.layout.activity_food_item, arrFoods);
        mListView.setAdapter(adapter);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intents = new Intent(getApplicationContext(), GridViewActivity.class);
//                Bundle bundles = new Bundle();
//                bundles.putStringArrayList("lst", lstOrdering);
//                intents.putExtra("lstOrdering", bundles);
                ListOrdering itemOrder;
                for (int i = 0; i < arrFoods.size(); i++) {


                    int number = Integer.parseInt(arrFoods.get(i).getNumber());
                    int price = Integer.parseInt(arrFoods.get(i).getprices().split(" ")[0].replace(".", ""));
                    String name = arrFoods.get(i).getname();
                    if (number != 0){
                        totalPrice += number*price;
                        itemOrder = new ListOrdering(name, number, price);
                        lstOrdering.add(itemOrder);

                    }
                }

                intents.putExtra("listOrdering", new DataWrapper(lstOrdering));
                intents.putExtra("totalPrice", totalPrice);
                setResult(GridViewActivity.RESULT_CODE_ADD, intents);
                finish();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Nhập số lượng");

                // Set up the input
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.requestFocus();
                input.setFocusableInTouchMode(true);
                builder.setView(input);

                input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            InputMethodManager inputMgr = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }
                    }
                });

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        FoodData item = (FoodData) arrFoods.get(position);
                        item.setNumber(m_Text);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();
            }
        });
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    private void LoadData() throws IOException {
        db.open();
        Cursor c = db.getAllFoods();
        if (c.getCount()==0) {
            LoadFirstTime();
        }
        else {
            if (c.moveToFirst()) {
                do {
                    String _name = c.getString(1);
                    String _price = c.getString(2);
                    String _image = c.getString(3);
                    Log.e("Name: ", _name);
                    // get image from drawable
                    Bitmap image = BitmapFactory.decodeResource(getResources(),
                            this.getResources().getIdentifier(_image,
                                    "drawable", getPackageName()));
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    arrFoods.add(new FoodData(imageInByte, _name, _price));
                } while (c.moveToNext());
            }
        }
        db.close();
    }

    private void LoadFirstTime() {
        int [] prgmImages={R.drawable.americano,R.drawable.ca_phe_sua_da,R.drawable.ca_phe_sua_nong,R.drawable.caramel_jelly_freeze,R.drawable.caramel_macchiato,R.drawable.chocolate_chip_cream,R.drawable.classic_jelly_freeze,R.drawable.caramel,R.drawable.caramel_cream_cheese,R.drawable.black_forest_mieng,R.drawable.cake_thom,R.drawable.cherry_bo,R.drawable.chocolate_cake,R.drawable.cookie_cream_freeze,R.drawable.cookies_hanhnhan};
        String [] prgmNameList={"Americano","Cà phê đên đá","Cà phê sữa nóng","Caramel Jelly Freeze","Caramel Macchiato","Chocolate Chip Cream","Classic Jelly Freeze","Caramel","Caramel Cream Cheese","Black Forest miếng","Bánh thơm","Cherry bơ","Chocolate Cake","Cookie Cream Freeze","Cookies hạnh nhân"};
        String [] prgmPriceList={"49.000 VND","29.000 VND","29.000 VND","55.000 VND","59.000 VND","90.000 VND","55.000 VND","21.000 VND","40.000 VND","29.000 VND","25.000 VND","60.000 VND","29.000 VND","55.000 VND","50.000 VND"};

        for (int i = 0; i < 15; i++) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), prgmImages[i]);
            Log.e("Name: ", prgmNameList[i]);
            // convert bitmap to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
            arrFoods.add(new FoodData(imageInByte,prgmNameList[i],prgmPriceList[i]));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_food, menu);
        return true;
    }

}
