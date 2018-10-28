package utils;

import java.util.*;

import models.Activity;

public class CompareObjLocation implements Comparator<Activity>{

    private ArrayList<Activity> sList;

    public CompareObjLocation() {
        sList = new ArrayList<Activity>();
    }

    @Override
    public int compare(Activity s1, Activity s2) {
        // return s2.getDistance().compareTo(s1.getDistance());
    	 return s2.getLocation().compareTo(s1.getLocation());    }

    public void add(Activity s1) {
        sList.add(s1);
    }

    public void displayList() {
        for(int i = 0; i<sList.size();i++) {
            System.out.println(sList.get(i).getLocation());
        }
    }
}