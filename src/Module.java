import java.util.ArrayList;

public class Module {
    private String name; // Name of the Module
    private String moduleCode; // The module code of the Module
    private String description; // The description of the Module
    private int creditUnits; // How many credits the Module carries
    // A list of Assessments that the module uses to determine the final grade of the student (e.g. CA1, CA2, CA3 etc.)
    private ArrayList<Assessment> assessments = new ArrayList<>();

    public Module(String name, String moduleCode, String description, int creditUnits) {
        setName(name);
        setModuleCode(moduleCode);
        setDescription(description);
        setCreditUnits(creditUnits);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public void setCreditUnits(int creditUnits) {
        this.creditUnits = creditUnits;
    }

    // Totals the marks scored across all Assessments.
    public double getOverallMarks() {
        double overallMarks = 0;
        for (Assessment assessment : this.assessments) {
            overallMarks += assessment.getWeightedMarks();
        }
        return overallMarks;
    }

    public double getOverallTotalMarks() {
        double overallTotalMarks = 0;
        for (Assessment assessment : this.assessments) {
            overallTotalMarks += assessment.getTotalMarks();
        }
        return overallTotalMarks;
    }

    public String getOverallGrade() {
        double marksPercentage =  (getOverallMarks())*100;
        String gradeLetter = "";
        if (marksPercentage > 100) {
            throw new IllegalArgumentException("The marks percentage is over a 100%.");
        }else if (marksPercentage >= 90) {
            gradeLetter = "A+";
        }else if (marksPercentage >= 80) {
            gradeLetter = "A";
        }else if (marksPercentage >= 75) {
            gradeLetter = "B+";
        }else if (marksPercentage >= 70) {
            gradeLetter = "B";
        }else if (marksPercentage >= 65) {
            gradeLetter = "C+";
        }else if (marksPercentage >= 60) {
            gradeLetter = "C";
        }else if (marksPercentage >= 55) {
            gradeLetter = "D+";
        }else if (marksPercentage >= 50) {
            gradeLetter = "D";
        }else if (marksPercentage >= 0) {
            gradeLetter = "F";
        }else if (marksPercentage < 0) {
            throw new IllegalArgumentException("The marks percentage is over a 100%.");
        }
        return gradeLetter;
    }

    public static double getGradePoint(String grade) {
        double gp;
        switch (grade) {
            case "A+":
            case "A":
                gp = 4.0;
                break;
            case "B+":
                gp = 3.5;
                break;
            case "B":
                gp = 3.0;
                break;
            case "C+":
                gp = 2.5;
                break;
            case "C":
                gp = 2.0;
                break;
            case "D+":
                gp = 1.5;
                break;
            case "D":
                gp = 1.0;
                break;
            case "F":
                gp = 0.0;
                break;
            default:
                throw new IllegalArgumentException("There is no grade point average as there is no grade.");
        }
        return gp;
    }

    public double getWeightedGradePoints() {
        return getGradePoint(getOverallGrade()) * creditUnits;
    }

    public void setAssessments(String name, String description, double totalMarks, double weightage) {
        this.assessments.add(new Assessment(name, description, totalMarks, weightage));
    }

    public void setAssessmentsObj(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    public void getAllAssessments() {
        this.assessments.forEach(assessment -> System.out.printf("%s - %s - %.0f - %%%.0f%n", assessment.getName(),
                assessment.getDescription(), assessment.getTotalMarks(), assessment.getWeightage()));
    }

    public void getAssessmentList(int index) {
        System.out.printf("%s - %s - %.1f - %%%.0f%n", this.assessments.get(index).getName(),
                this.assessments.get(index).getDescription(),
                this.assessments.get(index).getTotalMarks(),
                this.assessments.get(index).getWeightage());
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public int getIndexNumber(String name) {
        int index = 0;
        if (this.assessments.size() > 0){
            for (int x = 0; x < this.assessments.size(); x++) {
                if ((this.assessments.get(x).getName()).equals(name)) {
                    index = x;
                    break;
                }
            }
        }
        return index;
    }
}