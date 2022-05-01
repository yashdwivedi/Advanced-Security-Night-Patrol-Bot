package com.e.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

@TargetApi(Build.VERSION_CODES.ECLAIR)
public class Bluetoooth extends AppCompatActivity {
    BluetoothAdapter myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    int bt = 1;
    TextView textView,textView2;
    Button button7,scan_devices;
    Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_bluetooth);
        textView = (TextView) findViewById(R.id.textView5);
        textView.setText("");
        textView2 = (TextView)findViewById(R.id.textView7);
        button7 = (Button) findViewById(R.id.button7);
        scan_devices = (Button) findViewById(R.id.scan_devices);
        on_off(scan_devices);
        on_off(button7);
    }

    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == bt) {
            if (resultCode == RESULT_OK) {
                button7 = (Button) findViewById(R.id.button7);
                scan_devices = (Button) findViewById(R.id.scan_devices);
               // on_off(button7);
                //on_off(scan_devices);
                textView = (TextView) findViewById(R.id.textView5);
                textView.setText("Click on the scan devices button and connect to the device named V.A.V.I");
                Toast.makeText(getApplicationContext(),"Bluetooth Connected",Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                //Bluetooth enablling is cancelled
            }
        }
    }

    public void call_on(View view) {
        Intent categoryIntent=new Intent(Bluetoooth.this,invisible_button.class);
        startActivity(categoryIntent);
    }

    public void connect(View view) {
        if(myBluetoothAdapter==null){
            textView = (TextView)findViewById(R.id.textView5);
            textView.setText("Bluetooth not supported");
        }
        else{
            if(!myBluetoothAdapter.isEnabled()){
                startActivityForResult(enableBt,bt);
                on_off(button7);
                on_off(scan_devices);
                textView2.setVisibility(View.INVISIBLE);
                textView.setText("");
            }
            else{
                button7 = (Button) findViewById(R.id.button7);
                scan_devices = (Button) findViewById(R.id.scan_devices);
                Toast.makeText(getApplicationContext(),"Bluetooth Activated",Toast.LENGTH_LONG).show();
                on_off(button7);
                on_off(scan_devices);
                textView2.setVisibility(View.INVISIBLE);
                textView = (TextView) findViewById(R.id.textView5);
                textView.setText("Click on the scan devices button and connect to the device named V.A.V.I");
            }
        }
    }
    public boolean on_off(Button a)
    {
        if(a.isEnabled())
        {
            a.setEnabled(false);
            return false;
        }
        else
        {
            a.setEnabled(true);
            return true;
        }
    }

    public void connect_devices(View view)
    {
        Intent bluetoothPicker = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
        startActivity(bluetoothPicker);
    }


}
