package chapter3.example;
  //------------180508开始分割线----------
public class RedBlackBST<Key extends Comparable<Key>, Value> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	
	private Node root;
	private class Node{
		Key key;         //键
		Value val;       //值
		Node left,right; //子节点
		int N;           //以此节点作为根节点的数的节点个数
		boolean color;   //指向此节点的连接线的颜色
		public Node(Key key, Value val, int N, Boolean color) {
			this.key = key; this.val = val; this.N = N; this.color = color;
		}
	}
	
	
	private boolean isRed(Node x) {
		if(x == null) return false;
		return x.color == RED;
	}
	
	//size()
	public int size(Node node) {
		if(node == null) return 0;
		else //return size(note.left) + size(note.right) + 1;
			return node.N;
	}
	
	public int size() {
		return size(root);
	}
	
	private Node rotateLeft(Node h) {
		if(h == null)  return null;
		Node x = h;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1+size(h.left)+size(h.right);
		return x;
	}
	
	private Node rotateRight(Node h) {
		if(h == null)  return null;
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}
	
	void flipColors(Node h) {
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}
	
	public void put(Key key, Value val) {
		root = put(root, key, val);
		root.color = BLACK;
	}
	
	public Node put(Node h, Key key, Value val) {
		if(h == null)
			return new Node(key,val,1,RED);
		
		int cmp = key.compareTo(h.key);
		if(cmp < 0) h.left = put(h.left,key,val);
		else if(cmp > 0) h.right = put(h.right,key,val);
		else h.val = val;
		
		
		//在递归的调用中，下面的操作实际是自底向上的调整
		if(isRed(h.right) && !isRed(h.left))   h = rotateLeft(h);
		if(isRed(h.left)  && !isRed(h.right))  h = rotateRight(h);
		if(isRed(h.left)  &&  isRed(h.right))  flipColors(h);
		h.N = 1 + size(h.left) + size(h.right);
		return h;
	}
	
	//------------180508结束分割线----------
}
