package sortingpackage;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.border.Border;

import java.util.*;

/** 
 * @ClassName: Paint
 * @description: 画图类
 * @author: 李俊良
 * @Date: 2019年12月20日 下午5:59:10
 */

public class PaintRec extends Panel{
	private static final long serialVersionUID = -1660684789314782175L;
	Graphics graphics;
	Border border;
	static int speed=500;  //正常速度
	int interval,xInterval;  //第二个为堆排序中最后一层中结点之间的间隔
	int xinterval,yinterval,begin,end;
	int []radix=new int[10];
	int []location=new int[4];
	int []height,xCoordinate,yCoordinate,numberX,numberY;
	int []xCoordinateTree,yCoordinateTree,numberXTree,numberYTree;
	MainGUI gui;
	public PaintRec(MainGUI gui) {
		this.gui=gui;
	}
	public int[] getLocation(JComponent component,int x[]) {  //获取组件的位置信息
		x[0]=component.getX();
		x[1]=component.getY();
		x[2]=component.getWidth();
		x[3]=component.getHeight();
		return x;
	}
	public int getMax(int []array) {  //求最大值，来预估矩形的高度
		Arrays.sort(array);
		return array[array.length-1];
	}
	
	//直接插入排序
	public void directInsertInit(int array[]) {   //初始状态下画图(直接插入排序)
		int []getMax=new int[array.length];
		System.arraycopy(array,0,getMax ,0,array.length);
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		interval=location[2]/(7*array.length+11);    //计算间隔
		begin=location[0]+6*interval;
		yinterval=(location[3]/2-40)/getMax(getMax);  //将屏幕分成两部分,各占一半。
		height=new int[array.length];   //初始画图时的高度
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		for(int i=0;i<array.length;i++) {   //设置每个矩形的高度
			height[i]=array[i]*yinterval;
		}
		int y=(location[1]+location[3])/2-25;
		for(int i=0;i<array.length;i++) {
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+2*interval,y+10);   //数字照样占10
			numberX[i]=begin+2*interval;    //记录数字位置的坐标
			numberY[i]=y+10;
			graphics.setColor(Color.orange);
			graphics.fill3DRect(begin, y-height[i], 6*interval, height[i],true);
			xCoordinate[i]=begin;    //存放每个小矩形的坐标
			yCoordinate[i]=y-height[i];
			begin+=7*interval;
		}
	}
	public void directInsertDown(int array[],int i,int speed) {    //实现插入算法中的第i个矩形块下落
		try {
			int []getMax=new int[array.length];
			System.arraycopy(array,0,getMax ,0,array.length);
			int max=getMax(getMax);
			int y=(location[1]+location[3])/2-25;
			int standard=location[1]+location[3];   //最下方基准线
			Thread.sleep(speed);
			graphics=gui.panel1.getGraphics();
			graphics.setColor(Color.orange);    //擦除一律按照最大矩形擦除
			graphics.clearRect(xCoordinate[i], y-max*yinterval, 6*interval, max*yinterval+25);   //擦掉矩形块以及下面的数字
			Thread.sleep(speed);
			graphics.fill3DRect(xCoordinate[i], standard-25-yinterval*array[i], 6*interval, yinterval*array[i],true);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), numberX[i], standard-10);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void directInsertUp(int array[],int i,int current,int speed) {
		graphics=gui.panel1.getGraphics();
		graphics.setColor(Color.orange);
		int standard=location[1]+location[3];   //下方标准线
		try {
			Thread.sleep(speed);
			int []getMax=new int[array.length];
			System.arraycopy(array,0,getMax ,0,array.length);
			graphics.clearRect(xCoordinate[i],standard-25-current*yinterval,6*interval,yinterval*current+25);
			graphics.fill3DRect(xCoordinate[i], numberY[i]-yinterval*current-15,6*interval, current*yinterval,true);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(current), numberX[i], numberY[i]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void directInsertLeft(int array[],int i,int current,int speed) {    //将下面小矩形左移的同时，其左上方的小矩形将右移
		try {
			int k=i;
			int []getMax=new int[array.length];
			System.arraycopy(array,0,getMax ,0,array.length);
			int x=getMax(getMax);
			int standard=location[1]+location[3];
			Thread.sleep(speed);
			graphics=gui.panel1.getGraphics();
			graphics.setColor(Color.orange);
			Thread.sleep(speed);
			graphics.clearRect(xCoordinate[i], standard-yinterval*current-25, 6*interval, yinterval*current+25);//先擦掉下面的小矩形，然后再将其左移
			//Thread.sleep(2*speed);
			i--;
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[i], numberY[i]-15-x*yinterval, 6*interval, x*yinterval+25);  //擦掉左上方的小矩形
			graphics.fill3DRect(xCoordinate[i],standard-25-current*yinterval,6*interval,current*yinterval,true);  //小矩形左移
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(current), numberX[i], standard-10);  //小矩形下面的数字左移
			graphics.setColor(Color.orange);
			graphics.fill3DRect(xCoordinate[k], numberY[k]-15-yinterval*array[i], 6*interval, yinterval*array[i],true);  //上方小矩形右移
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), numberX[k], numberY[k]);
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//冒泡排序与简单选择排序
	public void bubbleSortInit(int array[]){            //初始状态下画图(冒泡排序)
		int []getMax=new int[array.length];
		System.arraycopy(array,0,getMax ,0,array.length);
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int y=location[1]+location[3]-60;
		interval=location[2]/(7*array.length+11);    //计算间隔
		begin=location[0]+6*interval;
		yinterval=(y-location[1]-15)/getMax(getMax);
		height=new int[array.length];   //初始画图时的高度
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		for(int i=0;i<height.length;i++) {
			height[i]=array[i]*yinterval;
		}
		for(int i=0;i<array.length;i++) {
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+2*interval,y+15);   //写字间隔10
			numberX[i]=begin+2*interval;    //记录数字位置的坐标
			numberY[i]=y+15;
			graphics.setColor(Color.orange);
			graphics.fill3DRect(begin, y-height[i], 6*interval, height[i],true);
			xCoordinate[i]=begin;    //存放每个小矩形的坐标
			yCoordinate[i]=y-height[i];
			begin+=7*interval;
		}
	}
	public void exchangeCoordinate(int i,int j,int x[],int y[],int z[],int diff) {   //交换两个小矩形的坐标以及高
		int t;
		t=y[i];y[i]=y[j];y[j]=t;
		t=z[i];z[i]=z[j];z[j]=t;
	}
	public void bubbleSortExchange(int x,int y,int length,int array[],int speed) {   //冒泡排序交换两个小矩形
		graphics=gui.panel1.getGraphics();
		graphics.setColor(Color.orange);
		int standard=location[1]+location[3]-60;
		try {
			Thread.sleep(speed);
			graphics.clearRect(xCoordinate[x],standard-array[x]*yinterval,6*interval,array[x]*yinterval+25);
			graphics.fill3DRect(xCoordinate[x],standard-array[y]*yinterval,6*interval,array[y]*yinterval,true);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[y]), numberX[x], numberY[x]);
			Thread.sleep(speed);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[y],standard-array[y]*yinterval,6*interval,array[y]*interval+25);
			graphics.fill3DRect(xCoordinate[y],standard-array[x]*yinterval,6*interval,array[x]*yinterval,true);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[x]), numberX[y], numberY[y]);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//希尔排序
	public void shellSortInit(int array[]) {   //希尔排序初始画图
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int standard=location[1]+location[3]/2;
		interval=location[2]/(7*array.length+1);    //计算间隔
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		begin=location[0]+interval;
		int y=standard-6*interval;
		for(int i=0;i<array.length;i++) {
			graphics.fillOval(begin, y, 6*interval, 6*interval);
			xCoordinate[i]=begin;
			yCoordinate[i]=y;
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+(int)2.5*interval, standard-3*interval);
			numberX[i]=begin+(int)2.5*interval;
			numberY[i]=standard-3*interval;
		    graphics.setColor(Color.orange);
		    begin+=7*interval;
		}
	}
	public void drawShellGap(int gap,int speed) {    //写间隔提示
		try {
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(location[0]+25*interval-20, location[1]+location[3]/2+10*interval-20, 15*interval, 3*interval);
			Thread.sleep(2*speed);
			getLocation(gui.panel1, location);
			graphics.setColor(Color.orange);
			graphics.setFont(new Font("宋体",Font.BOLD,25));
			if(gap!=0) {
				graphics.drawString("step = "+gap, location[0]+25*interval, location[1]+location[3]/2+10*interval);
			}
			else {
				graphics.drawString("Completed!!", location[0]+25*interval, location[1]+location[3]/2+10*interval);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void shellUp(int array[],int i,int speed) {     //待排序数据上升
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.blue);
		try {
			Thread.sleep(2*speed);
			graphics.clearRect(xCoordinate[i], yCoordinate[i], 6*interval, 6*interval);
			Thread.sleep(2*speed);
			graphics.fillOval(xCoordinate[i], yCoordinate[i]-15*interval,6*interval, 6*interval);
			graphics.setColor(Color.red);
			graphics.drawString(String.valueOf(array[i]), numberX[i], numberY[i]-15*interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void shellDown(int i,int []array,int current,int speed) {   //待插入数据下落
		try {
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics(); 
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-15*interval, 6*interval, 6*interval);
			graphics.fillOval(xCoordinate[i], yCoordinate[i], 6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(current), numberX[i], numberY[i]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void shellLeft(int array[],int i,int current,int gap,int speed) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		try {
			Thread.sleep(2*speed);
			Thread.sleep(2*speed);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-15*interval, 6*interval, 6*interval);
			i-=gap;
			graphics.setColor(Color.blue);
			graphics.fillOval(xCoordinate[i], yCoordinate[i]-15*interval, 6*interval,6*interval);
			graphics.setColor(Color.red);
			graphics.drawString(String.valueOf(current), numberX[i], numberY[i]-15*interval);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[i], yCoordinate[i], 6*interval, 6*interval);
			graphics.fillOval(xCoordinate[i+gap], yCoordinate[i+gap], 6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), numberX[i+gap], numberY[i+gap]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//快速排序
	public void drawArrow(int x1,int y1,int x2,int y2) {   //画箭头
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.red);
		graphics.drawLine(x1, y1, x2, y2);
		graphics.drawLine(x2, y2, x2-(int)(y2-y1)/4, y2-(int)(y2-y1)/4);
		graphics.drawLine(x2, y2, x2+(int)(y2-y1)/4, y2-(int)(y2-y1)/4);
	}
	public void quickSortInit(int array[],int low,int high) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int standard=location[1]+location[3]/2;
		interval=location[2]/(7*array.length+1);    //计算间隔
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		begin=location[0]+interval;
		int y=standard-6*interval;
		for(int i=0;i<array.length;i++) {
			graphics.fillOval(begin, y, 6*interval, 6*interval);
			xCoordinate[i]=begin;
			yCoordinate[i]=y;
			//System.out.println(xCoordinate[i]+" "+yCoordinate[i]);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+(int)2.5*interval, standard-3*interval);
			numberX[i]=begin+(int)2.5*interval;
			numberY[i]=standard-3*interval;
		    graphics.setColor(Color.orange);
		    begin+=7*interval;
		}
		//画两个箭头以及low,high提示符
		drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
		drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
		graphics.setFont(new Font("宋体",Font.BOLD,15));
		graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
		graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
	}
	public void quickSortMoveArrow(int i,boolean flag,int judge,int speed) {     //flag为真时--high,否则++low,需要判断是否重合
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		try {
			Thread.sleep(2*speed);
			//System.out.println(xCoordinate[1]);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-40, 6*interval, 40);  //清除箭头
			graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 10*interval);   //先清除字符提示以及光标
			Thread.sleep(2*speed);
			if(flag) {
				i--;
				if(i==judge) {     //high--后与low刚好重合，则将high放在上面，low在下面
					graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 10*interval);   //先清除掉low
					graphics.setFont(new Font("宋体",Font.BOLD,15));
					graphics.drawString("high", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);  //写high
					graphics.drawString("low", xCoordinate[i]+2*interval, yCoordinate[i]+10*interval);  //写low
				}
				else {
					drawArrow(xCoordinate[i]+3*interval, yCoordinate[i]-40, xCoordinate[i]+3*interval, yCoordinate[i]);
					graphics.setFont(new Font("宋体",Font.BOLD,15));
					graphics.drawString("high", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);
				}
			}
			else {
				i++;
				if(i==judge) {    //low++后与high刚好重合
					graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 10*interval);   //先清除掉low
					graphics.setFont(new Font("宋体",Font.BOLD,15));
					graphics.drawString("high", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);  //写high
					graphics.drawString("low", xCoordinate[i]+2*interval, yCoordinate[i]+10*interval);  //写low
				}
				else {
					drawArrow(xCoordinate[i]+3*interval, yCoordinate[i]-40, xCoordinate[i]+3*interval, yCoordinate[i]);
					graphics.setFont(new Font("宋体",Font.BOLD,15));
					graphics.drawString("low", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void quickSortReplacement(int array[],int low,int high,boolean flag,int speed) {    //true时array[low] = array[high]; 
		try {
			Thread.sleep(speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.blue);
			if(flag) {
				graphics.fillOval(xCoordinate[high], yCoordinate[high]-15*interval, 6*interval, 6*interval);  //high上移
				graphics.setColor(Color.red);
				graphics.drawString(String.valueOf(array[high]), numberX[high], numberY[high]-15*interval);
				Thread.sleep(speed);
				graphics.clearRect(xCoordinate[high], yCoordinate[high]-15*interval, 6*interval, 6*interval);
                graphics.setColor(Color.blue);
				graphics.fillOval(xCoordinate[low], yCoordinate[low]-15*interval, 6*interval, 6*interval);
				graphics.setColor(Color.red);
				graphics.drawString(String.valueOf(array[high]), numberX[low], numberY[low]-15*interval);
				graphics.clearRect(xCoordinate[low], yCoordinate[low], 6*interval, 6*interval);
				graphics.setColor(Color.blue);
				graphics.fillOval(xCoordinate[low], yCoordinate[low], 6*interval, 6*interval);
				graphics.setColor(Color.red);
				graphics.drawString(String.valueOf(array[low]), numberX[low], numberY[low]);
				Thread.sleep(speed);
				graphics.clearRect(xCoordinate[low], yCoordinate[low], 6*interval, 6*interval);
				graphics.clearRect(xCoordinate[low], yCoordinate[low]-15*interval, 6*interval, 6*interval);
				Thread.sleep(speed);
				graphics.setColor(Color.orange);
				graphics.fillOval(xCoordinate[low], yCoordinate[low], 6*interval, 6*interval);
				graphics.setColor(Color.black);
				graphics.drawString(String.valueOf(array[high]), numberX[low], numberY[low]);
			}
			else {
				graphics.fillOval(xCoordinate[low], yCoordinate[low]-15*interval, 6*interval, 6*interval);  //low上移
				graphics.setColor(Color.red);
				graphics.drawString(String.valueOf(array[low]), numberX[low], numberY[low]-15*interval);
				Thread.sleep(speed);
				graphics.clearRect(xCoordinate[low], yCoordinate[low]-15*interval, 6*interval, 6*interval);
                graphics.setColor(Color.blue);
				graphics.fillOval(xCoordinate[high], yCoordinate[high]-15*interval, 6*interval, 6*interval);
				graphics.setColor(Color.red);
				graphics.drawString(String.valueOf(array[low]), numberX[high], numberY[high]-15*interval);
				graphics.clearRect(xCoordinate[high], yCoordinate[high], 6*interval, 6*interval);
				graphics.setColor(Color.blue);
				graphics.fillOval(xCoordinate[high], yCoordinate[high], 6*interval, 6*interval);
				graphics.setColor(Color.red);
				graphics.drawString(String.valueOf(array[high]), numberX[high], numberY[high]);
				Thread.sleep(speed);
				graphics.clearRect(xCoordinate[high], yCoordinate[high], 6*interval, 6*interval);
				graphics.clearRect(xCoordinate[high], yCoordinate[high]-15*interval, 6*interval, 6*interval);
				Thread.sleep(speed);
				graphics.setColor(Color.orange);
				graphics.fillOval(xCoordinate[high], yCoordinate[high], 6*interval, 6*interval);
				graphics.setColor(Color.black);
				graphics.drawString(String.valueOf(array[low]), numberX[high], numberY[high]);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void quickSortClean(int i,int speed) {       //清除重合的low与high
		try {
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 18*interval);  //清除掉下方的两个字
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-40, 6*interval, 40);    //清除掉上面的箭头
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void quickSortTwoArrow(int low,int high,int speed) {
		try {
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			if(low==high) {
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("high", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
			}
			else {
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void quickLastSteps(int low,int pivot,int speed) {
		try {
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[low], yCoordinate[low], 6*interval, 6*interval);
			Thread.sleep(2*speed);
			graphics.fillOval(xCoordinate[low], yCoordinate[low], 6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(pivot), numberX[low], numberY[low]);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//折半插入排序
	public void binsertSortMovement(int low,int high,String str,int speed) {    //low=mid+1以及high=mid-1
		try {
			//System.out.println(low+"  "+high);
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			int mid=(low+high)/2;
			//graphics.clearRect(xCoordinate[mid], yCoordinate[mid]-40, 6*interval, 40);   //擦掉mid箭头
			//graphics.clearRect(xCoordinate[mid], yCoordinate[mid]+6*interval, 6*interval, 10*interval);   //清除mid字符
			if(str.equals("low")) {             //low=mid+1,需要判断low与high是否重合以及low+1==high此种情况
				if(low==high) {//此时三个箭头重合,要清除掉low
					graphics.clearRect(xCoordinate[mid], yCoordinate[mid]+8*interval, 6*interval, 10*interval);      //清除low字符
					graphics.drawString("high", xCoordinate[mid], yCoordinate[mid]+8*interval);
					graphics.drawString("mid", xCoordinate[mid], yCoordinate[mid]+10*interval);
					drawArrow(xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]-40, xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]);
					graphics.drawString("low", xCoordinate[mid+1], yCoordinate[mid+1]+8*interval);
				}
				if(low+1==high) {
					graphics.clearRect(xCoordinate[low], yCoordinate[low]-40, 6*interval, 40);//清除low箭头
					graphics.clearRect(xCoordinate[low], yCoordinate[low]+8*interval, 6*interval,10*interval);//清除low与mid字符
					graphics.drawString("low", xCoordinate[mid+1], yCoordinate[mid+1]+10*interval);
					graphics.drawString("mid", xCoordinate[mid+1], yCoordinate[mid+1]+12*interval);
				}
				else {    //low=mid+1,正常操作
					graphics.clearRect(xCoordinate[low], yCoordinate[low]-40, 6*interval, 40);   //清除掉low箭头
					graphics.clearRect(xCoordinate[low], yCoordinate[low]+6*interval, 6*interval, 10*interval);   //清除字符
					drawArrow(xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]-40, xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]);
					graphics.setFont(new Font("宋体",Font.BOLD,15));
					graphics.drawString(str, xCoordinate[mid+1]+2*interval, yCoordinate[mid+1]+8*interval);
					int mid1=(mid-1+low)/2;
					drawArrow(xCoordinate[mid1]+3*interval, yCoordinate[mid1]-40, xCoordinate[mid1]+3*interval, yCoordinate[mid1]);
					graphics.drawString("mid", xCoordinate[mid1]+2*interval, yCoordinate[mid1]+8*interval);
				}
			}
			else {     //high=mid-1，需要判断mid是否为0
				if(mid!=0) {
					graphics.clearRect(xCoordinate[high], yCoordinate[high]-40, 6*interval, 40);   //清除掉high箭头
					graphics.clearRect(xCoordinate[high], yCoordinate[high]+6*interval, 6*interval, 10*interval);   //清除字符
					drawArrow(xCoordinate[mid-1]+3*interval, yCoordinate[mid-1]-40, xCoordinate[mid-1]+3*interval, yCoordinate[mid-1]);
					graphics.setFont(new Font("宋体",Font.BOLD,15));
					graphics.drawString(str, xCoordinate[mid-1]+2*interval, yCoordinate[mid-1]+8*interval);
					int mid2=(mid-1+low)/2;
					drawArrow(xCoordinate[mid2]+3*interval, yCoordinate[mid2]-40, xCoordinate[mid2]+3*interval, yCoordinate[mid2]);
					graphics.drawString("mid", xCoordinate[mid2]+2*interval, yCoordinate[mid2]+8*interval);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void binsertSortInit(int array[],int low,int high) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int standard=location[1]+location[3]/2;
		interval=location[2]/(7*array.length+8);    //计算间隔，保留一个位置，应对high=-1的情况
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		begin=location[0]+12*interval;
		int y=standard-6*interval;
		for(int i=0;i<array.length;i++) {
			graphics.fillOval(begin, y, 6*interval, 6*interval);
			xCoordinate[i]=begin;
			yCoordinate[i]=y;
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+(int)2.5*interval, standard-3*interval);
			numberX[i]=begin+(int)2.5*interval;
			numberY[i]=standard-3*interval;
		    graphics.setColor(Color.orange);
		    begin+=7*interval;
		}
		//画三个箭头以及low,high,mid提示符，先判断low与high是否相等
		int mid=(low+high)/2;
		if(low==high) {      //low==high三者重合
			drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
			graphics.setFont(new Font("宋体",Font.BOLD,15));
			graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			graphics.drawString("low", xCoordinate[high]+2*interval, yCoordinate[high]+10*interval);
			graphics.drawString("mid", xCoordinate[high]+2*interval, yCoordinate[high]+12*interval);
		}
		else if(low+1==high){    //low与mid重合
			drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
			drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
			//drawArrow(xCoordinate[mid]+3*interval, yCoordinate[mid]-40, xCoordinate[mid]+3*interval, yCoordinate[mid]);
			
			graphics.setFont(new Font("宋体",Font.BOLD,15));
			graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
			graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
		}
		else {
			drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
			drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
			drawArrow(xCoordinate[mid]+3*interval, yCoordinate[mid]-40, xCoordinate[mid]+3*interval, yCoordinate[mid]);
			
			graphics.setFont(new Font("宋体",Font.BOLD,15));
			graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
			graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			graphics.drawString("mid", xCoordinate[mid]+2*interval, yCoordinate[mid]+8*interval);
		}
	}
	
	public void binsertClean(int array[]) {       //清除一切提示符
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		for(int i=0;i<array.length;i++) {
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-40, 6*interval, 40);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 7*interval);
		}
		graphics.clearRect(xCoordinate[0]-4*interval, yCoordinate[0]-40, 6*interval, 40);
		graphics.clearRect(xCoordinate[0]-4*interval, yCoordinate[0]+6*interval, 6*interval, 7*interval);
	}
	
	public void drawArrowAgain(int low,int high,int array[],int speed) {    //high==-1的情况经常出现
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		int mid=(low+high)/2;
		try {
			Thread.sleep(2*speed);
			if(low==high) {         //low==high==mid
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+10*interval);
				graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+12*interval);
				Thread.sleep(2*speed);
			}
			else if(low+1==high) {    //low==mid
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
				graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
				Thread.sleep(2*speed);
			}
			else if(high+1==low&&high!=-1) {   //high==mid  3 2 2
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
				graphics.drawString("mid", xCoordinate[high]+2*interval, yCoordinate[low]+10*interval);
				Thread.sleep(3*speed);
			}
			else if(high==-1) {   //0 -1 0
				binsertClean(array);  //先清除掉
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[0]-3*interval, yCoordinate[0]-40, xCoordinate[0]-3*interval, yCoordinate[0]);//high
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[0]-4*interval, yCoordinate[0]+8*interval);
				graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
				Thread.sleep(2*speed);
			}
			else {   //三者互不相等
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[mid]+3*interval, yCoordinate[mid]-40, xCoordinate[mid]+3*interval, yCoordinate[mid]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("宋体",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
				graphics.drawString("mid", xCoordinate[mid]+2*interval, yCoordinate[mid]+8*interval);
				Thread.sleep(2*speed);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void binsertSortUp(int i,int array[],int speed) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.blue);
		try {
			graphics.clearRect(xCoordinate[i], yCoordinate[i], 6*interval, 6*interval);
			graphics.fillOval(xCoordinate[i], yCoordinate[i]-20*interval, 6*interval, 6*interval);
			graphics.setColor(Color.red);
			graphics.drawString(String.valueOf(array[i]), numberX[i], numberY[i]-20*interval);
			Thread.sleep(2*speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void binsertSortReplacement(int array[],int j,int speed) {       //array[j+1] = array[j];
		try {
			Thread.sleep(speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.clearRect(xCoordinate[j], yCoordinate[j], 6*interval, 6*interval);   //先清除掉array[j]
			Thread.sleep(speed);
			graphics.setColor(Color.blue);
			graphics.fillOval(xCoordinate[j], yCoordinate[j]-10*interval, 6*interval, 6*interval);  //array[j]上移
			graphics.setColor(Color.red);
			graphics.drawString(String.valueOf(array[j]), numberX[j], numberY[j]-10*interval);
			Thread.sleep(speed);
			graphics.clearRect(xCoordinate[j], yCoordinate[j]-10*interval, 6*interval, 6*interval);//清除掉上移的array[j]
			Thread.sleep(speed);
			graphics.setColor(Color.blue);    //array[j]右移的同时清除掉下面的数据
			graphics.fillOval(xCoordinate[j+1], yCoordinate[j+1]-10*interval, 6*interval, 6*interval);
			graphics.setColor(Color.red);
			graphics.drawString(String.valueOf(array[j]), numberX[j+1], numberY[j+1]-10*interval);
			graphics.clearRect(xCoordinate[j+1], yCoordinate[j+1], 6*interval, 6*interval);
			Thread.sleep(speed);
			graphics.setColor(Color.orange);   //array[j+1]=array[j]
			graphics.clearRect(xCoordinate[j+1], yCoordinate[j+1]-10*interval, 6*interval, 6*interval);
			graphics.fillOval(xCoordinate[j+1], yCoordinate[j+1], 6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[j]), numberX[j+1], numberY[j+1]);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void binsertSortLastSteps(int high,int tempt,int i,int speed) {      //array[high+1] = tempt; 
		try {
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-20*interval, 6*interval, 6*interval);
			Thread.sleep(2*speed);
			graphics.setColor(Color.orange);
			graphics.fillOval(xCoordinate[high+1], yCoordinate[high+1], 6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(tempt), numberX[high+1], numberY[high+1]);
			Thread.sleep(3*speed);    //完成插入后睡眠1.5s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//堆排序
	public void heapSortInit(int []array,int speed) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int standard=location[1]+location[3];
		interval=location[2]/(7*array.length+1);    //计算间隔
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		begin=location[0]+interval;
		int y=standard-10*interval;
		for(int i=0;i<array.length;i++) {
			graphics.fillOval(begin, y, 6*interval, 6*interval);
			xCoordinate[i]=begin;
			yCoordinate[i]=y;
			//System.out.println(xCoordinate[i]+" "+yCoordinate[i]);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+(int)2.5*interval, standard-7*interval);
			numberX[i]=begin+(int)2.5*interval;
			numberY[i]=standard-7*interval;
		    graphics.setColor(Color.orange);
		    begin+=7*interval;
		}
		//开始先搭建初始的树
		try {
			xCoordinateTree=new int[array.length];    //存储树结点各个位置的坐标
			yCoordinateTree=new int[array.length];
			numberXTree=new int[array.length];
			numberYTree=new int[array.length];
			Thread.sleep(2*speed);
			int level=(int)Math.floor(Math.log((double)array.length)/Math.log(2.0))+1;    //二叉树的层数
			//需要计算每一层的间隔，每一层的间隔是不一样的
			int intervalPerLevel[]=new int[level];
			intervalPerLevel[0]=0;
			//System.out.println(level);
			double mid=Math.pow(2,level-1)*3;
			double mid1=(Math.pow(2,level-1)-1)*6;
			xInterval=(int)(location[2]-40)/(int)(mid+mid1);
			int yInterval=6*xInterval;
			intervalPerLevel[level-1]=6*xInterval;    //树结点最后一层间隔，上层间隔依次递增
			for(int i=level-2;i>=0;i--) {
				intervalPerLevel[i]=(int)(1.5*intervalPerLevel[i+1]);
			}
			int midStandard=location[0]+location[2]/2;    //中线位置的X坐标
			
			//先确定每个树结点的位置
			xCoordinateTree[0]=midStandard;yCoordinateTree[0]=location[1]+3*xInterval;
			for(int i=0;i<array.length;i++) {
				treeCoordinate(i, xInterval,array.length,intervalPerLevel,yInterval);   //求出所有树结点坐标
			}
			for(int i=0;i<array.length;i++) {
				numberXTree[i]=xCoordinateTree[i]+(int)1.5*xInterval;
				numberYTree[i]=yCoordinateTree[i]+(int)2*xInterval;
			}
			for(int i=0;i<array.length;i++) {     //画结点
				graphics.clearRect(xCoordinate[i], yCoordinate[i], 6*interval, 6*interval);   //清除下方小圆形
				Thread.sleep(2*speed);
				graphics.setColor(Color.orange);
				graphics.fillOval(xCoordinateTree[i], yCoordinateTree[i], 3*xInterval, 3*xInterval);
				graphics.setColor(Color.black);
				graphics.drawString(String.valueOf(array[i]), numberXTree[i], numberYTree[i]);
			}
			//画线
			for(int i=0;i<array.length;i++) {
				if(2*i+1<array.length&&2*i+2<array.length) {    //两个孩子均存在
					drawBranch1(xCoordinateTree[i], yCoordinateTree[i], xCoordinateTree[2*i+1], yCoordinateTree[2*i+1],xInterval);
					drawBranch2(xCoordinateTree[i], yCoordinateTree[i], xCoordinateTree[2*i+2], yCoordinateTree[2*i+2],xInterval);
				}
				if(2*i+2==array.length) {    //只存在左孩子
					drawBranch1(xCoordinateTree[i], yCoordinateTree[i], xCoordinateTree[2*i+1], yCoordinateTree[2*i+1],xInterval);
				}
				if(2*i+1==array.length) {   //左右孩子均不存在
					
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void treeCoordinate(int i,int xInterval,int length,int intervalPerLevel[],int yInterval) {
		//先计算出结点所在层数
		int nodeLevel=(int)Math.floor(Math.log((double)(i+1))/Math.log(2.0))+1;   //第i个结点所在的层次
		if(2*i+1<length&&2*i+2<length) {
			xCoordinateTree[2*i+1]=xCoordinateTree[i]-(int)(0.5*intervalPerLevel[nodeLevel-1]);
			yCoordinateTree[2*i+1]=yCoordinateTree[i]+yInterval;
			xCoordinateTree[2*i+2]=xCoordinateTree[i]+(int)(0.5*intervalPerLevel[nodeLevel-1]);
			yCoordinateTree[2*i+2]=yCoordinateTree[i]+yInterval;
		}
		if(2*i+2==length) {
			xCoordinateTree[2*i+1]=xCoordinateTree[i]-(int)(0.5*intervalPerLevel[nodeLevel-1]);
			yCoordinateTree[2*i+1]=yCoordinateTree[i]+yInterval;
		}
		if(2*i+1==length) {
			
		}
	}
	
	public void drawBranch1(int x1,int y1,int x2,int y2,int xInterval) {   //画左孩子
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.red);
		Graphics2D g = (Graphics2D)graphics;  //g是Graphics对象
		g.setStroke(new BasicStroke(3.0f));
		graphics.drawLine(x1, y1+3*xInterval, x2+3*xInterval, y2);
	}
	
	public void drawBranch2(int x1,int y1,int x2,int y2,int xInterval) {   //画右孩子
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.red);
		Graphics2D g = (Graphics2D)graphics;  //g是Graphics对象
		g.setStroke(new BasicStroke(3.0f));
		graphics.drawLine(x1+3*xInterval, y1+3*xInterval, x2, y2);
	}
	
	public void heapSortExchange(int i,int j,int array[],int speed) {    //初始建堆时交换两个结点
		try {
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinateTree[i], yCoordinateTree[i], 3*xInterval, 3*xInterval);
			graphics.clearRect(xCoordinateTree[j], yCoordinateTree[j], 3*xInterval, 3*xinterval);
			Thread.sleep(2*speed);
			graphics.fillOval(xCoordinateTree[i], yCoordinateTree[i], 3*xInterval, 3*xInterval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[j]), numberXTree[i], numberYTree[i]);
			Thread.sleep(speed);
			graphics.setColor(Color.orange);
			graphics.fillOval(xCoordinateTree[j], yCoordinateTree[j], 3*xInterval, 3*xInterval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), numberXTree[j], numberYTree[j]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void heapSortLast(int array[]) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		for(int i=0;i<array.length;i++) {
			graphics.fillOval(xCoordinate[i],yCoordinate[i] ,6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[array.length-i-1]), numberX[i],numberY[i]);
			graphics.setColor(Color.orange);
		}
	}
	
	//归并排序
	public void mergeSortInit(int array[]) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int standard=location[1];
		interval=location[2]/(7*array.length+1);    //计算间隔
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		begin=location[0]+interval;
		int y=standard+3*interval;
		for(int i=0;i<array.length;i++) {
			graphics.fillOval(begin, y, 6*interval, 6*interval);
			xCoordinate[i]=begin;
			yCoordinate[i]=y;
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+(int)2.5*interval, standard+(int)6*interval);
			numberX[i]=begin+(int)2.5*interval;
			numberY[i]=standard+(int)6*interval;
		    graphics.setColor(Color.orange);
		    begin+=7*interval;
		}
	}
	
	public void mergeSortYMove(int yCoordinate[],int numberY[]) {
		int move=7*interval;
		for(int i=0;i<yCoordinate.length;i++) {
			yCoordinate[i]+=move;
			numberY[i]+=move;
		}
	}
	
	
	public void mergeSortCore(int arr[],int index,int i,int speed) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		try {
			Thread.sleep(2*speed);
			graphics.fillOval(xCoordinate[index], yCoordinate[index], 6*interval, 6*interval);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(arr[i]), numberX[index], numberY[index]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	//基数排序
	public void radixSortInit(int array[]) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//获取面板的位置
		int standard=location[1];
		interval=location[2]/(7*array.length+1);    //计算间隔
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		begin=location[0]+(int)2.5*interval;
		int y=standard+3*interval;
		for(int i=0;i<array.length;i++) {
			graphics.fill3DRect(begin, y, 6*interval, 6*interval, true);
			xCoordinate[i]=begin;
			yCoordinate[i]=y;
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+(int)2.5*interval, standard+(int)6*interval);
			numberX[i]=begin+(int)2.5*interval;
			numberY[i]=standard+(int)6*interval;
		    graphics.setColor(Color.orange);
		    begin+=7*interval;
		}
		//画下方接收器与分配器
		graphics.setColor(Color.black);
		int yStandard=location[1]+location[3]-6*interval;
		for(int i=0;i<radix.length;i++) {
			radix[i]=0;             //存储每个接收器上接收到的数字个数
		}
		for(int i=0;i<10;i++) {
			graphics.drawLine(xCoordinate[i],yStandard, xCoordinate[i]+6*interval, yStandard);
			graphics.drawString(String.valueOf(i), xCoordinate[i]+3*interval, yStandard+3*interval);
		}
	}
	
	public void buckeListAdd(int num,int j,int array[],int speed) {   //bucketList.get(num).add(array[j])
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		int yStandard=location[1]+location[3]-15*interval;   //下方第一个小矩形的坐标
		try {
			Thread.sleep(speed);
			radix[num]++;
			graphics.clearRect(xCoordinate[j], yCoordinate[j], 6*interval, 6*interval); //先清除掉上方待加入的数据
			Thread.sleep(speed);
			graphics.setColor(Color.orange);
			graphics.fill3DRect(xCoordinate[num], yStandard-(radix[num]-1)*7*interval, 6*interval, 6*interval, true);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[j]), numberX[num], yStandard-(radix[num]-1)*7*interval+3*interval);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void buckeListClean(int index,int j,int k,int current,int speed) {         //array[index++] = bucketList.get(j).get(k);
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		int yStandard=location[1]+location[3]-15*interval;   //下方第一个小矩形的坐标,相邻两个矩形y坐标相差8*interval
		int yNumberk=yStandard-k*7*interval;   //第k个小矩形的坐标
		try {
			radix[j]--;
			Thread.sleep(speed);
			graphics.clearRect(xCoordinate[j], yNumberk, 6*interval, 6*interval);
			Thread.sleep(speed);
			graphics.setColor(Color.orange);
			graphics.fill3DRect(xCoordinate[index], yCoordinate[index], 6*interval, 6*interval, true);
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(current), numberX[index], numberY[index]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
 