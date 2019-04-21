package simulator.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Vector;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private List<Body> _bodies;
	private final String[] columnNames = { "Id", "Mass", "Position", "Velocity", "Acceleration" };
	private final Class[] columnClasses = {
			(new String()).getClass(),
			(new Double(0)).getClass(),
			(new Vector(2)).getClass(),
			(new Vector(2)).getClass(),
			(new Vector(2)).getClass()
	};

	public BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0: //ID
			return _bodies.get(row).getId();
		case 1: // Mass
			return _bodies.get(row).getMass();
		case 2: // Position
			return _bodies.get(row).getPosition();
		case 3: // Velocity
			return _bodies.get(row).getVelocity();
		case 4: // Acceleration
			return _bodies.get(row).getAcceleration();

		default:
			return _bodies.get(row);
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub

	}

}
