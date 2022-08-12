import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GradeTracker implements Serializable {
    private final ArrayList<Student> students = new ArrayList<>();
    public static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        // Runs the programme and prints the options
        run();
    }

    public static void run() {
        System.out.printf("Welcome to the Student Tracker Application.%n%n");
        GradeTracker gradeTracker = new GradeTracker();

        int choice = 0;

        while (choice != 5) {
            choice = nextInt(String.format(
                    "%nPlease select the following menu options:%n" +
                    "1. Student Management%n" +
                    "2. Module Management%n" +
                    "3. Assessment Management%n" +
                    "4. Save/Load File%n" +
                    "5. End Programme%n" +
                    "Enter Choice: "));


            switch (choice) {
                case 1:
                    gradeTracker.studentManagementOptions();
                    break;
                case 2:
                    gradeTracker.moduleManagementOptions();
                    break;
                case 3:
                    gradeTracker.assessmentManagementOptions();
                    break;
                case 4:
                    int subChoice = nextInt("""

                            Save/Load
                            1. Save
                            2. Load
                            3. Exist
                            Enter:\s""");
                    switch (subChoice) {
                        case 1:
                            try {
                                save(gradeTracker);
                            } catch (RuntimeException e) {
                                System.out.println("Unable to save file!");
                            }
                            break;
                        case 2:
                            try {
                                gradeTracker = load();
                            } catch (RuntimeException e) {
                                System.out.println("Unable to load save file");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    if (choice != 5) {
                        System.out.println("Please select a correct option");
                    }
                    break;
            }
        }
        System.out.printf("%n%nApplication Closed!%nHave a Nice Day!");
    }

    public void studentManagementOptions() {
        int subChoice = 0;
        while(subChoice != 5) {

            subChoice = nextInt(String.format(
                    "%nStudent Management Options:%n" +
                            "1. Student Management" +
                            "2. Create New Student%n" +
                            "3. Delete New Students%n" +
                            "4. View all Students%n" +
                            "5. Return to Main Menu%n" +
                            "Enter Choice: "));

            switch (subChoice) {
                case 1:
                    studentManagement();
                    break;
                case 2:
                    System.out.println();
                    addNewStudent();
                    break;
                case 3:
                    System.out.println();
                    deleteStudent();
                    break;
                case 4:
                    System.out.println();
                    viewAllStudentDetails();
                    break;
                default:
                    if (subChoice != 5) {
                        System.out.printf("%nPlease select the correct option");
                    }
                    break;
            }
        }
    }

    public void studentManagement() {
        String name = getStudentName();
        String studentID = getStudentID();
        if (!checkIfStudent(name, studentID)) {
            System.out.println("Please enter a valid student!");
        } else {
            int subChoice = 0;
            while(subChoice != 6) {
                subChoice = nextInt(String.format(
                        "%n%s's Management Options:%n" +
                                "1. Add or Remove Modules%n" +
                                "2. Add or Remove Module's Assessments%n" +
                                "3. Add or Remove Assessment Marks%n" +
                                "4. Results Calculations%n" +
                                "5. Display all modules & Assessments%n" +
                                "Enter Choice: ", name));

                switch (subChoice) {
                    case 1 -> addRemoveMod(name);
                    case 2 -> addRemoveAssess();
                    case 3 -> addOrRemoveMarks(name, studentID);
                    case 4 -> calculateResults();
                    case 5 -> displayModAssess();
                    default -> {
                        if (subChoice != 6) {
                            System.out.printf("%nPlease select the correct option");
                        }
                    }
                }
            }
        }
    }

    public void addRemoveMod(String name) {
        int subChoice = 0;
        while(subChoice != 3) {
            subChoice = nextInt(String.format(
                    "%nAdd or Remove Module Options:%n" +
                            "1. Add Modules%n" +
                            "2. Remove Module%n" +
                            "3. Return%n" +
                            "Enter Choice: "));

            switch (subChoice) {
                case 1 -> addStuMod(name);
                case 2 -> removeStuMod();
                default -> {
                    if (subChoice != 3) {
                        System.out.printf("%nPlease select the correct option");
                    }
                }
            }
        }
    }

    public void addStuMod(String name) {
        ModListSaveFile modListSaveFile = new ModListSaveFile();
        System.out.println("\nIndex : Name : Module Code : Description : Credits Units");
        int x = 1;
        for (Module mod : modListSaveFile.getModules()) {
            System.out.printf("%d: %s, %s, %s, %d%n", (x),
                    mod.getName(),mod.getModuleCode(),
                    mod.getDescription(), mod.getCreditUnits());
            x++;
        }

        System.out.print("\nEnter Module Code of the module you would like to add: ");
        String moduleCode = input.nextLine();

        boolean checkIfModAdded = false;
        for (Module addMod : modListSaveFile.getModules()) {
            if (moduleCode.equals(addMod.getModuleCode())) {
                this.students.get(getStudentIndex(name)).setModules(
                        addMod.getName(), addMod.getModuleCode(),
                        addMod.getDescription(), addMod.getCreditUnits());

                this.students.
                        get(getStudentIndex(name)).
                        getModules().get(
                                getStudentIndex(addMod.getName())
                        ).setAssessmentsObj(addMod.getAssessments());

                System.out.printf("%nProgramme - Module Code - Descriptor - Credit Units%n");
                this.students.get(getStudentIndex(name)).
                        getModuleList(this.students.
                                get(getStudentIndex(name)).getModuleIndex(addMod.getName()));
                checkIfModAdded = true;
            }
        }
        if (!checkIfModAdded) {
            System.out.println("Please enter a valid module code.");
        }

    }

    public void removeStuMod() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            String moduleName = getModuleName();

            // Check if student already exists
            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    this.students.get(getStudentIndex(name)).
                            removeModules(this.students.get(getStudentIndex(name))
                                    .getModuleIndex(moduleName));

                    System.out.printf("%nModule Removed Successfully");
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();
        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void calculateStudentMarks() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            String moduleName = getModuleName();

            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    System.out.printf("%nTotal Marks: %.0f%%",
                            (this.students.get(getStudentIndex(name)).
                                    getModules().get(students.get(getStudentIndex(name)).
                                            getModuleIndex(moduleName)).getOverallMarks()) * 100);
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();

        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void addRemoveAssess() {

    }

    public void addOrRemoveMarks(String name, String studentID) {
        if (this.students.size() > 0) {
            displayStudentModules(name, studentID);
            String moduleName = getModuleName();

            displayStudentModAssess(name, studentID, moduleName);
            String assessmentName = getAssessmentName();

            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    if (checkIfAssessment(name, moduleName, assessmentName)) {

                        int choice = nextInt(String.format(
                                "%nMarks Setter/Remover%n" +
                                        "1. Set Marks%n" +
                                        "2. Remove Marks%n" +
                                        "3. Return%n" +
                                        "Enter: "));

                        switch (choice) {
                            case 1 -> {
                                // Display marks between 0 to total achievable marks
                                System.out.println("Marks between 0 to " + this.students.
                                        get(getStudentIndex(name)).
                                        getModules().
                                        get(this.students.
                                                get(getStudentIndex(name)).
                                                getModuleIndex(moduleName)
                                        ).
                                        getAssessments().
                                        get(this.students.
                                                get(getStudentIndex(name)).
                                                getModules().
                                                get(this.students.
                                                        get(getStudentIndex(name)).
                                                        getModuleIndex(moduleName)
                                                ).getAssessmentIndex(assessmentName)
                                        ).getTotalMarks());
                                double marks = nextDouble(String.format("%nEnter Marks: "));

                                // Add Marks
                                try {
                                    this.students.
                                            get(getStudentIndex(name)).
                                            getModules().
                                            get(this.students.
                                                    get(getStudentIndex(name)).
                                                    getModuleIndex(moduleName)
                                            ).
                                            getAssessments().
                                            get(this.students.
                                                    get(getStudentIndex(name)).
                                                    getModules().
                                                    get(this.students.
                                                            get(getStudentIndex(name)).
                                                            getModuleIndex(moduleName)
                                                    ).getAssessmentIndex(assessmentName)
                                            ).setMarks(marks);
                                } catch (IllegalArgumentException exception) {
                                    System.out.println("Please enter a valid mark!");
                                }
                                System.out.println("Marks Added!");
                            }
                            case 2 -> {
                                // Remove Marks
                                this.students.
                                        get(getStudentIndex(name)).
                                        getModules().
                                        get(this.students.
                                                get(getStudentIndex(name)).
                                                getModuleIndex(moduleName)).
                                        getAssessments().get(
                                                (this.students.
                                                        get(getStudentIndex(name)).
                                                        getModules().
                                                        get(this.students.
                                                                get(getStudentIndex(name)).
                                                                getModuleIndex(moduleName)
                                                        ).getAssessmentIndex(assessmentName)
                                                )
                                        ).removeMarks();
                                System.out.println("Marks Removed!");
                            }
                            default -> {
                            }
                        }
                    } else {
                        System.out.println("There is no assessments assign to this modules!");
                    }
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();

        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void addNewStudent() {
        String name = getStudentName();
        String studentID = getStudentID();

        // Check if student already exists
        if (!checkIfStudent(name,studentID)) {
            this.students.add(new Student(name, studentID));
            System.out.println("Name - Student ID");
            System.out.printf("%s - %s%n", this.students.get(getStudentIndex(name)).getName(),
                    this.students.get(getStudentIndex(name)).getStudentID());
            System.out.println();
            System.out.printf("%nStudent Added Successfully");
        } else {
            System.out.printf("%nStudent already exist!");
        }
        System.out.println();
    }

    public void deleteStudent() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            // Check if student already exists
            if (checkIfStudent(name,studentID)) {
                this.students.remove(getStudentIndex(name));
                System.out.printf("%nStudent Removed Successfully");
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();
        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void calculateStudentGPA() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();

            // Check if student already exists
            if (checkIfStudent(name,studentID)) {
                System.out.printf("%n%s's GPA is: %.2f", name,
                        this.students.get(getStudentIndex(name)).getGPA());
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();
        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void viewAllStudentDetails() {
        System.out.println("Index - Name - Student ID");
        for (int i = 0; i < this.students.size(); i++) {
            System.out.printf("%d: %s - %s%n", i+1, this.students.get(i).getName(),
                    this.students.get(i).getStudentID());
        }
    }

    // Checks if the student exist in the Students Array List if so, return true if not returns false
    public boolean checkIfStudent(String name, String studentID) {
        if (this.students.size() > 0) {
            for (Student student : students) {
                if (student.getName().equals(name) &&
                        student.getStudentID().equals(studentID)) {
                    return true;
                }
            }
        }
        return false;
    }


    // Module Management & Methods
    public void moduleManagementOptions() {
        int subChoice = 0;
        while(subChoice != 6) {
            subChoice = nextInt(String.format(
                    "%nModule Management Options:%n" +
                    "1. Add modules to student%n" +
                    "2. Remove modules from student%n" +
                    "3. Calculate Student's marks for module%n" +
                    "4. Calculate Student's grade for module%n" +
                    "5. Display all Student's modules%n" +
                    "6. Return to Main Menu%n" +
                    "Enter Choice: "
            ));

            switch (subChoice) {
                case 1:
                    System.out.println();
                    addModule();
                    break;
                case 2:
                    System.out.println();
                    removeModule();
                    break;
                case 3:
                    System.out.println();
                    calculateStudentMarks();
                    break;
                case 4:
                    System.out.println();
                    calculateStudentGrade();
                    break;
                case 5:
                    System.out.println();
                    //displayAllStudentModules();
                    break;
                default:
                    if (subChoice != 6) {
                        System.out.printf("%nPlease select the correct option");
                    }
                    break;
            }
        }
    }

    public void addModule() {
        String name = getStudentName();
        String studentID = getStudentID();

        int subChoice = nextInt("""
                Would you like to
                1. Add custom module
                2. Add predefined module
                3. Exit
                Enter:\s""");

        switch (subChoice) {
            case 1:
                if (students.size() > 0) {
                    if (checkIfStudent(name, studentID)) {
                        System.out.printf("%nEnter Module Name: ");
                        String moduleName = input.nextLine();

                        System.out.print("Enter Module Code: ");
                        String moduleCode = input.nextLine();

                        System.out.print("Enter Descriptor: ");
                        String descriptor = input.nextLine();

                        int creditUnits = nextInt("Enter CreditUnits: ");

                        this.students.get(getStudentIndex(name)).
                                setModules(moduleName, moduleCode, descriptor, creditUnits);

                        System.out.printf("%nProgramme - Module Code - Descriptor - Credit Units%n");
                        this.students.get(getStudentIndex(name)).
                                getModuleList(this.students.
                                        get(getStudentIndex(name)).getModuleIndex(moduleName));

                    } else {
                        System.out.printf("%nStudent does not exist!");
                    }
                } else {
                    System.out.printf("%nThere is no student in the list!");
                }
                System.out.println();
                break;
            case 2:
                System.out.println("\nIndex : Name : Module Code : Description : Credits Units");
                int x = 1;
                for (PreDefMod mod : PreDefMod.values()) {
                    System.out.printf("%d: %s, %s, %s, %d%n", (x),
                            mod.getName(),mod.getModuleCode(),
                            mod.getDescription(), mod.getCreditUnits());
                    x++;
                }
                System.out.print("\nEnter Module Code of the module you would like to add: ");
                String moduleCode = input.nextLine();

                boolean checkIfModAdded = false;
                for (PreDefMod addMod : PreDefMod.values()) {
                    if (moduleCode.equals(addMod.getModuleCode())) {
                        this.students.get(getStudentIndex(name)).setModules(
                                addMod.getName(), addMod.getModuleCode(),
                                addMod.getDescription(), addMod.getCreditUnits());

                        this.students.
                                get(getStudentIndex(name)).
                                getModules().get(
                                        getStudentIndex(addMod.getName())
                                ).setAssessmentsObj(addMod.getAssessments());

                        System.out.printf("%nProgramme - Module Code - Descriptor - Credit Units%n");
                        this.students.get(getStudentIndex(name)).
                                getModuleList(this.students.
                                        get(getStudentIndex(name)).getModuleIndex(addMod.getName()));
                        checkIfModAdded = true;
                    }
                }
                if (!checkIfModAdded) {
                    System.out.println("Please enter a valid module code.");
                }
                break;
            case 3:
                break;
            default:
                System.out.println("Please input a value between 1 - 3");
                break;
        }
    }

    public void removeModule() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            String moduleName = getModuleName();

            // Check if student already exists
            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    this.students.get(getStudentIndex(name)).
                            removeModules(this.students.get(getStudentIndex(name))
                                    .getModuleIndex(moduleName));

                    System.out.printf("%nModule Removed Successfully");
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();
        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void calculateStudentGrade() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            String moduleName = getModuleName();

            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    System.out.printf("%nGrade: %s", this.students.get(getStudentIndex(name)).
                            getModules().get(students.get(getStudentIndex(name)).
                                    getModuleIndex(moduleName)).getOverallGrade());
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();

        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void displayStudentModules(String name, String studentID) {
        if (this.students.size() > 0) {
            if (checkIfStudent(name,studentID)) {
                if (this.students.get(getStudentIndex(name)).getModules().isEmpty()) {
                    System.out.println("There is no modules assigned to " + name);
                } else {
                    System.out.printf("%nProgramme - Module Code - Descriptor - Credit Units%n");
                    this.students.get(getStudentIndex(name)).getAllModules();
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public boolean checkIfModule(String name, String moduleName) {
        if (this.students.get(getStudentIndex(name)).getModules().size() > 0) {
            return this.students.
                    get(getStudentIndex(name)).
                    getModules().
                    get(this.students.
                            get(getStudentIndex(name)).
                            getModuleIndex(moduleName)
                    ).getName().
                    equals(moduleName);
        } else {
            return false;
        }
    }

    // Assessment Management & Methods
    public void assessmentManagementOptions() {
        int subChoice = 0;
        while(subChoice != 5) {
            subChoice = nextInt(String.format(
                    "%nAssessment Management Options:%n" +
                    "1. Add any number of Assessments to a Module that is assigned to a student%n" +
                    "2. Remove any Assessment in a Module assigned to a student%n" +
                    "3. Set or Remove marks from Assessment%n" +
                    "4. Display all assessment for a module taken by a student%n" +
                    "5. Return to Main Menu%n" +
                    "Enter Choice: "
            ));

            switch (subChoice) {
                case 1:
                    System.out.println();
                    addAssessments();
                    break;
                case 2:
                    System.out.println();
                    removeAssessments();
                    break;
                // case 3:
                    // System.out.println();
                    // addOrRemoveMarks();
                    // break;
                // case 4:
                    // System.out.println();
                    // displayAllStudentModelsAssessments();
                    // break;
                default:
                    if (subChoice != 5) {
                        System.out.printf("%nPlease select the correct option");
                    }
                    break;
            }
        }
    }

    public void addAssessments() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            String moduleName = getModuleName();

            System.out.printf("%nEnter Assessment's Name: ");
            String assessmentName = input.nextLine();

            System.out.print("Enter Descriptor: ");
            String descriptor = input.nextLine();

            double totalMarks = nextDouble("Enter total achievable marks: ");

            double weightage = nextDouble("Enter weightage percent: ");

            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    double totalWeightage = 0;
                    for (Assessment assessments : this.students.
                            get(getStudentIndex(name)).
                            getModules().get(getStudentIndex(moduleName)).
                            getAssessments()) {
                        totalWeightage += assessments.getWeightage();
                        }
                    if (!((weightage += totalWeightage) > 100)) {
                        this.students.
                                get(getStudentIndex(name)).
                                getModules().get(
                                        getStudentIndex(moduleName)
                                ).setAssessments(assessmentName, descriptor, totalMarks, weightage);

                        System.out.printf("%nTest - Descriptor - Total Achievable Marks - Weightage%n");
                        this.students.
                                get(getStudentIndex(name)).
                                getModules().get(getStudentIndex(moduleName)).
                                getAssessmentList(
                                        this.students.
                                        get(getStudentIndex(name)).
                                        getModules().
                                        get(getStudentIndex(moduleName)).
                                                getAssessmentIndex(assessmentName)
                                );
                        } else {
                        double maxWeightage = 100 - totalWeightage;
                        if (maxWeightage <= 0) {
                            System.out.printf("%nMaximum weightage has already been achieve,%n" +
                                    "Please remove existing assessments to add new assessments");
                        } else {
                            System.out.printf("%nThe weightage entered is not valid!%n" +
                                    "Please enter a value >= %.0f", maxWeightage);
                        }
                    }
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();

        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void removeAssessments() {
        if (this.students.size() > 0) {
            String name = getStudentName();
            String studentID = getStudentID();
            String moduleName = getModuleName();
            String assessmentName = getAssessmentName();

            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    if (checkIfAssessment(name, moduleName, assessmentName)) {
                        this.students.
                                get(getStudentIndex(name)).
                                getModules().
                                get(getStudentIndex(moduleName)).
                                getAssessments().
                                remove(getStudentIndex(assessmentName));
                    } else {
                        System.out.println("There is no assessments assign to this modules!");
                    }
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();

        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }

    public void displayStudentModAssess(String name, String studentID, String moduleName) {
        if (this.students.size() > 0) {
            if (checkIfStudent(name,studentID)) {
                if (checkIfModule(name, moduleName)) {
                    if (this.students.get(getStudentIndex(name)).
                            getModules().get(this.students.get(
                                    getStudentIndex(name)).
                                    getModuleIndex(moduleName)
                            ).getAssessments().isEmpty()) {
                        System.out.printf("There is no assessments assigned to %s", moduleName);
                    } else {
                        System.out.printf("%nTest - Descriptor - Total Achievable Marks - Weightage%n");
                        this.students.
                                get(getStudentIndex(name)).
                                getModules().
                                get(this.students.get(
                                        getStudentIndex(name)).
                                        getModuleIndex(moduleName)).
                                getAllAssessments();
                    }
                } else {
                    System.out.println("There is no modules assign to this student!");
                }
            } else {
                System.out.printf("%nStudent does not exist!");
            }
            System.out.println();

        } else {
            System.out.printf("%nThere is no student in the list!");
        }
        System.out.println();
    }


    // Check if assessment exist in Array List if so, return true if not returns false
    public boolean checkIfAssessment(String name, String moduleName, String assessmentName) {
        if (this.students.get(getStudentIndex(name)).getModules().size() > 0) {
            return this.students.
                    get(getStudentIndex(name)).
                    getModules().
                    get(this.students.
                            get(getStudentIndex(name)).
                            getModuleIndex(moduleName)).
                    getAssessments().get(this.students.
                            get(getStudentIndex(name)).
                            getModuleIndex(moduleName)).
                    getName().
                    equals(assessmentName);
        } else {
            return false;
        }
    }

    // Commonly used methods
    // Gets Student name
    public String getStudentName() {
        // Ask user for name of student
        System.out.print("Enter student's name: ");
        return input.nextLine();
    }

    // Gets Student ID
    public String getStudentID() {
        // Ask user for studentID of student
        System.out.print("Enter studentID number: ");
        return input.nextLine();
    }

    // Gets Module Name
    public String getModuleName() {
        System.out.print("Enter Module Name: ");
        return input.nextLine();
    }

    // Gets Assessment Name
    public String getAssessmentName() {
        System.out.print("Enter Assessment's Name: ");
        return input.nextLine();
    }

    // Gets the Student's Index Number in the Students Array List
    public int getStudentIndex(String name) {
        int index = 0;
        if (this.students.size() > 0){
            for (int x = 0; x < this.students.size(); x++) {
                if ((this.students.get(x).getName()).equals(name)) {
                    index = x;
                    break;
                }
            }
        }
        return index;
    }

    // Used for exception handling when user inputs a wrong data type into an Integer
    public static int nextInt(String prompt) {
        System.out.print(prompt);
        while (!input.hasNextInt()) {
            System.out.printf("Incorrect format for number%n" +
                    "Please re-enter number: ");
            input.next(); // remove and ignore next token
        }
        int x = input.nextInt();
        input.nextLine();
        return x;
    }

    // Double Version
    public static double nextDouble(String prompt) {
        System.out.print(prompt);
        while (!input.hasNextDouble()) {
            System.out.print(
                    "Incorrect format for number: please try again: ");
            input.next(); // remove and ignore next token
        }
        return input.nextDouble();
    }

    // Save Grade Tracker info into Save.txt file in src folder
    public static void save(GradeTracker gradeTracker) {
        try {
            File f = new File("./Save.txt");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gradeTracker);
            oos.close();
            fos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Load Grade Tracker info from Save.txt file in src folder
    public static GradeTracker load() {
        File f = new File("./Save.txt");
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GradeTracker gradeTracker = (GradeTracker) ois.readObject();
            System.out.println("Loaded!\n");
            return gradeTracker;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

// Old Code
/*
case 100: // Secret Menu for unused methods
                    try{
                        System.out.println("\nSecret Menu");
                        System.out.println("\nUsed for unused things");
                        double overallTotalMarks = gradeTracker.students.get(0).getModules().
                                get(0).getOverallTotalMarks();
                        System.out.println("Overall total marks for index 0: "
                                + overallTotalMarks);
                        double weightageMarks = gradeTracker.students.get(0).getModules().get(0).
                                getAssessments().get(0).getWeightageMarks();
                        System.out.println("Weightage Marks for index 0: "
                                + weightageMarks);
                    } catch (Exception exception) {
                        System.out.println("Well... Loading Failed?");
                    }
                    break;
 */
/*.
public static void save(GradeTracker) {
        try {
            File f = new File("./Save.txt");
            if (f.exists()) {
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gradeTracker);
                oos.close();
                fos.close();
                System.out.println("Saved!");
            } else {
                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gradeTracker);
                oos.close();
                fos.close();
                System.out.println("Saved!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 */