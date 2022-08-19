import java.io.*;
import java.util.ArrayList;

public class ModListSaveFile extends Module{
    private ArrayList<Module> modules = new ArrayList<>();

    public ModListSaveFile() {
        try {
            loadMod();
        } catch (RuntimeException e) {
            System.out.println("Empty Module List");
        }
    }

    public void loadMod() {
        try {
            File f = new File("./ModSave.txt");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Module> modules = (ArrayList<Module>) ois.readObject();
            System.out.println("Loaded!\n");
            this.modules = modules;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveModList() {
        File f = new File("./ModSave.txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.modules);
            oos.close();
            fos.close();
            System.out.println("Saved!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetModList() {
        try {
            File f = new File("./ModSave.txt");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject("");
            oos.close();
            fos.close();
            System.out.println("Module List Reset!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void removeModules(int index) {
        this.modules.remove(index);
    }

    public void setModules(String name, String moduleCode, String description, int creditUnits) {
        this.modules.add(new Module(name, moduleCode, description, creditUnits));
    }
}
