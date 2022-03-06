package sortingpackage;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.border.Border;

import java.util.*;

/** 
 * @ClassName: Paint
 * @description: ��ͼ��
 * @author: ���
 * @Date: 2019��12��20�� ����5:59:10
 */

public class PaintRec extends Panel{
	private static final long serialVersionUID = -1660684789314782175L;
	Graphics graphics;
	Border border;
	static int speed=500;  //�����ٶ�
	int interval,xInterval;  //�ڶ���Ϊ�����������һ���н��֮��ļ��
	int xinterval,yinterval,begin,end;
	int []radix=new int[10];
	int []location=new int[4];
	int []height,xCoordinate,yCoordinate,numberX,numberY;
	int []xCoordinateTree,yCoordinateTree,numberXTree,numberYTree;
	MainGUI gui;
	public PaintRec(MainGUI gui) {
		this.gui=gui;
	}
	public int[] getLocation(JComponent component,int x[]) {  //��ȡ�����λ����Ϣ
		x[0]=component.getX();
		x[1]=component.getY();
		x[2]=component.getWidth();
		x[3]=component.getHeight();
		return x;
	}
	public int getMax(int []array) {  //�����ֵ����Ԥ�����εĸ߶�
		Arrays.sort(array);
		return array[array.length-1];
	}
	
	//ֱ�Ӳ�������
	public void directInsertInit(int array[]) {   //��ʼ״̬�»�ͼ(ֱ�Ӳ�������)
		int []getMax=new int[array.length];
		System.arraycopy(array,0,getMax ,0,array.length);
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//��ȡ����λ��
		interval=location[2]/(7*array.length+11);    //������
		begin=location[0]+6*interval;
		yinterval=(location[3]/2-40)/getMax(getMax);  //����Ļ�ֳ�������,��ռһ�롣
		height=new int[array.length];   //��ʼ��ͼʱ�ĸ߶�
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		for(int i=0;i<array.length;i++) {   //����ÿ�����εĸ߶�
			height[i]=array[i]*yinterval;
		}
		int y=(location[1]+location[3])/2-25;
		for(int i=0;i<array.length;i++) {
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+2*interval,y+10);   //��������ռ10
			numberX[i]=begin+2*interval;    //��¼����λ�õ�����
			numberY[i]=y+10;
			graphics.setColor(Color.orange);
			graphics.fill3DRect(begin, y-height[i], 6*interval, height[i],true);
			xCoordinate[i]=begin;    //���ÿ��С���ε�����
			yCoordinate[i]=y-height[i];
			begin+=7*interval;
		}
	}
	public void directInsertDown(int array[],int i,int speed) {    //ʵ�ֲ����㷨�еĵ�i�����ο�����
		try {
			int []getMax=new int[array.length];
			System.arraycopy(array,0,getMax ,0,array.length);
			int max=getMax(getMax);
			int y=(location[1]+location[3])/2-25;
			int standard=location[1]+location[3];   //���·���׼��
			Thread.sleep(speed);
			graphics=gui.panel1.getGraphics();
			graphics.setColor(Color.orange);    //����һ�ɰ��������β���
			graphics.clearRect(xCoordinate[i], y-max*yinterval, 6*interval, max*yinterval+25);   //�������ο��Լ����������
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
		int standard=location[1]+location[3];   //�·���׼��
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
	public void directInsertLeft(int array[],int i,int current,int speed) {    //������С�������Ƶ�ͬʱ�������Ϸ���С���ν�����
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
			graphics.clearRect(xCoordinate[i], standard-yinterval*current-25, 6*interval, yinterval*current+25);//�Ȳ��������С���Σ�Ȼ���ٽ�������
			//Thread.sleep(2*speed);
			i--;
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[i], numberY[i]-15-x*yinterval, 6*interval, x*yinterval+25);  //�������Ϸ���С����
			graphics.fill3DRect(xCoordinate[i],standard-25-current*yinterval,6*interval,current*yinterval,true);  //С��������
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(current), numberX[i], standard-10);  //С�����������������
			graphics.setColor(Color.orange);
			graphics.fill3DRect(xCoordinate[k], numberY[k]-15-yinterval*array[i], 6*interval, yinterval*array[i],true);  //�Ϸ�С��������
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), numberX[k], numberY[k]);
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//ð���������ѡ������
	public void bubbleSortInit(int array[]){            //��ʼ״̬�»�ͼ(ð������)
		int []getMax=new int[array.length];
		System.arraycopy(array,0,getMax ,0,array.length);
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//��ȡ����λ��
		int y=location[1]+location[3]-60;
		interval=location[2]/(7*array.length+11);    //������
		begin=location[0]+6*interval;
		yinterval=(y-location[1]-15)/getMax(getMax);
		height=new int[array.length];   //��ʼ��ͼʱ�ĸ߶�
		xCoordinate=new int[array.length];
		yCoordinate=new int[array.length];
		numberX=new int[array.length];
		numberY=new int[array.length];
		for(int i=0;i<height.length;i++) {
			height[i]=array[i]*yinterval;
		}
		for(int i=0;i<array.length;i++) {
			graphics.setColor(Color.black);
			graphics.drawString(String.valueOf(array[i]), begin+2*interval,y+15);   //д�ּ��10
			numberX[i]=begin+2*interval;    //��¼����λ�õ�����
			numberY[i]=y+15;
			graphics.setColor(Color.orange);
			graphics.fill3DRect(begin, y-height[i], 6*interval, height[i],true);
			xCoordinate[i]=begin;    //���ÿ��С���ε�����
			yCoordinate[i]=y-height[i];
			begin+=7*interval;
		}
	}
	public void exchangeCoordinate(int i,int j,int x[],int y[],int z[],int diff) {   //��������С���ε������Լ���
		int t;
		t=y[i];y[i]=y[j];y[j]=t;
		t=z[i];z[i]=z[j];z[j]=t;
	}
	public void bubbleSortExchange(int x,int y,int length,int array[],int speed) {   //ð�����򽻻�����С����
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
	
	//ϣ������
	public void shellSortInit(int array[]) {   //ϣ�������ʼ��ͼ
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//��ȡ����λ��
		int standard=location[1]+location[3]/2;
		interval=location[2]/(7*array.length+1);    //������
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
	public void drawShellGap(int gap,int speed) {    //д�����ʾ
		try {
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(location[0]+25*interval-20, location[1]+location[3]/2+10*interval-20, 15*interval, 3*interval);
			Thread.sleep(2*speed);
			getLocation(gui.panel1, location);
			graphics.setColor(Color.orange);
			graphics.setFont(new Font("����",Font.BOLD,25));
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
	public void shellUp(int array[],int i,int speed) {     //��������������
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
	public void shellDown(int i,int []array,int current,int speed) {   //��������������
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
	
	//��������
	public void drawArrow(int x1,int y1,int x2,int y2) {   //����ͷ
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
		getLocation(gui.panel1,location);//��ȡ����λ��
		int standard=location[1]+location[3]/2;
		interval=location[2]/(7*array.length+1);    //������
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
		//��������ͷ�Լ�low,high��ʾ��
		drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
		drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
		graphics.setFont(new Font("����",Font.BOLD,15));
		graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
		graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
	}
	public void quickSortMoveArrow(int i,boolean flag,int judge,int speed) {     //flagΪ��ʱ--high,����++low,��Ҫ�ж��Ƿ��غ�
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		try {
			Thread.sleep(2*speed);
			//System.out.println(xCoordinate[1]);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-40, 6*interval, 40);  //�����ͷ
			graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 10*interval);   //������ַ���ʾ�Լ����
			Thread.sleep(2*speed);
			if(flag) {
				i--;
				if(i==judge) {     //high--����low�պ��غϣ���high�������棬low������
					graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 10*interval);   //�������low
					graphics.setFont(new Font("����",Font.BOLD,15));
					graphics.drawString("high", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);  //дhigh
					graphics.drawString("low", xCoordinate[i]+2*interval, yCoordinate[i]+10*interval);  //дlow
				}
				else {
					drawArrow(xCoordinate[i]+3*interval, yCoordinate[i]-40, xCoordinate[i]+3*interval, yCoordinate[i]);
					graphics.setFont(new Font("����",Font.BOLD,15));
					graphics.drawString("high", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);
				}
			}
			else {
				i++;
				if(i==judge) {    //low++����high�պ��غ�
					graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 10*interval);   //�������low
					graphics.setFont(new Font("����",Font.BOLD,15));
					graphics.drawString("high", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);  //дhigh
					graphics.drawString("low", xCoordinate[i]+2*interval, yCoordinate[i]+10*interval);  //дlow
				}
				else {
					drawArrow(xCoordinate[i]+3*interval, yCoordinate[i]-40, xCoordinate[i]+3*interval, yCoordinate[i]);
					graphics.setFont(new Font("����",Font.BOLD,15));
					graphics.drawString("low", xCoordinate[i]+2*interval, yCoordinate[i]+8*interval);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void quickSortReplacement(int array[],int low,int high,boolean flag,int speed) {    //trueʱarray[low] = array[high]; 
		try {
			Thread.sleep(speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.blue);
			if(flag) {
				graphics.fillOval(xCoordinate[high], yCoordinate[high]-15*interval, 6*interval, 6*interval);  //high����
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
				graphics.fillOval(xCoordinate[low], yCoordinate[low]-15*interval, 6*interval, 6*interval);  //low����
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
	
	public void quickSortClean(int i,int speed) {       //����غϵ�low��high
		try {
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 18*interval);  //������·���������
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-40, 6*interval, 40);    //���������ļ�ͷ
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
				graphics.setFont(new Font("����",Font.BOLD,15));
				graphics.drawString("high", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
			}
			else {
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("����",Font.BOLD,15));
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
	
	//�۰��������
	public void binsertSortMovement(int low,int high,String str,int speed) {    //low=mid+1�Լ�high=mid-1
		try {
			//System.out.println(low+"  "+high);
			Thread.sleep(2*speed);
			graphics=gui.panel1.getGraphics();
			super.paintComponents(graphics);
			graphics.setColor(Color.orange);
			int mid=(low+high)/2;
			//graphics.clearRect(xCoordinate[mid], yCoordinate[mid]-40, 6*interval, 40);   //����mid��ͷ
			//graphics.clearRect(xCoordinate[mid], yCoordinate[mid]+6*interval, 6*interval, 10*interval);   //���mid�ַ�
			if(str.equals("low")) {             //low=mid+1,��Ҫ�ж�low��high�Ƿ��غ��Լ�low+1==high�������
				if(low==high) {//��ʱ������ͷ�غ�,Ҫ�����low
					graphics.clearRect(xCoordinate[mid], yCoordinate[mid]+8*interval, 6*interval, 10*interval);      //���low�ַ�
					graphics.drawString("high", xCoordinate[mid], yCoordinate[mid]+8*interval);
					graphics.drawString("mid", xCoordinate[mid], yCoordinate[mid]+10*interval);
					drawArrow(xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]-40, xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]);
					graphics.drawString("low", xCoordinate[mid+1], yCoordinate[mid+1]+8*interval);
				}
				if(low+1==high) {
					graphics.clearRect(xCoordinate[low], yCoordinate[low]-40, 6*interval, 40);//���low��ͷ
					graphics.clearRect(xCoordinate[low], yCoordinate[low]+8*interval, 6*interval,10*interval);//���low��mid�ַ�
					graphics.drawString("low", xCoordinate[mid+1], yCoordinate[mid+1]+10*interval);
					graphics.drawString("mid", xCoordinate[mid+1], yCoordinate[mid+1]+12*interval);
				}
				else {    //low=mid+1,��������
					graphics.clearRect(xCoordinate[low], yCoordinate[low]-40, 6*interval, 40);   //�����low��ͷ
					graphics.clearRect(xCoordinate[low], yCoordinate[low]+6*interval, 6*interval, 10*interval);   //����ַ�
					drawArrow(xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]-40, xCoordinate[mid+1]+3*interval, yCoordinate[mid+1]);
					graphics.setFont(new Font("����",Font.BOLD,15));
					graphics.drawString(str, xCoordinate[mid+1]+2*interval, yCoordinate[mid+1]+8*interval);
					int mid1=(mid-1+low)/2;
					drawArrow(xCoordinate[mid1]+3*interval, yCoordinate[mid1]-40, xCoordinate[mid1]+3*interval, yCoordinate[mid1]);
					graphics.drawString("mid", xCoordinate[mid1]+2*interval, yCoordinate[mid1]+8*interval);
				}
			}
			else {     //high=mid-1����Ҫ�ж�mid�Ƿ�Ϊ0
				if(mid!=0) {
					graphics.clearRect(xCoordinate[high], yCoordinate[high]-40, 6*interval, 40);   //�����high��ͷ
					graphics.clearRect(xCoordinate[high], yCoordinate[high]+6*interval, 6*interval, 10*interval);   //����ַ�
					drawArrow(xCoordinate[mid-1]+3*interval, yCoordinate[mid-1]-40, xCoordinate[mid-1]+3*interval, yCoordinate[mid-1]);
					graphics.setFont(new Font("����",Font.BOLD,15));
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
		getLocation(gui.panel1,location);//��ȡ����λ��
		int standard=location[1]+location[3]/2;
		interval=location[2]/(7*array.length+8);    //������������һ��λ�ã�Ӧ��high=-1�����
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
		//��������ͷ�Լ�low,high,mid��ʾ�������ж�low��high�Ƿ����
		int mid=(low+high)/2;
		if(low==high) {      //low==high�����غ�
			drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
			graphics.setFont(new Font("����",Font.BOLD,15));
			graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			graphics.drawString("low", xCoordinate[high]+2*interval, yCoordinate[high]+10*interval);
			graphics.drawString("mid", xCoordinate[high]+2*interval, yCoordinate[high]+12*interval);
		}
		else if(low+1==high){    //low��mid�غ�
			drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
			drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
			//drawArrow(xCoordinate[mid]+3*interval, yCoordinate[mid]-40, xCoordinate[mid]+3*interval, yCoordinate[mid]);
			
			graphics.setFont(new Font("����",Font.BOLD,15));
			graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
			graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
		}
		else {
			drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
			drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
			drawArrow(xCoordinate[mid]+3*interval, yCoordinate[mid]-40, xCoordinate[mid]+3*interval, yCoordinate[mid]);
			
			graphics.setFont(new Font("����",Font.BOLD,15));
			graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
			graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
			graphics.drawString("mid", xCoordinate[mid]+2*interval, yCoordinate[mid]+8*interval);
		}
	}
	
	public void binsertClean(int array[]) {       //���һ����ʾ��
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		for(int i=0;i<array.length;i++) {
			graphics.clearRect(xCoordinate[i], yCoordinate[i]-40, 6*interval, 40);
			graphics.clearRect(xCoordinate[i], yCoordinate[i]+6*interval, 6*interval, 7*interval);
		}
		graphics.clearRect(xCoordinate[0]-4*interval, yCoordinate[0]-40, 6*interval, 40);
		graphics.clearRect(xCoordinate[0]-4*interval, yCoordinate[0]+6*interval, 6*interval, 7*interval);
	}
	
	public void drawArrowAgain(int low,int high,int array[],int speed) {    //high==-1�������������
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		int mid=(low+high)/2;
		try {
			Thread.sleep(2*speed);
			if(low==high) {         //low==high==mid
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				graphics.setFont(new Font("����",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+10*interval);
				graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+12*interval);
				Thread.sleep(2*speed);
			}
			else if(low+1==high) {    //low==mid
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("����",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
				graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
				Thread.sleep(2*speed);
			}
			else if(high+1==low&&high!=-1) {   //high==mid  3 2 2
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("����",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[high]+2*interval, yCoordinate[high]+8*interval);
				graphics.drawString("mid", xCoordinate[high]+2*interval, yCoordinate[low]+10*interval);
				Thread.sleep(3*speed);
			}
			else if(high==-1) {   //0 -1 0
				binsertClean(array);  //�������
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[0]-3*interval, yCoordinate[0]-40, xCoordinate[0]-3*interval, yCoordinate[0]);//high
				graphics.setFont(new Font("����",Font.BOLD,15));
				graphics.drawString("low", xCoordinate[low]+2*interval, yCoordinate[low]+8*interval);
				graphics.drawString("high", xCoordinate[0]-4*interval, yCoordinate[0]+8*interval);
				graphics.drawString("mid", xCoordinate[low]+2*interval, yCoordinate[low]+10*interval);
				Thread.sleep(2*speed);
			}
			else {   //���߻������
				binsertClean(array);
				drawArrow(xCoordinate[low]+3*interval, yCoordinate[low]-40, xCoordinate[low]+3*interval, yCoordinate[low]);
				drawArrow(xCoordinate[mid]+3*interval, yCoordinate[mid]-40, xCoordinate[mid]+3*interval, yCoordinate[mid]);
				drawArrow(xCoordinate[high]+3*interval, yCoordinate[high]-40, xCoordinate[high]+3*interval, yCoordinate[high]);
				graphics.setFont(new Font("����",Font.BOLD,15));
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
			graphics.clearRect(xCoordinate[j], yCoordinate[j], 6*interval, 6*interval);   //�������array[j]
			Thread.sleep(speed);
			graphics.setColor(Color.blue);
			graphics.fillOval(xCoordinate[j], yCoordinate[j]-10*interval, 6*interval, 6*interval);  //array[j]����
			graphics.setColor(Color.red);
			graphics.drawString(String.valueOf(array[j]), numberX[j], numberY[j]-10*interval);
			Thread.sleep(speed);
			graphics.clearRect(xCoordinate[j], yCoordinate[j]-10*interval, 6*interval, 6*interval);//��������Ƶ�array[j]
			Thread.sleep(speed);
			graphics.setColor(Color.blue);    //array[j]���Ƶ�ͬʱ��������������
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
			Thread.sleep(3*speed);    //��ɲ����˯��1.5s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//������
	public void heapSortInit(int []array,int speed) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//��ȡ����λ��
		int standard=location[1]+location[3];
		interval=location[2]/(7*array.length+1);    //������
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
		//��ʼ�ȴ��ʼ����
		try {
			xCoordinateTree=new int[array.length];    //�洢��������λ�õ�����
			yCoordinateTree=new int[array.length];
			numberXTree=new int[array.length];
			numberYTree=new int[array.length];
			Thread.sleep(2*speed);
			int level=(int)Math.floor(Math.log((double)array.length)/Math.log(2.0))+1;    //�������Ĳ���
			//��Ҫ����ÿһ��ļ����ÿһ��ļ���ǲ�һ����
			int intervalPerLevel[]=new int[level];
			intervalPerLevel[0]=0;
			//System.out.println(level);
			double mid=Math.pow(2,level-1)*3;
			double mid1=(Math.pow(2,level-1)-1)*6;
			xInterval=(int)(location[2]-40)/(int)(mid+mid1);
			int yInterval=6*xInterval;
			intervalPerLevel[level-1]=6*xInterval;    //��������һ�������ϲ������ε���
			for(int i=level-2;i>=0;i--) {
				intervalPerLevel[i]=(int)(1.5*intervalPerLevel[i+1]);
			}
			int midStandard=location[0]+location[2]/2;    //����λ�õ�X����
			
			//��ȷ��ÿ��������λ��
			xCoordinateTree[0]=midStandard;yCoordinateTree[0]=location[1]+3*xInterval;
			for(int i=0;i<array.length;i++) {
				treeCoordinate(i, xInterval,array.length,intervalPerLevel,yInterval);   //����������������
			}
			for(int i=0;i<array.length;i++) {
				numberXTree[i]=xCoordinateTree[i]+(int)1.5*xInterval;
				numberYTree[i]=yCoordinateTree[i]+(int)2*xInterval;
			}
			for(int i=0;i<array.length;i++) {     //�����
				graphics.clearRect(xCoordinate[i], yCoordinate[i], 6*interval, 6*interval);   //����·�СԲ��
				Thread.sleep(2*speed);
				graphics.setColor(Color.orange);
				graphics.fillOval(xCoordinateTree[i], yCoordinateTree[i], 3*xInterval, 3*xInterval);
				graphics.setColor(Color.black);
				graphics.drawString(String.valueOf(array[i]), numberXTree[i], numberYTree[i]);
			}
			//����
			for(int i=0;i<array.length;i++) {
				if(2*i+1<array.length&&2*i+2<array.length) {    //�������Ӿ�����
					drawBranch1(xCoordinateTree[i], yCoordinateTree[i], xCoordinateTree[2*i+1], yCoordinateTree[2*i+1],xInterval);
					drawBranch2(xCoordinateTree[i], yCoordinateTree[i], xCoordinateTree[2*i+2], yCoordinateTree[2*i+2],xInterval);
				}
				if(2*i+2==array.length) {    //ֻ��������
					drawBranch1(xCoordinateTree[i], yCoordinateTree[i], xCoordinateTree[2*i+1], yCoordinateTree[2*i+1],xInterval);
				}
				if(2*i+1==array.length) {   //���Һ��Ӿ�������
					
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void treeCoordinate(int i,int xInterval,int length,int intervalPerLevel[],int yInterval) {
		//�ȼ����������ڲ���
		int nodeLevel=(int)Math.floor(Math.log((double)(i+1))/Math.log(2.0))+1;   //��i��������ڵĲ��
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
	
	public void drawBranch1(int x1,int y1,int x2,int y2,int xInterval) {   //������
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.red);
		Graphics2D g = (Graphics2D)graphics;  //g��Graphics����
		g.setStroke(new BasicStroke(3.0f));
		graphics.drawLine(x1, y1+3*xInterval, x2+3*xInterval, y2);
	}
	
	public void drawBranch2(int x1,int y1,int x2,int y2,int xInterval) {   //���Һ���
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.red);
		Graphics2D g = (Graphics2D)graphics;  //g��Graphics����
		g.setStroke(new BasicStroke(3.0f));
		graphics.drawLine(x1+3*xInterval, y1+3*xInterval, x2, y2);
	}
	
	public void heapSortExchange(int i,int j,int array[],int speed) {    //��ʼ����ʱ�����������
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
	
	//�鲢����
	public void mergeSortInit(int array[]) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//��ȡ����λ��
		int standard=location[1];
		interval=location[2]/(7*array.length+1);    //������
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
	
	//��������
	public void radixSortInit(int array[]) {
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		graphics.setColor(Color.orange);
		getLocation(gui.panel1,location);//��ȡ����λ��
		int standard=location[1];
		interval=location[2]/(7*array.length+1);    //������
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
		//���·��������������
		graphics.setColor(Color.black);
		int yStandard=location[1]+location[3]-6*interval;
		for(int i=0;i<radix.length;i++) {
			radix[i]=0;             //�洢ÿ���������Ͻ��յ������ָ���
		}
		for(int i=0;i<10;i++) {
			graphics.drawLine(xCoordinate[i],yStandard, xCoordinate[i]+6*interval, yStandard);
			graphics.drawString(String.valueOf(i), xCoordinate[i]+3*interval, yStandard+3*interval);
		}
	}
	
	public void buckeListAdd(int num,int j,int array[],int speed) {   //bucketList.get(num).add(array[j])
		graphics=gui.panel1.getGraphics();
		super.paintComponents(graphics);
		int yStandard=location[1]+location[3]-15*interval;   //�·���һ��С���ε�����
		try {
			Thread.sleep(speed);
			radix[num]++;
			graphics.clearRect(xCoordinate[j], yCoordinate[j], 6*interval, 6*interval); //��������Ϸ������������
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
		int yStandard=location[1]+location[3]-15*interval;   //�·���һ��С���ε�����,������������y�������8*interval
		int yNumberk=yStandard-k*7*interval;   //��k��С���ε�����
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
 