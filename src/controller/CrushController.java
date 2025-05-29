package controller;

import model.CrushDAO;
import view.CrushAppGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CrushController {
    private CrushDAO dao;
    private CrushAppGUI gui;

    public CrushController(CrushDAO dao, CrushAppGUI gui) {
        this.dao = dao;
        this.gui = gui;
        gui.addPredictButtonListener(new PredictButtonListener());
    }

    class PredictButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String yourName = gui.getYourName();
            String input = gui.getCrushNames();

            if (yourName.isEmpty() || input.isEmpty()) {
                gui.showErrorMessage("Please fill both fields.");
                return;
            }

            gui.setStatusText("Generating predictions...");

            String[] crushes = input.split(",");
            StringBuilder resultBuilder = new StringBuilder("<html>");

            for (String name : crushes) {
                String crushName = name.trim();
                int percentage = dao.generateMatchPercentage();
                String message = dao.getCrushMessage(percentage);

                try {
                    dao.savePrediction(yourName, crushName, percentage, message);
                    resultBuilder.append(crushName)
                                 .append(": ")
                                 .append(percentage)
                                 .append("% → ")
                                 .append(message)
                                 .append("<br>");
                } catch (SQLException ex) {
                    gui.showErrorMessage("Error saving prediction: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            resultBuilder.append("</html>");
            gui.displayResults(resultBuilder);
            gui.setStatusText("Predictions complete!");
        }
    }
}