package utils;

import java.util.*;

import models.Activity;

public class CompareObjType implements Comparator<Activity>{

    private ArrayList<Activity> sList;

    public CompareObjType() {
        sList = new ArrayList<Activity>();
    }

    @Override
    public int compare(Activity s1, Activity s2) {
        // return s2.getDistance().compareTo(s1.getDistance());
         return s2.getType().compareTo(s1.getType());
    }

    public void add(Activity s1) {
        sList.add(s1);
    }

    public void displayList() {
        for(int i = 0; i<sList.size();i++) {
            System.out.println(sList.get(i).getType());
        }
    }
}