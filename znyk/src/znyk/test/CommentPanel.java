package znyk.test;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CommentPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public CommentPanel() {
		setLayout(null);
		
		JLabel label = new JLabel("\u4E0A\u6599\u5347\u964D\u53F0");
		label.setBounds(0, 28, 75, 15);
		add(label);
		
		textField = new JTextField();
		textField.setBounds(96, 25, 75, 21);
		add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u6258\u76D8\u53F7");
		label_1.setBounds(96, 10, 54, 15);
		add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(177, 25, 75, 21);
		add(textField_1);
		
		JLabel label_2 = new JLabel("\u7269\u6599");
		label_2.setBounds(177, 10, 54, 15);
		add(label_2);

	}
}
