package interfaces;

import java.util.Map;

public interface CustomerEmployee {
	public int getId();
	public String getName();
	public void setName(String name);
	public Map<String, String> toMap();
}
