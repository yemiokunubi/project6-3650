import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class HackAssembler{

  static ArrayList<String> fileInput = new ArrayList<String>();
  static int ramValue = 15;
     

  public static void firstPass(String path) throws FileNotFoundException{
    File file = new File(path);
    Scanner sc = new Scanner(file);
    
    int count = 0;
    while(sc.hasNextLine()){
      
      String line = sc.nextLine();
      String trimmed = line.trim();
      String firstElement = trimmed.split("")[0];
      if(firstElement.equals("") | firstElement.equals("/")){ //take out values that are not comments or whitespace
        
      }
      else{
        if(firstElement.equals("(") ){
          String insideParen = line.split("\\(")[1].split("\\)")[0]; //getting value inside parentheses
          Symbols.addValue(insideParen, count);

        }
        else{
          String trimming = line.trim();
          String newVal = trimming.split(" ")[0]; //get rid of lines that have comments at the end
          fileInput.add(newVal.trim());
          count++;
        }
      }
      
    }

  }

  public static String instructionType(String name){
    String trimmed = name.trim();
    String firstElement = trimmed.split("")[0];
    String instructionValue;
    if(firstElement.equals("@")){
      instructionValue = "A_INSTRUCTION";
    }
    else{
      instructionValue = "C_INSTRUCTION";
    }

    return instructionValue;

  }

  public static String computeA(String name){
    String symbolVal = name.split("@")[1]; //value after the @ sign
    if( isInt(symbolVal) == true){
      String trimmedBinary = convertToBinary(15, Integer.parseInt(symbolVal));
      return "0"+trimmedBinary;
    }
    else{
      boolean symbolNotExists =(Symbols.symbolTable.get(symbolVal)) == null;
      if(symbolNotExists == true){
        ramValue++;
        Symbols.addValue(symbolVal, ramValue);
        String trimmedBinary = convertToBinary(15, ramValue);
        return "0"+trimmedBinary;
        
      }
      else{
        int addressVal = Symbols.symbolTable.get(symbolVal);
        String trimmedBinary = convertToBinary(15, addressVal);
        return "0"+trimmedBinary;

      }
      // if(Symbols.symbolTable.get(symbolVal) == null);
    }


  }

  public static String computeC(String instruction){
    String bothSplits[] = instruction.split("[=;]");
    String a = "0";
    if(bothSplits.length == 3){
      String dest = bothSplits[0];
      String comp = bothSplits[1];
      String jump = bothSplits[2];
      if(comp.contains("M")){
        a = "1";
      }
      // CInstructionConvert.jump.get("jk");

      return "111" + a + CInstructionConvert.comp.get(comp) + CInstructionConvert.dest.get(dest) + CInstructionConvert.jump.get(jump);
    }
    else{
      String colon[] = instruction.split(";");
      String equals[] = instruction.split("=");

      if(colon.length == 2){
        String comp = colon[0];
        String jump = colon[1];
        String dest = "null";
        if(comp.contains("M")){
          a = "1";
        }
        // System.out.println("111" + a + CInstructionConvert.comp.get(comp) + CInstructionConvert.dest.get(dest) + CInstructionConvert.jump.get(jump));
        return "111" + a + CInstructionConvert.comp.get(comp) + CInstructionConvert.dest.get(dest) + CInstructionConvert.jump.get(jump);

      }
      else if(equals.length == 2){
        String dest = equals[0];
        String comp = equals[1];
        String jump = "null";
        if(comp.contains("M")){
          a = "1";
        }
        // System.out.println("111" + a + CInstructionConvert.comp.get(comp) + CInstructionConvert.dest.get(dest) + CInstructionConvert.jump.get(jump));
        return "111" + a + CInstructionConvert.comp.get(comp) + CInstructionConvert.dest.get(dest) + CInstructionConvert.jump.get(jump);

      }
      return "0";
    }

    
  }
  public static boolean isInt(String name){
    try
    {
        Integer.parseInt(name);
			return true;
    } catch (NumberFormatException ex)
    {
			return false;
    }
  }

  public static String convertToBinary(int bitLength, int preBinary){
    String binaryVal = Integer.toBinaryString(preBinary);
		String leadingZeros = "";
		for(int i = 0; i < bitLength-binaryVal.length(); i++){
			leadingZeros = leadingZeros + "0";
		}

    String binaryValExtended = leadingZeros+binaryVal;
    return binaryValExtended;

  }


  public static void createFile(String fileName) throws FileNotFoundException{
    try {
      File hFile = new File(fileName);
      if (hFile.createNewFile()) {
      } else {

      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public static void writeTofile(String fileName, String input, int i) throws FileNotFoundException{
    try {
      if(i==0){
        FileWriter myWriter = new FileWriter(fileName,false);
        myWriter.write(input);
        myWriter.close();
      }
      else{
        FileWriter myWriter = new FileWriter(fileName,true);
        myWriter.write(input);
        myWriter.close();
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }




  public static void main(String[] args) throws FileNotFoundException{

    String originalFile = args[0];
    String myFileNameNoPath = originalFile.split("\\.")[0] + ".hack";
    String folderName = args[1];
    String path = "/Users/yemi/Desktop/nand2tetris/projects/06/" +folderName +"/"+ originalFile;
    String myFileName = "/Users/yemi/Desktop/nand2tetris/projects/06/" + folderName + "/"+ myFileNameNoPath;
    createFile(myFileName);
    Symbols.main(args);  //initializes symbol table
    CInstructionConvert.main(args); //initialize c instruction table
    // Scanner sc = new Scanner(System.in);  
    // System.out.println("file path: ");
    // System.out.println(" /Users/yemi/Desktop/nand2tetris/projects/06/add/Add.asm ");
    // /Users/yemi/Desktop/nand2tetris/projects/06/Max/Max.asm
    // String path = sc.nextLine();
    // System.out.println("path is " + path);
    firstPass(path); //puts for loop labels in symbol table and takes out lines with whitespace and comments
    // System.out.println("hckbjc");
    // System.out.println(Symbols.symbolTable);

    
    for(int i = 0; i<fileInput.size(); i++){
      String currentLine = fileInput.get(i);
      // System.out.println(currentLine);


      if(instructionType(currentLine).equals("A_INSTRUCTION")){
        String finishedBinary = computeA(currentLine);
        writeTofile(myFileName, finishedBinary+"\n",i);
      }
      if(instructionType(currentLine).equals("C_INSTRUCTION")){
        String finishedBinary = computeC(currentLine);
        writeTofile(myFileName, finishedBinary+"\n",i);
      }

    }


  }

  

}
