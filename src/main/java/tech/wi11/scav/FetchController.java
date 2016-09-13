package tech.wi11.scav;

import tech.wi11.scav.models.ScUrl;
import tech.wi11.scav.models.Site;
import tech.wi11.scav.models.Sites;
import tech.wi11.scav.models.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Connects the user interface with actions.
 */
public class FetchController implements Initializable {
	
	/**
	 * The field for the user to enter their SpeedCurve API key.
	 */
    @FXML
    private TextField apiKey;
	
	/**
	 * A grid to place buttons for each of the sites owned by the user's account.
	 */
	@FXML
	private GridPane siteButtonsGrid;
	
	/**
	 * A multi-line text field to display the tests.
	 */
	@FXML
	private TextArea resultArea;
	
	/**
	 * A label informing the user of what happens when a site button is clicked.
	 */
	@FXML
	private Label selectSiteLabel;
	
	/**
	 * A label suggesting the next step with the data once it has been retrieved by the tool.
	 */
	@FXML
	private Label copyLabel;
    
	/**
	 * Handles the request sites button click.
	 * @param event 
	 */
    @FXML
    private void requestSites(ActionEvent event) {
		Fetcher.setApiKey(new FetcherAuth(apiKey.getText()));
		try {
			Sites sites = Fetcher.getSites();
			showSiteButtons(sites);
		} catch (IOException ex) {
			Logger.getLogger(FetchController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
	/**
	 * Initializes the view.
	 * @param url
	 * @param rb 
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultArea.setVisible(false);
		selectSiteLabel.setVisible(false);
		copyLabel.setVisible(false);
    }

	/**
	 * Generates buttons which will lead to the sites being displayed.
	 * @param sites 
	 */
	private void showSiteButtons(Sites sites) {
		int index = 0;
		for (Site site : sites.sites) {
			index++;
			Button siteButton = new Button(site.name);
			siteButton.setOnAction(new SiteClick(resultArea, site));
			siteButtonsGrid.add(siteButton, index, 1);
		}
		selectSiteLabel.setVisible(true);
	}
	
	/**
	 * Handles a site button being clicked by displaying test results for that site.
	 */
	class SiteClick implements EventHandler {

		private final Site site;
		private String results;
		
		/**
		 * Initializes an instance of the SiteClick object.
		 * @param resultsGrid
		 * @param site 
		 */
		public SiteClick(TextArea resultArea, Site site) {
			this.site = site;
			this.results = "";
		}
		
		/**
		 * Handles the button click.
		 * @param event 
		 */
		@Override
		public void handle(Event event) {
			try {
				this.results = "timestamp\trun\tbrowser\tbrowser_version\tviewport_width\tviewport_height\tbytes\trender\tvisually_complete\tdom\tloaded\tsize\timage_saving\trequests\tpagespeed\tspeedindex\thtml_requests\thtml_size\tcss_requests\tcss_size\tjs_requests\tjs_size\timage_requests\timage_size\tfont_requests\tfont_size\ttext_requests\ttext_size\tflash_requests\tflash_size\tother_requests\tother_size\thar\tscreen\turl\n";
				for (ScUrl url : this.site.urls) {
					ScUrl fullUrl = Fetcher.getUrl(url.url_id);
					for (Test test : fullUrl.tests) {
						if (test.render > 0) {
							this.results += String.format("%s\t%s\n", test.getTextRow(), url.url);
						}
					}
				}
				resultArea.setText(results);
				resultArea.setVisible(true);
				copyLabel.setVisible(true);
				resultArea.selectAll();
				resultArea.requestFocus();
			} catch (IOException ex) {
				Logger.getLogger(FetchController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
