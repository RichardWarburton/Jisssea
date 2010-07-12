package jisssea.ui;

import java.util.ArrayList;
import java.util.List;

import jisssea.util.CollectionsUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import charva.awt.Color;
import charva.awt.Container;
import charva.awt.Toolkit;
import charvax.swing.BorderFactory;
import charvax.swing.Box;
import charvax.swing.BoxLayout;
import charvax.swing.JFrame;
import charvax.swing.JLabel;
import charvax.swing.JTextField;
import charvax.swing.SwingUtilities;

public class InformationWindow extends JFrame {

	private static final Log log = LogFactory.getLog(InformationWindow.class);

	public static void main(String[] args) {
		// System.setProperty("charva.color", "");
		try {
			final InformationWindow win = new InformationWindow();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						win.addWindow(3, Urgency.NORMAL);
						win.removeWindow(1);
						Thread.sleep(2000);
					} catch (InterruptedException e) {

					}
					System.exit(0);
				}
			});
			win.addWindow(1, Urgency.NORMAL);
			win.addWindow(2, Urgency.BOLD);
			win.show();
		} catch (Throwable e) {
			log.debug("ffs - error", e);
		}
	}

	public enum Urgency {
		NORMAL {
			@Override
			public Color getColor() {
				return Color.green;
			}
		},
		BOLD {
			@Override
			public Color getColor() {
				return Color.red;
			}
		};
		public abstract Color getColor();
	}

	private final List<SingleWindowInfo> windows;

	private class SingleWindowInfo {
		protected SingleWindowInfo(int n, Urgency urgency) {
			super();
			this.n = n;
			label = new JLabel("" + n);
			label.setForeground(urgency.getColor());
			label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		}

		public final int n;
		public final JLabel label;

		@Override
		public String toString() {
			return "SingleWindowInfo [n=" + n + ", label=" + label + "]";
		}
	}

	public InformationWindow() {

		windows = new ArrayList<SingleWindowInfo>();

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(new JTextField(1));
		contentPane.add(Box.createHorizontalBox());

		setLocation(0, 0);
		setSize(Toolkit.getDefaultToolkit().getScreenRows(), UIConstants.INFO_WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
	}

	public void addWindow(int n, Urgency urgency) {
		final SingleWindowInfo info = new SingleWindowInfo(n, urgency);
		windows.add(info);
		getContentPane().add(info.label);
		forceRedraw();
	}

	public void removeWindow(final int n) {
		final SingleWindowInfo found = CollectionsUtility.findFirst(windows, new CollectionsUtility.Predicate<SingleWindowInfo>() {
			@Override
			public boolean check(SingleWindowInfo info) {
				return info.n == n;
			}
		});
		log.debug(found);
		windows.remove(found);
		getContentPane().remove(found.label);
		forceRedraw();
	}

	private void forceRedraw() {
		hide();
		show();
	}
}
