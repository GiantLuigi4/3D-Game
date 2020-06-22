package API.model;

import API.model.elements.ModelElement;

import java.util.ArrayList;
import java.util.Arrays;

public class Model {
	private final ArrayList<ModelElement> elements=new ArrayList<>();
	
	public ArrayList<ModelElement> getAllElements() {
		return elements;
	}
	
	public Model(ModelElement... elements) {
		this.addElements(elements);
	}
	public Model(ModelElement element) {
		this.elements.add(element);
	}
	public Model(ArrayList<? extends ModelElement> elements) {
		this.addElements(elements);
	}
	public Model(Model child,ModelElement... elements) {
		this(elements);
		this.elements.addAll(child.getAllElements());
	}
	public Model(ModelElement element,Model... children) {
		this(children);
		this.elements.add(element);
	}
	public Model(Model... children) {
		this.addChildren(children);
	}
	
	public void addChildren(Model... children) {
		ArrayList<ModelElement> elements=new ArrayList<>();
		Arrays.asList(children).forEach((c)->elements.addAll(c.getAllElements()));
		this.elements.addAll(elements);
	}
	
	public void addElements(ModelElement... elements) {
		this.elements.addAll(Arrays.asList(elements));
	}
	public void addElements(ArrayList<? extends ModelElement> elements) {
		this.elements.addAll(elements);
	}
}
