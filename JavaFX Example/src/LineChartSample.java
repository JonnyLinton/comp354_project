import java.io.*;
import java.util.Scanner;
import java.lang.*;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.awt.font.NumericShaper;


public class LineChartSample extends Application {


    public void start(Stage stage)
    {
        stage.setTitle("Line Chart Sample for IT1");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Daily stock values");

        final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("One stock over several years");

        lineChart.setCreateSymbols(false);

        XYChart.Series series = new XYChart.Series();
        series.setName("Stock data");


        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Other stock data");

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("Other stock data");

        XYChart.Series series5 = new XYChart.Series();
        series5.setName("Other stock data");



        try {
            BufferedReader br = new BufferedReader(new FileReader("Sample data.csv"));
            br.readLine();
            String line;
            int i = 0;

            while ((line = br.readLine()) != null && i < 1000)
            {
                String[] cols = line.split(",");
               // System.out.println("date: " + cols[0] + " Price: " + cols[6]);


                series.getData().add(new XYChart.Data(cols[0], Float.parseFloat(cols[6])));
                series3.getData().add(new XYChart.Data(cols[0], Float.parseFloat(cols[4])));
                series4.getData().add(new XYChart.Data(cols[0], Float.parseFloat(cols[3])));
                series5.getData().add(new XYChart.Data(cols[0], Float.parseFloat(cols[2])));

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(lineChart, 1800, 900);
        scene.getStylesheets().add("style.css");
        lineChart.getData().add(series);
        lineChart.getData().add(series3);
        lineChart.getData().add(series4);
        lineChart.getData().add(series5);





        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {


        launch(args);
        try{
            getDataFromCSV();
        }
        catch (IOException e)  {

            e.printStackTrace();
        }

    }

    public static void getDataFromCSV() throws IOException
    {



//           File stockData = new File("Sample data.csv");
//           Scanner scanner = new Scanner(stockData);
//            while(scanner.hasNext())
//            {
//                System.out.println(scanner.next() + ",");
//            }
//
//            scanner.close();






    }
}
