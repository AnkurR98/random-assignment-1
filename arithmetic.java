import java.io.*;
import java.util.*;

class arithmetic    {
    public static void main(String args[])throws IOException    {
        String productions_raw[] = new String[3];
        productions_raw[0] = "E -> T + E | T";
        productions_raw[1] = "T -> T * F | F";
        productions_raw[2] = "F -> ( E ) | id";
        String productions_lfrr[];
        // productions_lfrr = removeLeftFactoring(productions_raw); {Not to be used atm}
        productions_lfrr = removeLeftRecursion(productions_raw);
        for(String debugs : productions_lfrr)   {
            System.out.println(debugs);
        }
        System.out.print("Variables : ");
        String variables[] = getVariables(productions_lfrr);
        for(String debugs : variables)   {
            System.out.print(debugs+"\t");
        }
        String terminals[] = getTerminals(productions_lfrr);
        System.out.print("Terminals : ");
        for(String debugs : terminals)   {
            System.out.print(debugs+"\t");
        }
    }

    protected static boolean contains(String a, String b[]) {
        List<String> lst = Arrays.asList(b);
        if(lst.contains(a))
            return true;
        else
            return false;
    }

    // Under Developement
    //protected static String removeLeftFactoring(String[] productions_raw)  {

    // }

    protected static String[] removeLeftRecursion(String productions_raw[])   {
        String productions_mod[] = new String[4];
        int k = 0;
        for(String production : productions_raw)    {
            char a = production.charAt(0);
            int b = 1, c = 1;
            while(production.charAt(b)!='>')
                b++;
            while(production.charAt(c)!='|')
                c++;
            if(a == production.charAt(b+2)) {
                productions_mod[k] = a + " -> ";
                productions_mod[k+1] = a + "\' -> ";
                // Following code is for general grammars, I guess. Not required for current grammar
                // String inpString[][] = segregate(production, a);
                // for(int i = 0; i<inpstring[0].length; i++)
                //     productions_mod[k] = productions_mod[k] + inpstring[0][i] + " " + a + "\' " + "|";
                // for(int i = 0; i<inpstring[1].length; i++)
                //     productions_mod[k+1] = productions_mod[k+1] + inpstring[1][i] + " " + a + "\' " + "|";
                // productions_mod[k+1] = productions_mod[k+1] + " ε ";
                // k+=2;
                productions_mod[k] = productions_mod[k] + production.substring(c+1)  + " "  + a + "\'";
                productions_mod[k+1] = productions_mod[k+1] + production.substring(b+3,c-1)  + " "  + a + "\'";
                productions_mod[k+1] = productions_mod[k+1] + " | ε";
                k+=2;
            }
            else{
                productions_mod[k++] = production;
            }
        }
        return productions_mod;
    }

    protected static String[] getVariables(String productions_lfrr[])    {
        String s1 = "", variables[] = new String[4];
        int k = 0, i = 0;
        for(String production : productions_lfrr)   {
            while(production.charAt(k) != 32)  {
                s1 = s1 + (production.charAt(k));
                // System.out.println(s1);
                k++;
            }
            variables[i++] = s1;
            k = 0;
            s1 = "";
        }
        return variables;
    }

    protected static String[] getTerminals(String productions_lfrr[])   {
        String s1 = "", variables[] = new String[4];
        int k = 0, i = 0;
        for(String production : productions_lfrr)   {
            for(char term : production.toCharArray())  {
                while(term!=32 || term!='|' || term!='-' || term!='>')    {
                    s1 = s1 + term;
                }
                // System.out.println(s1);
                
            }
            variables[i++] = s1;
            k = 0;
            s1 = "";
        }
        return variables;
    }

}
