package com.example.barcode_reader;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    final String TAG = "barcodeReaderApp";
    private final static int ALL_PERMISSIONS_RESULT = 101;

    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    private boolean canGetMedia = false;


    private ZXingScannerView zXingScannerView;
    private Button AddD;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        BlutoothConnecting();
       // AddD=(Button)findViewById(R.id.button2);

        /*
        AddD.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                Intent intend = new Intent(MainActivity.this , Detailes.class) ;
                startActivity(intend);
            }

        });
*/
    }

    public void scan(View view){
        if(canGetMedia) {
            zXingScannerView = new ZXingScannerView(getApplicationContext());
            setContentView(zXingScannerView);
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        }else{
            showMessageOKCancel("No Permission",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        }

    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        if(canGetMedia) {
            zXingScannerView.stopCamera();
        }
    }
*/
    @Override
    public void handleResult(Result result) {
        //Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        //zXingScannerView.resumeCameraPreview(this);
        Intent intent = new Intent(MainActivity.this, resultActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("barcode",  result.getText());
        intent.putExtras(mBundle);
        startActivity(intent);
    }


    private void checkPermission()
    {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                        ALL_PERMISSIONS_RESULT);
                Log.d(TAG, "Permission requests");
            }else{

                canGetMedia = true;
            }
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetMedia = true;
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    void BlutoothConnecting()
    {
        StaticObjects.myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (StaticObjects.myBluetooth == null)
        {
            Toast.makeText(getApplicationContext(), "This device not support bluetooth", Toast.LENGTH_LONG).show();
        }
        else {
            if (!StaticObjects.myBluetooth.isEnabled()) {
                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                System.out.println("connectttttttttttttttttttttttt");
                startActivityForResult(enableAdapter, 1);
            }
            else
                BluetoothPairing();
        }
    }

    void BluetoothPairing()
    {
        if (StaticObjects.btSocket==null || !StaticObjects.btSocket.isConnected())
        {
            final ArrayList<String> deviceList = new ArrayList<String>();
            ArrayList<String> mDeviceList = new ArrayList<>();
            Set<BluetoothDevice> pairedDevices;
            final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


            Set<BluetoothDevice> all_devices = StaticObjects.myBluetooth.getBondedDevices();
            System.out.print("ggggggggggggggggggggggggggggggggggggggggggggg");
            String address = null;
            if (all_devices.size() > 0)
            {
                for (BluetoothDevice currentDevice : all_devices)
                {
                    address = currentDevice.getAddress();
                    deviceList.add("Device Name: " + currentDevice.getName() + "\nDevice Address: " + currentDevice.getAddress());

                }
                try {
                    Toast.makeText(getApplicationContext(),
                            "address :" + address
                            , Toast.LENGTH_LONG).show();
                    System.out.println("addressssssssssssssssssssssssssssssssssssssssssssssssssssssssss :" + address);
                    BluetoothDevice dispositivo = StaticObjects.myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    Toast.makeText(getApplicationContext(),
                            dispositivo.getAddress() + "*****" + dispositivo.getBondState()
                            , Toast.LENGTH_LONG).show();
                    System.out.println(dispositivo.getAddress() + "*****************************************" + dispositivo.getBondState());
                    StaticObjects.btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    //StaticObjects.myBluetooth.getDefaultAdapter().cancelDiscovery();
                    StaticObjects.btSocket.connect();
                    Toast.makeText(getApplicationContext(),
                            "connected"
                            , Toast.LENGTH_LONG).show();
                } catch (IOException e)
                {
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n" + e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
                            , Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            System.out.println("previossssssssssssssssssssssssssssssssssssss connect");
            BluetoothPairing();
        }
    }
}
