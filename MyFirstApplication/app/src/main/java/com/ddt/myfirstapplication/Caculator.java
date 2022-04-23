package com.ddt.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Caculator extends AppCompatActivity {
    EditText txt_Cal, txt_SecCal;
    Button btnAC, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9
            ,btnDot, btnSum, btnSub, btnMul, btnDiv, btnEqual;
    String tinhtoan, pheptoan;
    Boolean checkDot=false, checkBang=false;
    double num1, num2, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caculator);
        this.getViews();
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Cal.setText("");
                tinhtoan="";
                pheptoan="";
                checkBang=false;
                checkDot=false;
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    //tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText( "0");
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "1");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "2");
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "3");
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "4");
                }
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "5");
                }
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "6");
                }
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "7");
                }
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "8");
                }
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    tinhtoan = txt_Cal.getText().toString();
                    txt_Cal.setText(tinhtoan + "9");
                }
            }
        });
        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkBang) {
                    if (!checkDot) {
                        tinhtoan = txt_Cal.getText().toString();
                        txt_Cal.setText(tinhtoan + ".");
                        checkDot = true;
                    }
                }
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_Cal.getText().toString().equals("")) {
                    num1 = 0;
                    txt_SecCal.setText("0 + ");
                }
                else {
                    num1 = Double.parseDouble(txt_Cal.getText().toString());
                    txt_SecCal.setText(txt_Cal.getText().toString() + " + ");
                    txt_Cal.setText("");
                    tinhtoan = "";
                    pheptoan="+";
                    checkDot=false;
                    checkBang=false;
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_Cal.getText().toString().equals("")) {
                    num1 = 0;
                    txt_SecCal.setText("0 - ");
                }
                else {
                    num1 = Double.parseDouble(txt_Cal.getText().toString());
                    txt_SecCal.setText(txt_Cal.getText().toString() + " - ");
                }
                txt_Cal.setText("");
                tinhtoan = "";
                pheptoan="-";
                checkDot=false;
                checkBang=false;
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_Cal.getText().toString().equals("")) {
                    num1 = 0;
                    txt_SecCal.setText("0 x ");
                }
                else {
                    num1 = Double.parseDouble(txt_Cal.getText().toString());
                    txt_SecCal.setText(txt_Cal.getText().toString() + " x ");
                    txt_Cal.setText("");
                    tinhtoan = "";
                    pheptoan="*";
                    checkDot=false;
                    checkBang=false;
                }
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_Cal.getText().toString().equals("")) {
                    num1 = 0;
                    txt_SecCal.setText("0 / ");
                }
                else {
                    num1 = Double.parseDouble(txt_Cal.getText().toString());
                    txt_SecCal.setText(txt_Cal.getText().toString() + " / ");
                    txt_Cal.setText("");
                    tinhtoan = "";
                    pheptoan="/";
                    checkDot=false;
                    checkBang=false;
                }
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBang) {
                    checkDot = false;
                    tinhtoan = txt_Cal.getText().toString();
                    num2 = Double.parseDouble(txt_Cal.getText().toString());
                    txt_SecCal.setText(txt_SecCal.getText().toString() + txt_Cal.getText().toString());
                    txt_Cal.setText("");
                    if (pheptoan.equals("+")) {
                        result = num1 + num2;
                    }
                    else if (pheptoan.equals("-")) {
                        result = num1 - num2;
                    }
                    else if (pheptoan.equals("*")) {
                        result = num1 * num2;
                    }
                    else if (pheptoan.equals("/")) {
                        result = num1 / num2;
                    }
                    checkBang = true;
                    txt_Cal.setText(String.valueOf(result));
                }
                pheptoan = "";
            }
        });
    }

    private void getViews() {
        txt_Cal = findViewById(R.id.txt_Cal);
        txt_SecCal = findViewById(R.id.txt_secCal);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDot = findViewById(R.id.btnDot);
        btnDiv = findViewById(R.id.btnDiv);
        btnMul = findViewById(R.id.btnMulti);
        btnSum = findViewById(R.id.btnSum);
        btnSub = findViewById(R.id.btnSub);
        btnAC = findViewById(R.id.btnAC);
        btnEqual = findViewById(R.id.btnEqual);
    }
}