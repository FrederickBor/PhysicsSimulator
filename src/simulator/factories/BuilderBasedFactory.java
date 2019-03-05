package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> builderList;
	
	public BuilderBasedFactory(List<Builder<T>> builderList) {
		this.builderList = builderList;
		
	}
	
	@Override
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		for (Builder<T> b : builderList) {
			if (b.getTypeTag().equals(info.get("type"))) {
				return b.createInstance(info);
			}
		}
		
		throw new IllegalArgumentException();
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		
		for (Builder<T> b : builderList) {
			jsonObjects.add(b.getBuilderInfo());
		}
		
		return jsonObjects;
	}

}
