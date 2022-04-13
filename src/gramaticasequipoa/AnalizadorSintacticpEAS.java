
package gramaticasequipoa;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
* Tecnológico Nacional de México campus Pachuca
* Asignatura: Lenguajes y Autómatas II
* Catedrático: Ing. Roberto Hernandéz Pérez
* Equipo: 6
*   Lugo Martinez Saul Isui
*   Tomás Hernández Dulce Alejandra
*
* Programa: Analizador sintáctico p/CFG
* Gramática a^+b.
*/
public class AnalizadorSintacticpEAS extends javax.swing.JFrame {
    /*VARIABLES GLOBALES + PROGRAMA*/
    public char x; //A simbolo de salida de la cadena
    public int i = 0; //Inicializa el primer simbolo
    public String s; //La cadena
    public boolean valida; //Valor booleano de la cadena
    ArrayList<String> Simbolo = new ArrayList<String>();
    ArrayList<String> Tokens = new ArrayList<String>();
    ArrayList<Integer> NoCaracter = new ArrayList<Integer>();

    public AnalizadorSintacticpEAS() {
        initComponents();
    }    

    //Metodo para el siguiente caracter
    public char nextCaracter(){
        if(i < s.length()){
            x = s.charAt(i);
            this.GuardaNum(i);
            i++;
        } else {
            x = '*';
        }
        return x;
    }

    //El error de sintaxis
    public void Error(){
        //Append imprimime una linea en el jTextArea
        jTextArea1.append("Error de sintaxis \n");
        jTextArea1.append(s+"\n");                
        for (int j = 0; j < i-1; j++) {
            //Posisiona el texto en la siguiente linea
            jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
            jTextArea1.append(" ");
        }
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
        jTextArea1.append("^ \n");                
    }

    //Producción S
    public boolean S(){
        valida = false;
        if(x == 'a') {
            x = nextCaracter();
            String simbolo = String.valueOf(x);
            this.GuardaSimbolo(simbolo);
            this.GuardaToken("SNT");
            if (B()){
                valida = false;
                x = nextCaracter();
                if(x == '*'){
                    valida = true;
                }
            } else {
            valida = S();
        }
        }
        return valida;
    }

    //Función B
    public boolean B(){
        valida = false;
        if(x == 'b'){
            valida = true;
            String simbolo = String.valueOf(x);
            this.GuardaSimbolo(simbolo);
            this.GuardaToken("ST");
        }
        return valida;
    }

    //Funcion que llama al método S
    public void Llama(){
        s = jTextField1.getText();
        x = nextCaracter();
        if(S()){
            if(i==0){
                //Posisiona el texto en la siguiente linea
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                jTextArea1.append("Es una cadena vacia"+"\n");
                s = null;
                i = 0;
            } else {
                //Posisiona el texto en la siguiente linea
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                jTextArea1.append("Cadena valida: "+s+"\n");
                s = null;
                i = 0;
            }
        } else {
            if(i==0){
                System.out.println("Algo anda mal");
            } else {
                this.Error();
            }
        }
    }
    
    //IMPRIMIR EN TABLA DE RESULTADOS
    public void Imprime() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            int i;
            jTextField1.setText("");
            for (i = 0; i < Tokens.size(); i++) {
                //Imprime en cada columna, i es la fila
                jTable1.setValueAt(Tokens.get(i), i, 0);
                jTable1.setValueAt(Simbolo.get(i), i, 1);
                jTable1.setValueAt(NoCaracter.get(i), i, 2);                                
            }
                        
        } catch (Exception e) {
            System.out.println("Error en imprime: = " + e);
        }
    }
    
    
    //Limpiar campos
    public void Limpiar(){
        jTextField1.setText(null);
        jTextArea1.setText(null);
        i = 0;
        s = null;
    }
    
        //Método para guardar los datos de entrada
    public void GuardaSimbolo(String cadena){
        Simbolo.add(cadena);
    }
       
    //Metodo para guardar tokens
    public void GuardaToken(String token){
        Tokens.add(token);
    }

    //Metodo para guardar el no. de caracter
    public void GuardaNum(int num){
        NoCaracter.add(num);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 51, 153))); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Ingresa la cadena");

        jButton1.setText("INGRESA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("REINICIA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/EncabezadoLAII2.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Imagen2.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Analizador sintáctico - Equipo 6");

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
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "TOKEN", "LEX. ÚNICO", "No. Caracter"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton3.setText("Simbolos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(155, 155, 155))
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(31, 31, 31)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(73, 73, 73)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.Llama();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.Limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.Imprime();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticpEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticpEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticpEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnalizadorSintacticpEAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnalizadorSintacticpEAS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
