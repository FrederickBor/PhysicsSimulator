package simulator.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class Viewer extends JComponent implements SimulatorObserver {
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	private String _helpText1 = "h: toggle help, +: zoom-in, -: zoom-out, =: fit";
	private String _helpText2 = "Scaling ratio: ";
	private boolean first = true;
	private static final int RADIO = 5;
	private static final int DIAMETRO = RADIO*2;
	
	public Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		//Border with title
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Viewer",
				TitledBorder.LEFT, TitledBorder.TOP));
		
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case '-':
						_scale = _scale * 1.1;
						break;
					case '+':
						_scale = Math.max(1000.0, _scale / 1.1);
						break;
					case '=':
						autoScale();
						break;
					case 'h':
						_showHelp = !_showHelp;
						break;
					default:
				}
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//use 'gr' to draw not 'g'
		//calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		drawCenterCross(gr);
		drawBodies(gr);
		drawHelp(gr);
	}
	
	private void drawCenterCross(Graphics2D gr) {
		gr.setColor(Color.RED);
		// Horizontal line
		gr.drawLine(_centerX-4, _centerY, _centerX+4, _centerY);
		// Vertical line
		gr.drawLine(_centerX, _centerY-4, _centerX, _centerY+4);
	}
	
	private void drawBodies(Graphics2D gr) {
		
		for (Body body : _bodies) {
			double x = body.getPosition().coordinate(0);
			double y = body.getPosition().coordinate(1);
			//NOTA: Le resto el radio para centrarlo todo,
			// porque queda movido al pintar los cuerpos.
			int circleX = _centerX + (int) (x/_scale) - RADIO;
			int circleY = _centerY - (int) (y/_scale) - RADIO;
			
			gr.setColor(Color.BLUE);
			gr.fillOval(circleX, circleY, DIAMETRO, DIAMETRO);
			
			gr.setColor(Color.BLACK);
			gr.drawString(body.getId(), circleX - (int) (body.getId().length()/2), circleY - gr.getFontMetrics().getAscent());
			
		}
		
		if (first) {
			autoScale();
			first = false;
		}
		
		repaint();
	}
	
	private void drawHelp(Graphics2D gr) {
		if (_showHelp) {
			
			gr.setColor(Color.RED);
			
			FontMetrics fontMetrics = gr.getFontMetrics();
			
			int x = fontMetrics.getHeight();
			int y = fontMetrics.getAscent();
			
			gr.drawString(_helpText1, x, y*2);
			gr.drawString(_helpText2 + _scale, x, y*3);
		}
	}
	
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector p = b.getPosition();
			for (int i = 0; i < p.dim(); i++)
				max = Math.max(max,Math.abs(b.getPosition().coordinate(i)));
		}
		double size = Math.max(1.0, Math.min((double) getWidth(),(double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_bodies = bodies;
				autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_bodies = bodies;
				autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_bodies = bodies;
				autoScale();
				repaint();
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				repaint();
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// No hace nada
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// No hace nada
	}

}
