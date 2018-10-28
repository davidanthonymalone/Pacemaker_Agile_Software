package utils;

import java.util.*;

import models.Activity;

public class CompareObj implements Comparator<Activity>{

    private ArrayList<Activity> sList;

    public CompareObj() {
        sList = new ArrayList<Activity>();
    }

    @Override
    public int compare(Activity s1, Activity s2) {
        // return s2.getDistance().compareTo(s1.getDistance());
         return Double.compare(s2.getDistance(), s1.getDistance());
    }

    public void add(Activity s1) {
        sList.add(s1);
    }

    public void displayList() {
        for(int i = 0; i<sList.size();i++) {
            System.out.println(sList.get(i).getDistance());
        }
    }
}