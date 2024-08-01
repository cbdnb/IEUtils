package de.dnb.ie.ddcTk;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkListener;
import javax.swing.tree.TreePath;

import de.dnb.basics.collections.Multimap;
import de.dnb.basics.marc.MarcUtils;
import de.dnb.basics.trees.TreeUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.formatter.HTMLFormatter;

public class View {

	private GUI gui;

	private Multimap<Object, TreePath> paths;

	private JTree tree;

	View() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					View.this.gui = new GUI();
					View.this.gui.setVisible(true);

				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void setSearchListener(final ActionListener actionListener) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.this.gui.btnSearch.addActionListener(actionListener);
				View.this.gui.textFieldDDC.addActionListener(actionListener);
			};
		});
	}

	void setTk(final Record record) {
		if (record == null)
			return;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final HTMLFormatter formatter = new HTMLFormatter();
				formatter.setFontsize(16);
				formatter.setBorder(0);
				formatter.setSpacing(-3);
				formatter.setWidth(200);
				final String txt = formatter.format(record);
				View.this.gui.editorPaneTk.setText(txt);
			};
		});

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.this.gui.editorPaneTk.setCaretPosition(0);
			}
		});
	}

	public void setLinkListener(final HyperlinkListener listener) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.this.gui.editorPaneTk.addHyperlinkListener(listener);
			}
		});
	}

	public void setTree(final JTree tree) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				View.this.gui.scrollPaneTree.setViewportView(tree);
				View.this.tree = tree;
				View.this.paths = TreeUtils.getAllPaths(tree);
			}
		});
	}

	void setMarc(final org.marc4j.marc.Record record) {
		if (record == null)
			return;
		final String r = MarcUtils.readableFormat(record);
		this.gui.editorPaneMarc.setText(r);
	}

	void setDebug(final Record record) {
		if (record == null)
			return;
		this.gui.editorPaneDebug.setText(record.toString());
	}

	String getDDC() {
		return this.gui.textFieldDDC.getText();
	}

	void setEnabled() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.this.gui.btnSearch.setEnabled(true);
			};
		});
	}

	void setInfo(final String info) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.this.gui.labelInfo.setText(info);
				;
			};
		});
	}

	void expandTo(final String ddc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Collection<TreePath> pathsToDDC = View.this.paths
						.get(ddc);
				for (final TreePath treePath : pathsToDDC) {
					View.this.tree.collapsePath(treePath);
					View.this.tree.setSelectionPath(treePath);
				}
			};
		});
	}

}
