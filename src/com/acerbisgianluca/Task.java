package com.acerbisgianluca;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Task {

	private final String name;
	private final int duration;
	private final List<String> dependencies;
	private GregorianCalendar start;
	private GregorianCalendar end;

	public Task(String name, GregorianCalendar start, int duration, List<String> dependencies) {
		this.name = name;
		this.start = start;
		this.duration = duration;
		this.dependencies = dependencies;
		this.end = (GregorianCalendar) start.clone();
		this.end.add(Calendar.DAY_OF_MONTH, duration - 1);
	}

	public String getName() {
		return name;
	}

	public GregorianCalendar getStart() {
		return start;
	}

	public void setEarlyStart(GregorianCalendar start) {
		this.start = (GregorianCalendar) start.clone();
		this.start.add(Calendar.DAY_OF_MONTH, 1);
		this.end = (GregorianCalendar) this.start.clone();
		this.end.add(Calendar.DAY_OF_MONTH, this.duration - 1);
	}

	public void setLateFinish(GregorianCalendar finish) {
		this.end = (GregorianCalendar) finish.clone();
		this.end.add(Calendar.DAY_OF_MONTH, -1);
		this.start = (GregorianCalendar) this.end.clone();
		this.start.add(Calendar.DAY_OF_MONTH, -(this.duration + 1));
	}

	public GregorianCalendar getEnd() {
		return end;
	}

	public int getDuration() {
		return duration;
	}

	@Override
	public String toString() {
		return "Task{" +
				"name='" + name + '\'' +
				", duration=" + duration +
				'}';
	}

	public List<String> getDependencies() {
		return dependencies;
	}
}
