package five.main;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FiveTestActivity extends Activity{

    /** Called when the activity is first created. */
//	LinearLayout layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(layout);
        setContentView(R.layout.main);        
        //setContentView(myView);

//        layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.HORIZONTAL);
//    	layout.addView(myView);
//    	layout.addView(btn1);

    	final MyView myView = (MyView)findViewById(R.id.img1);//new MyView(this);
 //   	final ImageView myView = (ImageView)findViewById(R.id.img1);
    	Button btn1 = (Button)findViewById(R.id.btn1);//new Button(this);
    	if (myView == null)Log.i("dddd", "error");else Log.i("ffff","ffffff");
    	if (btn1 == null)Log.i("eeee", "error");else Log.i("ggggg","ffffff");
 //       final MyView myView = (MyView)findViewById(R.id.img1);
 //       final ImageView image = (ImageView)findViewById(R.id.img1);
 //       image.setImageBitmap(myView.drawChessboardLines());
        //image.setImageResource(images[0]);
        /*image.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		currentImg = (currentImg + 1) % 2;
        		image.setImageResource(images[currentImg]);
        	}
        });
        image.setOnTouchListener(new OnTouchListener()
        {
        	@Override
        	public boolean onTouch(View v, MotionEvent event)
        	{
        		switch (event.getAction()) {
        		case MotionEvent.ACTION_DOWN:
  //      			currentImg = (currentImg + 1) % 2;
  //          		image.setImageResource(images[currentImg]);
            		int x = (int) event.getX();
                    int y = (int) event.getY();
                    DisplayToast("触笔点击坐标:(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
            		break;
            	default:
            		break;
        		}
        	
        		return true;
        	}
        });*/
        /*
        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		//image.setImageBitmap(myView.drawRect());
 //       		image.setImageBitmap(myView.drawChessboardLines());
        	}
        });
        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
 //       		image.setImageBitmap(myView.drawTriangle());
        	}
        });
        */   
    }
    
    public void DisplayToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    
    public class MyView extends View{
        public final static int CHESS_WIDTH = 11;
        public final static int CHESS_HEIGHT = 11;
     
        //  保存绘制历史
        public List<CircleInfo> mCircleInfos = new ArrayList<MyView.CircleInfo>();
        
        private int viewWidth, viewHeight;
    	//点大小
        private int pointSize;
        
        //第一点偏离左上角从像数，为了棋盘居中
    	private int yOffset;
    	private int xOffset;

    	//人和电脑的当前落子点
    	Point curPointMan = new Point(0,0);
    	Point curPointComputer = new Point(0,0);
    	
    	//当前下棋状态
    	int chessStatus = 0;//0:未开始 1:已开始 2:已结束
    	
    	private Bitmap mBitmap;
    	private Paint paint;
    	private Canvas canvas;
    	
    	public MyView(Context context, AttributeSet attr) {
    		super(context, attr);

           	WindowManager wm = getWindowManager();
        	Display display = wm.getDefaultDisplay();
        	int width = display.getWidth();
        	int height = display.getWidth();
      	Log.i("aa", "Width:" + width + "Height:"+ height);// 
        	  
    		viewWidth = width;
    		viewHeight = height;
    		pointSize = (int) Math.min(Math.floor(width/CHESS_WIDTH), Math.floor(height/CHESS_HEIGHT));
    		paint = new Paint();  
            // 设置颜色  
            paint.setColor(Color.RED);  
            // 设置抗锯齿  
            paint.setAntiAlias(true);  
            // 设置线宽  
            paint.setStrokeWidth(3);  
            // 设置非填充  
            paint.setStyle(Style.STROKE);  
    		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
    		canvas = new Canvas(mBitmap);
    		drawChessboardLines();
    	}
    	
    	// 保存实心圆相关信息的类
        public class CircleInfo
        {
            private float x;                //  圆心横坐标
            private float y;                //  圆心纵坐标
            private int color;            //  画笔的颜色

            public float getX()
            {
                return x;
            }
            public void setX(float x)
            {
                this.x = x;
            }
            public float getY()
            {
                return y;
            }
            public void setY(float y)
            {
                this.y = y;
            }
            public int getColor()
            {
                return color;
            }
            public void setColor(int color)
            {
                this.color = color;
            }
        }
    	
   	    @Override
    	protected void onDraw(Canvas canvas) {
    		super.onDraw(canvas);
    		canvas.drawColor(Color.BLUE);
    		canvas.drawBitmap(mBitmap, 0, 0, null);
    		 
    		//drawChessboardLines();
    		for (CircleInfo circleInfo : mCircleInfos) 
            { 
                Paint paint = new Paint(); 
                //  设置画笔颜色 
                paint.setColor(circleInfo.getColor()); 
                //  绘制实心圆 
                canvas.drawCircle(circleInfo.getX(), circleInfo.getY(), pointSize/2, paint); 
            } 
    		if (chessStatus != 0)
    		{
    			Paint paint = new Paint();
    			paint.setColor(Color.GREEN);
    			canvas.drawCircle(curPointComputer.x, curPointComputer.y, pointSize/4, paint);
    		}
   	    }
   	    
		 private Bitmap drawChessboardLines(){		
		     //设置X、Y座标微调值，目的整个框居中
			 xOffset = ((viewWidth - (pointSize * (CHESS_WIDTH-1))) / 2);
			 yOffset = ((viewHeight - (pointSize * (CHESS_HEIGHT-1))) / 2);
			 
			 Log.i("bb", "xOffset:"+xOffset+"yOffset:"+yOffset+"pointsize:"+pointSize);
			 //竖线
		 	for (int i=0; i<CHESS_WIDTH; i++) {
		 		canvas.drawLine(xOffset+i*pointSize, yOffset, xOffset+i*pointSize, 
		 				yOffset+pointSize*(CHESS_HEIGHT-1), paint);
			}
		 	//横线
		 	for (int i=0; i<CHESS_HEIGHT; i++) {
		 		canvas.drawLine(xOffset, yOffset+i*pointSize, xOffset+pointSize*(CHESS_WIDTH-1),
		 				yOffset+i*pointSize, paint);
			}
		 	return mBitmap;
		 }
	  //根据触摸点坐标找到对应点
		private Point newPoint(float x, float y){
			Point p = new Point(0, 0);
			for (int i = 0; i < CHESS_WIDTH; i++) {
				if ((i * pointSize + xOffset - pointSize / 2) <= x
						&& x < ((i + 1) * pointSize + xOffset - pointSize / 2)) {
					p.x = i * pointSize + xOffset;
				}
			}
			for (int i = 0; i < CHESS_HEIGHT; i++) {
				if ((i * pointSize + yOffset - pointSize / 2) <= y
						&& y < ((i + 1) * pointSize + yOffset - pointSize / 2)) {
					p.y = i * pointSize + yOffset;
				}
			}
			return p;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event)
		{
			Point pt = new Point(0, 0);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				float x = event.getX();
				float y = event.getY();
				pt = newPoint(x,y);
				if (checkNewPoint(pt))
				{
					drawPointMan(pt);
					if (isFinish())
					{
						break;
					}
					pt = computerCalculate();
					drawPointComputer(pt);
				}
				break;
			default:
				break;
			}
			invalidate();
			return true;
		}

		private void drawPointMan(Point pt){
			Log.i("c", "ptx:"+pt.x+"pty"+pt.y);

			CircleInfo circleInfo = new CircleInfo();
			circleInfo.setX(pt.x);
			circleInfo.setY(pt.y);
			circleInfo.setColor(Color.BLACK);
			curPointMan = pt;
			mCircleInfos.add(circleInfo);
			chessStatus = 1;
		}
		private boolean checkNewPoint(Point pt) {
			if (0 == pt.x || 0 == pt.y)
			{
				return false;
			}
			return true;
		}
		private boolean isFinish() {
			return false;
		}
		private void drawPointComputer(Point pt){
			Log.i("d", "ptx:"+pt.x+"pty"+pt.y);

			CircleInfo circleInfo = new CircleInfo();
			circleInfo.setX(pt.x);
			circleInfo.setY(pt.y);
			circleInfo.setColor(Color.WHITE);
			curPointComputer = pt;
			mCircleInfos.add(circleInfo);
		}
		private Point computerCalculate() {
			Point pt = new Point(0, 0);
			pt.x = curPointMan.x+pointSize;
			pt.y = curPointMan.y;
			return pt;
		}
    }
}



