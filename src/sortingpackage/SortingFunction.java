package sortingpackage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;



/** 
 * @ClassName: SortingFunction
 * @description: 九种排序函数实现
 * @author: 李俊良
 * @Date: 2019年12月20日 下午12:14:30
 */

public class SortingFunction{
	MainGUI gui;
	PaintRec paintRec;
	Graphics graphics;
	int array[];
	ArrayList<ArrayList<Integer>> bucketList1 = new ArrayList<ArrayList<Integer>>();
	int []pivotPos;
	static int indexx=0,heapIndex=0;
	static int count=0,card=0;
	public SortingFunction(MainGUI gui) {
		this.gui=gui;
		paintRec=new PaintRec(gui);
		
	}
	
	/**
	 * 直接插入排序
	 */
	public int[] insertSort(int[] array){
		if(array.length > 0){
			paintRec.directInsertInit(array);   //初始状态下画图
			for(int i = 1 ;i<array.length;i++){
				int current = array[i];
				paintRec.directInsertDown(array, i,PaintRec.speed);  //待排序的小矩形先下移
				int index = i;
				while(index-1 >= 0 && current < array[index-1]){
					paintRec.directInsertLeft(array, index,current,PaintRec.speed);
					array[index] = array[index-1];    //小矩形右移
					index--;
				}
				paintRec.directInsertUp(array, index,current,PaintRec.speed);
				array[index] = current;             //相当于小矩形上升
			}
		}
		graphics=gui.panel1.getGraphics();
	    graphics.setColor(Color.black);
	    graphics.setFont(new Font("宋体",Font.BOLD,25));
	    graphics.drawString("Completed!!", 270, 500);
		return array;
	}
	
	//直接插入排序的拆分
	
	/**
	 * 折半插入排序
	 */
	public int[]binsertSort(int[] array) {
		graphics=gui.panel1.getGraphics();
	    graphics.setColor(Color.black);
	    graphics.setFont(new Font("宋体",Font.BOLD,25));
	    graphics.drawString("插入位置为high+1!!", 200, 500);
	    paintRec.binsertSortInit(array, 0, 0);     //初始状态下画图
		for (int i = 1; i < array.length; ++i) {     //初始画图与快速排序类似
			int temp = array[i];         
			int low = 0;
			int high = i - 1;               //在i的前面是已经排序完成的序列，以此为基准找到待插入位置
			paintRec.drawArrowAgain(low, high, array,PaintRec.speed);
			paintRec.binsertSortUp(i, array,PaintRec.speed);          //temp上升不动
			while(low<=high) {       //
				int mid = (high+low)/2;
				if(array[mid]<temp) {
				   //不移动直接全部删掉重新设置箭头避免麻烦
					paintRec.drawArrowAgain(mid+1, high, array,PaintRec.speed);
					low = mid+1;
				}
				else {
					paintRec.drawArrowAgain(low, mid-1, array,PaintRec.speed);
					high = mid-1;
				}
			}
			for (int j = i-1; j > high; --j) {
				paintRec.binsertSortReplacement(array, j,PaintRec.speed);
				array[j+1] = array[j];           //array[j]上升然后右移到array[j+1]上方，此时array[j+1]同步消失，然后上方圆形数据下落
			}
			paintRec.binsertSortLastSteps(high, temp,i,PaintRec.speed);
			array[high+1] = temp;            //找到插入位置为high+1
		}
		paintRec.binsertClean(array);
	    graphics.clearRect(150, 450, 300, 300);
	    graphics.drawString("Completed!!", 270, 500);
		return array;
	}
	//折半插入排序的拆分 
	/**
	  *希尔排序
	  */
	public int[] shellSort(int[] array) {
		if(array.length > 0) {
		    paintRec.shellSortInit(array);
		    int len = array.length;
            int gap = len / 2;
            paintRec.drawShellGap(gap,PaintRec.speed);
            while(gap > 0) {
            	for(int i = gap;i < len;i++) {
           		int temp = array[i];    //待插入结点上移
           		paintRec.shellUp(array, i,PaintRec.speed);
                int index = i - gap;
                while(index >= 0 && array[index] > temp) {
                	paintRec.shellLeft(array, index+gap, temp, gap,PaintRec.speed);
                   	array[index + gap] = array[index];      //后移一个元素
                    index -= gap;
                }
                paintRec.shellDown(index+gap, array, temp,PaintRec.speed);
                array[index + gap] = temp;     //待插入元素下落
           	}
           	gap /= 2;
           	paintRec.drawShellGap(gap,PaintRec.speed);
         }
		}
		return array;
	}
	

	
	/**
	 * 简单选择排序
	 */
	public int[] selectionSort(int[] array){
		if(array.length > 0){
			paintRec.bubbleSortInit(array);
			for(int i = 0;i<array.length;i++){
				int minIndex = i;
				for(int j = i;j<array.length;j++){//遍历未剩余未排序元素中继续寻找最小元素
					if(array[j] < array[minIndex]){
						minIndex = j;
					}
				}
				if(minIndex != i){
					paintRec.bubbleSortExchange(i, minIndex, array.length, array,PaintRec.speed);
					int temp = array[minIndex];
					array[minIndex] = array[i];
					array[i] = temp;
				}
			}
			
		}
		return array;
	}
	
	
	
	/**
	 * 调整堆
	 */
	public void heapAdjust(int[] array,int index,int length){
		//保存当前结点的下标
		int min = index;
		//当前节点左子节点的下标
		int lchild = 2*index;
		//当前节点右子节点的下标
		int rchild = 2*index + 1;
		if(length > lchild && array[min] > array[lchild]){
			min = lchild;
		}
		if(length > rchild && array[min] > array[rchild]){
			min = rchild;
		}
		//若此节点比其左右孩子的值小，就将其和最大值交换，并调整堆
		if(min != index){
			paintRec.heapSortExchange(index, min, array,PaintRec.speed);   //核心算法就是交换
			int temp = array[index];          
			array[index] = array[min];
			array[min] = temp;
			heapAdjust(array,min,length);
		}
		
	}
	
	/**
	 * 堆排序
	 */
	public int[] heapSort(int[] array){
		paintRec.heapSortInit(array,PaintRec.speed);
		int len = array.length;
		//初始构造一个小根堆
		for(int i = (len/2 - 1);i >= 0;i--){
			heapAdjust(array,i,len);
		}
		graphics=gui.panel1.getGraphics();
	    graphics.setColor(Color.black);
	    graphics.setFont(new Font("宋体",Font.BOLD,25));
	    graphics.drawString("第"+(++heapIndex)+"次调整完毕，找到一个最小值!", 200, 500);
		//将堆顶的元素和最后一个元素交换后重新调整堆
		for(int i = len - 1;i > 0;i--){         //交换就是擦掉两个圆再重新画一遍
			paintRec.heapSortExchange(i, 0, array,PaintRec.speed);
			int temp = array[i];
			array[i] = array[0];
			array[0] = temp;
			heapAdjust(array,0,i);    //调整成小根堆
			graphics.clearRect(150, 450, 400 , 80);
			graphics.drawString("第"+(++heapIndex)+"次调整完毕，找到一个最小值!", 200, 500);
		}
		graphics.clearRect(150, 450, 460 , 80);
		graphics.drawString("调整完毕，最终序列即树结点逆序序列:", 120, 500);
		paintRec.heapSortLast(array);
		return array;
	}
	//将堆排序拆分掉
	public void heapSortInit(int array[]) {    ////初始构造一个小根堆
		paintRec.heapSortInit(array,PaintRec.speed);
		int len = array.length;
		for(int i = (len/2 - 1);i >= 0;i--){
			heapAdjust(array,i,len);
		}
		heapIndex=0;
		graphics=gui.panel1.getGraphics();
	    graphics.setColor(Color.black);
	    graphics.setFont(new Font("宋体",Font.BOLD,25));
	    graphics.drawString("第"+(++heapIndex)+"次调整完毕，找到一个最小值!", 200, 500);
	}
	
	public void heapSortStepsOut(int array[],int i) {
		paintRec.heapSortExchange(i, 0, array,PaintRec.speed);
		int temp = array[i];
		array[i] = array[0];
		array[0] = temp;
		heapAdjust(array,0,i);    //调整成小根堆
		graphics.clearRect(150, 450, 400 , 80);
		graphics.drawString("第"+(++heapIndex)+"次调整完毕，找到一个最小值!", 200, 500);
	}
	public void heapSortLast(int array[]) {    //最后一步返回时将排序好的数组输出
		paintRec.heapSortLast(array);
	}
	
	
	/**
	 * 冒泡排序
	 */
	public int[] bubbleSort(int[] array){
		if(array.length > 0){
			paintRec.bubbleSortInit(array);   //初始状态下画图
			for(int i = 0;i<array.length;i++){
				for(int j = 0;j<array.length - 1 - i;j++){
					if(array[j] > array[j+1]){
						paintRec.bubbleSortExchange(j, j+1, array.length,array,PaintRec.speed);
						int temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;
					}
				}
			}
		}
		return array;
	}//冒泡排序拆分
	public void initBubble(int array[]) {
		paintRec.bubbleSortInit(array);
	}
	public void bubbleSortExchange(int i,int array[]) {
		for(int j = 0;j<array.length - 1 - i;j++){
			if(array[j] > array[j+1]){
				paintRec.bubbleSortExchange(j, j+1, array.length,array,PaintRec.speed);
				int temp = array[j];
				array[j] = array[j+1];
				array[j+1] = temp;
			}
		}
	}
	
	
	
	/**
	 * 快速排序算法
	 */
	public int partition(int[] array,int low,int high){
		int pivot = array[low];
		while(low < high){
			while(low < high && array[high] >= pivot) {       //光标移动
				paintRec.quickSortMoveArrow(high, true,low,PaintRec.speed);
				--high;
			}
			paintRec.quickSortReplacement(array, low, high, true,PaintRec.speed);
			array[low] = array[high];      //array[high]上移但不删除，然后移动到array[low]上方，最后替换掉array[low]
			while(low < high && array[low] <= pivot) {  //光标移动
				paintRec.quickSortMoveArrow(low, false,high,PaintRec.speed);
				++low;
			}
			paintRec.quickSortReplacement(array, low, high, false,PaintRec.speed);
			array[high] = array[low];     //同上
		}
		paintRec.quickLastSteps(low, pivot,PaintRec.speed);
		array[low] = pivot;   //清除掉再重新画图即可
		return low;			
	}
	public void QuickSort(int[] array,int low,int high,boolean flag,boolean repeat){
		if(flag) {
			pivotPos=new int[array.length];
			paintRec.quickSortInit(array,0,array.length-1);
		}
		if(low < high){
			if(repeat) {
				int pivotPos = partition(array,low,high);   //找到基准位置，先将重合的high以及low清除掉
				this.pivotPos[count]=pivotPos;
				count++;
				paintRec.quickSortClean(pivotPos,PaintRec.speed);  //清除重合的两个箭头
				paintRec.quickSortTwoArrow(low, pivotPos-1,PaintRec.speed);   //重新设置新的箭头，low与high可能还是重合的
				if(low==0&&pivotPos==1) {    //左边排序结束
					repeat=false;
					low=0;
					pivotPos=2;
					paintRec.quickSortClean(0,PaintRec.speed);   //清除重合的两个箭头
				}
				QuickSort(array,low,pivotPos - 1,false,repeat);    //一趟排完序之后必须重新设置箭头
			}
			else {
				if(card==0) {
					low=pivotPos[0]+1;
					high=array.length-1;
					card++;
					paintRec.quickSortTwoArrow(low, high,PaintRec.speed);
				}
				int pivotPos = partition(array,low,high);   //找到基准位置
				paintRec.quickSortClean(pivotPos,PaintRec.speed);
				paintRec.quickSortTwoArrow(pivotPos-1,high,PaintRec.speed);
				QuickSort(array,pivotPos + 1,high,false,false);		
			}
		}
		paintRec.binsertClean(array);
	}
	//快速排序拆分
	public void quickInit(int array[]) {
		pivotPos=new int[array.length];
		paintRec.quickSortInit(array,0,array.length-1);
	}
	public void quickLeft(int array[],int low,int high) {
		boolean flag=true;
		if(low<high) {
			int pivotPos = partition(array,low,high);   //找到基准位置，先将重合的high以及low清除掉
			this.pivotPos[count]=pivotPos;
			count++;
			paintRec.quickSortClean(pivotPos,PaintRec.speed);  //清除重合的两个箭头
			paintRec.quickSortTwoArrow(low, pivotPos-1,PaintRec.speed);   //重新设置新的箭头，low与high可能还是重合的
			if(low==0&&pivotPos==1) {    //左边排序结束
				low=0;
				pivotPos=2;
				paintRec.quickSortClean(0,PaintRec.speed);   //清除重合的两个箭头
				flag=false;  //跳出循环
			}
			if(flag) {
				quickLeft(array,low,pivotPos - 1);    //一趟排完序之后必须重新设置箭头	
			}
		}
		paintRec.binsertClean(array);
	}
	public void quickRight(int array[],int low,int high) {
		if(low<high) {
			//paintRec.binsertClean(array);
			int pivotPos = partition(array,low,high);   //找到基准位置
			paintRec.quickSortClean(pivotPos,PaintRec.speed);
			paintRec.quickSortTwoArrow(pivotPos-1,high,PaintRec.speed);
			quickRight(array,pivotPos + 1,high);
			paintRec.binsertClean(array);
		}
	}
	
	/**
	 * 2路归并算法
	 */
	/*public int[] MergeSort(int[] array){
		if(array.length < 2){
			return array;
		}
		int mid = array.length /2;
		int[] left = Arrays.copyOfRange(array, 0, mid);
		int[] right = Arrays.copyOfRange(array, mid, array.length);
		return merge(MergeSort(left),MergeSort(right));	
	}
	
	public int[] merge(int[] left,int[] right){     //把left与right归并成一个数组
		int[] result = new int[left.length + right.length];   //重新分配array
		for(int index = 0,i = 0, j = 0;index < result.length;index++){
			if(i >= left.length){
				result[index] = right[j++];
			}
			else if(j >= right.length){
				result[index] = left[i++];
			}
			else if(left[i] > right[j]){
				result[index] = right[j++];
			}
			else{
				result[index] = left[i++];
			}
		}
		return result;
		
	}*/
	
	
	//归并排序的非递归实现
	public void mergeSortSingleStep(int width,int []arr) {
		if(arr==null||arr.length<=0) {
			return;
		}
		if(width==1) {
			paintRec.mergeSortInit(arr);
			paintRec.mergeSortYMove(paintRec.yCoordinate, paintRec.numberY);
		}
		indexx=0;
		mergePass(arr, width);
		paintRec.mergeSortYMove(paintRec.yCoordinate, paintRec.numberY);
	}
	public void mergeSort(int[] arr) {
		 if(arr==null || arr.length<=0) {
			 return;
		 }
	     int width = 1;
	     paintRec.mergeSortInit(arr);   //未排序初始化时候的图
	     paintRec.mergeSortYMove(paintRec.yCoordinate, paintRec.numberY);  //先将坐标下移
	     while(width<arr.length) {
	    	 indexx=0;
	    	 mergePass(arr,width);    //将长度为width的数组排序之后再合并到arr中
	    	 //一趟归并之后将数组坐标下移，显示下一趟排序动态过程
	    	 paintRec.mergeSortYMove(paintRec.yCoordinate, paintRec.numberY);
	         width*=2;//定义要合并的小数组长度从1开始，在较小的长度数组都合并完成后，令长度*2，继续进行合并，直到合并完成。
	      }
	     graphics=gui.panel1.getGraphics();
	     graphics.setColor(Color.black);
	     graphics.setFont(new Font("宋体",Font.BOLD,25));
	     graphics.drawString("MergeSort Completed!", 200, 500);
	    }
	 public void mergePass(int[] arr,int width) {     //
	        int start=0;
	        while(start+2*width-1<arr.length) {    //依次合并每个长度为width的小数组里面的元素
	            int mid=start+width-1;
	            int end=start+2*width-1;
	            merge(arr,start,mid,end);
	            try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            start=start+2*width;
	        }
	        //剩余无法构成完整的两组也要进行处理
	        if(start+width-1<arr.length)
	            merge(arr, start, start+width-1, arr.length-1);
	 }
	 
	 public void merge(int[] arr, int start, int mid, int end) {//将[start~mid-1]和[mid~end]部分的数据合并到arr中;
		 int i=start;
	     int j=mid+1;
	     int[] temp = new int[end-start+1]; 
	     int index=0;
	     while(i<=mid && j<=end) {
	    	 if(arr[i]<=arr[j]) {
	    		 paintRec.mergeSortCore(arr, indexx, i,PaintRec.speed);
	    		 indexx++;
	    		 temp[index++]=arr[i++];   //将上面的元素放在新建的下标新建的数组里面
	    	 }
	         else {
	        	 paintRec.mergeSortCore(arr, indexx, j,PaintRec.speed);
	        	 indexx++;
	        	 temp[index++]=arr[j++]; 
	         }
	     }
	     while(i<=mid) {
	    	 paintRec.mergeSortCore(arr, indexx, i,PaintRec.speed);
	    	 indexx++;
	    	 temp[index++]=arr[i++];
	     }
	     while(j<=end) {
	    	 paintRec.mergeSortCore(arr, indexx, j,PaintRec.speed);
	    	 indexx++;
	    	 temp[index++]=arr[j++];
	     }
	     for(int k=start;k<=end;k++)
	         arr[k]=temp[k-start];     //将合并好的小数组替换原来的小数组中相应元素
	 }
	
	
	
	
	/**
     * 基数排序
     */
    public int[] radixSort(int[] array) {
        if (array == null || array.length < 2)
            return array;
        //先算出最大数的位数；
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
             max = Math.max(max, array[i]);
        }
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;           //maxDigit最大位数
        }
        paintRec.radixSortInit(array);
        int mod = 10, div = 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < 10;i++){
        	bucketList.add(new ArrayList<Integer>());      //新建十个队列
        }
        for(int i = 0;i < maxDigit;i++,mod *= 10 ,div *= 10){    //需要经历maxDigit次分配与收集
        	for(int j = 0;j < array.length;j++){       //第i次分配
        		int num = (array[j] % mod) / div;      //计算出从低到高第i位的数字为，然后加入到相应的队列当中
        		paintRec.buckeListAdd(num, j, array,PaintRec.speed);
        		bucketList.get(num).add(array[j]);     //收集器上添加数据
        	}
        	int index = 0;
        	for(int j = 0;j < bucketList.size();j++){     //第i次收集放入数组当中
        		for(int k = 0;k < bucketList.get(j).size();k++){
        			paintRec.buckeListClean(index, j, k, bucketList.get(j).get(k),PaintRec.speed);
        			array[index++] = bucketList.get(j).get(k);
        		}
    			bucketList.get(j).clear();
        	}	
        }
        graphics=gui.panel1.getGraphics();
	    graphics.setColor(Color.black);
	    graphics.setFont(new Font("宋体",Font.BOLD,25));
	    graphics.drawString("RaidxSorting Completed!!", 190, 500);
        return array;
    }
    
    public int[] radixSortInit(int array[]) {
    	if (array == null || array.length < 2)
            return array;
        paintRec.radixSortInit(array);
        for(int i = 0; i < 10;i++){
        	bucketList1.add(new ArrayList<Integer>());      //新建十个队列
        }
        return array;
    }
    
    public void radixSortCore1(int array[],int mod,int div) {
        //一次分配
        for(int j = 0;j < array.length;j++){       //第i次分配
    		int num = (array[j] % mod) / div;      //计算出从低到高第i位的数字为，然后加入到相应的队列当中
    		paintRec.buckeListAdd(num, j, array,PaintRec.speed);
    		bucketList1.get(num).add(array[j]);     //收集器上添加数据
    	}	
    }
    //一次收集
    public void radixSortCore2(int array[],ArrayList<ArrayList<Integer>> bucketList) {
    	int index = 0;
    	for(int j = 0;j < bucketList.size();j++){     //第i次收集放入数组当中
    		for(int k = 0;k < bucketList.get(j).size();k++){
    			paintRec.buckeListClean(index, j, k, bucketList.get(j).get(k),PaintRec.speed);
    			array[index++] = bucketList.get(j).get(k);
    		}
			bucketList.get(j).clear();
    	}
    }
}

class ThreadCore{
	EventAction action;
	SortingFunction function;
	public ThreadCore(SortingFunction function,EventAction action) {
		this.function=function;
		this.action=action;
	}
	public synchronized void step() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

