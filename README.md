# imshubhy-prospectaFakeStoreApiCalls

# Question no 3: What are the key things you would consider when creating/consuming an API to ensure that it is secure and reliable?

Answer : 
Authentication and Authorization
Input Validation 
Encryption (HTTPS and Data Encryption)
Error Handling and Logging
API Versioning
Timeouts and Retries
Data Integrity (Atomicity)
Monitoring and Analytics



# Question no 4 : CSV input and Output processing

here is the code :


package com.prospecta;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SimpleCSVProcessor {
    
    private static Map<String, Double> cellValues = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
      
        String inputFile = "input.csv";
        String outputFile = "output.csv";
   
        processCSV(inputFile, outputFile);
        System.out.println("Processing completed.");
    }
    
 
    public static void processCSV(String inputFile, String outputFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        
        String line;
        int row = 1;
        
        while ((line = br.readLine()) != null) {
            String[] cells = line.split(",");
            StringBuilder processedLine = new StringBuilder();
            
            for (int i = 0; i < cells.length; i++) {
                String cellValue = cells[i].trim();
                String cellRef = (char) ('A' + i) + String.valueOf(row); 
                
               
                if (cellValue.startsWith("=")) {
                    double result = evaluateFormula(cellValue.substring(1)); 
                    cellValues.put(cellRef, result);
                    processedLine.append(result);
                } else {
         
                    double value = Double.parseDouble(cellValue);
                    cellValues.put(cellRef, value);
                    processedLine.append(value);
                }
                
                if (i < cells.length - 1) {
                    processedLine.append(",");
                }
            }
            bw.write(processedLine.toString());
            bw.newLine();
            row++;
        }
        
        br.close();
        bw.close();
        System.out.println("Writing CSV to: " + new File("output.csv").getAbsolutePath());
    }
    
    
    public static double evaluateFormula(String formula) {
       
        for (String cellRef : cellValues.keySet()) {
            formula = formula.replace(cellRef, String.valueOf(cellValues.get(cellRef)));
        }
        
        
        String[] parts = formula.split("\\+");
        double result = 0;
        for (String part : parts) {
            result += Double.parseDouble(part.trim());
        }
        return result;
    }
}


# How will you tackle the challenge above?
Answer: 

Read the CSV file and store the values in a 2D array or HashMap.
Check for formulas by identifying if a cell starts with "=".
Evaluate formulas by replacing cell references (e.g., A1) with actual values and computing the result.
Handle cell dependencies by evaluating referenced cells first.
Write the results (values or calculated formulas) into an output CSV file.
Test the program with different input cases, including valid formulas and edge cases like circular dependencies

# What type of errors would you check for?
Answer :

Invalid cell references (e.g., referencing a non-existent cell like A99).
Circular dependencies between cells (e.g., A1 references B1 and B1 references A1).
Invalid formula syntax (e.g., unsupported operations or incorrect format).
Empty or missing values in cells.
Division by zero in formulas (e.g., =A1/0).
Type mismatches (e.g., attempting to add a string value to a number).
File read/write errors (e.g., file not found, permission issues).

# How might a user break your code?
Answer:

Entering invalid formulas (e.g., =A1++B1 or unsupported operations).
Using non-existent cell references (e.g., =Z100+5 when Z100 doesn't exist).
Creating circular dependencies (e.g., A1 = B1 + 1 and B1 = A1 + 1).
Inputting strings in numeric formulas (e.g., =A1 + "hello").
Providing an empty or malformed CSV file.
Using division by zero in formulas (e.g., =A1/0).
Inputting a very large CSV file, leading to memory overflow or performance issues.

