package team4.retailsystem.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.User;

public class UserPanel extends JPanel {
	
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	JTextField usernameTextField;
	JTextField idTextField;
	Database database;
	ArrayList<User> users = new ArrayList<>();
	JTextField usernameField;
	JTextField idField;
	JList list;
	JComboBox authLevelComboBox;

	public UserPanel() {
		setBounds(10, 88, 665, 296);
		setLayout(null);
		database = Database.getInstance();
		users = database.getUsers();

		list = new JList(users.toArray());
		JPanel userListPanel = new JPanel();
		userListPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		userListPanel.setBounds(10, 11, 159, 274);
		add(userListPanel);
		userListPanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 138, 216);
		userListPanel.add(scrollPane);
		scrollPane.setViewportView(list);
		list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));

		JPanel userDetailsPanel = new JPanel();
		userDetailsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		userDetailsPanel.setBounds(171, 11, 191, 118);
		add(userDetailsPanel);
		userDetailsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel idLabel = new JLabel("User ID No:");
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userDetailsPanel.add(idLabel);

		idField = new JTextField();
		userDetailsPanel.add(idField);
		idField.setEditable(false);
		idField.setColumns(10);

		JLabel usernameLabel = new JLabel("Username:");
		userDetailsPanel.add(usernameLabel);

		usernameField = new JTextField();
		userDetailsPanel.add(usernameField);
		usernameField.setColumns(10);

		JLabel authorizationLabel = new JLabel("User level:");
		userDetailsPanel.add(authorizationLabel);

		authLevelComboBox = new JComboBox();
		userDetailsPanel.add(authLevelComboBox);
		authLevelComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Normal User", "Administrator" }));

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				idField.setText(Integer.toString(((User) list
						.getSelectedValue()).getID()));
				usernameField.setText(((User) list.getSelectedValue())
						.getUsername());
				if (((User) list.getSelectedValue()).getAuthorizationLevel() == 1) {
					authLevelComboBox.setSelectedIndex(0);
				} else {
					authLevelComboBox.setSelectedIndex(1);
				}
			}
		});

		JButton btnResetPin = new JButton("Reset User Pin");
		userDetailsPanel.add(btnResetPin);
		btnResetPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// needs to be discussed with group
			}
		});

		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(10, 11, 138, 23);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//get name, pass, authLevel
				
				//call listeners
				for(RetailViewListener r:listeners){
					//r.clickAddUser(username, pass, authLevel);
				}
				
				/*database.addUser(new User());
				users = database.getUsers();
				list.setListData(users.toArray());*/
			}
		});
		userListPanel.add(btnAddUser);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(179, 262, 89, 23);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idField.getText() != "") {
					database.getUsers()
							.get(Integer.parseInt(idField.getText()) - 1)
							.setUsername(usernameField.getText());
					database.getUsers()
							.get(Integer.parseInt(idField.getText()) - 1)
							.setAuthorizationLevel(
									authLevelComboBox.getSelectedIndex() + 1);
				}
			}
		});
		add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(273, 262, 89, 23);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idField.getText() != "") {
					database.deleteUser(database.getUsers().get(
							Integer.parseInt(idField.getText()) - 1));
					idField.setText("");
					usernameField.setText("");
					users = database.getUsers();
					list.setListData(users.toArray());
				}
			}
		});
		add(btnDelete);
	}
	
	public void addListener(RetailViewListener r){
		listeners.add(r);
	}
}
