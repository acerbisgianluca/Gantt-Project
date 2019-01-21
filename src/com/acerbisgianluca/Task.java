package com.acerbisgianluca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Task implements Cloneable {

    private final String name;
    private final int duration;
    private final List<String> dependencies;
    private final List<Task> parents;
    private GregorianCalendar start;
    private GregorianCalendar end;

    public Task(String name, GregorianCalendar start, int duration, List<String> dependencies) {
        this.name = name;
        this.start = (GregorianCalendar) start.clone();
        this.duration = duration;
        this.dependencies = dependencies;
        this.parents = new ArrayList<>();
        this.end = (GregorianCalendar) this.start.clone();
        this.end.add(Calendar.DAY_OF_MONTH, duration - 1);
    }

    public String getName() {
        return name;
    }

    public GregorianCalendar getStart() {
        return start;
    }

    public void setEarlyStart(GregorianCalendar start) {
        this.start = (GregorianCalendar) start.clone();
        this.start.add(Calendar.DAY_OF_MONTH, 1);
        this.end = (GregorianCalendar) this.start.clone();
        this.end.add(Calendar.DAY_OF_MONTH, this.duration - 1);
    }

    public void setLateFinish(GregorianCalendar finish) {
        this.end = (GregorianCalendar) finish.clone();
        this.end.add(Calendar.DAY_OF_MONTH, -1);
        this.start = (GregorianCalendar) this.end.clone();
        this.start.add(Calendar.DAY_OF_MONTH, -(this.duration - 1));
    }

    public GregorianCalendar getEnd() {
        return end;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Task{"
                + "name='" + name + '\''
                + ", duration=" + duration + '\''
                + ", start='" + formatDate(this.start) + '\''
                + ", end=" + formatDate(this.end)
                + '}';
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    private String formatDate(GregorianCalendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());
        return dateFormatted;
    }

    public void addParent(Task t) {
        parents.add(t);
    }

    public List<Task> getParents() {
        return parents;
    }
}
