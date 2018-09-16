package com.example.eiichi.flexiblecalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;

    private enum ArithmeticMode
    {
        PLUS,
        MINUS,
        TIMES,
        DIVIDED,
        NON
    };
    Double   mHalfwayNumber_;
    Double   mResult_;
    boolean mArithmeticPressed_;
    ArithmeticMode mCurrentArithmeticMode_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSelf();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //列を追加する
        TableLayout tableLayout = (TableLayout)findViewById(R.id.mTableLayout_);
        for (int i = 0; i < 3; ++i) {
            TableRow tableRow = new TableRow(this);
            for (int ii = 0; ii < 3; ++ii) {
                Button numberButton = new Button(this);

                // クリック時の処理
                numberButton.setOnClickListener(new ButtonClickListener(this));

                numberButton.setId(getCalculatorStringNumber(i, ii));
                numberButton.setText(getCalculatorStringNumber(i, ii).toString());
                numberButton.setLayoutParams(new TableRow.LayoutParams(
                        //ViewGroup.LayoutParams.MATCH_PARENT,
                        0, // どちらでも良いのか？
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1));
                tableRow.addView(numberButton);

            }
            tableLayout.addView(tableRow, createParam(MP, MP));
        }


    }
    private void initializeSelf() {
        mHalfwayNumber_ = 0.0;
        mResult_ = 0.0;
        initializeTextView(R.id.mArithmeticTextView_, "");
        initializeTextView(R.id.mButterNumberTextView_, "0.0");
        initializeTextView(R.id.mResultTextView_, "0.0");
        mArithmeticPressed_ = false;
        mCurrentArithmeticMode_ = ArithmeticMode.NON;

    }
    private void initializeTextView(int aId, String aText) {
        TextView textView_ = (TextView) findViewById(aId);
        textView_.setText(aText);
    }

    public void addNumber(String aInputText) {
        TextView textView = (TextView) findViewById(R.id.mButterNumberTextView_);
        String current = (String) textView.getText();
        if (mArithmeticPressed_){
            textView.setText(aInputText);
            mArithmeticPressed_ = false;
        }
        else
        {
            textView.setText(current + aInputText);
        }

    }

    private void addArithmetic(String aInputText) {
        operate();
        TextView textView_ = (TextView) findViewById(R.id.mArithmeticTextView_);
        //String current = (String) textView_.getText();
        textView_.setText(aInputText);
        //mArithmeticPressed_ = true;

    }

    private void operate() {
        Double result = 0.0;
        switch (mCurrentArithmeticMode_)
        {
            case NON:
                result = getButterValue();
                break;
            case PLUS:
                result = mHalfwayNumber_ + getButterValue();
                break;
            case MINUS:
                result = mHalfwayNumber_ - getButterValue();
                break;
            case TIMES:
                result = mHalfwayNumber_ * getButterValue();
                break;
            case DIVIDED:
                result = mHalfwayNumber_ / getButterValue();
                break;
        }
        mHalfwayNumber_ = result;
        setResultText(result.toString());
    }
    private Double getButterValue(){
        TextView textView = (TextView) findViewById(R.id.mButterNumberTextView_);
        String halfway = (String) textView.getText();
        Double doubleButterNumber = 0.0;
        if (!halfway.isEmpty()) {
            doubleButterNumber = new Double(halfway).doubleValue();
        }
        return doubleButterNumber;
    }
    private void setResultText(String aText) {
        TextView resultView = (TextView) findViewById(R.id.mResultTextView_);
        resultView.setText(aText);
    }

    public void onPlusClicked    (View view) {
        addArithmetic("+");
        mCurrentArithmeticMode_ = ArithmeticMode.PLUS;
        mArithmeticPressed_ = true;

    }
    public void onMinusClicked    (View view) {
        addArithmetic("-");
        mCurrentArithmeticMode_ = ArithmeticMode.MINUS;
        mArithmeticPressed_ = true;

    }
    public void onMultipleClicked    (View view) {
        addArithmetic("×");
        mCurrentArithmeticMode_ = ArithmeticMode.TIMES;
        mArithmeticPressed_ = true;
    }
    public void onDevideClicked    (View view) {
        addArithmetic("÷");
        mCurrentArithmeticMode_ = ArithmeticMode.DIVIDED;
        mArithmeticPressed_ = true;
    }

    public void onEqualClicked    (View view) {
        mArithmeticPressed_ = true;
    }
    public void onClearClicked    (View view) {
        TextView halfwayTextView = (TextView) findViewById(R.id.mButterNumberTextView_);
        halfwayTextView.setText("");

        TextView resultTextView = (TextView) findViewById(R.id.mResultTextView_);
        resultTextView.setText("0.0");

        TextView textView_ = (TextView) findViewById(R.id.mArithmeticTextView_);
        textView_.setText("");

        mHalfwayNumber_ = 0.0;
        mResult_ = 0.0;
        mArithmeticPressed_ = true;
    }
    public void onAcClicked    (View view) {
        mArithmeticPressed_ = true;
    }


    private Integer getCalculatorStringNumber(int aColumn, int aRow) {
        if (0 == aColumn){
            if (0 == aRow) {
                return new Integer(7);
            }
            else if (1 == aRow) {
                return new Integer(8);
            }
            else if (2 == aRow) {
                return new Integer(9);
            }
        }
        else if (1 == aColumn){
            if (0 == aRow) {
                return new Integer(4);
            }
            else if (1 == aRow) {
                return new Integer(5);
            }
            else if (2 == aRow) {
                return new Integer(6);
            }
        }
        else if (2 == aColumn){
            if (0 == aRow) {
                return new Integer(1);
            }
            else if (1 == aRow) {
                return new Integer(2);
            }
            else if (2 == aRow) {
                return new Integer(3);
            }
        }
        return new Integer(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        // aaa
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private TableLayout.LayoutParams createParam(int w, int h){
        return new TableLayout.LayoutParams(w, h, 1);
    }
}
