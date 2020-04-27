package com.riobytes.request;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraRead extends AppCompatActivity implements ZXingScannerView.ResultHandler  {
    private ZXingScannerView scannerView;
    private TextView txtResult;
    private String typeTransaccion;
    private String m_Text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_read);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //implementation
        scannerView= (ZXingScannerView)findViewById(R.id.zxscan);
        txtResult = (TextView)findViewById(R.id.txt_result);

        typeTransaccion  = getIntent().getExtras().getString("Ingreso");
        //request permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(CameraRead.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(CameraRead.this,"Aceptar que la aplicacion utilice la camara",Toast.LENGTH_SHORT).show();
                        // MainActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 1011);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
        txtResult.setText(typeTransaccion);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(CameraRead.this,"Puede enviar datos",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(CameraRead.this,"Aceptar que la aplicacion utilice la camara",Toast.LENGTH_SHORT).show();
                        // MainActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 1011);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
        txtResult.setText(typeTransaccion);
    }
    @Override
    protected  void onDestroy (){
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult (Result rawResult){
        //this recibe resutl

        txtResult.setText(rawResult.getText());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1011: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Here user granted the permission
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(CameraRead.this, "Permission denied to read your Camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void  restart (View V){
        scannerView.setResultHandler(CameraRead.this);
        scannerView.startCamera();
        txtResult.setText("Reintentar");
    }

    public void  next (View V){

        if(txtResult.getText()!=typeTransaccion && txtResult.getText()!= "Reintentar") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cantidad");
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();

                    Intent myIntent = new Intent(getBaseContext(),   ListItems.class);

                    myIntent.putExtra("Signo", typeTransaccion);
                    myIntent.putExtra("Value", txtResult.getText());
                    myIntent.putExtra("Mount", m_Text);
                    startActivity(myIntent);


                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }else{
            Toast.makeText(CameraRead.this,"Debe leer un codigo para continuar",Toast.LENGTH_SHORT).show();

        }

    }

}
