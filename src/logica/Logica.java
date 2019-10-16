package logica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Partido;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logica {

    private static Logica INSTANCE = null;

    private ObservableList<Partido> listaPartidos;

    private Logica()
    {
        listaPartidos = FXCollections.observableArrayList();
        listaPartidos.add(new Partido("Madrid","Barsa","Primera",1,4,"09-11-2030"));
        listaPartidos.add(new Partido("Sporting","Oviedo","Segunda",3,1,"11-06-1997"));
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public void addPartido(Partido partido) {
        listaPartidos.add(partido);
    }


    public ObservableList<Partido> getListaPartidos() {
        return listaPartidos;
    }

    public void borrarPartido(int indiceBorrar) {
        listaPartidos.remove(indiceBorrar);
    }

    public void modificarPartido(Partido partidoM, int posicion) {
        listaPartidos.set(posicion,partidoM);
    }

    /**
    Scanner tec = new Scanner(System.in);


    public Logica() {

        //String path = "E:\\Ficheros\\DESIN\\GestorPartidos\\Temporada.dat"; casa
        String path = "C:\\Users\\HectorT\\Ficheros\\Temporada.dat";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        int numero;

        List<Partido> Temporada = new ArrayList<>();

        numero = opciones(tec);

        while (numero != 6) {

            switch (numero) {
                case 1:
                    darAlta(Temporada, path, tec, sdf);
                    numero = opciones(tec);
                    break;
                case 2:
                    mostrarPartido(Temporada, path);
                    numero = opciones(tec);
                    break;
                case 3:
                    borrarPartido(Temporada, path, tec);
                    numero = opciones(tec);
                    break;
                case 4:
                    mostrarPartidof(Temporada, path);
                    break;
                case 5:
                    mostrarPartidod(Temporada, path);
                    break;
                case 6:
                    break;
                default:
                    while (numero > 6 || numero < 1) {
                        System.out.println("Comando invalido introduzca otro");
                        numero = tec.nextInt();
                    }

            }


        }

    }
    public int opciones (Scanner tec) {

        System.out.println("Introduzca una de las siguientes opciones");
        System.out.println("1.Dar alta a un partido");
        System.out.println("2.Mostrar todos los partidos");
        System.out.println("3.Borrar un partido");
        System.out.println("4.Mostrar partidos ordenados por fecha");
        System.out.println("5.Mostrar todos los partidos de una division");
        System.out.println("6.Terminar programa");

        int num = tec.nextInt();

        return num;

    }

    public void darAlta(List<Partido> Temporada, String path, Scanner tec, SimpleDateFormat sdf) {

        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));

            String el;
            String ev;
            int div;
            String division = "";
            int res;
            Date fecha;

            System.out.println("Introduzca equipo local");
            el = tec.next();

            System.out.println("Introduzca equipo visitante");
            ev = tec.next();

            System.out.println("Introduzca division(1/2/3)");//Division d = Division.values()[seleccion*-1]*Valor leido por teclado
            div = tec.nextInt();
            switch (div) {
                case 1:
                    division = "Primera";
                    break;
                case 2:
                    division = "Segunda";
                    break;
                case 3:
                    division = "Tercera";
                    break;
                default:
                    while (div > 3 || div < 1) {
                        System.out.println("Division invalida introduzca una division valida(1/2/3)");
                        div = tec.nextInt();
                    }
            }

            System.out.println("Introduzca resultado");
            res = tec.nextInt();

            System.out.println("Introduzca fecha(dd/MM/yyyy)");
            String fech = tec.next();

            fecha = sdf.parse(fech);

            Partido p = new Partido(el, ev, division, res, fecha);
            Temporada.add(p);

            out.writeObject(Temporada);
            out.close();


        }
         catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void mostrarPartido(List<Partido> Temporada, String path) {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));

            int cont = 0;

            Temporada = (List<Partido>) in.readObject();

           for (Partido par : Temporada) {
                    System.out.println(cont + ".-" + par);
                    cont++;
            }

            in.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void borrarPartido(List<Partido> Temporada, String path, Scanner tec) {
        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));

            int pos;
            int cont=1;

            Temporada = (List<Partido>) in.readObject();

            Iterator<Partido> it = Temporada.iterator();

            System.out.println("Introduzca la posicion del partido");
            pos = tec.nextInt();

            Temporada.remove(pos);

            in.close();

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));

            out.writeObject(Temporada);
            out.flush();

            out.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }


    public void mostrarPartidof(List<Partido> Temporada, String path)
    {
        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));

            Temporada = (List<Partido>) in.readObject();

            //Iterator<Partido> it = Temporada.iterator();

            Collections.sort(Temporada);

            for (int i=0; i<Temporada.size(); i++)
            System.out.println(Temporada.get(i));


            in.close();

        }
        catch(FileNotFoundException e){
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        }
    }

    public void mostrarPartidod(List<Partido> Temporada, String path)
    {

        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));

            Temporada = (List<Partido>) in.readObject();

            Iterator<Partido> it = Temporada.iterator();

            System.out.println("Introduzca la division(1/2/3) que quiera ver");
            int div = tec.nextInt();

            switch (div) {
                case 1:
                    while(it.hasNext())
                    {
                        Partido p = it.next();

                        String d = p.getDivision();

                        if(d.equalsIgnoreCase("Primera") )
                        {
                            System.out.println(p);
                        }
                    }
                    break;
                case 2:
                    while(it.hasNext())
                    {
                        Partido p = it.next();

                        String d = p.getDivision();

                        if(d.equalsIgnoreCase("Segunda") )
                        {
                            System.out.println(p);
                        }
                    }
                    break;
                case 3:
                    while(it.hasNext())
                    {
                        Partido p = it.next();

                        String d = p.getDivision();

                        if(d.equalsIgnoreCase("Tercera") )
                        {
                            System.out.println(p);
                        }
                    }
                    break;
                default:
                    while (div > 3 || div < 1) {
                        System.out.println("Division invalida introduzca una division valida(1/2/3)");
                        div = tec.nextInt();
                    }
            }

            in.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
**/

    /**public static void main(String[] args) {

        Main interfaz = new Main();

    }**/

}

/**Date date = new Date();
 System.out.println(date);
 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");//
 // hh-->hora, mm-->minutos, ss-->segundos, dd-->dia, MM-->mes, yyyy-->año
 System.out.println(sdf.format(date));
 try {
 Date fecha = sdf.parse("9/11/2001");
 System.out.println(fecha);
 }
 catch (ParseException e)
 {

 }**/