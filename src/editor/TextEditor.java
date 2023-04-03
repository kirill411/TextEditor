package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;

public class TextEditor extends JFrame {
    FileManager fileManager;
    JFileChooser fileChooser = new JFileChooser(new File(".//"));
    JPanel innerPanel;
    JTextArea textArea;

    JTextField searchField;
    SearchResult searchResult;
    String currentSearchText;
    JCheckBox useRegexCheckBox;

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);

        fileChooser.setName("FileChooser");
        add(fileChooser);

        innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        innerPanel.setBackground(Color.LIGHT_GRAY);
        innerPanel.setBorder(new EmptyBorder(2, 5, 5, 5));
        innerPanel.add(getTextArea());
        innerPanel.add(getUpperPanel(), BorderLayout.NORTH);
        add(innerPanel);

        setJMenuBar(textEditorMenuBar());

        fileManager = new FileManager(fileChooser, textArea, this);
        setVisible(true);
    }

    JScrollPane getTextArea() {
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        textArea.setName("TextArea");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        return scrollPane;
    }

    JPanel getUpperPanel() {
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        upperPanel.setBackground(Color.LIGHT_GRAY);

        JButton openButton = new JButton(ImageIcons.OPEN.png);
        JButton saveButton = new JButton(ImageIcons.SAVE.png);
        searchField = new JTextField(20);
        JButton startSearchButton = new JButton(ImageIcons.START_SEARCH.png);
        JButton previousMatchButton = new JButton(ImageIcons.PREVIOUS_MATCH.png);
        JButton nextMatchButton = new JButton(ImageIcons.NEXT_MATCH.png);
        useRegexCheckBox = new JCheckBox("Use regex");

        searchField.setSize(new Dimension(this.getWidth() - 200, 20));

        openButton.setName("OpenButton");
        saveButton.setName("SaveButton");
        searchField.setName("SearchField");
        startSearchButton.setName("StartSearchButton");
        previousMatchButton.setName("PreviousMatchButton");
        nextMatchButton.setName("NextMatchButton");
        useRegexCheckBox.setName("UseRegExCheckbox");

        openButton.addActionListener(a -> fileManager.openFile());
        saveButton.addActionListener(a -> fileManager.saveFile());
        startSearchButton.addActionListener(a -> search(SearchResult.START_SEARCH));
        previousMatchButton.addActionListener(a -> search(SearchResult.PREVIOUS));
        nextMatchButton.addActionListener(a -> search(SearchResult.NEXT));

        upperPanel.add(openButton);
        upperPanel.add(saveButton);
        upperPanel.add(searchField);
        upperPanel.add(startSearchButton);
        upperPanel.add(previousMatchButton);
        upperPanel.add(nextMatchButton);
        upperPanel.add(useRegexCheckBox);

        return upperPanel;
    }

    JMenuBar textEditorMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");
        menuBar.add(menuFile);

        JMenuItem menuLoad = new JMenuItem("Load");
        JMenuItem menuSave = new JMenuItem("Save");
        JMenuItem menuExit = new JMenuItem("Exit");

        menuLoad.setName("MenuOpen");
        menuSave.setName("MenuSave");
        menuExit.setName("MenuExit");

        menuLoad.addActionListener(a -> fileManager.openFile());
        menuSave.addActionListener(a -> fileManager.saveFile());
        menuExit.addActionListener(a -> {
            dispose();
            System.exit(0);
        });

        menuFile.add(menuLoad);
        menuFile.add(menuSave);
        menuFile.addSeparator();
        menuFile.add(menuExit);

        JMenu menuSearch = new JMenu("Search");
        menuSearch.setName("MenuSearch");
        menuBar.add(menuSearch);

        JMenuItem startSearchItem = new JMenuItem("Start search");
        JMenuItem previousSearchItem = new JMenuItem("Previous search");
        JMenuItem nextSearchItem = new JMenuItem("Next match");
        JMenuItem useRegexItem = new JMenuItem("Use regular expression");

        startSearchItem.setName("MenuStartSearch");
        previousSearchItem.setName("MenuPreviousMatch");
        nextSearchItem.setName("MenuNextMatch");
        useRegexItem.setName("MenuUseRegExp");

        startSearchItem.addActionListener(a -> search(SearchResult.START_SEARCH));
        previousSearchItem.addActionListener(a -> search(SearchResult.PREVIOUS));
        nextSearchItem.addActionListener(a -> search(SearchResult.NEXT));
        useRegexItem.addActionListener(a -> useRegexCheckBox.doClick());

        menuSearch.add(startSearchItem);
        menuSearch.add(previousSearchItem);
        menuSearch.add(nextSearchItem);
        menuSearch.add(useRegexItem);

        return menuBar;
    }

    void search(int command) {
        if (command == SearchResult.START_SEARCH) {
            currentSearchText = searchField.getText();
            searchResult = new SearchResult(textArea, currentSearchText, useRegexCheckBox.isSelected());
            searchResult.execute();
        }

        if (currentSearchText == null || !currentSearchText.equals(searchField.getText())) {
            return;
        }

        if (command == SearchResult.PREVIOUS) {
            searchResult.previous();
        }

        if (command == SearchResult.NEXT) {
            searchResult.next();
        }
    }
}