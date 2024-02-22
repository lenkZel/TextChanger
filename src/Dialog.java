import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This class represents a dialog window for saving changes made to a file.
 */
public class Dialog extends JDialog {
    private JButton selectbtn;
    private JButton dontsavebtn;
    private JButton savebtn;
    private JTextField selectedtxt;

    private JFileChooser fileChooser;
    private TextReplacer textReplacer;

    /**
     * Constructs a new instance of Dialog.
     * @param okno The parent JFrame to which the dialog is attached
     * @param textReplacer The TextReplacer object for handling file operations
     * @param fileChooser The JFileChooser object for file selection
     */
    public Dialog(JFrame okno, TextReplacer textReplacer, JFileChooser fileChooser){
        super(okno, true);
        this.textReplacer = textReplacer;
        this.fileChooser = fileChooser;
        this.setTitle("Save Changes");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2,1,10,10));
        this.addComponents();
        this.pack();

        //ActionListener for select button
        this.selectbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = fileChooser.showSaveDialog(Dialog.this);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    textReplacer.setFile(file);
                    selectedtxt.setText(file.getPath());
                }
            }
        });

        //ActionListener for dontsave button
        this.dontsavebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(Dialog.this, "If you don´t save it, changes will be lost!", "Don´t save", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
                if(result == JOptionPane.YES_OPTION){
                    Dialog.this.dispose();
                }
                dispose();
            }
        });

        //ActionListener for save button
        this.savebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    textReplacer.saveFile();
                    okno.dispose();
                } catch(IOException ex){
                    JOptionPane.showMessageDialog(Dialog.this, "File Error", "Failed to save changes to the file.", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });
    }

    /**
     * Adds components to the dialog.
     */
    private void addComponents() {
        JPanel select = new JPanel(new FlowLayout());
        select.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        this.selectedtxt = new JTextField();
        this.selectedtxt.setEnabled(false);
        this.selectedtxt.setColumns(25);
        select.add(this.selectedtxt);

        this.selectbtn = new JButton("Select File");
        select.add(this.selectbtn);
        this.add(select);

        JPanel options = new JPanel(new FlowLayout());
        this.dontsavebtn = new JButton("Don´t save");
        this.savebtn = new JButton("Save");
        options.add(dontsavebtn);
        options.add(savebtn);
        this.add(options);
    }
}
