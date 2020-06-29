package com.dvd;

import com.dvd.config.DBConfig;
import com.dvd.dao.AppDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DVDSearchApp extends JFrame {
	JTextField txtTitle;
	JButton butSearch;
	JList list;
	JScrollPane scrollResults;
	ArrayList<String> dvdList;

	final String mainClass = DVDSearchApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//    final String mainDir = mainClass.substring(0, mainClass.indexOf("build"));

	public DVDSearchApp() {

		setTitle("DVD Search App");
		setLayout(null);

		DefaultListModel dlm = new DefaultListModel();
		list = new JList(dlm);
		dvdList = new ArrayList();
		txtTitle = new JTextField();
		txtTitle.setBounds(30, 30, 150, 25);
		butSearch = new JButton("Title Search");
		butSearch.setBounds(200, 30, 120, 25);
		scrollResults = new JScrollPane(list);
		scrollResults.setBounds(30, 85, 290, 150);

		try {
			// get connection
//            Connection connection = DBConfig.getInstance();
			Connection connection = DBConfig.getInstance();
			// get create statement
			Statement statement = connection.createStatement();
			// execute to use the right DB
			statement.executeUpdate("use software_deployment");
			// reinitialize create statement
			statement = connection.createStatement();
			// query
			String query = "select * From dvd_title";
			// execute query
			ResultSet resultSet = statement.executeQuery(query);
			boolean isDeleted = false;
			while (resultSet.next()) {
				dvdList.add(resultSet.getString("title"));
			}
		} catch (Exception e) {
			txtTitle.setText("Failed to Build DVD List");
			System.out.println(e);
		}

		butSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					dlm.removeAllElements();

					for (String curVal : dvdList) {
						String tempVal = curVal.toLowerCase();
						if (tempVal.contains(txtTitle.getText().toLowerCase())) {
							dlm.addElement(curVal);
						}
					}
				} catch (Exception e) {
					txtTitle.setText("Something Went Wrong!");
					System.out.println(e);
				}
			}
		});

		add(txtTitle);
		add(butSearch);
		add(scrollResults);

		setSize(360, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// info icon for dialog box
				Icon infoIcon = UIManager.getIcon("OptionPane.informationIcon");
				// drop down options
				Object[] options = {"Create DB", "Go to app"};
				// show input dialog with dropdown options
				String action = (String) JOptionPane.showInputDialog(null,
						"Select an action:", "ShowInputDialog",
						JOptionPane.PLAIN_MESSAGE, infoIcon, options, "Numbers");
				// on option select
				switch(action) {
					case "Create DB" : {
						// create DB, tables and populate with data
						AppDao.createDBAndTables();
						break;
					}
					case "Go to app" : {
						new DVDSearchApp();
					}
				}
			}
		});
	}
}
