package cn.deemons.duck;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
//            @Override
//            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//
//                Log.e(TAG, "name = " + name);
//                int n = attrs.getAttributeCount();
//                for (int i = 0; i < n; i++) {
//                    Log.e(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
//                }
//
//                return null;
//            }
//
//            @Override
//            public View onCreateView(String name, Context context, AttributeSet attrs) {
//
//                return null;
//            }
//        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView view = findViewById(R.id.text);
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
