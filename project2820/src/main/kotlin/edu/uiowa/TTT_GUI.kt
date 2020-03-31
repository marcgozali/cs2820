package edu.uiowa

import com.sun.org.apache.xpath.internal.operations.Bool
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.stage.Stage

enum class GameState {
    X_WON, O_WON, TIE, RUNNING
}
/*
    The TTT application I created uses a inner class of Cell() to handle the work of the gameController using the handleClick() method. The change between
    JavaFX and my previous, text only, code created some necessary changes in how I structure the n x n board. In this case, I opted to use a method of
    making a GridPane and filling it with a Cell class that extends the Button() JavaFX class. By doing so, I was able to create a gridpane of buttons that
    are all handled by the same handleClick() method and can be used to check the game state after each click. Also I had trouble of implementing two players,
    so I instead used Markers to define each player rather than have a Player object per person.
 */
class TicTacToe : Application() {
    var board = Array(3){ _-> Array(3){ _-> Cell()}}
    var statusMsg = Label("X start")
    var currentPlayer = Markers.X
    // stage handles the creation of the GridPane and handles the usage of each one
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Tic Tac Toe"
        val pane = GridPane()
        for(i in 0..2) {
            for(j in 0..2) {
                pane.add(board[i][j],j,i)
            }
        }
        val border = BorderPane()
        border.center = pane
        border.bottom = statusMsg
        val exit = Button("Back to Menu")
        border.right = exit
        val scene = Scene(border, 300.0, 300.0)
        primaryStage.scene = scene
        exit.setOnAction { val m = Menu(); m.start(primaryStage)  }
        primaryStage.show()
    }
    // isBoardFull & hasWon are methods used to determine if the game is still on-going
    fun isBoardFull():Boolean {
        for(i in 0..2) {
            for(j in 0..2) {
                if(board[i][j].getMarker() == Markers.EMPTY) {
                    return false
                }
            }
        }
        return true
    }
    // checks if player with m marker has won the game
    fun hasWon(m:Markers):Boolean {
        for(i in 0..2) {
            if(board[i][0].getMarker() == m && board[i][1].getMarker() == m && board[i][2].getMarker() == m)
                return true
        }
        for(i in 0..2) {
            if(board[0][i].getMarker() == m && board[1][i].getMarker() == m && board[2][i].getMarker() == m)
                return true
        }
        if(board[0][0].getMarker() == m && board[1][1].getMarker() == m && board[2][2].getMarker() == m)
            return true
        if(board[0][2].getMarker() == m && board[1][1].getMarker() == m && board[2][0].getMarker() == m)
            return true
        return false
    }
    /*
    Cell extends the button class to create an inter-actable object that can handle the gamestate by one function handleClick()
     */
    inner class Cell : Button() {
        private var player = Markers.EMPTY
        init {
            style = "-fx-border-color : black"
            this.setPrefSize(100.0,100.0)
            this.setOnMouseClicked { e -> handleClick() }
        }
        // handleClick function makes use of the extended Pane class for each Cell such that they individually handle the pane clicks to enter the correct label
        private fun handleClick() {
            if (player == Markers.EMPTY && currentPlayer != Markers.EMPTY) {
                changePlayer(currentPlayer)
                when {
                    hasWon(currentPlayer) -> {
                        statusMsg.text = currentPlayer.name + " has won!"
                        currentPlayer = Markers.EMPTY
                    }
                    isBoardFull() -> {
                        statusMsg.text = "Draw"
                        currentPlayer = Markers.EMPTY
                    }
                    else -> {
                        if(currentPlayer == Markers.X) {
                            currentPlayer = Markers.O
                        } else if(currentPlayer == Markers.O) {
                            currentPlayer = Markers.X
                        }
                        statusMsg.text = currentPlayer.name + " turn"
                    }
                }
            }
        }
        //returns marker of currentPlayer
        fun getMarker():Markers {
            return player
        }
        // changes player of the board to their marker.
        private fun changePlayer(m:Markers) {
            player = m
            if(player == Markers.X) {
                //text = "X"
                text = "X"
            }
            else if(player == Markers.O) {
                text = "O"
            }
        }
    }
}

/*
The Ultimate board follows the same concept as the regular TTT board, however, it adds one more layer of GridPanes, each of which contain a miniBoard object, which
contains a Cell object. It adds one more layer of game state functions of hasWon, and isTie, to check the gamestate of not only each individual board, but also to the
overall Ultimate Board. I didn't really understand how to format the Cell class in this case, which is why I have duplicates of the Cell class. I think this could
be fixed if I created multiple classes i.e. Cell, MiniBoard, and UltimateBoard, however, that can easily be fixed at a later date.
 */
class Ultimate : Application() {
    val statusMsg = Label("X will start")
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Ultimate Tic Tac Toe"
        val border = BorderPane()
        border.center = UltimateBoard()
        border.bottom = statusMsg
        val menuButton = Button("Back to Menu")
        border.right = menuButton
        val scene = Scene(border, 900.0, 900.0)
        primaryStage.scene = scene
        menuButton.setOnAction { val m = Menu(); m.start(primaryStage) }
        primaryStage.show()
    }
}
/*
Ultimate board adds a layer of gridpanes to the miniBoards, which each control the grid of 3x3 Cells:Button(). I separated this class from the Ultimate application
class because it makes it easier to debug the errors between JavaFX syntax and game control syntax. This board also controls the game state by comparing the state
of each miniboard, and each miniboard controls the game state of each Cell. This makes sense to create imbedded classes that require the use of currentPlayer
that change after function calls. However, putting the currentPlayer to the global class created unexpected JavaFX errors, so the Ultimate board is unforunately
unfinished.
 */
class UltimateBoard : GridPane() {
    var ultimateWinner = Markers.EMPTY
    var statusMsg = Label("Help")
    var board = Array(3){ _-> Array(3){ _-> MiniBoard()}}
    init {
        this.setPrefSize(900.0,900.0)
        for(i in 0..2) {
            for(j in 0..2) {
                this.add(board[i][j],j,i)
            }
        }
    }
    // Checks if the Ultimateboard has been won through the other MiniBoards
    fun uHasWon(w : Markers):Boolean {
        for (i in 0..2) {
            if (board[i][0].winner == w && board[i][1].winner == w && board[i][2].winner == w) {
                return true
            }
        }
        for(i in 0..2) {
            if (board[0][i].winner == w && board[1][i].winner == w && board[2][i].winner == w) {
                return true
            }
        }
        if(board[0][0].winner == w && board[1][1].winner == w && board[2][2].winner == w) {
            return true
        }
        if(board[0][2].winner == w && board[1][1].winner == w && board[2][0].winner == w) {
            return true
        }
        return false
    }
    // checks if the ultimateboard is full based on the game state of the miniBoards in the board array
    fun ultisBoardFull():Boolean {
        for(i in 0..2) {
            for(j in 0..2) {
                if(board[i][j].winner == Markers.EMPTY) {
                    return false
                }
            }
        }
        return true
    }
    /*
    MiniBoard is just the Board class from the project2820.kt file, where it just assembles a 3 x 3 board of Cell() that each contain their own marker.
    It contains the hasWon and isFull methods to determine if the game is completed on each miniboard, then sends that information up to the UltimateBoard.
     */
    inner class MiniBoard : GridPane()  {
        var winner = Markers.EMPTY
        var currentPlayer = Markers.X
        var board = Array(3){ _-> Array(3){ _-> Cell()}}
        init {
            this.style = "-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;"
            this.setPrefSize(300.0,300.0)
            for(i in 0..2) {
                for(j in 0..2) {
                    this.add(board[i][j],j,i)
                }
            }
        }
        // checks if the mini board has won based on the Cell function
        fun hasWon(m:Markers):Boolean {
            for(i in 0..2) {
                if(board[i][0].getMarker() == m && board[i][1].getMarker() == m && board[i][2].getMarker() == m) {
                    winner = m
                    return true
                }
            }
            for(i in 0..2) {
                if (board[0][i].getMarker() == m && board[1][i].getMarker() == m && board[2][i].getMarker() == m) {
                    winner = m
                    return true
                }
            }
            if(board[0][0].getMarker() == m && board[1][1].getMarker() == m && board[2][2].getMarker() == m) {
                winner = m
                return true
            }
            if(board[0][2].getMarker() == m && board[1][1].getMarker() == m && board[2][0].getMarker() == m) {
                winner = m
                return true
            }
            return false
        }
        // checks if the board is full, and results in a tie
        fun isBoardFull():Boolean {
            for(i in 0..2) {
                for(j in 0..2) {
                    if(board[i][j].getMarker() == Markers.EMPTY) {
                        return false
                    }
                }
            }
            winner = Markers.TIE
            return true
        }
        /*
        Cell extends the button class to create an inter-actable object that can handle the gamestate by one function handleClick()
        */
        inner class Cell : Button() {
            var piece = Markers.EMPTY
            init {
                style = "-fx-border-color : black"
                this.setPrefSize(100.0,100.0)
                this.setOnMouseClicked { e -> handleClick() }
            }
            // returns marker of current player
            fun getMarker():Markers {
                return piece
            }
            // handles the game state by each click on the button
            fun handleClick() {
                if (piece == Markers.EMPTY && currentPlayer != Markers.EMPTY) {
                    changePlayer(currentPlayer)
                    if(hasWon(currentPlayer)) {
                        if(uHasWon(currentPlayer)) {
                            ultimateWinner = currentPlayer
                        }
                        currentPlayer = Markers.EMPTY
                    }
                    else if(isBoardFull()) {
                        if(ultisBoardFull()) {
                            ultimateWinner = Markers.TIE
                        }
                        currentPlayer = Markers.EMPTY
                    }
                    else {
                        if(currentPlayer == Markers.X) {
                            currentPlayer = Markers.O
                        } else if(currentPlayer == Markers.O) {
                            currentPlayer = Markers.X
                        }
                    }
                }
            }
            // changes current player
            fun changePlayer(m:Markers) {
                piece = m
                if(piece == Markers.X) {
                    text = "X"
                }
                else if(piece == Markers.O) {
                    text = "O"
                }
            }
        }
    }
}


fun main(args: Array<String>) {
    // you can do some testing here, though unit testing needs to be
    // in the src/test/java directory
    Application.launch(Ultimate::class.java, *args)
}