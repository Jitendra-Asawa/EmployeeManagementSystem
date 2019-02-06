package dao;

import java.util.HashMap;
import interfaces.CustomerEmployee;


public class Operation<T extends CustomerEmployee> {

	private HashMap<Integer, T> details = new HashMap<>();


	public void create(int id, T tempObj) {
		details.put(id, tempObj);

	}

	public T read(int id) {
		// if (details.containsKey(id)) {
		return details.get(id);
		// }
		// return null;
	}

	public void update(int id, T tempObj) {
		if (details.containsKey(id)) {
			details.put(id, tempObj);
		}
	}

	// True means id present in the map
	public boolean checkId(int id) {
		if (details.containsKey(id)) {
			return true;
		}
		return false;
	}

	// removes the indexed employee or customer with given id
	public void remove(int id) {
		details.remove(id);

	}

	public int size() {
		return details.size();
	}

	public HashMap<Integer, T> getMap() {
		return details;
	}

}
