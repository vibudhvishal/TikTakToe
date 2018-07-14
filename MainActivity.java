package c.vibudhvishal.tiktaktoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons= new Button[3][3];

    private int RoundCount;

    private boolean Player1Turn = true;

    private int Player1Points;
    private int Player2Points;
    private TextView Player1_Tv;
    private TextView Player2_Tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Player1_Tv=(TextView)findViewById(R.id.p1_tv);
        Player2_Tv=(TextView)findViewById(R.id.p2_tv);

        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                String buttonID = "button_" + i+ j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                 buttons[i][j]=findViewById(resID);
                 buttons[i][j].setOnClickListener(this);
            }
        }
            Button buttonReset = findViewById(R.id.button_reset);
            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetGame();

                }
            });

    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals(""))
        {
            return;
        }

        if (Player1Turn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }

        RoundCount++;

        if (checkForWin())
        {
            if(Player1Turn)
            {
                player1Wins();
            }
            else
            {
                player2Wins();
            }
        }
        else if (RoundCount==9)
        {
            draw();
        }
        else
        {
            Player1Turn=!Player1Turn;
        }

    }

    private boolean checkForWin()
    {
        String[][]  field = new String[3][3];

        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++)
        {
            if (field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!field[i][0].equals(""))
            {
                return true;
            }
        }

        for (int i=0;i<3;i++)
        {
            if (field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!field[0][i].equals(""))
            {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&&!field[0][0].equals(""))
        {
            return true;
        }

        if (field[0][2].equals(field[1][1])&&field[0][2].equals(field[2][0])&&!field[0][2].equals(""))
        {
            return true;
        }

        return false;
    }

    private void player1Wins()
    {
        Player1Points++;
        Toast.makeText(this, "Player1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins()
    {
        Player2Points++;
        Toast.makeText(this, "Player2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void draw()
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText()
    {
        Player1_Tv.setText("Player 1: "+Player1Points);
        Player2_Tv.setText("Player 2: "+Player2Points);
    }

    private void resetBoard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                buttons[i][j].setText("");
            }
        }

        RoundCount = 0;
        Player1Turn = true;
    }
    private void resetGame()
    {
        Player1Points = 0;
        Player2Points =0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundcount",RoundCount);
        outState.putInt("Player1Points",Player1Points);
        outState.putInt("Player2Points",Player2Points);
        outState.putBoolean("Player1Turn",Player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        RoundCount = savedInstanceState.getInt("roundCount");
        Player1Points = savedInstanceState.getInt("Player1Points");
        Player2Points = savedInstanceState.getInt("Player2Points");
        Player1Turn = savedInstanceState.getBoolean("Player1Turn");

    }
}
