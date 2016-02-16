package action;

import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;

/**
 * Created by zzt on 2/13/16.
 * <p>
 * Usage:
 */
public class Account extends ActionSupport {

    //    User user;
    //    Account account;
    //    ArrayList<Reserve> reserves;
    private ArrayList<StudentBean> students;

    @Override
    public String execute() throws Exception {
        students = new ArrayList<>();
        students.add(new StudentBean("o7bb002", "Gokul", "ECE", 4));
        students.add(new StudentBean("o7bc074", "Muthu Vijay", "CSE", 6));
        students.add(new StudentBean("o7bb040", "Jaya Prakash", "ECE", 10));
        students.add(new StudentBean("o7bc055", "Mohiadeen", "CSE", 7));
        students.add(new StudentBean("o7bd047", "HariPriya", "IT", 1));
        students.add(new StudentBean("o7bd024", "Pavithra", "IT", 3));
        students.add(new StudentBean("o7bb009", "Aswin", "ECE", 8));
        students.add(new StudentBean("o7ba029", "Sharmila", "IT", 11));
        students.add(new StudentBean("o7ba027", "Nilafar", "IT", 2));
        students.add(new StudentBean("o7bd081", "Dinesh Babu", "MECH", 13));
        students.add(new StudentBean("o7ba062", "Lourde", "MECH", 9));
        students.add(new StudentBean("o7bc079", "Nisha", "CSC", 5));
        students.add(new StudentBean("o7bb039", "Guru Prasad", "MECH", 12));
        students.add(new StudentBean("o7bc033", "Gowtham Raj", "CSE", 15));
        students.add(new StudentBean("o7bb039", "Ibrahim Sha", "ECE", 14));
        students.add(new StudentBean("o7bd081", "Dinesh Babu", "MECH", 16));
        students.add(new StudentBean("o7bb002", "Gokul", "ECE", 4));
        students.add(new StudentBean("o7bc074", "Muthu Vijay", "CSE", 6));
        students.add(new StudentBean("o7bb040", "Jaya Prakash", "ECE", 10));
        students.add(new StudentBean("o7bc055", "Mohiadeen", "CSE", 7));
        students.add(new StudentBean("o7bd047", "HariPriya", "IT", 1));
        students.add(new StudentBean("o7bd024", "Pavithra", "IT", 3));
        students.add(new StudentBean("o7bb009", "Aswin", "ECE", 8));
        students.add(new StudentBean("o7ba029", "Sharmila", "IT", 11));
        students.add(new StudentBean("o7ba027", "Nilafar", "IT", 2));
        students.add(new StudentBean("o7bd081", "Dinesh Babu", "MECH", 13));
        students.add(new StudentBean("o7ba062", "Lourde", "MECH", 9));
        students.add(new StudentBean("o7bc079", "Nisha", "CSC", 5));
        students.add(new StudentBean("o7bb039", "Guru Prasad", "MECH", 12));
        students.add(new StudentBean("o7bc033", "Gowtham Raj", "CSE", 15));
        students.add(new StudentBean("o7bb039", "Ibrahim Sha", "ECE", 14));
        students.add(new StudentBean("o7bd081", "Dinesh Babu", "MECH", 16));

        return SUCCESS;
    }

    public ArrayList<StudentBean> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentBean> students) {
        this.students = students;
    }
}
