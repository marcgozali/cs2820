package edu.uiowa

import javafx.application.Application
import javafx.fxml.FXML
import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.stage.Stage


// this is where you will begin the first version of your project
// but you will also need to connect this project with your github.uiowa.edu
// repository, as explained in the discussion section 9th November

/*
    Markers are the X's and O's of the board. It includes an X, O, and EMPTY to indicate the status of each cell on the board
    TIE wasn't fully implemented, as it was intended to be a gamestate enum, however, I added it so late into the project that
    I didn't change it.
 */
enum class Markers {
    X, O, EMPTY, TIE
}

/*
    The board interface only implements the necessary functions when using a board. In this case, its all of the functions of the Board class
*/

interface Boards {
    //setSize creates an n x n size board, taking in an integer.
    fun setSize(x:Int) //doesnt work with drawBoard --> index out of bounds when size > 3
    //The putMarker function takes in an index and places it according to the boardIndex().
    fun putMarker(index:Int)
    //The reset function resets the board back to an empty n x n size board.
    fun reset()
    //The drawBoard function creates a text visualization of the board so that the players can follow along.
    fun drawBoard()
    //boardIndex() is used to index each position on the board, and allow easy access to place markers using only 1 number, rather than understanding how to use array indices.
    fun boardIndex()
}

/*
    The board class creates a row x col array of Markers that are initially set to EMPTY, and use various functions to adjust the board.
    The functionality of each method is explained in both the interface comments, as well as the class comments.
 */

class Board:Boards {
    var rows = 3; var cols = 3
    var board = Array(rows){ _-> Array(cols){ _-> Markers.EMPTY}}
    //changes board size
    override fun setSize(x: Int) {
        rows = x; cols = x
        board = Array(rows){ _-> Array(cols){ _-> Markers.EMPTY}}
    }
    //place markers @ index
    override fun putMarker(index: Int) {
        if(board[index / rows][index % cols] != Markers.EMPTY) {
            print("Position already taken")
        }
        else {
            board[index / rows][index % cols] = Markers.X
        }
    }
    //Re-initalize the board to EMPTY
    override fun reset() {
        board = Array(rows){ _-> Array(cols){ _-> Markers.EMPTY}}
    }
    //draws board using text
    override fun drawBoard() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                when {
                    board[row][col] == Markers.X -> print("X ")
                    board[row][col] == Markers.O -> print("O ")
                    else -> print("  ")
                }
                if (col < cols - 1)
                    print("|")
            }
            println()
            if (row < rows - 1) {
                println("-- ".repeat(cols))
            }
        }
    }
    //indexes board for ease of gameplay
    override fun boardIndex() {
        var index = 0
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                print(" $index ")
                index++
                if (col < cols - 1)
                    print("|")
            }
            println()
            if (row < rows - 1) {
                println("-- ".repeat(cols))
            }
        }
    }
}

// Ultimate board not implemented in the text version

/*
    Players interface contains only methods to adjust the number of wins of each player.
 */

interface Players {
    //adds win to player
    fun addWin()
    //returns number of wins of player
    fun numWins():Int
    //resets wins back to 0
    fun resetWins()
}

/*
    The name of each player shouldn't be adjusted after being set, and rather than adding a get/set function for their symbol,
    I opted to use just the variable as it's easier to get and set with 1 call rather than 2. i.e. player.getSymbol(), player.setSymbol() versus player.symbol
    Needless to say the function names are very self explanatory.
 */

class Player(_name:String, x:Markers):Players {
    val name = _name
    var symbol = x
    var wins = 0
    //adds win to player
    override fun addWin() {
        wins++
    }
    //returns number of wins of player
    override fun numWins():Int {
        return wins
    }
    //resets wins back to 0
    override fun resetWins() {
        wins = 0
    }
}
/*
    gameInterface has just the play function, as the game controller handles more of the intricacies of the game, and other methods are private.
 */
interface GameInterface {
    // play method essentially combines the methods of board and players, and use gameController methods to check if the game has been won or tied.
    fun play()
}
/*
    GameController uses a board object and 2 players to control the game. It uses two functions to check the game state, i.e. winCondition and isTie,
    and uses changeCurrPlayer, and putMarker to adjust the board accordingly. I added another putmarker function here, because it didn't work if I used the
    method from the board class.
 */
class GameController:GameInterface {
    private val rows = 3
    private val cols = 3
    private var B = Board()
    private var player1 = Player("p1", Markers.X)
    private var player2 = Player("p2", Markers.O)
    private var currentPlayer = player1
    //checks rows cols and then diagonals if the players marker has won the game
    private fun winCondition(x:Markers):Boolean {
        //check rows
        if((B.board[0][0] == x && B.board[0][1] == x && B.board[0][1] == x)||(B.board[1][0] == x && B.board[1][1] == x && B.board[1][1] == x)||(B.board[2][0] == x && B.board[2][1] == x && B.board[2][2] == x))
            return true
        if((B.board[0][0] == x && B.board[1][0] == x && B.board[2][0] == x)||(B.board[0][1] == x && B.board[1][1] == x && B.board[2][1] == x)||(B.board[0][2] == x && B.board[1][2] == x && B.board[2][2] == x))
            return true
        if((B.board[0][0] == x && B.board[1][1] == x && B.board[2][2] == x) || B.board[2][0] == x && B.board[1][1] == x && B.board[0][2] == x)
            return true
        return false
    }
    //checks if the game is tied by seeing if any spaces are empty, if not, then game isn't complete
    private fun isTie():Boolean {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if(B.board[row][col] == Markers.EMPTY)
                    return false
            }
        }
        return true
    }
    //changes player from current player
    private fun changeCurrentPlayer(currPlayer:Player) {
        if (currPlayer == player1)
            currentPlayer = player2
        else
            currentPlayer = player1
    }
    //places marker based on board index
    private fun putMarker(index: Int) {
        if(B.board[index / rows][index % cols] != Markers.EMPTY) {
            println("Position already taken. Please try again.")
            changeCurrentPlayer(currentPlayer)
        }
        else {
            B.board[index / rows][index % cols] = currentPlayer.symbol
        }
    }
    //Ignored in code coverage because it's just a condensed main function
    //play uses the private functions to check if either player has won or tied the board to determine the winner.
    override fun play() {
        println("Player 1, please choose a number 0-8 to place a marker")
        B.boardIndex()
        while(true) {
            val number = readLine()!!.toInt()
            putMarker(number)
            B.drawBoard()
            if(isTie()) {
                print("Game is tied")
                break
            }
            if(winCondition(currentPlayer.symbol)) {
                print("${currentPlayer.symbol} has won")
                currentPlayer.addWin()
                break
            }
            changeCurrentPlayer(currentPlayer)
        }
        println(" end")
    }
}

fun main(args: Array<String>) {
    // you can do some testing here, though unit testing needs to be
    // in the src/test/java directory
    //Application.launch(TicTacToe::class.java, *args)
    val gc = GameController()
    gc.play()
}
