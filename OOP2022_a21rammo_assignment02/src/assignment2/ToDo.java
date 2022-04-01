package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskListener;


// Event source = Generates the event(for example button
// Event listener object receives the event object and handles it
// Event object describes the event
public class ToDo implements TaskListener, ActionListener {

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
	private JLabel titleText = new JLabel("To Do List");

	private int total = 0, completed = 0;
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<Task> taskTypes = new ArrayList<Task>();
	private ArrayList<Task> completedTasks = new ArrayList<Task>();
	private Boolean completedBtnPressed = true;

	ToDo() {
		totalTasks = new JLabel();
		frame = new JFrame("Task management"); // Creates a frame
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
		root.add(titleText);
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
		// this action listener is connected to the sortType button. It removes all
		// tasks, sorts them by type and adds them back in to the GUI according to the
		// sorted order.
		sortByTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mid.removeAll();
				sortByType();

				for (int i = 0; i < taskTypes.size(); i++) {
					taskCreated(taskTypes.get(i));
				}
			}
		});

		bottom.add(sortByCompButton);
		sortByCompButton.addActionListener(new ActionListener() {
			// this action listener is connected to the sortCompleted button. It removes all
			// tasks, sorts them by completed and adds them back in to the GUI according to
			// the sorted order. If the user presses this bnutton again the sorting is
			// reversed and added in to the GUI again so that the user sees the non
			// completed tasks first.

			public void actionPerformed(ActionEvent e) {
				if (completedBtnPressed) {
					mid.removeAll();
					sortCompleted();
					for (int i = 0; i < completedTasks.size(); i++) {
						taskCreated(completedTasks.get(i));
					}
					completedBtnPressed = false;
				} else {
					completedBtnPressed = false;
					Collections.reverse(completedTasks);
					mid.removeAll();
					for (int i = 0; i < completedTasks.size(); i++)
						taskCreated(completedTasks.get(i));

				}
			}
		});
		bottom.add(sortByAlfButton);
		// this action listener is connected to the sortAlphabetical button. It removes
		// all tasks, sorts them in an alphabetical order and adds them back in to the
		// GUI.

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

	// this function sorts the task in an alphabetical order. It takes no input.
	private void sortAlphabetically() {
		Collections.sort(tasks, new TaskTextComparator());
	}

	// this function sorts the tasks by completed where the completed once are
	// first. It takes no input.
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

	// this function sorts tasks in a specific order based on type. It takes no
	// input
	private void sortByType() {
		String studyType = "Study";
		String homeType = "Home";
		String customType = "Work";
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskType().equals(homeType))
				taskTypes.add(tasks.get(i));
		}
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskType().equals(customType))
				taskTypes.add(tasks.get(i));
		}
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskType().equals(studyType))
				taskTypes.add(tasks.get(i));
		}

	}

	public static void main(String[] args) {

		ToDo t = new ToDo();

	}

	// This is connected to a checkbox where if the user selects it, The completed
	// amount on the status bar increases by 1. It takes an object of type Task as
	// input.
	@Override
	public void taskCompleted(Task t) {
		this.completed++;
		totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	}

	// This is also connected to a check box, but instead of the status bar
	// increasing it decreases when user unselects the check box. It takes an object
	// of Type Task as input.
	@Override
	public void taskUncompleted(Task t) {
		this.completed--;
		totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	}

	// this function adds the Tasks to the GUI. It takes An object of type Task as
	// input.
	@Override
	public void taskCreated(Task t) {
		mid.add(t.getGuiComponent());
		frame.validate();
	}

	// this function is responsible for removing a task from the GUI. It takes an
	// object of type Task as input.
	@Override
	public void taskRemoved(Task t) {
		mid.remove(t.getGuiComponent());
		this.total--;
		if (this.completed > 0) {
			this.completed--;
		}
		totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
		frame.validate();
	}

	// this function is responsible for creating new tasks when the user wants to
	// add a task.
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
			studyTask.setTaskListener(this);
			taskCreated(studyTask);
			tasks.add(studyTask);
			this.total++;
			this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
			frame.validate();
		}
		if (whichButton.getSource().equals(CustomTaskbutton)) {
			customTask = new CustomTask();
			taskCreated(customTask); // Has to refresh everytime clicking new task
			customTask.setTaskListener(this);
			tasks.add(customTask);
			this.total++;
			this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
			frame.validate();
		}
	}

	@Override
	public void taskChanged(Task t) {

	};

}
