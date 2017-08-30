package com.example.android.weathermap.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.android.weathermap.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import static android.provider.Settings.System.AIRPLANE_MODE_ON;


/**
 * Created by HP on 02-Jul-17.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    String iconurl;
    String cityName;
    String description;
    String temperature;


    public CustomInfoWindowAdapter(Context context, String iconurl, String cityName, String description, String temperature) {
        this.context = context;
        this.iconurl = iconurl;
        this.cityName = cityName;
        this.description = description;
        this.temperature = temperature;
    }
        /**
         * Gets the state of Airplane Mode.
         *
         * @param context
         * @return true if enabled.
         */
//        @SuppressWarnings("deprecation")
//        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//
//        public static boolean isAirplaneModeOn(Context context) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                return Settings.System.getInt(context.getContentResolver(),
//                        Settings.System.AIRPLANE_MODE_ON, 0) != 0;
//            } else {
//                return Settings.Global.getInt(context.getContentResolver(),
//                        Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
//            }
//        }
//

        static boolean isAirplaneModeOn(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            return Settings.System.getInt(contentResolver, AIRPLANE_MODE_ON, 0) != 0;
        }

    @Override
    public View getInfoWindow(Marker marker) {
        return  null;


    }

    @Override
    public View getInfoContents(Marker marker) {
        Fresco.initialize(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.custom_info_contents, null);
        //ImageView weathericon = (ImageView) view.findViewById(R.id.weather_icon);
        SimpleDraweeView weathericon = (SimpleDraweeView) view.findViewById(R.id.weather_icon);
        TextView city = (TextView) view.findViewById(R.id.cityName);
        TextView temp = (TextView) view.findViewById(R.id.temperature);
        TextView describe = (TextView) view.findViewById(R.id.description);
        city.setText(cityName);
        temp.setText(temperature);
        describe.setText(description);
        Log.d("Adapter", "getInfoContents: " + iconurl);































//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.listener(new Picasso.Listener() {
//            @Override
//            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                Log.e(TAG, "onImageLoadFailed: "+exception +"\t"+ uri );
//            }
//        });
//        Picasso pic = builder.build();
//        if(!isAirplaneModeOn(context))
//        {
//        pic.load(iconurl)
//             .error(R.mipmap.ic_launcher)
//             .placeholder(R.mipmap.ic_launcher)
//             .into(weathericon, new Callback() {
//                         @Override
//                         public void onSuccess() {
//                             Log.d(TAG, "onSuccess: ");
//                         }
//
//                         @Override
//                         public void onError() {
//                             Log.d(TAG, "onError: ");
//
//                         }
//                     }
//
//
//             );  }
//        else {
//            Log.e(TAG, "getInfoContents: "+"ON" );
//        }
//

//        Picasso.with(MapsActivity.getContext()).load(iconurl).fit().centerCrop().
//                noFade().
//                placeholder(R.mipmap.ic_launcher).error(R.color.colorAccent).into(weathericon);

//        Picasso.with(context).load(iconurl).fit().centerCrop().
//                noFade().
//                placeholder(R.mipmap.ic_launcher).error(R.color.colorAccent).into(weathericon);
//        Glide.with(context).load("http://openweathermap.org/img/w/03d.png").
//               into(weathericon);

        weathericon.setImageURI(iconurl);
        return  view;
    }
}