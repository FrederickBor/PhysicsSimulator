package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {
	
	/**
	 * Este método será llamado para crear las instancias de la clase correspondiente - una instancia subtipo de T.
	 * La estructura JSON que se pasa como parámetro al método createInstance incluye dos claves: type, que describe el tipo de objeto que se va a crear y; data, que es una estructura JSON que incluye toda la información necesaria para crear el objeto. 
	 * 
	 * @param info Estructura JSON que describe el objeto a crear.
	 * @return Una instancia de la clase correspondiente - una instancia de un subtipo de T.
	 * @throws IllegalArgumentException En caso de que info sea incorrecto.
	 */
	public T createInstance(JSONObject info) throws IllegalArgumentException;

	/**
	 * Devuelve una lista de objetos JSON, que son “plantillas” para structuras JSON válidas. Los objetos de esta lista se pueden pasar como parámetro al método createInstance. Esto es muy útil para saber cuáles son los valores válidos para una factoría concreta, sin saber mucho sobre la factoría en si misma. Por ejemplo, utilizaremos este método cuando mostremos al usuario los posibles valores de las leyes de la gravedad.
	 * 
	 * @return Una lista de objetos JSON, que son “plantillas” para structuras JSON válidas.
	 */
	public List<JSONObject> getInfo();
}