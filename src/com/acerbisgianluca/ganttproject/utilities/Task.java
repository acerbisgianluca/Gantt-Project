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
    private LocalDate start;
    /**
     * La data di fine.
     */
    private LocalDate end;
    /**
     * Indica se l'attività è critica o meno. In caso positivo non è possibile
     * spostarla.
     */
    private boolean critica;
    /**
     *
     */
    private int et;
    /**
     *
     */
    private int sd;
    /**
     *
     */
    private int a;
    private int m;
    private int b;
    private boolean advanced;

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
        this.critica = false;
        this.et = duration;
        this.sd = 0;
        this.advanced = false;
    }

    public Task(String name, LocalDate start, int a, int m, int b) {
        this.name = name;
        this.start = start.plusDays(0);
        this.defaultDate = start.plusDays(0);
        this.parents = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.end = start.plusDays(duration - 1);
        this.critica = false;
        this.a = a;
        this.m = m;
        this.b = b;
        this.et = (this.a + (4 * this.m) + this.b) / 6;
        this.sd = (this.b - this.a) / 6;
        this.duration = this.et;
        this.advanced = true;
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
     */
    public void update(String name, LocalDate date, int duration) {
        this.name = name;
        this.start = date.plusDays(0);
        this.defaultDate = date.plusDays(0);
        this.end = this.start.plusDays(duration - 1);
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
     */
    public void update(String name, LocalDate date, int a, int m, int b) {
        this.name = name;
        this.start = date.plusDays(0);
        this.defaultDate = date.plusDays(0);
        this.end = this.start.plusDays(duration - 1);
        this.a = a;
        this.m = m;
        this.b = b;
        this.et = (this.a + (4 * this.m) + this.b) / 6;
        this.sd = (this.b - this.a) / 6;
        this.duration = this.et;
    }

    /**
     * Ripristina la data di inizio a quella di default.
     */
    public void resetToDefault() {
        this.start = this.defaultDate.plusDays(0);
        this.end = this.start.plusDays(duration - 1);
        this.critica = false;
    }

    /**
     * Ritorna la data di inizio di default.
     *
     * @return La data di inizio di default.
     */
    public LocalDate getDefaultDate() {
        return defaultDate;
    }

    public boolean isCritica() {
        return critica;
    }

    public void setCritica() {
        this.critica = true;
    }

    public int getEt() {
        return et;
    }

    public int getSd() {
        return sd;
    }

    public boolean isAdvanced() {
        return advanced;
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
