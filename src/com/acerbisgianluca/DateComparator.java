package com.acerbisgianluca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DateComparator implements Comparator {

    protected boolean isSortAsc;
    protected Integer numeroColonna;

    public DateComparator() {
    }

    @Override
    public int compare(Object date1, Object date2) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
            return fmt.parse((String) date1).compareTo(fmt.parse((String) date2));
        } catch (ParseException ex) {
            return 0;
        }
    }
}
