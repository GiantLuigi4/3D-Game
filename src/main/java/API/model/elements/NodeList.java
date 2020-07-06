package API.model.elements;

import javafx.scene.Node;

import java.util.ArrayList;

public class NodeList extends ModelElement {
	private final Node[] nodes;
	public NodeList(ArrayList<Node> nodes) {
//		this.nodes=new Node[nodes.size()];
//		for (int i=0;i<nodes.size();i++) this.nodes[i]=nodes.get(i);
		if (nodes!=null&&nodes.size()!=0) {
			this.nodes=new Node[nodes.size()];
			for (int i=0;i<nodes.size();i++) this.nodes[i]=nodes.get(i);
		} else
			this.nodes=new Node[]{};
	}
	
	@Override
	public Node[] getNodes() {
		return nodes;
	}
}
