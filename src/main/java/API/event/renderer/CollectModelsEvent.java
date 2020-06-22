package API.event.renderer;

import API.event.Event;
import API.model.Model;

import java.util.ArrayList;

public abstract class CollectModelsEvent extends Event {
	public final ArrayList<Model> models;
	public CollectModelsEvent(boolean isCancelable, ArrayList<Model> modelsArrayList) {
		super(isCancelable);
		this.models=modelsArrayList;
	}
	
	/**Use this to add your own models**/
	public static class Pre extends CollectModelsEvent {
		public Pre(ArrayList<Model> modelsArrayList) {
			super(false, modelsArrayList);
		}
	}
	
	/**Use this to manipulate other models**/
	public static class Post extends CollectModelsEvent {
		public Post(ArrayList<Model> modelsArrayList) {
			super(false, modelsArrayList);
		}
	}
}
