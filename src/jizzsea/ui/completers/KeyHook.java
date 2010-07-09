package jizzsea.ui.completers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import charva.awt.event.KeyAdapter;
import charva.awt.event.KeyEvent;
import charvax.swing.JTextField;

public class KeyHook extends KeyAdapter {

	private static final Log log = LogFactory.getLog(KeyHook.class);
	
	private final int keyCode;
	private final Completer comp;
	
	public KeyHook(int keyCode, Completer comp) {
		super();
		this.keyCode = keyCode;
		this.comp = comp;
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		JTextField entry = (JTextField) ke.getSource();
		//log.debug("KeyHook: "+keyCode+"("+ke.getKeyCode()+") using "+comp);
		if(ke.getKeyCode() == keyCode) {
			entry.setText(comp.complete(entry.getText()));
		}
	}

}
