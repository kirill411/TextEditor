package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResult extends SwingWorker<Void, MatchResult> {
    static final int START_SEARCH = 0;
    static final int PREVIOUS = 1;
    static final int NEXT = 2;

    final List<MatchResult> results = new ArrayList<>();
    int matchID = 0;
    Matcher matcher;
    JTextArea textArea;

    SearchResult(JTextArea textArea, String searchTarget, boolean regex) {
        this.textArea = textArea;
        matcher = regex ?
                Pattern.compile(searchTarget, Pattern.MULTILINE).matcher(textArea.getText()) :
                Pattern.compile(Pattern.quote(searchTarget), Pattern.MULTILINE).matcher(textArea.getText());
    }

    @Override
    protected Void doInBackground() throws Exception {
        boolean firstMatch = false;
        while (matcher.find()) {
            if (!firstMatch) {
                publish(matcher.toMatchResult());
                firstMatch = true;
            }
            results.add(matcher.toMatchResult());
        }
        return null;
    }

    @Override
    protected void process(List<MatchResult> chunks) {
        selectText(chunks.get(0));
    }

    void previous() {
        matchID = (matchID + results.size() - 1) % results.size();
        selectText(results.get(matchID));
    }

    void next() {
        matchID = (matchID + 1) % results.size();
        selectText(results.get(matchID));
    }

    void selectText(MatchResult match) {
        textArea.setCaretPosition(match.end());
        textArea.select(match.start(), match.end());
        textArea.grabFocus();
    }
}
