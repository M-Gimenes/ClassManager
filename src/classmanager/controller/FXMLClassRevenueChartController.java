package classmanager.controller;

import classmanager.Main;
import classmanager.model.dao.LessonStudentDAO;
import classmanager.model.domain.ClassTotalValue;
import classmanager.util.ViewPaths;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class FXMLClassRevenueChartController {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Button buttonBack;

    private List<ClassTotalValue> data;

    public void initialize() {
        loadChart();
    }

    private void loadChart() {
        data = LessonStudentDAO.getInstance().getTotalReceivedPerClass();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Recebido (R$)");

        for (ClassTotalValue item : data) {
            series.getData().add(new XYChart.Data<>(item.getClassName(), item.getTotal()));
        }   

        barChart.getData().clear();
        barChart.getData().add(series);
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }
}
