package classmanager.controller;

import classmanager.Main;
import classmanager.model.dao.LessonDAO;
import classmanager.util.ViewPaths;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.Map;
import javafx.event.ActionEvent;

public class FXMLClassLessonChartController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        loadChart();
    }

    private void loadChart() {
        Map<String, Integer> data = LessonDAO.getInstance().getLessonCountPerClass();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Aulas Ministradas");

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }
}
