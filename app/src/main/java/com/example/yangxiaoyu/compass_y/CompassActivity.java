package com.example.yangxiaoyu.compass_y;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity {
    private CompassView compassView;
    private TextView pos;
    private SensorManager sensorManager;
    private SensorListener mListener = new SensorListener();
    private float degree ;
    private float mdegree = 0f;
    private String positionstr;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            Position position = (Position) msg.obj;
            compassView.setmDegree(position.getDegree());
            pos.setText(positionstr+(int)degree+"");

        }
    };

    public class SensorListener implements SensorEventListener{

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                 degree = event.values[0];
                // 创建旋转动画（反向转过degree度）
                RotateAnimation ra = new RotateAnimation(mdegree, -degree,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                // 设置动画的持续时间
                ra.setDuration(200);
                // 设置动画结束后的保留状态
                ra.setFillAfter(true);
                // 启动动画
                compassView.startAnimation(ra);
                mdegree = -degree;
                positionstr = null;
                if (22.5 >= degree || degree >= 337.5) {
                    positionstr = "N";
                } else if (22.5 < degree && degree <= 67.5) {
                    positionstr = "NE";
                } else if (67.5 < degree && degree <= 112.5) {
                    positionstr = "E";
                } else if (112.5 < degree && degree <= 157.5) {
                    positionstr = "SE";
                } else if (157.5 < degree && degree <= 202.5) {
                    positionstr = "S";
                } else if (202.5 < degree && degree <= 247.5) {
                    positionstr = "SW";
                } else if (247.5 < degree && degree <= 292.5) {
                    positionstr = "W";
                }else if(292.5<degree && degree<=337.5){
                    positionstr = "NW";
                }
                Message msg = mHandler.obtainMessage();
                msg.obj = new Position(degree, positionstr);
                msg.sendToTarget();
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        sensorManager = (SensorManager)getSystemService(getApplicationContext().SENSOR_SERVICE);
        compassView = (CompassView)findViewById(R.id.compass);
        pos = (TextView)findViewById(R.id.pos);

    }

    @Override
    protected void onResume() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener((SensorEventListener) mListener,sensor,SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(mListener);
        super.onPause();
    }
}
