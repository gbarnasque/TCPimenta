import java.util.*;

public class Main{
	public static void main(String[] args) {
		var t = new Teste();
		t.setVarteste1(7);
		t.setVarteste2(90);
		System.out.print(t.getVarteste1() + " multiplicado por " + t.getVarteste2() + " eh igual a ");
		System.out.println(t.getMultiplicacao());
	}
}

class Teste{
	private static int teste1;
	private static int teste2;

	public static void setVarteste1(int numero){
		teste1 = numero;
	}
	public static int getVarteste1(){
		return teste1;
	}

	public static void setVarteste2(int numero){
		teste2 = numero;
	}
	public static int getVarteste2(){
		return teste2;
	}

	public static int getMultiplicacao(){
		return multiplicaVarteste1Varteste2();
	}

	private static int multiplicaVarteste1Varteste2(){
		return (teste1 * teste2);
	}
}