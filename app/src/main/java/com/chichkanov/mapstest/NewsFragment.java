package com.chichkanov.mapstest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chichkanov.mapstest.model.Subject;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by chichkanov on 08.07.17.
 */

public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_news, container, false);
        initRasp(layout, new Subject(2.0, "Cambridge", new ArrayList<String>(), new ArrayList<String>(), "2 june 1991", "Разработка чего-то выфвыфв" ));
        initCountDown(layout);
        return layout;
    }

    private void initRasp(View layout, Subject subject){
        ((TextView) layout.findViewById(R.id.rasp_tv_name)).setText(subject.getTitle());
        ((TextView) layout.findViewById(R.id.rasp_tv_time)).setText(subject.getTime());
        ((TextView) layout.findViewById(R.id.rasp_tv_duration)).setText(String.valueOf(subject.getDuration()));
        ((TextView) layout.findViewById(R.id.rasp_tv_teacher)).setText("Test");
        ((TextView) layout.findViewById(R.id.rasp_tv_place)).setText(subject.getLocation());
    }

    private void initCountDown(View layout){
        final TextView tv = ((TextView) layout.findViewById(R.id.deadline_time));
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tv.setText("done!");
            }
        }.start();
    }
}
