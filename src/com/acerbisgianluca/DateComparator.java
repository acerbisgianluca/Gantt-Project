package com.acerbisgianluca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Viene utilizzato all'interno della tabella per ordinare le colonne in base alla data.
 * @author Gianluca
 */
public class DateComparator implements Comparator {

    /**
     * Crea un nuovo DateComparator.
     */
    public DateComparator() {
    }

    /**
     * Calcola quale delle 2 date viene prima o se coincidono.
     * @param date1 La prima data.
     * @param date2 La seconda data.
     * @return 0 se le date coincidono (o se avviene un errore nel parsing), meno di 0 se le prima data viene prima della seconda e più di 0 se avviene il contrario.
     */
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
