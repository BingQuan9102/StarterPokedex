import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DexPane extends BorderPane {
	/*Label for the pokemon name*/
	private Label pkmName = new Label();
	private String name;
	private String megaName;
	private String megaYName;
	private String gigantamaxName;

	/*Image of the pokemon*/
	private ImageView pkmSprite = new ImageView();
	private Image normalSprite;
	private Image shinySprite;
	private Image megaSprite;
	private Image megaShinySprite;
	private Image megaYSprite;
	private Image megaYShinySprite;
	private Image gigantamaxSprite;
	private Image gigantamaxShinySprite;

	/*HBox for pokemon typing*/
	private HBox pkmTyping = new HBox();
	private HBox setOne = new HBox(5);
	private HBox setTwo = new HBox(5);
	private boolean alternateTyping = false;
	
	/*Check box for shiny sprite*/
	private CheckBox chkShiny = new CheckBox("Shiny");

	/*Radio buttons for mega forms*/
	private RadioButton rbNormal = new RadioButton("Normal");
	private RadioButton rbMega = new RadioButton("Mega");
	private RadioButton rbMegaY = new RadioButton("Mega Y");
	private RadioButton rbGigantamax = new RadioButton("Gigantamax");
	private HBox paneForAlternateForms = new HBox(20);

	private VBox leftWindow = new VBox(20);

	/*Text area for description*/
	private TextArea pkmDescription = new TextArea();
	private String normalDescription;
	private String megaDescription;
	private String megaYDescription;
	private String gigantamaxDescription;

	public DexPane() {
		StackPane imgPane = new StackPane();
		imgPane.setPrefSize(420, 300);
		imgPane.getChildren().add(pkmSprite);
		imgPane.setStyle("-fx-background-color: grey");

		BorderPane left = new BorderPane();
		left.setCenter(imgPane);
		left.setBottom(pkmName);
		left.setPadding(new Insets(5, 5, 5, 5));
		pkmName.setFont(new Font("SansSerif", 30));
		BorderPane.setAlignment(pkmName, Pos.CENTER);

		pkmTyping.setAlignment(Pos.CENTER);

		ToggleGroup alternateFormGroup = new ToggleGroup();
		rbNormal.setToggleGroup(alternateFormGroup);
		rbMega.setToggleGroup(alternateFormGroup);
		rbMegaY.setToggleGroup(alternateFormGroup);
		rbGigantamax.setToggleGroup(alternateFormGroup);
		paneForAlternateForms.getChildren().add(rbNormal); // Mega Y for later
		paneForAlternateForms.setAlignment(Pos.CENTER);

		leftWindow.getChildren().addAll(left, pkmTyping, chkShiny);
		leftWindow.setAlignment(Pos.TOP_CENTER);

		EventHandler<ActionEvent> imageDisplayHandler = e -> {
			// Besides changing the sprite, need to change name, change type, change description
			if(chkShiny.isSelected()) {
				if(rbMega.isSelected()) {
					pkmSprite.setImage(megaShinySprite);
					pkmName.setText(megaName);
					swapToAlternateTyping();
					pkmDescription.setText(megaDescription);
				}
				else if(rbMegaY.isSelected()) {
					pkmSprite.setImage(megaYShinySprite);
					pkmName.setText(megaYName);
					swapToOriginalTyping();
					pkmDescription.setText(megaYDescription);
				}
				else if(rbGigantamax.isSelected()) {
					pkmSprite.setImage(gigantamaxShinySprite);
					pkmName.setText(gigantamaxName);
					swapToOriginalTyping();
					pkmDescription.setText(gigantamaxDescription);
				}
				else {
					pkmSprite.setImage(shinySprite);
					pkmName.setText(name);
					swapToOriginalTyping();
					pkmDescription.setText(normalDescription);
				}
			}
			else {
				if(rbMega.isSelected()) {
					pkmSprite.setImage(megaSprite);
					pkmName.setText(megaName);
					swapToAlternateTyping();
					pkmDescription.setText(megaDescription);
				}
				else if(rbMegaY.isSelected()) {
					pkmSprite.setImage(megaYSprite);
					pkmName.setText(megaYName);
					swapToOriginalTyping();
					pkmDescription.setText(megaYDescription);
				}
				else if(rbGigantamax.isSelected()) {
					pkmSprite.setImage(gigantamaxSprite);
					pkmName.setText(gigantamaxName);
					swapToOriginalTyping();
					pkmDescription.setText(gigantamaxDescription);
				}
				else {
					pkmSprite.setImage(normalSprite);
					pkmName.setText(name);
					swapToOriginalTyping();
					pkmDescription.setText(normalDescription);
				}
			}
		};

		chkShiny.setOnAction(imageDisplayHandler);
		rbNormal.setOnAction(imageDisplayHandler);
		rbMega.setOnAction(imageDisplayHandler);
		rbMegaY.setOnAction(imageDisplayHandler);
		rbGigantamax.setOnAction(imageDisplayHandler);

		pkmDescription.setFont(new Font("Consolas", 20));
		pkmDescription.setPrefSize(550, 450);
		pkmDescription.setWrapText(true);
		pkmDescription.setEditable(false);
		ScrollPane scrollPane = new ScrollPane(pkmDescription);

		setLeft(leftWindow);
		setCenter(scrollPane);
		setPadding(new Insets(5, 5, 5, 5));
	}

	public void setName(String name) {
		this.name = name;
		pkmName.setText(name);
	}

	public void setNormalAndShiny(String img) {
		this.normalSprite = new Image("Image/Pokemon/" + img + ".gif");
		this.shinySprite = new Image("Image/Pokemon/" + img + " Shiny.gif");
		pkmSprite.setImage(normalSprite);
		setUncheck();
	}


	public void setAlternateForms(String mega, String megaY, String gigantamax) {
		rbMega.setText("Mega"); // Reset radio button to "Mega" incase for pokemon with one mega forms
		// *IDEA* In future there might have pokemons with gigantamax but without megas
		// Removing radio buttons from the back
		paneForAlternateForms.getChildren().remove(rbGigantamax); // Remove radio button for gigantamax form incase a pokemon does not have
		paneForAlternateForms.getChildren().remove(rbMegaY); // Remove radio button for second mega form incase a pokemon does not have
		paneForAlternateForms.getChildren().remove(rbMega); // Remove radio button for mega form incase a pokemon does not have
		leftWindow.getChildren().remove(paneForAlternateForms); // Remove radio button pane incase for pokemon with no alternate forms
		
		if(!mega.equals("-")) {
			this.megaSprite = new Image("Image/Mega/" + mega + " Mega.gif");
			this.megaShinySprite = new Image("Image/Mega/" + mega + " Mega Shiny.gif");
			this.megaName = "Mega " + mega;
			paneForAlternateForms.getChildren().add(rbMega);
			if(mega.equals("Greninja")) {
				rbMega.setText("Ash-Greninja"); 
				this.megaName = "Ash-Greninja";
			}
			
			if(!megaY.equals("-") ) {
				rbMega.setText("Mega X");
				this.megaYSprite = new Image("Image/Mega/" + megaY + " Mega.gif");
				this.megaYShinySprite = new Image("Image/Mega/" + megaY + " Mega Shiny.gif");
				this.megaYName = "Mega " + megaY;
				paneForAlternateForms.getChildren().add(rbMegaY);
			}
		}
		if(gigantamax.equals(this.name)) {
			this.gigantamaxSprite = new Image("Image/Gigantamax/" + gigantamax + " Gigantamax.gif");
			this.gigantamaxShinySprite = new Image("Image/Gigantamax/" + gigantamax + " Gigantamax Shiny.gif");
			this.gigantamaxName = "Gigantamax " + gigantamax;
			paneForAlternateForms.getChildren().add(rbGigantamax);
		}

		if(mega.equals(this.name) || gigantamax.equals(this.name)) {
			// There exist at least one alternate form of the pokemon
			leftWindow.getChildren().add(paneForAlternateForms);
			rbNormal.setSelected(true);
		}
	}

	public void setFirstTyping(String t) {
		pkmTyping.getChildren().clear();
		setOne.getChildren().clear();
		setTwo.getChildren().clear();
		alternateTyping = false;

		setOne.getChildren().add(new ImageView("Image/Type/" + t + ".png"));
		setTwo.getChildren().add(new ImageView("Image/Type/" + t + ".png"));
	}

	public void setSecondTyping(String t) {
		if(!t.equals("-")) {
			setOne.getChildren().add(new ImageView("Image/Type/" + t + ".png"));
		}
		pkmTyping.getChildren().add(setOne);
	}

	public void setThirdTyping(String t) {
		if(!t.equals("-")) {
			setTwo.getChildren().add(new ImageView("Image/Type/" + t + ".png"));
			alternateTyping = true;
		}
	}

	public void swapToOriginalTyping() {
		if(alternateTyping) {
			pkmTyping.getChildren().clear();
			pkmTyping.getChildren().add(setOne);
		}
	}

	public void swapToAlternateTyping() {
		if(alternateTyping) {
			pkmTyping.getChildren().clear();
			pkmTyping.getChildren().add(setTwo);
		}
	}

	public void setUncheck() {
		chkShiny.setSelected(false);
		rbNormal.setSelected(false);
		rbMega.setSelected(false);
		rbMegaY.setSelected(false);
	}

	public void setDescription(String normal) {
		this.normalDescription = normal;
		pkmDescription.setText(normalDescription);
	}

	public void setMegaDescription(String mega) {
		this.megaDescription = mega;
	}

	public void setMegaYDescription(String megaY) {
		this.megaYDescription = megaY;
	}

	public void setGigantamaxDescription(String gigantamax) {
		this.gigantamaxDescription = gigantamax;
	}
}