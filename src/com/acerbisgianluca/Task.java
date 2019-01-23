package com.acerbisgianluca;

import java.text.SimpleDateFormat;
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
	private final String name;
	/**
	 * La durata dell'attività.
	 */
	private final int duration;
	/**
	 * La lista dei nomi delle dipendenze.
	 */
	private final List<String> dependenciesStr;
	/**
	 * La lista delle attività successive.
	 */
	private final List<Task> parents;
	/**
	 * La lista delle attività da cui dipende l'attività stessa.
	 */
	private final List<Task> dependencies;
	/**
	 * La data di inizio.
	 */
	private GregorianCalendar start;
	/**
	 * La data di fine.
	 */
	private GregorianCalendar end;


	/**
	 * Crea un nuovo Task (attività).
	 *
	 * @param name         Il suo nome.
	 * @param start        La sua data di inizio.
	 * @param duration     La sua durata.
	 * @param dependencies La lista dei nomi delle sue dipendenze.
	 */
	public Task(String name, GregorianCalendar start, int duration, List<String> dependencies) {
		this.name = name;
		this.start = (GregorianCalendar) start.clone();
		this.duration = duration;
		this.dependenciesStr = dependencies;
		this.parents = new ArrayList<>();
		this.dependencies = new ArrayList<>();
		this.end = (GregorianCalendar) this.start.clone();
		this.end.add(Calendar.DAY_OF_MONTH, duration - 1);
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
	public GregorianCalendar getStart() {
		return start;
	}

	/**
	 * Clona la data di inizio di un'altra attività, ci aggiunge un giorno perchè inizia il giorno successivo ed imposta la data di fine a data di inizio + (durata - 1).
	 *
	 * @param start La data di inizio di un'altra attività.
	 */
	public void setEarlyStart(GregorianCalendar start) {
		this.start = (GregorianCalendar) start.clone();
		this.start.add(Calendar.DAY_OF_MONTH, 1);
		this.end = (GregorianCalendar) this.start.clone();
		this.end.add(Calendar.DAY_OF_MONTH, this.duration - 1);
	}

	/**
	 * Clona la data di fine di un'altra attività, toglie un giorno perchè finisce il giorno precedente ed imposta la data di inizio a data di fine - (durata - 1).
	 *
	 * @param finish La data di fine di un'altra attività.
	 */
	public void setLateFinish(GregorianCalendar finish) {
		this.end = (GregorianCalendar) finish.clone();
		this.end.add(Calendar.DAY_OF_MONTH, -1);
		this.start = (GregorianCalendar) this.end.clone();
		this.start.add(Calendar.DAY_OF_MONTH, -(this.duration - 1));
	}

	/**
	 * Ritorna la data di fine dell'attività su cui è chiamato.
	 *
	 * @return La data di fine.
	 */
	public GregorianCalendar getEnd() {
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
	 * Ritorna la lista dei nomi delle dipendenze dell'attività su cui è chiamato.
	 *
	 * @return La lista dei nomi delle dipendenze.
	 */
	public List<String> getDependenciesStr() {
		return dependenciesStr;
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
	 * Ritorna la lista delle attività successive all'attività su cui è chiamato.
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
				+ "name='" + name + '\''
				+ ", duration=" + duration + '\''
				+ ", start='" + formatDate(this.start) + '\''
				+ ", end=" + formatDate(this.end)
				+ '}';
	}

	/**
	 * Ritorna una stringa in formato dd/MM/yyyy da una data in {@link java.util.GregorianCalendar}.
	 *
	 * @param calendar La data da cui estrarre la stringa.
	 * @return La data in formato leggibile.
	 */
	private String formatDate(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MMM/yyyy");
		fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		return dateFormatted;
	}
}
