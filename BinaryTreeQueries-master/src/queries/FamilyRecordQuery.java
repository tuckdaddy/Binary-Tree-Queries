
package queries;

import java.util.Iterator;
import readers.LineFileReader;
import iterators.Apply;
import iterators.ApplyFunction;
import binaryTree.BinaryTree;
import iterators.Predicate;
import iterators.ReduceFunction;
import java.io.IOException;
import java.util.List;


public class FamilyRecordQuery {
    public static void main(String[] args) throws IOException{
        Iterator<String> lines = new LineFileReader("../familyrecord.csv");
        Iterator<Object[]> recordsGeneric = new Apply<>(new ParseCSVLine(), lines);
        Iterator<FamilyRecord> records = new Apply<>(new ConvertToRecord(), recordsGeneric);        
        
        BinaryTree bt = new BinaryTree();
        BinaryTree nameBt = new BinaryTree();
        while(records.hasNext()){
            FamilyRecord record = records.next();
            nameBt.insertNode(record.name);
            bt.insertNode(record);
        }
        
        nameBt.displayTree();
        //bt.displayTree();
        //PART 3       
        String generation = "Generation 3: " + bt.combineValuesAtDepth(3, new ConcatenateNames());
        System.out.println(generation);

        int generationNumber = 4;
        String ageGroup = (String) nameBt.combineValuesAtDepthRecursive(generationNumber, new ConcatentateNamesRecursive(" "));
        System.out.println("Generation: " + generationNumber + " "  +  ageGroup);
        
        String generation1 = "Generation 1:" + bt.combineValuesAtDepth(1, new ConcatenateNames());
        System.out.println(generation1);

        //END PART 3
        
        //PART 5
        List<FamilyRecord> robertList = bt.wantedNodesIterative(new SelectName("Robert"));
        System.out.println("List of roberts: " +robertList);
        List<FamilyRecord> engineerList = bt.wantedNodesRecursive(new SelectJob("Engineer"));
        System.out.println("List of Engineers: "+engineerList);
        List<FamilyRecord> joeList = bt.wantedNodesIterative(new SelectName("Joe"));
        System.out.println("List of Joes: " + joeList);
        //END PART 5


        //Part 6
         List<FamilyRecord> under50;
         List<FamilyRecord> underN;
         under50 = bt.wantedNodesIterative(new Jungling(50));
         underN = bt.wantedNodesRecursive(new Jungling(24));

        //INSERT CODE Here

        System.out.println("This is family under N " + underN);
        System.out.println("This is family under 50 " + under50);
        
    }

    private static class ConcatenateNames implements ReduceFunction<FamilyRecord,String>{
        //PART 3        

        @Override
        public String combine(String soFar, FamilyRecord x) {
            //System.out.println(x);
                        return soFar+" "+x.name;
        }

        @Override
        public String initialValue() {
                        return "";
        }
        
    }

    private static class ConcatentateNamesRecursive implements ReduceFunction<String, String>{
        String g; 
        private ConcatentateNamesRecursive(String string) {
            this.g = string;
            
        }
        @Override
        public String combine(String bisher, String x) {
                return(bisher + x);
        }

        @Override
        public String initialValue() {
            return g;
        }

    }
  
    //PART 5 START
    private static class SelectName implements Predicate<FamilyRecord>{
        private String name;
        private SelectName(String name) {
            this.name = name;
        }
        @Override
        public boolean check(FamilyRecord x){
            //System.out.println(x.name);
            return(x.name.equals(name));
        }
        
        
    }
    
     private static class SelectJob implements Predicate<FamilyRecord>{
        private String name;
        private SelectJob(String name) {
            this.name = name;
        }
        @Override
        public boolean check(FamilyRecord x){
            return(x.job.contains(name));
        }
    }
    //PART 5 END
    private static class Jungling implements Predicate<FamilyRecord>{
        int year = 2017;
        private Jungling(Integer age){
            year = year - age;
        }
        @Override
        public boolean check(FamilyRecord d){
            
            return d.birthYear >= year;
        }
    }
    //PART 6 add new class here
    

    //////////////// Dont edit after here //////////////////////
    
    // Converts a CSV record from an Object[] to a FlightRecord
    private static class ConvertToRecord implements ApplyFunction<Object[], FamilyRecord> {
        @Override
        public FamilyRecord apply(Object[] r) {
            return new FamilyRecord((String)r[0],
                            (int)r[1], 
                            (String)r[2], 
                            (String)r[3],
                            (String)r[4]);
        }
    }
                
    private static class ParseCSVLine implements ApplyFunction<String, Object[]> {
        @Override
        public Object[] apply(String x) {
                String[] fields = x.split(",");
                Object[] r = new Object[fields.length];
                for (int i=0; i<fields.length; i++) {
                        // try to convert to integer
                        try {
                                r[i] = Integer.parseInt(fields[i]);
                        } catch (NumberFormatException ex) {
                                // if it fails, then leave a string
                                r[i] = fields[i];
                        }
                }
                return r;
        }
    }

    private static class FamilyRecord {
        public final String name;
        public final int birthYear;
        public final String city;
        public final String state;
        public final String job;
        
        private FamilyRecord(String n, int y, String c, String s, String j) {
            name = n;
            birthYear = y;
            city = c;
            state = s;
            job = j;
        }
        
        @Override
        public String toString(){
            return "Family record(Name=" + name + ", Birth Year=" + birthYear + ", City=" + city + ", State=" + state + ", Job=" + job + ")"; 
        }
        
    }

}
