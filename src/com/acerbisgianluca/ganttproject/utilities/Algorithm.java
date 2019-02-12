package com.acerbisgianluca.ganttproject.utilities;

import com.acerbisgianluca.ganttproject.exceptions.TaskNotFoundException;
import java.io.Serializable;
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
     * La lista di attività su cui lavora l'algoritmo Early Start / Early
     * Finish.
     */
    private final List<Task> tasksESEF;
    /**
     * La lista di attività su cui lavora l'algoritmo Late Start / Late Finish.
     */
    private final List<Task> tasksLSLF;
    /**
     * La lista di tutte le date iniziali.
     */
    private final List<LocalDate> startDates;
    /**
     * La lista di tutte le date finali.
     */
    private final List<LocalDate> endDates;

    private int totalEt;
    private int totalSd;
    
    /**
     * Crea un Algorithm ed inizializza tutte le liste.
     */
    public Algorithm() {
        tasksESEF = new ArrayList<>();
        tasksLSLF = new ArrayList<>();
        startDates = new ArrayList<>();
        endDates = new ArrayList<>();
    }

    /**
     * Aggiunge un {@link com.acerbisgianluca.ganttproject.utilities.Task} ad
     * una delle 2 liste in base al secondo paramentro.
     *
     * @param t Il {@link com.acerbisgianluca.ganttproject.utilities.Task} da
     * aggiungere.
     * @param esef Se è vero il
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} viene aggiunto
     * alla lista ES/EF, altrimenti a LS/LF.
     * @return Vero se l'operazione è andata a buon fine, altrimenti falso.
     */
    public boolean addTask(Task t, boolean esef) {
        return esef ? tasksESEF.add(t) : tasksLSLF.add(t);
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
        for (Task t2 : tasksLSLF) {
            if ((dur = totalDurationLate(t2, t2)) > maxDur) {
                maxDur = dur;
            } else {
                for (Task t1 : tasksLSLF) {
                    if ((dur = totalDuration(t1)) > maxDur) {
                        maxDur = dur;
                    }
                }
            }
        }
        maxDur = 0;
        // Early Start Early Finish
        for (Task t1 : tasksESEF) {
            if ((dur = totalDuration(t1)) > maxDur) {
                maxDur = dur;
            }
        }

        // Raccolgo tutte le date
        this.startDates.clear();
        this.endDates.clear();
        tasksESEF.stream().map((t) -> {
            this.startDates.add(t.getStart());
            return t;
        }).forEachOrdered((t) -> {
            this.endDates.add(t.getEnd());
        });

        LocalDate lastDate = Collections.max(this.endDates);

        // Sistemo le attività che potrebbero non essere posizionate in fondo
        tasksLSLF.forEach((t) -> {
            if (t.getParents().isEmpty() && t.getDependencies().isEmpty()) {
                t.setLateFinish(lastDate.plusDays(1));
            } else if (t.getParents().isEmpty()) {
                t.setLateFinish(lastDate.plusDays(1));
                t.getDependencies().forEach((child) -> {
                    lastMove(child, t);
                });
            }
        });

        for(int i = 0; i < tasksESEF.size(); i++){
            Task t = tasksESEF.get(i);
            Task t1 = tasksLSLF.get(i);
            if(t.getStart().equals(t1.getStart()))
                t1.setCritica();
        }
        
        totalEt = totalSd = 0;
        tasksLSLF.forEach((Task t) -> {
            if(t.isCritica()){
                totalEt += t.getEt();
                totalSd += Math.pow(t.getSd(), 2);
            }
        });
        totalSd = (int) Math.sqrt(totalSd);
        
        // Calcolo la durata effettiva
        long diff = java.sql.Date.valueOf(Collections.max(this.endDates)).getTime() - java.sql.Date.valueOf(Collections.min(this.startDates)).getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
    }

    private void lastMove(Task t, Task parent) {
        if (t.getParents().size() == 1) {
            t.setLateFinish(parent.getStart());
        } else {
            LocalDate best = parent.getStart();
            for (Task t1 : t.getParents()) {
                if (t1.getStart().isBefore(best)) {
                    best = t1.getStart();
                }
            }
            t.setLateFinish(best);
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
                if (t.getStart().compareTo(tDep.getEnd()) <= 0) {
                    t.setEarlyStart(tDep.getEnd());
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

            LocalDate best = parent.getStart();
            Task tBest = parent;
            for (Task t1 : t.getParents()) {
                if (t1.getStart().isBefore(best)) {
                    best = t1.getStart();
                    tBest = t1;
                }
            }
            if (t.getEnd().compareTo(best) < 0) {
                t.setLateFinish(best);
            } else {
                tBest.setEarlyStart(t.getEnd());
            }

            return t.getDuration();
        }

        int maxDuration = 0, dur;
        for (Task tDep : t.getDependencies()) {
            if ((dur = totalDurationLate(tDep, t)) > maxDuration) {
                if (t != parent && t.getEnd().compareTo(parent.getStart()) <= 0) {
                    t.setLateFinish(parent.getStart());
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
     * @param esef Se è vero il
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} viene cercato
     * nella lista ES/EF, altrimenti in LS/LF.
     * @return Il {@link com.acerbisgianluca.ganttproject.utilities.Task}
     * trovato.
     * @throws TaskNotFoundException Viene lanciata se non viene trovato alcun
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} con il nome dato.
     */
    public Task getTaskByName(String name, boolean esef) throws TaskNotFoundException {
        for (Task t : esef ? tasksESEF : tasksLSLF) {
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
    public List<Task> getTasksESEF() {
        return tasksESEF;
    }

    /**
     * Recupera la lista usata per l'algoritmo LS/LF.
     *
     * @return La lista LS/LF.
     */
    public List<Task> getTasksLSLF() {
        return tasksLSLF;
    }

    /**
     * Svuota le 2 liste.
     */
    public void resetLists() {
        tasksESEF.clear();
        tasksLSLF.clear();
    }

    /**
     * Ripristina la data di default per ogni
     * {@link com.acerbisgianluca.ganttproject.utilities.Task} in entrambe le
     * liste.
     */
    public void resetForRunning() {
        for (int i = 0; i < tasksESEF.size(); i++) {
            tasksESEF.get(i).resetToDefault();
            tasksLSLF.get(i).resetToDefault();
        }
    }

    /**
     * Rimuove da entrambe le liste le 2 attività passate come argomento, una
     * per ogni lista.
     *
     * @param tESEF L'attività da rimuovere dalla lista ESEF.
     * @param tLSLF L'attività da rimuovere dalla lista LSLF.
     */
    public void removeFromLists(Task tESEF, Task tLSLF) {
        tasksESEF.remove(tESEF);
        tasksLSLF.remove(tLSLF);
    }
    
    public int getTotalEt() {
        return totalEt;
    }

    public int getTotalSd() {
        return totalSd;
    }
}
