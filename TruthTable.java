package application;

import com.sun.javafx.scene.EnteredExitedHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class TruthTable {

	static boolean enteredDataFromUser = false;
	static String[] binaries = new String[16];
	
	static TableView<Row> table = new TableView<Row>();
	static TableView<Row> table1 = new TableView<Row>();

	static int[] resultFunction = new int[16];
	static int[] resultFunction1 = new int[16];
	static int inputSize;

	static ObservableList<Row> rows = FXCollections.observableArrayList(); 
	static ObservableList<Row> rows2 = FXCollections.observableArrayList(); 

	
	static void create(int inputNumber,boolean flag){
			inputSize = inputNumber;
			table.setEditable(true);
			table1.setEditable(true);	
			
			TableColumn<Row,Integer> first = new TableColumn<Row,Integer>("A");
			first.setCellValueFactory(new PropertyValueFactory<Row, Integer>("a"));
			first.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));
			first.setEditable(true);
			first.setOnEditCommit(
		            new EventHandler<CellEditEvent<Row, Integer>>() {
		                public void handle(CellEditEvent<Row, Integer> t) {
		                    ((Row) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setA(t.getNewValue());
		                }             
		           }
		     );
			
			
	        TableColumn<Row,Integer> second = new TableColumn<Row,Integer>("B");
	        second.setCellValueFactory(new PropertyValueFactory<Row,Integer>("b"));
			second.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));
	        second.setOnEditCommit(
		            new EventHandler<CellEditEvent<Row, Integer>>() {
		                public void handle(CellEditEvent<Row, Integer> t) {
		                    ((Row) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setB(t.getNewValue());
		                }
		            }
		     );
	        
	        TableColumn<Row,Integer> third = new TableColumn<Row,Integer>("C");
	        third.setCellValueFactory(new PropertyValueFactory<Row,Integer>("c"));
			third.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));
	        third.setOnEditCommit(
		            new EventHandler<CellEditEvent<Row, Integer>>() {
		                public void handle(CellEditEvent<Row, Integer> t) {
		                    ((Row) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setC(t.getNewValue());
		                }
		            }
		     );
	        
	        TableColumn<Row,Integer> four = new TableColumn<Row,Integer>("D");
	        four.setCellValueFactory(new PropertyValueFactory<Row,Integer>("d"));
			four.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));

	        four.setOnEditCommit(
		            new EventHandler<CellEditEvent<Row, Integer>>() {
		                public void handle(CellEditEvent<Row, Integer> t) {
		                    ((Row) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setD(t.getNewValue());
		                }
		            }
		     );
	        
	        TableColumn<Row,Integer> funct = new TableColumn<Row,Integer>("F");
	        funct.setCellValueFactory(new PropertyValueFactory<Row,Integer>("result"));
			funct.setCellFactory(TextFieldTableCell.<Row, Integer>forTableColumn(new IntegerStringConverter()));

	        funct.setOnEditCommit(
		            new EventHandler<CellEditEvent<Row, Integer>>() {
		                public void handle(CellEditEvent<Row, Integer> t) {
		                    ((Row) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setResult(t.getNewValue());
		                    enteredDataFromUser = true;
		                    resultFunction[t.getTablePosition().getRow()] = t.getTableView().getItems().get(
			                        t.getTablePosition().getRow()).getResult();
		                }
		            }
		     );
	        
	        if (inputNumber == 4){
	        	if(flag==false){
		        	table.getColumns().clear();
		        	table.getColumns().addAll(first,second,third,four,funct);
	        	}else{
		        	table1.getColumns().clear();
		        	table1.getColumns().addAll(first,second,third,four,funct);
	        	}
	        	first.setCellFactory(first.getCellFactory());
	            
	        }
	        else if (inputNumber == 3){
	        	if(flag==false){
		        	table.getColumns().clear();
		        	table.getColumns().addAll(first,second,third,funct);
	        	}else{
		        	table1.getColumns().clear();
		        	table1.getColumns().addAll(first,second,third,funct);
	        	}
	        }
	        else {
	        	if(flag==false){
		        	table.getColumns().clear();
		        	table.getColumns().addAll(first,second,funct);
	        	}else{
		        	table1.getColumns().clear();
		        	table1.getColumns().addAll(first,second,funct);
	        	}
	        }
			
	        
	        assignRows(inputNumber,flag);
	        if(flag==false)
	        table.getItems().setAll(rows);
	        else
	        table1.getItems().setAll(rows2);
        
	}
	
	static void assignRows(int inputNumber,boolean flag){
		
		int count = 0;
		for (int a = 0; a < 2; a++){
				for (int b = 0; b < 2; b++){
					if (inputNumber != 2){
						for (int c = 0; c < 2; c++){
							if (inputNumber != 3){
								for (int d = 0; d < 2; d++){
									if(flag==false){
										rows.add(new Row(a,b,c,d,resultFunction[count]));
										binaries[count] = a + "" + b + "" + c + "" + d;
									}
									else
										rows2.add(new Row(a,b,c,d,resultFunction1[count]));
									count++;
								}
							}
							else {
								if(flag==false){
									rows.add(new Row(a,b,c,0,resultFunction[count]));
									binaries[count] = a + "" + b + "" + c;
								}
								else
									rows2.add(new Row(a,b,c,0,resultFunction1[count]));
								count++;
							}
						}
					}
					else {
						if(flag==false){
							rows.add(new Row(a,b,0,0,resultFunction[count]));
							binaries[count] = a + "" + b;
						}
						else
							rows2.add(new Row(a,b,0,0,resultFunction1[count]));
						count++;
					}
				}
			}
		}
	
	
	
}
