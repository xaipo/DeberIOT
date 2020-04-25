package com.riobytes.request;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

    }

    public void ingreso(View V){
        Intent myIntent = new Intent(getBaseContext(),   CameraRead.class);


//Add your data to bundle

        myIntent.putExtra("Ingreso", "+");
        ListItemsSingle.getInstance().listAdd = new ArrayList<ProductItem>();

        startActivity(myIntent);

    }
    public void remove(View V){
        Intent myIntent = new Intent(getBaseContext(),   CameraRead.class);

        myIntent.putExtra("Ingreso", "-");
        ListItemsSingle.getInstance().listRemove = new ArrayList<ProductItem>();
        startActivity(myIntent);

    }

}
