package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	// A�ade atributos para todos los componentes (clases)
	Controller _ctrl;
	public static final int width = 1000;
	public static final int height = 820;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		this.setSize(width, height);
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
			
		// Completa el m�todo para construir la GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ControlPanel cPanel = new ControlPanel(_ctrl);
		BodiesTable bPanel = new BodiesTable(_ctrl);
		Viewer vPanel = new Viewer(_ctrl);
		StatusBar statusBar = new StatusBar(_ctrl);
		
		JPanel centerPanel = new JPanel();
		
		//SEG�N LA PRACTICA DEBE SER BoxLayout()
		//PERO AL UTILIZARLO NO ME FUNCIONA BIEN
		centerPanel.setLayout(new BorderLayout());
		
		
		Dimension d1 = new Dimension(width,55);
		Dimension d2 = new Dimension(width,300);
		Dimension d3 = new Dimension(width,400);
		
		cPanel.setPreferredSize(d1);
		bPanel.setPreferredSize(d2);
		vPanel.setPreferredSize(d3);
		
		centerPanel.add(bPanel,BorderLayout.NORTH);
		centerPanel.add(vPanel,BorderLayout.SOUTH);
		
		mainPanel.add(cPanel,BorderLayout.PAGE_START);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(statusBar,BorderLayout.PAGE_END);
		
				
	}
}
