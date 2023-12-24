
% eje x valores a cambiar
ejex = [1, 10, 100, 400, 800, 1200, 1600, 2000];

%valor de k
ejey = [0.2185, 0.2175, 0.208, 0.2035, 0.2115, 0.1875, 0.191, 0.1845];

%error de k

error = [0.05, 0.02, 0.05, 0.01, 0.04, 0.01, 0.012, 0.011];

errorbar(ejex,ejey, error, 'o-');
xlim([0.0, 2000.0]);

set (gca, "xgrid", "on");
set (gca, "ygrid", "on");
xlabel ("Altura maxima [m]", "fontsize", 20);
ylabel("K", "fontsize", 20);
set(gca, 'FontSize', 20)