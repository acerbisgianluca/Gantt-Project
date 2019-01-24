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
    public int compare(Object o1, Object o2) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
            return fmt.parse((String) o1).compareTo(fmt.parse((String) o2));
        } catch (ParseException ex) {
            return 0;
        }
    }
}
