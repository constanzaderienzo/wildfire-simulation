
figure(1)
ejex = [0, 0.15, 0.25, 0.50, 0.75];
ejey = [52563,56406,53849,52344,50590];

%plot(ejex,ejey, "marker", 'o',"linestyle", "-", "color", "black", "linewidth", 1);
error = [1115,1511,3151,1315,1215];
%figure(6)
errorbar(ejex,ejey, error,'o-r');
%hold off;

xlabel ("Spotting (Pc0) ", "fontsize", 20);
ylabel("Celdas quemadas", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 

