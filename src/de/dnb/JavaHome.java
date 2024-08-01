/**
 * 
 */
package de.dnb;

import javax.swing.JOptionPane;

/**
 * @author baumann
 *
 */
public class JavaHome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String javaHome = System.getProperty("java.home");
		JOptionPane.showMessageDialog(null, javaHome, "Pfad zur java.exe",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
