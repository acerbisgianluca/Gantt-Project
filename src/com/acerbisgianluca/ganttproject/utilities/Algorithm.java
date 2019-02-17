package com.acerbisgianluca.ganttproject.utilities;

import com.acerbisgianluca.ganttproject.exceptions.TaskNotFoundException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Contiene i metodi che gestiscono entrambi gli algoritmi e le liste di
 * attività.
 *
 * Per ogni tipo di algoritmo viene usata una lista diversa con oggetti clonati
 * e quindi diversi.
 *
 * @author Gianluca
 */
public class Algorithm implements Serializable {

    /**
     * La lista di attività su cui lavora l'algoritmo.
     * Finish.
     */
    private final List<Task> tasks;
    /**
     * La lista di tutte le date iniziali.
     */
    private final List<LocalDate> startDates;
    /**
     * La lista di tutte le date finali.
     */
    private final List<LocalDate> endDates;
    /**
     * La durata stimata del percorso critico.
     */
    private double totalEt;
    /**
     * La deviazione standard del percorso critico.
     */
    private double totalSd;
    /**
     * La lista dei giorni festivi selezionati.
     */
    private List<DayOfWeek> publicDays;
    
    /**
     * Crea un Algorithm ed inizializza tutte le liste.
     */
    public Algorithm() {
        this.tasks = new ArrayList<>();
        this.startDates = new ArrayList<>();
        this.endDates = new ArrayList<>();
        this.publicDays = new ArrayList<>();
    }

    /**
     * Aggiunge un {@link com.acerbisgianluca.ganttproject.utilities.Task} ad
     * una delle 2 liste in base al secondo paramentro.
     *
     * @param t Il {@link com.acerbisgianluca.ganttproject.utilities.Task} da
     * aggiungere.
     * @return Vero se l'operazione è andata a buon fine, altrimenti falso.
     */
    public boolean addTask(Task t) {
        return this.tasks.add(t);
    }

    /**
     * Esegue entrambi gli algoritmi ES/EF e LS/LF, calcola la durata totale
     * facendo la differenza fra data finale massima e data iniziale minima e
     * sistema alcune attività per farle cominciare il più tardi possibile
     * (LS/LF).
     *
     * @return La durata totale del ciclo di attività.
     */
    public int run() {
        int maxDur = 0, dur;
        // Late Start Late Finish
        for (Task t : this.tasks) {
            if ((dur = totalDurationLate(t, t)) > maxDur) {
                maxDur = dur;
            }
            /*else {
                for (Task t1 : tasks) {
                    if ((dur = totalDuration(t1)) > maxDur) {
                        maxDur = dur;
                    }
                }
            }*/
        }
        maxDur = 0;
        // Early Start Early Finish
        for (Task t : this.tasks) {
            if ((dur = totalDuration(t)) > maxDur) {
                maxDur = dur;
            }
        }

        // Raccolgo tutte le date
        this.startDates.clear();
        this.endDates.clear();
        tasks.stream().map((t) -> {
            this.startDates.add(t.getEarlyStart());
            return t;
        }).forEachOrdered((t) -> {
            this.endDates.add(t.getEarlyEnd());
        });

        LocalDate lastDate = Collections.max(this.endDates);

        // Sistemo le attività che potrebbero non essere posizionate in fondo
        this.tasks.forEach((t) -> {
            if (t.getParents().isEmpty() && t.getDependencies().isEmpty()) {
                t.setLateFinish(lastDate.plusDays(1), this.publicDays);
            } else if (t.getParents().isEmpty()) {
                t.setLateFinish(lastDate.plusDays(1), this.publicDays);
                t.getDependencies().forEach((child) -> {
                    lastMove(child, t);
                });
            }
        });

        this.tasks.stream().filter((t) -> (t.getEarlyStart().equals(t.getLateStart()))).forEachOrdered((t) -> {
            t.setCritica(true);
        });
        
        this.totalEt = this.totalSd = 0;
        this.tasks.forEach((Task t) -> {
            if(t.isCritica()){
                this.totalEt += t.getEt();
                this.totalSd += Math.pow(t.getSd(), 2);
            }
        });
        this.totalSd = Math.sqrt(this.totalSd);
        
        // Calcolo la durata effettiva
        long diff = java.sql.Date.valueOf(Collections.max(this.endDates)).getTime() - java.sql.Date.valueOf(Collections.min(this.startDates)).getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
    }

    /**
     * Sistema alcune attività con caratteristiche particolari non modificate dall'algoritmo principale.
     * @param t L'attività iniziale.
     * @param parent L'attività successiva. Se è il primo ciclo sarà uguale a t.
     */
    private void lastMove(Task t, Task parent) {
        if (t.getParents().size() == 1) {
            t.setLateFinish(parent.getLateStart(), this.publicDays);
        } else {
            LocalDate best = parent.getLateStart();
            for (Task t1 : t.getParents()) {
                if (t1.getLateStart().isBefore(best)) {
                    best = t1.getLateStart();
                }
            }
            t.setLateFinish(best, this.publicDays);
        }
        t.getDependencies().forEach((child) -> {
            lastMove(child, t);
        });
    }

    /**
     * E' il metodo ricorsivo che viene utilizzato per l'algoritmo ES/EF.
     *
     * @param t L'attività analizzata.
     * @return La durata dell'attività se non ha precedenze oppure la durata
     * dell'attività più tutte le precedenti.
     */
    private int totalDuration(Task t) {
        if (t.getDependencies().isEmpty()) {
            return t.getDuration();
        }

        int maxDuration = 0, dur;
        for (Task tDep : t.getDependencies()) {
            if ((dur = totalDuration(tDep)) > maxDuration) {
                if (t.getEarlyStart().compareTo(tDep.getEarlyEnd()) <= 0) {
                    t.setEarlyStart(tDep.getEarlyEnd(), this.publicDays);
                }
                maxDuration = dur;
            }
        }

        return maxDuration + t.getDuration();
    }

    /**
     * E' il metodo ricorsivo che viene utilizzato per l'algoritmo LS/LF.
     *
     * @param t L'attività analizzata.
     * @param parent L'attività successiva e che ha invocato precedente il
     * metodo.
     * @return La durata dell'attività se non ha precedenze oppure la durata
     * dell'attività più tutte le precedenti.
     */
    private int totalDurationLate(Task t, Task parent) {
        if (t.getDependencies().isEmpty()) {
            if (t == parent) {
                return t.getDuration();
            }

            LocalDate best = parent.getLateStart();
            Task tBest = parent;
            for (Task t1 : t.getParents()) {
                if (t1.getLateStart().isBefore(best)) {
                    best = t1.getLateStart();
                    tBest = t1;
                }
            }
            if (t.getLateEnd().compareTo(best) < 0) {
                t.setLateFinish(best, this.publicDays);
            } else {
                tBest.setLateFinish(t.getLateEnd().plusDays(tBest.getDuration()), this.publicDays);
            }

            return t.getDuration();
        }

        int maxDuration = 0, dur;
        for (Task tDep : t.getDependencies()) {
            if ((dur = totalDurationLate(tDep, t)) > maxDuration) {
                if (t != parent && t.getLateEnd().compareTo(parent.getLateStart()) <= 0) {
                    t.setLateFinish(parent.getLateStart(), this.publicDays);
                }
                maxDuration = dur;
            }
        }

        return maxDuration + t.getDuration();
    }

    /**
     * Cerca un {@link com.acerbisgianluca.ganttproject.utilities.Task} in una
     * delle 2 liste in base al secondo paramentro.
     *
     * @param name Il nome del
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} da cercare.
     * @return Il {@link com.acerbisgianluca.ganttproject.utilities.Task}
     * trovato.
     * @throws TaskNotFoundException Viene lanciata se non viene trovato alcun
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} con il nome dato.
     */
    public Task getTaskByName(String name) throws TaskNotFoundException {
        for (Task t : this.tasks) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        throw new TaskNotFoundException("Attività non trovata.");
    }

    /**
     * Recupera la lista usata per l'algoritmo ES/EF.
     *
     * @return La lista ES/EF.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Svuota le 2 liste.
     */
    public void resetLists() {
        tasks.clear();
    }

    /**
     * Ripristina la data di default per ogni
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} in entrambe le
     * liste.
     */
    public void resetForRunning() {
        this.tasks.forEach((t) -> {
            t.resetToDefault(this.publicDays);
        });
    }

    /**
     * Rimuove da entrambe le liste le 2 attività passate come argomento, una
     * per ogni lista.
     *
     * @param t L'attività da rimuovere.
     */
    public void removeFromLists(Task t) {
        tasks.remove(t);
    }
    
    /**
     * Ottiene la durata stimata del percorso critico.
     * @return La durata stimata del percorso critico.
     */
    public double getTotalEt() {
        return totalEt;
    }

    /**
     * Ottiene la deviazione standard del percorso critico.
     * @return La deviazione standard del percorso critico.
     */
    public double getTotalSd() {
        return totalSd;
    }

    /**
     * Imposta la lista dei giorni festivi.
     * @param list I giorni festivi selezionati nella maschera.
     */
    public void setPublicDays(List<DayOfWeek> list) {
        this.publicDays = list;
    }

    /**
     * Ottiene la lista dei giorni festivi.
     * @return I giorni festivi.
     */
    public List<DayOfWeek> getPublicDays() {
        return publicDays;
    }
}
