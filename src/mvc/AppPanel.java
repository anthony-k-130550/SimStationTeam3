package mvc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// AppPanel is the MVC controller
public class AppPanel extends JPanel implements Subscriber, ActionListener  {

    protected Model model;
    protected AppFactory factory;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;
    public static int FRAME_WIDTH = 1000;
    public static int FRAME_HEIGHT = 500;

    public AppPanel(AppFactory factory) {

        // initialize fields here
        this.factory = factory;
        this.model = factory.makeModel();
        this.view = factory.makeView(model);
        controlPanel = new JPanel();
        view.setBackground(Color.GRAY);
        controlPanel.setBackground(Color.CYAN);

        model.subscribe(this);

        this.setLayout(new GridLayout(1,2));
        this.add(controlPanel);
        this.add(view);

        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); //this allows the AppPanel to be perfectly 1000 by 500 instead of a few pixels short
    }


    public void display() {
        frame.setVisible(true);
        frame.pack();
    }

    public void update() {  /* override in extensions if needed */ }

    public Model getModel() { return model; }

    // called by file/open and file/new
    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        // view must also unsubscribe then resubscribe:
        view.setModel(this.model);
        model.changed();
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New",  "Save", "SaveAs", "Open", "Quit"}, this);
        result.add(fileMenu);

        JMenu editMenu =
                Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);

        JMenu helpMenu =
                Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);

        return result;
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String cmmd = ae.getActionCommand();

            if (cmmd.equals("Save")) {
                Utilities.save(model, false);
            } else if (cmmd.equals("SaveAs")) {
                Utilities.save(model, true);
            } else if (cmmd.equals("Open")) {
                Model newModel = Utilities.open(model);
                if (newModel != null) {
                    setModel(newModel);
                    model.setUnsavedChanges(false);
                }
            } else if (cmmd.equals("New")) { //tweaked original implementation of new
                if (model.getUnsavedChanges()) {
                    boolean confirm = Utilities.confirm("current model has unsaved changes, continue?");
                    if (confirm) {
                        setModel(factory.makeModel());
                        model.setUnsavedChanges(false);
                    } //if user says no, do nothing.
                } else { //there are no new changes to save, go ahead with the new model
                    setModel(factory.makeModel());
                    // needed cuz setModel sets to true:
                    model.setUnsavedChanges(false);
                }
            } else if (cmmd.equals("Quit")) {
                Utilities.saveChanges(model);
                System.exit(0);
            } else if (cmmd.equals("About")) {
                Utilities.inform(factory.about());
            } else if (cmmd.equals("Help")) {
                Utilities.inform(factory.getHelp());
            } else {
                Command command = factory.makeEditCommand(model, cmmd, controlPanel);
                if (command != null) {
                    command.execute();
                } else {
                    throw new Exception("Unknown command: " + cmmd);
                }

            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    protected void handleException(Exception e) {
        Utilities.error(e);
    }
}
