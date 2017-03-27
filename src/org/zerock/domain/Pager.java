package org.zerock.domain;

public class Pager {
	
	
	private int current;//현재페이지
	private int total;//총개수
	private int start;//시작페이지
	private int end;//마지막페이지
	private boolean prev;//이전이 있는가?
	private boolean next;//뒤가 있는가?
	private double viewCount = 10.0;//몇개 보여줄것인가(페이지를).
	private int dynamicEnd;//이동시킬때 쓸까?
	private int newStart;//너도
	public int getNewStart() {
		return newStart;
	}

	public void setNewStart(int newStart) {
		this.newStart = newStart;
	}
	private int size;
	public Pager(int pageNum,int totalCount){
		if(pageNum <= 0){//이렇게 막아야 1페이지나옴.
			pageNum=1;
		}
		this.current = pageNum;
		this.total = totalCount;
		calcPage();
	}
	
	public int getDynamicEnd() {
		return dynamicEnd;
	}

	public void setDynamicEnd(int dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}

	public Pager(int pageNum,int totalCount, int size){
		this.size=size;
		this.current = pageNum;
		this.total = totalCount;
		calcPage();
	}
	public Pager(int pageNum,int totalCount, int size,int viewCount){
		this.size=size;
		this.viewCount = viewCount;
		this.current = pageNum;
		this.total = totalCount;
		calcPage();
	}
	public void display(){//end처리가 의심됨 + 이거좀 이상한데 바꿔야겠는데.
	  newStart = this.current-(int)(this.viewCount/2);
		int sumOfcategory=(int)viewCount;
		if(newStart<1)newStart=1;
		sumOfcategory-=(this.current-newStart);
		 dynamicEnd= this.current+sumOfcategory;
		if(dynamicEnd>this.end)dynamicEnd=end;
		
		this.start = 1;
		this.end =(int)Math.ceil(total/(size/1.0));
		
		
	}
	
	private void calcPage(){
		int tempEnd = (int)(Math.ceil(this.current/viewCount)*viewCount);
		this.start = 
				tempEnd-(int)(viewCount-1);	
		this.end = 
				tempEnd*size>total?(int)Math.ceil(total/(size/1.0)):tempEnd;
		this.prev = start==1?false:true;
		this.next = this.total>(this.end*size)?true:false;
	}
	
	public void update(){
		
	}
	
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public double getViewCount() {
		return viewCount;
	}
	public void setViewCount(double viewCount) {
		this.viewCount = viewCount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	@Override
	public String toString() {
		return "Pager [current=" + current + ", total=" + total + ", start=" + start + ", end=" + end + ", prev=" + prev
				+ ", next=" + next + ", viewCount=" + viewCount + "]";
	}
	
}
