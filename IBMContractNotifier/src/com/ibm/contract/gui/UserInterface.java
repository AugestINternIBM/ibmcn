package com.ibm.contract.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.rpc.ServiceException;

import com.ibm.contract.client.ClientApplication;

import javax.swing.JTextField;
import javax.swing.JProgressBar;
import java.awt.Desktop;

public class UserInterface {

	private JFrame frame;
	private JPanel backgroundPanel;

	private JPanel navPanel;
	private JLabel titleLabel;
	private JEditorPane hostEdittext;

	private JFrame notify;
	private JButton settingButton;

	private JPanel bodyPanel;

	private JPanel forecastPanel;
	private JLabel forecastLabel;
	private static JTextField forecastTextField;
	private JButton forecastButton;

	private JPanel feedbackPanel;
	private JLabel feedbackLabel;
	private static JTextField feedbackTextField;
	private JButton feedbackButton;

	private JPanel outputPanel;
	private JLabel outputLabel;
	private static JTextField outputTextField;
	private JButton outputButton;

	private JPanel controlPanel;
	private static JProgressBar progressBar;
	private JButton runButton;

	private static JFileChooser fileChooser;
	private FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("XLS files", "xls", "xlsx");
	private int returnValue;
	private File selectedFile;
	private ClientApplication client = new ClientApplication();

	private static String inputPath = getConfig("inputPath");
	private static String outputPath = getConfig("outputPath");
	private static String host = getConfig("host");

	private FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text files", "txt");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface(); // Launch &
																// Create the
																// application.
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserInterface() {
		initialize(); // Initialize the contents of the frame.
	}

	private void initialize() {
		frame = new JFrame("IBM CONTRACTS");
		frame.setBounds(100, 100, 500, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(0, 128, 128));
		backgroundPanel.setBounds(0, 0, 486, 393);
		frame.getContentPane().add(backgroundPanel);
		backgroundPanel.setLayout(null);

		navPanel = new JPanel();
		navPanel.setBackground(new Color(0, 102, 204));
		navPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		navPanel.setBounds(0, 0, 486, 40);
		backgroundPanel.add(navPanel);
		navPanel.setLayout(null);

		settingButton = new JButton("\u2699");
		settingButton.setFont(new Font("Dialog.bold", Font.PLAIN, 25));
		settingButton.setBounds(5, 7, 30, 27);
		navPanel.add(settingButton);
		settingButton.setForeground(Color.white);
		settingButton.setBorder(null);
		settingButton.setFocusPainted(false);
		settingButton.setContentAreaFilled(false);

		settingButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					notify = new JFrame();
					notify.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(notify,
							"Welcome to IBMCN Filters Designer! \n\n" + "To add a new filter: \n"
									+ "1) Enter Field Name similar to Field Name in the forcast file to filter according to this column, entring only the Field Name in a row filters out forcast records with no data in this field. \n"
									+ "2) Choose Comparison Type whether comparing the filter values are \"equal\" the value in forcast file, or comparing whether the forcast file values \"contain\" the filter values. \n"
									+ "3) Enter Values which the filter will use to filter the forcast values to include or exclude, the values are to be separted by a comma. \n"
									+ "4) Choose Include state whether to \"include\" or \"exclude\" forcast records with values indicated in values section. \n"
									+ "5) To change the sender email or password, enter the config sheet in the exel workbook and change corresponding cells. \n"
									+ "6) Save the Exel sheet and close it.");
								   // "7) to unprotect the excel file to edit the fixed cells use the password "Admin". \n"
					File file = new File("FilterRules.xlsx");
					Desktop desktop = Desktop.getDesktop();
					desktop.open(file);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				settingButton.setForeground(Color.lightGray);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				settingButton.setForeground(Color.white);
			}
		});

		titleLabel = new JLabel("IBM Contracts");
		titleLabel.setBackground(Color.WHITE);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setBounds(40, 12, 141, 15);
		navPanel.add(titleLabel);

		hostEdittext = new JEditorPane();
		String serverAddress = getConfig("host");

		if (serverAddress == null) {
			serverAddress = "http\\://9.42.1.127\\:9090";
		} else {
			if (serverAddress.equals("")) {
				serverAddress = "http\\://9.42.1.127\\:9090";
			}

		}

		hostEdittext.setText(serverAddress);
		hostEdittext.setBounds(180, 9, 280, 21);
		navPanel.add(hostEdittext);

		bodyPanel = new JPanel();
		bodyPanel.setBackground(new Color(51, 102, 255));
		bodyPanel.setBounds(0, 39, 486, 354);
		backgroundPanel.add(bodyPanel);
		bodyPanel.setLayout(null);

		forecastPanel = new JPanel();
		forecastPanel.setBackground(new Color(0, 102, 204));
		forecastPanel.setForeground(new Color(255, 255, 255));
		forecastPanel.setBounds(10, 10, 466, 71);
		bodyPanel.add(forecastPanel);
		forecastPanel.setLayout(null);

		forecastLabel = new JLabel("Forecast Excel File Path");
		forecastLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		forecastLabel.setForeground(new Color(255, 255, 255));
		forecastLabel.setBounds(10, 9, 254, 13);
		forecastPanel.add(forecastLabel);

		forecastButton = new JButton("Upload");
		forecastButton.setBounds(330, 36, 124, 24);
		forecastPanel.add(forecastButton);
		forecastButton.setForeground(Color.white);
		forecastButton.setBorderPainted(false);
		forecastButton.setFocusPainted(false);
		forecastButton.setContentAreaFilled(false);

		forecastButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fileChooser = new JFileChooser(getConfig("inputPath"));
				fileChooser.setFileFilter(excelFilter);
				returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					forecastTextField.setText(selectedFile.getPath());
					inputPath = selectedFile.getPath();
					setConfig();
					progressBar.setValue(10);
					client.setFcPath(selectedFile.getPath());
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				forecastButton.setForeground(Color.lightGray);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				forecastButton.setForeground(Color.white);
			}
		});

		forecastTextField = new JTextField();
		forecastTextField.setEditable(false);
		forecastTextField.setBounds(10, 41, 310, 19);
		forecastPanel.add(forecastTextField);
		forecastTextField.setColumns(10);

		feedbackPanel = new JPanel();
		feedbackPanel.setLayout(null);
		feedbackPanel.setForeground(Color.WHITE);
		feedbackPanel.setBackground(new Color(0, 102, 204));
		feedbackPanel.setBounds(10, 91, 466, 71);
		bodyPanel.add(feedbackPanel);

		feedbackLabel = new JLabel("Feedback Excel File Path");
		feedbackLabel.setForeground(Color.WHITE);
		feedbackLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		feedbackLabel.setBounds(10, 9, 236, 13);
		feedbackPanel.add(feedbackLabel);

		feedbackButton = new JButton("Upload");
		feedbackButton.setBounds(330, 36, 124, 24);
		feedbackPanel.add(feedbackButton);
		feedbackButton.setForeground(Color.white);
		feedbackButton.setBorderPainted(false);
		feedbackButton.setFocusPainted(false);
		feedbackButton.setContentAreaFilled(false);

		feedbackButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fileChooser = new JFileChooser(getConfig("inputPath"));
				fileChooser.setFileFilter(excelFilter);
				returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					feedbackTextField.setText(selectedFile.getPath());
					inputPath = selectedFile.getPath();
					setConfig();
					progressBar.setValue(20);
					client.setFeedbackPath(selectedFile.getPath());
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				feedbackButton.setForeground(Color.lightGray);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				feedbackButton.setForeground(Color.white);
			}
		});

		feedbackTextField = new JTextField();
		feedbackTextField.setEditable(false);
		feedbackTextField.setColumns(10);
		feedbackTextField.setBounds(10, 41, 310, 19);
		feedbackPanel.add(feedbackTextField);

		outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setForeground(Color.WHITE);
		outputPanel.setBackground(new Color(0, 102, 204));
		outputPanel.setBounds(10, 172, 466, 71);
		bodyPanel.add(outputPanel);

		outputLabel = new JLabel("Output File Path");
		outputLabel.setForeground(Color.WHITE);
		outputLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		outputLabel.setBounds(10, 9, 236, 13);
		outputPanel.add(outputLabel);

		outputTextField = new JTextField();
		outputTextField.setEditable(false);
		outputTextField.setColumns(10);
		outputTextField.setBounds(10, 41, 310, 19);
		outputPanel.add(outputTextField);

		outputButton = new JButton("Select");
		outputButton.setForeground(Color.WHITE);
		outputButton.setFocusPainted(false);
		outputButton.setContentAreaFilled(false);
		outputButton.setBorderPainted(false);
		outputButton.setBounds(330, 36, 124, 24);
		outputPanel.add(outputButton);

		outputButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fileChooser = new JFileChooser(getConfig("outputPath"));
				fileChooser.setFileFilter(txtFilter);
				returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					outputTextField.setText(selectedFile.getPath());
					outputPath = selectedFile.getPath();
					setConfig();
					progressBar.setValue(30);
					client.setOutPutFilePath(selectedFile.getPath());
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				outputButton.setForeground(Color.lightGray);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				outputButton.setForeground(Color.white);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setForeground(Color.WHITE);
		controlPanel.setBackground(new Color(0, 102, 204));
		controlPanel.setBounds(10, 253, 466, 80);
		bodyPanel.add(controlPanel);
		controlPanel.setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("ProgressBar\r\n");
		progressBar.setForeground(new Color(0, 204, 255));
		progressBar.setBounds(10, 10, 446, 21);
		controlPanel.add(progressBar);

		runButton = new JButton("Generate");
		runButton.setIcon(null);
		runButton.setBounds(150, 40, 170, 32);
		controlPanel.add(runButton);
		runButton.setFont(new Font("Dialog", Font.BOLD, 18));
		runButton.setForeground(Color.white);
		runButton.setBorderPainted(false);
		runButton.setFocusPainted(false);
		runButton.setContentAreaFilled(false);

		runButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (checkValidity()) {
					progressBar.setValue(70);
					host = hostEdittext.getText();
					setConfig();
					try {
						client.invoqueRequest();
						progressBar.setValue(100);
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,
								"Connection to server failed! \n"
										+ "Please check connection to the server and try again.",
								"Error 404!", JOptionPane.ERROR_MESSAGE);
					} catch (ServiceException e1) {
						JOptionPane.showMessageDialog(null,
								"Connection to server failed! \n"
										+ "Please check connection to the server and try again",
								"Error 404!", JOptionPane.ERROR_MESSAGE);
					} catch (AddressException e1) {
						e1.printStackTrace();
						
					} catch (MessagingException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Connection to server failed! \n"
										+ "Invalid email authentication data!",
								"Error 404!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			public void mouseEntered(java.awt.event.MouseEvent evt) {
				runButton.setForeground(Color.lightGray);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				runButton.setForeground(Color.white);
			}
		});
	}

	private static boolean checkValidity() {
		if (forecastTextField.getText().equals("") || forecastTextField.getText().isEmpty()
				|| forecastTextField.getText() == null) {
			JOptionPane.showMessageDialog(null, "Not a valid Forecast Excel File Path!");
			return false;
		}

		if (feedbackTextField.getText().equals("") || feedbackTextField.getText().isEmpty()
				|| feedbackTextField.getText() == null) {
			JOptionPane.showMessageDialog(null, "Not a valid Feedback Excel File Path!");
			return false;
		}

		if ((outputTextField.getText().equals("") || outputTextField.getText().isEmpty()
				|| outputTextField.getText() == null)) {
			JOptionPane.showMessageDialog(null, "Not a valid Output Text File Path!");
			return false;
		}

		return true;
	}

	public static String getConfig(String io) {
		Properties prop = new Properties();
		String fileName = "app.config";
		InputStream is = null;
		try {
			File f = new File(fileName);
			if (!(f.exists() && !f.isDirectory())) {
				fileChooser = new JFileChooser();
				PrintWriter writer;
				try {
					writer = new PrintWriter("app.config", "UTF-8");
					writer.println("outputPath=%userprofile%\\documents");
					writer.println("host=http\\://9.42.1.127\\:9090");
					writer.println("inputPath=%userprofile%\\documents");
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		try {
			prop.load(is);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return (prop.getProperty(io));
	}

	public static void setConfig() {
		String fileName = "app.config";
		try {
			Properties props = new Properties();
			props.setProperty("inputPath", inputPath);
			props.setProperty("outputPath", outputPath);
			props.setProperty("host", host);
			props.store(new FileOutputStream(fileName), null);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}