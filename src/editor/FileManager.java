package editor;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class FileManager {
    JFileChooser fileChooser;
    JTextArea textArea;
    JFrame frame;

    FileManager(JFileChooser fileChooser, JTextArea textArea, JFrame frame) {
        this.fileChooser = fileChooser;
        this.textArea = textArea;
        this.frame = frame;
    }

    void openFile() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = fileChooser.showOpenDialog(frame);

        if (option != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile())){
            textArea.setText(new String(fis.readAllBytes(), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            textArea.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveFile() {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showSaveDialog(frame.getContentPane());

        if (option != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())){
            fos.write(textArea.getText().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
