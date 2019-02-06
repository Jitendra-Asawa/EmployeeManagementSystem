package data;

import java.util.LinkedHashMap;
import java.util.Map;

import interfaces.CustomerEmployee;

public class Customer implements CustomerEmployee {
	private int id;
	private String name;
	private int amount;

	public Customer() {
	}

	public Customer(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
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

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("id", Integer.toString(id));
		map.put("name", name);
		map.put("amount", Integer.toString(amount));
		return map;
	}

	@Override
	public String toString() {
		return "Customer ( Id=" + id + ", Name=" + name + ", Amount=" + amount + ")";
	}

}
