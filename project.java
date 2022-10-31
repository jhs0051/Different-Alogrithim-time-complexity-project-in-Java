import java.util.Scanner;
import java.io.File;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

public class project {
   // Function to calculate max between two ints    
    public int max(int int1, int int2) {
      if (int1 >= int2) {
         return int1;
      }
      else {
         return int2;
      }
   }    
   
   // Function to calculate max between three ints
   public int max(int int1, int int2, int int3) {
      if (int1 >= int2 && int1 >= int3) {
         return int1;
      }
      else if (int2 >= int1 && int2 >= int3) {
         return int2;
      }
      else {
         return int3;
      }
   } 

   // Algorithm-1(X : array[P..Q] of integer)
   public int algorithm_1(int[] X) {
      int P = 0; int Q = X.length; int maxSoFar = 0;
      for (int L = P; L <= Q; L++) {
         for (int U = L; U <= Q; U++) {
            int sum = 0;
            for (int I = L; I < U; I++) {
               sum = sum + X[I];
               /* sum now contains the sum of X[L..U] */
            }
            maxSoFar = max(maxSoFar, sum);
         } 
      } 
      return maxSoFar;
   }
   
   // Algorithm-2(X : array[P..Q] of integer)
   public int algorithm_2(int[] X) {
      int P = 0; int Q = X.length; int maxSoFar = 0;
      for (int L = P; L <= Q; L++) {
         int sum = 0;
         for (int U = L; U < Q; U++) {
            sum = sum + X[U];
            /* sum now contains the sum of X[L..U] */
            maxSoFar = max(maxSoFar, sum);
         }
      }
      return maxSoFar;
   }
   
   // Algorithm-3 recursive function MaxSum(X[L..U]: array of integer, L, U: integer)
   public int maxSum(int[] X, int L, int U) {
      if (L > U) {
         return 0; /* zero-element vector */
      }
      if (L == U) {
         return max(0, X[L]); /* one-element vector */
      }
      int M = ((L + U) / 2); /* A is X[L..M], B is X[M+1..U] */
      /* Find max crossing to left */
      int sum = 0;
      int maxToLeft = 0;
      for (int I = M; I >= L; I--) {
         sum = sum + X[I];
         maxToLeft = max(maxToLeft, sum);
      }
      /* Find max crossing to right */
      sum = 0;
      int maxToRight = 0;
      for (int I = M + 1; I <= U; I++) {
         sum = sum + X[I];
         maxToRight = max(maxToRight, sum);
      }
      int maxCrossing = maxToLeft + maxToRight; 
      int maxInA = maxSum(X, L, M);
      int maxInB = maxSum(X, M + 1, U);
      return max(maxCrossing, maxInA, maxInB);
   }  
   
   // Algorithm-4(X : array[P..Q] of integer)
   public int algorithm_4(int[] X) {
      int P = 0; int Q = X.length;
      int maxSoFar = 0;
      int maxEndingHere = 0;
      for (int I = P; I < Q; I++) {
         maxEndingHere = max(0, maxEndingHere + X[I]);
         maxSoFar = max(maxSoFar, maxEndingHere);
      }
      return maxSoFar; 
   }   

   /* Starting main function that usses scanner function to read data from a phw_input.txt file as instructed
    * by the project instructions. The phw file I'm using to tests contains 10 random ints both positive, negative, and zero
    * seperated by commas, like the instructions say to. The program then reads the file and stores the 10 ints in an int arrary.
    * The program will then use the arrary to run through the 4 algorithms provided by the instructions. 
    * The program then outputs the data to the console following the format provided. 
    * If no file is found, program will throw the file not found exception and end. */
   public static void main(String[] args) throws FileNotFoundException, IOException {
      
      // Create PHW File. Used https://www.w3schools.com/java/java_files_create.asp to learn how to create a file in java
      File phwInput = new File("phw_input.txt");
      // Stores values in array
      int[] phwArr = new int[10];
      // Uses scanner to scan array through input file
      Scanner scanner = new Scanner(phwInput);
      String string = scanner.nextLine();
      // Using a for loop to check ints from file until there is no longer any ints seperated by commas
      for (int i = 0; i < phwArr.length; i++) {
         /* Used this source to figure out how to see if string contains a comma in order to keep scanning the input file 
         * https://www.w3schools.com/java/ref_string_contains.asp */
         if (string.contains(",")) {
            String intVal = string.substring(0, string.indexOf(","));
            int data = Integer.parseInt(intVal);
            string = string.substring(string.indexOf(",") + 1, string.length());
            phwArr[i] = data;
         }
         else {
            String intVal = string.substring(0, string.length());
            int data = Integer.parseInt(intVal);
            phwArr[i] = data;
         }
      }  
      scanner.close();
      
      // Intializes sequence of 19 integers 
      int count = 0;
      int[][] arrMatrix = new int[19][];
      // Using for loop to create the 19 arrays of lengths 10-100 incremented by 5
      for (int i = 10; i <= 100; i += 5) {
         int[] valArry = new int[i];
         /* Used stackoverflow to see how to generate random ints for both negative positive and zero values
         https://stackoverflow.com/questions/3938992/how-to-generate-random-positive-and-negative-numbers-in-java */
         for (int j = 0; j < i; j++) {
            Random rand = new Random();
            int ranInt = (rand.nextInt(65536)-32768);
            // Store the random ints generated.
            valArry[j] = ranInt;
         }
         arrMatrix[count] = valArry;
         count++;
      } 
      
      project MSCS = new project();
      
      // Array matrix to store average execution times and calculated complexity 
      long[][] timeMat = new long[19][18];
      
      /* Using nanoseconds for each algorithm's run as suggested by instructions to get accurate data 
       * used this website to find out how to implement the nano second method in my program
       * https://www.tutorialspoint.com/how-to-measure-execution-time-for-a-java-method */

      // Execute first algorithm 
      for (int i = 0; i < 19; i++) {
         long finalTime = 0;
         long initialTime = System.nanoTime();
         for (int j = 0; j < 1000; j++) {
            MSCS.algorithm_1(arrMatrix[i]);
            long endTime = System.nanoTime();
            long timePassed = (endTime - initialTime);
            finalTime += timePassed;
         }
         // Stores average data in matrix
         long avg = (finalTime / 1000);
         timeMat[i][0] = avg;
      }
      
      // Execute second algorithm
      for (int i = 0; i < 19; i++) {
         long finalTime = 0;
         long initialTime = System.nanoTime();
         for (int j = 0; j < 1000; j++) {
            MSCS.algorithm_2(arrMatrix[i]);
            long endTime = System.nanoTime();
            long timePassed = (endTime - initialTime);
            finalTime += timePassed;
         }
         // Stores average data in matrix
         long avg = (finalTime / 1000);
         timeMat[i][1] = avg;
      } 
      
      // Execute third algorithm
      for (int i = 0; i < 19; i++) {
         long finalTime = 0;
         long initialTime = System.nanoTime();
         for (int j = 0; j < 1000; j++) {
            MSCS.maxSum(arrMatrix[i], 0, arrMatrix[i].length - 1);
            long endTime = System.nanoTime();
            long timePassed = (endTime - initialTime);
            finalTime += timePassed;
         }
         // Stores average data in matrix
         long avg = (finalTime / 1000);
         timeMat[i][2] = avg;
      }
      
      // Execute fourth algorithm
      for (int i = 0; i < 19; i++) {
         long finalTime = 0;
         long initialTime = System.nanoTime();
         for (int j = 0; j < 1000; j++) {
            MSCS.algorithm_4(arrMatrix[i]);
            long endTime = System.nanoTime();
            long timePassed = (endTime - initialTime);
            finalTime += timePassed;
         }
         // Stores average data in matrix
         long avg = (finalTime / 1000);
         timeMat[i][3] = avg;
      }  
      
      // calculates T1-4(n) based off my calculations from the instruction table
      int timeArr = 0;
      for (int i = 10; i <= 100; i += 5) {
         timeMat[timeArr][4] = (long) Math.ceil(Math.pow(i, 3) * 1000); 
         timeMat[timeArr][5] = (long) Math.ceil(Math.pow(i, 2) * 1000);
         timeMat[timeArr][6] = (long) Math.ceil(Math.pow(i, 2) * 1000);
         timeMat[timeArr][7] = (long) Math.ceil(i * 1000);
         timeArr++;
      } 
      
      // This segement uses an arraylist to store the matrix array data in order to write to the output file
      ArrayList<String> dataOutput = new ArrayList<String>();
      // Format for 19X8 matrix
      for (int i = 0; i < 19; i++) {
         String formatOutput = "";
         for (int j = 0; j <= 6; j++ ) {
            formatOutput += timeMat[i][j] + ",";
         }
         // Store data in matrix        
         formatOutput += timeMat[i][7];
         dataOutput.add(formatOutput);
      }
      
      // Prints the MSCS as determined by each of the algorithms to console window
      System.out.println("algorithm-1: " + MSCS.algorithm_1(phwArr));
      System.out.println("algorithm-2: " + MSCS.algorithm_2(phwArr));
      System.out.println("algorithm-3: " + MSCS.maxSum(phwArr, 0, 9));
      System.out.println("algorithm-4: " + MSCS.algorithm_4(phwArr));

      /* Used this source on how to use FileWriter method in order to write the outputs to the output file
       * https://www.geeksforgeeks.org/file-handling-java-using-filewriter-filereader/ */
      try {
        FileWriter writer = new FileWriter("JonathanSeibert_phw_output.txt");
        writer.write("algorithm-1,algorithm-2,algorithm-3,algorithm-4,T1(n),T2(n),T3(n),T4(n)");
         for (String dataString : dataOutput) {
            writer.write("\n" + dataString);
         }
         writer.close();
      }
      // Using IOEception method to print error message in case an exception is thrown
      catch (IOException exceptionMsg) {
         System.out.println("Could not write data to output file.");
      }                               
   }                  
}
