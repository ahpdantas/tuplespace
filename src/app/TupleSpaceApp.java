package app;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/**
 * This application that requires the following additional files:
 *   TreeDemoHelp.html
 *    arnold.html
 *    bloch.html
 *    chan.html
 *    jls.html
 *    swingtutorial.html
 *    tutorial.html
 *    tutorialcont.html
 *    vm.html
 */
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import gui.DeviceGui;
import gui.EnvironmentGui;
import logic.Device;
import logic.Environment;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TupleSpaceApp extends JPanel
                      implements TreeSelectionListener, MouseListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTree tree;
 
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
    
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;

    public TupleSpaceApp() {
        super(new GridLayout(1,0));

        //Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Espaço de Tuplas");
        createNodes(top);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        tree.addMouseListener(this);

        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }

        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
 
        Dimension minimumSize = new Dimension(300, 300);
        treeView.setMinimumSize(minimumSize);
 
        //Add the split pane to this panel.
        add(splitPane);
    }

    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            Device device = (Device)nodeInfo;
        } 
    }

    private void createNodes(DefaultMutableTreeNode top) {
        
        DefaultMutableTreeNode book = null;
        
        Environment amb1 = new Environment("Amb1");
        amb1.addDevice("dev1");
        amb1.addDevice("dev2");
        amb1.addDevice("dev3");
        
        top.add(new EnvironmentGui(amb1));
        
        Environment amb2 = new Environment("Amb2");
        amb2.addDevice("dev1");
        amb2.addDevice("dev2");
        amb2.addDevice("dev3");
        
        top.add(new EnvironmentGui(amb2));

     }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }

        //Create and set up the window.
        JFrame frame = new JFrame("Espaço de Tuplas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new TupleSpaceApp());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	    if (SwingUtilities.isRightMouseButton(e)) {

	    	JPopupMenu popupMenu = new JPopupMenu();
	        int row = tree.getClosestRowForLocation(e.getX(), e.getY());
	        TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
	        
   	        Object obj = path.getLastPathComponent();
	        if( obj instanceof DeviceGui ){
	        	JMenuItem moveJMenuItem = new JMenuItem("Move");
	        	moveJMenuItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Move");
						
					}
				});
	        	popupMenu.add(moveJMenuItem);
	        }
	        
	        JMenuItem deleteJMenuItem = new JMenuItem("Delete");
	        deleteJMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Delete");
				}
			});
        	popupMenu.add(deleteJMenuItem);
	        
	        
	        tree.setSelectionRow(row);
	        popupMenu.show(e.getComponent(), e.getX(), e.getY());
			
	    }
	    System.out.println("Mouse Event");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}