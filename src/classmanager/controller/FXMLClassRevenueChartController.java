package classmanager.controller;

import classmanager.model.dao.LessonStudentDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class FXMLClassRevenueChartController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        loadChart();
    }

    private void loadChart() {
        Map<String, Double> data = LessonStudentDAO.getInstance().getTotalReceivedPerClass();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Recebido (R$)");

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
