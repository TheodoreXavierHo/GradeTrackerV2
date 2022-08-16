import java.io.*;
import java.util.ArrayList;

public class AssessListSaveFile {
    private ArrayList<Assessment> assessments = new ArrayList<>();

    public AssessListSaveFile() {
        loadAssess();
    }

    public void loadAssess() {
        File f = new File("./AssessSave.txt");
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Assessment> assessments = (ArrayList<Assessment>) ois.readObject();
            System.out.println("Loaded!\n");
            this.assessments = assessments;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAssess() {
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

    public ArrayList<Assessment> getAssessment() {
        return assessments;
    }

    public void removeAssessments(int index) {
        this.assessments.remove(index);
    }

    public void setAssessments(String name, String assessmentCode, String description, double totalMarks, double weightage) {
        this.assessments.add(new Assessment(name, assessmentCode, description, totalMarks, weightage));
    }
}
