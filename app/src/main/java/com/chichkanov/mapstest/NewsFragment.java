package com.chichkanov.mapstest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chichkanov.mapstest.model.Storage;
import com.chichkanov.mapstest.model.Subject;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by chichkanov on 08.07.17.
 */

public class NewsFragment extends Fragment {

    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_news, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preferences_name), 0);
        Collection<Subject> subjects = Storage.getInstance(getContext()).getNearestSchedule(sharedPreferences.getString(getString(R.string.pref_school_name), getString(R.string.pref_default_school)));
        Iterator<Subject> iterator = subjects.iterator();
        if(iterator.hasNext()) {
            initRasp(layout, iterator.next());
        }
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCountDown(view);
        countDownTimer.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
        countDownTimer = null;
    }

    private void initRasp(View layout, Subject subject){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM - hh:mm");
        ((TextView) layout.findViewById(R.id.rasp_tv_name)).setText(subject.getTitle());
        ((TextView) layout.findViewById(R.id.rasp_tv_time)).setText(format.format(subject.getTime().getTime()));
        ((TextView) layout.findViewById(R.id.rasp_tv_duration)).setText(String.valueOf(subject.getDuration()) + " часа");
        ((TextView) layout.findViewById(R.id.rasp_tv_teacher)).setText(subject.getTeacher().get(0));
        ((TextView) layout.findViewById(R.id.rasp_tv_place)).setText(subject.getLocation());
    }

    private void initCountDown(View layout){
        final TextView tv = ((TextView) layout.findViewById(R.id.deadline_time));
        Calendar end = Calendar.getInstance();
        long start = end.getTimeInMillis();
        end.set(2017, 6, 9, 23, 45, 0);
        long duration = end.getTimeInMillis() - start;

        countDownTimer = new CountDownTimer(duration, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("Осталось: " + millisUntilFinished / 1000 / 60 / 60 + " часов " + millisUntilFinished / 1000 / 60 % 60 + " минут " + millisUntilFinished / 1000 % 60 + " секунд ");
            }

            public void onFinish() {
                tv.setText("done!");
            }
        };
    }
}
