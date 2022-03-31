package assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskListener;

// Event source = Generates the event(for example button
// Event listener object receives the event object and handles it
// Event object describes the event
public class ToDo implements TaskListener, ActionListener {

	private JScrollPane scrollWheel = new JScrollPane();
	private JButton StudyTaskbutton = new JButton("New StudyTask");
	private JButton HomeTaskbutton = new JButton("New HomeTask");
	private JButton CustomTaskbutton = new JButton("New WorkTask");
	private JButton sortByAlfButton = new JButton("Sorted Alfabetical");
	private JButton sortByCompButton = new JButton("Sorterd Completed");
	private JButton sortByTypeButton = new JButton("Sorted Type");
	private Task homeTask, studyTask, customTask;
	private JFrame frame;
	private JPanel mid, top, bottom, root;
	private JLabel totalTasks;

	private int total = 0, completed = 0;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<Task> taskTypes = new ArrayList<Task>();
	private ArrayList<Task> completedTasks = new ArrayList<Task>();
	private ArrayList<Task> unCompletedTasks = new ArrayList<Task>();
private String chozenTaskType;

	ToDo() {
		totalTasks = new JLabel();
		frame = new JFrame(); // Creates a frame
		root = new JPanel();
		// Top panel will hold the 3 different types of buttons.
		top = new JPanel();
		// Mid panel will hold all the task after one of the create button is clicked
		mid = new JPanel();
		// Bottom panel will hold the sortings button, 3 different type of sorting
		// buttons.
		bottom = new JPanel();
		// Root which will hold our 3 panels will go from a Y-axis, meaning top to down
		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		// Top layout will go from left to right because of X axis boxlayout
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		// Mid layout which hold the task created goes from up towards down.
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		frame.setTitle("Task management");
		top.add(HomeTaskbutton);
		top.add(Box.createHorizontalStrut(10)); // works like margin from css, creating space
		top.add(StudyTaskbutton);
		top.add(Box.createHorizontalStrut(10));
		top.add(CustomTaskbutton, BorderLayout.NORTH);
		root.add(top);
		root.add(mid);
		frame.add(root);
		frame.add(totalTasks);
		HomeTaskbutton.addActionListener(this);
		StudyTaskbutton.addActionListener(this);
		CustomTaskbutton.addActionListener(this);
		root.add(bottom);
		bottom.add(sortByTypeButton);
		sortByTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mid.removeAll();
				for(int i=0; i<taskTypes.size(); i++)
				{
					taskCreated(taskTypes.get(i));
				}
			}
		});
	
		bottom.add(sortByCompButton);
		sortByCompButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mid.removeAll();
				sortCompleted();
				for (int i = 0; i < completedTasks.size(); i++) {
					taskCreated(completedTasks.get(i));

				}
			}
		});
		bottom.add(sortByAlfButton);
		sortByAlfButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mid.removeAll();
				sortAlphabetically();
				for (int i = 0; i < tasks.size(); i++) {
					taskCreated(tasks.get(i));
				}
			}
		});

		frame.setMinimumSize(new Dimension(450, 300));
		frame.setLayout(new FlowLayout());
		frame.setBounds(100, 100, 400, 100); // size of frame
		frame.setVisible(true); // makes the frame visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit when clicking on closing button (X)

	}

	public void sortAlphabetically() {
		Collections.sort(tasks, new TaskTextComparator());
	}

	private void sortCompleted() {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isComplete())
				completedTasks.add(tasks.get(i));
		}

		for (int i = 0; i < tasks.size(); i++) {
			if (!tasks.get(i).isComplete())
				completedTasks.add(tasks.get(i));
		}
	}
private void sortByType()
{
	for(int i=0; i<tasks.size(); i++)
	{
		if(tasks.get(i).getTaskType().equals("Home"))
		taskTypes.add(tasks.get(i));
	}
	for(int i=0; i<tasks.size(); i++)
	{
		if(tasks.get(i).getTaskType().equals("Study"))
		taskTypes.add(tasks.get(i));
	}
	for(int i=0; i<tasks.size(); i++)
	{
		if(tasks.get(i).getTaskType().equals("Work"))
		taskTypes.add(tasks.get(i));
	}

}

	public static void main(String[] args) {

		ToDo t = new ToDo();

	}

	// This is connected to the checkerbox where if the user clickes on it, the
	// completed on statusbar increase with 1
	@Override
	public void taskCompleted(Task t) {
		this.completed++;
		totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	}

	@Override
	public void taskUncompleted(Task t) {
		// This is also connected to the checker box, but instead of the statusbar
		// increaing it will decrease when user unclicks the checker box
		this.completed--;
		totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	}

	@Override
	public void taskCreated(Task t) {
		// This call the gui component for every task created
		mid.add(t.getGuiComponent());
		frame.validate();

	}

	public void taskImportant() {

	}

	@Override
	public void taskRemoved(Task t) {
		// TODO Auto-generated method stub
		mid.remove(t.getGuiComponent());
		this.total--;
		if (this.completed > 0) {
			this.completed--;
		}
		totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
		frame.validate();
	}

	public void actionPerformed(ActionEvent whichButton) {
		if (whichButton.getSource().equals(HomeTaskbutton)) {
			homeTask = new HomeTask();
			tasks.add(homeTask);

			homeTask.setTaskListener(this);
			taskCreated(homeTask);
			this.total++;
			this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
			frame.validate();
		}
		if (whichButton.getSource().equals(StudyTaskbutton)) {
			studyTask = new StudyTask();
			// tasks.add(studyTask);
			studyTask.setTaskListener(this);
			taskCreated(studyTask);
			this.total++;
			this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
			frame.validate();
		}
		if (whichButton.getSource().equals(CustomTaskbutton)) {
			customTask = new CustomTask();
			tasks.add(customTask);
			taskCreated(customTask); // Has to refresh everytime clicking new task
			customTask.setTaskListener(this);
			this.total++;
			this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
			frame.validate();
		}
	}

	@Override
	public void taskChanged(Task t) {

	};

}
