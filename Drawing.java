
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Drawing extends Application
{
  private RadioButton redButton, blueButton, bisqueButton, eraserButton;
  private RadioButton thickLine, normalLine, thinLine;
  private Button clearButton;
  private VBox primaryBox;
  private Rectangle drawingBox;
  private BorderPane paneDraw;
  private Circle circle;
  private Text penStatus;
  private int clickCount = 2;//start at 2 so when clicked, will draw first
	
  public static void main(String[] args)
  {
    launch(args);
  }

  public void start(Stage primaryStage)
  {	
    paneDraw = new BorderPane();
    primaryBox = new VBox();
    primaryBox.setAlignment(Pos.TOP_CENTER);
		
    penStatus = new Text(); 
    penStatus.setText("Pen: OFF");
    penStatus.setFont(Font.font(18));
    primaryBox.getChildren().add(penStatus);
		
    drawingBox = new Rectangle(12.5, 50, 475, 420); //drawing space
    drawingBox.setFill(Color.TRANSPARENT);
    drawingBox.setStroke(Color.BLACK);		
    paneDraw.getChildren().add(drawingBox);
    BorderPane.setAlignment(drawingBox, Pos.CENTER);
		
    primaryBox.getChildren().add(paneDraw);
		
    ToggleGroup buttonGroup = new ToggleGroup(); //ToggleGroup to group buttons together
    ToggleGroup thicknessGroup = new ToggleGroup();
		
    redButton = new RadioButton("Red");
    redButton.setToggleGroup(buttonGroup);
    redButton.setSelected(true); //red will be selected first
		
    blueButton = new RadioButton("Blue");
    blueButton.setToggleGroup(buttonGroup);
		
    bisqueButton = new RadioButton("Bisque");
    bisqueButton.setToggleGroup(buttonGroup);
		
    eraserButton = new RadioButton("Eraser");
    eraserButton.setToggleGroup(buttonGroup);
		
    clearButton = new Button("Clear"); //not part of toggle group
    clearButton.setOnAction(this::handleClearButton);
		
    thickLine = new RadioButton("Thick");
    thickLine.setToggleGroup(thicknessGroup);
		
    normalLine = new RadioButton("Normal");
    normalLine.setToggleGroup(thicknessGroup);
    normalLine.setSelected(true);
		
    thinLine = new RadioButton("Thin");
    thinLine.setToggleGroup(thicknessGroup);
		
		
    HBox buttonBox = new HBox(redButton, blueButton, bisqueButton, eraserButton, clearButton); //for buttons
    HBox thicknessBox = new HBox(thickLine, normalLine, thinLine); //for thickness of drawing Lines - Extra Feature
		
    thicknessBox.setAlignment(Pos.CENTER);
    thicknessBox.setSpacing(10);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(10);
		
    primaryBox.getChildren().add(buttonBox); //add the HBox to the primaryBox	
    primaryBox.getChildren().add(thicknessBox); //add this HBox to primaryBox as well
		
		
    paneDraw.setOnMouseClicked(this::handleMouseClick);
				
    Scene scene = new Scene(primaryBox, 500, 500, Color.TRANSPARENT);
    primaryStage.setTitle("Draw Something"); //create the window for the program
    primaryStage.setScene(scene);
    primaryStage.setResizable(false); //dont allow user to be able to make the box larger
    primaryStage.show();
  }
	
  public void handleEraserMouse(MouseEvent event)
  {
    double x = event.getX();
    double y = event.getY();
		
    if(thickLine.isSelected())
	    circle = new Circle(x, y, 10); //create circle depending on preferred brush size
    if(normalLine.isSelected())
	    circle = new Circle(x, y, 6); 
    if(thinLine.isSelected())
      circle = new Circle(x, y, 3);
    
    if(circle instanceof Circle)
      paneDraw.getChildren().remove(event.getTarget());
  }

  public void handleClearButton(ActionEvent event)
  {
	  paneDraw.getChildren().clear(); //clear out the drawing
	  paneDraw.getChildren().add(drawingBox); //read the drawing box
  }
	
  private void handleMouseClick(MouseEvent event)
  {
	  if(clickCount % 2 == 0) //click count is even
	  {
	    if(!eraserButton.isSelected())
	    {
		    penStatus.setText("Pen: DRAW"); //draw
		    paneDraw.setOnMouseMoved(this::handleMouseDrawing);
		    clickCount++; //then add counter
	    }
	    else
	    {
		    penStatus.setText("Pen: ERASE"); //draw
		    paneDraw.setOnMouseMoved(this::handleEraserMouse);
		    clickCount++;
	    }
    }
	  else //if not, turn of and turn off drawing
	  {
	    penStatus.setText("Pen: OFF"); 
	    paneDraw.setOnMouseMoved(null);
	    clickCount++;
	  }
  }
	
  private void handleMouseDrawing(MouseEvent event) //function for drawing
  {
	  double x = event.getX();
	  double y = event.getY();
		
	  if(thickLine.isSelected())
	    circle = new Circle(x, y, 10); //create circle depending on preferred brush size
	  if(normalLine.isSelected())
	    circle = new Circle(x, y, 6); 
	  if(thinLine.isSelected())
	    circle = new Circle(x, y, 3);
		
	  if(redButton.isSelected()) //check to see which radio button is connected to get the color
	    circle.setFill(Color.RED);
	  if(blueButton.isSelected())
	    circle.setFill(Color.BLUE);
	  if(bisqueButton.isSelected())
	    circle.setFill(Color.BISQUE);
		
	  paneDraw.getChildren().add(circle);
  }
}
