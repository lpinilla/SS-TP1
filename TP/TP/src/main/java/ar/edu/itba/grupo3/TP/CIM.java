package ar.edu.itba.grupo3.TP;

import java.io.*;
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
    private boolean periodicEnvironment;
    private final boolean measureRadius;

    public void setPeriodicEnvironment(boolean periodicEnvironment){
        this.periodicEnvironment = periodicEnvironment;
    }

    public Map<Integer, Particle> getHeads() {
        return heads;
    }

    public void setHeads(Map<Integer, Particle> heads) {
        this.heads = heads;
    }

    public List<Particle> getAllParticles() {
        return allParticles;
    }

    public void setAllParticles(List<Particle> allParticles) {
        this.allParticles = allParticles;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    public float getRc() {
        return rc;
    }

    public void setRc(float rc) {
        this.rc = rc;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public CIM(int n, float l, float rc, int m, boolean periodicEnvironment, boolean measureRadius) throws IllegalArgumentException {
        if (n <= 0 || l <= 0 || rc <= 0 || m <= 0) throw new IllegalArgumentException("incorrect arguments");
        if ((l / m) <= rc) throw new IllegalArgumentException("No se cumple la condición 'l / m > rc'");
        this.heads = new TreeMap<>();
        this.allParticles = new ArrayList<>() {
            @Override
            public boolean add(Particle p) {
                super.add(p);
                allParticles.sort(Comparator.comparing(Particle::getId));
                return true;
            }
        };
        this.n = n;
        this.l = l;
        this.rc = rc;
        this.m = m;
        this.cellSize = l / m;
        this.periodicEnvironment = periodicEnvironment;
        this.measureRadius = measureRadius;
    }

    public CIM(int m, float rc, boolean periodicEnvironment, boolean measureRadius, String path) throws IllegalArgumentException {
        if (path.isEmpty()) throw new IllegalArgumentException("empty path");
        if (m <= 0 || rc <= 0) throw new IllegalArgumentException("Wrong arguments");
        this.heads = new TreeMap<>();
        this.allParticles = new ArrayList<>() {
            @Override
            public boolean add(Particle p) {
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
        this.cellSize = l / m;
        if ((l / m) <= rc) throw new IllegalArgumentException("No se cumple la condición 'l / m > rc'");
    }

    public void loadStaticFile(String path) {
        if (path.isEmpty()) return;
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            this.n = Integer.parseInt(br.readLine());
            this.l = Integer.parseInt(br.readLine());
            //particles
            int index = 0;
            while ((s = br.readLine()) != null) {
                String[] rad_prop = s.split(" {4}");
                this.allParticles.add(
                        new Particle(
                                Double.parseDouble(rad_prop[0]),
                                Double.parseDouble(rad_prop[1]),
                                index));
                index++;
            }
            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadDynamicFile(String path) {
        if (path.isEmpty()) return;
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            int time = Integer.parseInt(br.readLine()); //ignore first line
            //particles
            int index = 0;
            Particle aux;
            while ((s = br.readLine()) != null) {
                String[] position = s.split(" {4}");
                aux = this.allParticles.get(index);
                aux.setX(Double.parseDouble(position[0]));
                aux.setY(Double.parseDouble(position[1]));
                putInCell(aux);
                index++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void putInCell(Particle p) {
        int arrPos = getParticleCurrentCell(p);
        if (arrPos == -1) return;
        if (this.heads.get(arrPos) == null) {
            this.heads.put(arrPos, p);
        } else {
            this.heads.get(arrPos).getParticlesFromCell().add(p);
        }
    }

    public Particle moveCell(Particle cell, double xDispl, double yDispl){
        if(cell == null) return null;
        Particle ret = new Particle(cell.getX(), cell.getY(), cell.getRadious(), cell.getProperty());
        ret.setId(cell.getId());
        Particle aux;
        for(Particle p : cell.getParticlesFromCell()){
            aux = cloneParticle(p);
            //agregar desplazamiento
            aux.setX(aux.getX() + xDispl);
            aux.setY(aux.getY() + yDispl);
            ret.getParticlesFromCell().add(aux);
        }
        return ret;
    }

    private Particle cloneParticle(Particle p){
        Particle ret = new Particle(p.getX(), p.getY(), p.getProperty(), p.getRadious());
        ret.setId(p.getId());
        return ret;
    }


    public List<Particle> getLShapeHeaders(int cell){
        Particle[] neighborCells = new Particle[5];
        neighborCells[0] = heads.get(cell);
        Particle p;
        //up
        if(cell + m < m * m) neighborCells[1] = heads.get(cell + m);
        //upper right
        if(cell + m + 1 < m * m) neighborCells[2] = heads.get(cell + m + 1);
        //right
        if(cell +1 < (((cell +1) / m) * m) + m - 1) neighborCells[3] = heads.get(cell + 1);
        //bottom right
        if(cell - m +1 > 0) neighborCells[4] = heads.get(cell - m + 1);
        //corrections
        if(periodicEnvironment){
            //last row
            if(cell >= m * m - m){
                neighborCells[1] = moveCell(heads.get( cell % m), 0.0, (double) m * m - m); //up
                neighborCells[2] = moveCell(heads.get((cell + 1) % m),0,(double) m * m - m); //upper right
            }
            //last column
            if(cell % m == m -1){
                neighborCells[2] = moveCell(heads.get(cell + 1), 0, m * m - m); //upper right
                neighborCells[3] = moveCell(heads.get(cell / m * m), m * m - m, 0); //right
                neighborCells[4] = moveCell(heads.get((cell / m * m) - m), m * m - m, 0); //bottom right
            }
            //first row
            if(cell <= m){
                neighborCells[4] = moveCell(heads.get(cell+1 + m * m - 2 * m), m * m - m, 0);
            }
        }
        return Arrays.stream(neighborCells).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public int getParticleCurrentCell(Particle p) {
        if (p == null) return -1;
        int x = (int) (p.getX() / cellSize);
        int y = (int) (p.getY() / cellSize);
        return x + getM() * y;
    }

    public void calculateNeighbors() {
        double radiusMultiplier = (measureRadius) ? 1.0 : 0.0;
        Particle currentCellHead;
        for (Integer cellNumber : getHeads().keySet()) {
            currentCellHead = getHeads().get(cellNumber);
            for (Particle heads : getLShapeHeaders(cellNumber)) {
                for (Particle possibleNeighbor : heads.getParticlesFromCell()) {
                    for (Particle particleInCurrentCell : currentCellHead.getParticlesFromCell()) {
                        addIfInRange(particleInCurrentCell, possibleNeighbor, radiusMultiplier);
                    }
                }
            }
        }
    }

    public void addIfInRange(Particle p1, Particle p2, double measureRadiusYesNo) {
        if (p1.equals(p2)) return;
        double deltaX = Math.abs(p2.getX() - p1.getX());
        double deltaY = Math.abs(p2.getY() - p1.getY());
        double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY) -
                ((p2.getRadious() + p1.getRadious()) * measureRadiusYesNo);
        //check if distance is withing rc
        if (dist < getRc()) {
            p1.getNeighbours().add(p2);
            p2.getNeighbours().add(p1);
        }
    }

    public void saveNeighborsToFile(String path) {
        if (path.isEmpty()) return;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
            for (int i = 0; i < this.getN(); i++) {
                writer.write(i + ",");
                StringBuilder builder = new StringBuilder();
                for (Particle p : this.getAllParticles().get(i).getNeighbours()) {
                    builder.append(p);
                    builder.append(',');
                }
                String id = builder.toString();
                writer.write(id);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void saveTimeToFile(String dirPath, int N, int M, double tprom, double tmin, double tmax) {
        try {
            File f = new File(dirPath + "TimeOutput" + N + ".txt");
            double aux1 = tprom - tmin;
            double aux2 = tmax - tprom;
            if (!f.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(Integer.toString(N));
                writer.newLine();
                writer.write(M + " " + tprom + " " + aux1 + " " + aux2);
                //necesito todos los tiempos?
//                for (int i = 0; i < time.length; i++) {
//                    writer.write(Double.toString(time[i])+" ");
//                }
                writer.newLine();
                writer.flush();
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
                writer.write(M + " " + tprom + " " + aux1 + " " + aux2);
//                for (int i = 0; i < time.length; i++) {
//                   writer.write(Double.toString(time[i])+" ");
//                }
                writer.newLine();
                writer.flush();
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}