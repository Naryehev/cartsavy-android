package com.example.a20.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 20 on 7/12/2017.
 */

public class listAdapter extends ArrayAdapter<scannedFood>{

   private Activity Context ;
    private List<scannedFood> foodlist;
    private DatabaseReference databaseReference;

    public listAdapter(Activity context, List<scannedFood> foods ){
        super(context, R.layout.fridge_list, foods);
        this.Context = context;
        this.foodlist = foods;

    }
    @NonNull
    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        final LayoutInflater  inflater = Context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.fridge_list,null, true);
        TextView fridgeitems = (TextView) listViewItem.findViewById(R.id.foodtitle);
        TextView descrption = (TextView) listViewItem.findViewById(R.id.description);
        ImageView thumbnail = (ImageView) listViewItem.findViewById(R.id.thumbnail);
        final scannedFood scanfood = foodlist.get(position);

        fridgeitems.setText(scanfood.getProduct_name_en());
        descrption.setText(scanfood.getIngredients_text());
        String thumb = scanfood.getImage_url();
        Picasso.with(Context).load(String.valueOf(thumb)).into(thumbnail);


        return listViewItem;
    }

}
