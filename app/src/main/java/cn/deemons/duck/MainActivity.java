package cn.deemons.duck;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import cn.deemons.library.ShapeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view = findViewById(R.id.text);
//        ConstraintLayout root = findViewById(R.id.root_view);


        view.setBackground(new ShapeUtils(GradientDrawable.RECTANGLE)
                .corner(10)
                .stroke(3, Color.parseColor("#0000ff"))
                .gradientLinear(GradientDrawable.Orientation.LEFT_RIGHT)
                .gradientColor(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
                .create()
        );

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new View(v.getContext());
//                new LinearLayout(v.getContext());
//                new ConstraintLayout(v.getContext());
            }
        });

    }


}
