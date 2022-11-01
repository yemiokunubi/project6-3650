import java.util.HashMap;

public class CInstructionConvert {

  static HashMap<String,String> comp = new HashMap<>();
  static HashMap<String,String> dest = new HashMap<>();
  static HashMap<String,String> jump = new HashMap<>();

  public static void compTable(){
    comp.put("0", "101010");
    comp.put("1", "111111");
    comp.put("-1","111010" );
    comp.put("D", "001100");
    comp.put("A", "110000");
    comp.put("!D", "001101");
    comp.put("!A", "110001");
    comp.put("-D", "001111");
    comp.put("-A","110011" );
    comp.put("D+1", "011111");
    comp.put("A+1", "110111");
    comp.put("D-1", "001110");
    comp.put("A-1","110010" );
    comp.put("D+A","000010" );
    comp.put("D-A", "010011");
    comp.put("A-D", "000111");
    comp.put("D&A", "000000");
    comp.put("D|A", "010101");


    comp.put("M", "110000");
    comp.put("!M", "110001");
    comp.put("-M", "110011");
    comp.put("M+1", "110111");
    comp.put("M-1","110010" );
    comp.put("D+M", "000010");
    comp.put("D-M","010011" );
    comp.put("M-D", "000111");
    comp.put("D&M", "000000");
    comp.put("D|M","010101" );



  }

  public static void destTable(){
    dest.put("null","000" );
    dest.put("M","001" );
    dest.put("D","010" );
    dest.put("DM","011" );
    dest.put("MD","011" );
    dest.put("A","100" );
    dest.put("AM","101" );
    dest.put("MA","101" );
    dest.put("AD","110" );
    dest.put("DA","110" );
    dest.put("ADM","111" );
    
  }

  public static void jumpTable(){
    jump.put("null","000" );
    jump.put("JGT","001" );
    jump.put("JEQ","010" );
    jump.put("JGE","011" );
    jump.put("JLT","100" );
    jump.put("JNE","101" );
    jump.put("JLE","110" );
    jump.put("JMP","111" );
  }

  public static String tableValues(String tabName, String keyVal){
    return jump.get("jk");
  }

  public static void main(String[] args) {
    //initializing the tables
    compTable();
    destTable();
    jumpTable();


  }
  
}
