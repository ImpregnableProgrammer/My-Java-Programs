// This Challenge: https://codegolf.stackexchange.com/questions/113764/how-many-holes
// NOT Finished
import java.io.*;
class Hole_Counter {
    void Counter(String[]a) {
	int y=0,x=0;
	char T='A';
	for(int f=0;f<a.length&&T!=' ';a[y]=T==' '?a[y].substring(0,x)+'@'+a[y].substring(x+1):a[y],++f){for(int g=0;g<a[f].length()&&T!=' ';T=a[f].charAt(g),y=f,x=g,++g);}
	//System.out.println(a[1]);
	boolean P=false;
	for(;;){int Q=y,I=x;for(int D=-1;D<2;++D){for(int E=-1;E<2;++E){P=a[y+D].charAt(x+E)==' ';x+=P?E:0;;y+=P?D:0;if(P){a[y]=a[y].substring(0,x)+'@'+a[y].substring(x+1);break;}}}if(y==Q&&x==I){break;}}
	//System.out.println(y+","+x);
	for(String H:a) {
	    System.out.println(H);
	}
    }
    
    public static void main(String[]a) {
	Hole_Counter G=new Hole_Counter();
	G.Counter(a);
    }
}
