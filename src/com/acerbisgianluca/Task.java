package com.acerbisgianluca;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Rappresenta un'attavità e contiene gli attributi che la identificano.
 */
public class Task {

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
    private List<Task> parents;
    /**
     * La lista delle attività da cui dipende l'attività stessa.
     */
    private List<Task> dependencies;
    /**
     * La data di inizio.
     */
    private LocalDate defaultDate;
    /**
     * La data di inizio.
     */
    private LocalDate start;
    /**
     * La data di fine.
     */
    private LocalDate end;

    /**
     * Crea un nuovo Task (attività).
     *
     * @param name Il suo nome.
     * @param start La sua data di inizio.
     * @param duration La sua durata.
     */
    public Task(String name, LocalDate start, int duration) {
        this.name = name;
        this.start = start.plusDays(0);
        this.defaultDate = start.plusDays(0);
        this.duration = duration;
        this.parents = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.end = start.plusDays(duration - 1);
    }

    /**
     * Ritorna il nome dell'attività su cui è chiamato.
     *
     * @return Il nome dell'attività.
     */
    public String getName() {
        return name;
    }

    /**
     * Ritorna la data di inizio dell'attività su cui è chiamato.
     *
     * @return La data di inizio.
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Clona la data di inizio di un'altra attività, ci aggiunge un giorno
     * perchè inizia il giorno successivo ed imposta la data di fine a data di
     * inizio + (durata - 1).
     *
     * @param start La data di inizio di un'altra attività.
     */
    public void setEarlyStart(LocalDate start) {
        this.start = start.plusDays(1);
        this.end = this.start.plusDays(this.duration - 1);
    }

    /**
     * Clona la data di fine di un'altra attività, toglie un giorno perchè
     * finisce il giorno precedente ed imposta la data di inizio a data di fine
     * - (durata - 1).
     *
     * @param finish La data di fine di un'altra attività.
     */
    public void setLateFinish(LocalDate finish) {
        this.end = finish.minusDays(1);
        this.start = this.end.minusDays(this.duration - 1);
    }

    /**
     * Ritorna la data di fine dell'attività su cui è chiamato.
     *
     * @return La data di fine.
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Ritorna la durata dell'attività su cui è chiamato.
     *
     * @return La durata.
     */
    public int getDuration() {
        return duration;
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
     * Ritorna la lista delle dipendenze dell'attività su cui è chiamato.
     *
     * @return La lista della attività che precedono l'attività stessa.
     */
    public List<Task> getDependencies() {
        return dependencies;
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
     * Ritorna la lista delle attività successive all'attività su cui è
     * chiamato.
     *
     * @return La lista delle attività successive.
     */
    public List<Task> getParents() {
        return parents;
    }

    /**
     * Ritorna una stringa con le principali informazioni dell'attività.
     *
     * @return Una stringa con tutte le informazioni.
     */
    @Override
    public String toString() {
        return "Task{"
                + "name='" + this.name + '\''
                + ", duration=" + this.duration + '\''
                + ", start='" + this.start.toString() + '\''
                + ", end=" + this.end.toString()
                + '}';
    }

    public void removeDependency(Task t) {
        dependencies.remove(t);
    }

    public void removeParent(Task t) {
        parents.remove(t);
    }

    public void update(String name, LocalDate date, int duration) {
        this.name = name;
        this.start = date.plusDays(0);
        this.defaultDate = date.plusDays(0);
        this.end = this.start.plusDays(duration - 1);
        this.duration = duration;
    }

    public void resetToDefault() {
        this.start = this.defaultDate.plusDays(0);
        this.end = this.start.plusDays(duration - 1);
    }

    public LocalDate getDefaultDate() {
        return defaultDate;
    }
}
