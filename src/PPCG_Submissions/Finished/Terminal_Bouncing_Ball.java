// This Challenge: http://codegolf.stackexchange.com/questions/110410/8-bit-style-bouncing-ball-around-a-canvas

// Ungolfed
class Terminal_Bouncing_Ball_Ungolfed {
    public static void main(String[] args) throws InterruptedException {
	int X = 0, Y = 0, dx = 1, dy = 1;
	while (true) {
	    System.out.print(String.format("\033[%d;%dH",X,Y));
	    Thread.sleep(50);
	    dx = (X < 1) ? 1 : (X > 71) ? -1 : dx;
	    dy = (Y < 1) ? 1 : (Y > 237) ? -1 : dy;
	    X += dx;
	    Y += dy;
	}
    }
}

// Golfed @ 176 bytes; 85 x 25 region
class Terminal_Bouncing_Ball_Golfed{public static void main(String[]a)throws Exception{for(int X=1,Y=1,x=1,y=1;;System.out.print("\033["+X+";"+Y+"H"),Thread.sleep(50),X+=x=X%25<1?-x:x,Y+=y=Y%85<1?-y:y);}}

// Invoker
class Terminal_Bouncing_Ball {
    public static void main(String[]a) throws Exception {
	Terminal_Bouncing_Ball_Golfed Command = new Terminal_Bouncing_Ball_Golfed();
	Command.main(null);
    }
}
