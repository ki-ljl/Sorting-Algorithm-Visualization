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
 * @description: ��������
 * @author: ���
 * @Date: 2019��12��20�� ����11:16:19
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
	String []data={"ֱ�Ӳ�������","�۰��������","ϣ������","��ѡ������","������","ð������","��������","�鲢����","��������"};
	public MainGUI() {
		frame=new JFrame();
		panel1=new JPanel();
		panel2=new JPanel();
		panel2.setLayout(new GridLayout(1, 3));
		str= "������                                                                                                                                             ";
		model=new DefaultListModel<String>();
		listCode=new JList<String>(model);
		model.add(0, str);
		speedLabel=new JLabel("��ǰ�ٶ�Ϊ1000",JLabel.CENTER);
		panel3=new JPanel();
		panel3.setLayout(new BorderLayout());
		//panel1.setBorder(BorderFactory.createLoweredBevelBorder());
		Border border1=BorderFactory.createLoweredBevelBorder();
		panel1.setBorder(BorderFactory.createTitledBorder(border1,"������ʾ��"));
		panel2.setBorder(BorderFactory.createTitledBorder(border1,"������"));
		listCode.setBackground(Color.darkGray);
		listCode.setForeground(Color.orange);
		pane=new JScrollPane(listCode);
		panel3.add(pane);
		Border border=BorderFactory.createEtchedBorder();
		panel3.setBorder(border);
		ImageIcon startIcon=new ImageIcon("start.jpg");
		ImageIcon stopIcon=new ImageIcon("stop.jpg");
		ImageIcon continueIcon=new ImageIcon("continue.jpg");
		startButton=new JButton("������ʾ");
		startButton.setFont(new Font("����",Font.BOLD,25));
		startButton.setBackground(Color.LIGHT_GRAY);
		startButton.setPreferredSize(new Dimension(stopIcon.getIconWidth(), stopIcon.getIconHeight()));
		acceButton=new JButton("����");
		acceButton.setBackground(Color.LIGHT_GRAY);
		acceButton.setFont(new Font("����",Font.BOLD,25));
		//acceButton.setForeground(Color.YELLOW);
		deceButton=new JButton("����");
		deceButton.setBackground(Color.LIGHT_GRAY);
		deceButton.setFont(new Font("����",Font.BOLD,25));
		buttonStep=new JButton("����ִ��");
		buttonStep.setBackground(Color.white);
		buttonStep.setForeground(Color.RED);
		buttonStep.setFont(new Font("����",Font.BOLD,25));
		outLoopButton=new JButton("����ѭ��");
		startButton.setBorder(BorderFactory.createLoweredBevelBorder());
		acceButton.setBorder(BorderFactory.createLoweredBevelBorder());
		deceButton.setBorder(BorderFactory.createLoweredBevelBorder());
		buttonStep.setBorder(BorderFactory.createLoweredBevelBorder());
		speedLabel.setFont(new Font("����",Font.BOLD,25));
		speedLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		panel4=new JPanel();
		panel4.setLayout(new GridLayout(1,2));
		panel4.add(buttonStep);//panel4.add(outLoopButton);
		panel4.add(speedLabel);
		panel3.add(BorderLayout.SOUTH,panel4);
		panel2.add(startButton);//panel2.add(pauseButton);panel2.add(continueButton);
		panel2.add(acceButton);panel2.add(deceButton);
		menuBar=new JMenuBar();
		insertMenu=new JMenu("����");
		selectMenu=new JMenu("ѡ��");
		exchangeMenu=new JMenu("����");
		mergeMenu=new JMenu("�鲢");
		radixMenu=new JMenu("����");
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
	static String flag="";     //�ж�ִ����һ����������
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
		paintRec=new PaintRec(gui);  //������ͼ����
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
	public void recover() {    //ÿ������֮��Ҫ��ԭ�����������һ������
		int []x= {70,73,70,23,93,18,11,68};
		int []radix= {70,83,45,78,37,16,22,57,11,99};
		this.x=Arrays.copyOfRange(x, 0, x.length);
		this.radix=Arrays.copyOfRange(radix, 0, radix.length);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==gui.acceButton) {     //���ü���,��ֻ����һ��ʼ�����ü���
			if(speed>=100) {
				speed-=100;
				PaintRec.speed=EventAction.speed;  //�����ٶ�
				gui.speedLabel.setText("��ǰ�ٶ�Ϊ"+String.valueOf(2000-PaintRec.speed));
				//JOptionPane.showMessageDialog(gui.frame, "��ǰ�ٶ�Ϊ"+String.valueOf(2000-PaintRec.speed), "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(gui.frame, "�Ѵﵽ����ٶ�!!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
		if(e.getSource()==gui.deceButton) {
			if(2000-speed>=200) {
				speed+=100;
				PaintRec.speed=EventAction.speed;
				gui.speedLabel.setText("��ǰ�ٶ�Ϊ"+String.valueOf(2000-PaintRec.speed));
			}
			else {
				JOptionPane.showMessageDialog(gui.frame, "���������ٶ�!!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);	
			}
		}
		if(e.getActionCommand()=="ð������") {
			try {
				File file=new File("ð������.txt");
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
			flag="ð������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"ð������"));
		}
		if(e.getActionCommand()=="��������") {
			try {
				File file=new File("��������.txt");
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
			flag="��������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"��������"));
		}
		if(e.getActionCommand()=="ֱ�Ӳ�������") {
			try {
				File file=new File("ֱ�Ӳ�������.txt");
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
			flag="ֱ�Ӳ�������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"ֱ�Ӳ�������"));
		}
		if(e.getActionCommand()=="�۰��������") {
			try {
				File file=new File("�۰��������.txt");
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
			flag="�۰��������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"�۰��������"));
		}
		if(e.getActionCommand()=="ϣ������") {
			try {
				File file=new File("ϣ������.txt");
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
			flag="ϣ������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"ϣ������"));
		}
		if(e.getActionCommand()=="��ѡ������") {
			try {
				File file=new File("��ѡ������.txt");
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
			flag="��ѡ������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"��ѡ������"));
		}
		if(e.getActionCommand()=="������") {
			try {
				File file=new File("������.txt");
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
			flagHeap=x.length-1;   //����������ʾ
			flag="������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"������"));
		}
		if(e.getActionCommand()=="�鲢����") {
			try {
				File file=new File("�鲢����.txt");
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
			flag="�鲢����";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"�鲢����"));
		}
		if(e.getActionCommand()=="��������") {
			try {
				File file=new File("��������.txt");
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
			flag="��������";
			Border border=BorderFactory.createLoweredBevelBorder();
			gui.panel1.setBorder(BorderFactory.createTitledBorder(border,"��������"));
		}  
		//����������ʾ
		if(e.getSource()==gui.startButton&&flag.equals("ð������")) {
			function.bubbleSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("ֱ�Ӳ�������")) {
			function.insertSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("��ѡ������")) {
			function.selectionSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("ϣ������")) {
			function.shellSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("��������")) {
			function.QuickSort(x, 0, x.length-1,true,true);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("�۰��������")) {
			function.binsertSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("������")) {
			function.heapSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("�鲢����")) {
			function.mergeSort(x);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.equals("��������")) {
			function.radixSort(radix);
			recover();
		}
		if(e.getSource()==gui.startButton&&flag.length()<2||e.getSource()==gui.buttonStep&&flag.length()<2) {
			JOptionPane.showMessageDialog(gui.frame, "�����ڲ˵���ѡ��һ������ʽ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);	
		}
		if(e.getSource()==gui.buttonStep&&flag.length()>=2&&flag.substring(flag.length()-2).equals("����")) {
			gui.listCode.setSelectedIndex(index);
			if(flag.equals("������")) {
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
					graphics.setFont(new Font("����",Font.BOLD,25));
					graphics.setColor(Color.black);
					graphics.drawString("������ϣ��������м��������������:", 120, 500);
					function.heapSortLast(x);
				}
				if(index==13) {
					index=0;
					recover();
				}
			}
			if(flag.equals("�鲢����")) {
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
			if(flag.equals("��������")) {
				if(cleanFlag[2]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[2]=false;
				}
				if(index==16) {
					function.radixSortInit(radix);   //��ʼ��
				}
				if(index==22&&flagStep==0) {
					function.radixSortCore1(radix, 10, 1);   //��һ�η���
					flagStep++;
				}
				if(index==27&&flagStep==1) {   //��һ���ռ�
					function.radixSortCore2(radix, function.bucketList1);
					flagStep++;
				}
				if(index==29&&flagStep==2) {   //�ص�ѭ����ʼλ��
					index=18;
					flagIndex=false;
					gui.listCode.setSelectedIndex(index);
				}
				if(index==22&&flagStep==2) {
					function.radixSortCore1(radix, 100, 10);   //�ڶ��η���
					flagStep++;
				}
				if(index==27&&flagStep==3) {    //�ڶ����ռ�
					function.radixSortCore2(radix, function.bucketList1);
				}
				if(index==31) {
					Graphics graphics=gui.panel1.getGraphics();
				    graphics.setColor(Color.black);
				    graphics.setFont(new Font("����",Font.BOLD,25));
				    graphics.drawString("RaidxSorting Completed!!", 190, 500);
				}
				if(index==32) {
					index=0;
					recover();
				}
			}
			if(flag.equals("ֱ�Ӳ�������")) {    //OK
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
						function.paintRec.directInsertDown(x, flagDirec,PaintRec.speed);   //�ڴ����򳤷��ο�����
					}
					if(index==8) {    //Ѱ�Ҳ���λ��
						while(index1-1 >= 0 && current < x[index1-1]){
							function.paintRec.directInsertLeft(x, index1,current,PaintRec.speed);
							x[index1] = x[index1-1];    //С��������
							index1--;    //���������ô�ܹ�����������
						}
					}
					if(index==10) {
						function.paintRec.directInsertUp(x, index1,current,PaintRec.speed);   //�ҵ�����λ��
						x[index1]=current;
						flagDirec++;   //i++
						card=0;   //������һ��ѭ��
						index=3;
						gui.listCode.setSelectedIndex(index);
						flagIndex=false;
					}
				}
				if(index==13) {
					Graphics graphics=gui.panel1.getGraphics();
				    graphics.setColor(Color.black);
				    graphics.setFont(new Font("����",Font.BOLD,25));
				    graphics.drawString("Completed!!", 270, 500);
				}
				if(index==14) {
					index=0;
					recover();
				}
			}
			if(flag.equals("ð������")) {
				if(cleanFlag[4]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[4]=false;
				}
				if(index==3) {
					function.initBubble(x);  //��ʼ��
					if(flagBubble==x.length) {   //����ѭ��
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
			if(flag.equals("��������")) {
				if(cleanFlag[5]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[5]=false;
				}
				if(index==2) {
					function.quickInit(x);
				}
				if(index==4) {
					pivot=function.partition(x, 0, x.length-1);//�õ���һ������Ļ�׼����λ��
				}
				if(index==6) {
					function.paintRec.quickSortClean(pivot,PaintRec.speed);  //����غϵ�������ͷ
					function.paintRec.quickSortInit(x, 0, pivot-1);
					function.quickLeft(x, 0, pivot-1);
				}
				if(index==8) {
					function.paintRec.quickSortClean(0,PaintRec.speed);  //����غϵ�������ͷ
					function.paintRec.quickSortInit(x, pivot+1, x.length-1);
					function.quickRight(x, pivot+1, x.length-1);
				}
				if(index==9) {
					index=0;
					recover();
				}
			}
			if(flag.equals("��ѡ������")) {
				if(cleanFlag[6]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[6]=false;
				}
				if(index==2) {
					function.paintRec.bubbleSortInit(x);   //��ʼ��
				}
				if(index==3&&flagSelect==x.length) {
					index=17;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(index==12) {
					if(flagSelect<x.length) {
						int minIndex = flagSelect;
						for(int j = flagSelect;j<x.length;j++){//����δʣ��δ����Ԫ���м���Ѱ����СԪ��
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
			if(flag.equals("ϣ������")) {
				if(cleanFlag[7]) {
					graphics=gui.panel1.getGraphics();
					graphics.clearRect(gui.panel1.getX(), gui.panel1.getY(), gui.panel1.getWidth(), gui.panel1.getHeight());
					cleanFlag[7]=false;
				}
				if(index==2) {    //��ʼ��
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
			           		int temp = x[i];    //������������
			           		function.paintRec.shellUp(x, i,PaintRec.speed);
			                int index = i - gap;
			                while(index >= 0 && x[index] > temp) {
			                	function.paintRec.shellLeft(x, index+gap, temp, gap,PaintRec.speed);
			                   	x[index + gap] = x[index];      //����һ��Ԫ��
			                    index -= gap;
			                }
			                function.paintRec.shellDown(index+gap, x, temp,PaintRec.speed);
			                x[index + gap] = temp;     //������Ԫ������
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
			if(flag.equals("�۰��������")) {
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
				    graphics.setFont(new Font("����",Font.BOLD,25));
				    graphics.drawString("����λ��Ϊhigh+1!!", 200, 500);
				    paintRec.binsertSortInit(x, 0, 0);     //��ʼ״̬�»�ͼ
				}
				if(index==2&&flagBinser==x.length) {   //����ѭ��
					index=20;
					gui.listCode.setSelectedIndex(index);
					flagIndex=false;
				}
				if(flagBinser<x.length) {
					if(index==5) {
						paintRec.drawArrowAgain(low, high, x,PaintRec.speed);
						paintRec.binsertSortUp(flagBinser, x,PaintRec.speed);
					}
					if(index==8) {    //�ҵ�����λ��
						while(low<=high) {       //
							int mid = (high+low)/2;
							if(x[mid]<temp) {
							   //���ƶ�ֱ��ȫ��ɾ���������ü�ͷ�����鷳
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
							x[j+1] = x[j];           //array[j]����Ȼ�����Ƶ�array[j+1]�Ϸ�����ʱarray[j+1]ͬ����ʧ��Ȼ���Ϸ�Բ����������
						}
					}
					if(index==19) {
						paintRec.binsertSortLastSteps(high, temp,flagBinser,PaintRec.speed);
						x[high+1] = temp;
						flagBinser++;
						card1=0;   //�µ�һ���������
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
