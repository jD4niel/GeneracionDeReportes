
import Conexion.Consulta;
import Conexion.Insertar;
import Entidades.Cotizacion;
import Entidades.Pedido;
import Entidades.Proyectos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jdaniel
 */
public class Calculos extends javax.swing.JFrame {

    /**
     * Creates new form Calculos
     */
    public Calculos() {
        initComponents();
        BindCombo();
        FillLum();
        textFecha();
       
    }
    public void tablaUno(){
         DefaultTableModel amodel = (DefaultTableModel) jTable1.getModel();
        amodel.setRowCount(0);

        try {
            Consulta mq = new Consulta();
            Connection con = mq.getConnection();
            Statement st = null;
            ResultSet rs;

            String colhead[] = {"Poblacion", "Luminaria", "Cantidad", "watz","dsdd"};
            amodel.setColumnIdentifiers(colhead);
             Statement stm=con.createStatement();
            rs = stm.executeQuery("SELECT poblacion.nombre,luminarias.nombre,sum(pedido.cantidad),cotizaciones.watz FROM proyectos \n"
                    + "INNER JOIN cotizaciones on cotizaciones.id=proyectos.cotizaciones_id\n"
                    + "INNER JOIN pedido on pedido.id=cotizaciones.pedido_id\n"
                    + "INNER JOIN luminarias on luminarias.id=pedido.luminaria_id\n"
                    + "INNER JOIN municipio_poblacion ON municipio_poblacion.id=cotizaciones.mun_poblacion_id\n"
                    + "INNER JOIN poblacion ON poblacion.id=municipio_poblacion.poblacion_id\n"
                    + "INNER JOIN municipios ON municipios.id=municipio_poblacion.municipio_id\n"
                    + "GROUP BY poblacion.nombre,luminarias.nombre,pedido.cantidad,cotizaciones.watz");

           ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            while (rs.next()) {
                Object[] obj = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    obj[i] = rs.getObject(i + 1);
                }
                amodel.addRow(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void mostrar() {
        DefaultTableModel modelo = new DefaultTableModel();

        Consulta mq = new Consulta();
        Connection con = mq.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT  FROM cotizaciones");
            while (rs.next()) {
                c_luminaria.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void textFecha() {
        String fechaHoy = obtenerFechaFormateada(getFecha(), "dd/LL/yyyy");
        txtFecha.setText(fechaHoy);
    }

    public LocalDate getFecha() {
        LocalDate fecha = LocalDate.now();
        return fecha;
    }

    String obtenerFechaFormateada(LocalDate fecha, String formato) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formato);
        return fecha.format(dtf);
    }

    public void FillLum() {
        Consulta mq = new Consulta();
        Connection con = mq.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM luminarias");
            while (rs.next()) {
                c_luminaria.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FillPopulation() {
        c_poblacion.removeAllItems();
        String municipio_n = c_municipio.getSelectedItem().toString();
        String estado = c_estado.getSelectedItem().toString();
        System.out.println("muni" + municipio_n);
        System.out.println("estado " + estado);
        Consulta mq = new Consulta();
        Connection con = mq.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM poblacion "
                    + "INNER JOIN municipio_poblacion ON municipio_poblacion.poblacion_id=poblacion.id "
                    + "INNER JOIN municipios ON municipio_poblacion.municipio_id=municipios.id "
                    + "INNER JOIN estados_municipios ON estados_municipios.municipios_id=municipios.id "
                    + "INNER JOIN estados on estados_municipios.estados_id=estados.id "
                    + "WHERE municipios.municipio='" + municipio_n + "'"
                    + "and estados.estado='" + estado + "'");
            while (rs.next()) {
                c_poblacion.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int ultimaID(String tabla) {
        try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            int valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT " + tabla + ".id FROM " + tabla + "\n"
                    + "ORDER BY " + tabla + ".id DESC LIMIT 1");
            System.out.println("rs: " + rs.toString());
            if (rs.next()) {
                valor = Integer.parseInt(rs.getString(tabla + ".id"));
                return valor;
            } else {
                return 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int regresaID(String tabla, String campo) {
        try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            int valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT " + tabla + ".id FROM " + tabla + " WHERE nombre='" + campo + "'");
            System.out.println("rs: " + rs.toString());
            if (rs.next()) {
                valor = Integer.parseInt(rs.getString(tabla + ".id"));
                return valor;
            } else {
                return 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void BindCombo() {
        Consulta query = new Consulta();
        Connection con = query.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM estados");
            while (rs.next()) {
                c_estado.addItem(rs.getString("estado"));
            }
            c_estado.setSelectedItem("Hidalgo");

        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        c_estado = new javax.swing.JComboBox<>();
        c_municipio = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        c_luminaria = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtWatz = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        c_poblacion = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        btnAddPoblacion = new javax.swing.JButton();
        txtPrecioKwh = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Municipio");

        jLabel2.setText("Estado");

        c_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_estadoActionPerformed(evt);
            }
        });

        c_municipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_municipioActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(c_municipio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(c_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(c_municipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jButton1)
                .addContainerGap(224, Short.MAX_VALUE))
        );

        jLabel6.setText("Luminaria");

        c_luminaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_luminariaActionPerformed(evt);
            }
        });

        jLabel7.setText("Watz");

        jLabel8.setText("Cantidad");

        c_poblacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_poblacionActionPerformed(evt);
            }
        });

        jLabel5.setText("Población");

        jLabel9.setText("Fecha");

        btnAddPoblacion.setText("Agregar");
        btnAddPoblacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPoblacionActionPerformed(evt);
            }
        });

        jLabel10.setText("Precio KHW");

        jRadioButton1.setText("jRadioButton1");

        jRadioButton2.setText("jRadioButton2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(c_poblacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(c_luminaria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFecha)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPrecioKwh, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtWatz, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAddPoblacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(13, 13, 13))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jRadioButton1)
                .addGap(33, 33, 33)
                .addComponent(jRadioButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_poblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(c_luminaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtWatz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioKwh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(btnAddPoblacion))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_estadoActionPerformed
        c_municipio.removeAllItems();
        String estado = c_estado.getSelectedItem().toString();
        System.out.println("estado ; " + estado);
        Consulta mq = new Consulta();
        Connection con = mq.getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM municipios INNER JOIN estados_municipios ON estados_municipios.municipios_id = municipios.id\n"
                    + "INNER JOIN estados ON estados.id=estados_municipios.estados_id WHERE estados.estado='" + estado + "'");
            while (rs.next()) {
                c_municipio.addItem(rs.getString("municipio"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_c_estadoActionPerformed

    private void c_luminariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_luminariaActionPerformed

    }//GEN-LAST:event_c_luminariaActionPerformed

    private void c_municipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_municipioActionPerformed
        //aquiva
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                FillPopulation();
            }
        }, 1000);
    }//GEN-LAST:event_c_municipioActionPerformed

    private void c_poblacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_poblacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_poblacionActionPerformed

    private void btnAddPoblacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPoblacionActionPerformed
        // Agregar poblacion:
        String estado = c_estado.getSelectedItem().toString();
        String municipio = c_municipio.getSelectedItem().toString();
        String poblacion = c_poblacion.getSelectedItem().toString();
        String luminaria = c_luminaria.getSelectedItem().toString();
        int watz = Integer.parseInt(txtWatz.getText());
        String fecha = txtFecha.getText();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        float preciok = Float.parseFloat(txtPrecioKwh.getText());

        int luminariaId = regresaID("luminarias", luminaria);
        System.out.println("luminaria id: " + luminariaId);
        Pedido pedido = new Pedido(luminariaId, cantidad);
        Cotizacion cotizacion = new Cotizacion(fecha, watz, 1, ultimaID("pedido") + 1);
        Proyectos proyecto = new Proyectos(ultimaID("cotizaciones") + 1, preciok, 0);// int cotizacion, float preciok, boolean proyecto
        Insertar in = new Insertar();

        try {
            in.insertarPedido(pedido);
            in.insertarCot(cotizacion);
            in.insertarProyecto(proyecto);
            tablaUno();
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAddPoblacionActionPerformed

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
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPoblacion;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> c_estado;
    private javax.swing.JComboBox<String> c_luminaria;
    private javax.swing.JComboBox<String> c_municipio;
    private javax.swing.JComboBox<String> c_poblacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtPrecioKwh;
    private javax.swing.JTextField txtWatz;
    // End of variables declaration//GEN-END:variables

    /* - - - - - functions - - - -- - */
}
