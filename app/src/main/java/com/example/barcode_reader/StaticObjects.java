package com.example.barcode_reader;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

import java.util.ArrayList;

public class StaticObjects
 {
     static ArrayList<node> ProductBill= new ArrayList<node>();

     static BluetoothAdapter myBluetooth ;
     static BluetoothSocket btSocket;
}
