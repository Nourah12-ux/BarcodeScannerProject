package com.example.barcode_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Detailes extends AppCompatActivity {

    DatabaseHelper DataBase;

    EditText barcodeT, product_name, price;
    Button save, searchProduct , DeleteProduct,BD;
    TextView list;
    String value1 ,value2 , value3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        DataBase = new DatabaseHelper(this);
        barcodeT = (EditText) findViewById(R.id.txtbarcode);
        product_name = (EditText) findViewById(R.id.txtPname);
        price = (EditText) findViewById(R.id.txtPrice);

        searchProduct=(Button)findViewById(R.id.btnSearch);
        DeleteProduct=(Button)findViewById(R.id.button3);
        save = (Button) findViewById(R.id.btnSave);
        list = (TextView) findViewById(R.id.textV2);

        BD=(Button)findViewById(R.id.button4);



        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String barcodeNum = barcodeT.getText().toString();
                String ProductName = product_name.getText().toString();
                String PriceNum = price.getText().toString();
                DataBase.insert(barcodeNum,ProductName,PriceNum);
                list.setText("");
                ArrayList<HashMap<String, String>> productList = DataBase.GetProduct();
                list.append("size = " + productList.size()+"\n");
                for ( int a=0 ; a< productList.size() ; a++)
                {
                    HashMap<String, String> element = productList.get(a);
                    value1 = element.get(DatabaseHelper.COL_1);
                    value2=element.get(DatabaseHelper.COL_2);
                    value3=element.get(DatabaseHelper.COL_3);
                    list.append(
                            value1 + "::"+ value2+"::"+ value3+"\n");
                }

            }
        });
        searchProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intend = new Intent(Detailes.this , Search.class) ;
                startActivity(intend);
            }
        });

        DeleteProduct.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent intend = new Intent(Detailes.this , Delete.class) ;
                startActivity(intend);
            }
        });

        BD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcodeT.setText("");
                product_name.setText("");
                price.setText("");
            }
        });




    }
}
