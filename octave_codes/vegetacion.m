
figure(1)
ejex = [1, 2,  3];
ejey = [86563.3, 136932, 249786.67];

%plot(ejex,ejey, "marker", 'o',"linestyle", "-", "color", "black", "linewidth", 1);
error = [3125.8, 20296.1,  20.59935274];
%figure(6)
errorbar(ejex,ejey, error, 'o-');
%hold off;

xlabel ("Vegetacion", "fontsize", 20);
ylabel("Celdas quemadas", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 

figure(2)
ejex = [1, 2,  3];
ejey = [280.16, 333.65, 426.75];

%plot(ejex,ejey, "marker", 'o',"linestyle", "-", "color", "black", "linewidth", 1);
error = [11.27, 72.22,  12.77];
%figure(6)
errorbar(ejex,ejey, error, 'o-');
%hold off;

xlabel ("Vegetacion", "fontsize", 20);
ylabel("Celdas quemadas por dt", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 
