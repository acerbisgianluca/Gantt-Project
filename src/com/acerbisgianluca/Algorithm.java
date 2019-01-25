package com.acerbisgianluca;

import com.acerbisgianluca.exceptions.TaskNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Algorithm {

    /**
     * La lista di attività su cui lavora l'algoritmo Early Start / Early
     * Finish.
     */
    private final List<Task> tasksESEF;
    /**
     * La lista di attività su cui lavora l'algoritmo Late Start / Late Finish.
     */
    private final List<Task> tasksLSLF;
    private final List<LocalDate> startDates;
    private final List<LocalDate> endDates;

    public Algorithm() {
        tasksESEF = new ArrayList<>();
        tasksLSLF = new ArrayList<>();
        startDates = new ArrayList<>();
        endDates = new ArrayList<>();
    }

    /**
     *
     * @param t
     * @param esef
     * @return
     */
    public boolean addTask(Task t, boolean esef) {
        return esef ? tasksESEF.add(t) : tasksLSLF.add(t);
    }

    /**
     * Esegue entrambi gli algoritmi ES/EF e LS/LF.
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

        this.startDates.clear();
        this.endDates.clear();
        for (Task t : tasksESEF) {
            this.startDates.add(t.getStart());
            this.endDates.add(t.getEnd());
        }

        LocalDate lastDate = Collections.max(this.endDates);

        for (Task t : tasksLSLF) {
            if (t.getParents().isEmpty() && t.getDependencies().isEmpty()) {
                t.setLateFinish(lastDate.plusDays(1));
            } else if (t.getParents().isEmpty()) {
                t.setLateFinish(lastDate.plusDays(1));
                for (Task child : t.getDependencies()) {
                    lastMove(child, t);
                }
            }
        }

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
        for (Task child : t.getDependencies()) {
            lastMove(child, t);
        }
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

    public Task getTaskByName(String name, boolean esef) throws TaskNotFoundException {
        for (Task t : esef ? tasksESEF : tasksLSLF) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        throw new TaskNotFoundException("Attività non trovata.");
    }

    public List<Task> getTasksESEF() {
        return tasksESEF;
    }

    public List<Task> getTasksLSLF() {
        return tasksLSLF;
    }

    public void resetLists() {
        tasksESEF.clear();
        tasksLSLF.clear();
    }

    public void resetForRunning() {
        for (int i = 0; i < tasksESEF.size(); i++) {
            tasksESEF.get(i).resetToDefault();
            tasksLSLF.get(i).resetToDefault();
        }
    }
}
