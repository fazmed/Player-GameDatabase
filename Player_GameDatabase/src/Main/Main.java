package Main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


 
public class Main extends Application 
{
		Object[][] data = null;
		String fName = null;
		String lName = null;
		String address = null;
		String province = null;
		String pCode = null;
		String pNumber = null;
		
		int pId = 0;
		
		String playerGameId;
		String gameId;
		String gTitle;
		String gScore;
		String date;
		
		String displayInfo = "";
	
		public static void main(String[] args)
		{
	            launch(args);
	    }
		

        @Override
        public void start(Stage primaryStage) throws Exception
        {
                
	        	//creating the scene, screen title and borderpane object
	    		primaryStage.setTitle("Player Registration");
	    		BorderPane pane = new BorderPane();
	    		Scene scene = new Scene(pane, 800, 400);
	    		pane.setPadding(new Insets(20,20,20,20));
	    		primaryStage.setScene(scene);
	    		primaryStage.show();
	    		
	    		//creating gridpane for the left most side area to input player information
	    		GridPane leftGrid = new GridPane();
	    		leftGrid.setPadding(new Insets(10,10,10,10));
	    		pane.setLeft(leftGrid);
	    		leftGrid.setAlignment(Pos.CENTER);
	    		leftGrid.setHgap(10);
	    	    leftGrid.setVgap(10);
	    	    leftGrid.add(new Label("Player Information:"), 0, 0);
	    	    leftGrid.add(new Label("First Name:"), 0, 1);
	    	    TextField fName1 = new TextField();
	    	    leftGrid.add(fName1, 1, 1);
	    	    leftGrid.add(new Label("Last Name:"), 0, 2);
	    	    TextField lName1 = new TextField();
	    	    leftGrid.add(lName1, 1, 2);    
	    	    leftGrid.add(new Label("Address:"), 0, 3);
	    	    TextField address1 = new TextField();
	    	    leftGrid.add(address1, 1, 3);
	    	    leftGrid.add(new Label("Province:"), 0, 4);
	    	    TextField province1 = new TextField();
	    	    leftGrid.add(province1, 1, 4);
	    	    leftGrid.add(new Label("Postal Code:"), 0, 5);
	    	    TextField pCode1 = new TextField();
	    	    leftGrid.add(pCode1, 1, 5);
	    	    leftGrid.add(new Label("Phone Number:"), 0, 6);
	    	    TextField pNumber1 = new TextField();
	    	    leftGrid.add(pNumber1, 1, 6);
	    	    
	    	    
	    	    
	    	    //creating gridpane for the right most side area to input game information information
	    		GridPane rightGrid = new GridPane();
	    		rightGrid.setPadding(new Insets(10,10,10,10));
	    		pane.setRight(rightGrid);
	    		rightGrid.setAlignment(Pos.CENTER);
	    		rightGrid.setHgap(10);
	    		rightGrid.setVgap(10);
	    		rightGrid.add(new Label("Update Player by Id:"), 0, 0);
	    		TextField playerId = new TextField();
	    	    rightGrid.add(playerId, 1, 0);
	    	    Button updatePlayer = new Button("Update");
	    		rightGrid.add(updatePlayer, 2, 0);
	    		rightGrid.add(new Label("Game Information:"), 0, 4);
	    		rightGrid.add(new Label("Game Id:"), 0, 5);
	    	    TextField gameId1 = new TextField();
	    	    rightGrid.add(gameId1, 1, 5);
	    	    rightGrid.add(new Label("Game Title:"), 0, 6);
	    	    TextField gTitle1 = new TextField();
	    	    rightGrid.add(gTitle1, 1, 6); 
	    	    rightGrid.add(new Label("Player Game Id:"), 0, 7);
	    	    TextField playerGameId1 = new TextField();
	    	    rightGrid.add(playerGameId1, 1, 7);    
	    	    rightGrid.add(new Label("Game Score:"), 0, 8);
	    	    TextField gScore1 = new TextField();
	    	    rightGrid.add(gScore1, 1, 8); 
	    	    rightGrid.add(new Label("Date Played:"), 0, 9);
	    	    TextField dPlayed = new TextField();
	    	    rightGrid.add(dPlayed, 1, 9);


	    	    
	    	    //creating gridpane for the bottom area 
	    	    GridPane downGrid = new GridPane();
	    		downGrid.setPadding(new Insets(10,10,10,10));
	    		pane.setBottom(downGrid);
	    		downGrid.setAlignment(Pos.TOP_RIGHT);
	    		downGrid.setVgap(10);
	    		Button createPlayer = new Button("Create Player");
	    		createPlayer.setPadding(new Insets(5,60,5,60));
	    		//GridPane.setHalignment(update, HPos.CENTER);
	    		downGrid.add(createPlayer, 3, 0);
	    		Button displayPlayer = new Button("Display All Players");
	    		displayPlayer.setPadding(new Insets(5,30,5,30));
	    		//GridPane.setHalignment(update, HPos.CENTER);
	    		downGrid.add(displayPlayer, 4, 0);
	    		
	    		
	    		//event handler on button create player button click
	    		createPlayer.setOnAction(new EventHandler<ActionEvent>()
	    		{
	    		@Override
	    		public void handle(ActionEvent event)
	    			{
	    			
	    			pId =  Integer.parseInt(playerId.getText());
	    			fName = fName1.getText();
	    			lName = lName1.getText();
	    			address = address1.getText();
	    			province = province1.getText();
	    			pCode = pCode1.getText();
	    			pNumber = pNumber1.getText();
	    			
	    			gameId = gameId1.getText();
	    			playerGameId = playerGameId1.getText();
	    			gTitle =  gTitle1.getText();
	    			gScore = gScore1.getText();
	    			date = dPlayed.getText();
	    			
	    			try 
		    			{
		                	
	                        System.out.println("> Start Program ...");
	                        Class.forName("oracle.jdbc.driver.OracleDriver");
	                        System.out.println("> Driver Loaded successfully.");
	                       
							//establishing a connection
	 
	                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD"," COMP228_w22_sy_148", "password");
	                        System.out.println("> Connection successful.");
	                        
	                        //Setting the query1 to insert data for table Player
	                        String sql1 = "Insert into Player values(?,?,?,?,?,?,?)";
	            	        PreparedStatement stmt1 = conn.prepareStatement(sql1);
	            	        stmt1.setInt(1, pId);
	            	        stmt1.setString(2, fName);
	            	        stmt1.setString(3, lName);
	            	        stmt1.setString(4, address);
	            	        stmt1.setString(5, province);
	            	        stmt1.setString(6, pCode);
	            	        stmt1.setString(7, pNumber);
	            	        int i = stmt1.executeUpdate();  
	            	        System.out.println(i + "records inserted to Player");  
	            	        
	            	        
	            	        //Setting the query2 to insert data for table Game
	            	        String sql2 = "Insert into Game values(?,?)";
	            	        PreparedStatement stmt2 = conn.prepareStatement(sql2);
	            	        stmt2.setString(1, gameId);
	            	        stmt2.setString(2, gTitle);
	            	        int j = stmt2.executeUpdate();  
	            	        System.out.println(j + "records inserted to Game"); 
	            	        
	            	      //Setting the query3 to insert data for table Playerandgame
	            	        String sql3 = "Insert into PlayerAndGame values(?,?,?,?,?)";
	            	        PreparedStatement stmt3 = conn.prepareStatement(sql3);
	            	        stmt3.setString(1, playerGameId);
	            	        stmt3.setString(2, gameId);
	            	        stmt3.setInt(3, pId);
	            	        stmt3.setString(4, date);
	            	        stmt3.setString(5, gScore);
	            	        int k = stmt3.executeUpdate();  
	            	        System.out.println(k + "records inserted to Playerandgame");
	            	        
	            	        //close connection
	            	        conn.close();  
	            	        
	                		
	                	}
                                   
	                catch(Exception e)
		                	{
		                		System.out.println(e);
		                	}  
		    							
		    			}
		    		});
	    	    
	    		
	    		
	    		//event handler on button display all players button click
	    		displayPlayer.setOnAction(new EventHandler<ActionEvent>()
	    		{
	    		@Override
	    		public void handle(ActionEvent event)
	    			{	    			
	    			
	    			try 
		    			{
		                	
	                        System.out.println("> Start Program ...");
	                        Class.forName("oracle.jdbc.driver.OracleDriver");
	                        System.out.println("> Driver Loaded successfully.");
	                       
							//establish a connection
	                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD"," COMP228_w22_sy_148", "password");
	                        System.out.println("> Connection successful.");

	                        
	                        //Query to show player table
	                        String sql = "Select * from Player";
	            	        PreparedStatement stmt = conn.prepareStatement(sql);
	            	        ResultSet rs = stmt.executeQuery(sql); 
	            	        
	            	        
	        		        JTable table = new JTable(buildTableModel(rs));   
	        		        JOptionPane.showMessageDialog(null, new JScrollPane(table));
	            	        
	            	        //Close connection
	            	        conn.close();  
	        		        
	        		        
	                	}
                                   
                catch(Exception e)
	                	{
	                		System.out.println(e);
	                	}  
	    			
	    		
	    							
	    			}
	    		});

	    		
	    		
	    		
	    		//event handler on button update button click
	    		updatePlayer.setOnAction(new EventHandler<ActionEvent>()
	    		{
	    		@Override
	    		public void handle(ActionEvent event)
	    			{
	    			
	    			try 
		    			{
		                	
	                        System.out.println("> Start Program ...");
	                        Class.forName("oracle.jdbc.driver.OracleDriver");
	                        System.out.println("> Driver Loaded successfully.");
	                       
							//establish a connection
	 
	                        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD"," COMP228_w22_sy_148", "password");
	                        System.out.println("> Connection successful.");
	                        
	                        //Getting input data from text area
	                        pId =  Integer.parseInt(playerId.getText());
	    	    			fName = fName1.getText();
	    	    			lName = lName1.getText();
	    	    			address = address1.getText();
	    	    			province = province1.getText();
	    	    			pCode = pCode1.getText();
	    	    			pNumber = pNumber1.getText();
	    	    			gameId = gameId1.getText();
	    	    			playerGameId = playerGameId1.getText();
	    	    			gTitle =  gTitle1.getText();
	    	    			gScore = gScore1.getText();
	    	    			date = dPlayed.getText();
	                       
	    	    			
	    	    			//Query to collect data by resultset on Player and PlayerandGame table
	                        PreparedStatement stmt0 = conn.prepareStatement("Select * From player where Player_Id =?");
	                        PreparedStatement stmt01 = conn.prepareStatement("Select * From PlayerAndGame where Player_Id =?");
	                        
	                        //Query to Update data on Player table
	                        String sql = "update Player set First_Name=?, Last_Name=?, Address=?, Postal_Code=?,"
	                        		+ "Province=?, Phone_Number=? where Player_Id = ?";
	                        PreparedStatement stmt = conn.prepareStatement(sql);
	                        
	                        //Query to Update data on PlayerAndGame table
	                        String sql1 = "update PlayerAndGame set Player_Game_Id=?, Game_Id=?, Playing_Date=?, Score=? "
	                        		+ "where Player_Id =?";
	                        PreparedStatement stmt1 = conn.prepareStatement(sql1);
	                        
	                        
	                        //Query to Update data on Game table
	                        String sql2 = "update Game set Game_Title=? where Game_Id=?";	
	                        PreparedStatement stmt2 = conn.prepareStatement(sql2);
	                        
	                        //Query to collect data by resultset on Game table
	                        PreparedStatement stmt02 = conn.prepareStatement("Select * From Game where Game_Id =?");
	                        
	                        
	                        //Initializing prepare statements
	                        stmt0.setInt(1, pId);
	                        stmt.setInt(7, pId);
	                        stmt1.setInt(5, pId);
	                        stmt01.setInt(1, pId);
	                        
	                        //resultset object declare
	                        ResultSet rs = stmt0.executeQuery();
	                        ResultSet rs1 = stmt01.executeQuery();
	                        rs.next();
	                        rs1.next();
	                        
	                        
	                        gameId = rs1.getString(2);
	                        stmt02.setString(1, gameId);
	                        stmt2.setString(2, gameId);
	                        ResultSet rs2 = stmt02.executeQuery();
	                        rs2.next();
	                        

	                        
	                        
	                        //Update Data to respective cell section if value is entered else the old value remains in the cell
	                        if (fName.isEmpty())
	                        	{
	                        	fName = rs.getString(2);
	                        	stmt.setString(1, fName); 
	                        	}
	                        else
	                        	stmt.setString(1, fName);
	                        
	                        if (lName.isEmpty())
	                        {
	                        	lName = rs.getString(3);
	                        	stmt.setString(2, lName); 
	                        }
	                        else
	                        	stmt.setString(2, lName);
	                    
	                        
	                        if (address.isEmpty())
	                        {
	                        	address = rs.getString(4);
	                        	stmt.setString(3, address); 
	                        }
	                        else
	                        	stmt.setString(3, address);

	                        
	                        if (province.isEmpty())
	                        {
	                        	province = rs.getString(5);
	                        	stmt.setString(4, province); 
	                        }
	                        else
	                        	stmt.setString(4, province);
	            	        
	                        
	                        if (pCode.isEmpty())
	                        {
	                        	pCode = rs.getString(6);
	                        	stmt.setString(5, pCode); 
	                        }
	                        else
	                        	stmt.setString(5, pCode);
	            	        
	                        
	                        if (pNumber.isEmpty())
	                        {
	                        	pNumber = rs.getString(7);
	                        	stmt.setString(6, rs.getString(7));
	                        }
	                        else
	                        	stmt.setString(6, pNumber);
	            	        
	                        
	                        int i = stmt.executeUpdate();  
	                        System.out.println(i+" records updated");
	                        
	                        
	                        
	                        if (playerGameId.isEmpty())
                        	{
	                        	playerGameId = rs1.getString(1);
	                        	stmt1.setString(1, playerGameId); 
                        	}
	                        else
	                        	stmt1.setString(1, playerGameId);
	                        
	                        if (gameId.isEmpty())
                        	{
	                        	gameId = rs1.getString(2);
	                        	stmt1.setString(2, gameId); 
                        	}
	                        else
	                        	stmt1.setString(2, gameId);
	                        
	                        
	                        if (date.isEmpty())
                        	{
	                        	date = rs1.getString(4);
	                        	stmt1.setString(3, date); 
                        	}
	                        else
	                        	stmt1.setString(3, date);
	                        
	                        if (gScore.isEmpty())
                        	{
	                        	gScore = rs1.getString(5);
	                        	stmt1.setString(4, gScore); 
                        	}
	                        else
	                        	stmt1.setString(4, gScore);
	            	        
	                        
	                        
	                        int j = stmt1.executeUpdate();  
	                        System.out.println(j+" records updated");
	            	        
	                        
	                       
	                        
	                        
	                        if (gTitle.isEmpty())
                        	{
	                        	gTitle = rs2.getString(2);
	                        	stmt2.setString(1, gTitle); 
                        	}
	                        else
	                        stmt2.setString(1, gTitle);
	                        
	                        
	                        int k = stmt2.executeUpdate();  
	                        System.out.println(k+" records updated");
	                        
	            	        conn.close();  
	            	        
	                		
	                	}
                                   
                catch(Exception e)
	                	{
	                		System.out.println(e);
	                	}  
	    			}				
	    		});	
        }
        
        
        
        //Build table model method to show the Display player info in a table
        public static DefaultTableModel buildTableModel(ResultSet rs)
		        throws SQLException 
		{

		    ResultSetMetaData metaData = rs.getMetaData();

		    // names of columns
		    Vector<String> columnNames = new Vector<String>();
		    int columnCount = metaData.getColumnCount();
		    for (int column = 1; column <= columnCount; column++) 
		    {
		        columnNames.add(metaData.getColumnName(column));
		    }

		    // data of the table
		    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		    while (rs.next()) 
		    {
		        Vector<Object> vector = new Vector<Object>();
		        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
		        {
		            vector.add(rs.getObject(columnIndex));
		        }
		        data.add(vector);
		    }

		    return new DefaultTableModel(data, columnNames);

		}
}







