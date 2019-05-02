
import iterators.Predicate;
import binaryTree.BinaryTree;
import iterators.ReduceFunction;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class BinaryTreeTest {

    public BinaryTreeTest() {
    }

    @Test
    public void testInsertionAndtoArray() {
        BinaryTree bt = new BinaryTree();
        bt.insertNode(50);
        bt.insertNode(2);
        bt.insertNode(34);
        bt.insertNode(19);
        bt.insertNode(6);
        bt.insertNode(22);
        bt.displayTree();
        Object[] actual = bt.toArray();
        Object[] expected = {50, 2, 34, 19, 6, 22};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sumOfDepthTest() {
        BinaryTree bt = new BinaryTree();
        bt.insertNode(50);
        bt.insertNode(2);
        bt.insertNode(34);
        bt.insertNode(19);
        bt.insertNode(6);
        bt.insertNode(22);
        int sum = (int) bt.combineValuesAtDepth(2, new IntegerSum());
        assertEquals(47, sum);
    }

    @Test
    public void wantedNodesIterativeTest() {
        BinaryTree bt = new BinaryTree();
        bt.insertNode(50);
        bt.insertNode(2);
        bt.insertNode(34);
        bt.insertNode(19);
        bt.insertNode(6);
        bt.insertNode(22);
        Integer[] expected = {50,2,6,34,22};
        List<Integer> answers = bt.wantedNodesIterative(new IsEven());
        assertArrayEquals(expected, answers.toArray());
    }

    // Example implementation of ReduceFunction used by the given test
    private static class IntegerSum implements ReduceFunction<Integer, Integer> {
        @Override
        public Integer combine(Integer soFar, Integer x) {
                        return soFar+x;
        }

        @Override
        public Integer initialValue() {
                        return 0;
        }
    }

    // Example implementation of IsEven used by the given test
    private static class IsEven implements Predicate<Integer> {
        @Override
        public boolean check(Integer data) {
                        return data % 2 == 0;
        }
    }
    private static class IsOdd implements Predicate<Integer>{
        @Override
        public boolean check(Integer data){
            return data % 2 !=0;
        }
    }
    private static class Multiples implements Predicate<Integer>{
        int  multi = 0;
        private Multiples(Integer multipleOF){
            this.multi = multipleOF;
            
        }
        @Override
        public boolean check(Integer data){
            return data % multi == 0;
        }
    }
    /* The staff will run your code on several additional JUnit tests of our own.
       You must write additional tests below to provide more evidence that your
       method implementations are correct. 

       This test code is part of the assignment, just like the other code.

       If you write a new test and it fails, GREAT! That means you are making
       progress. Either the test is wrong and you just need to fix it, or it means you found
       a bug in your BinaryTree code and now you can go fix it. Don't remove good tests just
       because they fail.
     */


    // write your new tests below here, using the @Test methods above as an example.
    @Test
    public void wantedNodesRecursiveTest() {
                    BinaryTree bt = new BinaryTree();
                    bt.insertNode(50);
                    bt.insertNode(2);
                    bt.insertNode(34);
                    bt.insertNode(19);
                    bt.insertNode(6);
                    bt.insertNode(22);
                    Integer[] expected = {50,2,6,34,22};
                    List<Integer> answers = bt.wantedNodesRecursive(new IsEven());
                    assertArrayEquals(expected, answers.toArray());
    }
    @Test 
    public void emptyTest(){
        BinaryTree bt = new BinaryTree();
        Integer[] expected = {};
        Object[]answer = bt.toArray();
        assertArrayEquals(expected, answer);
    }
    @Test
    public void oneNodeTest(){
        BinaryTree bt = new BinaryTree();
        bt.insertNode(20);
        Integer[] expected = {20};
        List answer = bt.wantedNodesRecursive(new IsEven());
        assertArrayEquals(expected, answer.toArray());
    }
    @Test
    public void wantedRecursiveIsOdd(){
        BinaryTree bt = new BinaryTree();
        bt.insertNode(20);
        bt.insertNode(19);
        bt.insertNode(20);
        bt.insertNode(20);
        bt.insertNode(1234567);
        bt.insertNode(20);
        bt.insertNode(20);
        bt.insertNode(20);
        bt.insertNode(33);
        bt.insertNode(20);
        bt.insertNode(20);
        bt.insertNode(20);
        Integer[] expected = {19,33,1234567};
        List answer = bt.wantedNodesRecursive(new IsOdd());
        assertArrayEquals(expected, answer.toArray());
    }
    @Test
    public void MultiplesTest(){
        BinaryTree bt = new BinaryTree();
        bt.insertNode(10);
        bt.insertNode(9);
        bt.insertNode(18);
        bt.insertNode(26);
        bt.insertNode(108);
        bt.insertNode(79);
        bt.displayTree();
        Integer[] expected = {9,108,18};
        List answer = bt.wantedNodesRecursive(new Multiples(9));
        assertArrayEquals(expected, answer.toArray());
    }
     @Test
    public void MultiplesIterativeTest(){
        BinaryTree bt = new BinaryTree();
        bt.insertNode(10);
        bt.insertNode(9);
        bt.insertNode(18);
        bt.insertNode(26);
        bt.insertNode(79);
        Integer[] expected = {10,26,18};
        List answer = bt.wantedNodesIterative(new Multiples(2));
        assertArrayEquals(expected, answer.toArray());
    }
}
