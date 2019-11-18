package com.example.barcode_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class resultActivity extends AppCompatActivity {

    private String barCode;
    DatabaseHelper Database;
    TextView  product,price;
    Button Add,Cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Database=new DatabaseHelper(this);
        Cancel=(Button)findViewById(R.id.button5);
        Add=(Button)findViewById(R.id.button6);
        product = (TextView)findViewById(R.id.textView9);
        price=(TextView)findViewById(R.id.textView10);
        Bundle bundle = getIntent().getExtras();

        barCode = bundle.getString("barcode");



        ArrayList<HashMap<String, String>> PList=Database.GetProductByBarcode(barCode);
        for ( int a=0 ; a< PList.size() ; a++)
        {
            HashMap<String, String> element = PList.get(a);

            product.setText(element.get(DatabaseHelper.COL_2));

            price.setText(element.get(DatabaseHelper.COL_3));
        }

        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intend = new Intent(resultActivity.this,MainActivity.class) ;
                startActivity(intend);
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                boolean found=false;

                for(int i=0;i<StaticObjects.ProductBill.size();i++)
                {
                    if(barCode.equals(StaticObjects.ProductBill.get(i).Barcode))
                    {
                       StaticObjects.ProductBill.get(i).Number++;
                        StaticObjects.ProductBill.get(i).Price+=Integer.parseInt( price.getText().toString());
                        found=true;

                    }
                }
                if(found==false)
                {
                    node objectN=new node();
                    objectN.Barcode = barCode;
                    objectN.productName = product.getText().toString();
                    objectN.Price =Integer.parseInt( price.getText().toString());
                    objectN.Number = 1;
                    StaticObjects.ProductBill.add(objectN);
                }


                Intent intend = new Intent(resultActivity.this,SendActivity.class) ;
                startActivity(intend);
            }
        });

    }
}
