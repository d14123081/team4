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
	JList userList;
	private JTextField idField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	final JComboBox authComboBox;

	public UserPanel() {
		setBounds(10, 88, 642, 296);
		setLayout(null);
		database = Database.getInstance();
		JPanel userListPanel = new JPanel();
		userListPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		userListPanel.setBounds(10, 11, 159, 274);
		add(userListPanel);
		userListPanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 138, 252);
		userListPanel.add(scrollPane);

		userList = new JList();
		scrollPane.setViewportView(userList);
		userList.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(166, 11, 465, 274);
		add(panel);
		panel.setLayout(null);

		JLabel lblUserId = new JLabel("User ID:");
		lblUserId.setBounds(10, 16, 116, 14);
		panel.add(lblUserId);

		idField = new JTextField();
		idField.setEditable(false);
		idField.setBounds(174, 8, 281, 30);
		panel.add(idField);
		idField.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 75, 116, 14);
		panel.add(lblUsername);

		JLabel lblAuthLevel = new JLabel("Auth. Level:");
		lblAuthLevel.setBounds(10, 133, 116, 14);
		panel.add(lblAuthLevel);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(174, 67, 281, 30);
		panel.add(usernameField);

		authComboBox = new JComboBox();
		authComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Normal User", "Administrator" }));
		authComboBox.setBounds(174, 125, 281, 30);
		panel.add(authComboBox);

		passwordField = new JPasswordField();
		passwordField.setBounds(174, 184, 281, 30);
		panel.add(passwordField);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 192, 116, 14);
		panel.add(lblPassword);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(105, 240, 80, 23);
		panel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usernameField.getText().equals("")
						|| passwordField.getPassword().length == 0) {
					// throw exception
				} else {
					String username = usernameField.getText();
					String password = passwordField.getPassword().toString();
					int authLevel = authComboBox.getSelectedIndex() + 1;

					for (RetailViewListener r : listeners) {
						r.clickCreateUser(username, password, authLevel);
					}
				}
			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(195, 240, 80, 23);
		panel.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().equals("")
						|| usernameField.getText().equals("")
						|| passwordField.getPassword().length == 0) {
					// throw exception
				} else {
					int id = Integer.parseInt(idField.getText());
					String username = usernameField.getText();
					String password = passwordField.getPassword().toString();
					int authLevel = authComboBox.getSelectedIndex() + 1;

					for (RetailViewListener r : listeners) {
						r.clickUpdateUser(id, username, password, authLevel);
					}
				}
			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(285, 240, 80, 23);
		panel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().equals("")) {
					// throw exception
				} else {
					int userId = Integer.parseInt(idField.getText());
					for (RetailViewListener r : listeners) {
						r.clickDeleteUser(userId);
					}
				}
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(375, 240, 80, 23);
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cancel button
			}
		});

		userList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				idField.setText(Integer.toString(((User)userList.getSelectedValue()).getID()));
				usernameField.setText(((User)userList.getSelectedValue()).getUsername());
				if(((User)userList.getSelectedValue()).getAuthorizationLevel() == 1)
				{
					authComboBox.setSelectedIndex(0);
				}
				else
				{
					authComboBox.setSelectedIndex(1);
				}
				//passwordField.setText(((User)userList.getSelectedValue()).getPassword()); obviously not how its meant to be.. unsure about passwords
			}
		});
	}

	public void updateUserList(ArrayList<User> users){
		userList.setListData(users.toArray());
	}
	
	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}
    
    /*
     * A method that clears temp fields on logout.
     */
	public void logout(){
		String empty = "";
		idField.setText(empty);
		usernameField.setText(empty);
		authComboBox.setSelectedIndex(0);
		passwordField.setText(empty);
	}
}
