package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.IntegerStringConverter;
import javafx.stage.Stage;

public class FileOperation {
	int j = 0;
	Boolean flag;
	String imagePath = "file:C:/Users/izel/Desktop/icons/";
	VBox vbBase;
	VBox vbKarno;
    HBox hbTruthTable;
    VBox vbox;

	FileChooser fileChooser = new FileChooser();
	
	Button btnExit;
	Button selectFile;
	Button readFile;
	Button resetManual;
	Button reset;
	Button edit;
	
	TextField pathText;
	TextField tvQuestion;
	
	TextArea sop;
	TextField sopManual;
	TextArea simplifyManual;
    TextArea simplifyFileExpression;
	
	TextArea tvReadTT;
	Button simplify;
	Button Manual;
	Stage primaryStage;
	
	public FileOperation(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public VBox getVBox(){
		return vbBase;
	}
	public void createObjects(){
		fileChooser.getExtensionFilters().addAll( 
				new ExtensionFilter("tt file", "*tt"),
				new ExtensionFilter("be file", "*be")
		);
		
		Image img = new Image(imagePath + "exit.png", 35,35,true,true);
		
		btnExit = new Button("EXIT",new ImageView(img));
		btnExit.setOnAction(myHandler);
		
		HBox exitHb = new HBox();
		exitHb.setAlignment(Pos.TOP_LEFT);
		exitHb.getChildren().add(btnExit);
		
		img = new Image(imagePath + "icnSelect.png", 35,35,true,true);
		selectFile = new Button("Select File",new ImageView(img));
		selectFile.setOnAction(myHandler);
		selectFile.getStyleClass().add("btnSelect");

		pathText = new TextField();
		pathText.setPromptText("The path which you will choose!");
		pathText.setEditable(false);
		pathText.setAlignment(Pos.CENTER);
		pathText.getStyleClass().add("text");
		
		img = new Image(imagePath + "icnRead.png", 35,35,true,true);
		readFile = new Button("Read File",new ImageView(img));
		readFile.setOnAction(myHandler);
		readFile.getStyleClass().add("btnSelect");
		
		VBox vb = new VBox();
		vb.getStyleClass().add("vboxTop");
		vb.setSpacing(10);
		vb.setPadding(new Insets(10,0, 10, 0));
		vb.getChildren().addAll(exitHb,selectFile,pathText,readFile);
		
		tvQuestion = new TextField();
		tvQuestion.setEditable(false);
		tvQuestion.setAlignment(Pos.CENTER);
		tvQuestion.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		tvQuestion.setStyle("-fx-text-inner-color: green;");
		tvQuestion.getStyleClass().add("text");
		
        tvReadTT = new TextArea();
        tvReadTT.setMaxWidth(80);
        
        vbox = new VBox();
        hbTruthTable = new HBox();
        simplifyFileExpression = new TextArea();
        simplifyFileExpression.setPromptText("Simplified expression");
        simplifyFileExpression.setMaxWidth(300);
        TruthTable.table.setEditable(true);
        hbTruthTable.getChildren().addAll(tvReadTT,TruthTable.table);
        hbTruthTable.setAlignment(Pos.CENTER_LEFT);
        vbox.getChildren().addAll(hbTruthTable,simplifyFileExpression);
        
		vbKarno = new VBox();
		simplify = new Button("Simplify");
		simplify.setOnAction(myHandler);
        sop = new TextArea();
        sop.setPromptText("Simplified SOP Expression");
		vbKarno.setAlignment(Pos.CENTER);
		//vbKarno.getChildren().addAll(KMap.createKMap(),simplify,sop);
		vbKarno.getChildren().addAll(simplify,sop);
		
		VBox vbManual = new VBox();
		resetManual = new Button ("Reset");
		resetManual.setOnAction(myHandler);
		Manual = new Button("Simplify");
		Manual.setOnAction(myHandler);
		sopManual = new TextField();
		sopManual.setPromptText("Please enter your expression");
		
		simplifyManual = new TextArea();
		simplifyManual.setPromptText("Simplified boolean expression");
		simplifyManual.setPrefWidth(250);
		simplifyManual.setPrefHeight(50);
		sopManual.setPrefWidth(250);
		sopManual.setPrefHeight(50);
		vbManual.setAlignment(Pos.CENTER_RIGHT);
		vbManual.getChildren().addAll(resetManual,TruthTable.table1,sopManual,Manual,simplifyManual);
		
		HBox results = new HBox();
		results.setAlignment(Pos.CENTER);
		results.setSpacing(30);
		results.getChildren().addAll(vbox,vbKarno,vbManual);
        
		VBox vb2Radio = new VBox();
		vb2Radio.setSpacing(5);
		vb2Radio.setPadding(new Insets(10, 0, 0, 10));
		vb2Radio.setAlignment(Pos.TOP_CENTER);
		vb2Radio.getChildren().addAll(vb,tvQuestion);
		
		vbBase = new VBox();
		edit = new Button (" Edit");
		reset = new Button ("Reset");
		edit.setOnAction(myHandler);
		reset.setOnAction(myHandler);
		vbBase.getChildren().addAll(vb2Radio,edit,results,reset);
		
	}
	
	public String getSopFormFromTable(){
		String sopForm = "F = ";
		
		for (int k = 0; k < TruthTable.binaries.length; k++){
			
			if (TruthTable.binaries[k] != null && TruthTable.resultFunction[k] == 1){
				int charVar = 0;
				for (int t = 0; t < TruthTable.binaries[k].length(); t++){
					
					if (TruthTable.binaries[k].charAt(t) == '0')
						sopForm += (char)(charVar+65) + "'";
					else
						sopForm += (char)(charVar+65);
					charVar++;
					
					if (t == TruthTable.binaries[k].length()-1){
						if (k+1 < TruthTable.binaries.length && TruthTable.binaries[k+1] != null)
							sopForm += "+";
					}
					else {
						sopForm += ".";
					}
				}
			}
			
		}
		if (sopForm.charAt(sopForm.length()-1) == '+'){
			StringBuilder sb = new StringBuilder(sopForm);
			sb.setCharAt(sopForm.length()-1, ' ');
			sopForm = sb.toString();
		}
			
		return sopForm;
	}
	
	final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>(){
	    public void handle(ActionEvent event) {
	        if (event.getSource() == selectFile){
	        	File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null){
					tvReadTT.setText("");
					pathText.setText(file.getAbsolutePath());					
				}
	        }
	        else if (event.getSource() == readFile){
	        	TruthTable.binaries = new String[16];
				TruthTable.resultFunction = new int[16];
				TruthTable.rows.clear();
	        	tvQuestion.setText("Reading is successfully completed. Now, how do you want to see all results?");
	        	FileReader fileReader;
				try {
					fileReader = new FileReader(pathText.getText());
					BufferedReader br = new BufferedReader(fileReader);
					Simplification opt = new Simplification();
					String line;
					int i = 0;
					boolean isTTFile = true;
					KMap kMap = new KMap();
					while ((line = br.readLine()) != null){
						if (line.charAt(0) == 'F'){
							isTTFile = false;
							sop.setText(line);
							kMap.setSop(sop.getText());
							System.out.println(sop.getText());
							for (int j = 1; j >= 0; j--){
								if (sop.getText().indexOf((char)(67+j)) != -1){ // d var mý?
									kMap.inputSize = j+3; break;	
								}
								else
									kMap.inputSize = 2;
							}
							vbKarno.getChildren().addAll(kMap.simplification());
							sop.setText(sop.getText() + "\n" + "Result of Simplification with Karno: " + kMap.giveResult() + "\n");
							
							
						}else {
							tvReadTT.setText(tvReadTT.getText() + line + "\n");
							TruthTable.resultFunction[i]=Character.getNumericValue(line.charAt(line.length()-1));
							i++;
						}
					}						
					br.close();
					if (isTTFile){
						flag=false;
						j=i;
						TruthTable.create((int)(Math.log10(i)/Math.log10(2)),flag);
						kMap.inputSize = TruthTable.inputSize;
						kMap.setSop(getSopFormFromTable());
						vbKarno.getChildren().addAll(kMap.simplification());
						simplifyManual.setText(opt.simplify(getSopFormFromTable()));
						simplifyFileExpression.setText(sop.getText() + "\n" + "Result of Simplification: " + kMap.giveResult() + "\n");
						System.out.println(sop.getText());
						sop.setText(sop.getText() + "\n" + "Result of Simplification: " + kMap.giveResult() + "\n");
					}
					
					
				}catch (IOException e) {
					e.printStackTrace();
				}	        	
	        }
	        else if (event.getSource() == simplify){
	        	if (TruthTable.enteredDataFromUser){
	        		sop.setText("");
	        		vbKarno.getChildren().remove(2);
	        		KMap kMap = new KMap();
		        	kMap.inputSize = TruthTable.inputSize;
		        	System.out.println(getSopFormFromTable());
					kMap.setSop(getSopFormFromTable());
					vbKarno.getChildren().addAll(kMap.simplification());
					sop.setText(sop.getText() + "\n" + "Result of Simplification with Karno: " + kMap.giveResult() + "\n");
					TruthTable.enteredDataFromUser = false;
	        	}
	        }
	        
	        
	        else if(event.getSource() == reset){
	        	tvReadTT.setText("");
	        	tvQuestion.setText("");
	        	pathText.setText("");
	        	sop.setText("");
        		vbKarno.getChildren().remove(2);
    			TruthTable.table.getColumns().clear();
	    		
	        }
	        else if(event.getSource() == resetManual){
	        	sopManual.setText("");
	    		simplifyManual.setText("");
    			TruthTable.table1.getColumns().clear();
    			TruthTable.rows2.clear();
				TruthTable.resultFunction1 = new int[16];

	        }
	        else if (event.getSource() == Manual){
	        	TruthTable.rows2.clear();
	        	Simplification opt = new Simplification();
				simplifyManual.setText(opt.simplify(sopManual.getText()));
				int inputSize = opt.nodesPart1[0].getBinaryRep().length();
				
				if(!(simplifyManual.getText().equalsIgnoreCase(""))){
					
	        		String result = simplifyManual.getText();
	        		String literals = "";
	        		
	        		for (int i = 0; i < inputSize; i++){
	        			if(result.indexOf(String.valueOf((char)(i+65))) != -1){
        					literals += (char)(i+65);
	        			}
	        		}
	        		
	        		int len = opt.numberOfElements(opt.nodesPart1);
	        		
	        		if (literals.length() == inputSize){
	        			for (int i = 0; i < len; i++){
		        			int row = Integer.parseInt(opt.nodesPart1[i].getBinaryRep(), 2);
		        			TruthTable.resultFunction1[row] = 1;
	        			}
		        		TruthTable.create(inputSize, true);
	        		}
	        		else 
	        			createTableHere(literals,result);
	        		
		        }

	        }
	        else if (event.getSource() == edit){
	        	//String newText=TruthTable.TruthTableToExpression((int)(Math.log10(j)/Math.log10(2)));
	        	//tvReadTT.setText(newText);
	        }
	        else if (event.getSource() == btnExit){
	        	primaryStage.close();
	        }
	    }

		private void createTableHere(String literals,String result) {
						
			TruthTable.rows2.clear();
			TruthTable.table1.getColumns().clear();
			TruthTable.resultFunction1 = new int[(int) Math.pow(2, literals.length())];
			
			for (int i = 0; i < literals.length(); i++){
				String colName = String.valueOf(literals.charAt(i));
				
				TableColumn<Row,Integer> first = new TableColumn<Row,Integer>(colName);
				first.setCellValueFactory(new PropertyValueFactory<Row, Integer>(String.valueOf((char)(i+65)).toLowerCase()));
				first.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));
				
				TruthTable.table1.getColumns().add(first);
				
			}
			
			TableColumn<Row,Integer> funct = new TableColumn<Row,Integer>("F");
	        funct.setCellValueFactory(new PropertyValueFactory<Row,Integer>("result"));
			funct.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));
			funct.setOnEditCommit(
		            new EventHandler<CellEditEvent<Row, Integer>>() {
		                public void handle(CellEditEvent<Row, Integer> t) {
		                    ((Row) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setResult(t.getNewValue());
		                }
		            }
		     );
			TruthTable.table1.getColumns().add(funct);

			 if (literals.length() != 1)
	    	 	TruthTable.assignRows(literals.length(), true);
	    	 for (int i = 0; i < TruthTable.resultFunction1.length; i++)
	    		 TruthTable.resultFunction1[i] = 0;
	    	 
	    	 String[] terms = result.split("\\+");
	    	 
	    	 for (int i = 0; i < terms.length; i++){
	    		 String[] lit = terms[i].trim().split("\\.");
	    		 StringBuilder sb = new StringBuilder("");
    			 int count = 0;
	    		 for (int j = 0; j < literals.length(); j++){
				 	if (terms[i].indexOf(String.valueOf(literals.charAt(j))) != -1){
    					 if (lit[count].length() == 2)
    						 sb.append("0");
    					 else
    						 sb.append("1");
    					 count++;
    				 }
    				 else
    					 sb.append("X");
	    		 }
	    		 int row;
	    		 int index = sb.toString().indexOf("X");
	    		 if (index != -1){
	    			 sb.setCharAt(index, '0');
	    			 row = Integer.parseInt(sb.toString(), 2);
	    			 TruthTable.resultFunction1[row] = 1;
	    			 
	    			 sb.setCharAt(index, '1');
	    			 row = Integer.parseInt(sb.toString(), 2);
	    			 TruthTable.resultFunction1[row] = 1;
	    		 }
	    		 else{
	    			 row = Integer.parseInt(sb.toString(), 2);
    			 	 TruthTable.resultFunction1[row] = 1;
	    		 }
	    	 }
	    	 
    		 TruthTable.rows2.clear(); 

	    	 if (literals.length() == 1){
	    		for (int i = 0; i < 2; i++){
	    			TruthTable.rows2.add(new Row(i,0,0,0,TruthTable.resultFunction1[i]));
	    		}
	    	 }
	    	 else {
		    	TruthTable.assignRows(literals.length(), true);
	    	 }
	    	 
			 TruthTable.table1.getItems().setAll(TruthTable.rows2);
		}

	};
}

	
	

