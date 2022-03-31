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

	JScrollPane scrollWheel = new JScrollPane();
	JButton StudyTaskbutton = new JButton("New StudyTask");
	JButton HomeTaskbutton = new JButton("New HomeTask");
	JButton CustomTaskbutton = new JButton("New WorkTask");
	JButton sortByAlfButton = new JButton("Sortering alfabetical");
	JButton sortByCompButton = new JButton("Sortering by completed");
	JButton sortByTypeButton = new JButton("Unsorted");
	Task homeTask, studyTask, customTask;
	JFrame frame;
	JPanel mid;
	JLabel totalTasks;

	int total = 0, completed = 0;
	ArrayList<Task> tasks = new ArrayList<Task>();
	ArrayList<Task> tasksAlphabetical = new ArrayList<Task>();
	ArrayList<Task> completedTasks = new ArrayList<Task>();
	ArrayList<Task> unCompletedTasks = new ArrayList<Task>();

	ToDo() {
		totalTasks = new JLabel();
		frame = new JFrame(); // Creates a frame
		JPanel root = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		mid = new JPanel();

		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		frame.setTitle("Task management");
		top.add(HomeTaskbutton);
		top.add(Box.createHorizontalStrut(10)); // works like margin from css
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
		bottom.add(sortByAlfButton);
		bottom.add(sortByCompButton);
		sortByCompButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		bottom.add(sortByTypeButton);
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
		tasksAlphabetical.addAll(tasks);
>>>>>>> d3371be44a43858126291517c0849e3c21cd6aee
		Collections.sort(tasks, new TaskTextComparator());
		mid.removeAll();
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
