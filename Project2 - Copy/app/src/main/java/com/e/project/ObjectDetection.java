package com.e.project;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class ObjectDetection extends AppCompatActivity {

    //TextView t1,t2;

    String address = null , name = null;

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    TextToSpeech mTTS;
    Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    boolean stopThread = false;
    public String s = "";
    byte buffer[];
    private OutputStream outputStream;
    private InputStream inputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detection);
        //t2 = (TextView)findViewById(R.id.output_obj);
        //t1 = (TextView)findViewById(R.id.textView8);
        connect();
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }


    public void connect()
    {
        try
        {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            address = myBluetooth.getAddress();
            pairedDevices = myBluetooth.getBondedDevices();

            if(pairedDevices.size()>0)
            {
                for(BluetoothDevice bt: pairedDevices)
                {
                    address = bt.getAddress().toString();
                    name = bt.getName().toString();
                    Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                }
            }
        }

        catch (Exception we){}
        myBluetooth = BluetoothAdapter.getDefaultAdapter();     //get the mobile bluetooth Device
        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address); //connects to the specific device
        try {
            btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
            btSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try
        {
            //t1.setText("connected");
        }
        catch (Exception e){}
        send_data("O");
    }

    public void send_data(String str)
    {
        try
        {
            if(btSocket!=null) {
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();

                btSocket.getOutputStream().write(str.toString().getBytes());

            }
            else
                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        //String st = rec_data();
        /*float pitch = 1f;
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(0.8f);
        mTTS.speak("Laptop, mouse, and keyboard", TextToSpeech.QUEUE_FLUSH, null);*/
        Intent categoryIntent=new Intent(ObjectDetection.this,invisible_button.class);
        startActivity(categoryIntent);

    }

    public String rec_data()
    {
        /*
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        s = "";
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {
                                   t2.setText(string);
                                }
                            });

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
        //t1.setText(s);*/
        s="Laptop, mouse, and keyboard";
        return s;
    }


}
