
//To Implement Predictive Parsing
import java.io.*;
import java.util.*;

class manas {
    protected static char a[];
    protected static int top = -1, i;

    protected static void error() {
        System.out.println("Syntax Error");
    }

    protected static void push(String k) // Pushes The Set Of Characters on to the Stack
    {
        for (i = 0; i < k.length(); i++) {
            if (top < 9)
                a[++top] = k.charAt(i);
        }
    }

    protected static char TOS() // Returns TOP of the Stack
    {
        return a[top];
    }

    protected static void pop() // Pops 1 element from the Stack
    {
        if (top >= 0)
            a[top--] = '\0';
    }

    protected static void display() // Displays Elements Of Stack
    {
        for (i = 0; i <= top; i++)
            System.out.print(a[i]);
    }

    protected static void display1(char p[], int m) // Displays The Present Input String
    {
        int l;
        System.out.println("\t");
        for (l = m; p[l] != '\0'; l++)
            System.out.print( p[l]);
    }

    protected static char[] stack(){
        return a;
    }

public static void main(String args[])
{
    char st,an, ip[];
    String r;
    int ir,ic,j=0,k;
    char t[5][6][10]={"$","$","TH","$","TH","$",
            "+TH","$","e","e","$","e",
            "$","$","FU","$","FU","$",
            "e","*FU","e","e","$","e",
            "$","$","(E)","$","i","$"};
    System.out.println("\nEnter any String(Append with $)");
    ip  = new BufferedReader(new InputStreamReader(System.in)).readLine().toCharArray();   
    System.out.println("Stack\tInput\tOutput\n\n");
    push("$E");
    display();
    String rip = ip.toString();
    System.out.println("\t"+rip+"\n");
    for(j=0;ip[j]!='\0';)
    {
    if(TOS()==an)
        {
        pop();
        display();
        display1(ip,j+1);
        System.out.println("\tPOP\n");
        j++;
        }
        an=ip[j];
        st=TOS();
        if(st=='E')ir=0;
        else if(st=='H')ir=1;
        else if(st=='T')ir=2;
        else if(st=='U')ir=3;
        else if(st=='F')ir=4;
        else {
            error();
            break;
            }
        if(an=='+')ic=0;
        else if(an=='*')ic=1;
        else if(an=='(')ic=2;
        else if(an==')')ic=3;
        else if((an>='a'&&an<='z')||(an>='A'&&an<='Z')){ic=4;an='i';}
        else if(an=='$')ic=5;
        strcpy(r,strrev(t[ir][ic]));
        strrev(t[ir][ic]);
        pop();
        push(r);
        if(TOS()=='e')
        {
        pop();
        display();
        display1(ip,j);
        System.out.println("\t%c->%c\n",st,238);
        }
        else{
        display();
        display1(ip,j);
        System.out.println("\t%c->%s\n",st,t[ir][ic]);
        }
        if(TOS()=='$'&&an=='$')
        break;
        if(TOS()=='$'){
        error();
        break;
        }
        }
        k=strcmp(stack(),"$");
        if(k==0)
        System.out.println("\n Given String is accepted");
        else
        System.out.println("\n Given String is not accepted");
    return 0;
    }
}