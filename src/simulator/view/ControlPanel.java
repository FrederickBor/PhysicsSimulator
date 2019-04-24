package simulator.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.launcher.Main;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private final static Double DT_DEFAULT_VALUE = 2500.0;
	private final static Integer STEPS_DEFAULT_VALUE = 150;

	private Controller _ctrl;
	private boolean _stopped;
	private JPanel pnlPpal;
	private JPanel pnlDer;
	private JToolBar tb;
	private JButton loadFiles;
	private JButton selectGravityLaw;
	private JButton play;
	private JButton stop;
	private JButton exit;
	private JSpinner steps;
	private JTextField deltaTime;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		
		pnlPpal = new JPanel(new GridLayout(1, 2));
		pnlDer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		tb = new JToolBar();
		
		//JBUTTONS
		loadFiles = createLoadButton();
		selectGravityLaw = createGravityLawButton();
		play = createPlayButton();
		stop = createStopButton();
		exit = createExitButton();
		
		//JSPINNER
		steps = createStepsSpinner();
		
		//JTEXTFIELD
		deltaTime = createDeltaTimeField();
		
		// ADDING ALL COMPONENTS TO THE TOOLBAR
		tb.add(loadFiles);
		tb.addSeparator();
		tb.add(selectGravityLaw);
		tb.addSeparator();
		tb.add(play);
		tb.add(stop);
		tb.add(new JLabel("Steps:"));
		tb.add(steps);
		tb.add(new JLabel("Delta-Time:"));
		tb.add(deltaTime);
		tb.addSeparator();
		
		//Ensamblando todo
		pnlDer.add(exit);	

		pnlPpal.add(tb);
		pnlPpal.add(pnlDer);
		
		this.add(pnlPpal);
	}
	
	private JButton createLoadButton(){
		JButton btn = new JButton();
		
		Icon icon = new ImageIcon("resources/icons/open.png");
		btn.setIcon(icon);
		btn.setToolTipText("Load bodies file into the editor");
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setMultiSelectionEnabled(false);
		fc.setFileFilter(new FileNameExtensionFilter("Text files", "json", "txt"));
		
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int ret = fc.showOpenDialog(ControlPanel.this);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					String filePath = fc.getSelectedFile().toString();
					_ctrl.reset();
					try {
						_ctrl.loadBodies(new FileInputStream(filePath));
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(ControlPanel.this, "Something went wrong by loading " + filePath,
								"Load Error", JOptionPane.ERROR_MESSAGE);
					}
				} 
				else if (ret == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(ControlPanel.this, "Cancel was pressed.", "Load file", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				else {
					JOptionPane.showMessageDialog(ControlPanel.this, "An error has occured trying to load the file.", 
							"Load Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		return btn;
	}
	
	private JButton createGravityLawButton() {
		JButton btn = new JButton();
		
		Icon icon = new ImageIcon("resources/icons/physics.png");
		btn.setIcon(icon);
		btn.setToolTipText("Load the gravity law to be used");
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Icon icon = new ImageIcon("");
				
				List<JSONObject> gravityLawsList = _ctrl.getGravityLawsFactory().getInfo();
				List<String> valuesList = new ArrayList<String>();
				
				for (JSONObject obj : gravityLawsList) {
					valuesList.add(obj.getString("desc") + " (" + obj.getString("type") + ")" );
				}
				
				Object[] values = valuesList.toArray();
				
				try {
					Object seleccion = JOptionPane.showInputDialog(
							   ControlPanel.this,
							   "Select gravity laws to be used",
							   "Gravity Laws Selector",
							   JOptionPane.QUESTION_MESSAGE,
							   icon,  
							   values, 
							   values[0]);
					
					String glText = seleccion.toString().split("\\(")[1].split("\\)")[0];
					
					for (Builder<GravityLaws> gl : Main._gravities) {
						if (gl.getTypeTag().equals(glText)) {
							_ctrl.setGravityLaws(gl.getBuilderInfo());
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(ControlPanel.this, "Something happens trying to load the Gravity Law",
							"Load Gravity Law Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		return btn;
	}
	
	private JButton createPlayButton() {
		JButton btn = new JButton();
		
		Icon icon = new ImageIcon("resources/icons/run.png");
		btn.setIcon(icon);
		btn.setToolTipText("Starts the simulation");
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				play.setEnabled(false);
				loadFiles.setEnabled(false);
				selectGravityLaw.setEnabled(false);
				play.setEnabled(false);
				exit.setEnabled(false);
				_stopped = false;
				int steps = 0;
				try {
					double dt =  Double.parseDouble(ControlPanel.this.deltaTime.getText());
					_ctrl.setDeltaTime(dt);
					steps = (Integer) ControlPanel.this.steps.getValue();
					
				} catch (Exception e) {
					_stopped = true;
					JOptionPane.showMessageDialog(ControlPanel.this, e.getMessage(), "Error Loading Steps", JOptionPane.ERROR_MESSAGE);
				}
				
				run_sim(steps);
				
			}
		});
		
		return btn;
	}
	
	private JButton createStopButton() {
		JButton btn = new JButton();
		
		Icon icon = new ImageIcon("resources/icons/stop.png");
		btn.setIcon(icon);
		btn.setToolTipText("Stops the simulation");
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_stopped = true;
			}
		});
		
		return btn;
	}
	
	private JButton createExitButton() {
		JButton btn = new JButton();
		
		Icon icon = new ImageIcon("resources/icons/exit.png");
		btn.setIcon(icon);
		btn.setToolTipText("Close the simulator");
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int optionType = JOptionPane.DEFAULT_OPTION;
				int messageType = JOptionPane.QUESTION_MESSAGE;
				String[] textoBotones = { "Yes", "No"};
				int res = JOptionPane.showOptionDialog(
						ControlPanel.this,
						"Are you sure you want to quit?",
						"Exit Confirm Dialog", optionType, messageType, null,
						textoBotones, textoBotones[0]);
				
				if (res == 0) {
					System.exit(0);
				}
			}
		});
		
		return btn;
	}
	
	private JSpinner createStepsSpinner(){
		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 9999999, 100);
		JSpinner spinner = new JSpinner(model);
			
		spinner.setValue(STEPS_DEFAULT_VALUE);
		
		return spinner;
	}
	
	private JTextField createDeltaTimeField() {
		JTextField txtField = new JTextField(DT_DEFAULT_VALUE.toString());
		
		return txtField;
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				
				int messageType = JOptionPane.ERROR_MESSAGE;
				JOptionPane.showMessageDialog(ControlPanel.this, e.toString(), "Error Message", messageType);
				
				play.setEnabled(true);
				loadFiles.setEnabled(true);
				selectGravityLaw.setEnabled(true);
				play.setEnabled(true);
				exit.setEnabled(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			_stopped = true;
			play.setEnabled(true);
			loadFiles.setEnabled(true);
			selectGravityLaw.setEnabled(true);
			play.setEnabled(true);
			exit.setEnabled(true);
		}
	}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		deltaTime.setText(Double.toString(dt));
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		deltaTime.setText(Double.toString(dt));
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		deltaTime.setText(Double.toString(dt));
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {		
	}
	
}
