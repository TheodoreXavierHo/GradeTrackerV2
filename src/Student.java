import java.util.ArrayList;

public class Student {
    private String name; // Student Name
    private String studentID; // Student ID (The description of the module.)
    private final ArrayList<Module> modules = new ArrayList<>(); // A list of Modules the Student is taking.

    public Student (String name, String studentID) {
        setName(name);
        setStudentID(studentID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getTotalCreditUnits() {
        int total = 0;
        for (Module module : modules) {
            total += module.getCreditUnits();
        }
        return total;
    }

    public double getGPA() {
        double totalWeightedGradePoint = 0;
        for (Module module : modules) {
            totalWeightedGradePoint += module.getWeightedGradePoints();
        }
        return totalWeightedGradePoint / getTotalCreditUnits();
    }

    public void setModules(String name, String moduleCode, String description, int creditUnits) {
        this.modules.add(new Module(name, moduleCode, description, creditUnits));
    }

    public void getAllModules() {
        this.modules.forEach(modules -> System.out.println(modules.getName() + " - " + modules.getModuleCode()
                + " - " + modules.getDescription() + " - " + modules.getCreditUnits()));
    }

    public void getModuleList(int index) {
        System.out.printf("%s - %s - %s - %d%n", this.modules.get(index).getName(),
                this.modules.get(index).getModuleCode(),
                this.modules.get(index).getDescription(),
                this.modules.get(index).getCreditUnits());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    // Gets the Module's Index Number in the Modules Array List
    public int getIndexNumber(String name) {
        int index = 0;
        if (this.modules.size() > 0){
            for (int x = 0; x < this.modules.size(); x++) {
                if ((this.modules.get(x).getName()).equals(name)) {
                    index = x;
                    break;
                }
            }
        }
        return index;
    }

    public void removeModules(int index) {
        this.modules.remove(index);
    }
}