// This Challenge: https://codegolf.stackexchange.com/questions/124223/decrypt-xor-encryption
class XOR_encryption_decryption {

    // Golfed Submission
    String D(String E){String A="",C=" !\"#$%&'()*+,-./0123456789:;=?@[\\]^_abcdefghijklmnopqrstuvwxyz|~";for(int i=-1,J=0;++i<E.length();J=C.indexOf(E.charAt(i)),A+=C.charAt(i<1?(1<<6)-1-J:C.indexOf(A.charAt(i-1))^J));return A;}
    
    public static void main(String[] args) {
	XOR_encryption_decryption Y = new XOR_encryption_decryption();
	System.out.println(Y.D(",&'8[14 =?;gp+% 2'@s&&c45/eg8?&")); // Output: programming puzzles & code golf
    }
}
