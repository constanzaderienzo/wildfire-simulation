input1 = dlmread('BurnedPerDt.txt', '');
input1([1,2],:) = [];
x = input1(:,1);
y = input1(:,2); %fraccion de particulas


plot(x,y, "marker", 'o',"linestyle", "-", "color", "m", "linewidth", 1);

set (gca, "xgrid", "on")
xlabel ("Tiempo [s]", "fontsize", 20);
ylabel("Celdas quemandose", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 

