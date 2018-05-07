package chapter3.alixia.exercise;

import lib.Queue;

public class BST <Key extends Comparable<Key>, Value>{
	
	private Note root;
	private class Note{
		private Key key;
		private Value val;
		private Note left , right;
		private int N;
		public Note(Key key, Value val, int N) {
			this.key = key; this.val = val; this.N = N;
		}
	}
	public int size(Note note) {
		if(note == null) return 0;
		else //return size(note.left) + size(note.right) + 1;
			return note.N;
	}
	
	public int size() {
		return size(root);
	}
	//放入数据
	public void put(Key key, Value val) {
		put(root, key, val);
	}
	private void put(Note note, Key key, Value val) {
		if(note == null)//第一个节点，root == null,是空树;
		{
			root = new Note(key, val, 1);
			return;
		}
		int tmp = key.compareTo(note.key);
		if(tmp < 0) 
			if(note.left == null) {
				Note tmpNote = new Note(key, val, 1);
				note.left = tmpNote;
			}
			else
				put(note.left, key, val);
		else 
			if(note.right == null) {
				Note tmpNote = new Note(key, val, 1);
				note.right = tmpNote;
			}
			else
				put(note.right, key, val);
		note.N ++ ;
	} 
	//取数据
	public Value get(Key key) {
		return get(root, key);
	}
	private Value get(Note note, Key key) {//查找note为根节点的树
		if(note == null ) return null;
		int tmp = key.compareTo(note.key);
		if(tmp == 0) return note.val;
		if(tmp < 0) return get(note.left, key);
		else
			return get(note.right, key);
	}
	
	//求最小值
	public Key min() {
		return min(root).key;
	}
	private Note min(Note node) {
		if(node.left == null) return node;
		return min(node.left);
	}
	//求最大值
	public Key max() {
		return max(root).key;
	}
	private Note max(Note node) {
		if(node.right == null) return node;
		return max(node.right);
	}
	//求排名为k（树中有k个小于目标值的键）的键
	public Key select(int k) {
		return select(root,k).key;
	}
	private Note select(Note note,int k) {
		if(note == null) return null;
		int temp = size(note.left);
		if(temp == k) return note;
		else
			if(temp > k)
				return select(note.left,k);
			else
				return select(note.right,k-temp-1);
	}
	
	//求书中键值小于等于key的节点的个数
	public int rank(Key key) {
		return rank(root,key);
	}
	
	private int rank(Note note,Key key) {
		if(note == null) return 0;
		int temp = size(note.left);
		int com = note.key.compareTo(key);
		if(com == 0) return temp+1;
		else if(com == -1) return rank(note.right,key) + temp + 1;
		else return rank(note.left,key);
	}
	//删除书中的最小值
	public void deleteMin() {
		root = deleteMin(root);
	}
	private Note deleteMin(Note note) {
		if(note == null) return null;
		if(note.left == null) return note.right;
		else {
			note.left = deleteMin(note.left);
			note.N --;
			return note;
		}
	}
	//删除书中的最小值
	public void delete(Key key) {
		root = delete(root,key);
	}
	private Note delete(Note note,Key key) {
		if(note == null) return null;
		int com = note.key.compareTo(key);
		if(com == -1) note.right = delete(note.right,key);
		if(com == 1) note.left = delete(note.left,key);
		else {
			if(note.right == null) return note.left;
			if(note.left == null) return note.right;
			
			Note temp = min(note.right);
			temp.left = note.left;
			note = note.right;
			
//			Note temp = note;
//			note = min(temp.right);
//			note.left = temp.left;
//			note.right = deleteMin(temp.right);
		}
		note.N = note.left.N + note.right.N +1;
		return note;
		
	}
	
	//中序遍历打印
	public void printMid() {
		printMid(root);
	}
	private void printMid(Note note) {
		if(note == null) return;
		printMid(note.left);
		System.out.print(String.valueOf(note.val)+" ");
		printMid(note.right);
	}
	
	
	//查找树中键值在lo~hi之间的所有元素
	public Iterable<Key> keys(Key lo,Key hi){
		Queue<Key> que = new Queue<>();
		keys(root,que,lo,hi);
		return que;
	}
	private void keys(Note note,Queue<Key> que,Key lo,Key hi) {
		if(note == null) return;
		int cmpLo = lo.compareTo(note.key);
		int cmpHi = hi.compareTo(note.key);
		if(cmpLo < 0) keys(note.left,que,lo,hi);
		if(cmpLo <= 0 && cmpHi >= 0) que.enqueue(note.key);
		if(cmpHi > 0) keys(note.right,que,lo,hi);
	}
	
	public static void main(String[] args) {
		BST<Integer,String > human = new BST<Integer, String>();
		human.put(2, "LiSi");
		human.put(1, "ZhangShan");
		human.put(3, "WangWu");
		human.put(4, "YangLiu");
		human.put(0, "Lisa");
		System.out.println(human.get(4));
		System.out.println(human.min());
		System.out.println(human.max());
		System.out.println(human.select(2));
		System.out.println(human.rank(3));
//		human.deleteMin();
//		human.delete(2);
		human.printMid();
		System.out.println();
		Iterable<Integer> que = human.keys(1, 4);
		for(int i:que)
			System.out.print(i);
	}
}
