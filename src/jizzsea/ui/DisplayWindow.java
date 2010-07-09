package jizzsea.ui;

import static charva.awt.BorderLayout.SOUTH;
import static charva.awt.BorderLayout.WEST;

import java.util.List;

import jizzsea.controller.Controller;
import jizzsea.controller.messages.ConnectMessage;
import jizzsea.controller.messages.DisconnectMessage;
import jizzsea.controller.messages.ErrorMessage;
import jizzsea.controller.messages.JoinMessage;
import jizzsea.controller.messages.LogMessage;
import jizzsea.controller.messages.Message;
import jizzsea.controller.messages.MessageMessage;
import jizzsea.controller.messages.MessageType;
import jizzsea.controller.messages.NickChangeMessage;
import jizzsea.controller.messages.PrivateMessageMessage;
import jizzsea.controller.messages.TopicMessage;
import jizzsea.controller.messages.UserMessage;
import jizzsea.ui.completers.CompleterRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import charva.awt.BorderLayout;
import charva.awt.Container;
import charva.awt.Point;
import charva.awt.Toolkit;
import charva.awt.event.ActionEvent;
import charva.awt.event.ActionListener;
import charva.awt.event.KeyEvent;
import charva.awt.event.KeyListener;
import charvax.swing.DefaultListModel;
import charvax.swing.JFrame;
import charvax.swing.JList;
import charvax.swing.JScrollPane;
import charvax.swing.JTextField;
import charvax.swing.JViewport;
import charvax.swing.border.TitledBorder;

public class DisplayWindow extends JFrame implements KeyListener {

	private static final Log log = LogFactory.getLog(DisplayWindow.class);
	
	private final int windowNumber;
	private final JList messages;
	private final JTextField entry;
	private final DefaultListModel model;
	private final Controller handler;

	private JViewport viewport;

	private JScrollPane scroll;
	
	public DisplayWindow(final int windowNumber, final Controller handler) {
		
		this.windowNumber = windowNumber;
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = toolkit.getScreenColumns();
		int height = toolkit.getScreenRows();
		setSize(width, height);
		this.handler = handler;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messages = new JList();
		entry = new JTextField(width-2);
		
		model = new DefaultListModel();
		
		messages.setVisibleRowCount(height-5);
		messages.setColumns(width-4);
		scroll = new JScrollPane(messages);
        TitledBorder viewportBorder = new TitledBorder("window "+windowNumber);
        scroll.setViewportBorder(viewportBorder);
        viewport = scroll.getViewport();
		
		messages.setModel(model);
		entry.addKeyListener(this);
		CompleterRegistry.v.addCompleters(entry);
		
		Container pane = getContentPane();
		
		setFocus(entry);
		
		BorderLayout layout = new BorderLayout();
		pane.setLayout(layout);
		pane.add(scroll, WEST);
		pane.add(entry,SOUTH);
		
		validate();
	}
	
	private void writeToConsole(String from, String msg) {
		DateTime dt = new DateTime();
		
		model.addElement(dt.getHourOfDay() + ":"
				+ zerod(dt.getMinuteOfHour()) + " < " + from + " > " + msg);
	}
	
	public void printMessages(List<Message> msgs) {
		model.clear();
		for (Message msg : msgs) {
			final MessageType type = msg.getType();
			switch (type) {
			case CONNECT:
				writeToConsole("Connected to "+((ConnectMessage)msg).getName());
				break;
			case DISCONNECT:
				writeToConsole("Disconnected from "+((DisconnectMessage)msg).getName());
				break;
			case ERROR:
				writeToConsole("Error: "+((ErrorMessage)msg).getMsg());
				break;
			case LOG:
				writeToConsole(((LogMessage)msg).getMsg());
				break;
			case TOPIC:
				TopicMessage tm = (TopicMessage)msg;
				writeToConsole(tm.getChannel(),"Topic changed to " + tm.getTopic());
				break;
			case NICKCHANGE:
				NickChangeMessage ncm = (NickChangeMessage) msg;
				writeToConsole(ncm.getOldNick(),"Nick changed to "+ncm.getNewNick());
				break;
			case JOIN:
				writeToConsole("Joined: "+((JoinMessage)msg).getChannel());
				break;
			case MESSAGE: {
				MessageMessage mmsg = (MessageMessage) msg;
				writeToConsole(mmsg.getSender(), mmsg.getMessage());
				break;
			} case PRIVATEMESSAGE: {
				PrivateMessageMessage mmsg = (PrivateMessageMessage) msg;
				writeToConsole(mmsg.getSender(), mmsg.getMessage());
				break;
			} default:
				break;
			}
		}
		
		if(!messages.hasFocus()) {
			int size = model.getSize();
			int overflow = messages.getVisibleRowCount() - size;
			Point pos = viewport.getViewPosition();
			messages.setSelectedIndex(size-1);
			if(overflow < 0) {
				pos.y = overflow;
				viewport.setViewPosition(pos);
			}
		}
		repaint();
	}
	
	public void writeToConsole(String string) {
		writeToConsole(" ", string);
	}
	
	private String zerod(int val) {
		return (val < 10) ? "0" + val : "" + val;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == 343) {
			if(entry.getText().equals("/quit"))
				System.exit(0);
			else {
				handler.message(new UserMessage(entry.getText(), this));
			}
		}
	}
}
