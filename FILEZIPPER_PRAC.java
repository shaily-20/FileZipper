import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class FILEZIPPER_PRAC extends JFrame {

    private final JFileChooser jFileChooser = new JFileChooser();
    private static final DefaultListModel<File> defaultListModel = new DefaultListModel<File>() {
        private final ArrayList<File> arrayList = new ArrayList<>();

        @Override
        public void addElement(File file) {
            arrayList.add(file);
            super.addElement(file);
        }

        @Override
        public File get(int index) {
            return arrayList.get(index);
        }

        @Override
        public File remove(int index) {
            arrayList.remove(index);
            return super.remove(index);
        }
    };
    private final JList<File> jList = new JList<>(defaultListModel);
    private final ArrayList<String> history = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FILEZIPPER_PRAC() {

        this.setTitle("File Zipper");
        this.setBounds(275, 300, 500, 400);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = menuBar.add(new JMenu("File"));
        Action addAction = new CustomAction("Add", "Add new file to Archive", "ctrl D");
        Action deleteAction = new CustomAction("Delete", "Delete selected files from the Archive", "ctrl U");
        Action zipAction = new CustomAction("Zip", "Zip the file", "ctrl Z");

        fileMenu.add(addAction);
        fileMenu.add(deleteAction);
        fileMenu.add(zipAction);

        JMenu settingsMenu = menuBar.add(new JMenu("Settings"));
        JMenuItem aboutUsItem = new JMenuItem("About Us");
        JMenuItem contactItem = new JMenuItem("Contact");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem historyItem = new JMenuItem("History");

        aboutUsItem.addActionListener(e -> JOptionPane.showMessageDialog(rootPane,
                "File Zipper App v1.0\nDeveloped by: [Your Name]",
                "About Us", JOptionPane.INFORMATION_MESSAGE));

        contactItem.addActionListener(e -> JOptionPane.showMessageDialog(rootPane,
                "For support, contact: support@example.com",
                "Contact", JOptionPane.INFORMATION_MESSAGE));

        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(rootPane,
                "To use this app, add files to the archive, delete them, or create a zip archive.",
                "Help", JOptionPane.INFORMATION_MESSAGE));

        historyItem.addActionListener(e -> showHistory());

        settingsMenu.add(aboutUsItem);
        settingsMenu.add(contactItem);
        settingsMenu.add(helpItem);
        settingsMenu.add(historyItem);

        JButton addButton = new JButton(addAction);
        JButton deleteButton = new JButton(deleteAction);
        JButton zipButton = new JButton(zipAction);
        JScrollPane jScrollPane = new JScrollPane(jList);

        jList.setBorder(BorderFactory.createEtchedBorder());

        addButton.setMinimumSize(new Dimension(80, 30));
        deleteButton.setMinimumSize(new Dimension(80, 30));
        zipButton.setMinimumSize(new Dimension(80, 30));
        jScrollPane.setPreferredSize(new Dimension(250, 300));

        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(jScrollPane, 100, 150, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(addButton)
                                        .addComponent(deleteButton)
                                        .addComponent(zipButton)
                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addComponent(addButton)
                                        .addComponent(deleteButton)
                                        .addGap(40, 40, 40)
                                        .addComponent(zipButton))
        );

        this.getContentPane().setLayout(layout);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public class CustomAction extends AbstractAction {
        public static final int BUFFER = 1024;

        public CustomAction(String name, String description, String shortcutKey) {
            this.putValue(Action.NAME, name);
            this.putValue(Action.SHORT_DESCRIPTION, description);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(shortcutKey));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Add":
                    addFileToArchive();
                    break;
                case "Delete":
                    deleteFileFromList();
                    break;
                case "Zip":
                    createZipArchive();
                    break;
                default:
                    break;
            }
        }

        private void addFileToArchive() {
            jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.setMultiSelectionEnabled(true);

            int result = jFileChooser.showDialog(rootPane, "Add File To Archive");

            if (result == JFileChooser.APPROVE_OPTION) {
                File[] files = jFileChooser.getSelectedFiles();

                for (File file : files) {
                    if (!isFileRepeating(file.getPath())) {
                        defaultListModel.addElement(file);
                        addToHistory("Added", file.getName());
                    }
                }
            }
        }

        private boolean isFileRepeating(String testedFile) {
            for (int i = 0; i < defaultListModel.getSize(); i++) {
                if (defaultListModel.get(i).getPath().equals(testedFile)) {
                    return true;
                }
            }
            return false;
        }

        private void deleteFileFromList() {
            int[] selectedIndices = jList.getSelectedIndices();
            for (int i = 0; i < selectedIndices.length; i++) {
                File removedFile = defaultListModel.get(selectedIndices[i] - i);
                defaultListModel.remove(selectedIndices[i] - i);
                addToHistory("Deleted", removedFile.getName());
            }
        }

        private void createZipArchive() {
            if (defaultListModel.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "No files to zip!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            jFileChooser.setSelectedFile(new File(System.getProperty("user.dir") + File.separator + "myArchive.zip"));
            jFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("ZIP Archives", "zip"));

            int result = jFileChooser.showDialog(rootPane, "Compress");

            if (result == JFileChooser.APPROVE_OPTION) {
                byte[] buffer = new byte[BUFFER];
                try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(jFileChooser.getSelectedFile()), BUFFER))) {
                    for (int i = 0; i < defaultListModel.getSize(); i++) {
                        File currentFile = defaultListModel.get(i);
                        if (!currentFile.isDirectory()) {
                            zipFile(zipOutputStream, currentFile, buffer, currentFile.getParentFile().getPath());
                        } else {
                            zipDirectory(zipOutputStream, currentFile, buffer, currentFile.getParentFile().getPath());
                        }
                    }
                    addToHistory("Zipped", jFileChooser.getSelectedFile().getName());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(rootPane, "Error during zipping: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void zipFile(ZipOutputStream zOutS, File filePath, byte[] buffer, String basePath) throws IOException {
            try (BufferedInputStream inS = new BufferedInputStream(new FileInputStream(filePath), BUFFER)) {
                String entryName = filePath.getPath().substring(basePath.length() + 1);
                zOutS.putNextEntry(new ZipEntry(entryName));

                int len;
                while ((len = inS.read(buffer)) != -1) {
                    zOutS.write(buffer, 0, len);
                }
                zOutS.closeEntry();
            }
        }

        private void zipDirectory(ZipOutputStream zOutS, File folder, byte[] buffer, String basePath) throws IOException {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        zipDirectory(zOutS, file, buffer, basePath);
                    } else {
                        zipFile(zOutS, file, buffer, basePath);
                    }
                }
            }
        }

        private void addToHistory(String action, String fileName) {
            String timestamp = dateFormat.format(new Date());
            history.add(action + " - " + fileName + " at " + timestamp);
        }
    }

    private void showHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "No history available.", "History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder historyText = new StringBuilder();
            for (String entry : history) {
                historyText.append(entry).append("\n");
            }
            JOptionPane.showMessageDialog(rootPane, historyText.toString(), "History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FILEZIPPER_PRAC().setVisible(true));
    }
}
