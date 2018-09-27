package cn.deemons.duck;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import cn.deemons.library.ShapeUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2()
        {
            @Override
            public View onCreateView(View parent, String name, Context context,
                AttributeSet attrs) {
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                Log.e(TAG, "name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++)
                {
                    Log.e(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }

                return null;
            }
        });

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

        new MyFactor().onCreateView("", null, null);

    }

    class MyFactor implements LayoutInflater.Factory {
        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }

}
