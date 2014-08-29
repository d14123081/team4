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

	private Database database;
	private JList userList;
	private JTextField idField, usernameField, idTextField;
	private JPasswordField passwordField;
	private JButton btnDelete, btnCancel, btnAdd;
	private JComboBox authComboBox;
	private JPanel panel, userListPanel, panel2;
	private JLabel lblPassword, lblAuthLevel, lblUsername, lblUserId;
	private JCheckBox chckbxNew;
	private JScrollPane scrollPane;

	public UserPanel() {
		setBounds(10, 88, 820, 600);
		setLayout(null);
		initialiseComponents();
		addListeners();
		constructView();
	}

	public void initialiseComponents() {
		database = Database.getInstance();
		panel = new JPanel();
		btnAdd = new JButton("Submit");
		btnCancel = new JButton("Cancel");
		btnAdd = new JButton("Submit");
		chckbxNew = new JCheckBox("New");
		lblUserId = new JLabel("User ID:");
		idField = new JTextField();
		scrollPane = new JScrollPane();
		userList = new JList();
		btnDelete = new JButton("Delete User");
		authComboBox = new JComboBox();
		lblUsername = new JLabel("Username:");
		lblAuthLevel = new JLabel("Auth. Level:");
		userListPanel = new JPanel();
		panel2 = new JPanel();
	}

	public void constructView() {
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 11, 600, 578);
		add(panel);
		panel.setLayout(null);
		btnCancel.setBounds(484, 544, 100, 23);
		panel.add(btnCancel);
		btnAdd.setBounds(374, 544, 100, 23);
		panel.add(btnAdd);
		chckbxNew.setBounds(12, 7, 59, 23);
		panel.add(chckbxNew);
		lblUserId.setBounds(415, 11, 59, 14);
		panel.add(lblUserId);
		idField.setBounds(464, 8, 120, 20);
		panel.add(idField);
		idField.setEditable(false);
		idField.setColumns(10);
		panel2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel2.setBounds(12, 37, 574, 157);
		panel.add(panel2);
		panel2.setLayout(null);
		usernameField = new JTextField();
		usernameField.setBounds(194, 11, 370, 30);
		panel2.add(usernameField);
		usernameField.setColumns(10);
		authComboBox.setBounds(194, 61, 370, 30);
		panel2.add(authComboBox);
		authComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Normal User", "Administrator" }));
		passwordField = new JPasswordField();
		passwordField.setBounds(194, 113, 370, 30);
		panel2.add(passwordField);
		lblUsername.setBounds(24, 19, 67, 14);
		panel2.add(lblUsername);
		lblAuthLevel.setBounds(24, 69, 72, 14);
		panel2.add(lblAuthLevel);
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(24, 121, 67, 14);
		panel2.add(lblPassword);
		userListPanel.setBounds(610, 11, 200, 578);
		add(userListPanel);
		userListPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		userListPanel.setLayout(null);
		scrollPane.setBounds(10, 11, 180, 526);
		userListPanel.add(scrollPane);
		scrollPane.setViewportView(userList);
		userList.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		btnDelete.setBounds(10, 544, 180, 23);
		userListPanel.add(btnDelete);
	}

	public void addListeners() {
		// Handles submit button click
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validateFields()) {
					showError("Please fill empty fields");
				} else {
					String username = usernameField.getText();
					String password = new String(passwordField.getPassword());
					int authLevel = authComboBox.getSelectedIndex() + 1;

					if (chckbxNew.isSelected()) {
						for (RetailViewListener r : listeners) {
							r.clickCreateUser(username, password, authLevel);
						}
						logout();
					} else if (!chckbxNew.isSelected()
							&& !userList.isSelectionEmpty()) {
						int id = Integer.parseInt(idField.getText());
						for (RetailViewListener r : listeners) {
							r.clickUpdateUser(id, username, password, authLevel);
						}
						logout();
					} else {
						showError("You must create a new user or select one from the list to edit");
					}
				}
			}
		});

		// Handles delete button click
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userList.isSelectionEmpty()) {
					showError("No user selected");
				} else {
					int userId = Integer.parseInt(idField.getText());
					for (RetailViewListener r : listeners) {
						r.clickDeleteUser(userId);
					}
					logout();
				}
			}
		});

		// Handles a click on user list
		userList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				idField.setText(Integer.toString(((User) userList
						.getSelectedValue()).getID()));
				usernameField.setText(((User) userList.getSelectedValue())
						.getUsername());
				chckbxNew.setSelected(false);
				if (((User) userList.getSelectedValue())
						.getAuthorizationLevel() == 1) {
					authComboBox.setSelectedIndex(0);
				} else {
					authComboBox.setSelectedIndex(1);
				}
			}
		});

		// Handles cancel button click
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		// Handles the "new" checkbox
		chckbxNew.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					clearFields();
				} else {
					clearFields();
				}

			}
		});
	}
	
	//Resets the GUI
	public void clearFields()
	{
		idField.setText(null);
		usernameField.setText(null);
		passwordField.setText(null);
		userList.clearSelection();
	}

	public void updateUserList(ArrayList<User> users) {
		userList.setListData(users.toArray());
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	public void showError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage);
	}

	// Clears the GUI
	public void logout() {
		clearFields();
		chckbxNew.setSelected(false);
	}

	// Handles what is displayed depending on what user is logged in
	public void updateUserFunctionality(User u) {
		if (u.getAuthorizationLevel() == User.NORMAL_USER) {
			usernameField.setEnabled(false);
			authComboBox.setEnabled(false);
			userList.setEnabled(false);
			idField.setText(Integer.toString(u.getID()));
			usernameField.setText(u.getUsername());
			authComboBox.setSelectedIndex(0);
			btnAdd.setVisible(true);
			btnDelete.setVisible(false);
			btnCancel.setVisible(false);
		} else {
			usernameField.setEnabled(true);
			authComboBox.setEnabled(true);
			userList.setEnabled(true);
			btnAdd.setVisible(true);
			btnDelete.setVisible(true);
			btnCancel.setVisible(true);
			idField.setText(null);
			usernameField.setText(null);
		}
	}

	// Validates fields
	public boolean validateFields() {
		if (usernameField.getText().equals(null)
				|| passwordField.getPassword().length == 0) {
			return false;
		}
		return true;
	}
}