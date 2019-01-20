package com.acerbisgianluca;

import java.util.*;

public class Main {

	private static List<Task> tasks;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		tasks = new ArrayList<>();

		try {
			// Ask for number of tasks
			System.out.print("Quante attività ci sono (numero intero)? ");
			int nTasks = scan.nextInt();
			scan.nextLine();

			int i, duration;
			String[] dependencies, date;
			String name;
			for (i = 0; i < nTasks; i++) {
				System.out.print("Nome della attività " + (i + 1) + ": ");
				name = scan.nextLine();

				System.out.print("- Data di inizio della attività " + (i + 1) + " nel seguente formato gg/mm/aaaa (premi invio per la data di oggi): ");
				String dateS = scan.nextLine();
				date = dateS.trim().replaceAll(" ", "").split("/");
				GregorianCalendar gc = dateS.equals("") ? new GregorianCalendar() : new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));

				System.out.print("- Durata della attività " + (i + 1) + " (in giorni): ");
				duration = scan.nextInt();
				scan.nextLine();

				System.out.print("- Dipendenze della attività " + (i + 1) + " separate dalla virgola: ");
				dependencies = scan.nextLine().trim().replaceAll(" ", "").split(",");

				tasks.add(new Task(name, gc, duration, Arrays.asList(dependencies)));
			}
		} catch (InputMismatchException ex) {
			System.out.println("Inserisci un intero!");
			System.exit(0);
		}

		int maxDur = 0, dur;
		try {
			for (Task t : tasks)
				if ((dur = totalDuration(t)) > maxDur)
					maxDur = dur;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		System.out.println("I giorni minimi necessari per completare tutte le attività sono " + maxDur);

		new Tabella().showResult(tasks);
	}

	private static int totalDuration(Task t) throws Exception {
		if (t.getDependencies().get(0).equalsIgnoreCase(""))
			return t.getDuration();

		Task tDep;
		int maxDuration = 0, dur;
		for (String taskName : t.getDependencies()) {
			tDep = Main.getTaskByName(taskName);
			if ((dur = totalDuration(tDep)) > maxDuration) {
				if (!t.getStart().getTime().after(tDep.getEnd().getTime()))
					t.setEarlyStart(tDep.getEnd());
				maxDuration = dur;
			}
		}

		return maxDuration + t.getDuration();
	}

	/*private static int totalDurationLate(Task t, Task parent){
		if(t.getDependencies().get(0).equalsIgnoreCase("")){
			return t.getDuration();
		}

		Task tDep;
		int maxDuration = 0, dur;
		for(String taskName : t.getDependencies()){
			tDep = Main.getTaskByName(taskName);
			if((dur = totalDurationLate(tDep, t)) > maxDuration) {
				if(t.getEnd().getTime().after(parent.getStart().getTime())){
					t.setLateFinish(parent.getStart());
				}

				maxDuration = dur;
			}
		}

		return maxDuration + t.getDuration();
	}*/

	private static Task getTaskByName(String name) throws Exception {
		for (Task t : tasks)
			if (t.getName().equalsIgnoreCase(name))
				return t;

		throw new Exception("Attività non trovata!");
	}
}
