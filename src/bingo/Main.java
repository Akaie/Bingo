package bingo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Random random = new Random();
        Button newNumber = new Button("Next Number");
        Button clearBoard = new Button("Clear Board");
        GridPane r = new GridPane();
        r.setAlignment(Pos.TOP_CENTER);
        r.setPadding(new Insets(20,20,20,20));
        r.setHgap(10);
        GridPane t = new GridPane();
        t.add(newNumber, 0 , 0);
        t.add(clearBoard, 1, 0);
        t.setAlignment(Pos.CENTER);
        GridPane newnum = new GridPane();
        newnum.add(new Label("Called Number: "), 0, 0);
        Label nextnum = new Label(" ");
        newnum.add(nextnum, 1, 0);
        newnum.setAlignment(Pos.CENTER);
        GridPane g = new GridPane();
        g.add(r, 0, 0);
        g.add(newnum, 0, 1);
        g.add(t, 0, 2);
        g.setAlignment(Pos.CENTER);
        Label[] l = new Label[75];
        boolean[] track = new boolean[75];
        String[] letters = {"B", "I","N","G","O"};
        for(int i = 0; i < l.length; i++) {
        	l[i] = new Label("    ");
        }
        try {
        	File f = new File("numbers.dat");
        	if(f.exists()) {
	        	FileReader fr = new FileReader(f);
	        	BufferedReader br = new BufferedReader(fr);
	        	String line = br.readLine();
	        	while(line != null && !line.equalsIgnoreCase(" ") && !line.equalsIgnoreCase("")) {
	        		int number = Integer.parseInt(line);
	        		l[number-1].setText(line);
	        		track[number-1] = true;
	        		line = br.readLine();
	        	}
	        	br.close();
        	}
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        for(int i = 0; i < l.length; i++) {
        	if(i%15 == 0) {
        		Label letterlabel = new Label(letters[i/15]);
        		letterlabel.setStyle("-fx-font-weight: bold");
        		r.add(letterlabel, 0, i/15);
        	}
        	r.add(l[i], i%15+1, i/15, 1, 1);
        }

        EventHandler<ActionEvent> newNumberEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                int num = random.nextInt(75);
                boolean all = true;
                for(int i = 0; i < track.length; i++) {
                	all = track[i] && all;
                }
                while(track[num] == true && !all)
                	num = random.nextInt(75);
                track[num] = true;
                if(num<9)
                	l[num].setText("0"+(num+1));
                else
                	l[num].setText(""+(num+1));
                if(!all)
	                try {
	                	nextnum.setText(""+(num+1));
	                	File f = new File("numbers.dat");
						FileWriter fw = new FileWriter(f, true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.append(""+(num+1)+"\n");
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            } 
        };
        newNumber.setOnAction(newNumberEvent);
        EventHandler<ActionEvent> clearEvent = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                Alert a = new Alert(AlertType.CONFIRMATION);
                a.setTitle("Confirmation");
                a.setHeaderText(null);
                a.setContentText("Are you sure you want to wipe this game? This can't be undone!");
                Optional<ButtonType> result = a.showAndWait();
                if(result.get() == ButtonType.OK) {
                	for(int i = 0; i < l.length; i++) {
                    	l[i].setText("    ");
                    	track[i] = false;
                    }
                	try {
                	File f = new File("numbers.dat");
					FileWriter fw = new FileWriter(f);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.close();
                	}
                	catch(Exception ee) {
                		
                	}
                	nextnum.setText(" ");
                }  
            } 
        };
        clearBoard.setOnAction(clearEvent);
        Scene sc = new Scene(g, 420,250);
        primaryStage.setTitle("Bingo");
        primaryStage.setScene(sc);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
