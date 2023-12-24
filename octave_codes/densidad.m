
figure(1)
ejex = [20, 50,  80];
ejey = [50716, 92419.3, 136932];

%plot(ejex,ejey, "marker", 'o',"linestyle", "-", "color", "black", "linewidth", 1);
error = [2076.27,4318.85,  20296.1];
%figure(6)
errorbar(ejex,ejey, error, 'o-');
%hold off;

xlabel ("Densidad", "fontsize", 20);
ylabel("Celdas quemadas", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 

figure(2)
ejex = [20, 50,  80];
ejey = [150.95, 313.38, 333.65];

%plot(ejex,ejey, "marker", 'o',"linestyle", "-", "color", "black", "linewidth", 1);
error = [5.74, 17.53,  72.2];
%figure(6)
errorbar(ejex,ejey, error, 'o-');
%hold off;

xlabel ("Densidad", "fontsize", 20);
ylabel("Celdas quemadas por dt", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 
