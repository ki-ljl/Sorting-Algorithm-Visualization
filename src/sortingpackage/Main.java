package sortingpackage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import java.util.Arrays;


/** 
 * @ClassName: Main
 * @description: 主界面类
 * @author: 李俊良
 * @Date: 2019年12月20日 上午11:16:19
 */
class MainGUI{
	JFrame frame;
	JPanel panel1,panel2,panel3,panel4;
	JLabel speedLabel;
	JList<String> listCode;
	JMenuBar menuBar;
	JScrollPane pane;
	String str;
	DefaultListModel<String> model;
	JButton startButton,deceButton,acceButton,buttonStep,outLoopButton;
	JMenu insertMenu,selectMenu,exchangeMenu,mergeMenu,radixMenu;
	JMenuItem []items=new JMenuItem[9];
	String []data={"直接插入排序","折半插入排序","希尔排序","简单选择排序","堆排序","冒泡排序","快速排序","归并排序","基数排序"};
	public MainGUI() {
		frame=new JFrame();
		panel1=new JPanel();
		panel2=new JPanel();
		panel2.setLayout(new GridLayout(1, 3));
		str= "代码区                                                                                                                                             ";
		model=new DefaultListModel<String>();
		listCode=new JList<String>(model);
		model.add(0, str);
		speedLabel=new JLabel("当前速度为1000",JLabel.CENTER);
		panel3=new JPanel();
		panel3.setLayout(new BorderLayout());
		//panel1.setBorder(BorderFactory.createLoweredBevelBorder());
		Border border1=BorderFactory.createLoweredBevelBorder();
		panel1.setBorder(BorderFactory.createTitledBorder(border1,"动画演示区"));
		panel2.setBorder(BorderFactory.createTitledBorder(border1,"功能区"));
		listCode.setBackground(Color.darkGray);
		listCode.setForeground(Color.orange);
		pane=new JScrollPane(listCode);
		panel3.add(pane);
		Border border=BorderFactory.createEtchedBorder();
		panel3.setBorder(border);
		ImageIcon startIcon=new ImageIcon("start.jpg");
		ImageIcon stopIcon=new ImageIcon("stop.jpg");
		ImageIcon continueIcon=new ImageIcon("continue.jpg");
		startButton=new JButton("完整演示");
		startButton.setFont(new Font("宋体",Font.BOLD,25));
		startButton.setBackground(Color.LIGHT_GRAY);
		startButton.setPreferredSize(new Dimension(stopIcon.getIconWidth(), stopIcon.getIconHeight()));
		acceButton=new JButton("加速");
		acceButton.setBackground(Color.LIGHT_GRAY);
		acceButton.setFont(new Font("宋体",Font.BOLD,25));
		//acceButton.setForeground(Color.YELLOW);
		deceButton=new JButton("减速");
		deceButton.setBackground(Color.LIGHT_GRAY);
		deceButton.setFont(new Font("宋体",Font.BOLD,25));
		buttonStep=new JButton("单步执行");
		buttonStep.setBackground(Color.white);
		buttonStep.setForeground(Color.RED);
		buttonStep.setFont(new Font("宋体",Font.BOLD,25));
		outLoopButton=new JButton("跳出循环");
		startButton.setBorder(BorderFactory.createLoweredBevelBorder());
		acceButton.setBorder(BorderFactory.createLoweredBevelBorder());
		deceButton.setBorder(BorderFactory.createLoweredBevelBorder());
		buttonStep.setBorder(BorderFactory.createLoweredBevelBorder());
		speedLabel.setFont(new Font("宋体",Font.BOLD,25));
		speedLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		panel4=new JPanel();
		panel4.setLayout(new GridLayout(1,2));
		panel4.add(buttonStep);//panel4.add(outLoopButton);
		panel4.add(speedLabel);
		panel3.add(BorderLayout.SOUTH,panel4);
		panel2.add(startButton);//panel2.add(pauseButton);panel2.add(continueButton);
		panel2.add(acceButton);panel2.add(deceButton);
		menuBar=new JMenuBar();
		insertMenu=new JMenu("插入");
		selectMenu=new JMenu("选择");
		exchangeMenu=new JMenu("交换");
		mergeMenu=new JMenu("归并");
		radixMenu=new JMenu("基数");
		for(int i=0;i<items.length;i++) {
			items[i]=new JMenuItem(data[i]);
		}
		insertMenu.add(items[0]);insertMenu.addSeparator();insertMenu.add(items[1]);insertMenu.addSeparator();
		insertMenu.add(items[2]);
		selectMenu.add(items[3]);selectMenu.addSeparator();selectMenu.add(items[4]);
		exchangeMenu.add(items[5]);exchangeMenu.addSeparator();exchangeMenu.add(items[6]);
		mergeMenu.add(items[7]);
		radixMenu.add(items[8]);
		menuBar.add(insertMenu);
		menuBar.add(selectMenu);
		menuBar.add(exchangeMenu);
		menuBar.add(mergeMenu);
		menuBar.add(radixMenu);
		frame.setJMenuBar(menuBar);
		frame.add(BorderLayout.CENTER,panel1);
		frame.add(BorderLayout.SOUTH,panel2);
		frame.add(BorderLayout.EAST,panel3);
		for(int i=0;i<9;i++) {
			items[i].addActionListener(new EventAction(this));
		}
		startButton.addActionListener(new EventAction(this));
		acceButton.addActionListener(new EventAction(this));
		deceButton.addActionListener(new EventAction(this));
		buttonStep.addActionListener(new EventAction(this));
		frame.setSize(1200, 800);
		//frame.setSize(800, 600);
		frame.setTitle("DynamicSorting");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
}
class EventAction implements ActionListener{
	MainGUI gui;
	int [][]codeNum=new int[9][];
	int pivot=0,low=0,high=0,temp=0;
	static String flag="";     //判断执行哪一个操作函数
	static int speed=1000,card=0,card1=0;
	static int index=1,index1=0,current=0;
	static int width=1,flagBinser=1;
	static int flagStep=0,flagBubble=0,flagSelect=0;
	static int flagDirec=1,flagDirect=1,gap=0;
	static boolean flagIndex=true;
	static boolean []cleanFlag= new boolean[9];
	Graphics graphics;
	//int []x= {41,100,63,12,4,45,8,55,11,68,93};
	int []x= {70,73,70,23,93,18,11,68};
	int []radix= {70,83,45,78,37,16,22,57,11,99};
	static int flagHeap=0;
	//int []x= {1,3,4,5,2};
	PaintRec paintRec;
	SortingFunction function;
	public EventAction(MainGUI gui) {
		this.gui=gui;
		paintRec=new PaintRec(gui);  //创建画图对象
		function=new SortingFunction(gui);
		codeNum[0]=new int[20];codeNum[1]=new int[20];codeNum[2]=new int[20];codeNum[3]=new int[20];codeNum[4]=new int[13];
		codeNum[5]=new int[20];codeNum[6]=new int[20];codeNum[7]=new int[20];codeNum[8]=new int[20];
		for(int i=0;i<codeNum.length;i++) {
			for(int j=0;j<codeNum[i].length;j++) {
				codeNum[i][j]=j+1;
			}
		}
		for(int i=0;i<cleanFlag.length;i++) {
			cleanFlag[i]=true;
		}
		graphics=gui.panel1.getGraphics();
	}
	public void recover() {    //每次排序之后都要复原，方便进行下一次排序
		int []x= {70,73,70,23,93,18,11,68};
		int []radix= {70,83,45,78,37,16,22,57,11,99};
		this.x=Arrays.copyOfRange(x, 0, x.length);
		this.radix=Arrays.copyOfRange(radix, 0, radix.length);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==gui.acceButton) {     //设置加速,且只能在一开始就设置加速
			if(speed>=100) {
				speed-=100;
				PaintRec.speed=EventAction.speed;  //设置速度
				gui.speedLabel.setText("当前速度为"+String.valueOf(2000-PaintRec.speed));
				//JOptionPane.showMessageDialog(gui.frame, "当前速度为"+String.valueOf(2000-PaintRec.speed), "提示", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(gui.frame, "已达到最快速度!!", "提示", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
		if(e.getSource()==gui.deceButton) {
			if(2000-speed>=200) {
				speed+=100;
				PaintRec.speed=EventAction.speed;
				gui.speedLabel.setText("当前速度为"+String.valueOf(2000-PaintRec.speed));
			}
			else {
				JOptionPane.showMessageDialog(gui.frame, "已是最慢速度!!", "提示", JOptionPane.INFORMATION_MESSAGE);	
			}
		}
		if(e.getActionCommand()=="冒泡排序") {
			try {
				File file=new File("冒泡排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="冒泡排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"冒泡排序"));
		}
		if(e.getActionCommand()=="快速排序") {
			try {
				File file=new File("快速排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="快速排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"快速排序"));
		}
		if(e.getActionCommand()=="直接插入排序") {
			try {
				File file=new File("直接插入排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="直接插入排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"直接插入排序"));
		}
		if(e.getActionCommand()=="折半插入排序") {
			try {
				File file=new File("折半插入排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="折半插入排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"折半插入排序"));
		}
		if(e.getActionCommand()=="希尔排序") {
			try {
				File file=new File("希尔排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="希尔排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"希尔排序"));
		}
		if(e.getActionCommand()=="简单选择排序") {
			try {
				File file=new File("简单选择排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="简单选择排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"简单选择排序"));
		}
		if(e.getActionCommand()=="堆排序") {
			try {
				File file=new File("堆排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flagHeap=x.length-1;   //后续单步演示
			flag="堆排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"堆排序"));
		}
		if(e.getActionCommand()=="归并排序") {
			try {
				File file=new File("归并排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="归并排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"归并排序"));
		}
		if(e.getActionCommand()=="基数排序") {
			try {
				File file=new File("基数排序.txt");
				String str;
				BufferedReader reader=new BufferedReader(new FileReader(file));
				gui.model=new DefaultListModel<String>();
				gui.model.add(0, gui.str);
				int index=1;
				while((str=reader.readLine())!=null) {
					gui.model.add(index, str);
					index++;
				}
				reader.close();
				gui.listCode.setModel(gui.model);
				gui.listCode.setSelectedIndex(0);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			flag="基数排序";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"基数排序"));
		}  
		//完整过程演示
		if(e.getSource()==gui.startButton&&flag.equals("冒泡排序")) {
			function.bubbleSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("直接插入排序")) {
			function.insertSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("简单选择排序")) {
			function.selectionSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("希尔排序")) {
			function.shellSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("快速排序")) {
			function.QuickSort(x, 0, x.length-1,true,true);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("折半插入排序")) {
			function.binsertSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("堆排序")) {
			function.heapSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("归并排序")) {
			function.mergeSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("基数排序")) {
			function.radixSort(radix);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.length()<2||e.getSource()==gui.buttonStep&&flag.length()<2) {
			JOptionPane.showMessageDialog(gui.frame, "请先在菜单中选择一种排序方式！", "提示", JOptionPane.INFORMATION_MESSAGE);	
		}
		if(e.getSource()==gui.buttonStep&&flag.length()>=2&&flag.substring(flag.length()-2).equals("排序")) {
			gui.listCode.setSelectedIndex(index);
			if(flag.equals("堆排序")) {
				if(cleanFlag[0]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[0]=false;
				}
				if(index==5) {
					function.heapSortInit(x);
				}
				if(flagHeap>0) {
					if(index==11) {
						function.heapSortStepsOut(x,flagHeap);
						flagHeap--;
						index=6;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(index==6&&flagHeap==0) {
					index=12;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(index==13) {
					Graphics graphics=gui.panel1.getGraphics();
					graphics.clearRect(150, 450, 450 , 80);
					graphics.setFont(new Font("宋体",Font.BOLD,25));
					graphics.setColor(Color.black);
					graphics.drawString("调整完毕，最终序列即树结点逆序序列:", 120, 500);
					function.heapSortLast(x);
				}
				if(index==13) {
					index=0;
					recover();
				}
			}
			if(flag.equals("归并排序")) {
				if(cleanFlag[1]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[1]=false;
				}
				if(flagDirec==x.length-1) {
					index=12;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(index==7) {
					if(width<x.length) {
						function.mergeSortSingleStep(width, x);
						width*=2;
						index=5;
						flagIndex=false;
						if(width<x.length) {
							gui.listCode.setSelectedIndex(index);	
						}
						else {
							index=8;
							gui.listCode.setSelectedIndex(index);
							flagIndex=false;
						}
					}
					else {
						index=10;
						flagIndex=false;
						gui.listCode.setSelectedIndex(index);
					}
				}
				if(index==9) {
					index=0;
					recover();
				}
			}
			if(flag.equals("基数排序")) {
				if(cleanFlag[2]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[2]=false;
				}
				if(index==16) {
					function.radixSortInit(radix);   //初始化
				}
				if(index==22&&flagStep==0) {
					function.radixSortCore1(radix, 10, 1);   //第一次分配
					flagStep++;
				}
				if(index==27&&flagStep==1) {   //第一次收集
					function.radixSortCore2(radix, function.bucketList1);
					flagStep++;
				}
				if(index==29&&flagStep==2) {   //回到循环初始位置
					index=18;
					flagIndex=false;
					gui.listCode.setSelectedIndex(index);
				}
				if(index==22&&flagStep==2) {
					function.radixSortCore1(radix, 100, 10);   //第二次分配
					flagStep++;
				}
				if(index==27&&flagStep==3) {    //第二次收集
					function.radixSortCore2(radix, function.bucketList1);
				}
				if(index==31) {
					Graphics graphics=gui.panel1.getGraphics();
				    graphics.setColor(Color.black);
				    graphics.setFont(new Font("宋体",Font.BOLD,25));
				    graphics.drawString("RaidxSorting Completed!!", 190, 500);
				}
				if(index==32) {
					index=0;
					recover();
				}
			}
			if(flag.equals("直接插入排序")) {    //OK
				if(cleanFlag[3]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[3]=false;
				}
				if(card==0&&flagDirec<x.length) {
					index1=flagDirec;
					current=x[flagDirec];
					card++;
				}
				if(index==3) {
					function.paintRec.directInsertInit(x);
				}
				if(index==3&&flagDirec==x.length) {
					index=12;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(flagDirec<x.length) {
					if(index==4) {
						function.paintRec.directInsertDown(x, flagDirec,PaintRec.speed);   //第待排序长方形块下落
					}
					if(index==8) {    //寻找插入位置
						while(index1-1 >= 0 && current < x[index1-1]){
							function.paintRec.directInsertLeft(x, index1,current,PaintRec.speed);
							x[index1] = x[index1-1];    //小矩形右移
							index1--;    //这里的数怎么能够保存下来呢
						}
					}
					if(index==10) {
						function.paintRec.directInsertUp(x, index1,current,PaintRec.speed);   //找到插入位置
						x[index1]=current;
						flagDirec++;   //i++
						card=0;   //进行下一轮循环
						index=3;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(index==13) {
					Graphics graphics=gui.panel1.getGraphics();
				    graphics.setColor(Color.black);
				    graphics.setFont(new Font("宋体",Font.BOLD,25));
				    graphics.drawString("Completed!!", 270, 500);
				}
				if(index==14) {
					index=0;
					recover();
				}
			}
			if(flag.equals("冒泡排序")) {
				if(cleanFlag[4]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[4]=false;
				}
				if(index==3) {
					function.initBubble(x);  //初始化
					if(flagBubble==x.length) {   //跳出循环
						index=12;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(flagBubble<x.length) {
					if(index==7) {
						function.bubbleSortExchange(flagBubble, x);
						flagBubble++;
						index=3;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(index==14) {
					index=0;
					recover();
				}
			}
			if(flag.equals("快速排序")) {
				if(cleanFlag[5]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[5]=false;
				}
				if(index==2) {
					function.quickInit(x);
				}
				if(index==4) {
					pivot=function.partition(x, 0, x.length-1);//得到第一轮排序的基准所落位置
				}
				if(index==6) {
					function.paintRec.quickSortClean(pivot,PaintRec.speed);  //清除重合的两个箭头
					function.paintRec.quickSortInit(x, 0, pivot-1);
					function.quickLeft(x, 0, pivot-1);
				}
				if(index==8) {
					function.paintRec.quickSortClean(0,PaintRec.speed);  //清除重合的两个箭头
					function.paintRec.quickSortInit(x, pivot+1, x.length-1);
					function.quickRight(x, pivot+1, x.length-1);
				}
				if(index==9) {
					index=0;
					recover();
				}
			}
			if(flag.equals("简单选择排序")) {
				if(cleanFlag[6]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[6]=false;
				}
				if(index==2) {
					function.paintRec.bubbleSortInit(x);   //初始化
				}
				if(index==3&&flagSelect==x.length) {
					index=17;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(index==12) {
					if(flagSelect<x.length) {
						int minIndex = flagSelect;
						for(int j = flagSelect;j<x.length;j++){//遍历未剩余未排序元素中继续寻找最小元素
							if(x[j] < x[minIndex]){
								minIndex = j;
							}
						}
						if(minIndex != flagSelect){
							function.paintRec.bubbleSortExchange(flagSelect, minIndex, x.length, x,PaintRec.speed);
							int temp = x[minIndex];
							x[minIndex] = x[flagSelect];
							x[flagSelect] = temp;
						}
						flagSelect++;
						index=3;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(index==18) {
					index=0;
					recover();
				}
			}
			if(flag.equals("希尔排序")) {
				if(cleanFlag[7]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[7]=false;
				}
				if(index==2) {    //初始化
					function.paintRec.shellSortInit(x);
				}
				if(index==4) {
					gap=x.length/2;
					function.paintRec.drawShellGap(gap,PaintRec.speed);
				}
				if(index==5&&gap==0) {
					index=18;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(gap>0) {
					if(index==12) {
						for(int i = gap;i < x.length;i++) {
			           		int temp = x[i];    //待插入结点上移
			           		function.paintRec.shellUp(x, i,PaintRec.speed);
			                int index = i - gap;
			                while(index >= 0 && x[index] > temp) {
			                	function.paintRec.shellLeft(x, index+gap, temp, gap,PaintRec.speed);
			                   	x[index + gap] = x[index];      //后移一个元素
			                    index -= gap;
			                }
			                function.paintRec.shellDown(index+gap, x, temp,PaintRec.speed);
			                x[index + gap] = temp;     //待插入元素下落
			           	}
					}
					if(index==15) {
						gap/=2;
						function.paintRec.drawShellGap(gap,PaintRec.speed);
						index=5;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(gap<0) {
					index=17;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(index==19) {
					index=0;
					recover();
				}
			}
			if(flag.equals("折半插入排序")) {
				if(cleanFlag[8]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[8]=false;
				}
				if(card1==0&&flagBinser<x.length) {
					low=0;
					high=flagBinser-1;
					temp=x[flagBinser];
					card1++;
				}
				if(index==1) {
					Graphics graphics=gui.panel1.getGraphics();
				    graphics.setColor(Color.black);
				    graphics.setFont(new Font("宋体",Font.BOLD,25));
				    graphics.drawString("插入位置为high+1!!", 200, 500);
				    paintRec.binsertSortInit(x, 0, 0);     //初始状态下画图
				}
				if(index==2&&flagBinser==x.length) {   //跳出循环
					index=20;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(flagBinser<x.length) {
					if(index==5) {
						paintRec.drawArrowAgain(low, high, x,PaintRec.speed);
						paintRec.binsertSortUp(flagBinser, x,PaintRec.speed);
					}
					if(index==8) {    //找到插入位置
						while(low<=high) {       //
							int mid = (high+low)/2;
							if(x[mid]<temp) {
							   //不移动直接全部删掉重新设置箭头避免麻烦
								paintRec.drawArrowAgain(mid+1, high, x,PaintRec.speed);
								low = mid+1;
							}
							else {
								paintRec.drawArrowAgain(low, mid-1, x,PaintRec.speed);
								high = mid-1;
							}
						}
						index=14;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
					if(index==17) {
						for (int j = flagBinser-1; j > high; --j) {
							paintRec.binsertSortReplacement(x, j,PaintRec.speed);
							x[j+1] = x[j];           //array[j]上升然后右移到array[j+1]上方，此时array[j+1]同步消失，然后上方圆形数据下落
						}
					}
					if(index==19) {
						paintRec.binsertSortLastSteps(high, temp,flagBinser,PaintRec.speed);
						x[high+1] = temp;
						flagBinser++;
						card1=0;   //新的一轮排序过程
						index=2;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(index==21) {
					paintRec.binsertClean(x);
					index=0;
					recover();
				}
			}
			if(flagIndex) {    
				index++;
			}
			flagIndex=true;
		}
	}
	
}
public class Main {

	public static void main(String[] args) {
		new MainGUI();
	}

}
