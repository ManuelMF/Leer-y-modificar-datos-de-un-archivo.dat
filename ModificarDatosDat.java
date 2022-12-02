package a02FicherosJavaNIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.Scanner;

public class a30Ejercicio15b {

	static Scanner sc = new Scanner(System.in);
    static RandomAccessFile fichero = null;
	
	public static void main(String[] args) throws IOException {
		
		try {
			fichero = new RandomAccessFile("RegistroAleatorio.dat", "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Error creando el RandomAccessFile de RegistroAleatorio.dad");
		}
		
		menu();
	}
	
	public static void menu() throws IOException {
		
		String res;
		boolean salir = false;
		
		do {
			System.out.println("Que quieres hacer:");
			System.out.println("1) Mirar todos los registros");
			System.out.println("2) Mirar un registro");
			System.out.println("3) Añadir registros");
			System.out.println("4) Salir");
			res = sc.nextLine();

			if (res.equals("1")) {
				mostrarFichero();
			} else if (res.equals("2")) {
				System.out.print("Numero de registro: ");
				mostrar1Registro(Integer.parseInt(sc.nextLine()));
			} else if (res.equals("3")) {
				AnyadirRegistro();
				
			} else if (res.equals("4")) {
				fichero.close();
				salir = true;
			}
		} while(salir == false);
	}

	public static void mostrarFichero() throws IOException  {
		
		fichero.seek(0);
		for (;;) {
		
			leerfila();
			
			if (fichero.getFilePointer() == fichero.length()) {
				break;
			}
		}
	}
	
	public static void mostrar1Registro(int numeroReg) throws IOException {

		BigDecimal bytesPos = new BigDecimal(0), bytes = new BigDecimal(92), line = new BigDecimal(numeroReg);		
	    
	    bytesPos = bytes.multiply(line).subtract(new BigDecimal(92));
	    
	    fichero.seek(bytesPos.intValue());
	    
	    leerfila();
	}
	
	public static void leerfila() {
		
		int numRegistro = 0, edad = 0, telefono = 0;
		String nombre = null, apellido = null;
		char charNombre[] = new char[15], charApellido[] = new char[25];
		
		try {
			numRegistro = fichero.readInt();

			for (int i = 0; i < charNombre.length; i++) {
				charNombre[i] = fichero.readChar();
			}
			nombre = new String(charNombre).trim();

			for (int i = 0; i < charApellido.length; i++) {
				charApellido[i] = fichero.readChar();
			}
			apellido = new String(charApellido).trim();

			edad = fichero.readInt();

			telefono = fichero.readInt();
			
			System.out.println("Registro num: " + numRegistro + " Nombre: " + nombre + " Apellido: " 
	                   + apellido  + " Edad: " + edad + " Teléfono: " + telefono);
			
		} catch (IOException e) {
			System.out.println("Error Buscando registro");
		}

	}
	
	public static void AnyadirRegistro() {
		
		StringBuffer nombre, apellido;
		int numRegistro, edad, telefono;
		
		System.out.print("Número de registro: ");
		numRegistro = buscarNumeroRegistro();
		System.out.println(numRegistro);

		System.out.print("Nombre: ");
		nombre = new StringBuffer(sc.nextLine());
		nombre.setLength(15);

		System.out.print("Apellido: ");
		apellido = new StringBuffer(sc.nextLine());
		apellido.setLength(25);

		System.out.print("Edad: ");
		edad = Integer.parseInt(sc.nextLine());

		System.out.println("Teléfono: ");
		telefono = Integer.parseInt(sc.nextLine());

		introducirDato(numRegistro, nombre, apellido, edad, telefono);
	}
	
	public static void introducirDato(int numRegistro, StringBuffer nombre, StringBuffer apellido, int edad, int telefono)  {
		
		try {
			fichero.writeInt(numRegistro);
			fichero.writeChars(nombre.toString());
			fichero.writeChars(apellido.toString());
			fichero.writeInt(edad);
			fichero.writeInt(telefono);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static int buscarNumeroRegistro()  {
		
		BigDecimal bytes = new BigDecimal(0), bytesLine = new BigDecimal(92), res = new BigDecimal(0);		
	    
//	    coger número de bytes
	    try {
			bytes = new BigDecimal(fichero.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    res = bytes.divide(bytesLine);

		return res.intValue()+1;
	}

	
}
	