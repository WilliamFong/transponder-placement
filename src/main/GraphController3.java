package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

public class GraphController3 implements Initializable {
	private StringBuilder resultsText = new StringBuilder();
	private Scanner scanner;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Text results;
    
    @FXML
    private LineChart<?,?> resultsGraph;
    
    @FXML
    private NumberAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String path = new File(".").getCanonicalPath().concat("/src/results/");
			File mostRecentFile = getMostRecentFile(new File(path).listFiles());
			setResultsData(mostRecentFile);
			setResultsGraph(mostRecentFile);
		} catch (Exception e) {
		}
    }
    
    private File getMostRecentFile(File[] csvFiles) {
		File mostRecent = csvFiles[0];
		if(csvFiles.length==1) 
			return mostRecent;
		
		else {
			
			for(int i =0; i < csvFiles.length; i++) {
				if(mostRecent.lastModified() < csvFiles[i].lastModified()) {
					if (csvFiles[i].getName().contains("utilization")) {
						mostRecent = csvFiles[i];
					}
				}
			}
		}
		return mostRecent;
	}
	
	private ArrayList<String[]> readableFile(File read) throws FileNotFoundException {
		scanner = new Scanner(read);
		ArrayList<String[]> file = new ArrayList<>();
		while(scanner.hasNext()) {
			
			String[] line = scanner.nextLine().split(",");
			file.add(line);
		}	
		return file;
	}
	
	private void setResultsData(File file) throws IOException {
		ArrayList<String[]> fileArray = readableFile(file);
		for(int i =0; i<fileArray.size();i++) {
			for(int j=0; j< fileArray.get(i).length; j++) {
				if(i==0)
					resultsText.append(fileArray.get(i)[j] + "	");
				else 
					resultsText.append(fileArray.get(i)[j] + "				");
			}
			resultsText.append("\n");
		}
		results.setText(resultsText.toString());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setResultsGraph(File file) throws IOException {
		ArrayList<String[]> fileArray = readableFile(file);
		x.setLabel("Bandwidth");
		y.setLabel("Utilization");
		XYChart.Series series = new XYChart.Series();
		series.setName(fileArray.get(0)[0]);
		XYChart.Series series2 = new XYChart.Series();
		series2.setName(fileArray.get(13)[0]);
		XYChart.Series series3 = new XYChart.Series();
		series3.setName(fileArray.get(26)[0]);
		XYChart.Series series4 = new XYChart.Series();
		series4.setName(fileArray.get(39)[0]);
		
		for (int i = 0; i < fileArray.size(); i++) {
			if (i >= 2 && i <= 12) {
				series.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(i)[0]), Integer.parseInt(fileArray.get(i)[1])));
			}
			else if (i >= 15 && i <= 25) {
				series2.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(i)[0]), Integer.parseInt(fileArray.get(i)[1])));
			}
			else if (i >= 28 && i <= 38) {
				series3.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(i)[0]), Integer.parseInt(fileArray.get(i)[1])));
			}
			else if (i >= 41) {
				series4.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(i)[0]), Integer.parseInt(fileArray.get(i)[1])));
			}
		}
		
//		int counter = 0;
//			for(int i = 0; i < 4; i++) {
//				counter++;
//				counter++;
//				for (int j = 0; j < 11; j++) {
//					if (i == 0) {
//						series.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(counter)[0]), Integer.parseInt(fileArray.get(counter)[1])));
//					}
//					else if (i == 1) {
//						series2.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(counter)[0]), Integer.parseInt(fileArray.get(counter)[1])));
//					}
//					else if (i == 2) {
//						series3.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(counter)[0]), Integer.parseInt(fileArray.get(counter)[1])));
//					}
//					else if (i == 3) {
//						series4.getData().add(new XYChart.Data(Integer.parseInt(fileArray.get(counter)[0]), Integer.parseInt(fileArray.get(counter)[1])));
//					}
//					counter++;
//				}
//			}

	
		resultsGraph.getData().addAll(series, series2, series3, series4);
	}
}
