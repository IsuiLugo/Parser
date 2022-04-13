package gramaticasequipoa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AnalizadorSintacticoEAS extends javax.swing.JFrame {

    //Varibles globales
    ArrayList<String> Entradas = new ArrayList<String>(); //Guarda las cadenas del archivo fuente
    ArrayList<String> Lexemas = new ArrayList<String>(); //Guarda los lexemas fuente
    ArrayList<String> LexemasUnicos = new ArrayList<String>(); //Guarda los lexemas unicos
    ArrayList<String> Tokens = new ArrayList<String>(); //Guarda los nombres de los tokens
    ArrayList<String> Identificadores = new ArrayList<String>(); //Guarda los nombre de los identificadores
    ArrayList<String> Comentarios = new ArrayList<String>(); //Elimina los comentarios del archivo fuente
    ArrayList<Integer> Linea = new ArrayList<Integer>(); //Guarda el número de linea
    String Token1 = null; 
    String Token2 = null;
    String Token3 = null;
    String TokenExpre = null;
    String TokenError = null;
    String Identificador = null;
    public char x; //A simbolo de salida de la cadena
    public int k = 0; //Inicializa el primer simbolo de la gramatica
    //public String s; //La cadena
    public boolean valida; //Valor booleano de la cadena

    public AnalizadorSintacticoEAS() {
        initComponents();
    }

    //Metodo para el tokenA que acepta cualquier cadena de caracteres
    public boolean TokenA(String w) {
        String RExpresion = "[a-z]*";
        boolean valida = false;
        Pattern expresion = Pattern.compile(RExpresion);
        Matcher evalua = expresion.matcher(w);
        if (evalua.matches()) {
            valida = true;
        } else {
            valida = false;
        }
        return valida;
    }

    //Metodo para el tokenB que acepta cualquier cadena de números
    public boolean TokenB(String w) {
        String RExpresion = "[0-9]*";
        boolean val = false;
        Pattern expresion = Pattern.compile(RExpresion);
        Matcher evalua = expresion.matcher(w);
        if (evalua.matches()) {
            val = true;
        } else {
            val = false;
        }
        return val;
    }
    
    /*Metodo para el tokenEA que acepta
     *EA = Expresiones aritmeticas*/
    
    public boolean TokenEA(String w) {
        String RExpresion = "[a-z|+-/|0-9]*";
        boolean vali = false;
        Pattern expresion = Pattern.compile(RExpresion);
        Matcher evalua = expresion.matcher(w);
        if (evalua.matches()) {
            vali = true;
        } else {
            vali = false;
        }
        return vali;
    }

    /*Método para el tokenExpresion que acepta cualquier
    /*expresion aritmetica simple */
    public boolean TokenExpresion(String w) {
        String RExpresion = "[a-z|0-9]*[+-/][a-z|0-9]*";
        boolean vali = false;
        Pattern expresion = Pattern.compile(RExpresion);
        Matcher evalua = expresion.matcher(w);
        if (evalua.matches()) {
            vali = true;
        } else {
            vali = false;
        }
        return vali;
    }

    //Metodo para validar cada token, lexema, y CFG de manera iterativa
    public void Valida(String w, int linea) {
        linea  = linea + 1;
        boolean val = false;
        //Elimina comentarios
        for (int j = 0; j < w.length(); j++) {
            char a = w.charAt(j);
            String q = String.valueOf(a);
            if (q.equals("/")) {
                val = true;
                GuardaComentarios(w);
                break;
            } else {
                val = false;
            }
        }

        if (val) {
            //Es un comentario
            GuardaComentarios(w);
        } else if (TokenA(w)) {
            //Es una cadena validada por el tokenA (solo letras)
            w.replace(" ", "");
            boolean cadenaTrue = Llama(w);
            //LLama a la CFG para saber si cumple
            if (cadenaTrue) {
                //Posisiona el texto en la siguiente linea
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                jTextArea1.append("Cadena valida: " + w + "\n");
                //Es una cadena valida por la CFG
                k = 0;//Reinicia el contador de la CFG con k = 0
                Token1 = "TokenA";
                this.GuardaToken(Token1);
                this.GuardaLexema(w);
                Identificador = "ID";
                this.GuardaIdentificador(Identificador);
                String unico = w.replace("a","0");
                unico = unico.replace("b","1");
                this.GuardaUnicos(unico);
                this.GuardaLinea(linea);
            } else {
                Error(w, linea);
                TokenError = "ErrorSintax";
                this.GuardaToken(TokenError);
                this.GuardaLexema(w);
                Identificador = "ID";
                this.GuardaIdentificador(Identificador);
                String unico = w.replace("a","0");
                unico = unico.replace("b","1");
                this.GuardaUnicos(unico);
                this.GuardaLinea(linea);
            }

        } else if (TokenB(w)) {
            //Es una cadena validad por el TokenB (solo numeros)
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            jTextArea1.append(w + "\n");
            Token2 = "TokenB";
            this.GuardaToken(Token2);
            this.GuardaLexema(w);
            Identificador = "NUM";
            this.GuardaIdentificador(Identificador);
            this.GuardaUnicos(w);
            this.GuardaLinea(linea);

        } else if (TokenEA(w)) {
            //Es una cadena valida por ser una expresion aritmetica
            if (TokenExpresion(w)) {
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                jTextArea1.append(w + "\n");
                Token3 = "TokenEAS";
                this.GuardaToken(Token3);
                this.GuardaLexema(w);
                Identificador = "ID";
                this.GuardaIdentificador(Identificador);
                this.GuardaUnicos(w);
                this.GuardaLinea(linea);
            } else {
                Error(w, linea);
                Token3 = "ErrorEAS";
                this.GuardaToken(Token3);
                this.GuardaLexema(w);
                Identificador = "NUM";
                this.GuardaIdentificador(Identificador);
                this.GuardaUnicos(w);
                this.GuardaLinea(linea);
            }
        } else {
            Error(w, linea);
             Token3 = "NotFound";
            this.GuardaToken(Token3);
            this.GuardaLexema(w);
            Identificador = "NotFound";
            this.GuardaIdentificador(Identificador);
            this.GuardaUnicos(w);
            this.GuardaLinea(linea);
        }

    }

    /*COMIENZA LA GRAMATICA LIBRE DE CONTEXTO*/
    //Funcion que llama al método S
    public boolean Llama(String w) {
        boolean comprueb = false;
        x = nextCaracter(w);
        if (S(w)) {
            comprueb = true;
        }
        return comprueb;
    }

    //Metodo para el siguiente caracter
    public char nextCaracter(String w) {
        if (k < w.length()) {
            x = w.charAt(k);
            k++;
        } else {
            x = '*';
        }
        return x;
    }

    //Función S
    public boolean S(String w) {
        valida = false;
        if (x == 'a') {
            x = nextCaracter(w);
            if (B()) {
                valida = false;
                x = nextCaracter(w);
                if (x == '*') {
                    valida = true;
                }
            } else {
                valida = S(w);
            }
        }
        return valida;
    }

    //Función B
    public boolean B() {
        valida = false;
        if (x == 'b') {
            valida = true;
        }
        return valida;
    }

    /*TERMINA LA GRAMATICA LIBRE DE CONTEXTO*/
    //Métrodo para el error de sintaxis
    public void Error(String w, int linea) {
        //Append imprimime una linea en el jTextArea
        jTextArea1.append("Error de sintaxis linea: " + linea + "\n");
        jTextArea1.append(w + "\n");
        for (int j = 0; j < k - 1; j++) {
            //Posisiona el texto en la siguiente linea
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            jTextArea1.append(" ");
        }
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        jTextArea1.append("^" + "\n");
    }

    //Limpiar campos
    public void Limpiar() {
        jTextField1.setText(null);
        jTextArea1.setText(null);
        k = 0;

    }
    
    public String Rutas() {
        String opcion = null;
        int indice = jComboBox1.getSelectedIndex();
        if (indice == 0) {
            opcion = "C:\\Users\\hp\\Documents\\NetBeansProjects\\GramaticasEquipoA\\CadenasTrue.txt";
        } else if (indice == 1) {
            opcion = "C:\\Users\\hp\\Documents\\NetBeansProjects\\GramaticasEquipoA\\Expresiones.txt";
        } else {
            opcion = "C:\\Users\\hp\\Documents\\NetBeansProjects\\GramaticasEquipoA\\Documento.txt";
        }
        return opcion;
    }
    
    //LECTOR DE ARCHIVO
    public void LeerArchivo() {
        String cadena = "";
        String ruta = Rutas();
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader lectura = new BufferedReader(lector);
            //Lee cada linea del archivo
            cadena = lectura.readLine();
            while (cadena != null) {
                this.GuardaEntradas(cadena);
                cadena = lectura.readLine();
            }
            jTextField1.setText("Cadenas leidas correctamente del archivo TXT");
        } catch (FileNotFoundException ex) {
            System.out.println("Error1 en leer: " + ex);
        } catch (IOException ex) {
            System.out.println("Error2 en leer: " + ex);
        }
    }

    //Llama de manera iterativa a los Tokens
    public void Procesa() {
        try {
            for (int i = 0; i < Entradas.size(); i++) {
                String cadena = Entradas.get(i);
                Valida(cadena, i);
            }
            jTextField1.setText("Cadenas procesadas");
        } catch (Exception e) {
            System.out.println("Error en procesa = " + e);
        }
    }

    //IMPRIMIR EN TABLA DE LEXEMAS FUENTE
    public void Imprime() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            int i;
            jTextField1.setText("");
            for (i = 0; i < Lexemas.size(); i++) {
                //Imprime en cada columna, i es la fila
                jTable1.setValueAt(Tokens.get(i), i, 0);
                jTable1.setValueAt(Lexemas.get(i), i, 1);
                jTable1.setValueAt(Linea.get(i), i, 2);
            }

        } catch (Exception e) {
            System.out.println("Error en imprime: = " + e);
        }
    }

    //IMPRIMIR EN TABLA DE SIMBOLOS
    public void Imprime2() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
            int i;
            jTextField1.setText("");
            for (i = 0; i < Lexemas.size(); i++) {
                //Imprime en cada columna, i es la fila
                jTable2.setValueAt(Identificadores.get(i), i, 0);
                jTable2.setValueAt(LexemasUnicos.get(i), i, 1);
                jTable2.setValueAt(Linea.get(i), i, 2);
            }
        } catch (Exception e) {
            System.out.println("Error en imprime2: = " + e);
        }
    }

    //Método para imprimir en TextArea los comentarios
    public void ImprimeArea() {
        for (int i = 0; i < Comentarios.size(); i++) {
            jTextArea1.append(Comentarios.get(i) + "\n");

        }
    }

    //Método para converti de binario a decimal
    public String BinarioDecimal(String binario) {
        double decimal = 0f;
        String valor;
        int j = (binario.length()) - 1;
        for (double i = 0; i <= (binario.length()) - 1; i++) {
            double valorActual = Character.getNumericValue(binario.charAt(j));
            j--; //Itera en cada valor del número
            double suma = (Math.pow(2, i)) * valorActual;
            decimal = decimal + suma;
        }
        int Resultado = (int) decimal; //Elimina el cero del valor double
        valor = String.valueOf(Resultado);
        return valor;
    }

    //Método para guardar los datos de entrada
    public void GuardaEntradas(String cadena) {
        Entradas.add(cadena);
    }

    //Metodo para guardar lexemas   
    public void GuardaLexema(String cadena) {
        Lexemas.add(cadena);
    }

    //Metodo para guardar lexemas únicos   
    public void GuardaUnicos(String cadena) {
        LexemasUnicos.add(cadena);
    }

    //Metodo para guardar tokens
    public void GuardaToken(String token) {
        Tokens.add(token);
    }

    //Metodo para guardar el no. de linea encontrada
    public void GuardaLinea(int linea) {
        Linea.add(linea);
    }

    //Método para guardar los comentarios
    public void GuardaComentarios(String comentario) {
        Comentarios.add(comentario);
    }

    //Método para guardar los comentarios
    public void GuardaIdentificador(String comentario) {
        Identificadores.add(comentario);
    }

    //Metodo para limpiar la tabla
    public void limpiarTabla(JTable tabla) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int filas = tabla.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
            jTextArea1.setText("");
        } catch (Exception e) {
            System.out.println("Error en limpiar tabla: = " + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("CARGAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("MOSTRAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("TOKENS FUENTE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("SÍMBOLOS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("COMENTARIOS");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("REINICIA");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "TOKEN", "LEXEMA", "No. LINEA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "TOKEN", "LEX. ÚNICO", "No. Linea"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consola", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 14), new java.awt.Color(0, 51, 153))); // NOI18N
        jScrollPane3.setViewportView(jTextArea1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CADENAS", "EXPRESIONES ARITMETICAS", "GENERAL" }));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/encabezado.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Proyecto de asignatura: Analizador Sintáctico p/EAS");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Equipo A");

        jLabel5.setText("Lexemas archivo fuente");

        jLabel6.setText("Tabla de simbolos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(294, 294, 294))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane3))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.LeerArchivo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.Procesa();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.Imprime();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.Imprime2();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.ImprimeArea();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.limpiarTabla(jTable1);
        this.limpiarTabla(jTable2);
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticoEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticoEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticoEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticoEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnalizadorSintacticoEAS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
