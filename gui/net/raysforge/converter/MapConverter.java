package net.raysforge.converter;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.raysforge.prodeus.Map2Emap;
import net.raysforge.prodeus.textures.GenerateTexturesFromPngZip;

public class MapConverter extends javax.swing.JFrame {

    JFileChooser jFileChooserOpen = new JFileChooser();
    JFileChooser jFileChooserSave = new JFileChooser();
    JToolBar toolBar = new JToolBar();
    JButton toolbarButtonExtractMaterials = new JButton();
    JButton toolbarButtonConvertMap = new JButton();
    JButton toolbarButtonExamples = new JButton();
    JTextField textFieldPathToMap = new JTextField();
    JButton buttonSelectMap = new JButton();
    JTextField textFieldPathToEmap = new JTextField();
    JButton buttonSelectEmap = new JButton();
    JScrollPane scrollPaneLogs = new JScrollPane();
    JTextArea textAreaForLogs = new JTextArea();
    JTextField textFieldPathToMaterials = new JTextField();
    JButton buttonSelectPathToMaterials = new JButton();
    JTextField textFieldPathToQ2TexturesZip = new JTextField();
    JButton buttonSelectTexturesZip = new JButton();
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu();
    JMenuItem menuItemClose = new JMenuItem();
    JMenu menuHelp = new JMenu();
    JMenuItem menuItemAbout = new JMenuItem();

    public MapConverter() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        toolBar.setFloatable(false);
        toolbarButtonExtractMaterials.setText("Step2 Extract Materials");
        toolbarButtonExtractMaterials.setFocusable(false);
        toolbarButtonExtractMaterials.setHorizontalTextPosition(SwingConstants.CENTER);
        toolbarButtonExtractMaterials.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolbarButtonExtractMaterials.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                toolbarButtonExtractMaterialsActionPerformed(evt);
            }
        });
        toolBar.add(toolbarButtonExtractMaterials);

        toolbarButtonConvertMap.setText("Step3 Convert Quake2 Map");
        toolbarButtonConvertMap.setFocusable(false);
        toolbarButtonConvertMap.setHorizontalTextPosition(SwingConstants.CENTER);
        toolbarButtonConvertMap.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolbarButtonConvertMap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                toolbarButtonConvertMapActionPerformed(evt);
            }
        });
        toolBar.add(toolbarButtonConvertMap);

        toolbarButtonExamples.setText("Fill TextFields With Examples");
        toolbarButtonExamples.setFocusable(false);
        toolbarButtonExamples.setHorizontalTextPosition(SwingConstants.CENTER);
        toolbarButtonExamples.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolbarButtonExamples.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                toolbarButtonExamplesActionPerformed(evt);
            }
        });
        toolBar.add(toolbarButtonExamples);

        textFieldPathToMap.setText("Path To Quake2 Map File");

        buttonSelectMap.setText("Select Quake2 Map File");
        buttonSelectMap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonSelectMapActionPerformed(evt);
            }
        });

        textFieldPathToEmap.setText("Path To Prodeus Emap File");

        buttonSelectEmap.setText("Select Emap File");
        buttonSelectEmap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonSelectEmapActionPerformed(evt);
            }
        });

        textAreaForLogs.setColumns(20);
        textAreaForLogs.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        textAreaForLogs.setRows(5);
        textAreaForLogs.setText("Step1: Select all four files and folders.\n\nThe Quake 2 Neural Upscaled Textures can be found here: https://github.com/Calinou/quake2-neural-upscale\nPlease select the downloaded quake2-neural-upscale-textures-2.0.1.pkz file.\n\nThe Path to the Prodeus Materials Folder is something like:\nxxxPATH_TO_STEAMxxx\\Steam\\steamapps\\common\\Prodeus\\Prodeus_Data\\StreamingAssets\\Materials\n\nThe Base1.map file can be found here:\nhttps://icculus.org/gtkradiant/downloads/1.5/gamepacks/q2-example-maps-2004-12-06.tar.gz\nThis file can be extracted using 7-Zip: https://www.7-zip.org/\n\nThe Path to the Prodeus Custom Maps Folder is something like:\nC:\\Users\\xxxYOUR_USERNAMExxx\\AppData\\LocalLow\\BoundingBoxSoftware\\Prodeus\\LocalData\\Maps\n\nYou might need to run this app with Admin priviledges in order to write to these folders.\n\n\n");
        scrollPaneLogs.setViewportView(textAreaForLogs);

        textFieldPathToMaterials.setText("Path To Prodeus Materials Folder");

        buttonSelectPathToMaterials.setText("Select Path To Materials");
        buttonSelectPathToMaterials.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonSelectPathToMaterialsActionPerformed(evt);
            }
        });

        textFieldPathToQ2TexturesZip.setText("Path To Quake2 Neural Textures Zip");

        buttonSelectTexturesZip.setText("Select Textures Zip File");
        buttonSelectTexturesZip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonSelectTexturesZipActionPerformed(evt);
            }
        });

        menuFile.setText("File");

        menuItemClose.setText("Close");
        menuFile.add(menuItemClose);

        menuBar.add(menuFile);

        menuHelp.setText("Help");

        menuItemAbout.setText("About");
        menuHelp.add(menuItemAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneLogs)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(textFieldPathToQ2TexturesZip, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                            .addComponent(textFieldPathToMaterials, GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldPathToMap, GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldPathToEmap, GroupLayout.Alignment.LEADING))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(buttonSelectMap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonSelectEmap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonSelectPathToMaterials, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addComponent(buttonSelectTexturesZip, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPathToQ2TexturesZip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSelectTexturesZip))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPathToMaterials, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSelectPathToMaterials))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPathToMap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSelectMap))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPathToEmap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSelectEmap))
                .addGap(18, 18, 18)
                .addComponent(scrollPaneLogs, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSelectTexturesZipActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buttonSelectTexturesZipActionPerformed
        jFileChooserOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = jFileChooserOpen.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            File f= jFileChooserOpen.getSelectedFile();
            textFieldPathToQ2TexturesZip.setText(f.toString());
        }
    }//GEN-LAST:event_buttonSelectTexturesZipActionPerformed

    private void buttonSelectPathToMaterialsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buttonSelectPathToMaterialsActionPerformed
        jFileChooserOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = jFileChooserOpen.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
          File f= jFileChooserOpen.getSelectedFile();
          textFieldPathToMaterials.setText(f.toString());
        }
    }//GEN-LAST:event_buttonSelectPathToMaterialsActionPerformed

    private void buttonSelectMapActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buttonSelectMapActionPerformed
        jFileChooserOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Quake2 Map File", "map");
        jFileChooserOpen.setFileFilter(fnef);
        int option = jFileChooserOpen.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            File f= jFileChooserOpen.getSelectedFile();
            textFieldPathToMap.setText(f.toString());
        }
    }//GEN-LAST:event_buttonSelectMapActionPerformed

    private void buttonSelectEmapActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buttonSelectEmapActionPerformed
        jFileChooserSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Prodeus Emap File", "emap");
        jFileChooserSave.setFileFilter(fnef);
        int option = jFileChooserSave.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            File f= jFileChooserSave.getSelectedFile();
            textFieldPathToEmap.setText(f.toString());
        }
    }//GEN-LAST:event_buttonSelectEmapActionPerformed

    private void toolbarButtonExtractMaterialsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_toolbarButtonExtractMaterialsActionPerformed
        String zipFile = textFieldPathToQ2TexturesZip.getText();
        String pathMat = textFieldPathToMaterials.getText();
        GenerateTexturesFromPngZip gen = new GenerateTexturesFromPngZip(zipFile, pathMat);
        try {
            gen.generate();
            textAreaForLogs.setText("Success... I think!");
        } catch (IOException ex) {
            textAreaForLogs.setText(ex.getMessage());
            Logger.getLogger(MapConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toolbarButtonExtractMaterialsActionPerformed

    private void toolbarButtonConvertMapActionPerformed(ActionEvent evt) {//GEN-FIRST:event_toolbarButtonConvertMapActionPerformed
        try {
            String map = textFieldPathToMap.getText();
            String emap = textFieldPathToEmap.getText();
            String materials = textFieldPathToMaterials.getText();
            Map2Emap.convert( map, emap, materials);
            textAreaForLogs.setText("Success... I think! (Use the level editor to play.)");
        } catch (IOException ex) {
            textAreaForLogs.setText(ex.getMessage());
            Logger.getLogger(MapConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toolbarButtonConvertMapActionPerformed

    private void toolbarButtonExamplesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_toolbarButtonExamplesActionPerformed
        textFieldPathToQ2TexturesZip.setText("D:\\GameDev\\ProdeusProjectLinks\\quake2-neural-upscale-textures-2.0.1.zip");
        textFieldPathToEmap.setText("C:\\Users\\Ray\\AppData\\LocalLow\\BoundingBoxSoftware\\Prodeus\\LocalData\\Maps\\base1.emap");
        textFieldPathToMap.setText("D:\\GameDev\\ProdeusProjectLinks\\base1.map");
        textFieldPathToMaterials.setText("D:\\Action\\Steam\\steamapps\\common\\Prodeus\\Prodeus_Data\\StreamingAssets\\Materials");
    }//GEN-LAST:event_toolbarButtonExamplesActionPerformed

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MapConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MapConverter().setVisible(true);
            }
        });
    }
}
