import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class PokedexDemo extends Application {

	// Declare an arraylist of Strings for holding pokemon names
	private ArrayList<String> pkmNames = new ArrayList<>();
	private ArrayList<String> pkmWithMega = new ArrayList<>();
	private ArrayList<String> pkmWithMegaY = new ArrayList<>();
	private ArrayList<String> pkmWithGigantamax = new ArrayList<>();

	// Declare an arraylist of Strings for holding the starter pokemon's type
	private ArrayList<String> pkmFirstTyping = new ArrayList<>();
	private ArrayList<String> pkmSecondTyping = new ArrayList<>();
	private ArrayList<String> pkmThirdTyping = new ArrayList<>();

	// Declare an arraylist of Strings for holding starter pokemon descriptions
	private ArrayList<String> pkmNormalDescription = new ArrayList<>();
	private ArrayList<String> pkmMegaDescription = new ArrayList<>();
	private ArrayList<String> pkmMegaYDescription = new ArrayList<>();
	private ArrayList<String> pkmGigantamaxDescription = new ArrayList<>();

	// Declare and create a dex pane
	private DexPane dexPane = new DexPane();


	@Override
	public void start(Stage primaryStage) throws Exception {

		// Read pokemon names and details from file
		readNames();
		readPkmWithMega();
		readPkmWithMegaY();
		readPkmWithGigantamax();

		// Read pokemon typings from file
		readFirstTyping();
		readSecondTyping();
		readThirdTyping();

		// Read pokemon descriptions from file
		readNormalDescription();
		readMegaDescription();
		readMegaYDescription();
		readGigantamaxDescription();
		
		// Create a list of pokemon
		ObservableList<PokemonIcon> pokemonIcon = FXCollections.observableArrayList();
		for(int i = 0; i < pkmNames.size(); i++) {
			pokemonIcon.add(new PokemonIcon(pkmNames.get(i)));
		}

		// Create the ListView
		ListView<PokemonIcon> lv = new ListView<>();

		// Setup the CellFactory
		lv.setCellFactory(listView -> new ListCell<PokemonIcon>() {
			@Override
			protected void updateItem(PokemonIcon icon, boolean empty){
				super.updateItem(icon, empty);
			
				if(empty) {
					setGraphic(null);
				} 
				else {
					// Create a HBox to hold our displayed value
					HBox hBox = new HBox(5);
					hBox.setAlignment(Pos.CENTER_LEFT);

					// Add the values from our piece to the HBox
					hBox.getChildren().addAll(icon.getImage(), new Label(icon.getName()));

					// Set teh HBox as the display
					setGraphic(hBox);
				}
			}
		});

		// Bind the list of pieces to the ListView
		lv.setItems(pokemonIcon);
		lv.setPrefSize(200, 1100);
		lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		HBox titlePane = new HBox(50);
		ImageView b1 = new ImageView("Image/Sword.png");
		b1.setFitWidth(317);
		b1.setFitHeight(300);
		Label title = new Label("STARTER\nPOKEDEX");
		title.setFont(new Font("Arial", 60));
		title.setStyle("-fx-font-weight: bold");
		ImageView b2 = new ImageView("Image/Shield.png");
		b2.setFitWidth(317);
		b2.setFitHeight(300);
		BackgroundImage banner = new BackgroundImage(new Image("Image/Banner.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		titlePane.setBackground(new Background(banner));
		titlePane.getChildren().addAll(b1, title, b2);
		titlePane.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setTop(titlePane);
		pane.setLeft(new ScrollPane(lv));
		pane.setCenter(dexPane);

		setDisplay(0);

		lv.getSelectionModel().selectedItemProperty().addListener(
			ov -> {
				for(Integer i: lv.getSelectionModel().getSelectedIndices()) {
					setDisplay(i);
				}
			}
		);

		Scene scene = new Scene(pane, 1200, 850);
		primaryStage.getIcons().add(new Image("Image/Pokeball.png"));
		primaryStage.setTitle("Stater Pokedex");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void setDisplay(int index) {
		dexPane.setName(pkmNames.get(index));
		dexPane.setNormalAndShiny(pkmNames.get(index));
		dexPane.setAlternateForms(pkmWithMega.get(index), pkmWithMegaY.get(index), pkmWithGigantamax.get(index)); //
		dexPane.setFirstTyping(pkmFirstTyping.get(index));
		dexPane.setSecondTyping(pkmSecondTyping.get(index));
		dexPane.setThirdTyping(pkmThirdTyping.get(index));
		dexPane.setDescription(pkmNormalDescription.get(index));
		dexPane.setMegaDescription(pkmMegaDescription.get(index));
		dexPane.setMegaYDescription(pkmMegaYDescription.get(index));
		dexPane.setGigantamaxDescription(pkmGigantamaxDescription.get(index));
	}

	public void readNames() throws Exception {
		File file = new File("StarterPokedex/StarterPokemon.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmNames.add(input.nextLine());
		}
		input.close();
	}

	public void readPkmWithMega() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonWithMega.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmWithMega.add(input.nextLine());
		}
		input.close();
	}

	public void readPkmWithMegaY() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonWithMegaY.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmWithMegaY.add(input.nextLine());
		}
		input.close();
	}

	public void readPkmWithGigantamax() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonWithGigantamax.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmWithGigantamax.add(input.nextLine());
		}
		input.close();
	}

	public void readFirstTyping() throws Exception {
		File file = new File("StarterPokedex/StarterPokemon1stTyping.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmFirstTyping.add(input.nextLine());
		}
		input.close();
	}

	public void readSecondTyping() throws Exception {
		File file = new File("StarterPokedex/StarterPokemon2ndTyping.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmSecondTyping.add(input.nextLine());
		}
		input.close();
	}

	public void readThirdTyping() throws Exception {
		File file = new File("StarterPokedex/StarterPokemon3rdTyping.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			pkmThirdTyping.add(input.nextLine());
		}
		input.close();
	}

	public void readNormalDescription() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonDescription.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			String s = input.nextLine();
			s = s.replaceAll("\\\\n", System.lineSeparator());
			pkmNormalDescription.add(s);

		}
		input.close();
	}

	public void readMegaDescription() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonMegaDescription.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			String s = input.nextLine();
			s = s.replaceAll("\\\\n", System.lineSeparator());
			pkmMegaDescription.add(s);

		}
		input.close();
	}

	public void readMegaYDescription() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonMegaYDescription.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			String s = input.nextLine();
			s = s.replaceAll("\\\\n", System.lineSeparator());
			pkmMegaYDescription.add(s);

		}
		input.close();
	}

	public void readGigantamaxDescription() throws Exception {
		File file = new File("StarterPokedex/StarterPokemonGigantamaxDescription.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			String s = input.nextLine();
			s = s.replaceAll("\\\\n", System.lineSeparator());
			pkmGigantamaxDescription.add(s);

		}
		input.close();
	}
}

class PokemonIcon {
	private final String name;
	private final ImageView icon;

	public PokemonIcon(String p) {
		this.name = p;
		this.icon = new ImageView("Image/Icon/" + p + ".png");

		/*this.icon = new ImageView("Image/Icon2/" + p + ".png");
		this.icon.setFitWidth(50);
		this.icon.setFitHeight(50);*/
	}

	public String getName() {
		return name;
	}

	public ImageView getImage() {
		return icon;
	}
}