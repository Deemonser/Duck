package cn.deemons.duck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view = findViewById(R.id.text);
//        ConstraintLayout root = findViewById(R.id.root_view);


//        view.setBackground(new ShapeUtils(GradientDrawable.RECTANGLE)
//                .corner(10)
//                .stroke(3, Color.parseColor("#0000ff"))
//                .gradientLinear(GradientDrawable.Orientation.LEFT_RIGHT)
//                .gradientColor(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
//                .create()
//        );



    }

}
