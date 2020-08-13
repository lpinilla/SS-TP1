function TimeOutputPlot(N)
  #cargo el archivo
  times1 = dlmread("../TimeOutput1.txt",'', 1, 0);
  times2 = dlmread("../TimeOutput2.txt",'', 1, 0);
  times3 = dlmread("../TimeOutput3.txt",'', 1, 0);
  times4 = dlmread("../TimeOutput4.txt",'', 1, 0);
  times5=dlmread("../TimeOutput5.txt",'', 1, 0);


  #longitud de las filas
  fil=times1(1,:);
  sizeF=length(fil)-1;
  
  #longitud de las columnas
  col=times1(:,1);
  sizeC=length(col);
  
  #agarro las M, eje x
  x=times1(:,1);
  
  #analizo 1
  for i=1:sizeC
  aux=times1(i,2:sizeF)/1000000000;
  y(i)=mean(aux);
  s(i)=std(aux);
  end
  errorbar(x,y,s,'r')
  hold on
  
  #analizo 2
  for i=1:sizeC
  aux=times2(i,2:sizeF)/1000000000;
  y(i)=mean(aux);
  s(i)=std(aux);
  end
  errorbar(x,y,s,'g')
  hold on
  
  #analizo 3
  for i=1:sizeC
  aux=times3(i,2:sizeF)/1000000000;
  y(i)=mean(aux);
  s(i)=std(aux);
  end
  errorbar(x,y,s,'b')
  hold on
  
  #analizo 4
  for i=1:sizeC
  aux=times4(i,2:sizeF)/1000000000;
  y(i)=mean(aux);
  s(i)=std(aux);
  end
  errorbar(x,y,s,'m')
  hold on
  
   #analizo 5
  for i=1:sizeC
  aux=times5(i,2:sizeF)/1000000000;
  y(i)=mean(aux);
  s(i)=std(aux);
  end
  errorbar(x,y,s,'k')
  hold on
  
  #para graficos
   str1 = strcat('N = ', num2str(N));
   str2 = strcat('N = ', num2str(N+100));
   str3 = strcat('N = ', num2str(N+200));
   str4 = strcat('N = ', num2str(N+300));
   str5 = strcat('N = ', num2str(N+400));

  title("M vs Tiempo para distintos N")
  legend(str1,str2,str3,str4,str5)
  xlabel('M')
  ylabel('Tiempo [segundos]')
  
  print graficoTiempo.pdf
  open graficoTiempo.pdf
 
  
  
  
 endfunction