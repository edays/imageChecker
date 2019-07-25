import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class View extends JFrame implements Observer{

    private Model model;
    private JPanel contents;
    private JButton openButton;
    private JButton saveButton;

    public View(Model model) {

        // Set up the frame
        setTitle("Image Comparator");
        int minDimensionWidth = 300;    // Size configuration
        int minDimensionHeight = 150;
        setMinimumSize(new Dimension(minDimensionWidth, minDimensionHeight));
        setPreferredSize(new Dimension(500, 400));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // set up button for open and run
        openButton = new JButton("Open...");
        openButton.setMaximumSize(new Dimension(100, 50));
        openButton.setPreferredSize(new Dimension(100, 50));
        openButton.addActionListener(new OpenButtonListener());

        // set up button for save
        saveButton = new JButton("Save...");
        saveButton.setMaximumSize(new Dimension(100, 50));
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.addActionListener(new SaveButtonListener());

        // set content panel and layout
        contents = new JPanel();
        contents.setLayout(new GridBagLayout());
        contents.add(openButton, new GridBagConstraints());
        contents.add(saveButton, new GridBagConstraints());

        setContentPane(contents);
        setVisible(true);

        this.model = model;
        model.addObserver(this);
    }

    /**
     * Button Listener for open button
     */
    class OpenButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("csv files", "csv");
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            int ret = fileChooser.showOpenDialog(View.this);

            switch (ret) {
                case JFileChooser.APPROVE_OPTION:
                    File file = fileChooser.getSelectedFile();
                    model.startProcess(file.getAbsolutePath());   // start campare and output
                    break;
                case JFileChooser.CANCEL_OPTION:
                    return;
                case JFileChooser.ERROR_OPTION:
                    return;
            }
        }
    }

    /**
     * Button listener for save button
     */
    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("csv files", "csv");
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            int ret = fileChooser.showSaveDialog(View.this);
            if (ret != 0) return;

            // get file name
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(filename);
            filename += ".csv"; // add extension
            model.saveResult(filename);
        }
    }


    /**
     * If this method is called, show an update dialog
     */
    @Override
    public void update() {
        // create a dialog that notifies user of the new update
        JDialog dialog = new JDialog(this);
        dialog.setSize(300,100);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);  // Do not update by closing the dialog
        dialog.setLayout(new GridLayout(0, 1));
        dialog.setLocationRelativeTo(this); // appears in the middle of this view
        dialog.setVisible(true);

        // Give user useful instructions
        JPanel messagePanel = new JPanel(new GridLayout(0, 1));
        JLabel messageLabel = new JLabel("A new version is available.");
        JLabel messageLabel2 = new JLabel("Visit www.loblawdigital.com for details!");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        messageLabel2.setFont(new Font("SansSerif", Font.PLAIN, 15));
        messagePanel.add(messageLabel);
        messagePanel.add(messageLabel2);

        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Cancel");
        JButton confirmButton = new JButton("Update");
        // Actions if user press the specific button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSoftware();
            }
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        dialog.add(messagePanel);
        dialog.add(buttonPanel);
    }
}
