package bank_marketing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class menu implements ActionListener{
	//To create a menu firstly we need to create a menubar that holds a menu.
	JMenuBar menubar;
	// menu is a component that holds all menu items.
	JMenu file;
	JMenu database;
	JMenuItem databaseItem;
	JMenuItem exit;
	JMenuItem configuration;
	JPanel panel;
	JTextField database_name ;
	JTextField train_table ;
	JTextField test_table ;
	JTextField user_name;
	JTextField host;
	JPasswordField password;
	JButton send;
	JFrame databaseFrame;
	public menu(){
		menubar = new JMenuBar();
		//File menu
		file = new JMenu("File");
				
		//Exit menuItem under the file menu
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		
		database = new JMenu("Database");
		databaseItem = new JMenuItem("Connection");
		databaseItem.addActionListener(this);
		
		database.add(databaseItem);
		
		//Add Menu to the menubar
		menubar.add(file);
		menubar.add(database);
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == exit){
			System.exit(0);
		}
		if(e.getSource() == databaseItem){
			
			databaseFrame = new JFrame("Connection");
			databaseFrame.setLayout(null);
			
			panel = new JPanel();
			panel.setLayout(null);
			
			host = new JTextField();
			host.setText("ftp.rsltgy.com");
			host.setSize(100,20);
			host.setLocation(80, 10);
			panel.add(host);
						
			JLabel tmp1 = new JLabel ("Host            :");
			tmp1.setSize(tmp1.getPreferredSize());
			tmp1.setLocation(10, 10);
			panel.add(tmp1);
						
			database_name = new JTextField();
			database_name.setText("data_mining");
			database_name.setSize(100,20);
			database_name.setLocation(80, 40);
			panel.add(database_name);
			
			JLabel tmp2 = new JLabel ("Database   :");
			tmp2.setSize(tmp2.getPreferredSize());
			tmp2.setLocation(10, 40);
			panel.add(tmp2);
	
			user_name = new JTextField();
			user_name.setText("resul");
			user_name.setSize(100,20);
			user_name.setLocation(80, 80);
			panel.add(user_name);
			
			JLabel tmp3 = new JLabel ("User            :");
			tmp3.setSize(tmp3.getPreferredSize());
			tmp3.setLocation(10, 80);
			panel.add(tmp3);
			
			password = new JPasswordField();
			password.setText("tugay");
			password.setSize(100,20);
			password.setLocation(80, 120);
			panel.add(password);
			
			JLabel tmp4 = new JLabel ("Password  :");
			tmp4.setSize(tmp4.getPreferredSize());
			tmp4.setLocation(10, 120);
			panel.add(tmp4);
			
			
			send = new JButton("send");
			send.setSize(100,30);
			send.setLocation(45, 150);
			panel.add(send);
			send.addActionListener(this);
			
			train_table = new JTextField();
			train_table.setText("train_bank");
			train_table.setSize(130,20);
			train_table.setLocation(240, 10);
			panel.add(train_table);
			
			
			JLabel tmp5 = new JLabel ("Train set :");
			tmp5.setSize(tmp5.getPreferredSize());
			tmp5.setLocation(180, 10);
			panel.add(tmp5);
			
			
			test_table = new JTextField();
			test_table.setText("test_bank");
			test_table.setSize(130,20);
			test_table.setLocation(240, 40);
			panel.add(test_table);
						
			
			JLabel tmp6 = new JLabel ("Test set :");
			tmp6.setSize(tmp6.getPreferredSize());
			tmp6.setLocation(180, 40);
			panel.add(tmp6);
			
			databaseFrame.setSize(400, 300);
			databaseFrame.setLocation(1366/2-200, 768/2-150);
			databaseFrame.setContentPane(panel);
			databaseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			databaseFrame.setVisible(true);
		}
		
		if(e.getSource() == send){
			train_job.database_name = database_name.getText();
			train_job.user_name = user_name.getText();
			train_job.password = password.getText();
			train_job.host = host.getText();
			train_job.table_name = train_table.getText();
			test_job.test_table_name = test_table.getText();
			bank_marketing.train_button.setEnabled(true);
			bank_marketing.test_button.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Done!");
			databaseFrame.dispose();
		}
	}
	
}
