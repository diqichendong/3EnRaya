package com.example.a3enraya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int VACIO = 0;
    private final int J1 = 1;
    private final int J2 = 2;
    private final int FICHA_0 = R.drawable.ficha_0;
    private final int FICHA_1 = R.drawable.ficha_1;
    private final int FICHA_2 = R.drawable.ficha_2;

    int[][] casillero = new int[3][3];
    boolean esPrimeraParte = true;
    boolean esTurno1 = true;
    boolean estaEnJuego = false;
    boolean esPrimerClic = true;
    int fil, col, filDesde, colDesde;
    View desdeCasilla;
    int contInicio = 0;
    String nombre1 = "1";
    String nombre2 = "2";
    int ganador = 0;

    TextView lblInfo;
    Button btnEmpezar, btnNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        mostrarDatosEnTablero();

    }

    private void initComponents() {
        lblInfo = findViewById(R.id.lblInfo);

        // Asignamos listeners a los botones del casillero
        int[] btns = {
                R.id.btn00, R.id.btn01, R.id.btn02,
                R.id.btn10, R.id.btn11, R.id.btn12,
                R.id.btn20, R.id.btn21, R.id.btn22
        };
        for (int id : btns) {
            findViewById(id).setOnClickListener(v -> clickOnCasilla(v));
        }

        btnEmpezar = findViewById(R.id.btnEmpezar);
        btnEmpezar.setOnClickListener(v -> btnEmpezar(v));

        btnNombres = findViewById(R.id.btnNombres);
        btnNombres.setOnClickListener(v -> btnNombres(v));
    }

    private void mostrarDatosEnTablero() {
        // para cada elemento seleccionable del tablero (GrisLayout)
        // le asignamos un foreground según el valor almacenado en la matriz casillero
        ArrayList<View> btn = findViewById(R.id.tablero).getTouchables();
        int queFicha = 0;
        int i = 0;
        for (int f = 0; f < 3; f++) {
            for (int c = 0; c < 3; c++) {
                if (casillero[f][c] == VACIO) queFicha = FICHA_0;
                if (casillero[f][c] == J1) queFicha = FICHA_1;
                if (casillero[f][c] == J2) queFicha = FICHA_2;
                btn.get(i++).setForeground(getDrawable(queFicha));
            }
        }
        mostrarQuienJuega();
    }

    private void clickOnCasilla(View v) {
        if (estaEnJuego) {
            if (esPrimeraParte) {
                detectarFilCol(v);
                if (esTurno1) {
                    if (casillero[fil][col] == VACIO) {
                        casillero[fil][col] = J1;
                        v.setForeground(getDrawable(FICHA_1));
                        esTurno1 = false;
                    }
                } else {
                    if (casillero[fil][col] == VACIO) {
                        casillero[fil][col] = J2;
                        v.setForeground(getDrawable(FICHA_2));
                        esTurno1 = true;
                    }
                }
                if (contInicio++ == 5) esPrimeraParte = false;
            } else {
                detectarFilCol(v);
                if (esPrimerClic) {
                    if (esTurno1) {
                        if (casillero[fil][col] == J1) {
                            filDesde = fil;
                            colDesde = col;
                            desdeCasilla = v;
                            esPrimerClic = false;
                        }
                    } else {
                        if (casillero[fil][col] == J2) {
                            filDesde = fil;
                            colDesde = col;
                            desdeCasilla = v;
                            esPrimerClic = false;
                        }
                    }
                } else {
                    if (esTurno1) {
                        if (casillero[fil][col] == VACIO) {
                            casillero[filDesde][colDesde] = VACIO;
                            casillero[fil][col] = J1;
                            esPrimerClic = true;
                            desdeCasilla.setForeground(getDrawable(FICHA_0));
                            v.setForeground(getDrawable(FICHA_1));
                            esTurno1 = false;
                        }
                    } else {
                        if (casillero[fil][col] == 0) {
                            casillero[filDesde][colDesde] = VACIO;
                            casillero[fil][col] = J2;
                            esPrimerClic = true;
                            desdeCasilla.setForeground(getDrawable(FICHA_0));
                            v.setForeground(getDrawable(FICHA_2));
                            esTurno1 = true;
                        }
                    }
                }
            }
            detectarFinalJuego();
            mostrarQuienJuega();
        }
    }

    private void detectarFinalJuego() {
        ganador = 0;
        // primera horizontal
        if (casillero[0][0] == J1 && casillero[0][1] == J1 && casillero[0][2] == J1) {
            ganador = 1;
        }
        if (casillero[0][0] == J2 && casillero[0][1] == J2 && casillero[0][2] == J2) {
            ganador = 2;
        }
        // segunda horizontal
        if (casillero[1][0] == J1 && casillero[1][1] == J1 && casillero[1][2] == J1) {
            ganador = 1;
        }
        if (casillero[1][0] == J2 && casillero[1][1] == J2 && casillero[1][2] == J2) {
            ganador = 2;
        }
        // tercera horizontal
        if (casillero[2][0] == J1 && casillero[2][1] == J1 && casillero[2][2] == J1) {
            ganador = 1;
        }
        if (casillero[2][0] == J2 && casillero[2][1] == J2 && casillero[2][2] == J2) {
            ganador = 2;
        }
        // primera vertical
        if (casillero[0][0] == J1 && casillero[1][0] == J1 && casillero[2][0] == J1) {
            ganador = 1;
        }
        if (casillero[0][0] == J2 && casillero[1][0] == J2 && casillero[2][0] == J2) {
            ganador = 2;
        }
        // segunda vertical
        if (casillero[0][1] == J1 && casillero[1][1] == J1 && casillero[2][1] == J1) {
            ganador = 1;
        }
        if (casillero[0][1] == J2 && casillero[1][1] == J2 && casillero[2][1] == J2) {
            ganador = 2;
        }
        // tercera vertical
        if (casillero[0][2] == J1 && casillero[1][2] == J1 && casillero[2][2] == J1) {
            ganador = 1;
        }
        if (casillero[0][2] == J2 && casillero[1][2] == J2 && casillero[2][2] == J2) {
            ganador = 2;
        }
        // diagonal 1
        if (casillero[0][0] == J1 && casillero[1][1] == J1 && casillero[2][2] == J1) {
            ganador = 1;
        }
        if (casillero[0][0] == J2 && casillero[1][1] == J2 && casillero[2][2] == J2) {
            ganador = 2;
        }
        // diagonal 2
        if (casillero[0][2] == J1 && casillero[1][1] == J1 && casillero[2][0] == J1) {
            ganador = 1;
        }
        if (casillero[0][2] == J2 && casillero[1][1] == J2 && casillero[2][0] == J2) {
            ganador = 2;
        }

        if (ganador != 0) {
            estaEnJuego = false;
        }
    }

    private void mostrarQuienJuega() {
        String mensaje = "";
        if (estaEnJuego) {  // Mostrar qué jugador tiene el turno
            lblInfo.setTextColor((esTurno1 ? getColor(R.color.jugador1) : getColor(R.color.jugador2)));
            mensaje = getString(R.string.turno) + (esTurno1 ? nombre1 : nombre2);
        } else {
            if (ganador != 0) {  // Mostrar si hay algún ganador
                lblInfo.setTextColor(getColor(R.color.info));
                mensaje = getString(R.string.gana) + (ganador == 1 ? nombre1 : nombre2);
            }
        }
        lblInfo.setText(mensaje);
    }

    private void detectarFilCol(View btn) {
        // Determinar en qué fila y columna está el botón btn pulsado
        if (btn.getId() == R.id.btn00) {
            fil = 0;
            col = 0;
        }
        if (btn.getId() == R.id.btn01) {
            fil = 0;
            col = 1;
        }
        if (btn.getId() == R.id.btn02) {
            fil = 0;
            col = 2;
        }
        if (btn.getId() == R.id.btn10) {
            fil = 1;
            col = 0;
        }
        if (btn.getId() == R.id.btn11) {
            fil = 1;
            col = 1;
        }
        if (btn.getId() == R.id.btn12) {
            fil = 1;
            col = 2;
        }
        if (btn.getId() == R.id.btn20) {
            fil = 2;
            col = 0;
        }
        if (btn.getId() == R.id.btn21) {
            fil = 2;
            col = 1;
        }
        if (btn.getId() == R.id.btn22) {
            fil = 2;
            col = 2;
        }
    }

    public void btnEmpezar(View v) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.nueva_partida)
                .setCancelable(false)
                .setMessage(R.string.quieres_una_nueva_partida)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.si, (dialog, which) -> nuevaPartida())
                .show();
    }

    private void nuevaPartida() {
        esPrimeraParte = true;
        esTurno1 = true;
        estaEnJuego = true;
        contInicio = 0;
        esPrimerClic = true;
        mostrarQuienJuega();

        // Ponemos el casillero como en blanco
        for (int f = 0; f < 3; f++) {
            for (int c = 0; c < 3; c++) {
                casillero[f][c] = VACIO;
            }
        }

        // Asignamos el foreground blanco a todos los botones del tablero (GridLayout)
        ArrayList<View> views = findViewById(R.id.tablero).getTouchables();
        for (View v : views) {
            if (v instanceof Button) {
                v.setForeground(getDrawable(FICHA_0));
            }
        }
    }

    public void btnNombres(View view) {
        final View layout_nombres = getLayoutInflater().inflate(R.layout.preguntar_nombres, null);
        EditText txtNombre1 = layout_nombres.findViewById(R.id.txtNombre1);
        EditText txtNombre2 = layout_nombres.findViewById(R.id.txtNombre2);
        if (!nombre1.equals("1")) {
            txtNombre1.setText(nombre1);
        }
        if (!nombre2.equals("2")) {
            txtNombre2.setText(nombre2);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.nombre_jugadores)
                .setView(layout_nombres)
                .setNegativeButton(R.string.cancelar, null)
                .setPositiveButton(R.string.guardar, (dialog, which) -> {
                    nombre1 = txtNombre1.getText().toString().equals("") ? "1" : txtNombre1.getText().toString().trim();
                    nombre2 = txtNombre2.getText().toString().equals("") ? "2" : txtNombre2.getText().toString().trim();
                    mostrarQuienJuega();
                })
                .create()
                .show();
    }

}