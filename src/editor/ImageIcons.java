package editor;
import javax.swing.ImageIcon;

public enum ImageIcons {
    OPEN(new ImageIcon("images/load-30.png")),
    SAVE(new ImageIcon("images/save-30.png")),
    START_SEARCH(new ImageIcon("images/search-30.png")),
    PREVIOUS_MATCH(new ImageIcon("images/left-30.png")),
    NEXT_MATCH(new ImageIcon("images/right-30.png"));

    final ImageIcon png;
    ImageIcons(ImageIcon png) {
        this.png = png;
    }
}
