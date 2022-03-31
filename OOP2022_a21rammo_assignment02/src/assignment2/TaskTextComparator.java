package assignment2;

import java.util.Comparator;

import se.his.it401g.todo.Task;

public class TaskTextComparator implements Comparator
 {
public int compare(Object o1, Object o2)
{
    Task task1=(Task) o1;
    Task task2=(Task) o2;
    return (task1.getText()).compareTo(task2.getText());


}
}
