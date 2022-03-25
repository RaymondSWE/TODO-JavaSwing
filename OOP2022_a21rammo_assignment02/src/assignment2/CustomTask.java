package assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskInputListener;
import se.his.it401g.todo.TaskListener;


public class CustomTask extends JPanel implements Task {

	/**
	 * The editable text field.
	 */
	private JTextField text;

	/**
	 * The non editable text label.
	 */
	private JLabel textLabel;

	/**
	 * Check box holding the completion status.
	 */
	JCheckBox completed = new JCheckBox();
	

	/**
	 * Check box holding the important status.
	 */
	JCheckBox important = new JCheckBox("Important");

	/**
	 * The task listener used for reporting changes to the main application.
	 */
	private TaskListener listener;

	/**
	 * This is the constructor for the task, initiating the GUI component for the
	 * task. Several listeners are used to react to various events in the GUI.
	 */
	public CustomTask() {
		super(new BorderLayout());
		this.text = new JTextField("New task", 20);
		this.textLabel = new JLabel();
		this.textLabel.setVisible(false);
		JPanel center = new JPanel();
		center.add(text);
		center.add(textLabel);
		add(center);
		Color standard = getBackground();

		TaskInputListener inputListener = new TaskInputListener((Task) this, text, textLabel);
		this.text.addKeyListener(inputListener);
		this.textLabel.addMouseListener(inputListener);

		JButton remove = new JButton("Remove");
		add(remove, BorderLayout.EAST);
		remove.addActionListener(inputListener);

		add(completed, BorderLayout.WEST);
		completed.addItemListener(inputListener);

		add(important, BorderLayout.SOUTH);
		important.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(important.isSelected()) {
					setBackground(Color.red);
					center.setBackground(Color.red);
					completed.setBackground(Color.red);
					important.setBackground(Color.red);
                }
                else {
                	setBackground(standard);
                	center.setBackground(standard);
					completed.setBackground(standard);
					important.setBackground(standard);
                }
                   
            }
        }
    );

		setMaximumSize(new Dimension(1000, 100));
		setBorder(new TitledBorder(getTaskType()));
	}

	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public String getTaskType() {
		return "Work";
	}

	@Override
	public void setTaskListener(TaskListener t) {
		listener = t;
	}

	@Override
	public TaskListener getTaskListener() {
		return listener;
	}

	@Override
	public boolean isComplete() {
		return completed.isSelected();
	}
	
	public boolean isImportant() {
		return important.isSelected();
	}

	@Override
	public Component getGuiComponent() {
		// Since this class extends JPanel, it is itself a GUI component, and thus we
		// can return "this".
		return this;
	}
}