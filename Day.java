package com.example.kobihawk.personaguide;

/**
 * Created by Kobi Hawk on 2016-09-16.
 */
public class Day {
    private int month, day;
    String guidePortion;

    public Day(int month, int day, String guidePortion)
    {
        this.month = month;
        this.day = day;
        this.guidePortion = guidePortion;
    }
    public int getMonth()
    {
        return month;
    }
    public int getDay()
    {
        return day;
    }
    public String getGuide()
    {
        return guidePortion;
    }
}
