package com.vaibhav.hangman;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView bestscore,cscore,word,uletters;
    EditText eletter;
    Button checkbuton;
    ImageView imageView;
    Context context;
    String checkletter,str,s="",lettersused="";
    int i=0,currentscore=0,highscore=0,counter=0,check=1,wordcompletioncheck=1,mcounter=0,runcheck=0;
    int[] randomlist;
    String[] wordlist={"AWKWARD","BANJO","BUNGLER","CROQUET","CRYPT","DWARVES","FERVID",
            "GYPSY","HYPHEN","IVORY","KIOSK","MEMENTO","MYSTIFY","OXYGEN","PIXEL","RHYTHMIC","ROGUE","SPHINX",
            "UNZIP","WAXY","WILDEBEEST","ZEALOUS","ZIGZAG","ZIPPY","ZOMBIE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context=this;
        SharedPreferences sPrefs = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        highscore = sPrefs.getInt("highscore", 0);
        UIinit();
        bestscore.setText(String.valueOf(highscore));
        randomlist=randomindex();
        gameplay();
        checkbuton.setOnClickListener(checkclick);

    }

    public View.OnClickListener checkclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (eletter.getText().toString().equals("")){
                Toast.makeText(context,"Please Enter a letter to check",Toast.LENGTH_SHORT).show();
            }
            else {
                gameplay();
                if (wordcompletioncheck == 1) {
                    updatescore();
                    updatewordindex();
                    lettersused="";
                    wordcompletioncheck=1;
                    s="";
                    counter--;
                    gameplay();
                }

            }
        }
    };

    public void updatewordindex(){
        if (i < 25)
            i++;
        else if (i==25) {
            i = 0;
            randomlist = randomindex();
        }
    }

    public void updatescore(){
        currentscore++;
        cscore.setText(String.valueOf(currentscore));
        if(currentscore>highscore) {
            highscore = currentscore;
            bestscore.setText(String.valueOf(highscore));
        }
    }


    public void gameplay(){
        checkletter=eletter.getText().toString();
        if (runcheck!=0)
            check=0;
        str=wordlist[randomlist[i]];
        for(int j=0;j<str.length();j++){
            if(wordcompletioncheck==1){
                String k=s;
                s=k+"_ ";
            }
            if (checkletter.toUpperCase().equals(Character.toString(str.charAt(j)))){
                StringBuilder builder=new StringBuilder(s);
                builder.setCharAt(2*j,str.charAt(j));
                s=builder.toString();
                check=1;
            }
            }
        word.setText(s);
            runcheck++;
        for (int m=0;m<s.length();m++){
            if(Character.toString(s.charAt(m)).equals("_")) {
                wordcompletioncheck = 0;
                break;
            }
            else{
                wordcompletioncheck=1;
            }
        }
        if (!lettersused.equals(""))
        lettersused=lettersused+","+checkletter;
        else
            lettersused=checkletter;
        uletters.setText(lettersused);
        animate();
        eletter.setText("");
    }

    public void animate(){
        if(check==0){
            counter++;
            if(counter==1)
                imageView.setImageResource(R.drawable.h7);
            if(counter==2)
                imageView.setImageResource(R.drawable.h5);
            if(counter==3)
                imageView.setImageResource(R.drawable.h4);
            if(counter==4)
                imageView.setImageResource(R.drawable.h3);
            if(counter==5)
                imageView.setImageResource(R.drawable.h2);
            if(counter==6) {
                imageView.setImageResource(R.drawable.h1);
                Intent intent=new Intent(context,Main3Activity.class);
                startActivity(intent);
            }
        }
    }

    public void UIinit(){
        bestscore=findViewById(R.id.highscore);
        cscore=findViewById(R.id.currentscore);
        word=findViewById(R.id.puzzleword);
        uletters=findViewById(R.id.UsedLetters);
        eletter=findViewById(R.id.entertext);
        imageView=findViewById(R.id.imageview);
        checkbuton=findViewById(R.id.button);
    }

    public int[] randomindex(){
        int[] drawNum=new int[25];
        for(int i=0;i<25;i++){
            drawNum[0]=(int)(Math.random()*25);
            for(int  j=0;j<i;j++){
                if(drawNum[i]==drawNum[j]){
                    drawNum[i]=(int)(Math.random()*25);
                }
            }
        }
        return drawNum;
    }
    @Override
    protected void onPause() {
        SharedPreferences sPrefs = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putInt("highscore", highscore);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onStop() {
        SharedPreferences sPrefs = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putInt("highscore", highscore);
        editor.apply();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences sPrefs = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putInt("highscore", highscore);
        editor.apply();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(Main2Activity.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
