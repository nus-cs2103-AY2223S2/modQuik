package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tutorial.Tutorial;

import java.util.logging.Logger;

/**
 * Panel containing the list of tutorials.
 */
public class TutorialListPanel extends UiPart<Region> {
    private static final String FXML = "TutorialListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TutorialListPanel.class);

    @javafx.fxml.FXML
    private ListView<Tutorial> tutorialListView;

    /**
     * Creates a {@code TutorialListPanel} with the given {@code ObservableList}.
     */
    public TutorialListPanel(ObservableList<Tutorial> tutorialList) {
        super(FXML);
        tutorialListView.setItems(tutorialList);
        tutorialListView.setCellFactory(listView -> new TutorialListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tutorial} using a {@code TutorialCard}.
     */
    class TutorialListViewCell extends ListCell<Tutorial> {
        @Override
        protected void updateItem(Tutorial tutorial, boolean empty) {
            super.updateItem(tutorial, empty);

            if (empty || tutorial == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TutorialCard(tutorial, getIndex() + 1).getRoot());
            }
        }
    }

}
