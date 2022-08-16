public class Assessment {

    private String name; // Name of the Assessment (e.g. CA1, CA2, etc.).

    private String description; // The description of the Module

    private String assessmentCode;

    private double marks; // The marks scored for this particular Assessment

    private double totalMarks; // Total possible marks for this Assessment

    private double weightage; // The percentage of marks (out of the overall) that this Assessment carries


    // Set up the Assessment Constructor
    public Assessment(String name, String assessmentCode, String description, double totalMarks, double weightage) {
        setName(name);
        setAssessmentCode(assessmentCode);
        setDescription(description);
        setTotalMarks(totalMarks);
        setWeightage(weightage);
    }

    // Get the weighted marks (marks/total marks) of an Assessment
    public double getWeightedMarks() {
        return (getMarks()/getTotalMarks()) * (weightage*0.01);
    }

    // Get Assessment Name
    public String getName() {
        return name;
    }

    // Set Assessment Name
    public void setName(String name) {
        this.name = name;
    }

    // Get the Assessment Description
    public String getDescription() {
        return description;
    }

    // Se the Assessment Description
    public void setDescription(String description) {
        this.description = description;
    }

    // Get Assessment Marks
    public double getMarks() {
        return marks;
    }

    // Set Assessment Marks
    public void setMarks(double marks) {
        if (marks < 0) {
            throw new IllegalArgumentException("Marks set is less than 0. PLease enter a value above 0.");
        } else if (marks >= this.totalMarks) {
            throw new IllegalArgumentException("Marks set is greater than total available marks.\n" +
                    "PLease enter a value below the total marks.");
        } else {
            this.marks = marks;
        }
    }

    public void removeMarks() {
        this.marks = 0;

    }

    // Get total marks possible for the Assessments
    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        if (totalMarks < 0) {
            throw new IllegalArgumentException("Total Marks is either less than 0 PLease enter a value above 0.");
        } else {
            this.totalMarks = totalMarks;
        }
    }

    public double getWeightage() {
        return weightage;
    }

    public void setWeightage(double weightage) {
        this.weightage = weightage;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }
}