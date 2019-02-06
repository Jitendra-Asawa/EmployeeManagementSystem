package data;

//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import interfaces.CustomerEmployee;

public class Employee implements CustomerEmployee {

	private int id;
	private String name;
	private String team;

	public Employee() {
	}

	public Employee(int id) {
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("id", Integer.toString(id));
		map.put("name", name);
		map.put("team", team);
		return map;
	}

	@Override
	public String toString() {
		return "Employee ( Id=" + id + ", Name=" + name + ", Team=" + team + ")";
	}

}
