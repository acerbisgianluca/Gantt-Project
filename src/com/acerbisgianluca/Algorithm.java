package com.acerbisgianluca;

import com.acerbisgianluca.exceptions.TaskNotFoundException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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

    public Algorithm() {
        tasksESEF = new ArrayList<>();
        tasksLSLF = new ArrayList<>();
    }

    /**
     *
     * @param t
     * @param esef
     * @return
     */
    public boolean addTask(Task t, boolean esef){
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
        return maxDur;
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
                t.setEarlyStart(tDep.getEnd());
                /*if (t.getStart().getTime().compareTo(tDep.getEnd().getTime()) != 0) {
                    t.setEarlyStart(tDep.getEnd());
                }*/
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
            if(t == parent)
                return t.getDuration();
            
            GregorianCalendar best = parent.getStart();
            for (Task t1 : t.getParents()) {
                if (t1.getStart().before(best)) {
                    best = t1.getStart();
                }
            }
            if (!t.getEnd().after(best)) {
                t.setLateFinish(best);
            }

            return t.getDuration();
        }

        int maxDuration = 0, dur;
        for (Task tDep : t.getDependencies()) {
            if ((dur = totalDurationLate(tDep, t)) > maxDuration) {
                t.setLateFinish(parent.getStart());
                /*if (t.getEnd().getTime().before(parent.getStart().getTime())) {
                    t.setLateFinish(parent.getStart());
                }*/
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
    
    public void resetLists(){
        tasksESEF.clear();
        tasksLSLF.clear();
    }

    public void resetForRunning() {
        for(int i = 0; i < tasksESEF.size(); i++){
            tasksESEF.get(i).resetToDefault();
            tasksLSLF.get(i).resetToDefault();
        }
    }
}
