package simstation;

import mvc.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WorldPanel extends AppPanel
{
    public JPanel threadPanel = new JPanel();

    public WorldPanel(WorldFactory factory)
    {
        super(factory);

        threadPanel.setLayout(new GridLayout(1, 5));
        threadPanel.setOpaque(false);

        JPanel p = new JPanel();
        p.setOpaque(false);
        JButton button = new JButton("Start");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Pause");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Resume");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Stop");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        p = new JPanel();
        p.setOpaque(false);
        button = new JButton("Stats");
        button.addActionListener(this);
        p.add(button);
        threadPanel.add(p);

        controlPanel.setLayout(new BorderLayout());

        p = new JPanel();
        p.setOpaque(false);
        p.add(threadPanel);

        controlPanel.add(p,  BorderLayout.NORTH);
    }

    public void setModel(Model m) {
        super.setModel(m);
        World w = (World)m;
        //w.startAgents(); I want to start manually
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String cmmd = ae.getActionCommand();

            if (cmmd.equals("Save")) {
                World m = (World) model;
                if (m.hasStarted()) { //insist that the model has started according to assignment
                    throw new IllegalThreadStateException("Threads have already started, cannot be saved");
                }
                Utilities.save(model, false);
            } else if (cmmd.equals("SaveAs")) {
                World m = (World) model;
                if (m.hasStarted()) { //insist that the model has started according to assignment
                    throw new IllegalThreadStateException("Threads have already started, cannot be saved");
                }
                Utilities.save(model, true);
            } else if (cmmd.equals("Open")) {
                Model newModel = Utilities.open(model);
                if (newModel != null) {
                    World world = (World) newModel;
                    world.rethread();
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

}
