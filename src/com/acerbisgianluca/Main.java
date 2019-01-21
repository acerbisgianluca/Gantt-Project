package com.acerbisgianluca;

import java.util.*;

public class Main {

	private static List<Task> tasks;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		tasks = new ArrayList<>();

		int mode = 1;
		try {
			// Modalità
			System.out.print("Modalità: 1 (default) - Early Start/Early Finish\t2- Late Start/Late Finish (digita 1 o 2): ");
			mode = scan.nextInt();

			// Numero di attività
			System.out.print("Quante attività ci sono (numero intero)? ");
			int nTasks = scan.nextInt();
			scan.nextLine();

			int i, duration;
			String[] dependencies, date;
			String name;
			for (i = 0; i < nTasks; i++) {
				// Nome attività
				System.out.print("Nome della attività " + (i + 1) + ": ");
				name = scan.nextLine();

				// Data di inizio dell'attività
				System.out.print("- Data di inizio della attività " + (i + 1) + " nel seguente formato gg/mm/aaaa (premi invio per la data di oggi): ");
				String dateS = scan.nextLine();
				date = dateS.trim().replaceAll(" ", "").split("/");
				GregorianCalendar gc = dateS.equals("") ? new GregorianCalendar() : new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));

				// Durata dell'attività
				System.out.print("- Durata della attività " + (i + 1) + " (in giorni): ");
				duration = scan.nextInt();
				scan.nextLine();

				// Lista di precedenze dell'attività
				System.out.print("- Dipendenze della attività " + (i + 1) + " separate dalla virgola: ");
				dependencies = scan.nextLine().trim().replaceAll(" ", "").split(",");

				tasks.add(new Task(name, gc, duration, Arrays.asList(dependencies)));
			}
		} catch (InputMismatchException ex) {
			System.out.println("Inserisci un intero!");
			System.exit(0);
		}

		fillParent();

		int maxDur = 0, dur;
		try {
			// Mode 1 è quella di default e viene chiamata anche se viene inserito un numero diverso sia da 1 che da 2!
			if (mode == 2)
				for (Task t2 : tasks) {
					if ((dur = totalDurationLate(t2, t2)) > maxDur)
						maxDur = dur;
					else
						for (Task t1 : tasks)
							if ((dur = totalDuration(t1)) > maxDur)
								maxDur = dur;
				}
			else
				for (Task t1 : tasks)
					if ((dur = totalDuration(t1)) > maxDur)
						maxDur = dur;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		System.out.println("I giorni minimi necessari per completare tutte le attività sono " + maxDur);

		new Tabella().showResult(tasks);
	}

	private static int totalDuration(Task t) throws Exception {
		if (t.getDependencies().get(0).equalsIgnoreCase("")) {
			return t.getDuration();
		}

		Task tDep;
		int maxDuration = 0, dur;
		for (String taskName : t.getDependencies()) {
			tDep = Main.getTaskByName(taskName);
			if ((dur = totalDuration(tDep)) > maxDuration) {
				if (!t.getStart().getTime().after(tDep.getEnd().getTime())) {
					t.setEarlyStart(tDep.getEnd());
				}
				maxDuration = dur;
			}
		}

		return maxDuration + t.getDuration();
	}

	private static int totalDurationLate(Task t, Task parent) throws Exception {
		if (t.getDependencies().get(0).equalsIgnoreCase("")) {

			GregorianCalendar best = parent.getStart();
			for (Task t1 : t.getParents()) {
				if (t1.getStart().before(best))
					best = t1.getStart();
			}
			if (!t.getEnd().after(best))
				t.setLateFinish(best);


			return t.getDuration();
		}

		Task tDep;
		int maxDuration = 0, dur;
		for (String taskName : t.getDependencies()) {
			tDep = Main.getTaskByName(taskName);
			if ((dur = totalDurationLate(tDep, t)) > maxDuration) {
				if (t.getEnd().getTime().before(parent.getStart().getTime())) {
					t.setLateFinish(parent.getStart());
				}
				maxDuration = dur;
			}
		}

		return maxDuration + t.getDuration();
	}

	private static Task getTaskByName(String name) throws Exception {
		for (Task t : tasks) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}

		throw new Exception("Attività non trovata!");
	}

	private static void fillParent() {
		for (Task t : tasks)
			for (Task t1 : tasks)
				if (t1.getDependencies().contains(t.getName()))
					t.addParent(t1);
	}
}
