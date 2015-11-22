package bank_marketing;
/*
 * 
 * Author : resultugay
 * */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class bank_marketing  extends JFrame implements ActionListener{
	JPanel general_panel;
	JPanel parameter_panel;
	JPanel animation_panel;
	JLabel parameters_label [];
	JTextField parameters_text [];
	public static JTextArea result;
	
	public static JScrollPane scrollpane;
	
	public static JButton train_button ;
	public static JButton test_button ;
	public static JTextField iteration_number;
	public static JTextField test_with;
	public static JTextField error_tolerance;
	public static JLabel iteration;
	public static JLabel error;
	public static JLabel w1[][];
	public static JLabel w2[];
	
	public static JCheckBox visible;

	public bank_marketing(){
		
		//Frame that holds all panels.
		JFrame schema = new JFrame("Bank Marketing");
		schema.setLayout(null);
		//General panel that holds all subpanels.
		general_panel = new JPanel();
		general_panel.setLayout(null);
		
		//parameters_panel properties.
		parameter_panel = new JPanel();
		//set all parameter labels and textfields.
		//set_parameters(parameter_panel);
		
		//These loops set all weight labels from input layer to hidden layer.
		w1 = new JLabel[3][10];
		for(int i = 0 ; i < 10 ; i++)
		{
			for(int j = 0 ; j < 3 ; j++)
			{
				w1[j][i] = new JLabel("     ");
				w1[j][i].setSize(100,10);
				w1[j][i].setLocation(500,54*(i+1)+j*12);
						
		    }
		}
		//These loops set all weight labels from hidden layer to output layer.
		w2 = new JLabel[10];
		for(int i = 0 ; i < 10 ; i++)
		{
			w2[i] = new JLabel("    ");
			w2[i].setSize(100,10);
			w2[i].setLocation(720,55*(i+1));
			
		}
		
		//animation_panel is a panel that all animations in.
		animation_panel = new JPanel();
		animation_panel.setLayout(null);
		//animation class is responsible to draw all the things to given panel.
		animation animation = new animation(animation_panel);
		animation.setLocation(250, 40);
		animation.setSize(800,600);
		animation.setBackground(Color.white);
		
		JLabel temp1 = new JLabel("Number of Iteration :");
		temp1.setSize(temp1.getPreferredSize());
		temp1.setLocation(1060, 20);
						
		iteration_number = new JTextField();
		iteration_number.setSize(100,30);
		iteration_number.setToolTipText("Iteration number");
		iteration_number.setLocation(1190,15);
		
		visible = new JCheckBox();
		visible.setText("Visible Animation");
		visible.setSize(200,30);
		visible.setLocation(580, 10);
					
		train_button = new JButton("Train");
		train_button.setSize(100,30);
		train_button.setLocation(1190, 50);
		train_button.addActionListener(this);
		train_button.setEnabled(false);
		
		JLabel temp2 = new JLabel("Error Tolerance :");
		temp2.setSize(temp2.getPreferredSize());
		temp2.setLocation(1060, 120);
		
		
		error_tolerance = new JTextField();
		error_tolerance.setSize(100,30);
		error_tolerance.setToolTipText("Error Tolerance");
		error_tolerance.setLocation(1190,115);
		
		JLabel temp3 = new JLabel("Test with :");
		temp3.setSize(temp3.getPreferredSize());
		temp3.setLocation(1060, 160);
		
		test_with = new JTextField();
		test_with.setSize(100,30);
		test_with.setToolTipText("Test Tolerance");
		test_with.setLocation(1060,180);
		
		
		test_button = new JButton("Test");
		test_button.setSize(100,30);
		test_button.setLocation(1190, 180);
		test_button.addActionListener(this);
		test_button.setEnabled(false);
		
		JLabel tm1 = new JLabel("Iteration :");
		tm1.setSize(tm1.getPreferredSize());
		tm1.setLocation(10, 10);
		
		JLabel tm2 = new JLabel("Error :");
		tm2.setSize(tm2.getPreferredSize());
		tm2.setLocation(10, 40);
		
		result = new JTextArea();
		result.setSize(230,500);
		result.setLocation(10, 100);
		
		scrollpane = new JScrollPane(result);
		scrollpane.setSize(230, 500);
		scrollpane.setLocation(10, 100);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		iteration = new JLabel("                                 ");
		iteration.setSize(iteration.getPreferredSize());
		iteration.setLocation(100, 10);
		
		error = new JLabel("                                 ");
		error.setSize(250,20);
		error.setLocation(50, 40);
		
		
		general_panel.add(parameter_panel);
		//general_panel.add(result);
		general_panel.add(scrollpane);
		general_panel.add(train_button);
		general_panel.add(test_button);
		general_panel.add(visible);
		general_panel.add(tm1);
		general_panel.add(tm2);
		general_panel.add(iteration);
		general_panel.add(error);
		general_panel.add(iteration_number);
		general_panel.add(temp1);
		general_panel.add(error_tolerance);
		general_panel.add(temp2);
		general_panel.add(temp3);
		general_panel.add(test_with);
		//add all weights to the general panel.
		for(int i = 0 ; i < 3 ; i++)
		{
			for(int j = 0 ; j < 10 ; j++)
			{
			 general_panel.add(w1[i][j]);
			}
		}
		for(int j = 0 ; j < 10 ; j++)
		{
		 general_panel.add(w2[j]);
		}
		
		general_panel.add(animation);
		//To take screen size.
		Dimension screen =  Toolkit.getDefaultToolkit().getScreenSize();		
		schema.setSize((int)screen.getWidth(),(int)screen.getHeight());
		schema.setLocation(0,0);
		//We need to attach general_panel to the frame with this.
		schema.setContentPane(general_panel);
		//to handle menu object
		menu menu = new menu();
		schema.setJMenuBar(menu.menubar);
		schema.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		schema.setVisible(true);
	}
		
	
	public void set_parameters(JPanel panel){
		parameter_panel.setLayout(null);
		parameter_panel.setLocation(0,30);
		parameter_panel.setSize(250,600);
		parameters_label = new JLabel[17]; 
		parameters_text = new JTextField[17];
		for(int i = 0; i < 17 ; i++){
			//parameters name is set related to loop variable i.
			parameters_label[i] = new JLabel("parameter " + i + " : ");
			parameters_label[i].setSize(parameters_label[i].getPreferredSize());
			//and location is set as one under the other.
     		parameters_label[i].setLocation(10,panel.getSize().height/(parameters_label[i].getPreferredSize().height+1)*i);
     		parameters_text[i] = new JTextField();
     		parameters_text[i].setSize(100,20);
     		parameters_text[i].setLocation(parameters_label[i].getPreferredSize().width+10,600/(parameters_label[i].getPreferredSize().height+1)*i);
     		
			panel.add(parameters_label[i]);
			panel.add(parameters_text[i]);
		}
	}
	
	public static void main(String[] args) {
		bank_marketing obj = new bank_marketing();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == train_button){
			train_job train = new train_job();
			train.start();
		}
		if(arg0.getSource() == test_button){
			test_job train1 = new test_job();
			train1.start();
		}
	}

}
