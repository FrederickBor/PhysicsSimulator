package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.view.StatusBar;;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	// Añade atributos para todos los componentes (clases)
	Controller _ctrl;
	public static final int WIDTH = 1085;
	public static final int HEIGHT = 820;
	
	ControlPanel cPanel;
	BodiesTable bPanel;
	Viewer vPanel;
	StatusBar statusBar;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
			
		// Completa el método para construir la GUI
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		
		cPanel = new ControlPanel(_ctrl);
		bPanel = new BodiesTable(_ctrl);
		vPanel = new Viewer(_ctrl);
		statusBar = new StatusBar(_ctrl);
		
		JPanel centerPanel = new JPanel();
		
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		cPanel.setPreferredSize(new Dimension(WIDTH,55));
		bPanel.setPreferredSize(new Dimension(WIDTH,300));
		vPanel.setPreferredSize(new Dimension(WIDTH,400));
		
		centerPanel.add(bPanel);
		centerPanel.add(vPanel);
		
		mainPanel.add(cPanel,BorderLayout.PAGE_START);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(statusBar,BorderLayout.PAGE_END);
		
				
	}
}
