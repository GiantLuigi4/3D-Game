package API.utils;

import java.util.ArrayList;

public class StringyHashMap<T,H> {
	public ArrayList<T> keys=new ArrayList<>();
	public ArrayList<H> objects=new ArrayList<>();
	
	public StringyHashMap() {
	}
	
	public boolean containsKey(T key) {
		boolean check1=keys.contains(key);
		if (!check1) {
			for (T key1:keys) {
				if (key1.toString().equals(key.toString())) {
					return true;
				}
			}
		}
		return check1;
	}
	public H get(T key) {
		int i=0;
		for (T key1:keys) {
			if (key1.toString().equals(key.toString())) {
				return objects.get(i);
			}
			i++;
		}
		return null;
	}
	public void remove(T key) {
		int i=0;
		try {
			for (T key1:keys) {
				if (key1.toString().equals(key.toString())) {
					objects.remove(i);
					keys.remove(i);
					return;
				}
				i++;
			}
		} catch (Exception err) {}
	}
	public void add(T key,H object) {
		keys.add(key);
		objects.add(object);
	}
	public void addOrReplace(T key,H object) {
		if (keys.contains(key)) this.remove(key);
		keys.add(key);
		objects.add(object);
	}
}
