function TimeOutputPlot
  #cargo el archivo
  times = dlmread("../TP/TP/resources/TimeOutput2.txt",'', 1, 0);
  x=times(:,1);
  y=times(:,2);
  neg=times(:,3);
  pos=times(:,4);
  

  
  #cargo otro archivo
  times2 = dlmread("../TP/TP/resources/TimeOutput4.txt",'', 1, 0);
  x2=times2(:,1);
  y2=times2(:,2);
  neg2=times2(:,3);
  pos2=times2(:,4);
  
  errorbar(x,y,neg,pos,'r')
  hold on
  errorbar(x2,y2,neg2,pos2)
  
  title("M vs Time")
  legend('N=2','N=4')

  
  
  
 endfunction