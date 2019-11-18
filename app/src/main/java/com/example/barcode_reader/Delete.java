package com.example.barcode_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Delete extends AppCompatActivity {
    Button DeleteP;
    TextView textV2;
    EditText editT2;
    DatabaseHelper DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        DataBase = new DatabaseHelper(this);

        DeleteP=(Button)findViewById(R.id.button2);

        DeleteP.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                textV2=(TextView)findViewById(R.id.textV2);
                editT2=(EditText)findViewById(R.id.editT2);
                String bar=editT2.getText().toString();
                DataBase.Delete(bar);

            }
        });


    }

}
