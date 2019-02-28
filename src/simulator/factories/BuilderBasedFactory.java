package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> builderList;
	Builder<T> builder;
	
	public BuilderBasedFactory(List<Builder<T>> builderList) {
		this.builderList = builderList;
	}
	
	@Override
	public T createInstance(JSONObject info) {		
		return builder.createInstance(info);
	}

	@Override
	public List<JSONObject> getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
