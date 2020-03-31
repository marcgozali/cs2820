package edu.uiowa

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage


//Menu app creates a simple window to switch between gamemodes i.e. TicTacToe or Ultimate TicTacToe
class Menu : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Menu"
        val vbox = VBox()
        vbox.alignment = Pos.CENTER
        val ttt = Button("TicTacToe")
        ttt.setPrefSize(100.0,50.0)
        vbox.children += ttt
        val ultimate = Button("Ultimate")
        vbox.children += ultimate
        ultimate.setPrefSize(100.0,50.0)
        val exit = Button("Exit")
        exit.setPrefSize(100.0,50.0)
        vbox.children += exit
        val scene = Scene(vbox, 300.0, 300.0)
        primaryStage.scene = scene
        ttt.setOnAction { e ->
            val t = TicTacToe()
            t.start(primaryStage) }
        ultimate.setOnAction { e ->
            val t = Ultimate()
            t.start(primaryStage)
        }
        exit.setOnAction { Platform.exit() }
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    // you can do some testing here, though unit testing needs to be
    // in the src/test/java directory
    Application.launch(Menu::class.java, *args)
}