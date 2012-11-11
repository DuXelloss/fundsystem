package com.chenjun.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.chenjun.fund.drawable.ChartInfo;
import com.chenjun.fund.drawable.DrawSizeInfo;
import com.chenjun.fund.drawable.Drawable;
import com.chenjun.fund.model.Jjjz;
import com.chenjun.fund.model.Jjjzs;
import com.chenjun.utils.DateUtils;
import com.chenjun.utils.StringUtils;

public class JjjzsChart extends View{
	/**
	 * ͼ����ʱ�����ȼ�����
	 * ���X�᷽�򻬶����볬�����������������X����Ļ�����Y��Ļ�����Ч
	 * ��֮�����С���������������Y��Ļ���
	 */
	private static final int X_FLING_DISTANCE = 10;
	
	/**
	 * ͼ��һ�λ���������ͼ����Ϣ��λ�õ�Ӱ����
	 * ���磬���󻬶�һ�Σ�startOffset �� endOffset ����Ӧ���������������������������£�
	 */
	private static final int ONCE_FLING_CHANGE_OFFSET = 10;
	
	//������ʷ��ֵ��Ϣ
	private Jjjzs jjjzs;
	
	//ͼ������ͣ���ʼ��Ϊ һ����
	private ChartType chartType = ChartType.MONTH;
	
	//ͼ���ͼ����Ϣ���㣬�ߣ�XY��Ĳ����ȵȣ�
	private ChartInfo chartInfo;
	
	//ͼ��Ĵ�С
	private DrawSizeInfo sizeInfo;
	
	//ͳ����Ϣ�ؼ���
	private RangeInfoViewGroup rangeInfoViewGroup;
	
	//��ͼ���ͼ����Ϣ�� ����� ��������ʷ��ֵ��Ϣ�� ��ƫ������Ӧ��ϵ
	private int startOffset = 0;
	private int endOffset = 0;
	
	//�û�������ĳ����chartInfo�е�ID
	private int checkedID;
	
	//��ʾX�������ĸ�����ͼ���ĸ�����ͬ
	private boolean isScroll = false;
	
	//��ʾ�û��Ƿ񵥻���ͼ����ĳ��
	private boolean isChecked;
//	float touchedX;
//	float touchedY;
	
	public JjjzsChart(Context context){
		super(context);
		//setWillNotDraw(false);
	}
	
	public JjjzsChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		//setWillNotDraw(false);
	}
	
	
	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	public Jjjzs getJjjzs() {
		return jjjzs;
	}
	public void setJjjzs(Jjjzs jjjzs) {
		this.jjjzs = jjjzs;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public RangeInfoViewGroup getRangeInfoViewGroup() {
		return rangeInfoViewGroup;
	}
	public void setRangeInfoViewGroup(RangeInfoViewGroup rangeInfoViewGroup) {
		this.rangeInfoViewGroup = rangeInfoViewGroup;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		synchronized(this){
			super.onDraw(canvas);
			
			//��ʼ����ͼ����Ϣ��ChartInfo����
			initChartInfo();
			
			//������ͼ
			sizeInfo = new DrawSizeInfo(getWidth(), getHeight(), getWidth() * 0.1, getHeight() * 0.1, getWidth() * 0.03, getHeight() * 0.1 );
			Drawable.drawPointLineChart(canvas, chartInfo, sizeInfo, isScroll);
			
			//����������Ϣ��ͼ���ϵĵ��쾻ֵ���ǵ�����Ϣ��
			drawExtraInfo(canvas);
			
			//���û�����ͼ�����Ϣ
			drawTouchLine(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(chartInfo == null){
			return false;
		}
		return gestureDetector.onTouchEvent(event);
	}
	
	/**
	 * ��ʼ����ͼ����Ϣ��ChartInfo����
	 * �����������ʷ��ֵ��Ϣ��Jjjzs������Ϊ�գ���ô���ӡ�������ʷ��ֵ��Ϣ��Jjjzs�������û�ͼ�ġ�ͼ����Ϣ��ChartInfo������������ͼ�������ͼ
	 */
	private void initChartInfo(){
		if(jjjzs != null){
			switch(chartType){
			case TEN_DAY:
				endOffset = jjjzs.getCount();
				startOffset = jjjzs.search(DateUtils.getPre10DayStr());
				break;
			case MONTH:
				endOffset = jjjzs.getCount();
				startOffset = jjjzs.search(DateUtils.getPreMonthDayStr());
				break;
			case THREE_MONTH:
				endOffset = jjjzs.getCount();
				startOffset = jjjzs.search(DateUtils.getPre3MonthDayStr());
				break;
			case HALF_YEAR:
				endOffset = jjjzs.getCount();
				startOffset = jjjzs.search(DateUtils.getPreHalfYearDayStr());
				break;
			case YEAR:
				endOffset = jjjzs.getCount();
				startOffset = jjjzs.search(DateUtils.getPreYearDayStr());
				break;
			case SELF_CONFIG:
				break;
			default:
				break;
			}
			chartInfo = jjjzs.getChartInfo(startOffset, endOffset);
			this.refleshRangeInfo();
		}
	}
	
	/**
	 * ������������ܻ����û����ʱ�ĸ�����
	 * @param canvas
	 */
	private void drawTouchLine(Canvas canvas){
		if(!isChecked){
			return;
		}
		Paint touchLinePaint = new Paint();
		touchLinePaint.setStyle(Style.STROKE);
		touchLinePaint.setColor(Color.BLUE);
		touchLinePaint.setPathEffect(new DashPathEffect(new float[]{10,5,10,5},1));
		canvas.drawLine(IDtoX(checkedID), (float)(getHeight() * 0.1), IDtoX(checkedID), (float)(getHeight() * 0.9), touchLinePaint);
	}
	
	/**
	 * ���û�����ͼ��ĳ��ʱ������������������ͼ������ʾ�����Ļ���ֵ��Ϣ
	 * ��û�е�����ߵ�������Ϸ���ͼ������ʱ������ʾ���һ��Ļ���ֵ��Ϣ
	 * @param canvas
	 */
	private void drawExtraInfo(Canvas canvas){
		/*------ ��������Ϣ-----*/
		Paint extraInfoPaint = new Paint();
        extraInfoPaint.setStyle(Style.FILL);
        extraInfoPaint.setColor(Color.WHITE);
        extraInfoPaint.setTextAlign(Align.LEFT);
        extraInfoPaint.setTypeface(Typeface.DEFAULT);
        extraInfoPaint.setSubpixelText(true);
        extraInfoPaint.setAntiAlias(true);
        extraInfoPaint.setDither(true);
        extraInfoPaint.setTextSize((float)sizeInfo.getPaddingUp() / 2);
		if(chartInfo == null){
			canvas.drawText("������������..", (float)sizeInfo.getPaddingLeft(), (float)sizeInfo.getPaddingUp() * 3 / 4, extraInfoPaint);
			return;
		}
		if(!isChecked){
			checkedID = chartInfo.getPiontCount() - 1;
		}
		if(checkedID == 0){
			Jjjz jjjz = (Jjjz)chartInfo.getPointExtraInfo().get(checkedID);
        	String info = "����: " + StringUtils.dateStringChange(jjjz.getRq()) + "  ��ֵ: " + StringUtils.stringLenthFormat(jjjz.getJz(), 6, '0');
        	canvas.drawText(info, (float)sizeInfo.getPaddingLeft(), (float)sizeInfo.getPaddingUp() * 3 / 4, extraInfoPaint);
		}else{
			Jjjz jjjz = (Jjjz)chartInfo.getPointExtraInfo().get(checkedID);
			Jjjz preJjjz = (Jjjz)chartInfo.getPointExtraInfo().get(checkedID - 1);
			double jzIncrease = Double.parseDouble(jjjz.getFqjz()) - Double.parseDouble(preJjjz.getFqjz());
			String jzIncreaseStr;
			if(jzIncrease < 0){
				jzIncreaseStr = StringUtils.stringLenthFormat(String.valueOf(jzIncrease), 7, '0');
			}else {
				jzIncreaseStr = StringUtils.stringLenthFormat(String.valueOf(jzIncrease), 6, '0');
			}
        	String info = "����: " + StringUtils.dateStringChange(jjjz.getRq()) + "  ��ֵ: " + StringUtils.stringLenthFormat(jjjz.getJz(), 6, '0') + " �ǵ�:" + jzIncreaseStr;
        	canvas.drawText(info, (float)sizeInfo.getPaddingLeft(), (float)sizeInfo.getPaddingUp() * 3 / 4, extraInfoPaint);
		}
	}
	
	/**
	 * ��������ͳ����Ϣ
	 * ��ͼ���е���Ϣ�����仯ʱ���ͻ���������������ͳ��������ϢҲ������Ӧ�ı仯
	 */
	private void refleshRangeInfo(){
		if(rangeInfoViewGroup == null){
			return;
		}
		String startDate = ((Jjjz)chartInfo.getPointExtraInfo().get(0)).getRq();
		String startDateStr = StringUtils.dateStringChange(startDate);
		
		String endDate = ((Jjjz)chartInfo.getPointExtraInfo().get(chartInfo.getPiontCount() - 1)).getRq();
		String endDateStr = StringUtils.dateStringChange(endDate);
		
		double startJz = chartInfo.getPoint().get(0);
		double endJz = chartInfo.getPoint().get(chartInfo.getPiontCount() - 1);
		
		String stratJzStr = StringUtils.stringLenthFormat(String.valueOf(startJz), 6, '0');
		String endJzStr = StringUtils.stringLenthFormat(String.valueOf(endJz), 6, '0');
		
		double startFqjz = Double.parseDouble(((Jjjz)chartInfo.getPointExtraInfo().get(0)).getFqjz());
		double endFqjz = Double.parseDouble(((Jjjz)chartInfo.getPointExtraInfo().get(chartInfo.getPiontCount() - 1)).getFqjz());
		double jzIncrease = endFqjz - startFqjz;
		double jzIncreasePrecent = jzIncrease / startFqjz;
		
		rangeInfoViewGroup.getStartDateTextView().setText(startDateStr);
		rangeInfoViewGroup.getEndDateTextView().setText(endDateStr);
		rangeInfoViewGroup.getStartJjTextView().setText(stratJzStr);
		rangeInfoViewGroup.getEndJjTextView().setText(endJzStr);
		if(jzIncrease < 0){
			String jzIncreaseStr = StringUtils.stringLenthFormat(String.valueOf(jzIncrease), 7, '0');
			String jzIncrsesePrecentStr = StringUtils.stringLenthFormat(String.valueOf(jzIncreasePrecent * 100), 6, '0') + "%";
			rangeInfoViewGroup.getRangeIncreaseTextView().setTextColor(Color.GREEN);
			rangeInfoViewGroup.getRangeIncreaseTextView().setText(jzIncreaseStr);
			rangeInfoViewGroup.getRangeIncreasePrecentTextView().setTextColor(Color.GREEN);
			rangeInfoViewGroup.getRangeIncreasePrecentTextView().setText(jzIncrsesePrecentStr);
		}else{
			String jzIncreaseStr = StringUtils.stringLenthFormat(String.valueOf(jzIncrease), 6, '0');
			String jzIncrsesePrecentStr = StringUtils.stringLenthFormat(String.valueOf(jzIncreasePrecent * 100), 5, '0') + "%";
			rangeInfoViewGroup.getRangeIncreaseTextView().setTextColor(Color.RED);
			rangeInfoViewGroup.getRangeIncreaseTextView().setText(jzIncreaseStr);
			rangeInfoViewGroup.getRangeIncreasePrecentTextView().setTextColor(Color.RED);
			rangeInfoViewGroup.getRangeIncreasePrecentTextView().setText(jzIncrsesePrecentStr);
		}
	}
	
	/**
	 * ���Ƽ�����
	 */
	private GestureDetector gestureDetector = new GestureDetector(new OnGestureListener()  
    {  
        /**
         * �û����ͼ��Ķ���������ֻ���ǵ������ͼ��ġ�ͼ�������⣬���������ȡ����ǰ�ĵ���ͼ����
         */
        public boolean onDown(MotionEvent event) {  
        	if(event.getX() < sizeInfo.getPaddingLeft() || event.getX() > (sizeInfo.getWidth() - getPaddingRight())){
        		//System.out.println(event.getX());
        		//System.out.println(sizeInfo.getWidth() - getPaddingRight());
        		JjjzsChart.this.postInvalidate();
        		isChecked = false;
        		return false;
        	}
            return true;  
        }  
        
        /**
         * �м��ٶȵĻ���������ͼ��ֻ�����м��ٶȵĻ���������û���ٶȵĻ�������Ч
         * �õ�������X���룬���һ������ȼ��������¡�
         * ����������������Ҽ�����س���ĳ��ֵ��X_FLING_DISTANCE������ô��������������������һ������������»�����
         */
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,    
                float velocityY) {  
        	
        	// �û����´������������ƶ����ɿ�,���ʱ�������ָ�˶����м��ٶȵġ�  
            // ��1��MotionEvent ACTION_DOWN,    
            // ���ACTION_MOVE, 1��ACTION_UP����    
            // e1����1��ACTION_DOWN MotionEvent    
            // e2�����һ��ACTION_MOVE MotionEvent    
            // velocityX��X���ϵ��ƶ��ٶȣ�����/��    
            // velocityY��Y���ϵ��ƶ��ٶȣ�����/��   
        	
        	//�Ƚ�ͼ���Ƿ񱻵�����ȡ����
        	isChecked = false;
        	
        	//�õ�������X���룬���һ������ȼ��������¡�
        	//����������������Ҽ�����س���ĳ��ֵ��X_FLING_DISTANCE������ô��������������������һ������������»�����
        	float distanceX = e2.getX() - e1.getX();
        	
        	//�����һ���
        	if(distanceX > X_FLING_DISTANCE) {
        		return JjjzsChart.this.scrollToRight();
        	}
        	
        	//���ҵ��󻬶�
        	else if(distanceX < - X_FLING_DISTANCE) {
        		return JjjzsChart.this.scrollToLeft();
        	}
        	
        	//�������»���
        	if(velocityY > 10){
        		return JjjzsChart.this.scrollToBottom();
        	}
        	
        	//�������ϻ���
        	else if(velocityY < -10){
        		return JjjzsChart.this.scrollToUp();
        	}
            return true;  
        }  
        
        // �û��������������ɶ��MotionEvent ACTION_DOWN����    
        public void onLongPress(MotionEvent event) {  
//        	System.out.println("on long pressed");  
        }  
        
        /**
         * �޼��ٶȵĻ�������������Ч����
         */
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,    
                float distanceY) { 
        	
        	// �����¼������ڴ�������Ѹ�ٵ��ƶ��������onScroll����ACTION_MOVE����  
            // e1����1��ACTION_DOWN MotionEvent  
            // e2�����һ��ACTION_MOVE MotionEvent    
            // distanceX�������ϴβ���onScroll�¼���X���ƶ��ľ���  
            // distanceY�������ϴβ���onScroll�¼���Y���ƶ��ľ���  
        	
            return true;  
        }  
        
          
        public void onShowPress(MotionEvent event) {  
        	//����˴�����������û���ƶ��͵���Ķ�����onShowPress��onDown����������  
            //onDown�ǣ�һ�����������£������ϲ���onDown�¼�������onShowPress��onDown�¼�������  
            //һ��ʱ���ڣ����û���ƶ����͵����¼�������Ϊ��onShowPress�¼���
        }  
        
        /**
         * ����ͼ���������������Ѿ���û�е㵽��ͼ���������ڵĶ������ų���onDown()����false����
         * ֻҪ���ǵ���ڡ�ͼ���ڵķ���
         */
        public boolean onSingleTapUp(MotionEvent event) {  
        	// ����������󣬵��������������в�����onLongPress��onScroll��onFling�¼����Ͳ������onSingleTapUp�¼���
        	//�õ��������X����
            float tapUpX = event.getX();
            //��ID��������������ͼ�����
            checkedID = touchXtoID(tapUpX);
            //����ͼ���Ƿ񱻵��Ϊ���桱
            isChecked = true;
            JjjzsChart.this.postInvalidate();
            return true;  
        }  
          
    });  
	
	/**
	 * ͼ���أ��������󻬶�
	 * @return
	 */
	private boolean scrollToLeft(){
		startOffset += ONCE_FLING_CHANGE_OFFSET;
		endOffset += ONCE_FLING_CHANGE_OFFSET;
		
		JjjzsChart.this.validateOffset();
		chartType = ChartType.SELF_CONFIG;
		JjjzsChart.this.postInvalidate();
		return true;
	}
	
	
	/**
	 * ͼ���أ��������һ���
	 * @return
	 */
	private boolean scrollToRight(){
		startOffset -= ONCE_FLING_CHANGE_OFFSET;
		endOffset -= ONCE_FLING_CHANGE_OFFSET;
		JjjzsChart.this.validateOffset();
		chartType = ChartType.SELF_CONFIG;
		JjjzsChart.this.postInvalidate();
		return true;
	}
	
	/**
	 * ͼ���أ��������ϻ���
	 * @return
	 */
	private boolean scrollToUp(){
		startOffset -= ONCE_FLING_CHANGE_OFFSET;
		JjjzsChart.this.validateOffset();
		chartType = ChartType.SELF_CONFIG;
		JjjzsChart.this.postInvalidate();
		return true;
	}
	
	/**
	 * ͼ���أ��������»���
	 * @return
	 */
	private boolean scrollToBottom(){
		startOffset += ONCE_FLING_CHANGE_OFFSET;
		JjjzsChart.this.validateOffset();
		chartType = ChartType.SELF_CONFIG;
		JjjzsChart.this.postInvalidate();
		return true;
	}
	
	/**
	 * ��֤�����ݵ���ʼ�����ֹ�㣬�Է�ֹ�������
	 */
	private void validateOffset(){
		if(endOffset < 1){
			endOffset = 1;
		}
		if(endOffset > jjjzs.getCount()){
			endOffset = jjjzs.getCount();
		}
		
		if(startOffset < 0){
			startOffset = 0;
		}
		if(startOffset > endOffset - 1){
			startOffset = endOffset -1;
		}
		
	}
	
	/**
	 * ������������ת��Ϊ��Ӧ�Ļ�����ChartPoints�е��±�
	 * ��������ת�������Ƕ��һ�٣��û������õ�X�¿��ܲ��ǻ���ͼ���ĳһ�㣬ͨ��������������ת���ܻ�����������������Ļ�����Ϣ
	 * @param touchX
	 * @return
	 */
	private int touchXtoID(float touchX){
		double cellX;
		double chartWidth = sizeInfo.getWidth() - sizeInfo.getPaddingLeft() - sizeInfo.getPaddingRight();
		if(isScroll){
			cellX = (double)chartWidth / (chartInfo.getDomainCount() -1);
		}else{
			cellX = (double)chartWidth / (chartInfo.getPiontCount() -1);
		}
		return (int)((touchX - sizeInfo.getPaddingLeft()) / cellX);
	}
	
	
	/**
	 * ��������±�ת��ΪJjjzsChart�е�X����
	 * @param ID
	 * @return
	 */
	private float IDtoX(int ID){
		//float cellX = (float)(sizeInfo.getWidth() - sizeInfo.getPaddingLeft() - sizeInfo.getPaddingRight()) / (chartInfo.getDomainCount() -1);
		double cellX;
		double chartWidth = sizeInfo.getWidth() - sizeInfo.getPaddingLeft() - sizeInfo.getPaddingRight();
		if(isScroll){
			cellX = (double)chartWidth / (chartInfo.getDomainCount() -1);
		}else{
			cellX = (double)chartWidth / (chartInfo.getPiontCount() -1);
		}
		return (float)(ID * cellX + sizeInfo.getPaddingLeft());
	}
	
	/**
	 * ͼ���е�ͼ�����ͣ�������ʾ����ֵ����������
	 * �� ʮ���ͣ�һ���ͣ�һ�����ͣ������ͣ�һ���ͣ����Զ���
	 * ��ʼ��ʱ��һ����
	 * �Զ��������û�������ͼ��֮������͡�
	 * @author zet
	 *
	 */
	public enum ChartType{
		TEN_DAY,
		MONTH,
		THREE_MONTH,
		HALF_YEAR,
		YEAR,
		SELF_CONFIG;
	}
}
