package com.johnson.cannabis.utils;

import com.johnson.cannabis.models.Strains;

import java.util.Comparator;

public class CustomComparator implements Comparator<Strains> {

    @Override
    public int compare(Strains s1, Strains s2) {
        return s2.getRating().compareTo(s1.getRating());
    }
}