function CellIndexMethod(number)
#cargo el archivo con todas las particulas
particles = dlmread("../TP/TP/resources/Dynamic100.txt",'', 1, 0);
cant_particles=length(particles);

#cargo el archivo con todos los neighbours
neighbours =  dlmread("../TP/TP/resources/AlgunosVecinos_100_rc6.txt",',', 0, 0);

#guardo la fila de la particula que quiero ver los neighbours
interest_particle = neighbours(number,:);
cant_neigh=length(interest_particle);

#dibujo todas las particulas
x=particles(1:cant_particles);
y=particles(cant_particles+1:cant_particles*2);
plot(x,y,'o')
hold on

#dibujo la paticula en cuestion
m=interest_particle(1,1)
x1=particles(m,1)
y1=particles(m,2)
plot(x1,y1,'r*')
hold on

#dibujo los neighbours de la particula en cuestion
for i = 2:cant_neigh
   n=interest_particle(1,i)
   if n!=0
   x2=particles(n,1)
   y2=particles(n,2)
   plot(x2,y2,'g*')
   hold on
   endif
endfor

title("Grafico de particulas y vecinos")
legend('Todas Las Particulas','Particula','Vecinos','Location','SouthOutside')
endfunction

