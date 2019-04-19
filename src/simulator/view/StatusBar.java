package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		_currTime = new JLabel("0");
		_numOfBodies = new JLabel("0");
		_currLaws = new JLabel("");
		
		addTime();
		addBodies();
		addGravityLaw();		
		
	}
	
	private void addTime() {
		JLabel timeText = new JLabel("Time: ");
		this.add(timeText);
		this.add(_currTime);
		JSeparator separador = new JSeparator(JSeparator.VERTICAL);
		this.add(separador);
	}
	
	private void addBodies() {
		JLabel bodiesText = new JLabel("Bodies: ");
		this.add(bodiesText);
		this.add(_numOfBodies);
		JSeparator separador = new JSeparator(JSeparator.VERTICAL);
		this.add(separador);
	}
	
	private void addGravityLaw() {
		JLabel gravityText = new JLabel("Laws: ");
		this.add(gravityText);
		this.add(_currLaws);
		JSeparator separador = new JSeparator(JSeparator.VERTICAL);
		this.add(separador);
	}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		_currTime.setText("0");
		_currLaws.setText(gLawsDesc);
		this.repaint();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		int numBodies = bodies.size();
		_numOfBodies.setText(Integer.toString(numBodies));
		this.repaint();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		
		_currTime.setText(Double.toString(time));
		
		this.repaint();
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