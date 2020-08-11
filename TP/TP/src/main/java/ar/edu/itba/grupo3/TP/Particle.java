package ar.edu.itba.grupo3.TP;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Particle implements Comparable<Particle> {
    private Double x; //x position of particle
    private Double y; //y position of particle
    private Integer id; //id of particle
    private Double radius; //radius of particle
    private Double property; //value of property
    private Set<Particle> neighbours; //list of neighbours

    //Vamos a tener una lista de particulas general, la primer particula de la lista hace referencia a la particula "padre" ubicada en el casillero cero
    //la segunda particula del array hace referencia a la particula "padre" ubicada en el segundo casillero del tablero....
    //"padre" llamamos a la primer particula que esta ubicada en ese casillero. el next de esa particula, hace refernecia
    //a otra particula ubicada en el mismo casillero
    private List<Particle> particlesSameCellList;

    public Particle(Double x, Double y, Double radius, Double property) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.property = property;
        this.neighbours = new TreeSet<>();
        this.particlesSameCellList = new LinkedList<>();
        this.particlesSameCellList.add(this);
    }

    public Particle(Double radius, Double property, Integer id){
        this.radius = radius;
        this.property = property;
        this.id = id;
        this.x = -1.0; //placeholder
        this.y = -1.0; //placeholder
        this.neighbours = new TreeSet<>();
        this.particlesSameCellList = new LinkedList<>();
        this.particlesSameCellList.add(this);
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

    public void setNeighbours(Set<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    public void setParticlesSameCellList(List<Particle> particlesSameCellList) {
        this.particlesSameCellList = particlesSameCellList;
    }

    public Double getProperty() {
        return property;
    }

    public void setProperty(Double property) {
        this.property = property;
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

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public List<Particle> getParticlesFromCell() {
        return particlesSameCellList;
    }

    public String toString(){
        return this.getId().toString();
    }

    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass()) return false;
        if(o == this) return true;
        Particle p = (Particle) o;
        return this.id.equals(p.id);
    }

    @Override
    public int compareTo(Particle particle) {
        return this.getId().compareTo(particle.getId());
    }
}
