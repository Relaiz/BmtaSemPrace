package com.example.bmta
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity(), View.OnClickListener {

    private val TAG = "DEBUG: "

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }

    private var player1Turn = true

    private var roundCount = 0

    private var player1Points = 0
    private var player2Points = 0

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewPlayer1 = findViewById(R.id.text_view_p1)
        textViewPlayer2 = findViewById(R.id.text_view_p2)

        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]?.setOnClickListener(this)
            }
        }

        val buttonReset = findViewById<Button>(R.id.button_reset)
        buttonReset.setOnClickListener {
            resetGame()
        }
    }

    override fun onClick(v: View) {
        if (!(v as Button).text.toString().isEmpty()) {
            return
        }

        if (player1Turn) {
            v.text = "X"
        } else {
            v.text = "O"
        }

        roundCount++

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            player1Turn = !player1Turn
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { arrayOfNulls<String>(3) }

        for (i in 0..2) {
            for (j in 0..2) {
                field[i][j] = buttons[i][j]?.text.toString()
            }
        }

        for (i in 0..2) {
            if (field[i][0] == field[i][1] &&
                field[i][0] == field[i][2] &&
                !field[i][0].isNullOrEmpty()
            ) {
                Log.v(TAG, "WinOnRow")
                return true
            }
        }

        for (i in 0..2) {
            if (field[0][i] == field[1][i] &&
                field[0][i] == field[2][i] &&
                !field[0][i].isNullOrEmpty()
            ) {
                Log.v(TAG, "WinOnColumn")
                return true
            }
        }

        if (field[0][0] == field[1][1] &&
            field[0][0] == field[2][2] &&
            !field[0][0].isNullOrEmpty()
        ) {
            Log.v(TAG, "WinOnDiagonal")
            return true
        }

        if (field[0][2] == field[1][1] &&
            field[0][2] == field[2][0] &&
            !field[0][2].isNullOrEmpty()
        ) {
            Log.v(TAG, "WinOnInvertedDiagonal")
            return true
        }

        return false
    }

    private fun player1Wins() {
        player1Points++
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show()
        updatePointsText()
        //resetBoard();
    }

    private fun player2Wins() {
        player2Points++
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show()
        updatePointsText()
        //resetBoard();
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show()
        // resetBoard();
    }

    private fun updatePointsText() {
        textViewPlayer1.text = "Player 1: $player1Points"
        textViewPlayer2.text = "Player 2: $player2Points"
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = ""
            }
        }

        roundCount = 0
        player1Turn = true
    }

    private fun resetGame() {
        player1Points = 0
        player2Points = 0
        updatePointsText()
        resetBoard()
    }
}