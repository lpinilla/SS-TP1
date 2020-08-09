package ar.edu.itba.grupo3.TP;

import java.util.List;

public class Particle {
    double x; //x posicion of particle
    double y; //y posicion of particle
    int id; //id of particle
    double radius; //radius of particle
    List<Particle> neighbours; //list of neighbours

    //Vamos a tener una lista de particulas general, la primer particula de la lista hace referencia a la particula "padre" ubicada en el casillero cero
    //la segunda particula del array hace referencia a la particula "padre" ubicada en el segundo casillero del tablero....
    //"padre" llamamos a la primer particula que esta ubicada en ese casillero. el next de esa particula, hace refernecia
    //a otra particula ubicada en el mismo casillero
    List<Particle> next;

    public Particle(double x, double y, double radious) {
        this.x = x;
        this.y = y;
        this.radius = radious;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setNeighbours(List<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    public void setNext(List<Particle> next) {
        this.next = next;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public double getRadious() {
        return radius;
    }

    public List<Particle> getNeighbours() {
        return neighbours;
    }

    public List<Particle> getNext() {
        return next;
    }
}
