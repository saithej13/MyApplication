package com.vst.myapplication.Utils;

import static java.nio.file.Files.write;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.databinding.SettingsBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

public class Settings extends BaseFragment {
    private static final String PRINTER_NAME = "MPT-II"; // Update with your printer name
    private static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private ProjectRepository repository;
    SettingsBinding binding;
    BluetoothAdapter bluetoothAdapter;
    static BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    public static OutputStream outputStream;
    InputStream inputStream;
    Thread thread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    boolean sendsms=false;
    boolean sendwhatsapp=false;
    private Context context;

    private Vector<BluetoothDevice> remoteDevices;



    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings, parent, false);
        repository = new ProjectRepository();
        this.context = getContext();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater, parent, viewLifecycleOwner, savedInstanceState);
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner, Bundle savedInstanceState) {
//        final String[] selectPrinter =attendance_types.toArray(new String[0]);
        binding.tvprinter.setText(preference.getStringFromPreference(Preference.Select_Printer, "") + "");
        binding.tvprintersize.setText(preference.getStringFromPreference(Preference.Select_Printer_Size, "") + "");
        binding.tvRCF.setText(preference.getStringFromPreference(Preference.Select_Rate_collection_formate, "") + "");
        binding.tvbillperiod.setText(preference.getIntFromPreference(Preference.Select_Bill_Period, 0) + "");

        sendsms = preference.getbooleanFromPreference("sendsms",false);
        sendwhatsapp = preference.getbooleanFromPreference("sendwhatsapp",false);
        final String[] selectPrinter = {"Printer 1", "Printer 2", "Printer 3"};
//        binding.tvprinter.setOnClickListener(v -> setDropDown(v, binding.tvprinter, selectPrinter));

        final String[] selectPrinterSize = {"2 Inch", "3 Inch"};
        binding.tvprintersize.setOnClickListener(v -> setDropDown(v, binding.tvprintersize, selectPrinterSize));

        final String[] selectRCF = {"1", "2", "3"};
        binding.tvRCF.setOnClickListener(v -> setDropDown(v, binding.tvRCF, selectRCF));

        binding.tbsms.setChecked(sendsms);
        binding.tbwhatsapp.setChecked(sendwhatsapp);

        binding.tvSubmit.setOnClickListener(v -> {
//            AppConstants.Select_Printer = binding.tvprinter.getText().toString();
//            AppConstants.Select_Printer_Size = binding.tvprintersize.getText().toString();
//            AppConstants.Select_Rate_collection_formate = binding.tvRCF.getText().toString();
            preference.saveStringInPreference(Preference.Select_Printer, binding.tvprinter.getText().toString());
            preference.saveStringInPreference(Preference.Select_Printer_Size, binding.tvprintersize.getText().toString());
            preference.saveStringInPreference(Preference.Select_Rate_collection_formate, binding.tvRCF.getText().toString());
            preference.saveIntInPreference(Preference.Select_Bill_Period, Integer.parseInt(binding.tvbillperiod.getText().toString()));
            preference.saveBooleanInPreference("sendwhatsapp", binding.tbwhatsapp.isChecked());
            preference.saveBooleanInPreference("sendsms", binding.tbsms.isChecked());
            preference.commitPreference();
            try {
                printData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        binding.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FindBluetoothDevice();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        binding.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.image100);
                    printImage(bm);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        binding.disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    disconnectBT();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        binding.tvprinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent bluetoothPicker = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
                    startActivity(bluetoothPicker);
//                    openBluetoothPrinter();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


    }
    void printData() throws  IOException{
        try{
            String msg = "***Test Print Success***";
            msg+="\n";
            msg+="pinting in next line";
            msg+="\n";
            outputStream.write(msg.getBytes());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    void FindBluetoothDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, 1);
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
                return;
            }
        }

        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                binding.tvprinter.setText("No Bluetooth Adapter found");
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                ((Activity) context).startActivityForResult(enableBT, 0);
                return;
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice pairedDevice : pairedDevices) {
                    if (pairedDevice.getName().equals(PRINTER_NAME)) {
                        bluetoothDevice = pairedDevice;
                        binding.tvprinter.setText("Bluetooth Printer Attached: " + pairedDevice.getName());
                        openBluetoothPrinter();
                        break;
                    }
                }
            } else {
                binding.tvprinter.setText("No paired Bluetooth devices found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void disconnectBT() throws IOException {
        try {
            stopWorker = true;
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                bluetoothSocket = null;
            }
            binding.tvprinter.setText("Printer Disconnected.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            try {
                openBluetoothPrinter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //setupbtooth

    void openBluetoothPrinter() throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                return;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
                return;
            }
        }

        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(PRINTER_UUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();
            beginListenData();
        } catch (IOException e) {
            // Ensure resources are closed if connection fails
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                bluetoothSocket = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            e.printStackTrace();
            throw e;
        }
    }

    void beginListenData(){
        try{

            final Handler handler =new Handler();
            final byte delimiter=10;
            stopWorker =false;
            readBufferPosition=0;
            readBuffer = new byte[1024];

            thread=new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if(byteAvailable>0){
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for(int i=0; i<byteAvailable; i++){
                                    byte b = packetByte[i];
                                    if(b==delimiter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer,0,
                                                encodedByte,0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte, StandardCharsets.US_ASCII);
                                        readBufferPosition=0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
//                                                lblprinter.setText(data);
                                            }
                                        });
                                    }else{
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }
                            }
                        }catch(Exception ex){
                            stopWorker=true;
                        }
                    }

                }
            });

            thread.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void printPhoto() {
        try {
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.milk);
//            if(bmp!=null){
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inTargetDensity = 200;
//                options.inDensity = 200;
//                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.milk, options);
//                outputStream.write(bmp);
//                outputStream.flush();
//                outputStream.close();
//            }else{
//                Log.e("Print Photo error", "the file isn't exists");
//            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;

            // Decode the drawable resource into a bitmap
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.image100, options);
            ByteArrayOutputStream byteArrayOutputStream = null;
            if (bmp != null) {
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                    // Convert ByteArrayOutputStream to byte array
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    String base64ToPrint = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    // Compress the bitmap into PNG format and write to output stream
                    outputStream.write(base64ToPrint.getBytes());
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    printBitmap(bmp);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Handle the case where bitmap could not be decoded
                System.out.println("Failed to decode resource into bitmap.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    public static void printImage(Bitmap bm) {
        try {
            PrintPic printPic1 = PrintPic.getInstance();
            printPic1.init(bm);
            byte[] bitmapdata2 = printPic1.printDraw();
            outputStream.write(bitmapdata2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                outputStream.flush();
//                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printNewLine() {
        try {
            outputStream.write(PrintCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printBitmap( Bitmap bitmap) {
//        try {
//            OutputStream outputStream = bluetoothSocket.getOutputStream();
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            byte[] imageBytes = byteArrayOutputStream.toByteArray();
//            outputStream.write(imageBytes);
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (bitmap == null) {
            System.err.println("Bitmap is null.");
            return;
        }

        OutputStream outputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            // Ensure BluetoothSocket is not null
            if (bluetoothSocket == null) {
                throw new IllegalArgumentException("BluetoothSocket cannot be null.");
            }

            // Get the output stream from the Bluetooth socket
            outputStream = bluetoothSocket.getOutputStream();

            // Compress the bitmap into PNG format and write to ByteArrayOutputStream
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            // Convert ByteArrayOutputStream to byte array
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Write byte array to OutputStream
            outputStream.write(imageBytes);
            outputStream.flush();

        } catch (IOException e) {
            System.err.println("IOException occurred while printing bitmap: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close ByteArrayOutputStream and OutputStream in the finally block
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                System.err.println("IOException occurred while closing streams: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /*  COPIED   */


}
