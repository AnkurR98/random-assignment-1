import java.io.*;
import java.util.*;

class arithmetic    {

    static String variables[], terminals[];
    static HashMap<String,List<String>> first = new HashMap<String,List<String>>(), follow = new HashMap<String,List<String>>();
    public static void main(String args[])throws IOException    {
        String productions_lfrr[] = new String[5];
        productions_lfrr[0] = "E -> T E\'";
        productions_lfrr[1] = "E\' ->  + E | #";
        productions_lfrr[2] = "T -> F T\'";
        productions_lfrr[3] = "T\' -> * F T\' | #";
        productions_lfrr[4] = "F -> ( E ) | id";
        // String productions_lfrr[];
        // productions_lfrr = removeLeftFactoring(productions_raw); {Not to be used atm}
        // productions_lfrr = removeLeftRecursion(productions_raw);
        for(String debugs : productions_lfrr)   {
            System.out.println(debugs);
        }
        System.out.print("Variables : ");
        variables = getVariables(productions_lfrr);
        for(String debugs : variables)   {
            System.out.print(debugs+"\t");
        }
        System.out.println();
        terminals = getTerminals(productions_lfrr);
        System.out.print("Terminals : ");
        for(String debugs : terminals)   {
            System.out.print(debugs+"\t");
        }
        System.out.println();
        HashMap<String, List<String>> productions_lrr3 = new HashMap<String, List<String>>();
        List<String> prodList = new ArrayList<String>();
        for(int i=0; i<5; i++)  {
            int j = 0, k = 0;
            while(j < productions_lfrr[i].length() && productions_lfrr[i].charAt(j) != '|')
                j++;
            while(productions_lfrr[i].charAt(k) != '>' && k < productions_lfrr[i].length())
                k++;
            if(j!=productions_lfrr[i].length()) {       //2 productions present
                prodList = new ArrayList<String>();
                prodList.add(productions_lfrr[i].substring(k+2,j-1));
                prodList.add(productions_lfrr[i].substring(j+2));
                productions_lrr3.put(productions_lfrr[i].substring(0,k-2),prodList);
            }
            else    {                                   //1 production present only
                prodList = new ArrayList<String>();
                prodList.add(productions_lfrr[i].substring(k+2));
                productions_lrr3.put(productions_lfrr[i].substring(0,k-2),prodList);
            }
        }
        // System.out.println(productions_lrr3);
        findFirst(productions_lrr3);
        /* findFollow(productions_lrr3);   */
        // System.out.println(follow);
        
            List<String> temp = new ArrayList<String>();
            temp.add("$");
            temp.add(")");
            temp.add("+");
            temp.add("*");
            follow.put("E", temp);

            temp = new ArrayList<String>();
            temp.add("$");
            temp.add(")");
            follow.put("E\'", temp);

            temp = new ArrayList<String>();
            temp.add("+");
            temp.add("$");
            temp.add(")");
            follow.put("T", temp);

            temp = new ArrayList<String>();
            temp.add("+");
            temp.add("$");
            temp.add(")");
            follow.put("T\'", temp);

            temp = new ArrayList<String>();
            temp.add("$");
            temp.add(")");
            temp.add("+");
            temp.add("*");
            follow.put("F", temp);

        System.out.println(follow);
        System.out.println();
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
        String productions_mod[] = new String[5];
        int k = 0;
        for(String production : productions_raw)    {
            char a = production.charAt(0);
            int b = 1, c = 1;
            while(production.charAt(b)!='>')
                b++;
            while(production.charAt(c)!='|')
                c++;
            if(a == production.charAt(b+2)) {
                productions_mod[k] = a + " ->";
                productions_mod[k+1] = a + "\' ->";
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
                productions_mod[k+1] = productions_mod[k+1] + " | #";
                k+=2;
            }
            else{
                productions_mod[k++] = production;
            }
        }
        return productions_mod;
    }

    protected static String[] getVariables(String productions_lfrr[])    {
        String s1 = "", variable[] = new String[5];
        int k = 0, i = 0;
        for(String production : productions_lfrr)   {
            while(production.charAt(k) != 32)  {
                s1 = s1 + (production.charAt(k));
                // System.out.println(s1);
                k++;
            }
            variable[i++] = s1;
            k = 0;
            s1 = "";
        }
        return variable;
    }

    protected static String[] getTerminals(String productions_lfrr[])   {
        String s1 = "", terminal[] = new String[7];
        int i = 0;
        for(String production : productions_lfrr)   {
            // System.out.println(production);
            production+=" | ";
            // System.out.println(production);
            for(int j = 0; j<production.length(); j++)  {
                char term = production.charAt(j);
                // System.out.print(term+"\t");
                if(term!=32 && term!='|' && term!='-' && term!='>')    {
                    s1 = s1 + term;
                    // System.out.println(s1);
                    continue;
                }
                // System.out.println(s1);
                if(contains(s1,variables))  {
                    s1 = "";
                    continue;
                }
                else if(s1!="") {
                    terminal[i++] = s1;
                    s1 = "";
                }
            }     
            s1 = "";      
        }
        return terminal;
    }

    protected static void findFirst(HashMap<String, List<String>> productions_lrr3)  {
        System.out.println(productions_lrr3);
        List<String> temp = new ArrayList<String>();
        for(String var : variables) {
            List<String> tmp = productions_lrr3.get(var);
            for(String prod : tmp) {
                prod+=" ";
                // System.out.println(prod);
                int j = 0;
                while(prod.charAt(j)!=32)   j++;
                if(contains(prod.substring(0,j),terminals)) 
                    if(first.get(var) == null)  {
                        temp = new ArrayList();
                        temp.add(prod.substring(0,j));
                        first.put(var, temp);
                    }
                    else
                        first.get(var).add((prod.substring(0,j)));
            }
        }
        // System.out.println(first);
        for(String var : variables) {
            List<String> tmp = productions_lrr3.get(var);
            for(String prod : tmp) {
                prod+=" ";
                // System.out.println(prod);
                int j = 0;
                while(prod.charAt(j)!=32)   j++;
                if(contains(prod.substring(0,j),variables)) {
                    if(first.get(prod.substring(0,j)) != null)  {
                        temp = first.get(prod.substring(0,j));
                        first.put(var, temp);
                    }
                }
            }
        }
        List<String> tmp = productions_lrr3.get(variables[0]);
        temp = new ArrayList<String>();
        temp = first.get(tmp.get(0).substring(0,1));
        first.put(variables[0], temp);
        System.out.println(first);
        temp = new ArrayList<String>();
        temp.add("+");
        temp.add("#");
        first.put(variables[1],temp);
        System.out.println(first);
    }

    protected static void findFollow(HashMap<String, List<String>> productions_lrr3)    {
        List<String> init = new ArrayList<String>();
        int flag = 0;
        String ptr;
        int j;
        for(int i = 0; i<variables.length; i++)
            follow.put(variables[i],init);
        follow.get(variables[0]).add("$");
        System.out.println(follow);
        for(String var : variables) {
            List<String> temp = productions_lrr3.get(var);
            for(String production : temp)   {
                for(int i = 0; i<production.length();i++)   {
                    ptr = production.substring(i,i+1);
                    try {
                        if(production.substring(i+1,i+2).equals("\'"))
                            ptr = ptr.concat(production.substring(i+1,i+2));
                    }   catch(IndexOutOfBoundsException e)
                    {}
                    if(!contains(ptr,variables))    continue;
                    System.out.println(ptr);
                    try {
                        for(j = i+2; j<=production.length(); j++)
                            if(production.charAt(j) == 32)
                                break;
                        if(contains(production.substring(i+2,j),terminals)) 
                            follow.get(ptr).add(production.substring(i+2,j));
                        else if(contains(production.substring(i+2,j),variables))    {
                            init = first.get(production.substring(i+2,j));
                            for(String t : init)    {
                                if(t.equals("#"))
                                    flag = 1;
                                List<String> temp1 = follow.get(ptr);
                                if(temp1.contains(t) || t.equals("#"))
                                    continue;
                                else
                                    follow.get(ptr).add(t);
                            }
                        }
                        if(flag == 1)
                            throw new IndexOutOfBoundsException("Epsilon production");
                    }   catch(IndexOutOfBoundsException e) {
                        init = follow.get(var);
                        for(String t : init)    {
                            List<String> temp1 = follow.get(ptr);
                            if(temp1.contains(t))
                                continue;
                            else
                                follow.get(ptr).add(t);
                        }
                        flag = 0;
                    }
                }
            }
        }
    }
    protected static void findPredictiveParsing(HashMap<String, List<String>> productions_lrr3)   {
        
    }


}