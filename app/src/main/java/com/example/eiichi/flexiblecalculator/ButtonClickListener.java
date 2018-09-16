package com.example.eiichi.flexiblecalculator;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ButtonClickListener implements OnClickListener {
    private MainActivity mMainActivity_;
    ButtonClickListener(MainActivity aActivity)
    {
        mMainActivity_ = aActivity;
    }

    @Override
    public void onClick(View v) {
       Button button = (Button) v;
       Integer id = v.getId();
        mMainActivity_.addNumber(id.toString());
    }
}
