module bingo {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	
	opens bingo to javafx.fxml;
	exports bingo to javafx.graphics, javafx.fxml;
}