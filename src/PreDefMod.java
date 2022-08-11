import java.util.ArrayList;

public enum PreDefMod {
    // declare constant of enum type
    PF("Programming Fundamentals", "PF001",
            "Java", 1),

    CN("Communication and Networks", "CN001",
            "Computer Networking", 1),

    BSP("Business Statistics with Python", "BSP001",
            "Business Statistics", 1),

    PS("Problem Solving", "PS001",
            "Coding Logic & Python", 1),

    DMS("Database Management and Security", "DMS001",
            "Security Management", 1),

    ID("Interaction Design", "ID001",
            "Design Interaction", 1),

    IPM("IT Project Management", "IPM001",
            "Management of IT Projects", 1),

    SDT("Systems Development Technique", "SDT001",
            "Development Techniques for Systems", 1);

    // Instance fields
    private final String name;
    private final String moduleCode;
    private final String description;
    private final int creditUnits;
    private final ArrayList<Assessment> assessments = new ArrayList<>();

    // Enum constructor
    PreDefMod(String name, String moduleCode,
              String description, int creditUnits) {
        this.name = name;
        this.moduleCode = moduleCode;
        this.description = description;
        this.creditUnits = creditUnits;
        this.assessments.add(new Assessment("CA1", "Common Test 1",
                100, 10));
        this.assessments.add(new Assessment("CA2", "Common Test 2",
                100, 40));
        this.assessments.add(new Assessment("CA3", "Common Test 3",
                100, 40));
        this.assessments.add(new Assessment("CA4", "Common Test 4",
                100, 10));
    }

    public String getName() {
        return name;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getDescription() {
        return description;
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }
}