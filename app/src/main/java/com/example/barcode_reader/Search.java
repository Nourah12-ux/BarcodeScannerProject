package com.example.barcode_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity {
    Button search;
    DatabaseHelper DataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DataBase = new DatabaseHelper(this);

        search = (Button) findViewById(R.id.buttonS);

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText barcode=(EditText)findViewById(R.id.editT1);
                TextView TextV=(TextView)findViewById(R.id.textV3);
                String bar=barcode.getText().toString();
                ArrayList<HashMap<String, String>> PList=DataBase.GetProductByBarcode(bar);
                TextV.setText(Integer.toString(PList.size())+"\n");
                for ( int a=0 ; a< PList.size() ; a++)
                {
                    HashMap<String, String> element = PList.get(a);
                    TextV.append(
                            element.get(DatabaseHelper.COL_1)+"::" + "\t"+ element.get(DatabaseHelper.COL_2)+"::" + "\t"+
                                    "::"+element.get(DatabaseHelper.COL_3)+"\n");
                }

            }
        });

    }
}
