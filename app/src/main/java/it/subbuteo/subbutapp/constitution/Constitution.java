package it.subbuteo.subbutapp.constitution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import it.subbuteo.subbutapp.R;

public class Constitution {

    private Context ctx;

    public Constitution(Context ctx) {
        this.ctx = ctx;
    }

    public void showConstitution(){
        LayoutInflater inflater = LayoutInflater.from(ctx);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.constitution, null);
        setDetailData(popupView);

        PopupWindow mpopup = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        WindowManager wm = (WindowManager) popupView.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        mpopup.setWidth(width*9/10);
        mpopup.setHeight(height*8/10);
        mpopup.setAnimationStyle(android.R.style.Animation_Dialog);
        mpopup.setOutsideTouchable(true);
        mpopup.setFocusable(true);
        mpopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mpopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void setDetailData(View v){
        TextView constitution = v.findViewById(R.id.txt_constitution);
        constitution.setText(R.string.constitution);
    }

}
