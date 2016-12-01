package component;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * HintTextField extends JTextField to add a label when the JTextField does not have
 * focus. When focus is gained, the JTextField changes to an empty text field.
 * @author Thomas Van Riper
 * November 29, 2016
 *
 */
public class HintTextField extends JTextField implements FocusListener
{

	/**
	 * An automatically generated serial version UID.
	 */
	private static final long serialVersionUID = 8372589644352688322L;
	
	private String hint;
	private boolean showingHint;
	
	public HintTextField(String hint)
	{
		super(hint);
		this.hint = hint;
		this.setForeground(Color.GRAY);
		showingHint = true;
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if (this.getText().isEmpty())
		{
			super.setText("");
			this.setForeground(Color.BLACK);
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		if (this.getText().isEmpty())
		{
			super.setText(hint);
			this.setForeground(Color.GRAY);
			showingHint = true;
		}
	}

	@Override
	public String getText()
	{
		return showingHint ? "" : super.getText();
	}
}
