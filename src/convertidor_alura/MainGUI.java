package convertidor_alura;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainGUI extends JFrame implements ItemListener, ActionListener {

	private String[] currencies = { "MXN", "USD", "EUR", "ARS", "CLP", "COP", "BRL", "UYU" };
	private String[] temperatures = { "°C", "°F", "°K" };
	private String[] numerics = { "DEC", "HEX", "OCT", "BIN" };
	private JTextField txtInput;
	private JComboBox<String> comboInput, comboOutput, comboType;
	private JButton btnConvert, btnReset, btnCopy;
	private JLabel lblResultNumber, lblResultType;

	public MainGUI(String title) {
		this.setLayout(null);
		this.setTitle(title);

		comboType = new JComboBox<>();
		comboInput = new JComboBox<>();
		comboOutput = new JComboBox<>();

		txtInput = new JTextField();

		btnConvert = new JButton();
		btnReset = new JButton();
		btnCopy = new JButton();

		lblResultNumber = new JLabel();
		lblResultType = new JLabel();

		guiStructure();
	}

	public JComboBox<String> createComboBox(JComboBox<String> combo, int x, int y, int w, int h, String[] list,
			int index) {
		combo.setBounds(x, y, w, h);
		combo.setModel(new DefaultComboBoxModel<>(list));
		combo.setSelectedIndex(index);
		combo.addItemListener(this);
		return combo;
	}

	public JLabel createLabel(int x, int y, String text) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, 284, 12);
		return label;
	}

	public JPanel separator(int x, int y, int w, int h) {
		JPanel sep = new JPanel();
		sep.setBounds(x, y, w, h);
		sep.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		return sep;
	}

	public JTextField createTextField(JTextField field, int x, int y, int w, int h) {
		field.setBounds(x, y, w, h);
		return field;
	}

	public JButton createButton(JButton button, int x, int y, int w, int h, String text) {
		button.setText(text);
		button.setBounds(x, y, w, h);
		button.addActionListener(this);
		return button;
	}

	public JLabel createResultLabel(JLabel label, int x, int y, int w, int h, String text, int size) {
		label.setText(text);
		label.setBounds(x, y, w, h);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Droid Sans", Font.PLAIN, size));
		return label;
	}

	public void guiStructure() {
		// LEFT COMPONENTS
		// INFO: Appending type conversions combobox
		this.getContentPane().add(createLabel(8, 8, "Tipo de conversion"));
		String[] lista = { "Moneda", "Temperatura", "Numérico" };
		this.getContentPane().add(createComboBox(comboType, 8, 28, 284, 30, lista, 0));
		this.getContentPane().add(separator(8, 73, 284, 1));

		// INFO: Appending Input fields
		this.getContentPane().add(createLabel(8, 93, "Dato de entrada:"));
		this.getContentPane().add(createTextField(txtInput, 8, 113, 184, 30));
		this.getContentPane().add(createComboBox(comboInput, 199, 113, 93, 30, currencies, 0));

		// INFO: Appending Output fields
		this.getContentPane().add(createLabel(8, 168, "Dato de salida:"));
		this.getContentPane().add(createComboBox(comboOutput, 199, 159, 93, 30, currencies, 1));

		// INFO: Appending convert Buttons
		this.getContentPane().add(createButton(btnReset, 8, 220, 138, 30, "Reiniciar"));
		this.getContentPane().add(createButton(btnConvert, 154, 220, 138, 30, "Convertir"));

		// NOTE: RIGHT COMPONENTS
		// INFO: Appending vertical separator
		this.getContentPane().add(separator(300, 8, 1, 251));
		this.getContentPane().add(createLabel(308, 8, "RESULTADO:"));
		this.getContentPane().add(createResultLabel(lblResultNumber, 350, 74, 300, 70, "00.00", 56));
		this.getContentPane().add(createResultLabel(lblResultType, 350, 110, 300, 70, "USD", 24));
		this.getContentPane().add(createButton(btnCopy, 431, 220, 138, 30, "Copiar"));

	}

	public void fillCombos(String[] lista) {
		comboInput.setModel(new DefaultComboBoxModel<String>(lista));
		comboOutput.setModel(new DefaultComboBoxModel<String>(lista));
		comboOutput.setSelectedIndex(1);
		lblResultType.setText((String) comboOutput.getSelectedItem());
	}

	public void comboTypeOperations(int opc) {
		switch (opc) {
		case 0:
			fillCombos(currencies);
			break;
		case 1:
			fillCombos(temperatures);
			break;
		case 2:
			fillCombos(numerics);
			break;
		default:
			JOptionPane.showMessageDialog(null, opc);
			break;
		}
	}

	public boolean isNumber(String data) {
		try {
			Double.parseDouble(data);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void displayTempOrCurrencieResult(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("#.###");
		String result = decimalFormat.format(value);

		lblResultNumber.setText(result);
		lblResultType.setText((String) comboOutput.getSelectedItem());
	}

	public void convertCurrency(String amount) {
		// TODO: Run exception when 'amount' is not a number
		if (!isNumber(amount)) {
			JOptionPane.showMessageDialog(null, "No es un valor valido");
		} else {
			Currencies cc = new Currencies();
			cc.setBaseCurrency((String) comboInput.getSelectedItem());
			cc.setTargetCurrency((String) comboOutput.getSelectedItem());
			cc.setAmount(Integer.parseInt(amount));
			displayTempOrCurrencieResult(cc.convertCurrency());
		}
	}

	public void convertTemperature(String value) {
		if (!isNumber(value)) {
			JOptionPane.showMessageDialog(null, "No es un valor valido");
		} else {
			String key = String.valueOf(comboInput.getSelectedIndex()) + String.valueOf(comboOutput.getSelectedIndex());
			Temperatures tt = new Temperatures();
			tt.setKey(key);
			tt.setBaseValue(Double.parseDouble(value));
			displayTempOrCurrencieResult(tt.tempConverter());
		}
	}

	public void convertNumbers(String value) {
		int i = comboInput.getSelectedIndex();
		int j = comboOutput.getSelectedIndex();
		String key = String.valueOf(i) + String.valueOf(j);

		Numerics nn = new Numerics();
		nn.setKey(key);
		nn.setNumberString(txtInput.getText());

		lblResultNumber.setText(nn.conversionFilter());
		lblResultType.setText((String) comboOutput.getSelectedItem());

	}

	public void buttonConvertOperations() {
		String data = txtInput.getText().trim();
		if (!data.isBlank() && !data.isEmpty()) {
			int type = comboType.getSelectedIndex();
			switch (type) {
			case 0:
				convertCurrency(data);
				break;
			case 1:
				convertTemperature(data);
				break;
			case 2:
				convertNumbers(data);
				break;
			default:
				JOptionPane.showMessageDialog(null, "No se puede realizar la conversion");
				buttonResetOperations();
				break;
			}
		} else
			JOptionPane.showMessageDialog(null, "El campo de entrada está vacio");
	}

	public void buttonResetOperations() {
		comboType.setSelectedIndex(0);
		txtInput.setText("");
		fillCombos(currencies);
		comboOutput.setSelectedIndex(1);
		lblResultNumber.setText("00.00");
		lblResultType.setText("USD");
	}

	private void buttonCopyOperations() {
		String labelText = lblResultNumber.getText();
		switch (labelText) {
		case "00.00":
		case "0.0":
		case "0":
			String message = "¡No hay texto que copiar!";
			String title = "Copiar resultado al portapapeles";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.OK_OPTION);
			break;
		default:
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			StringSelection stringSelection = new StringSelection(labelText);
			clipboard.setContents(stringSelection, null);

			JOptionPane.showMessageDialog(null, "Texto Copiado al portapapeles");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
			if (comboBox == comboType) {
				comboTypeOperations(comboType.getSelectedIndex());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReset)
			buttonResetOperations();
		else if (e.getSource() == btnConvert)
			buttonConvertOperations();
		else
			buttonCopyOperations();
	}

	public static void main(String[] args) {
		MainGUI mainFrame = new MainGUI("** Conversor de datos  |  Challenge Alura **");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				String message = "¿Desea salir del programa?";
				String title = "Confirmar cierre";
				int confirm = JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		mainFrame.setSize(700, 266);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

}
