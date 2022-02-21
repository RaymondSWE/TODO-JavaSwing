package assignment2;

import java.awt.BorderLayout;
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

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskListener;

// Event source = Generates the event(for example button
// Event listener object receives the event object and handles it
// Event object describes the event
public class ToDo implements TaskListener {

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
		frame.setTitle("Task management by Raman and Karl");
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

		HomeTaskbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				homeTask = new HomeTask();
				taskCreated(homeTask);
				total++;
				totalTasks.setText("Total task completed: " + completed + "/" + total);
			}
		});
		// homeTask.getGuiComponent().setVisible(false);
		StudyTaskbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				studyTask = new StudyTask();
				taskCreated(studyTask); // Has to refresh everytime clicking new task
				total++;
				totalTasks.setText("Total task completed: " + completed + "/" + total);
			}
		});

		CustomTaskbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				customTask = new CustomTask();
				taskCreated(customTask); // Has to refresh everytime clicking new task
				total++;
				totalTasks.setText("Total task completed: " + completed + "/" + total);
			}
		});

		frame.setMinimumSize(new Dimension(450, 300));
		frame.setLayout(new FlowLayout());
		frame.setBounds(100, 100, 400, 100); // size of frame
		frame.setVisible(true); // makes the frame visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit when clicking on closing button (X)
	}

	public static void main(String[] args) {

		new ToDo();

	}

	@Override
	public void taskChanged(Task t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskCompleted(Task t) {
		// TODO Auto-generated method stub
		t.isComplete();
	}

	@Override
	public void taskUncompleted(Task t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskCreated(Task t) {
		// TODO Auto-generated method stub
		mid.add(t.getGuiComponent());
		mid.add(Box.createVerticalStrut(10));
		frame.validate();

	}

	@Override
	public void taskRemoved(Task t) {
		// TODO Auto-generated method stub
		System.out.println("here");

	}

}
