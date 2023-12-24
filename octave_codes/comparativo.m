%Diria de hacer solo 5, aca esta hecho con 2

input1 = dlmread('BurnedCells2.txt', '');
input1([1,2],:) = [];
x = input1(:,1);
y = input1(:,2); 
plot(x,y, "linestyle", "-", "color", "m", "linewidth", 2);
hold on;

input1 = dlmread('BurnedCells3.txt', '');
input1([1,2],:) = [];
x = input1(:,1);
y = input1(:,2); 
plot(x,y, "linestyle", "-", "color", "b", "linewidth", 2);
hold on;


set (gca, "xgrid", "on");
set (gca, "ygrid", "on");
xlabel ("Tiempo [s]", "fontsize", 20);
ylabel("Celdas quemadas", "fontsize", 20);

legend("Densidad A", "Densidad B");