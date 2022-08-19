import java.io.*;
import java.util.ArrayList;

public class AssessListSaveFile extends Assessment{
    private ArrayList<Assessment> assessments = new ArrayList<>();

    public AssessListSaveFile() {
        try {
            loadAssessList();
        } catch (RuntimeException e) {
            System.out.println("Empty Assessment List");
        }
    }

    public void loadAssessList() {
        try {
            File f = new File("./AssessSave.txt");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Assessment> assessments = (ArrayList<Assessment>) ois.readObject();
            System.out.println("Loaded!\n");
            this.assessments = assessments;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAssessList() {
        File f = new File("./AssessSave.txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.assessments);
            oos.close();
            fos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetAssessList() {
        try {
            File f = new File("./AssessSave.txt");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject("");
            oos.close();
            fos.close();
            System.out.println("Assessments List Reset!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Assessment> getAssessment() {
        return assessments;
    }

    public void removeAssessments(int index) {
        this.assessments.remove(index);
    }

    public void setAssessments(String name, String assessmentCode, String description,
                               double totalMarks, double weightage) {
        this.assessments.add(new Assessment(name, assessmentCode, description, totalMarks, weightage));
    }

    public void setAssessmentsObj(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }
}
