/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.dc_calibration.ui;

import static org.jlab.dc_calibration.constants.Constants.nThBinsVz;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import org.jlab.dc_calibration.fit.TimeToDistanceFitter;

/**
 *
 * @author kpadhikari
 */
public class FitControlBinSelectionUI extends javax.swing.JFrame
{
	public boolean[] checkboxVals = new boolean[nThBinsVz];// {false, false, false, false, false,
															// false, false, false, false, false};
	TimeToDistanceFitter fitter;
	FitControlUI fitControl;

	/**
	 * Creates new form FitControlBinSelectionUI
	 */
	public FitControlBinSelectionUI(FitControlUI fitControl, TimeToDistanceFitter fitter)
	{
		initComponents();
		initCheckBoxValues();
		this.fitter = fitter;
		this.fitControl = fitControl;

		// Adding listener to act on CloseWindowAction() when window is closed.
		// Same will happen if 'Ok' button is pressed
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				CloseWindowAction();
			}
		});
	}

	public void initCheckBoxValues()
	{
		for (int i = 0; i < checkboxVals.length; i++)
		{
			checkboxVals[i] = false;
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT
	 * modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	//
	// kp:
	// https://stackoverflow.com/questions/19433358/difference-between-dispose-and-exit-on-close-in-java
	//
	// EXIT_ON_CLOSE will terminate the program.
	//
	// DISPOSE_ON_CLOSE will call dispose() on the frame, which will make it disappear and remove
	// the resources it is using.
	// You cannot bring it back, unlike hiding it.
	//
	// See aslo JFrame.dispose() vs System.exit()
	//
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents()
	{

		jPanel1 = new javax.swing.JPanel();
		jCheckBox1 = new javax.swing.JCheckBox();
		jCheckBox2 = new javax.swing.JCheckBox();
		jCheckBox3 = new javax.swing.JCheckBox();
		jCheckBox4 = new javax.swing.JCheckBox();
		jCheckBox5 = new javax.swing.JCheckBox();
		jCheckBox6 = new javax.swing.JCheckBox();
		jCheckBox7 = new javax.swing.JCheckBox();
		jCheckBox8 = new javax.swing.JCheckBox();
		jCheckBox9 = new javax.swing.JCheckBox();
		jCheckBox10 = new javax.swing.JCheckBox();
		jCheckBox11 = new javax.swing.JCheckBox();
		jCheckBox12 = new javax.swing.JCheckBox();
		jCheckBox13 = new javax.swing.JCheckBox();
		jCheckBox14 = new javax.swing.JCheckBox();
		jCheckBox15 = new javax.swing.JCheckBox();
		jCheckBox16 = new javax.swing.JCheckBox();
		jCheckBox17 = new javax.swing.JCheckBox();
		jPanel2 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bin Selector");

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Local Angle (alpha) Bins",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(0, 102, 0))); // NOI18N

		jCheckBox1.setText("( -55,-45)");
		jCheckBox1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox1ActionPerformed(evt);
			}
		});

		jCheckBox2.setText("( -45,-35)");
		jCheckBox2.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox2ActionPerformed(evt);
			}
		});

		jCheckBox3.setText("( -35,-25)");
		jCheckBox3.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox3ActionPerformed(evt);
			}
		});

		jCheckBox4.setText("( -25,-20)");
		jCheckBox4.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox4ActionPerformed(evt);
			}
		});

		jCheckBox5.setText("(-20,-15)");
		jCheckBox5.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox5ActionPerformed(evt);
			}
		});

		jCheckBox6.setText("(-15,-10)");
		jCheckBox6.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox6ActionPerformed(evt);
			}
		});

		jCheckBox7.setText("(-10,-6)");
		jCheckBox7.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox7ActionPerformed(evt);
			}
		});

		jCheckBox8.setText("(-6,-2)");
		jCheckBox8.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox8ActionPerformed(evt);
			}
		});

		jCheckBox9.setText("(-2,2)");
		jCheckBox9.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox9ActionPerformed(evt);
			}
		});

		jCheckBox10.setText("(2,6)");
		jCheckBox10.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox10ActionPerformed(evt);
			}
		});

		jCheckBox11.setText("(6,10)");
		jCheckBox11.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox11ActionPerformed(evt);
			}
		});

		jCheckBox12.setText("(10,15)");
		jCheckBox12.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox12ActionPerformed(evt);
			}
		});

		jCheckBox13.setText("(15,20)");
		jCheckBox13.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox13ActionPerformed(evt);
			}
		});

		jCheckBox14.setText("(20,25)");
		jCheckBox14.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox14ActionPerformed(evt);
			}
		});

		jCheckBox15.setText("(25,35)");
		jCheckBox15.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox15ActionPerformed(evt);
			}
		});

		jCheckBox16.setText("(35,45)");
		jCheckBox16.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox16ActionPerformed(evt);
			}
		});

		jCheckBox17.setText("(45,55)");
		jCheckBox17.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jCheckBox17ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(jCheckBox10)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(100, 100, 100))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel1Layout.createSequentialGroup()
														.addGroup(jPanel1Layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(jPanel1Layout.createSequentialGroup()
																		.addComponent(jCheckBox5)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGroup(jPanel1Layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(jCheckBox2)
																				.addComponent(jCheckBox6)
																				.addComponent(jCheckBox11))
																		.addGap(18, 18, 18)
																		.addGroup(jPanel1Layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(jCheckBox3)
																				.addComponent(jCheckBox7))
																		.addGap(26, 26, 26))
																.addGroup(jPanel1Layout.createSequentialGroup()
																		.addGroup(jPanel1Layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(jCheckBox1)
																				.addGroup(jPanel1Layout
																						.createSequentialGroup()
																						.addComponent(jCheckBox14)
																						.addGap(90, 90, 90)
																						.addComponent(jCheckBox15)
																						.addGap(39, 39, 39)
																						.addComponent(jCheckBox16)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				45, Short.MAX_VALUE)))
														.addGroup(jPanel1Layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jCheckBox17)
																.addComponent(jCheckBox4)
																.addComponent(jCheckBox13)
																.addComponent(jCheckBox8))
														.addContainerGap())))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(jCheckBox9)
								.addGap(220, 220, 220)));
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jCheckBox1)
										.addComponent(jCheckBox2)
										.addComponent(jCheckBox3)
										.addComponent(jCheckBox4))
								.addGap(18, 18, 18)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jCheckBox5)
										.addComponent(jCheckBox6)
										.addComponent(jCheckBox7)
										.addComponent(jCheckBox8))
								.addGap(18, 18, 18)
								.addComponent(jCheckBox9)
								.addGap(18, 18, 18)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jCheckBox10)
										.addComponent(jCheckBox11)
										.addComponent(jCheckBox12)
										.addComponent(jCheckBox13))
								.addGap(27, 27, 27)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jCheckBox14)
										.addComponent(jCheckBox15)
										.addComponent(jCheckBox16)
										.addComponent(jCheckBox17))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select B-field Bins",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(0, 102, 0))); // NOI18N

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 115, Short.MAX_VALUE));

		jButton1.setText("OK");
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				jButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton1)
								.addGap(53, 53, 53)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jButton1)
								.addContainerGap(12, Short.MAX_VALUE)));

		jPanel1.getAccessibleContext().setAccessibleName("Select Local Angle (Alpha) Bins");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox4ActionPerformed
		int i = 3;
		if (jCheckBox4.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox4ActionPerformed

	private void jCheckBox15ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox15ActionPerformed
		int i = 14;
		if (jCheckBox15.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox15ActionPerformed

	private void jCheckBox16ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox16ActionPerformed
		int i = 15;
		if (jCheckBox16.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox16ActionPerformed

	private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox1ActionPerformed
		int i = 0;
		if (jCheckBox1.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox1ActionPerformed

	private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox2ActionPerformed
		int i = 1;
		if (jCheckBox2.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox2ActionPerformed

	private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox3ActionPerformed
		int i = 2;
		if (jCheckBox3.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox3ActionPerformed

	private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox5ActionPerformed
		int i = 4;
		if (jCheckBox5.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox5ActionPerformed

	private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox6ActionPerformed
		int i = 5;
		if (jCheckBox6.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox6ActionPerformed

	private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox7ActionPerformed
		int i = 6;
		if (jCheckBox7.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox7ActionPerformed

	private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox8ActionPerformed
		int i = 7;
		if (jCheckBox8.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox8ActionPerformed

	private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox9ActionPerformed
		int i = 8;
		if (jCheckBox9.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox9ActionPerformed

	private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox10ActionPerformed
		int i = 9;
		if (jCheckBox10.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox10ActionPerformed

	private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox11ActionPerformed
		int i = 10;
		if (jCheckBox11.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox11ActionPerformed

	private void jCheckBox12ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox12ActionPerformed
		int i = 11;
		if (jCheckBox12.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox12ActionPerformed

	private void jCheckBox13ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox13ActionPerformed
		int i = 12;
		if (jCheckBox13.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox13ActionPerformed

	private void jCheckBox14ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox14ActionPerformed
		int i = 13;
		if (jCheckBox14.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox14ActionPerformed

	private void jCheckBox17ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jCheckBox17ActionPerformed
		int i = 16;
		if (jCheckBox17.isSelected())
		{
			checkboxVals[i] = true;
		}
		else
		{
			checkboxVals[i] = false;
		}
		System.out.println((i + 1) + "th angular bin selected");
	}// GEN-LAST:event_jCheckBox17ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
	{// GEN-FIRST:event_jButton1ActionPerformed
		// This is OK button
		// The goal here is to pass checkboxVals array to FitControlUI, so that it can be passed to
		// the fitter.
		// FitControlUI fitControl = new FitControlUI();
		CloseWindowAction();
	}// GEN-LAST:event_jButton1ActionPerformed

	public void CloseWindowAction()
	{
		// First pass the checkboxVals array to FitControlUI, and then close then close the selector
		// window.
		fitControl.selectedAngleBins = checkboxVals;

		// for (int i = 0; i < checkboxVals.length; i++) {
		// System.out.println("fitControl.selectedAngleBins["+i+"] = " +
		// fitControl.selectedAngleBins[i]);
		// }

		// System.exit(0); //This will kill the whole program
		this.dispose(); // This will only exit this window (and will dispose the used resources)
		// this.hide();
		// JFrame.dispose(); causes the JFrame window to be destroyed and cleaned up by the
		// operating system.
	}

	public void createAndDisplayTheForm()
	{

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new FitControlBinSelectionUI(fitControl, fitter).setVisible(true);
			}
		});
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and
		 * feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(FitControlBinSelectionUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(FitControlBinSelectionUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(FitControlBinSelectionUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(FitControlBinSelectionUI.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		OrderOfAction OA = null;
		boolean isLinearFit = true;
		ArrayList<String> fileArray = null;

		TimeToDistanceFitter fitter = new TimeToDistanceFitter(OA, fileArray, isLinearFit);
		FitControlUI fitControlTmp = new FitControlUI(fitter);
		FitControlBinSelectionUI binSelector = new FitControlBinSelectionUI(fitControlTmp, fitter);
		binSelector.createAndDisplayTheForm();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JCheckBox jCheckBox10;
	private javax.swing.JCheckBox jCheckBox11;
	private javax.swing.JCheckBox jCheckBox12;
	private javax.swing.JCheckBox jCheckBox13;
	private javax.swing.JCheckBox jCheckBox14;
	private javax.swing.JCheckBox jCheckBox15;
	private javax.swing.JCheckBox jCheckBox16;
	private javax.swing.JCheckBox jCheckBox17;
	private javax.swing.JCheckBox jCheckBox2;
	private javax.swing.JCheckBox jCheckBox3;
	private javax.swing.JCheckBox jCheckBox4;
	private javax.swing.JCheckBox jCheckBox5;
	private javax.swing.JCheckBox jCheckBox6;
	private javax.swing.JCheckBox jCheckBox7;
	private javax.swing.JCheckBox jCheckBox8;
	private javax.swing.JCheckBox jCheckBox9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	// End of variables declaration//GEN-END:variables
}