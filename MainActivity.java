package com.example.kobihawk.personaguide;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;
import android.widget.CalendarView;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.example.kobihawk.personaguide.Day;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, CalendarView.OnDateChangeListener {

    private TextView changeText;
    private CalendarView calendar;
    private ScrollView scrollView;

    private GestureDetectorCompat gestureDetector;

    private String guide = "";
    private String[] parts;
    private Day[] allDays;
    private int index;

    private static final int MIN_DISTANCE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load all of our android stuff
        index = 0;
        changeText = (TextView)findViewById(R.id.changeText);
        calendar = (CalendarView)findViewById(R.id.calendarView);
        this.gestureDetector = new GestureDetectorCompat(this, this);

        //load the guide
        String guide = loadGuide();
        parts = guide.split("<>---------------------------------------------------------------------------<>");
        loadDays();

        gestureDetector.setOnDoubleTapListener(this);
        calendar.setOnDateChangeListener(this);
    }

    public String loadGuide()
    {
        String result = "";
        AssetManager assetManager = getBaseContext().getAssets();
        try {
            result = new Scanner(assetManager.open("P3PGuide.txt")).useDelimiter("//Z").next(); //copies entire file to string
        } catch (IOException e)
        {
            System.out.println("ABORT MISSION");
        }
        return result;
    }

    public void loadDays()
    {
        Scanner scanner;
        int month = 0;
        int day = 0;
        String temp;
        String[] tokens;

        allDays = new Day[parts.length];

        for(int i = 1; i < allDays.length-1; i++) // first index (parts[0]) is blank
        {
            temp = parts[i];

            /*
            The following three split statements are meant to isolate the data we care about; the date.
            A typical line will look like "\n4/6, wordswordswords".
            The first split will split that into "\n4/6" and " wordswordswords"
            The second split will split that into "" and "4/6"
            The third split will split that into "4" and "6".
             */
            tokens = parts[i].split(",", 2);
            tokens = tokens[0].split("\n", 2);
            tokens = tokens[1].split("/", 2);

            month = Integer.parseInt(tokens[0]);
            day = Integer.parseInt(tokens[1]);
            allDays[i] = new Day(month, day, temp);
        }
    }

    @Override
    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) { // year, month, day

        int j = 1;
        boolean found = false;
        while((j < allDays.length-1) && !found)
        {
            if((allDays[j].getMonth() == i1+1) && (allDays[j].getDay() == i2))
            {
                found = true;
            }
            else
            {
                j++;
            }
        }
        if(found)
        {
            index = j;
            changeText.setText(allDays[j].getGuide());
            adjustSize();
        }
        else
        {
            changeText.setText("No action today");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x1, x2, deltaX;
        //scrollView = (scrollView)findViewById()

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                deltaX = x2 - x1;

                if(Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if(x1 > x2) // right to left
                    {

                        if(index < parts.length-1)
                        {
                            index++;
                            changeText.setText(parts[index]);
                            adjustSize();
                        }
                    }
                    else
                    {
                        if(index > 0)
                        {
                            index--;
                            changeText.setText(parts[index]);
                            adjustSize();
                        }
                    }
                }
                else
                {
                    //tap
                }
                break;
        }
        return true;
    }

    public void adjustSize()
    {
        int theLength = allDays[index].getGuide().length();

        if(theLength > 160)
        {
            changeText.setTextSize(14);
        }
        else if(theLength > 120)
        {
            changeText.setTextSize(18);
        }
        else if(theLength > 80)
        {
            changeText.setTextSize(20);
        }
        else
        {
            changeText.setTextSize(24);
        }
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

}
