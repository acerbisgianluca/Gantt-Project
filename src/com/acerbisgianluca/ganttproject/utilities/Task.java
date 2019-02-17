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
     * La durata stimata.
     */
    private double et;
    /**
     * La deviazione standard.
     */
    private double sd;
    /**
     * La durata ottimistica.
     */
    private int optimisticDuration;
    /**
     * La durata probabile.
     */
    private int probableDuration;
    /**
     * La durata pessimistica.
     */
    private int pessimisticDuration;

    /**
     * Crea un nuovo Task (attività).
     *
     * @param name Il suo nome.
     * @param start La sua data di inizio.
     * @param duration La sua durata.
     * @param list La lista dei giorni festivi.
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
        this.optimisticDuration = 1;
        this.probableDuration = 1;
        this.pessimisticDuration = 1;
        this.et = duration;
        this.sd = 0;
    }

    /**
     * Crea un nuovo Task (attività).
     * @param name Il nome dell'attività.
     * @param start La data iniziale dell'attività.
     * @param a La durata ottimistica dell'attività.
     * @param m La durata probabile dell'attività.
     * @param b La durata pessimistica dell'attività.
     * @param list La lista dei giorni festivi.
     */
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
        this.optimisticDuration = a;
        this.probableDuration = m;
        this.pessimisticDuration = b;
        this.et = (this.optimisticDuration + (4 * this.probableDuration) + this.pessimisticDuration) / 6.0;
        this.sd = (this.pessimisticDuration - this.optimisticDuration) / 6.0;
        this.duration = (int) this.et;
    }

    /**
     * Clona la data di inizio di un'altra attività, ci aggiunge un giorno
 perchè inizia il giorno successivo ed imposta la data di fine optimisticDuration data di
 inizio + (durata - 1).
     *
     * @param start La data di inizio di un'altra attività.
     * @param publicHolidays La lista dei giorni festivi.
     */
    public void setEarlyStart(LocalDate start, List<DayOfWeek> publicHolidays) {
        this.earlyStart = add(start, 1, publicHolidays);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
    }

    /**
     * Clona la data di fine di un'altra attività, toglie un giorno perchè
 finisce il giorno precedente ed imposta la data di inizio optimisticDuration data di fine
 - (durata - 1).
     *
     * @param finish La data di fine di un'altra attività.
     * @param publicHolidays La lista dei giorni festivi.
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
     * Aggiorna i dati di un Task in base optimisticDuration quelli passati nel form.
     *
     * @param name Il nome nuovo.
     * @param date La nuova data di inizio.
     * @param duration La nuova durata.
     * @param publicHolidays La lista dei giorni festivi.
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
     * Aggiorna i dati di un Task in base optimisticDuration quelli passati nel form.
     *
     * @param name Il nome nuovo.
     * @param date La nuova data di inizio.
     * @param a La durata ottimistica.
     * @param m La durata probabile.
     * @param b La durata pessimistica.
     * @param publicHolidays La lista dei giorni festivi.
     */
    public void update(String name, LocalDate date, int a, int m, int b, List<DayOfWeek> publicHolidays) {
        this.name = name;
        this.earlyStart = add(date, 0, publicHolidays);
        this.lateStart = add(date, 0, publicHolidays);
        this.defaultDate = date.plusDays(0);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.lateEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.optimisticDuration = a;
        this.probableDuration = m;
        this.pessimisticDuration = b;
        this.et = (this.optimisticDuration + (4 * this.probableDuration) + this.pessimisticDuration) / 6.0;
        this.sd = (this.pessimisticDuration - this.optimisticDuration) / 6.0;
        this.duration = (int) this.et;
    }

    /**
     * Ripristina la data di inizio optimisticDuration quella di default.
     *
     * @param publicHolidays La lista dei giorni festivi.
     */
    public void resetToDefault(List<DayOfWeek> publicHolidays) {
        this.earlyStart = add(this.defaultDate, 0, publicHolidays);
        this.lateStart = add(this.defaultDate, 0, publicHolidays);
        this.earlyEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.lateEnd = add(this.earlyStart, duration - 1, publicHolidays);
        this.critica = false;
    }

    /**
     * Aggiunge/sottrae alla data fornita un certo numero di giorni in base ai giorni festivi.
     * @param start La data di inizio.
     * @param businessDay Il numero di giorni lavorativi.
     * @param list La lista dei giorni festivi.
     * @return La nuova data ottenuta usando solo i giorni lavorativi.
     */
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

    /**
     * Ottiene il nome dell'attività.
     * @return Il nome dell'attività.
     */
    public String getName() {
        return name;
    }

    /**
     * Ottiene la durata dell'attività.
     * @return La durata dell'attività.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Ottiene le attività successive dell'attività.
     * @return Le attività successive dell'attività.
     */
    public List<Task> getParents() {
        return parents;
    }

    /**
     * Ottiene le attività precedenti dell'attività.
     * @return Le attività precedenti dell'attività.
     */
    public List<Task> getDependencies() {
        return dependencies;
    }

    /**
     * Ottiene la data impostata inizialmente.
     * @return La data impostata inizialmente.
     */
    public LocalDate getDefaultDate() {
        return defaultDate;
    }

    /**
     * Ottiene la data iniziale dell'algoritmo Early.
     * @return La data iniziale dell'algoritmo Early.
     */
    public LocalDate getEarlyStart() {
        return earlyStart;
    }
    
    /**
     * Ottiene la data finale dell'algoritmo Early.
     * @return La data finale dell'algoritmo Early.
     */
    public LocalDate getEarlyEnd() {
        return earlyEnd;
    }

    /**
     * Ottiene la data iniziale dell'algoritmo Late.
     * @return La data iniziale dell'algoritmo Late.
     */
    public LocalDate getLateStart() {
        return lateStart;
    }

    /**
     * Ottiene la data finale dell'algoritmo Late.
     * @return La data finale dell'algoritmo Late.
     */
    public LocalDate getLateEnd() {
        return lateEnd;
    }

    /**
     * Ottiene se l'attività è critica o meno.
     * @return Criticità dell'attività.
     */
    public boolean isCritica() {
        return critica;
    }

    /**
     * Imposta il valore di criticità fornito.
     * @param critica Indica se è critica o meno.
     */
    public void setCritica(boolean critica) {
        this.critica = critica;
    }

    /**
     * Ottiene la durata stimata.
     * @return La durata stimata.
     */
    public double getEt() {
        return et;
    }

    /**
     * Ottiene la deviazione standard.
     * @return La deviazione standard.
     */
    public double getSd() {
        return sd;
    }

    /**
     * Ottiene la durata ottimistica.
     * @return La durata ottimistica.
     */
    public int getOptimisticDuration() {
        return optimisticDuration;
    }

    /**
     * Ottiene la durata probabile.
     * @return La durata probabile.
     */
    public int getProbableDuration() {
        return probableDuration;
    }

    /**
     * Ottiene la durata pessimistica.
     * @return La durata pessimistica.
     */
    public int getPessimisticDuration() {
        return pessimisticDuration;
    }
}
