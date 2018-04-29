package com.victorsquarecity.app.login;

import android.view.View;

import com.victorsquarecity.app.R;
import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

/**
 * Created by mmasood on 2/23/2017.
 */

public class IntroActivity extends VerticalIntro {
    @Override
    protected void init() {
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.one)
                .image(R.drawable.kfc)
                .title("Welcome to VictorCity...")
                .text("Travel is only thing that makes you richer")
                .build());



        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.five)
                .image(R.drawable.bakefactory)
                .title("Did you know!! columbus had not VictorCity app that why it took him years to find USA")
                .text("Whole world just one shake of mobile away")
                .build());

        setSkipText("Skip");
       // setDoneText("Lets Be Columbus and discover the unseen!!!");
    }

    @Override
    protected Integer setLastItemBottomViewColor() {
        return R.color.seven;
    }

    @Override
    protected void onSkipPressed(View view) {

    }

    @Override
    protected void onFragmentChanged(int position) {

    }

    @Override
    protected void onDonePressed() {
        finish();
    }
}
