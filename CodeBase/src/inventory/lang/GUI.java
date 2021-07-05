package inventory.lang;

import java.io.File;
import java.util.Stack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

/**
 * This is a Swing GUI for the Pantry_Sorter application. While it was originally
 * written for a COMP261 paper at VUW, I have since modified it to the point
 * where it would no longer be fit for purpose for the COMP261 paper.
 * 
 * This GUI enables users to interact with a file containing information about
 * their pantries. It is more intended as an interim step up from using the
 * command line, rather than a final solution.
 * 
 * @author tony for VUW, COMP261 ~ Modified by darianisak
 */
public abstract class GUI {
	
	//	Ordinary Global Variables

	/**
	 * The idea of the eventStack is to have an easy way of determining
	 * which button was most recently pressed by the user. A button press will
	 * push a string representation of the button to the eventStack, that
	 * way, with little effort, Pantry_Sorter can handle seperate button
	 * presses appropriately, without the need for mutiple distinct methods.
	 */
	private static Stack<String> eventStack = new Stack<String>();


	/**
	 * onAction is an abstract method which enables communication between
	 * the GUI and the main Pantry_Sorter class. This serves as the primary
	 * method by which actions are performed during runtime.
	 * 
	 * @param events is a stack of all buttons pressed recently; this stack
	 * does not include references to the queryField.
	 * @param input is text specified by the user through the use of the
	 * queryField, aka JTextField.
	 */
	protected abstract void onAction(Stack<String> events, String input);
	
	/**
	 * onAction is a single argument specific method, ideally only used for
	 * cases of "static" button presses, such as save. Besides this, it is
	 * functional identical as a method prototype to the priorly defined
	 * onAction method.
	 * 
	 * @param events is a stack of all buttons pressed recently; this stack
	 * does not include references to the queryField.
	 */
	protected abstract void onAction(Stack<String> events);

	/**
	 * onLoad is an abstract method which allows the working file to be
	 * communicated between the GUI and the Pantry_Sorter class.
	 * 
	 * @param items is a file, specified by the user, to contain their pantry,
	 * formatted accordingly with the parsers grammar set.
	 */
	protected abstract void onLoad(File items);

	/**
	 * getTextOutputArea is a getter method for obtaining the JTextArea object
	 * responsible for all program output. This aspect of the GUI comprises the
	 * majority of the window and is located below the tool bar.
	 * 
	 * @return the object responsible for program output.
	 */
	public JTextArea getTextOutputArea() {
		return textOutputArea;
	}

	/**
	 * getSearchBox is a getter method for obtaining the JTextField object
	 * responsible for allowing the user to input information. This aspect of
	 * the GUI is located to the right of the Query label.
	 * 
	 * @return the object responsible for user input.
	 */
	public JTextField getSearchBox() {
		return queryField;
	}

	/**
	 * redraw is a helper method, responsible for updating the GUI. This method
	 * should be called any time a change is expected to occur on screen, aka
	 * following any button press or search. Without this method being called,
	 * the display will not be updated.
	 */
	public void redraw() {frame.repaint();}
	
	//	GUI related global variables
	
	private static final int TEXT_OUTPUT_ROWS = 10;	//	Defines height of textOutputArea
	private static final int SEARCH_COLS = 15;		//	Defines width of queryField
	
	private JFrame frame;
	private JTextArea textOutputArea;
	private JTextField queryField;
	private JFileChooser fileChooser;

	/**
	 * Constructor method for GUI; acts as a super constructor for Pantry_Sorter
	 */
	public GUI() {initialise();}

	private void initialise() {

		//	Initializes the buttons and their respective action listeners, but
		//	does not add it to the GUI.
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("QUIT");
				System.exit(0);
			}
		});

		fileChooser = new JFileChooser();
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("LOAD");
				File items = null;
				
				// set up the file chooser
				fileChooser.setDialogTitle("Select input directory");
				// run the file chooser and check the user didn't hit cancel
				if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					// get the files in the selected directory and match them to
					// the files we need.
					items = fileChooser.getSelectedFile();

					//	Checks that the selected file is not null
					if	(items == null)	{
						System.out.println("Error in file handling.");
					}	else {
						onLoad(items);
					}
				}
			}
		});

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("SEARCH");
				onAction(eventStack, getSearchBox().getText());
				redraw();
			}
		});
		
		JButton display = new JButton("Display");
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev)	{
				eventStack.push("DISPLAY");
				onAction(eventStack);
				redraw();
			}
		});

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("ADD");
				onAction(eventStack, getSearchBox().getText());
				redraw();
			}
		});

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("REMOVE");
				onAction(eventStack, getSearchBox().getText());
				redraw();
			}
		});

		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("SAVE");
				onAction(eventStack);
				redraw();
			}
		});

		JButton sort = new JButton("Sort");
		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				eventStack.push("SORT");
				onAction(eventStack);
				redraw();
			}
		});

		//	Generate the Query Field element of the GUI
		
		queryField = new JTextField(SEARCH_COLS);
		queryField.setMaximumSize(new Dimension(0, 25));
		queryField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	This is the only listener not logged by the eventStack
				//	as that would require extra computation to handle
				onAction(eventStack, queryField.getText());
				redraw();
			}
		});

		//	Generate the enclosing layouts of the top bar
		
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

		// make an empty border so the components aren't right up against the
		// frame edge.
		
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		controls.setBorder(edge);

		//	GUI layout for the load and save buttons
		
		JPanel loadSave = new JPanel();
		loadSave.setLayout(new GridLayout(2, 1));
		// manually set a fixed size for the panel containing the load and quit
		// buttons (doesn't change with window resize).
		loadSave.setMaximumSize(new Dimension(50, 100));
		loadSave.add(load);
		loadSave.add(save);
		controls.add(loadSave);
		// rigid areas are invisible components that can be used to space
		// components out.
		controls.add(Box.createRigidArea(new Dimension(15, 0)));

		//	GUI layout of main controls ~ Comments from this point on are 
		//	largely unmodified from the orginal source
		
		JPanel navigation = new JPanel();
		navigation.setMaximumSize(new Dimension(150, 60));
		navigation.setLayout(new GridLayout(2, 3));
		navigation.add(add);
		navigation.add(remove);
		navigation.add(searchButton);
		navigation.add(sort);
		navigation.add(display);
		navigation.add(quit);
		controls.add(navigation);
		controls.add(Box.createRigidArea(new Dimension(15, 0)));
		// glue is another invisible component that grows to take up all the
		// space it can on resize.
		controls.add(Box.createHorizontalGlue());

		controls.add(new JLabel("Query"));
		controls.add(Box.createRigidArea(new Dimension(5, 0)));
		controls.add(queryField);

		//	Generate the output area where all text will be written

		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true); // pretty line wrap.
		textOutputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textOutputArea);
		// these two lines make the JScrollPane always scroll to the bottom when
		// text is appended to the JTextArea.
		DefaultCaret caret = (DefaultCaret) textOutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		/*
		 * finally, make the outer JFrame and put it all together. this is more
		 * complicated than it could be, as we put the drawing and text output
		 * components inside a JSplitPane so they can be resized by the user.
		 * the JScrollPane and the top bar are then added to the frame.
		 */

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(5); // make the selectable area smaller
		split.setContinuousLayout(true); // make the panes resize nicely
		split.setResizeWeight(1); // always give extra space to drawings
		// JSplitPanes have a default border that makes an ugly row of pixels at
		// the top, remove it.
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setBottomComponent(scroll);

		frame = new JFrame("Pantry Sorter");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(controls, BorderLayout.NORTH);
		frame.add(split, BorderLayout.CENTER);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}
}