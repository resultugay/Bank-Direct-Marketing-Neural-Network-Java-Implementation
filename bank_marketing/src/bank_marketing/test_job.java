package bank_marketing;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.ResultSetMetaData;

import static bank_marketing.bank_marketing.*;
import static bank_marketing.train_job.*;

public class test_job extends Thread{
	static Connection conn;
	static Statement st;
	static ResultSet rs;
	static int database = 0;
	static int success = 0;
	static int fail = 0;
	boolean test_control = true;
	public static double error;
	public static String test_table_name;

	
	public static double [][] validation_set;
	public static double [] desired_output;

		@Override
		public void run(){
		if(test_control){	
			conn = bank_dao.connect(host,database_name, user_name,password);
			try {
				
				  /*In here we take row and column number to handle*/	
				Statement st2 = conn.createStatement();
				ResultSet rs2 = st2.executeQuery("select * from "+test_table_name+";");
				ResultSetMetaData rsmd = (ResultSetMetaData) rs2.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				rs2.last();
				validation_set = new double[rs2.getRow()][columnsNumber]; 
				desired_output = new double[rs2.getRow()];
				st2.close();
				
				st = conn.createStatement();
				rs = st.executeQuery("select * from "+test_table_name+";");
				
				while(rs.next()){
					for(int j = 0 ; j <  validation_set[0].length ; j++){
						validation_set[database][j] = rs.getDouble(j+1);
					}
					desired_output[database] = rs.getDouble(columnsNumber);
					database = database + 1;
				}
				database = 0;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			test_control = false;
		}
		if(error_tolerance.getText().equals("")){
			error = 0.1;
		}else
			error = Double.parseDouble(error_tolerance.getText());
		
		if(test_with.getText().equals("")){
			test_with_rate = 0.1;
		}else
			test_with_rate = Double.parseDouble(test_with.getText());
		
		test_with.setText(test_with_rate+"");
		
		
		/*
	
		for(int i = 0 ; i < layer[0].neurons.length ; i++){
			for(int j = 0 ; j < layer[1].neurons.length; j++){
			//	weights[0].weights[i][j] = weights[0].weights_temp[i][j];
			//	System.out.println("0.katman w" + i + "" + j + ":" + weights[0].weights[i][j]);
			}
		}
		
		for(int i = 0 ; i < layer[1].neurons.length ; i++){
			for(int j = 0 ; j < layer[2].neurons.length; j++){
			//	weights[1].weights[i][j] = weights[1].weights_temp[i][j];
			//    System.out.println("1.katman w" + i + "" + j + ":" + weights[1].weights[i][j]);
			}
		}
	*/
		for(int k = 0 ; k < validation_set.length ; k++){
				
				for(int i = 0 ; i < layer.length ; i++){
					for(int j = 0 ; j < layer[i].neurons.length ; j++)
					{
						layer[i].neurons[j].output= 0;
						layer[i].neurons[j].input= 0;
					}
				}

				  //this loop for the first layer which is input layer
			    for(int j = 0 ; j < train_set[0].length; j++){
					layer[0].neurons[j].input = validation_set[k][j];
					layer[0].neurons[j].output = validation_set[k][j];
					//System.out.println(layer[0].neurons[j].output );
				}
			
			    for(int i = 0 ; i < layer[1].neurons.length ; i++){
					for(int j = 0 ; j < layer[0].neurons.length; j++){
						layer[1].neurons[i].input += layer[0].neurons[j].output*
													 weights[0].weights[j][i];
					
					}
					//System.out.println(i+" "+layer[1].neurons[i].input );
				}
			    
			    for(int i = 0 ; i < layer[2].neurons.length ; i++){
					for(int j = 0 ; j < layer[1].neurons.length; j++){
						layer[2].neurons[i].input += layer[1].neurons[j].output*
													 weights[1].weights[j][i];
					}					
				}
	    
			    for(int i = 0 ; i < layer[1].neurons.length ; i++){
					layer[1].neurons[i].output = 1/(1+Math.exp(-layer[1].neurons[i].input));
					
				}

				for(int i = 0 ; i < layer[2].neurons.length ; i++){
					for(int j = 0 ; j < layer[1].neurons.length; j++){
						layer[2].neurons[i].input += layer[1].neurons[j].output*
													 weights[1].weights[j][i];
					}					
				}
			    
			    
			    for(int i = 0 ; i < layer[2].neurons.length ; i++){
					layer[2].neurons[i].input += 1*weights[1].weights[layer[1].neurons.length][i];	
					
				}
				
				/*
				 * The neurons of the output layer is activated as every neurons do.
				 * */
				for(int i = 0 ; i < layer[2].neurons.length ; i++){
					layer[2].neurons[i].output = 1/(1+Math.exp(-layer[2].neurons[i].input));
				}
				
			//System.out.print("Expected : " + desired_output[k] + "Actual");
			//System.out.format("%.20f\n", layer[2].neurons[0].output);
	
					result.setCaretPosition(result.getDocument().getLength());
			result.setSelectedTextColor(Color.red);

			for(int i = 0 ; i < layer[2].neurons.length ; i++){ 
				
				if(Math.abs(desired_output[k] - layer[2].neurons[i].output) <= test_with_rate){
					success++;
					}
				else{
					fail++;
					result.setText(result.getText() + "Misclassifed  ");
					}							
			  }
			result.setText(result.getText() + k + ") Expected:  " + desired_output[k] + "  Actual : " + String.format("%.20f\n",  layer[2].neurons[0].output) + "\n");
			
			}
			double rate = (double)success/(success + fail);
		//	System.out.println("success : " + success + " fail : " +  fail);
			JOptionPane.showMessageDialog(null,"success rate = %" + rate*100);
			success = 0;
			fail = 0;
		}
}
