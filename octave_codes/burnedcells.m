input1 = dlmread('BurnedCells2.txt', '');
input1([1,2],:) = [];
x = input1(:,1);
y = input1(:,2); %fraccion de particulas


plot(x,y, "marker", 'o',"linestyle", "-", "color", "m", "linewidth", 1);
hold on;

input1 = dlmread('BurnedCells4.txt', '');
input1([1,2],:) = [];
x = input1(:,1);
y = input1(:,2); %fraccion de particulas
plot(x,y, "marker", 'o',"linestyle", "-", "color", "b", "linewidth", 1);
hold on;

input1 = dlmread('BurnedCells4.txt', '');
input1([1,2],:) = [];
x = input1(:,1);
y = input1(:,2); %fraccion de particulas
plot(x,y, "marker", 'o',"linestyle", "-", "color", "m", "linewidth", 1);

%legend("Densidad 3","Densidad 2", "Densidad 1");

set (gca, "xgrid", "on")
xlabel ("Tiempo [s]", "fontsize", 20);
ylabel("Celdas quemadas", "fontsize", 20);
set(gca, 'FontSize', 20)
hold on; 

