package com.example.barcode_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class SendActivity extends AppCompatActivity {


    Button AddProduct,SendProduct;
    int sum = 0;
    TextView total;

    private static final String TAG = "bluetooth1";





    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
            insertP();
        AddProduct=(Button)findViewById(R.id.button4);

        AddProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                System.out.println("remoooooooooooooooooooooooooooooooooooveeeeeeeeeeeee");
                //table.removeAllViews();

                Intent intend = new Intent(SendActivity.this,MainActivity.class) ;
                startActivity(intend);
            }
        });

        SendProduct=(Button)findViewById(R.id.button5);
        SendProduct.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {

                  send();

          }
      });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void insertP ()


    {
        table= (TableLayout)findViewById(R.id.table1);


        total = (TextView) findViewById(R.id.textView8);


        for(int i=0 ; i <StaticObjects.ProductBill.size();i++) {


            final TableRow ProductRow = new TableRow(getApplicationContext());

            TextView PN = new TextView(getApplicationContext());
            TextView Num = new TextView(getApplicationContext());

            TextView PR = new TextView(getApplicationContext());

            final ImageButton DeleteB = new ImageButton(getApplicationContext());
            DeleteB.setImageResource(android.R.drawable.ic_menu_delete);
            DeleteB.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //DeleteB.setScaleType(ImageView.ScaleType.);
            // DeleteB.setAdjustViewBounds();

            DeleteB.setTag(StaticObjects.ProductBill.get(i).Barcode);
            DeleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow row2 = (TableRow) DeleteB.getParent();
                    for (int i = 0; i < StaticObjects.ProductBill.size(); i++) {
                        if (StaticObjects.ProductBill.get(i).Barcode == DeleteB.getTag()) {
                            sum = sum - StaticObjects.ProductBill.get(i).Price;

                            StaticObjects.ProductBill.remove(StaticObjects.ProductBill.get(i));

                            total.setText(String.valueOf(sum));
                        }
                    }
                    table.removeView(row2);
                }
            });


            PN.setText(StaticObjects.ProductBill.get(i).productName);
            PN.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            Num.setText(String.valueOf(StaticObjects.ProductBill.get(i).Number));
            Num.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            PR.setText(String.valueOf(StaticObjects.ProductBill.get(i).Price));
            PR.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //PR.setTag( new String(StaticObjects.ProductBill.get(i).Barcode) );


            ProductRow.addView(PN);
            ProductRow.addView(Num);
            ProductRow.addView(PR);
            ProductRow.addView(DeleteB);
            ProductRow.setGravity(Gravity.CENTER);


            table.addView(ProductRow);
        }
            sum=0;
        for (int s = 0; s < StaticObjects.ProductBill.size(); s++)
        {

            sum =sum+StaticObjects.ProductBill.get(s).Price;
            total.setText(String.valueOf(sum));

        }



    }
    void send()
    {


            String value = new String();
            for ( int i=0 ; i<StaticObjects.ProductBill.size() ; i++)
            {
                value+= StaticObjects.ProductBill.get(i).Barcode+","+StaticObjects.ProductBill.get(i).Number+";";
            }
            //String value2=String.valueOf(StaticObjects.ProductBill.get(i).Number);
           // String value3=String.valueOf(StaticObjects.ProductBill.get(i).Price);




                        try
                        {
                            StaticObjects.btSocket.getOutputStream().write(value.toString().getBytes());
                            //StaticObjects.btSocket.getOutputStream().write(StaticObjects.ProductBill.get(0).productName.toString().getBytes());
                            Log.d(TAG, "...Send data: " + value + "...");

                        }
                        catch (IOException e)
                        {
                            Toast.makeText(getApplicationContext(),
                                    "errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr2222"
                                    , Toast.LENGTH_LONG).show();
                        }


                }




    }



