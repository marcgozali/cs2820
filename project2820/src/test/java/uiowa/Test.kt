package edu.uiowa

import org.junit.Test
import org.junit.Assert.*

class Test {
    @Test
    fun boardtest() {
        val B = Board()
        for(i in 0 until B.rows) {
            for(j in 0 until B.cols) {
                assertEquals(B.board[i][j],Markers.EMPTY)
            }
        }
        B.drawBoard()
        B.setSize(4)
        B.boardIndex()
        B.board[0][0] = Markers.X
        B.board[0][1] = Markers.O
        B.board[0][2] = Markers.X
        B.board[1][1] = Markers.O
        B.reset()
        for(i in 0 until B.rows) {
            for(j in 0 until B.cols) {
                assertEquals(B.board[i][j],Markers.EMPTY)
            }
        }
    }

    @Test
    fun playerTest() {
        val A = Player("tester1",Markers.X)
        assertEquals(Markers.X, A.symbol)
        assertEquals("tester1",A.name)
        assertEquals(0,A.wins)
        A.addWin()
        assertEquals(1,A.wins)
        assertEquals(1,A.numWins())
        A.resetWins()
        assertEquals(0,A.wins)
    }
    @Test
    fun gameController() {
        val C = GameController()
        C.putMarker(0)
        C.putMarker(1)
        C.putMarker(2)
        C.putMarker(2)
        assertEquals(Markers.X,C.B.board[0][0])
        assertEquals(Markers.X,C.B.board[0][1])
        assertEquals(Markers.X,C.B.board[0][2])
        assertEquals(false, C.isTie())
        C.winCondition(C.player1.symbol)
        C.changeCurrentPlayer(C.player1)
        assertEquals(C.player2, C.currentPlayer)
        C.changeCurrentPlayer(C.player2)
        assertEquals(C.player1,C.currentPlayer)
        C.putMarker(3); C.putMarker(4); C.putMarker(5); C.putMarker(6); C.putMarker(7); C.putMarker(8)
        assertEquals(true,C.isTie())
        C.play()
    }
}

