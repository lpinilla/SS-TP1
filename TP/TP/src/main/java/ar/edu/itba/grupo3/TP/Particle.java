package ar.edu.itba.grupo3.TP;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Particle {
    Double x; //x position of particle
    Double y; //y position of particle
    Integer id; //id of particle
    Double radius; //radius of particle
    Double property; //value of property
    List<Particle> neighbours; //list of neighbours

    //Vamos a tener una lista de particulas general, la primer particula de la lista hace referencia a la particula "padre" ubicada en el casillero cero
    //la segunda particula del array hace referencia a la particula "padre" ubicada en el segundo casillero del tablero....
    //"padre" llamamos a la primer particula que esta ubicada en ese casillero. el next de esa particula, hace refernecia
    //a otra particula ubicada en el mismo casillero
    List<Particle> particlesSameCellList;

    public Particle(Double x, Double y, Double radius, Double property) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.property = property;
        this.neighbours = new LinkedList<>();
        this.particlesSameCellList = new LinkedList<>();
    }

    public Particle(Double radius, Double property, Integer id){
        this.radius = radius;
        this.property = property;
        this.id = id;
        this.x = -1.0; //placeholder
        this.y = -1.0; //placeholder
        this.neighbours = new LinkedList<>();
        this.particlesSameCellList = new LinkedList<>();
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public void setNeighbours(List<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    public void setParticlesSameCellList(List<Particle> particlesSameCellList) {
        this.particlesSameCellList = particlesSameCellList;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Integer getId() {
        return id;
    }

    public Double getRadious() {
        return radius;
    }

    public List<Particle> getNeighbours() {
        return neighbours;
    }

    public List<Particle> getParticlesSameCellList() {
        return particlesSameCellList;
    }

}
