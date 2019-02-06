package interfaces;

public interface Management<T extends CustomerEmployee> {
	public void create();

	public void read();

	public void update();

	public void delete();

	public void readAll();
}
