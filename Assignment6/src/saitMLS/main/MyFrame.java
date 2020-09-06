package saitMLS.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import saitMLS.exceptions.InvalidClientTypeException;
import saitMLS.exceptions.InvalidPhoneNumberException;
import saitMLS.exceptions.InvalidPostalCodeException;
import saitMLS.exceptions.property.InvalidLegalDescriptionException;
import saitMLS.exceptions.property.InvalidNumberOfBathroomsException;
import saitMLS.persistance.property.CommercialPropertyBroker;
import saitMLS.persistance.property.ResidentialPropertyBroker;
import saitMLS.problemDomain.clientale.Client;
import saitMLS.problemDomain.clientale.ClientBroker;
import saitMLS.problemDomain.property.CommercialProperty;
import saitMLS.problemDomain.property.ResidentialProperty;

/**
 * MyFrame Creates a GUI for the mls broker
 * @author Tyler Decarie
 *
 */
public class MyFrame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// buttons at the top of the gui to switch the page
	private JButton btnClient;
	private JButton btnResidential;
	private JButton btnCommercial;
	// array list of panels (Client, Residential, Commercial)
	private ArrayList<JPanel> panelList;
	// ActionListeners for interactions within the GUI
	private ActionListener actionListener;
	private ActionListener actionListener2;
	private ActionListener SaveListener;
	private ActionListener DeleteListener;
	private ActionListener ClearListener;
	// overall frame of the GUI
	private JFrame frame;
	// JLists of search results for the 3 panels 
	private JList <String>clientSearchResult;
	private JList <String>residentialSearchResult;
	private JList <String>commercialSearchResult;
	// 
	private DefaultListModel<String> clientListModel;
	private DefaultListModel<String> residentialListModel;
	private DefaultListModel<String> commercialListModel;
	
	private ClientBroker cb;
	private ResidentialPropertyBroker res;
	private CommercialPropertyBroker com;
	
	private JButton btnSearchClient;
	private JButton btnClearClient;
	
	private JButton btnSearchResidential;
	private JButton btnClearResidential;
	
	private JButton btnSearchCommercial;
	private JButton btnClearCommercial;
	
	private ArrayList<Client> searchResultClient;
	private ArrayList<ResidentialProperty> searchResultResidential;
	private ArrayList<CommercialProperty> searchResultCommercial;
	
	private JTextField clientIDtf;
	private JTextField clientFirstTf;
	private JTextField clientLastTf;
	private JTextField clientAddressTf;
	private JTextField clientPostalTf;
	private JTextField clientPhoneTf;
	private JComboBox<Character> clientTypeCb;
	
	private JTextField ResidentialIDtf;
	private JTextField ResidentialLegalTf;
	private JTextField ResidentialAddressTf;
	private JComboBox<String> ResidentialQuadrantCb;
	private JComboBox<String> ResidentialZoningCb;
	private JTextField ResidentialAskingPriceTf;
	private JTextField ResidentialSquareTf;
	private JTextField ResidentialBathroomsTf;
	private JTextField ResidentialBedroomsTf;
	private JComboBox<Character> ResidentialTypeCb;
	private JTextField ResidentialCommentsTf;
	
	private JTextField commercialIDtf;
	private JTextField commercialLegalTf;
	private JTextField commercialAddressTf;
	private JComboBox<String> commercialQuadrantCb;
	private JComboBox<String> commercialZoningCb;
	private JTextField commercialAskingPriceTf;
	private JComboBox<String> commercialTypeCb;
	private JTextField commercialFloorsTf;
	private JTextField commercialCommentsTf;
	
	private JRadioButton clientIDrb;
	private JRadioButton clientLastrb;
	private JRadioButton clientTyperb;
	private JTextField searchClientTf;
	
	private JButton saveClient;
	private JButton deleteClient;
	private JButton clearClient;
	
	private JButton saveResidential;
	private JButton deleteResidential;
	private JButton clearResidential;
	
	private JButton saveCommercial;
	private JButton deleteCommercial;
	private JButton clearCommercial;

	private JRadioButton ResidentialIDrb;
	private JRadioButton ResidentialQuadrantrb;
	private JRadioButton ResidentialPricerb;
	private JTextField searchResidentialTf;
	
	private JRadioButton CommercialIDrb;
	private JRadioButton CommercialQuadrantrb;
	private JRadioButton CommercialPricerb;
	private JTextField searchCommercialTf;
	
	private String clientOperator = "ID";
	private String residentialOperator = "ID";
	private String commercialOperator = "ID";

	public MyFrame() throws InvalidPhoneNumberException, InvalidPostalCodeException, IOException {
		initialize();
	}
	/**
	 *  Initializes the brokers, actionlisteners, GUI frame, and calls header panels
	 * @throws IOException 
	 * @throws InvalidPostalCodeException 
	 * @throws InvalidPhoneNumberException 
	 */

	private void initialize() throws InvalidPhoneNumberException, InvalidPostalCodeException, IOException {
		cb = ClientBroker.getBroker();
		res = ResidentialPropertyBroker.getBroker();
		com = CommercialPropertyBroker.getBroker();
		frame = new JFrame("MLS Broker");
		frame.setBounds(10, 10, 800, 700);
		actionListener = new MyActionListener();
		actionListener2 = new MyActionListener2();
		SaveListener = new SaveListner();
		DeleteListener = new DeleteListener();
		ClearListener = new ClearListener();
		panelList = new ArrayList<JPanel>();
		frame.add(getNorthPanel(), BorderLayout.NORTH);
		panelList.add(getClientPanel());
		panelList.add(getResidentialPanel());
		panelList.add(getCommercialPanel());
		
		frame.add(panelList.get(0), BorderLayout.CENTER);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { 
                try {
					cb.closeBroker();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                try {
					res.closeBroker();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                try {
					com.closeBroker();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                System.exit(0);
            }
        });
	} 
	
	/**
	 * 
	 * @return returns the panel for the client selection
	 */

	private JPanel getClientPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getClientNorthCenterPanel(), BorderLayout.NORTH);
		panel.add(getClientCenterCenterPanel(), BorderLayout.CENTER);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the panel for the residential property selection
	 */
	
	private JPanel getResidentialPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getResidentialNorthCenterPanel(), BorderLayout.NORTH);
		panel.add(getResidentialCenterCenterPanel());
		return panel;
	}
	
	/**
	 * 
	 * @return returns the panel for the commercial property selection
	 */
	
	
	private JPanel getCommercialPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getCommercialNorthCenterPanel(), BorderLayout.NORTH);
		panel.add(getCommercialCenterCenterPanel());
		return panel;
	}
	
	/**
	 * 
	 * @return returns the main panel for the client page
	 */
	
	
	private JPanel getClientCenterCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(getClientLeftCenterPanel());
		panel.add(getClientRightCenterPanel());
		return panel;
	}
	
	/**
	 * 
	 * @return returns the main panel for the residential property page
	 */
	
	
	private JPanel getResidentialCenterCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(getResidentialLeftCenterPanel());
		panel.add(getResidentialRightCenterPanel());
		return panel;
	}
	
	/**
	 * 
	 * @return returns the main panel for the commercial property page
	 */
	private JPanel getCommercialCenterCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(getCommercialLeftCenterPanel());
		panel.add(getCommercialRightCenterPanel());
		return panel;
	}
	
	
	/**
	 * 
	 * @return returns the client information panel
	 */
	private JPanel getClientRightCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED) );

		
		JLabel clientTitle = new JLabel("Client Information");
		clientTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		JLabel clientID = new JLabel("Client Id:");
		clientID.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientIDtf = new JTextField();
		clientIDtf.setMaximumSize(new Dimension(70,20));
		clientIDtf.setEditable(false);
		
		JLabel clientFirst = new JLabel("First Name:");
		clientFirst.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientFirstTf = new JTextField();
		clientFirstTf.setMaximumSize(new Dimension(150,20));

		JLabel clientLast = new JLabel("Last Name:");
		clientLast.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientLastTf = new JTextField();
		clientLastTf.setMaximumSize(new Dimension(150,20));
		
		JLabel clientAddress = new JLabel("Address:");
		clientAddress.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientAddressTf = new JTextField();
		clientAddressTf.setMaximumSize(new Dimension(250,20));

		JLabel clientPostal = new JLabel("Postal Code:");
		clientPostal.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientPostalTf = new JTextField();
		clientPostalTf.setMaximumSize(new Dimension(150,20));
		
		JLabel clientPhone = new JLabel("Phone Number:");
		clientPhone.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientPhoneTf = new JTextField();
		clientPhoneTf.setMaximumSize(new Dimension(150,20));
		
		JLabel clientType = new JLabel("Client Type:");
		clientType.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		clientTypeCb = new JComboBox<>();
		clientTypeCb.setMaximumSize(new Dimension(40,20));
		clientTypeCb.addItem('C');
		clientTypeCb.addItem('R');
		clientTypeCb.addItem('B');

		saveClient = new JButton("Save");
		saveClient.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveClient.addActionListener(SaveListener);
		
		deleteClient = new JButton("Delete");
		deleteClient.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		deleteClient.addActionListener(DeleteListener);
		
		clearClient = new JButton("Clear");
		clearClient.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clearClient.addActionListener(ClearListener);
		
		panel.add(clientTitle);
		panel.add(Box.createVerticalGlue());
		panel.add(clientID);
		panel.add(clientIDtf);
		panel.add(Box.createVerticalGlue());
		panel.add(clientFirst);
		panel.add(clientFirstTf);
		panel.add(Box.createVerticalGlue());
		panel.add(clientLast);
		panel.add(clientLastTf);
		panel.add(Box.createVerticalGlue());
		panel.add(clientAddress);
		panel.add(clientAddressTf);
		panel.add(Box.createVerticalGlue());
		panel.add(clientPostal);
		panel.add(clientPostalTf);
		panel.add(Box.createVerticalGlue());
		panel.add(clientPhone);
		panel.add(clientPhoneTf);
		panel.add(Box.createVerticalGlue());
		panel.add(clientType);
		panel.add(clientTypeCb);
		panel.add(Box.createVerticalGlue());
		panel.add(saveClient);
		panel.add(Box.createVerticalGlue());
		panel.add(deleteClient);
		panel.add(Box.createVerticalGlue());
		panel.add(clearClient);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the residential property information panel
	 */
	private JPanel getResidentialRightCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED) );

		
		JLabel ResidentialTitle = new JLabel("Residential Property Information");
		ResidentialTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		JLabel ResidentialID = new JLabel("Residential Property Id:");
		ResidentialID.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialIDtf = new JTextField();
		ResidentialIDtf.setMaximumSize(new Dimension(70,20));
		ResidentialIDtf.setEditable(false);
		
		JLabel ResidentialLegal = new JLabel("Property Legal Description:");
		ResidentialLegal.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialLegalTf = new JTextField();
		ResidentialLegalTf.setMaximumSize(new Dimension(150,20));
		
		JLabel ResidentialAddress = new JLabel("Property Address:");
		ResidentialAddress.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialAddressTf = new JTextField();
		ResidentialAddressTf.setMaximumSize(new Dimension(250,20));
		
		JLabel ResidentialQuadrant = new JLabel("City Quadrant:");
		ResidentialQuadrant.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialQuadrantCb = new JComboBox<>();
		ResidentialQuadrantCb.setMaximumSize(new Dimension(60,20));
		ResidentialQuadrantCb.addItem("NW");
		ResidentialQuadrantCb.addItem("NE");
		ResidentialQuadrantCb.addItem("SW");
		ResidentialQuadrantCb.addItem("SE");
		
		JLabel ResidentialZoning = new JLabel("Zoning of Property:");
		ResidentialZoning.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialZoningCb = new JComboBox<>();
		ResidentialZoningCb.setMaximumSize(new Dimension(60,20));
		ResidentialZoningCb.addItem("R1");
		ResidentialZoningCb.addItem("R2");
		ResidentialZoningCb.addItem("R3");
		ResidentialZoningCb.addItem("R4");
		ResidentialZoningCb.addItem("I1");
		ResidentialZoningCb.addItem("I2");
		ResidentialZoningCb.addItem("I3");
		ResidentialZoningCb.addItem("I4");

		JLabel ResidentialAskingPrice = new JLabel("Asking Price:");
		ResidentialAskingPrice.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialAskingPriceTf = new JTextField();
		ResidentialAskingPriceTf.setMaximumSize(new Dimension(150,20));
		
		JLabel ResidentialSquare = new JLabel("Building Square Footage:");
		ResidentialSquare.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialSquareTf = new JTextField();
		ResidentialSquareTf.setMaximumSize(new Dimension(150,20));
		
		JLabel ResidentialBathrooms = new JLabel("No. of Bathrooms:");
		ResidentialBathrooms.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialBathroomsTf = new JTextField();
		ResidentialBathroomsTf.setMaximumSize(new Dimension(150,20));
		
		JLabel ResidentialBedrooms = new JLabel("No. of Bedrooms:");
		ResidentialBedrooms.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialBedroomsTf = new JTextField();
		ResidentialBedroomsTf.setMaximumSize(new Dimension(150,20));
		
		JLabel ResidentialType = new JLabel("Residential Type:");
		ResidentialType.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialTypeCb = new JComboBox<>();
		ResidentialTypeCb.setMaximumSize(new Dimension(40,20));
		ResidentialTypeCb.addItem('A');
		ResidentialTypeCb.addItem('D');
		ResidentialTypeCb.addItem('N');
		
		JLabel ResidentialComments = new JLabel("Comments about Property:");
		ResidentialComments.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		ResidentialCommentsTf = new JTextField();
		ResidentialCommentsTf.setMaximumSize(new Dimension(300,20));

		saveResidential = new JButton("Save");
		saveResidential.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveResidential.addActionListener(SaveListener);
		
		deleteResidential = new JButton("Delete");
		deleteResidential.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		deleteResidential.addActionListener(DeleteListener);
		
		clearResidential = new JButton("Clear");
		clearResidential.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clearResidential.addActionListener(ClearListener);
		
		panel.add(ResidentialTitle);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialID);
		panel.add(ResidentialIDtf);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialLegal);
		panel.add(ResidentialLegalTf);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialQuadrant);
		panel.add(ResidentialQuadrantCb);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialZoning);
		panel.add(ResidentialZoningCb);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialAskingPrice);
		panel.add(ResidentialAskingPriceTf);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialSquare);
		panel.add(ResidentialSquareTf);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialBathrooms);
		panel.add(ResidentialBathroomsTf);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialBedrooms);
		panel.add(ResidentialBedroomsTf);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialType);
		panel.add(ResidentialTypeCb);
		panel.add(Box.createVerticalGlue());
		panel.add(saveResidential);
		panel.add(Box.createVerticalGlue());
		panel.add(deleteResidential);
		panel.add(Box.createVerticalGlue());
		panel.add(clearResidential);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the commercial property information panel
	 */
	private JPanel getCommercialRightCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED) );
		
		JLabel CommercialTitle = new JLabel("Commercial Property Information");
		CommercialTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		JLabel CommercialID = new JLabel("Commercial Property Id:");
		CommercialID.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialIDtf = new JTextField();
		commercialIDtf.setMaximumSize(new Dimension(70,20));
		commercialIDtf.setEditable(false);
		
		JLabel CommercialLegal = new JLabel("Property Legal Description:");
		CommercialLegal.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialLegalTf = new JTextField();
		commercialLegalTf.setMaximumSize(new Dimension(150,20));
		
		JLabel CommercialAddress = new JLabel("Property Address:");
		CommercialAddress.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialAddressTf = new JTextField();
		commercialAddressTf.setMaximumSize(new Dimension(250,20));
		
		JLabel CommercialQuadrant = new JLabel("City Quadrant:");
		CommercialQuadrant.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialQuadrantCb = new JComboBox<>();
		commercialQuadrantCb.setMaximumSize(new Dimension(60,20));
		commercialQuadrantCb.addItem("NW");
		commercialQuadrantCb.addItem("NE");
		commercialQuadrantCb.addItem("SW");
		commercialQuadrantCb.addItem("SE");
		
		JLabel CommercialZoning = new JLabel("Zoning of Property:");
		CommercialZoning.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialZoningCb = new JComboBox<>();
		commercialZoningCb.setMaximumSize(new Dimension(60,20));
		commercialZoningCb.addItem("R1");
		commercialZoningCb.addItem("R2");
		commercialZoningCb.addItem("R3");
		commercialZoningCb.addItem("R4");
		commercialZoningCb.addItem("I1");
		commercialZoningCb.addItem("I2");
		commercialZoningCb.addItem("I3");
		commercialZoningCb.addItem("I4");

		JLabel CommercialAskingPrice = new JLabel("Asking Price:");
		CommercialAskingPrice.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialAskingPriceTf = new JTextField();
		commercialAskingPriceTf.setMaximumSize(new Dimension(150,20));
		
		JLabel CommercialType = new JLabel("Property Type:");
		CommercialType.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialTypeCb = new JComboBox<>();
		commercialTypeCb.setMaximumSize(new Dimension(40,20));
		commercialTypeCb.addItem("M");
		commercialTypeCb.addItem("O");
		
		JLabel CommercialFloors = new JLabel("No. of Floors:");
		CommercialFloors.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialFloorsTf = new JTextField();
		commercialFloorsTf.setMaximumSize(new Dimension(50,20));
		
		JLabel CommercialComments = new JLabel("Comments about Property:");
		CommercialComments.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		commercialCommentsTf = new JTextField();
		commercialCommentsTf.setMaximumSize(new Dimension(300,20));

		saveCommercial = new JButton("Save");
		saveCommercial.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveCommercial.addActionListener(SaveListener);
		
		deleteCommercial = new JButton("Delete");
		deleteCommercial.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		deleteCommercial.addActionListener(DeleteListener);
		
		clearCommercial = new JButton("Clear");
		clearCommercial.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clearCommercial.addActionListener(ClearListener);
		
		panel.add(CommercialTitle);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialID);
		panel.add(commercialIDtf);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialLegal);
		panel.add(commercialLegalTf);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialAddress);
		panel.add(commercialAddressTf);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialQuadrant);
		panel.add(commercialQuadrantCb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialZoning);
		panel.add(commercialZoningCb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialAskingPrice);
		panel.add(commercialAskingPriceTf);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialType);
		panel.add(commercialTypeCb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialType);
		panel.add(commercialTypeCb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialFloors);
		panel.add(commercialFloorsTf);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialComments);
		panel.add(commercialCommentsTf);
		panel.add(Box.createVerticalGlue());
		panel.add(saveCommercial);
		panel.add(Box.createVerticalGlue());
		panel.add(deleteCommercial);
		panel.add(Box.createVerticalGlue());
		panel.add(clearCommercial);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the left side of the client panel
	 */
	private JPanel getClientLeftCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.add(getClientTopLeftCenterPanel());
		panel.add(getClientBottomLeftCenterPanel());
		return panel;
	}
	
	
	/**
	 * 
	 * @return returns the left side of the residential property panel
	 */
	private JPanel getResidentialLeftCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.add(getResidentialTopLeftCenterPanel());
		panel.add(getResidentialBottomLeftCenterPanel());
		return panel;
	}
	
	
	/**
	 * 
	 * @return returns the left side of the commercial property panel
	 */
	private JPanel getCommercialLeftCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.add(getCommercialTopLeftCenterPanel());
		panel.add(getCommercialBottomLeftCenterPanel());
		return panel;
	}
	
	/**
	 * 
	 * @return returns the list panel of the client panel
	 */
	private JPanel getClientBottomLeftCenterPanel() {
		JPanel panel = new JPanel();
		clientListModel = new DefaultListModel<String>();
		clientSearchResult = new JList<String>(clientListModel);
		clientSearchResult.addListSelectionListener(new ClientListSelectionListener());
		JScrollPane clientScrollPane = new JScrollPane(clientSearchResult);
		clientSearchResult.setFixedCellWidth(380);
		clientSearchResult.setVisibleRowCount(15);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );

		panel.add(clientScrollPane);
		panel.setBackground(Color.LIGHT_GRAY);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the list panel of the residential property panel
	 */
	private JPanel getResidentialBottomLeftCenterPanel() {
		JPanel panel = new JPanel();
		residentialListModel = new DefaultListModel<String>();
		residentialSearchResult = new JList<String>(residentialListModel);
		residentialSearchResult.addListSelectionListener(new ResidentialListSelectionListener());
		JScrollPane residentialScrollPane = new JScrollPane(residentialSearchResult);
		residentialSearchResult.setFixedCellWidth(380);
		residentialSearchResult.setVisibleRowCount(15);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );

		panel.add(residentialScrollPane);
		panel.setBackground(Color.LIGHT_GRAY);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the list panel of the commercial property panel
	 */
	private JPanel getCommercialBottomLeftCenterPanel() {
		JPanel panel = new JPanel();
		commercialListModel = new DefaultListModel<String>();
		commercialSearchResult = new JList<String>(commercialListModel);
		commercialSearchResult.addListSelectionListener(new CommercialListSelectionListener());
		JScrollPane commercialScrollPane = new JScrollPane(commercialSearchResult);
		commercialSearchResult.setFixedCellWidth(380);
		commercialSearchResult.setVisibleRowCount(15);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );

		panel.add(commercialScrollPane);
		panel.setBackground(Color.LIGHT_GRAY );
		return panel;
	}
	
	/**
	 * 
	 * @return returns the search selection panel for client 
	 */
	private JPanel getClientTopLeftCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel clientTitle = new JLabel("Search Clients");
		clientTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		JLabel clientDesc = new JLabel("Select type of search to be performed");
		clientDesc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		clientIDrb = new JRadioButton("Client ID");
		clientIDrb.setSelected(true);
		clientIDrb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clientIDrb.addActionListener(actionListener2);

		clientLastrb = new JRadioButton("Last Name");
		clientLastrb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clientLastrb.addActionListener(actionListener2);
		
		clientTyperb = new JRadioButton("Client Type");
		clientTyperb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clientTyperb.addActionListener(actionListener2);
		
		JLabel clientInstruction = new JLabel("Enter the search parameter below:");
		clientInstruction.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		searchClientTf = new JTextField();
		searchClientTf.setMaximumSize(new Dimension(150, 25));
		
		panel.setBackground(Color.LIGHT_GRAY);
		btnSearchClient = new JButton("Search");
		btnSearchClient.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnSearchClient.addActionListener(actionListener2);
		
		btnClearClient = new JButton("Clear Search");
		btnClearClient.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnClearClient.addActionListener(actionListener2);

		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );

		panel.add(clientTitle);
		panel.add(Box.createVerticalGlue());
		panel.add(clientDesc);
		panel.add(Box.createVerticalGlue());
		panel.add(clientIDrb);
		panel.add(Box.createVerticalGlue());
		panel.add(clientLastrb);
		panel.add(Box.createVerticalGlue());
		panel.add(clientTyperb);
		panel.add(Box.createVerticalGlue());
		panel.add(clientInstruction);
		panel.add(Box.createVerticalGlue());
		panel.add(searchClientTf);
		panel.add(Box.createVerticalGlue());
		panel.add(btnSearchClient);
		panel.add(Box.createVerticalGlue());
		panel.add(btnClearClient);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the search selection panel for residential property 
	 */
	private JPanel getResidentialTopLeftCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);
		JLabel ResidentialTitle = new JLabel("Search Residential Properties");
		ResidentialTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		JLabel ResidentialDesc = new JLabel("Select type of search to be performed");
		ResidentialDesc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		ResidentialIDrb = new JRadioButton("Residential Property ID");
		ResidentialIDrb.setSelected(true);
		ResidentialIDrb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		ResidentialIDrb.addActionListener(actionListener2);

		ResidentialQuadrantrb = new JRadioButton("Quadrant of City");
		ResidentialQuadrantrb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		ResidentialQuadrantrb.addActionListener(actionListener2);


		ResidentialPricerb = new JRadioButton("Residential Property Price");
		ResidentialPricerb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		ResidentialPricerb.addActionListener(actionListener2);

		
		JLabel ResidentialInstruction = new JLabel("Enter the search parameter below:");
		ResidentialInstruction.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		searchResidentialTf = new JTextField();
		searchResidentialTf.setMaximumSize(new Dimension(150, 25));
		
		panel.setBackground(Color.LIGHT_GRAY);
		btnSearchResidential = new JButton("Search");
		btnSearchResidential.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnSearchResidential.addActionListener(actionListener2);
		
		btnClearResidential = new JButton("Clear Search");
		btnClearResidential.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnClearResidential.addActionListener(actionListener2);

		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );

		panel.add(ResidentialTitle);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialDesc);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialIDrb);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialQuadrantrb);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialPricerb);
		panel.add(Box.createVerticalGlue());
		panel.add(ResidentialInstruction);
		panel.add(Box.createVerticalGlue());
		panel.add(searchResidentialTf);
		panel.add(Box.createVerticalGlue());
		panel.add(btnSearchResidential);
		panel.add(Box.createVerticalGlue());
		panel.add(btnClearResidential);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the search selection panel for commercial property  
	 */
	private JPanel getCommercialTopLeftCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.LIGHT_GRAY);
		JLabel CommercialTitle = new JLabel("Search Commercial Properties");
		CommercialTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		JLabel CommercialDesc = new JLabel("Select type of search to be performed");
		CommercialDesc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		CommercialIDrb = new JRadioButton("Commercial Property ID");
		CommercialIDrb.setSelected(true);
		CommercialIDrb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		CommercialIDrb.addActionListener(actionListener2);

		CommercialQuadrantrb = new JRadioButton("Quadrant of City");
		CommercialQuadrantrb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		CommercialQuadrantrb.addActionListener(actionListener2);

		CommercialPricerb = new JRadioButton("Commercial Property Price");
		CommercialPricerb.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		CommercialPricerb.addActionListener(actionListener2);
		
		JLabel CommercialInstruction = new JLabel("Enter the search parameter below:");
		CommercialInstruction.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		searchCommercialTf = new JTextField();
		searchCommercialTf.setMaximumSize(new Dimension(150, 25));
		
		panel.setBackground(Color.LIGHT_GRAY);
		btnSearchCommercial = new JButton("Search");
		btnSearchCommercial.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnSearchCommercial.addActionListener(actionListener2);
		
		btnClearCommercial = new JButton("Clear Search");
		btnClearCommercial.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnClearCommercial.addActionListener(actionListener2);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );

		panel.add(CommercialTitle);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialDesc);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialIDrb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialQuadrantrb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialPricerb);
		panel.add(Box.createVerticalGlue());
		panel.add(CommercialInstruction);
		panel.add(Box.createVerticalGlue());
		panel.add(searchCommercialTf);
		panel.add(Box.createVerticalGlue());
		panel.add(btnSearchCommercial);
		panel.add(Box.createVerticalGlue());
		panel.add(btnClearCommercial);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the top panel of the frame 
	 */
	private JPanel getNorthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		Border buttonEdge = BorderFactory.createRaisedBevelBorder();
		
		btnClient = new JButton("Client");
		btnClient.setBorder(buttonEdge);
		btnClient.addActionListener(actionListener);
		
		btnResidential = new JButton("Residential");
		btnResidential.setBorder(buttonEdge);
		btnResidential.addActionListener(actionListener);
		
		btnCommercial = new JButton("Commercial");
		btnCommercial.setBorder(buttonEdge);
		btnCommercial.addActionListener(actionListener);
		
		panel.add(btnClient);
		panel.add(btnResidential);
		panel.add(btnCommercial);
		
		return panel;
	}
	
	/**
	 * 
	 * @return returns the title panel for the client page
	 */
	private JPanel getClientNorthCenterPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Client Management");
		
		label.setForeground(Color.BLUE);
		label.setFont(new Font("TimesRoman",Font.BOLD,28));
		
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );
		panel.add(label);
		panel.setBackground(Color.lightGray);
		return panel;
	}
	
	/**
	 * 
	 * @return returns the title panel for the residential property page
	 */
	
	
	private JPanel getResidentialNorthCenterPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Residential Management");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("TimesRoman",Font.BOLD,28));
		panel.add(label);
		panel.setBackground(Color.lightGray);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED) );
		return panel;
	}
	
	/**
	 * 
	 * @return returns the title panel for the commercial property page
	 */
	
	
	private JPanel getCommercialNorthCenterPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Commmercial Management");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("TimesRoman",Font.BOLD,28));
		panel.add(label);
		panel.setBackground(Color.lightGray);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		return panel;
	}
	
	/**
	 *  ActionListener to switch between the 3 main panels
	 * 
	 *
	 */
	
	
	
	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JPanel tp;
			for(int i = 0; i < panelList.size(); i++)
			{
				tp = panelList.get(i);
				frame.remove(tp);
				tp.setVisible(false);
			}
			
			if(e.getSource() == btnClient)
			{
				tp = panelList.get(0);
				frame.add(tp);
				tp.setVisible(true);
				
			}
			else if(e.getSource() == btnResidential)
			{
				tp = panelList.get(1);
				frame.add(tp);
				tp.setVisible(true);
				
			}
			else if(e.getSource() == btnCommercial)
			{
				tp = panelList.get(2);
				frame.add(tp);
				tp.setVisible(true);
				
			}
			
		}

	}
	
	/**
	 * ActionListener for the top left panels to select the search type, read the search text box, 
	 * fill the search result text box with results that match the search criterea.
	 *
	 */
	
	
	private class MyActionListener2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
				// if else for switching radio buttons 
				if (e.getSource() == clientIDrb) {
					clientOperator = "ID";
					clientLastrb.setSelected(false);
					clientTyperb.setSelected(false);
				}
				else if (e.getSource() == clientLastrb) {
					clientOperator = "Last";
					clientIDrb.setSelected(false);
					clientTyperb.setSelected(false);
				}
				else if (e.getSource() == clientTyperb) {
					clientOperator = "Type";
					clientIDrb.setSelected(false);
					clientLastrb.setSelected(false);
				}
				
				// search clients by last name 
				if((e.getSource() == btnSearchClient) && clientOperator.equals("Last")) {
					clientListModel.removeAllElements();
					Client client = new Client();
					client.setLastName(searchClientTf.getText());
					searchResultClient = (ArrayList<Client>) cb.search(client);
					
					for(Client c : searchResultClient) {
						//System.out.println(c);
						clientListModel.addElement(c.getClientID()+ " " + c.getFirstName().trim() + " " + c.getLastName().trim() + 
								" " + c.getClientType());
					}
					
				}
				// search clients by ID
				else if((e.getSource() == btnSearchClient) && clientOperator.equals("ID")) {
					clientListModel.removeAllElements();
					Client client = new Client();
					client.setClientID(Long.parseLong(searchClientTf.getText()));
					searchResultClient = (ArrayList<Client>) cb.search(client);
					
					for(Client c : searchResultClient) {
						//System.out.println(c);
						clientListModel.addElement(c.getClientID()+ " " + c.getFirstName().trim() + " " + c.getLastName().trim() + 
								" " + c.getClientType());
					}
					
				}
				// search clients by type
				else if((e.getSource() == btnSearchClient) && clientOperator.equals("Type")) {
					clientListModel.removeAllElements();
					Client client = new Client();
					//try {
						client.setClientType(searchClientTf.getText().charAt(0));
					//} catch (InvalidClientTypeException e1) {
					//	JOptionPane.showMessageDialog(null, "Invalid Client Type Selection");
						
					//
					searchResultClient = (ArrayList<Client>) cb.search(client);
					
					for(Client c : searchResultClient) {
						//System.out.println(c);
						clientListModel.addElement(c.getClientID()+ " " + c.getFirstName().trim() + " " + c.getLastName().trim() + 
								" " + c.getClientType());
					}
					
				}
				
				// if else to switch radio buttons 
				if (e.getSource() == ResidentialIDrb) {
					residentialOperator = "ID";
					ResidentialQuadrantrb.setSelected(false);
					ResidentialPricerb.setSelected(false);
				}
				else if (e.getSource() == ResidentialQuadrantrb) {
					residentialOperator = "Quadrant";
					ResidentialIDrb.setSelected(false);
					ResidentialPricerb.setSelected(false);
				}
				else if (e.getSource() == ResidentialPricerb) {
					residentialOperator = "Price";
					ResidentialIDrb.setSelected(false);
					ResidentialQuadrantrb.setSelected(false);
				}
				
				// search residential property by ID
				if((e.getSource() == btnSearchResidential) && residentialOperator.equals("ID")) {
					residentialListModel.removeAllElements();
					ResidentialProperty resident = new ResidentialProperty();
					resident.setId(Long.parseLong(searchResidentialTf.getText()));
					searchResultResidential = (ArrayList<ResidentialProperty>) res.search(resident);
					
					for(ResidentialProperty r : searchResultResidential) {
						//System.out.println(c);
						residentialListModel.addElement(r.getId()+ " " + r.getLegalDescription() + " " + r.getQuadrant() + 
								" " + r.getAskingPrice());
					}
					
				}
				// search residential property by quadrant 
				else if((e.getSource() == btnSearchResidential) && residentialOperator.equals("Quadrant")) {
					residentialListModel.removeAllElements();
					ResidentialProperty resident = new ResidentialProperty();
					resident.setQuadrant(searchResidentialTf.getText());
					searchResultResidential = (ArrayList<ResidentialProperty>) res.search(resident);
					
					for(ResidentialProperty r : searchResultResidential) {
						//System.out.println(c);
						residentialListModel.addElement(r.getId()+ " " + r.getLegalDescription() + " " + r.getQuadrant() + 
								" " + r.getAskingPrice());
					}
					
				}
				// search residential property by price
				else if((e.getSource() == btnSearchResidential) && residentialOperator.equals("Price")) {
					residentialListModel.removeAllElements();
					ResidentialProperty resident = new ResidentialProperty();
					resident.setAskingPrice(Double.parseDouble(searchResidentialTf.getText()));
					searchResultResidential = (ArrayList<ResidentialProperty>) res.search(resident);
					
					for(ResidentialProperty r : searchResultResidential) {
						//System.out.println(c);
						residentialListModel.addElement(r.getId()+ " " + r.getLegalDescription() + " " + r.getQuadrant() + 
								" " + r.getAskingPrice());
					}
					
				}
				
				// if else to switch radio buttons 
				if (e.getSource() == CommercialIDrb) {
					commercialOperator = "ID";
					CommercialPricerb.setSelected(false);
					CommercialQuadrantrb.setSelected(false);
				}
				else if (e.getSource() == CommercialPricerb) {
					commercialOperator = "Price";
					CommercialIDrb.setSelected(false);
					CommercialQuadrantrb.setSelected(false);
				}
				else if (e.getSource() == CommercialQuadrantrb) {
					commercialOperator = "Quadrant";
					CommercialIDrb.setSelected(false);
					CommercialPricerb.setSelected(false);
				}
				
				// search commercial property by ID
				if((e.getSource() == btnSearchCommercial) && commercialOperator.equals("ID")) {
					commercialListModel.removeAllElements();
					CommercialProperty commercial = new CommercialProperty();
					commercial.setId(Long.parseLong(searchCommercialTf.getText()));
					searchResultCommercial = (ArrayList<CommercialProperty>) com.search(commercial);
					
					for(CommercialProperty cp : searchResultCommercial) {
						//System.out.println(c);
						commercialListModel.addElement(cp.getId()+ " " + cp.getLegalDescription() + " " + cp.getQuadrant() + 
								" " + cp.getAskingPrice());
					}
					
				}
				// search commercial property by price
				else if((e.getSource() == btnSearchCommercial) && commercialOperator.equals("Price")) {
					commercialListModel.removeAllElements();
					CommercialProperty commercial = new CommercialProperty();
					commercial.setAskingPrice(Double.parseDouble(searchCommercialTf.getText()));
					searchResultCommercial = (ArrayList<CommercialProperty>) com.search(commercial);
					
					for(CommercialProperty cp : searchResultCommercial) {
						//System.out.println(c);
						commercialListModel.addElement(cp.getId()+ " " + cp.getLegalDescription() + " " + cp.getQuadrant() + 
								" " + cp.getAskingPrice());
					}
					
				}
				// search commercial property by quadrant
				else if((e.getSource() == btnSearchCommercial) && commercialOperator.equals("Quadrant")) {
					commercialListModel.removeAllElements();
					CommercialProperty commercial = new CommercialProperty();
					commercial.setQuadrant(searchCommercialTf.getText());
					searchResultCommercial = (ArrayList<CommercialProperty>) com.search(commercial);
					
					for(CommercialProperty cp : searchResultCommercial) {
						//System.out.println(c);
						commercialListModel.addElement(cp.getId()+ " " + cp.getLegalDescription() + " " + cp.getQuadrant() + 
								" " + cp.getAskingPrice());
					}
					
				}
				
			
				// if else for clearing the search results from 
				if(e.getSource() == btnClearClient) {
					clientListModel.removeAllElements();
					searchClientTf.setText("");
				}
				
				else if(e.getSource() == btnClearResidential) {
					residentialListModel.removeAllElements();
					searchResidentialTf.setText("");
				}
				
				else if(e.getSource() == btnClearCommercial) {
					commercialListModel.removeAllElements();
					searchCommercialTf.setText("");
				}


			}
			
	}
	
	/**
	 *  ActionListener for clicking on a client entry to return the results to the right side 
	 */
	
	
	private class ClientListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent arg0) {
            if (clientSearchResult.getSelectedIndex() >= 0) {

                int index = clientSearchResult.getSelectedIndex();
                Client temp = new Client();
                temp = searchResultClient.get(index);
                clientIDtf.setText(Long.toString(temp.getClientID()));
                clientFirstTf.setText(temp.getFirstName());
                clientLastTf.setText(temp.getLastName());
                clientAddressTf.setText(temp.getAddress());
                clientPostalTf.setText(temp.getPostalCode());
                clientPhoneTf.setText(temp.getPhoneNumber());
                if (temp.getClientType() == 'C') {
                	clientTypeCb.setSelectedIndex(0);
                } else if (temp.getClientType() == 'R') {
                	clientTypeCb.setSelectedIndex(1);

                } else
                	clientTypeCb.setSelectedIndex(2);
            }
        }
	}
	/**
	 *	ActionListener for clicking on a residential property entry to return the results to the right side 
	 */
	
	
	private class ResidentialListSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(residentialSearchResult.getSelectedIndex() >= 0) {
				int index = residentialSearchResult.getSelectedIndex();
				ResidentialProperty temp = new ResidentialProperty();
				temp = searchResultResidential.get(index);
				ResidentialIDtf.setText(Long.toString(temp.getId()));
				ResidentialLegalTf.setText(temp.getLegalDescription());
				ResidentialAddressTf.setText(temp.getAddress());
				ResidentialQuadrantCb.setSelectedItem(temp.getQuadrant());
				ResidentialZoningCb.setSelectedItem(temp.getZone());
				ResidentialAskingPriceTf.setText(Double.toString(temp.getAskingPrice()));
				ResidentialSquareTf.setText(Double.toString(temp.getArea()));
				ResidentialBathroomsTf.setText(Double.toString(temp.getBathrooms()));
				ResidentialBedroomsTf.setText(Integer.toString(temp.getBedrooms()));
				ResidentialTypeCb.setSelectedItem(temp.getGarage());
				if (temp.getGarage() == 'A') {
					ResidentialTypeCb.setSelectedIndex(0);
                } else if (temp.getGarage() == 'D') {
                	ResidentialTypeCb.setSelectedIndex(1);

                } else
                	ResidentialTypeCb.setSelectedIndex(2);
				ResidentialCommentsTf.setText(temp.getComments());
			}
			
		}
		
	}
	/**
	 * ActionListener for clicking on a commercial property entry to return it to the right side
	 */
	
	
	private class CommercialListSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(commercialSearchResult.getSelectedIndex() >= 0) {
				int index = commercialSearchResult.getSelectedIndex();
				CommercialProperty temp = new CommercialProperty();
				temp = searchResultCommercial.get(index);
				commercialIDtf.setText(Long.toString(temp.getId()));
				commercialLegalTf.setText(temp.getLegalDescription());
				commercialAddressTf.setText(temp.getAddress());
				commercialQuadrantCb.setSelectedItem(temp.getQuadrant());
				commercialZoningCb.setSelectedItem(temp.getZone());
				commercialAskingPriceTf.setText(Double.toString(temp.getAskingPrice()));
				commercialTypeCb.setSelectedItem(temp.getType());
				if (temp.getType().equals("M")) {
					commercialTypeCb.setSelectedIndex(0);
                } else {
                	commercialTypeCb.setSelectedIndex(1);
                }
				commercialFloorsTf.setText(Integer.toString(temp.getNoFloors()));
				commercialCommentsTf.setText(temp.getComments());
				//System.out.println(searchResultcommercial.get(index));
			}
			
		}
		
	}
	
	/**
	 * ActionListener to save the information from the right panel to the backend 
	 */
	
	private class SaveListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean validClient = true;
			boolean validResidential = true;
			boolean validCommercial = true;
			
			// for saving the client information 
			if (e.getSource() == saveClient) {
				Client temp = new Client();
				if (clientIDtf.getText().equals("")) {
					temp.setClientID(0);
				}
				else if (!(clientIDtf.getText().equals(""))) {
				temp.setClientID(Long.parseLong(clientIDtf.getText()));
				}
				temp.setFirstName(clientFirstTf.getText());
				temp.setLastName(clientLastTf.getText());
				temp.setAddress(clientAddressTf.getText());
				try {
					temp.setPostalCode(clientPostalTf.getText());
				} catch (InvalidPostalCodeException e1) {
					validClient = false;
					JOptionPane.showMessageDialog(null, "Invalid Postal Code. \n Must be in the format of A9A 9A9");
				}
				try {
					temp.setPhoneNumber(clientPhoneTf.getText());
				} catch (InvalidPhoneNumberException e1) {
					validClient = false;
					JOptionPane.showMessageDialog(null, "Invalid Phone Number. \n Must be in the format of 403-222-5555");
				}
				//try {
					temp.setClientType((char) clientTypeCb.getSelectedItem());
				//} catch (InvalidClientTypeException e1) {
				//	validClient = false;
				//	JOptionPane.showMessageDialog(null, "Invalid Client Type. \n Must be \"R\", \"C\", or \"B\".");
				//} 
				if (validClient) {
				cb.persist(temp);
				JOptionPane.showMessageDialog(null, "Client Successfully Saved");
				}
			}
			
			// for saving the residential property 
			else if (e.getSource() == saveResidential) {
				ResidentialProperty temp = new ResidentialProperty();
				if (ResidentialIDtf.getText().equals("")) {
					temp.setId(0);
				}
				else if (!(ResidentialIDtf.getText().equals(""))) {
					temp.setId(Long.parseLong(ResidentialIDtf.getText()));
				}
				temp.setLegalDescription(ResidentialLegalTf.getText());
				temp.setAddress(ResidentialAddressTf.getText());
				temp.setQuadrant((String) ResidentialQuadrantCb.getSelectedItem());
				temp.setZone((String) ResidentialZoningCb.getSelectedItem());
				temp.setAskingPrice(Double.parseDouble(ResidentialAskingPriceTf.getText()));
				temp.setArea(Double.parseDouble(ResidentialSquareTf.getText()));
				try {
					temp.setBathrooms(Double.parseDouble(ResidentialBathroomsTf.getText()));
				} catch (NumberFormatException e1) {
					validResidential = false;
					JOptionPane.showMessageDialog(null, "Invalid Bathroom Number");
				}
				temp.setBedrooms(Integer.parseInt(ResidentialBedroomsTf.getText()));
				temp.setGarage((char) ResidentialTypeCb.getSelectedItem());
				temp.setComments(ResidentialCommentsTf.getText());
				
				if (validResidential) {
					res.persist(temp);
					JOptionPane.showMessageDialog(null, "Residential Property Successfully Saved");
				}
			}
			
			// for saving the commercial property information 
			else if (e.getSource() == saveCommercial) {
				CommercialProperty temp = new CommercialProperty();
				if (commercialIDtf.getText().equals("")) {
					temp.setId(0);
				}
				else if (!(commercialIDtf.getText().equals(""))) {
					temp.setId(Long.parseLong(commercialIDtf.getText()));
				}
				temp.setLegalDescription(commercialLegalTf.getText());
				temp.setAddress(commercialAddressTf.getText());
				temp.setQuadrant((String) commercialQuadrantCb.getSelectedItem());
				temp.setZone((String) commercialZoningCb.getSelectedItem());
				temp.setAskingPrice(Double.parseDouble(commercialAskingPriceTf.getText()));
				temp.setType((String) commercialTypeCb.getSelectedItem());
				temp.setNoFloors(Integer.parseInt(commercialFloorsTf.getText()));
				temp.setComments(commercialCommentsTf.getText());
				
				if (validCommercial) {
					com.persist(temp);
					JOptionPane.showMessageDialog(null, "Commercial Property Successfully Saved");
				}
			}
			
		}
		
	}
	
	/**
	 * for clearing the right panel of the information its holding 
	 */
	
	private class ClearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == clearClient) {
				clientIDtf.setText(null);
				clientFirstTf.setText(null);
				clientLastTf.setText(null);
				clientAddressTf.setText(null);
				clientPostalTf.setText(null);
				clientPhoneTf.setText(null);
				clientTypeCb.setSelectedIndex(0);
			}
			else if (e.getSource() == clearResidential) {
				ResidentialIDtf.setText(null);
				ResidentialLegalTf.setText(null);
				ResidentialAddressTf.setText(null);
				ResidentialQuadrantCb.setSelectedIndex(0);
				ResidentialZoningCb.setSelectedIndex(0);
				ResidentialAskingPriceTf.setText(null);
				ResidentialSquareTf.setText(null);
				ResidentialBathroomsTf.setText(null);
				ResidentialBedroomsTf.setText(null);
				ResidentialTypeCb.setSelectedIndex(0);
				ResidentialCommentsTf.setText(null);
			}
			else if (e.getSource() == clearCommercial) {
				commercialIDtf.setText(null);
				commercialLegalTf.setText(null);
				commercialAddressTf.setText(null);
				commercialQuadrantCb.setSelectedIndex(0);
				commercialZoningCb.setSelectedIndex(0);
				commercialAskingPriceTf.setText(null);
				commercialTypeCb.setSelectedIndex(0);
				commercialFloorsTf.setText(null);
				commercialCommentsTf.setText(null);
			}
		}
		
	}
	/**
	 * for deleting a record from the backend, very similar to SaveListener
	 */
	
	private class DeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			boolean validClient = true;
			boolean validResidential = true;
			boolean validCommercial = true;
			
			// to delete from a selected client
			if (e.getSource() == deleteClient) {
				Client temp = new Client();
				if (clientIDtf.getText().equals("")) {
					temp.setClientID(0);
					validClient = false;
				}
				else if (!(clientIDtf.getText().equals(""))) {
				temp.setClientID(Long.parseLong(clientIDtf.getText()));
				}
				temp.setFirstName(clientFirstTf.getText());
				temp.setLastName(clientLastTf.getText());
				temp.setAddress(clientAddressTf.getText());
				try {
					temp.setPostalCode(clientPostalTf.getText());
				} catch (InvalidPostalCodeException e1) {
					validClient = false;
				}
				try {
					temp.setPhoneNumber(clientPhoneTf.getText());
				} catch (InvalidPhoneNumberException e1) {
					validClient = false;
				}
				//try {
					temp.setClientType((char) clientTypeCb.getSelectedItem());
				//} catch (InvalidClientTypeException e1) {
				//	validClient = false;
				//} 
				if (validClient) {
				cb.remove(temp);
				JOptionPane.showMessageDialog(null, "Client Successfully Removed");
				
			
					
					clientIDtf.setText(null);
					clientFirstTf.setText(null);
					clientLastTf.setText(null);
					clientAddressTf.setText(null);
					clientPostalTf.setText(null);
					clientPhoneTf.setText(null);
					clientTypeCb.setSelectedIndex(0);
					
					int index = clientSearchResult.getSelectedIndex();
					clientListModel.removeElementAt(index);
				}
				else {
					JOptionPane.showMessageDialog(null, "Client Removal Failed");
				}
			}
			
			// to delete from a selected residential property
			else if (e.getSource() == deleteResidential) {
				ResidentialProperty temp = new ResidentialProperty();
				if (ResidentialIDtf.getText().equals("")) {
					temp.setId(0);
					validResidential = false;
				}
				else if (!(ResidentialIDtf.getText().equals(""))) {
					temp.setId(Long.parseLong(ResidentialIDtf.getText()));
				}
				temp.setLegalDescription(ResidentialLegalTf.getText());
				temp.setAddress(ResidentialAddressTf.getText());
				temp.setQuadrant((String) ResidentialQuadrantCb.getSelectedItem());
				temp.setZone((String) ResidentialZoningCb.getSelectedItem());
				if (ResidentialAskingPriceTf.getText().equals("")) {
					temp.setAskingPrice(0);
					validResidential = false;
				}
				else if (!(ResidentialAskingPriceTf.getText().equals(""))) {
				temp.setAskingPrice(Double.parseDouble(ResidentialAskingPriceTf.getText()));
				}
				if (ResidentialSquareTf.getText().equals("")) {
					temp.setArea(0);
					validResidential = false;
				}
				else if (!(ResidentialSquareTf.getText().equals(""))) {
				temp.setArea(Double.parseDouble(ResidentialSquareTf.getText()));
				}
				try {
					temp.setBathrooms(Double.parseDouble(ResidentialBathroomsTf.getText()));
				} catch (NumberFormatException e1) {
					validResidential = false;
				}
				if (ResidentialBedroomsTf.getText().equals("")) {
					temp.setBedrooms(0);
					validResidential = false;
				}
				else if (!(ResidentialBedroomsTf.getText().equals(""))) {
				temp.setBedrooms(Integer.parseInt(ResidentialBedroomsTf.getText()));
				}
				temp.setGarage((char) ResidentialTypeCb.getSelectedItem());
				temp.setComments(ResidentialCommentsTf.getText());
				
				if (validResidential) {
					res.remove(temp);
					JOptionPane.showMessageDialog(null, "Residential Propery Deletion Successfull");
				
			
					
					ResidentialIDtf.setText(null);
					ResidentialLegalTf.setText(null);
					ResidentialAddressTf.setText(null);
					ResidentialQuadrantCb.setSelectedIndex(0);
					ResidentialZoningCb.setSelectedIndex(0);
					ResidentialAskingPriceTf.setText(null);
					ResidentialSquareTf.setText(null);
					ResidentialBathroomsTf.setText(null);
					ResidentialBedroomsTf.setText(null);
					ResidentialTypeCb.setSelectedIndex(0);
					ResidentialCommentsTf.setText(null);
					
					int index = residentialSearchResult.getSelectedIndex();
					residentialListModel.removeElementAt(index);
				}
				else {
					JOptionPane.showMessageDialog(null, "Residential Property Removal Failed");
				}
			}
				
			// to delete a selected commercial property
				else if (e.getSource() == deleteCommercial) {
					
					CommercialProperty temp = new CommercialProperty();
					if (commercialIDtf.getText().equals("")) {
						temp.setId(0);
						validCommercial = false;
					}
					else if (!(commercialIDtf.getText().equals(""))) {
						temp.setId(Long.parseLong(commercialIDtf.getText()));
					}
					temp.setLegalDescription(commercialLegalTf.getText());
					temp.setAddress(commercialAddressTf.getText());
					temp.setQuadrant((String) commercialQuadrantCb.getSelectedItem());
					temp.setZone((String) commercialZoningCb.getSelectedItem());
					if (commercialAskingPriceTf.getText().equals("")) {
						temp.setAskingPrice(0);
						validCommercial = false;
					}
					else if (!(commercialAskingPriceTf.getText().equals(""))) {
					temp.setAskingPrice(Double.parseDouble(commercialAskingPriceTf.getText()));
					}
					temp.setType((String) commercialTypeCb.getSelectedItem());
					if (commercialFloorsTf.getText().equals("")) {
						temp.setNoFloors(0);
						validCommercial = false;
					}
					else if (!(commercialFloorsTf.getText().equals(""))) {
					temp.setNoFloors(Integer.parseInt(commercialFloorsTf.getText()));
					}
					temp.setComments(commercialCommentsTf.getText());
					
					if (validCommercial) {
						com.remove(temp);
						JOptionPane.showMessageDialog(null, "Commercial Property Deletion Successfull");
					
					
					commercialIDtf.setText(null);
					commercialLegalTf.setText(null);
					commercialAddressTf.setText(null);
					commercialQuadrantCb.setSelectedIndex(0);
					commercialZoningCb.setSelectedIndex(0);
					commercialAskingPriceTf.setText(null);
					commercialTypeCb.setSelectedIndex(0);
					commercialFloorsTf.setText(null);
					commercialCommentsTf.setText(null);
					
					int index = commercialSearchResult.getSelectedIndex();
					commercialListModel.removeElementAt(index);
					}
					else {
						JOptionPane.showMessageDialog(null, "Commercial Property Removal Failed");
					}
				}
				
		}
		
	}
}