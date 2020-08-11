package ar.edu.itba.grupo3.TP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CIM {

    private Map<Integer, Particle> heads;
    private List<Particle> allParticles;
    private int n; //total number of particles
    private float l; //total length
    private float rc; //Force radius
    private int m; //number of cells per side
    private final float cellSize;//size of cell
    private final boolean periodicEnvironment;
    private final boolean measureRadius;

    public Map<Integer, Particle> getHeads() { return heads; }

    public void setHeads(Map<Integer, Particle> heads) {  this.heads = heads; }

    public List<Particle> getAllParticles() { return allParticles; }

    public void setAllParticles(List<Particle> allParticles) { this.allParticles = allParticles; }

    public int getN() { return n; }

    public void setN(int n) { this.n = n; }

    public float getL() { return l; }

    public void setL(float l) { this.l = l; }

    public float getRc() { return rc; }

    public void setRc(float rc) { this.rc = rc; }

    public int getM() { return m; }

    public void setM(int m) { this.m = m; }

    public CIM(int n, float l, float rc, int m, boolean periodicEnvironment, boolean measureRadius) throws IllegalArgumentException {
        if(n <= 0 || l <= 0 || rc <= 0 || m <= 0) throw new IllegalArgumentException("incorrect arguments");
        if((l / m) <= rc) throw new IllegalArgumentException("No se cumple la condición 'l / m > rc'");
        this.heads = new TreeMap<>();
        this.allParticles = new ArrayList<>() {
            @Override
            public boolean add(Particle p){
                super.add(p);
                allParticles.sort(Comparator.comparing(Particle::getId));
                return true;
            }
        };
        this.n = n;
        this.l = l;
        this.rc = rc;
        this.m = m;
        this.cellSize = l/m;
        this.periodicEnvironment = periodicEnvironment;
        this.measureRadius = measureRadius;
    }

    public CIM(int m, float rc, boolean periodicEnvironment, boolean measureRadius, String path) throws IllegalArgumentException{
        if(path.isEmpty()) throw new IllegalArgumentException("empty path");
        if(m <= 0 || rc <= 0) throw new IllegalArgumentException("Wrong arguments");
        this.heads = new TreeMap<>();
        this.allParticles = new ArrayList<>() {
            @Override
            public boolean add(Particle p){
                super.add(p);
                allParticles.sort(Comparator.comparing(Particle::getId));
                return true;
            }
        };
        this.rc = rc;
        this.m = m;
        this.periodicEnvironment = periodicEnvironment;
        this.measureRadius = measureRadius;
        loadStaticFile(path);
        this.cellSize = l/m;
        if((l / m) <= rc) throw new IllegalArgumentException("No se cumple la condición 'l / m > rc'");
    }

    public void loadStaticFile(String path) {
        if(path.isEmpty()) return;
        File file = new File(path);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            this.n = Integer.parseInt(br.readLine());
            this.l = Integer.parseInt(br.readLine());
            //particles
            int index = 0;
            while( (s = br.readLine()) != null){
                String[] rad_prop = s.split(" {4}");
                this.allParticles.add(
                        new Particle(
                                Double.parseDouble(rad_prop[0]),
                                Double.parseDouble(rad_prop[1]),
                                index));
                index++;
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void loadDynamicFile(String path){
        if(path.isEmpty()) return;
        File file = new File(path);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            int time = Integer.parseInt(br.readLine()); //ignore first line
            //particles
            int index = 0;
            Particle aux;
            while( (s = br.readLine()) != null){
                String[] position = s.split(" {4}");
                aux = this.allParticles.get(index);
                aux.setX(Double.parseDouble(position[0]));
                aux.setY(Double.parseDouble(position[1]));
                putInCell(aux);
                index++;
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void putInCell(Particle p){
        int arrPos = getParticleCurrentCell(p);
        if(arrPos == -1) return;
        if(this.heads.get(arrPos) == null){
            this.heads.put(arrPos, p);
        }else{
            this.heads.get(arrPos).getParticlesFromCell().add(p);
        }
    }

    public List<Particle> getLShapeHeaders(int cell){
        List<Particle> neighborCells = new ArrayList<>();
        //if periodicEnvironment is set, apply modulus m to all calculations
        neighborCells.add(heads.get(cell));
        int aux = (periodicEnvironment) ? m : m * m;
        Particle p;
        //up
        if(cell + m < m * m){
            p = heads.get(cell + m % aux);
            if (p != null) neighborCells.add(heads.get(cell + m % aux));
        }
        //upper right
        if(cell + m + 1  < m * m){
            p = heads.get(cell + m + 1 % aux);
            if (p != null) neighborCells.add(heads.get(cell + m + 1 % aux));
        }
        //right
        if(cell + 1 < (cell / 10 + 1) * m){
            p = heads.get(cell+1 % aux);
            if ( p != null) neighborCells.add(heads.get(cell + 1 % aux));
        }
        //bottom right
        if(cell + 1 - m > 0){
            p = heads.get(cell+1-m % aux);
            if (p != null) neighborCells.add(heads.get(cell+1-m % aux));
        }
        return neighborCells;
    }

    public int getParticleCurrentCell(Particle p){
        if(p == null) return -1;
        int x = (int) (p.getX() / cellSize);
        int y = (int) (p.getY() / cellSize);
        return x + getM() * y;
    }

    public List<Integer> getParticleNeighborsIds(Particle p){
        return getLShapeNeighborParticles(p).stream().map(Particle::getId).collect(Collectors.toList());
    }

    public Set<Particle> getLShapeNeighborParticles(Particle p){
        //find out where it is
        if(p == null) return null;
        int cellNumber = getParticleCurrentCell(p);
        double deltaX, deltaY;
        double dist;
        double measureRadiusYesNo = (measureRadius) ? 1.0 : 0.0;
        List<Particle> neighborCells = getLShapeHeaders(cellNumber);
        for(Particle cellNeighbor : neighborCells){
            if(cellNeighbor == null) continue;
            for(Particle cellParticle : cellNeighbor.getParticlesFromCell()){
                deltaX = cellParticle.getX() - p.getX();
                deltaY = cellParticle.getY() - p.getY();
                dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY) -
                       ((cellParticle.getRadious() + p.getRadious()) * measureRadiusYesNo);
                //check if it's neighbor
                if(dist < getRc()){
                    p.getNeighbours().add(cellParticle);
                    cellParticle.getNeighbours().add(p);
                }
            }
        }
        return p.getNeighbours();
    }


    public void calculateNeighbors(){
        double radiusMultiplier = (measureRadius) ? 1.0 : 0.0;
        Particle currentCellHead;
        for(Integer cellNumber : getHeads().keySet()){
            currentCellHead = getHeads().get(cellNumber);
            for(Particle heads : getLShapeHeaders(cellNumber)){
                for(Particle possibleNeighbor : heads.getParticlesFromCell()){
                    for(Particle particleInCurrentCell : currentCellHead.getParticlesFromCell()){
                        addIfInRange(particleInCurrentCell, possibleNeighbor, radiusMultiplier);
                    }
                }
            }
        }
    }

    public void addIfInRange(Particle p1, Particle p2, double measureRadiusYesNo){
        if(p1.equals(p2)) return;
        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getY() - p1.getY();
        double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY) -
                ((p2.getRadious() + p1.getRadious()) * measureRadiusYesNo);
        //check if distance is withing rc
        if(dist < getRc()){
            p1.getNeighbours().add(p2);
            p2.getNeighbours().add(p1);
        }
    }

}
