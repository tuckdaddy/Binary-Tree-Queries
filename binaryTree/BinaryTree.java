/*
Author: Tucker Snider
*/
package binaryTree;


import iterators.Predicate;
import iterators.ReduceFunction;
import java.util.*;

public class BinaryTree<T, OutT> {

    // the root of the tree
    private TreeNode<T> root;
    // the queue of TreeNodes in the tree that have 0 or 1 children
    private final List<TreeNode<T>> nodesToInsertAt;

    // number of TreeNodes in the tree
    public int size;

    public BinaryTree() {
            root = null;
            nodesToInsertAt = new LinkedList<>();
            size = 0;
    }
    /*
    Notice that there is a LinkedList called nodesToInsertAt. 
    This will help you keep track of the next available spot in the binary tree. If done
    correctly, testInsertionAndtoArray test should pass.
    */
    /*
    Insert the element d into the BinaryTree at the
    "next available location" starting with the left
    */
    //PART 1 HERE
    public void insertNode(T d) {
         /* Functions to insert data */
         TreeNode newNode = new TreeNode(d);
         nodesToInsertAt.add(newNode);
         size++;
         if(root == null){
             root = newNode;
             return;
         }
         TreeNode current; 
         current = nodesToInsertAt.get(0);
         if(current.left == null){
             current.left = newNode;
             return;
         }
         if(current.right == null){
             current.right = newNode;
             nodesToInsertAt.remove(0);
         }
    }
  
    /* Takes a depth n and a ReduceFunction f
    and returns the "combined value" of all elements in the tree at depth n,
    where depth=0 is the root.

    "combined" means the result of starting with f.initialValue
    and "concatentation or combination" each element using f.combine

    Requirement: must use a loop
    */
    public OutT combineValuesAtDepth(int depth, ReduceFunction<T,OutT> f) {
        //PART 2 HERE
        Queue<TreeNode<T>> q = new LinkedList<>();
        TreeNode<T> knoten = root;
        int n = 0;
        q.add(knoten);
        while(n != depth){
            
            int nodesAtLevel = q.size();
            
            while(nodesAtLevel > 0){
                knoten = q.poll();
                if(knoten.left != null){
                    q.add(knoten.left);
                }
                if(knoten.right != null){
                    q.add(knoten.right);
                }
                nodesAtLevel--;
            } //end inner while loop
            n++;
        } // end outer while loop
        
        OutT sum = f.initialValue();
        for(TreeNode<T> nodes : q){
            sum  = f.combine(sum, nodes.data);
        }
        return  sum;
    }

    /*Takes a depth n and a ReduceFunction f
    and returns the "combined value" of all elements in the tree at depth n,
    where depth=0 is the root.

    "combined" means the result of starting with f.initialValue
    and "concatentation or combination" each element using f.combine

    NOTE that the "in" generic type and "out" generic type are the same type.
    This makes the recurision a easier.
    Requirement: must use a recursive.
    */
    public T combineValuesAtDepthRecursive(int n, ReduceFunction<T,T> f) {
        return combineValuesHelper(n, root, f, f.initialValue());                   
    }

    private T combineValuesHelper(int depth, TreeNode<T> node, ReduceFunction<T,T> f, T sum){
        if(node == null){
            return sum;
        }
        else if(depth == 0){
            return f.combine(sum, node.data);
        }
        else{
            return f.combine(combineValuesHelper(depth-1, node.left,f,sum),combineValuesHelper(depth-1, node.right,f,sum));
        }
            
    }

    /* Takes a Predicate p and returns the list of all elements
    for which p.check is true. The elements must be returned in
    "pre-order" traversal order.

    Requirement: must use a loop
    */
    public List<T> wantedNodesIterative(Predicate<T> p) {
        //PART 4 
        if(root == null){
            return null;
        }
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.add(root);
        
        List<T> wantedNodes =new LinkedList<>();
        TreeNode<T> current;
        
        while(!stack.isEmpty()){
            current = stack.pop();
            if(p.check(current.data)){
                wantedNodes.add(current.data);
            }
            if(current.right != null){
                stack.addFirst(current.right);
            }
            if(current.left != null){
                stack.addFirst(current.left);
            }
        }
        return wantedNodes;
    }
    

    /* Takes a Predicate p and returns the list of all elements
    for which p.check is true. The elements must be returned in
    "pre-order" traversal order.

    Requirement: must be recursive
    */
    /*
    Requirement: must be recursive
    */
    public List<T> wantedNodesRecursive(Predicate<T> p) {
        return wantedNodesRecursive(p, root);
    }
    
    private List<T> wantedNodesRecursive(Predicate<T> p, TreeNode<T> node){
        if(node == null) {
            return null;
        }        
        
        List<T> res = new LinkedList();
        if(p.check(node.data)) {
            res.add(node.data);  
        }
        if(node.left != null) {
            List<T> wantedNodes = (wantedNodesRecursive(p, node.left));
            for(int i = 0; i < wantedNodes.size(); i++) {
                res.add(wantedNodes.get(i));
            }
        }
        if(node.right != null) {
            
            List<T> wantedNodes = (wantedNodesRecursive(p, node.right));
            for(int i = 0; i < wantedNodes.size(); i++) {
                res.add(wantedNodes.get(i));
            }            
        }        
        return res;
    }

    //////////////// Dont edit after here //////////////////////


    public void printTree() {
        Object[] nodeArray = this.toArray();
        for (int i = 0; i < nodeArray.length; i++) {
                        System.out.println(nodeArray[i]);
        }
    }
    
    public void displayTree()
    {
        Stack globalStack = new Stack<TreeNode>();
        globalStack.push(root);	
        int emptyLeaf = 32;
        boolean isRowEmpty = false;
        System.out.println("****..................................................................................................................................****");
        while(isRowEmpty==false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<emptyLeaf; j++)
                    System.out.print("  ");

            while(globalStack.isEmpty()==false)
            {
                    TreeNode temp = (TreeNode) globalStack.pop();
                    if(temp != null)
                    {
                        System.out.print(temp.data);
                        localStack.push(temp.left);
                        localStack.push(temp.right);
                        if(temp.left != null ||temp.right != null)
                            isRowEmpty = false;
                    }
                    else
                    {
                        System.out.print("--");
                        localStack.push(null);
                        localStack.push(null);
                    }

                    for(int j=0; j<emptyLeaf*2-2; j++)
                        System.out.print("  ");
            }
            System.out.println();
            emptyLeaf /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );

        }

	System.out.println("****..................................................................................................................................****");
    } 
    
    public Object[] toArray() {
        Object[] r = new Object[size];
        if (root == null) {
            return r;
        }

        // traverse the tree to visit all nodes,
        // adding them to r
        List<TreeNode> frontier = new LinkedList<>();
        frontier.add(root);
        int soFar = 0;

        while (frontier.size() > 0) {
            TreeNode v = (TreeNode) frontier.get(0);
            r[soFar] = v.data;
            soFar++;

            if (v.left != null) {
                frontier.add(v.left);
            }
            if (v.right != null) {
                frontier.add(v.right);
            }

            frontier.remove(0);
        }
        return r;
    }

    private static class TreeNode<T> {

        public T data;
        public TreeNode<T> left;
        public TreeNode<T> right;

        public TreeNode(T d) {
                        data = d;
                        left = null;
                        right = null;
        }
    }

 
}
