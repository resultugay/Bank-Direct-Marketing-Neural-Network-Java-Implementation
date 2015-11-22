package bank_marketing;

import static bank_marketing.bank_marketing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.ResultSetMetaData;


public class train_job extends Thread{
	
	static Connection conn;
	static Statement st;
	static ResultSet rs;
	
	static int database = 0;
	static double alfa = 0.5;
	static double momentum = 0.8;

	public static layer layer [];
	public static weights weights [];
	public static int loop;
	
	static boolean control = true;
	
	public static double neuron2;

	public static double [][] train_set ;
	public static double [] desired_output;
	
	public static String host;
	public static String database_name;
	public static String user_name;
	public static String password;
	public static String table_name;
	public double err = 0;
	public double error_rate = 0;
	public static double test_with_rate;
	@Override
	public void run(){
	  if(control){	 
		conn = bank_dao.connect(host,database_name, user_name,password);
		try {
			
			result.setText("Connection established \n Please wait..\n");
		  /*In here we take row and column number to handle*/	
			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery("select * from "+table_name+";");
			ResultSetMetaData rsmd = (ResultSetMetaData) rs2.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			rs2.last();
			train_set = new double[rs2.getRow()][columnsNumber]; 
			desired_output = new double[rs2.getRow()];
			st2.close();
			
			st = conn.createStatement();
			rs = st.executeQuery("select * from "+table_name+";");
			
			while(rs.next()){
				for(int j = 0 ; j < train_set[0].length ; j++){
					train_set[database][j] = rs.getDouble(j+1);
				}
				desired_output[database] = rs.getDouble(columnsNumber);
				database = database + 1;
			}
			database = 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQL Error!! Check your tables");
			e.printStackTrace();
		}
		try {
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }
		if(iteration_number.getText().equals("")){
			loop = 10000;
		}else{
			loop = Integer.parseInt(iteration_number.getText());
			}
		iteration_number.setText(loop+"");
		
		if(error_tolerance.getText().equals("")){
			error_rate = 0.001;
		}else
			error_rate = Double.parseDouble(error_tolerance.getText());
		
		error_tolerance.setText(error_rate+"");
		
		//Backpropagation algorithm needs to be coded here.
		//we have three layers here.
		layer = new layer[3];
		//input layer has 16 neurons and 1 bias
		layer[0] = new layer(train_set[0].length);
		//hidden layer has 30 neurons
		layer[1] = new layer(35);
		//output layer has 1 output layer
		layer[2] = new layer(1);	
		//weights
		if(control){
			weights =  new weights[layer.length-1];
			//weights between input and hidden layer
			weights[0] = new weights(layer[0].neurons.length, layer[1].neurons.length,0.2);
			//weight between hidden and output layer
			weights[1] = new weights(layer[1].neurons.length+1, layer[2].neurons.length,0.3);
			control = false; 	
		}
		
	for(int u = 0 ; u < loop ; u++){	
		for(int k = 0 ; k <  train_set.length ; k++){
			   //this loop for the first layer which is input layer
			    for(int j = 0 ; j < train_set[0].length; j++){
					layer[0].neurons[j].input = train_set[k][j];
					layer[0].neurons[j].output = train_set[k][j];
					//System.out.println(	layer[0].neurons[j].output);
				}
				 /*
				 *in this loop all inputs transfer from input layer with weights which is related to*
				 *its input to hidden layer
				 */
				for(int i = 0 ; i < layer[1].neurons.length ; i++){
					for(int j = 0 ; j < layer[0].neurons.length; j++){
						layer[1].neurons[i].input += layer[0].neurons[j].output*
													 weights[0].weights[j][i];
					
					}
					
				}
				/*
				 *The activation formula is implemented for each neuron
				 *in the hidden_layer.Activation formula is sigmoidal 
				 * */
				for(int i = 0 ; i < layer[1].neurons.length ; i++){
					layer[1].neurons[i].output = 1/(1+Math.exp(-layer[1].neurons[i].input));
					
				}
				/*
				 * in these loop the neuron of the output_layer is being calculated.
				 * */
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
				
				/*
				 * In this loop the error of every neuron is calculating.
				 * */
				for(int i = 0 ; i < layer[2].neurons.length; i++){
					err += Math.pow(desired_output[k] - layer[2].neurons[i].output,2);	
					layer[2].neurons[i].error = desired_output[k] - layer[2].neurons[i].output;	
					}
				/*sigma for the each neuron of the output_layer*/
				for(int i = 0 ; i < layer[2].neurons.length; i++){
					layer[2].neurons[i].sigma = layer[2].neurons[i].output*
												(1-layer[2].neurons[i].output)*
												layer[2].neurons[i].error;	
				
				}

				/*weights delta value from hidden layer to the output layer*/
				for(int i = 0 ; i < layer[1].neurons.length ; i++){
					for(int j = 0 ; j < layer[2].neurons.length; j++){
						weights[1].delta[i][j] = alfa *
												 layer[2].neurons[j].sigma*
												 layer[1].neurons[i].output+
												 momentum*weights[1].delta[i][j];			
					
					}
				}
				/*delta for the bias*/
				for(int i = 0 ; i < layer[2].neurons.length; i++){
					weights[1].delta[layer[1].neurons.length][i]= alfa *
												layer[2].neurons[i].sigma*
												1+
												momentum*weights[1].delta[layer[1].neurons.length][i];
					
				}
			
				
				/*new weights from hidden layer to the output layer*/
				for(int i = 0 ; i < layer[1].neurons.length ; i++){
					for(int j = 0 ; j < layer[2].neurons.length; j++){
						weights[1].weights[i][j] =	weights[1].weights[i][j]+
													weights[1].delta[i][j];
						
						if(i < 10 && j < 1 && bank_marketing.visible.isSelected()){
							w2[i].setText(weights[1].weights[i][j]+"");
						}
					
					}
				}
				/*bias weight*/
				for(int i = 0 ; i < layer[2].neurons.length; i++){
						weights[1].weights[layer[1].neurons.length][i] = weights[1].weights[layer[1].neurons.length][i]+
							weights[1].delta[layer[1].neurons.length][i];

				}
				
				
				
				
				for(int i = 0 ; i < layer[1].neurons.length; i++){
					for(int j = 0 ; j < layer[2].neurons.length ; j++){
						layer[1].neurons[i].sigma = layer[1].neurons[i].output*
													(1-layer[1].neurons[i].output)*
													layer[2].neurons[j].sigma*
													0.2;	
						
					}				
				}
				

				for(int i = 0 ; i < layer[1].neurons.length ; i++){
					for(int j = 0 ; j < layer[0].neurons.length; j++){
						weights[0].delta[j][i] = ( alfa *
												 layer[1].neurons[i].sigma*
												 train_set[k][j])+
												 (momentum*weights[0].delta[j][i]);	
					}
				}

				

				for(int i = 0 ; i < layer[1].neurons.length ; i++){
					for(int j = 0 ; j < layer[0].neurons.length; j++){
						weights[0].weights[j][i] += weights[0].delta[j][i];
						//System.out.println(weights[0].weights[j][i]);
						if(i < 10 && j < 3 && bank_marketing.visible.isSelected()){
							w1[j][i].setText(weights[0].weights[j][i]+"");
						}
					}
				}
				
			
				
				
				for(int i = 0 ; i < layer.length ; i++){
					for(int j = 0 ; j < layer[i].neurons.length ; j++)
					{
						layer[i].neurons[j].output= 0;
						layer[i].neurons[j].input = 0;
					}
				}
				
			
		}
		iteration.setText(u+"");
		error.setText(String.format("%.20f\n", err));
		
		if( Math.abs(err) < error_rate)
			  break;
		err = 0;
	 }
	
	}
}
