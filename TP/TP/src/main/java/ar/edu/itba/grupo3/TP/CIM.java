package ar.edu.itba.grupo3.TP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CIM {

    private List<Particle> heads; //all the matrix
    private List<Particle> allParticles;
    private int n; //total number of particles
    private float l; //total length
    private float rc; //Force radius
    private int m; //number of cells per side
    private float cellSize;//size of cell
    private boolean periodicEnvironment;
    private boolean measureRadius;

    public List<Particle> getHeads() { return heads; }

    public void setHeads(List<Particle> heads) {  this.heads = heads; }

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

    private void populateArrays(){
        for(int i = 0; i < n; i++){
            this.heads.add(null);
        }
    }


    public CIM(int n, float l, float rc, int m, boolean periodicEnvironment, boolean measureRadius) throws IllegalArgumentException {
        if(n <= 0 || l <= 0 || rc <= 0 || m <= 0) throw new IllegalArgumentException("incorrect arguments");
        if((l / m) <= rc) throw new IllegalArgumentException("No se cumple la condición 'l / m > rc'");
        this.heads = new ArrayList<>();
        this.allParticles = new ArrayList<>() {
            @Override
            public boolean add(Particle p){
                super.add(p);
                allParticles.sort(Comparator.comparing((Particle p2) -> p2.getId()));
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
        populateArrays();
    }

    public CIM(int m, float rc, boolean periodicEnvironment, boolean measureRadius, String path) throws IllegalArgumentException{
        if(path.isEmpty()) throw new IllegalArgumentException("empty path");
        if(m <= 0 || rc <= 0) throw new IllegalArgumentException("Wrong arguments");
        this.heads = new ArrayList<>();
        this.allParticles = new ArrayList<>() {
            @Override
            public boolean add(Particle p){
                super.add(p);
                allParticles.sort(Comparator.comparing((Particle p2) -> p2.getId()));
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
        populateArrays();
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
                String[] rad_prop = s.split(" {3}");
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
                String[] position = s.split(" {3}");
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
        if(this.heads.get(arrPos) == null){
            this.heads.set(arrPos, p);
        }else{
            this.heads.get(arrPos).getParticlesSameCellList().add(p);
        }
    }

    private List<Particle> getNeighborCells(int i){
        List<Particle> neighborCells = new ArrayList<>();
        //if periodicEnvironment is set, apply modulus m to all calculations
        int aux = (periodicEnvironment) ? m : m * m;
        //up
        if(i + m < getHeads().size()){
            neighborCells.add(heads.get(i + m % aux));
        }
        //upper right
        if(i + m + 1  < getHeads().size()){
            neighborCells.add(heads.get(i + m % aux));
        }
        //right
        if(i + 1 < getM()){
            neighborCells.add(heads.get(i+1 % aux));
        }
        //bottom right
        if(i + 1 - m > 0){
            neighborCells.add(heads.get(i+1-m % aux));
        }
        return neighborCells;
    }

    private int getParticleCurrentCell(Particle p){
        int x = (int) (p.getX() / cellSize);
        int y = (int) (p.getY() / cellSize);
        return x + getM() * y;
    }

    public List<Integer> getParticleNeighborsIds(Particle p){
        return getParticleNeighbors(p).stream().map(Particle::getId).collect(Collectors.toList());
    }

    public List<Particle> getParticleNeighbors(Particle p){
        //find out where it is
        int cellNumber = getParticleCurrentCell(p);
        double deltaX, deltaY;
        double dist;
        double measureRadiusYesNo = (measureRadius) ? 1.0 : 0.0;
        List<Particle> neighborCells = getNeighborCells(cellNumber);
        for(Particle cellNeighbor : neighborCells){
            if(cellNeighbor == null) continue;
            for(Particle cellParticle : cellNeighbor.getParticlesSameCellList()){
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

}
