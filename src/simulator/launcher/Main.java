package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

/*
 * Examples of command-line parameters:
 * 
 *  -h
 *  -i resources/examples/ex4.4body.txt -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl ftcg
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl nlug
 *
 */

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.FallingToCenterGravityBuilder;
import simulator.factories.MassLossingBodyBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoGravityBuilder;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	private final static Double DT_DEFAULT_VALUE = 2500.0;
	private final static Integer STEPS_DEFAULT_VALUE = 150;

	// some attributes to stores values corresponding to command-line parameters
	private static Double _dtime = null;
	private static Integer _steps = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _mode = null;
	private static JSONObject _gravityLawsInfo = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<GravityLaws> _gravityLawsFactory;

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void init() {
		// initialize the bodies factory

		List<Builder<Body>> bodies = new ArrayList<Builder<Body>>();
		bodies.add(new BasicBodyBuilder());
		bodies.add(new MassLossingBodyBuilder());

		_bodyFactory = new BuilderBasedFactory<Body>(bodies); // FACTORIA DE BODIES

		// initialize the gravity laws factory

		List<Builder<GravityLaws>> gravities = new ArrayList<Builder<GravityLaws>>();
		gravities.add(new NewtonUniversalGravitationBuilder());
		gravities.add(new FallingToCenterGravityBuilder());
		gravities.add(new NoGravityBuilder());

		_gravityLawsFactory = new BuilderBasedFactory<GravityLaws>(gravities); // FACTORIA DE GL
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseDeltaTimeOption(line);
			parseGravityLawsOption(line);
			parseStepsOption(line);
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		// output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("Output file, where output is written. Default value: the standar output.").build());

		// Steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc(
				"An integer representing the number of simulation steps. Default value: " + STEPS_DEFAULT_VALUE + ".")
				.build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ DT_DEFAULT_VALUE + ".")
				.build());
		
		// mode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
				.desc("Execution Mode. Possible values: ’batch’(Batch mode), ’gui’ (Graphical User Interface mode). "
						+ "Default value: 'batch'.").build());

		// gravity laws -- there is a workaround to make it work even when
		// _gravityLawsFactory is null.
		//
		String gravityLawsValues = "N/A";
		String defaultGravityLawsValue = "N/A";
		if (_gravityLawsFactory != null) {
			gravityLawsValues = "";
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gravityLawsValues.length() > 0) {
					gravityLawsValues = gravityLawsValues + ", ";
				}
				gravityLawsValues = gravityLawsValues + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
			}
			defaultGravityLawsValue = _gravityLawsFactory.getInfo().get(0).getString("type");
		}
		cmdLineOptions.addOption(Option.builder("gl").longOpt("gravity-laws").hasArg()
				.desc("Gravity laws to be used in the simulator. Possible values: " + gravityLawsValues
						+ ". Default value: '" + defaultGravityLawsValue + "'.")
				.build());

		return cmdLineOptions;
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		_mode = line.getOptionValue("m");
		
		if (_mode == null) {
			_mode = "batch";
			// EN REALIDAD POR DEFECTO DEBE SER BATCH MODE A NO SER QUE INDIQUEMOS LO CONTRARIO
			//throw new ParseException("A mode has to be specified");
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode.toUpperCase().equals("BATCH")) {
			throw new ParseException("An input file of bodies is required");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", DT_DEFAULT_VALUE.toString());
		
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static void parseStepsOption(CommandLine line) throws ParseException {
		String s = line.getOptionValue("s", STEPS_DEFAULT_VALUE.toString());
		try {
			_steps = Integer.parseInt(s);
			assert (_steps > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid steps value: " + s);
		}
	}

	private static void parseGravityLawsOption(CommandLine line) throws ParseException {

		String gl = line.getOptionValue("gl");
		if (gl != null) {
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gl.equals(fe.getString("type"))) {
					_gravityLawsInfo = fe;
					break;
				}
			}
			if (_gravityLawsInfo == null) {
				throw new ParseException("Invalid gravity laws: " + gl);
			}
		} else {
			_gravityLawsInfo = _gravityLawsFactory.getInfo().get(0);
		}
	}

	private static void startBatchMode() throws Exception {
		// create and connect components, then start the simulator
		// BuilderBasedFactory<GravityLaws> builder = new
		// BuilderBasedFactory<GravityLaws>(_gravities); //ESTO YA SE GENERA EN init()
		// NO ES NECESARIO HACERLO DE NUEVO AQUÃ�
		// GravityLaws gl = builder.createInstance(_gravityLawsInfo);
		GravityLaws gl = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator ps = new PhysicsSimulator(gl, _dtime);
		Controller controller = new Controller(ps, _bodyFactory,_gravityLawsFactory);

		controller.loadBodies(new FileInputStream(_inFile));

		if (_outFile != null)
			controller.run(_dtime, new FileOutputStream(_outFile));
		else
			controller.run(_dtime, null);
	}
	
	private static void startGUIMode() throws Exception {
		GravityLaws gl = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator ps = new PhysicsSimulator(gl, _dtime);
		Controller controller = new Controller(ps, _bodyFactory,_gravityLawsFactory);
		
		if (_inFile != null)
			controller.loadBodies(new FileInputStream(_inFile));

		final MainWindow v = new MainWindow(controller);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				v.pack();
				v.setLocationRelativeTo(null);
				v.setVisible(true);
			}});
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if (_mode.toUpperCase().equals("BATCH")) {
			startBatchMode();
		} else {
			startGUIMode();
		}
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}

	public static int getSteps() {
		return _steps;
	}
}
