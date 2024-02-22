import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This class represents the main frame of the application.
 */
public class AppFrame extends JFrame {
    private JButton openbtn;
    private JButton replacebtn;

    private JLabel firstapplbl;
    private JLabel secondapplbl;

    private JTextField firstapptxt;
    private JTextField secondapptxt;
    private JTextField thirdapptxt;

    private JFileChooser fileChooser;
    private TextReplacer textReplacer;

    /**
     * Constructs a new instance of AppFrame.
     */
    public AppFrame(){
        addComponents();    //Adds components to the frame
        init();             //Initializes the frame properties and layout

        //Initialize file chooser and text replacer
        this.fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
        this.fileChooser.setFileFilter(filter);
        this.textReplacer = new TextReplacer();

        //ActionListener for open button
        this.openbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = fileChooser.showOpenDialog(AppFrame.this);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(!file.isFile() || !file.canRead()){
                        JOptionPane.showMessageDialog(AppFrame.this, "Wrong file!", "File error", JOptionPane.ERROR_MESSAGE);
                        file = null;
                    }
                    textReplacer.setFile(file);
                    thirdapptxt.setText(file.getPath());
                }
            }
        });

        //ActionListener for replace button
        this.replacebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String original = "";
                String neww = "";
                try{
                    original = firstapptxt.getText();
                    if(original.equals("")){
                        throw new IllegalArgumentException();
                    }
                    neww = secondapptxt.getText();
                    if(neww.equals("")){
                        throw new IllegalArgumentException();
                    }
                } catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(AppFrame.this, "Please enter original and new words.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                textReplacer.setOriginalWord(original);
                textReplacer.setNewWord(neww);
                if(textReplacer.replaceWords()){
                    Dialog save = new Dialog(AppFrame.this, textReplacer, fileChooser);
                    save.setVisible(true);
                    firstapptxt.setText("");
                    secondapptxt.setText("");
                    textReplacer.setFile(null);
                }else{
                    JOptionPane.showMessageDialog(AppFrame.this, "Failed to replace words.", "Replacement Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Initializes the frame properties and layout.
     */
    private void init() {
        setTitle("TextChanger");
        setPreferredSize(new Dimension(500,350));
        setLayout(new GridLayout(3,1,10,10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    /**
     * Adds components to the frame.
     */
    private void addComponents(){
        JPanel textChanger = new JPanel(new GridLayout(1,2,10,10));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        this.firstapplbl = new JLabel("Original Word: ");
        this.firstapptxt = new JTextField("");
        left.add(this.firstapplbl);
        left.add(this.firstapptxt);
        textChanger.add(left);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        this.secondapplbl = new JLabel("New Word: ");
        this.secondapptxt = new JTextField("");
        right.add(this.secondapplbl);
        right.add(this.secondapptxt);
        textChanger.add(right);
        this.add(textChanger);

        JPanel chooser = new JPanel(new FlowLayout());
        chooser.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        this.thirdapptxt = new JTextField("Select File");
        this.thirdapptxt.setEnabled(false);
        this.thirdapptxt.setColumns(25);
        chooser.add(this.thirdapptxt);

        this.openbtn = new JButton("Open");
        chooser.add(openbtn);
        this.add(chooser);

        this.replacebtn = new JButton("Replace".toUpperCase());
        this.replacebtn.setBackground(Color.LIGHT_GRAY);
        this.replacebtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        this.replacebtn.setForeground(Color.BLACK);
        this.replacebtn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        this.add(this.replacebtn);
    }
}
