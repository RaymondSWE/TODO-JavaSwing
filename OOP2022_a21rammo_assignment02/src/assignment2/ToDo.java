package assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskListener;

// Event source = Generates the event(for example button
// Event listener object receives the event object and handles it
// Event object describes the event
public class ToDo implements TaskListener,ActionListener {
	
	JScrollPane scrollWheel = new JScrollPane();
	JButton StudyTaskbutton = new JButton("New StudyTask");
	JButton HomeTaskbutton = new JButton("New HomeTask");
	JButton CustomTaskbutton = new JButton("New WorkTask");
	JLabel titleText = new JLabel("To Do List");
	Task homeTask, studyTask, customTask;
	JFrame frame;
	JPanel mid;
	JLabel totalTasks;

	int total = 0, completed = 0;

	ToDo() {
		totalTasks = new JLabel();
		frame = new JFrame(); // Creates a frame
		JPanel root = new JPanel();
		JPanel top = new JPanel();
		mid = new JPanel();

		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		frame.setTitle("Task management");
		frame.add(titleText);

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


		frame.setMinimumSize(new Dimension(450, 300));
		frame.setLayout(new FlowLayout());
		frame.setBounds(100, 100, 400, 100); // size of frame
		frame.setVisible(true); // makes the frame visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit when clicking on closing button (X)
	}

	public static void main(String[] args) {
		
		ToDo t = new ToDo();

	}


	@Override
	public void taskCompleted(Task t) {
		// This is connected to the checkerbox where if the user clickes on it, the completed on statusbar increase with 1
        this.completed++;
        totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	}

	@Override
	public void taskUncompleted(Task t) {
		// This is also connected to the checker box, but instead of the statusbar increaing it will decrease when user unclicks the checker box
	      this.completed--;
	        totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	}

	@Override
	public void taskCreated(Task t) {
		// This call the gui component for every task created
		mid.add(t.getGuiComponent());
		//mid.add(Box.createVerticalStrut(10));
		frame.validate();

	}
	
	public void taskImportant() {
		
	}

	
	@Override
	public void taskRemoved (Task t) {
		// TODO Auto-generated method stub
		mid.remove(t.getGuiComponent());
		this.total--;
		if(this.completed>0) {
			this.completed--;
		}	
        totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
        frame.validate();
	}
	
	public void actionPerformed(ActionEvent whichButton) {
		if(whichButton.getSource().equals(HomeTaskbutton))
		{
			homeTask = new HomeTask();
	     	homeTask.setTaskListener(this);
	        taskCreated(homeTask);
	        this.total++;
	        this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	        frame.validate();
		}
		if(whichButton.getSource().equals(StudyTaskbutton))
		{
			studyTask = new StudyTask();
			studyTask.setTaskListener(this);
			taskCreated(studyTask);
	        this.total++;
	        this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	        frame.validate();
		}
		if(whichButton.getSource().equals(CustomTaskbutton))
		{
			customTask = new CustomTask();
			taskCreated(customTask); // Has to refresh everytime clicking new task
			customTask.setTaskListener(this);
	        this.total++;
	        this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	        frame.validate();
		}
	}

	@Override
	public void taskChanged(Task t) {
		// TODO Auto-generated method stub
		
	};
	
	/*

	@Override
	public void actionPerformed(ActionEvent e) {
			homeTask = new HomeTask();
	     	homeTask.setTaskListener(this);
	        taskCreated(homeTask);
	        this.total++;
	        this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
	        frame.validate();
	}

	public void actionPerformed1(ActionEvent e) {
		studyTask = new StudyTask();
		studyTask.setTaskListener(this);
		taskCreated(studyTask);
        this.total++;
        this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
        frame.validate();
	}
	
	public void actionPerformed2(ActionEvent e) {
		customTask = new CustomTask();
		taskCreated(customTask); // Has to refresh everytime clicking new task
		customTask.setTaskListener(this);
        this.total++;
        this.totalTasks.setText("Total task completed: " + this.completed + "/" + this.total);
        frame.validate();
	}
	*/
}

