
import java.awt.Label;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author William
 */
public class DirectoryPanel extends javax.swing.JPanel {

    /**
     * Creates new form Test
     */
    private Client client;
    private DefaultListModel<String> files;

    public DirectoryPanel() {
        initComponents();
        client = Client.getInstance();
        lblSelectedFile.setText("None");
    }

    public void refreshFileListing() {
        files = client.fetchDirectoryListing();
        jlistFiles.removeAll();
        jlistFiles.setModel(files);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUpload = new javax.swing.JButton();
        btnDownload = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlistFiles = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabelSel = new javax.swing.JLabel();
        lblSelectedFile = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(380, 250));

        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnDownload.setLabel("Download");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });

        jlistFiles.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlistFilesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jlistFiles);

        jLabel1.setText("Files");

        jLabelSel.setText("Selected:");

        lblSelectedFile.setText("None");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelSel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSelectedFile))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUpload)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDownload)))
                        .addGap(0, 206, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSel)
                    .addComponent(lblSelectedFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpload)
                    .addComponent(btnDownload))
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("jLabelFiles");
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select file to testUpload");
        int returnval = jfc.showOpenDialog(this);
        if (returnval == JFileChooser.APPROVE_OPTION) {
            client.upload(jfc.getSelectedFile());
        }
        
        refreshFileListing();

    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        // TODO add your handling code here:
        if (!lblSelectedFile.getText().equals("") && !lblSelectedFile.getText().equals("None")) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Select destination");
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnval = jfc.showOpenDialog(this);
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath(), lblSelectedFile.getText());
                    System.out.println(f);
                    boolean result = client.download(f);
                    if (result)
                        JOptionPane.showMessageDialog(null, "Download Successful");
                    else
                        JOptionPane.showMessageDialog(null, "Download failed");
                           
                }
            }
    }//GEN-LAST:event_btnDownloadActionPerformed

    private void jlistFilesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlistFilesValueChanged
        // TODO add your handling code here:
        if (jlistFiles.getModel().getSize() > 0)
            lblSelectedFile.setText(jlistFiles.getSelectedValue().toString());
    }//GEN-LAST:event_jlistFilesValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnUpload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelSel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList jlistFiles;
    private javax.swing.JLabel lblSelectedFile;
    // End of variables declaration//GEN-END:variables
}
