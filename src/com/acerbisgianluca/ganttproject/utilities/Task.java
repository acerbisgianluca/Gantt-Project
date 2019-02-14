package com.acerbisgianluca.ganttproject.utilities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un'attavità e contiene gli attributi che la identificano.
 *
 * @author Gianluca
 */
public class Task implements Serializable {

    /**
     * Il nome dell'attività
     */
    private String name;
    /**
     * La durata dell'attività.
     */
    private int duration;
    /**
     * La lista delle attività successive.
     */
    private final List<Task> parents;
    /**
     * La lista delle attività da cui dipende l'attività stessa.
     */
    private final List<Task> dependencies;
    /**
     * La data di inizio di default.
     */
    private LocalDate defaultDate;
    /**
     * La data di inizio.
     */
    private LocalDate earlyStart;
    /**
     * La data di fine.
     */
    private LocalDate earlyEnd;
    /**
     * La data di inizio.
     */
    private LocalDate lateStart;
    /**
     * La data di fine.
     */
    private LocalDate lateEnd;
    /**
     * Indica se l'attività è critica o meno. In caso positivo non è possibile
     * spostarla.
     */
    private boolean critica;
    /**
     *
     */
    private double et;
    /**
     *
     */
    private double sd;
    /**
     *
     */
    private int a;
    private int m;
    private int b;

    /**
     * Crea un nuovo Task (attività).
     *
     * @param name Il suo nome.
     * @param start La sua data di inizio.
     * @param duration La sua durata.
     * @param list
     */
    public Task(String name, LocalDate start, int duration, List<DayOfWeek> list) {
        this.name = name;
        this.earlyStart = add(start, 0, list);
        this.lateStart = add(start, 0, list);
        this.defaultDate = start.plusDays(0);
        this.duration = duration;
        this.parents = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.earlyEnd = add(start, duration - 1, list);
        this.lateEnd = add(start, duration - 1, list);
        this.critica = false;
        this.a = 1;
        this.m = 1;
        this.b = 1;
        this.et = duration;
        this.sd = 0;
    }

    public Task(String name, LocalDate start, int a, int m, int b, List<DayOfWeek> list) {
        this.name = name;
        this.earlyStart = add(start, 0, list);
        this.lateStart = add(start, 0, list);
        this.defaultDate = start.plusDays(0);
        this.parents = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.earlyEnd = add(start, duration - 1, list);
        this.lateEnd = add(start, duration - 1, list);
        this.critica = false;
        this.a = a;
        this.m = m;
        this.b = b;
        this.et = (this.a + (4 * this.m) + this.b) / 6.0;
        this.sd = (this.b - this.a) / 6.0;
        this.duration = (int) this.et;
    }

    /**
     * Clona la data di inizio di un'altra attività, ci aggiunge un giorno
     * perchè inizia il giorno successivo ed imposta la data di fine a data di
     * inizio + (durata - 1).
     *
     * @param start La data di inizio di un'altra attività.
     * @param publicHolidays
     */
    public void setEarlyStart(LocalDate start, List<DayOfWeek> publicHolidays) {
        this.earlyStart = add(start, 1, publicHolidays);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
    }

    /**
     * Clona la data di fine di un'altra attività, toglie un giorno perchè
     * finisce il giorno precedente ed imposta la data di inizio a data di fine
     * - (durata - 1).
     *
     * @param finish La data di fine di un'altra attività.
     * @param publicHolidays
     */
    public void setLateFinish(LocalDate finish, List<DayOfWeek> publicHolidays) {
        this.lateEnd = add(finish, -1, publicHolidays);
        this.lateStart = add(this.lateEnd, -(duration - 1), publicHolidays);
    }

    /**
     * Aggiunge una nuova attività alla lista.
     *
     * @param t L'attività da aggiungere.
     */
    public void addDependency(Task t) {
        dependencies.add(t);
    }

    /**
     * Aggiunge un'attività alla lista di quelle successive.
     *
     * @param t L'attività da aggiungere.
     */
    public void addParent(Task t) {
        parents.add(t);
    }

    /**
     * Rimuove la dipendenza data.
     *
     * @param t Il Task da rimuovere.
     */
    public void removeDependency(Task t) {
        dependencies.remove(t);
    }

    /**
     * Rimuove il successivo dato.
     *
     * @param t Il Task da rimuovere.
     */
    public void removeParent(Task t) {
        parents.remove(t);
    }

    /**
     * Aggiorna i dati di un Task in base a quelli passati nel form.
     *
     * @param name Il nome nuovo.
     * @param date La nuova data di inizio.
     * @param duration La nuova durata.
     * @param publicHolidays
     */
    public void update(String name, LocalDate date, int duration, List<DayOfWeek> publicHolidays) {
        this.name = name;
        this.earlyStart = add(date, 0, publicHolidays);
        this.lateStart = add(date, 0, publicHolidays);
        this.defaultDate = date.plusDays(0);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.lateEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.et = duration;
        this.sd = 0;
        this.duration = duration;
    }

    /**
     * Aggiorna i dati di un Task in base a quelli passati nel form.
     *
     * @param name Il nome nuovo.
     * @param date La nuova data di inizio.
     * @param a
     * @param m
     * @param b
     * @param publicHolidays
     */
    public void update(String name, LocalDate date, int a, int m, int b, List<DayOfWeek> publicHolidays) {
        this.name = name;
        this.earlyStart = add(date, 0, publicHolidays);
        this.lateStart = add(date, 0, publicHolidays);
        this.defaultDate = date.plusDays(0);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.lateEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.a = a;
        this.m = m;
        this.b = b;
        this.et = (this.a + (4 * this.m) + this.b) / 6.0;
        this.sd = (this.b - this.a) / 6.0;
        this.duration = (int) this.et;
    }

    /**
     * Ripristina la data di inizio a quella di default.
     *
     * @param publicHolidays
     */
    public void resetToDefault(List<DayOfWeek> publicHolidays) {
        this.earlyStart = add(this.defaultDate, 0, publicHolidays);
        this.lateStart = add(this.defaultDate, 0, publicHolidays);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.lateEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.critica = false;
    }

    private LocalDate add(LocalDate start, int businessDay, List<DayOfWeek> list) {
        if (list.isEmpty()) {
            return start.plusDays(businessDay);
        }

        boolean positive = businessDay >= 0;
        int dur = Math.abs(businessDay);
        int added = 0;
        if (dur == 0) {
            while (added <= dur) {
                if (!list.contains(start.getDayOfWeek())) {
                    ++added;
                    break;
                }

                if (positive) {
                    start = start.plusDays(1);
                } else {
                    start = start.minusDays(1);
                }
            }
        } else {
            while (added < dur) {
                if (positive) {
                    start = start.plusDays(1);
                } else {
                    start = start.minusDays(1);
                }

                if (!list.contains(start.getDayOfWeek())) {
                    ++added;
                }
            }
        }

        return start;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public List<Task> getParents() {
        return parents;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public LocalDate getDefaultDate() {
        return defaultDate;
    }

    public LocalDate getEarlyStart() {
        return earlyStart;
    }

    public LocalDate getEarlyEnd() {
        return earlyEnd;
    }

    public LocalDate getLateStart() {
        return lateStart;
    }

    public LocalDate getLateEnd() {
        return lateEnd;
    }

    public boolean isCritica() {
        return critica;
    }

    public void setCritica(boolean critica) {
        this.critica = critica;
    }

    public double getEt() {
        return et;
    }

    public double getSd() {
        return sd;
    }

    public int getA() {
        return a;
    }

    public int getM() {
        return m;
    }

    public int getB() {
        return b;
    }
}
