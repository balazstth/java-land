/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaLand.ui;

import JavaLand.util.ContainerMonitor;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author aladar
 */
public class NotepadInternalFrame extends javax.swing.JInternalFrame {

    //==========================================================================
    // Fields
    //==========================================================================    
    private String savefileName = "notepad.txt";
    private Charset savefileEncoding = StandardCharsets.UTF_16;
    private boolean saved = false;

    //==========================================================================
    // Singleton
    //==========================================================================
    /**
     * Creates new form NotepadInternalFrame
     */
    private NotepadInternalFrame() {
        initComponents();
        ContainerMonitor.registerContainer(this, NotepadInternalFrame.class.getName(),
                this.getX(), this.getY(), this.getWidth(), this.getHeight());
                
        // Initial load
        try {
            editorPane.setText(loadFiletoString(savefileName, savefileEncoding));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "There was an error while loading the Notepad.");
            Logger.getLogger(NotepadInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Save
        NotepadInternalFrame _this = this;        
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (;;) {
                    if (_this.isVisible() && saved == false) {
                        try {
                            saveStringToFile(savefileName, savefileEncoding, editorPane.getText());
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "There was an error while saving the Notepad.");
                            Logger.getLogger(NotepadInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }                    
                        // saved = true;
                    }
                    // Auto-save every ten seconds
                    Thread.sleep(10000);
                    // The thread never exits in its current form.
                }
            }
        }.execute();
    }
    
    public static NotepadInternalFrame getInstance() {
        return NotepadInternalFrameHolder.INSTANCE;
    }
    
    private static class NotepadInternalFrameHolder {
        private static final NotepadInternalFrame INSTANCE = new NotepadInternalFrame();
    }
    
    //==========================================================================
    // Dialogs opened from this JInternalFrame need this.
    // Invoke setMainFrame() just after calling the constructor.
    //==========================================================================
    private MainFrame mainFrame;
    
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    //==========================================================================
    // Desktop
    //==========================================================================
    private JDesktopPane desktop;
    
    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }
    
    //==========================================================================
    // Save
    //==========================================================================
    private void saveStringToFile(String path, Charset encoding, String text) throws IOException {
        Writer out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), encoding));
        try {
            out.write(text);
        } finally {
            out.close();
        }
    }
        
    //==========================================================================
    // Load
    //==========================================================================
    static String loadFiletoString(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        editorPane = new javax.swing.JEditorPane();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Notepad");
        setAutoscrolls(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        jScrollPane1.setViewportView(editorPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    // Hidden
    //==========================================================================
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        closedOrHidden();
        // Focus back
        mainFrame.lastFocus.pop();
        if (mainFrame.lastFocus.size() > 0) {
            mainFrame.show(mainFrame.lastFocus.getLast());
        } 
    }//GEN-LAST:event_formComponentHidden

    //==========================================================================
    // Closed
    //==========================================================================
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        closedOrHidden();
    }//GEN-LAST:event_formInternalFrameClosed

    private void closedOrHidden() {
        // Save
        try {
            saveStringToFile(savefileName, savefileEncoding, editorPane.getText());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "There was an error saving the Notepad.");
            Logger.getLogger(NotepadInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane editorPane;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
