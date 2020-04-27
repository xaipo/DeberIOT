package com.riobytes.request;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ListItems extends AppCompatActivity {
String typeTransaccion;
String idProduct;
String mount;
    ListView listView;
    List<ProductItem> showListProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();

            }
        });
        FloatingActionButton fab1 = findViewById(R.id.fab2);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        typeTransaccion  = getIntent().getExtras().getString("Signo");
        idProduct  = getIntent().getExtras().getString("Value");
        mount  = getIntent().getExtras().getString("Mount");

        ProductItem pro= new ProductItem(idProduct,mount);
        ProductItem aux;
        showListProducts= new ArrayList<ProductItem>();
        boolean flag= false;
        int index=0;
        if(typeTransaccion.equals("+")) {
            for (int i = 0; i < ListItemsSingle.getInstance().listAdd.size(); i++) {
                Toast.makeText(ListItems.this,ListItemsSingle.getInstance().listAdd.get(i).sku,Toast.LENGTH_SHORT).show();
                Toast.makeText(ListItems.this,idProduct,Toast.LENGTH_SHORT).show();
                if (ListItemsSingle.getInstance().listAdd.get(i).sku.equals( idProduct) ){
                    index = i;
                    flag = true;
                    break;
                }
            }
        }else{
            for (int i = 0; i < ListItemsSingle.getInstance().listRemove.size(); i++) {
                if (ListItemsSingle.getInstance().listRemove.get(i).sku.equals( idProduct)) {
                    index = i;
                    flag = true;
                    break;
                }
            }
        }
        if(typeTransaccion.equals("+")){
            if(flag){
                aux=ListItemsSingle.getInstance().listAdd.get(index);
                int products = Integer.parseInt(aux.mount)+ Integer.parseInt(mount);
                aux.mount=String.valueOf(products);
                ListItemsSingle.getInstance().listAdd.set(index,aux);

            }else{
                ListItemsSingle.getInstance().listAdd.add(pro);
            }

            showListProducts=ListItemsSingle.getInstance().listAdd;
        }else{
            if(flag){
                aux=ListItemsSingle.getInstance().listRemove.get(index);
                int products = Integer.parseInt(aux.mount)+ Integer.parseInt(mount);
                aux.mount=String.valueOf(products);
                ListItemsSingle.getInstance().listRemove.set(index,aux);

            }else{
                ListItemsSingle.getInstance().listRemove.add(pro);
            }
            showListProducts=ListItemsSingle.getInstance().listRemove;

        }


        listView = findViewById(R.id.listView);
        // now create an adapter class

        String rSku []= new String[showListProducts.size()];
        String rMount [] = new String[showListProducts.size()];
        for(int i=0;i<showListProducts.size();i++){
            rSku[i]=showListProducts.get(i).sku;
            rMount[i]=showListProducts.get(i).mount;
        }
        MyAdapter adapter = new MyAdapter(this, rSku,rMount);
        listView.setAdapter(adapter);
        // there is my mistake...
        // now again check this..

        // now set item click on list view

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText(ListItems.this, "Facebook Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ListItems.this, "Whatsapp Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ListItems.this, "Twitter Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ListItems.this, "Instagram Description", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ListItems.this, "Youtube Description", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ListItems.this, "Youtube Description", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void sendRequest(View view) {


        try {

            String Url = "http://159.89.55.10:3005/history/saveHistory";
            JSONArray arr = new JSONArray();
            if (typeTransaccion.equals("+")) {
                for (int i = 0; i < ListItemsSingle.getInstance().listAdd.size(); i++) {
                    JSONObject obj = new JSONObject();
                    obj.put("sku", ListItemsSingle.getInstance().listAdd.get(i).sku);
                    obj.put("stock", ListItemsSingle.getInstance().listAdd.get(i).mount);
                    arr.put(obj);
                }
            } else {
                for (int i = 0; i < ListItemsSingle.getInstance().listRemove.size(); i++) {

                }
            }

            JSONObject send = new JSONObject();
            send.put("description", "MOVIMIENTO_BODEGA");
            send.put("tipo", typeTransaccion.toString());
            send.put("products", arr);


            StringEntity entity = new StringEntity(send.toString());
            AsyncHttpClient client = new AsyncHttpClient();


            client.post(getApplicationContext(), Url, entity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                    try {


                        //user.setUserId(obj.getInt("userid"));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "404 - Nie odnaleziono serwera!", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "500 - Coś poszło nie tak po stronie serwera!", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 403) {
                        Toast.makeText(getApplicationContext(), "Podano niepoprawne dane!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), throwable.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];


        MyAdapter(Context c, String title[], String description[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
           // ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            // now set our resources on views
           // images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            row.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view) {
                    final View view1=view;
                    new AlertDialog.Builder(ListItems.this)
                            .setTitle("Title")
                            .setMessage("Esta seguro que desea eliminar?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    for (int i = 0; i < ListItemsSingle.getInstance().listAdd.size(); i++) {
                     /*   Toast.makeText(ListItems.this,ListItemsSingle.getInstance().listAdd.get(i).sku,Toast.LENGTH_SHORT).show();
                        Toast.makeText(ListItems.this,idProduct,Toast.LENGTH_SHORT).show();*/
                                        ListView listView = findViewById(R.id.listView);
                                        Object search =listView.getItemAtPosition(listView.indexOfChild(view1));
                                        if(typeTransaccion.equals("+")) {
                                            if (ListItemsSingle.getInstance().listAdd.get(i).sku.equals(String.valueOf(search))) {

                                                ListItemsSingle.getInstance().listAdd.remove(i);
                                                showListProducts = ListItemsSingle.getInstance().listAdd;
                                            }
                                        }else{

                                            if (ListItemsSingle.getInstance().listRemove.get(i).sku.equals(String.valueOf(search))) {

                                                ListItemsSingle.getInstance().listRemove.remove(i);
                                                showListProducts = ListItemsSingle.getInstance().listRemove;
                                            }
                                        }
                                            //  remove(listView.indexOfChild(view));

                                            String rSku []= new String[showListProducts.size()];
                                            String rMount [] = new String[showListProducts.size()];
                                            for(int j=0;j<showListProducts.size();j++){
                                                rSku[j]=showListProducts.get(j).sku;
                                                rMount[j]=showListProducts.get(j).mount;
                                            }
                                            MyAdapter adapter = new MyAdapter(ListItems.this, rSku,rMount);
                                            listView.setAdapter(adapter);
                                            break;

                                    }
                                }})
                            .setNegativeButton(android.R.string.no, null).show();





                    return true;
                }
            });
            return row;
        }
    }
}
