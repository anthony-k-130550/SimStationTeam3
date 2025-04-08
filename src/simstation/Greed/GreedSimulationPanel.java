package simstation.Greed;
import simstation.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GreedSimulationPanel extends WorldPanel implements ChangeListener {
    JSlider greed;
    JSlider growBackRate;
    JSlider moveEnergy;

    public GreedSimulationPanel(WorldFactory factory) {
        super(factory);

        //my buffers so it looks nice
        JPanel leftBuffer = new JPanel();
        leftBuffer.setPreferredSize(new Dimension(100,500));
        leftBuffer.setOpaque(false);

        JPanel rightBuffer = new JPanel();
        rightBuffer.setPreferredSize(new Dimension(100,500));
        rightBuffer.setOpaque(false);

        JPanel bottomBuffer = new JPanel();
        bottomBuffer.setPreferredSize(new Dimension(500,150));
        bottomBuffer.setOpaque(false);

        JPanel sliders = new JPanel();
        sliders.setBackground(Color.CYAN);
        sliders.setLayout(new GridLayout(6,1));

        JLabel greedText = new JLabel("Greed:");
        greedText.setOpaque(false);
        greedText.setHorizontalAlignment(JLabel.CENTER);
        sliders.add(greedText);

        greed = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
        greed.setMajorTickSpacing(10);
        greed.setMinorTickSpacing(1);
        greed.setPaintTicks(true);
        greed.setPaintLabels(true);
        greed.setSnapToTicks(true);
        greed.addChangeListener(this);
        sliders.add(greed);

        JLabel growBackText = new JLabel("GrowBack Rate: ");
        growBackText.setOpaque(false);
        growBackText.setHorizontalAlignment(JLabel.CENTER);
        sliders.add(growBackText);

        growBackRate = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
        growBackRate.setMajorTickSpacing(10);
        growBackRate.setMinorTickSpacing(1);
        growBackRate.setPaintTicks(true);
        growBackRate.setPaintLabels(true);
        growBackRate.setSnapToTicks(true);
        growBackRate.addChangeListener(this);
        sliders.add(growBackRate);

        JLabel moveEnergyText = new JLabel("Move Energy: ");
        moveEnergyText.setOpaque(false);
        moveEnergyText.setHorizontalAlignment(JLabel.CENTER);
        sliders.add(moveEnergyText);

        moveEnergy = new JSlider(JSlider.HORIZONTAL, 0, 50, 10);
        moveEnergy.setMajorTickSpacing(10);
        moveEnergy.setMinorTickSpacing(1);
        moveEnergy.setPaintTicks(true);
        moveEnergy.setPaintLabels(true);
        moveEnergy.setSnapToTicks(true);
        moveEnergy.addChangeListener(this);
        sliders.add(moveEnergy);

        controlPanel.add(leftBuffer, BorderLayout.WEST);
        controlPanel.add(rightBuffer, BorderLayout.EAST);
        controlPanel.add(bottomBuffer, BorderLayout.SOUTH);
        controlPanel.add(sliders, BorderLayout.CENTER);
    }

    public void stateChanged(ChangeEvent e) {
        try {
            if (e.getSource() == greed) {
                int greedNumber = greed.getValue();
                Cow.greediness = greedNumber;
            } else if (e.getSource() == growBackRate) {
                int growNumber = growBackRate.getValue();
                Patch.growBackRate = growNumber;
            } else if (e.getSource() == moveEnergy) {
                int moveCost = moveEnergy.getValue();

                if (!(model instanceof Meadow)) {
                    throw new IllegalArgumentException("Model must be instance of Meadow!!");
                }
                Meadow m = (Meadow) model;
                m.setMoveEnergy(moveCost);
            }
        } catch (Exception f) {
            handleException(f);
        }

    }
}
