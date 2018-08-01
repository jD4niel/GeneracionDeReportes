
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
        radioOld.setSelected(true);
        radios();
        tablaUno();
        tablaDos();
       
    }
    public void tablaUno(){
         DefaultTableModel amodel = (DefaultTableModel) jTable1.getModel();
        amodel.setRowCount(0);

        try {
            Consulta mq = new Consulta();
            Connection con = mq.getConnection();
            Statement st = null;
            ResultSet rs;

            String colhead[] = {"Poblacion", "Luminaria", "Cantidad", "watz"};
            amodel.setColumnIdentifiers(colhead);
             Statement stm=con.createStatement();
            rs = stm.executeQuery("SELECT poblacion.nombre,luminarias.nombre,sum(pedido.cantidad),cotizaciones.watz FROM proyectos \n"
                    + "INNER JOIN cotizaciones on cotizaciones.id=proyectos.cotizaciones_id\n"
                    + "INNER JOIN pedido on pedido.id=cotizaciones.pedido_id\n"
                    + "INNER JOIN luminarias on luminarias.id=pedido.luminaria_id\n"
                    + "INNER JOIN municipio_poblacion ON municipio_poblacion.id=cotizaciones.mun_poblacion_id\n"
                    + "INNER JOIN poblacion ON poblacion.id=municipio_poblacion.poblacion_id\n"
                    + "INNER JOIN municipios ON municipios.id=municipio_poblacion.municipio_id WHERE cotizaciones.proyecto_ne=0\n"
                    + "GROUP BY poblacion.nombre,luminarias.nombre,cotizaciones.watz ORDER BY luminarias.nombre");

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
    public void tablaDos(){
         DefaultTableModel amodel = (DefaultTableModel) jTable2.getModel();
        amodel.setRowCount(0);

        try {
            Consulta mq = new Consulta();
            Connection con = mq.getConnection();
            Statement st = null;
            ResultSet rs;

            String colhead[] = {"Poblacion", "Luminaria", "Cantidad", "watz"};
            amodel.setColumnIdentifiers(colhead);
             Statement stm=con.createStatement();
            rs = stm.executeQuery("SELECT poblacion.nombre,luminarias.nombre,sum(pedido.cantidad),cotizaciones.watz FROM proyectos \n"
                    + "INNER JOIN cotizaciones on cotizaciones.id=proyectos.cotizaciones_id\n"
                    + "INNER JOIN pedido on pedido.id=cotizaciones.pedido_id\n"
                    + "INNER JOIN luminarias on luminarias.id=pedido.luminaria_id\n"
                    + "INNER JOIN municipio_poblacion ON municipio_poblacion.id=cotizaciones.mun_poblacion_id\n"
                    + "INNER JOIN poblacion ON poblacion.id=municipio_poblacion.poblacion_id\n"
                    + "INNER JOIN municipios ON municipios.id=municipio_poblacion.municipio_id WHERE cotizaciones.proyecto_ne=1\n"
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
    public void radios(){
        buttonGroup1.add(radioOld);
        buttonGroup1.add(radioNew);
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
            rs = st.executeQuery("SELECT max(" + tabla + ".id) FROM " + tabla);
            if (rs.next()) {
                valor = Integer.parseInt(rs.getString("max(" +tabla + ".id)"));
                return valor;
            } else {
                return 1;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
    public int regresaPoblacion(String estado,String municipio,String poblacion){
        try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            int valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT municipio_poblacion.id, poblacion.nombre, municipios.municipio, estados.estado FROM municipio_poblacion \n" +
                                "INNER JOIN municipios on municipios.id=municipio_poblacion.municipio_id\n" +
                                "INNER JOIN poblacion on poblacion.id=municipio_poblacion.poblacion_id\n" +
                                "INNER JOIN estados_municipios on estados_municipios.municipios_id=municipios.id\n" +
                                "INNER JOIN estados on estados_municipios.estados_id=estados.id\n" +
                                "WHERE poblacion.nombre='"+poblacion+"' and municipios.municipio='"+municipio+"' and estados.estado='"+estado+"'");
            if (rs.next()) {
                valor = Integer.parseInt(rs.getString("municipio_poblacion.id"));
                return valor;
            } else {
                return 0;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public int regresaID(String tabla, String campo, String nombreCampo) {
        try {
            Consulta query = new Consulta();
            Connection con = query.getConnection();
            Statement st;
            ResultSet rs;
            int valor;
            st = con.createStatement();
            rs = st.executeQuery("SELECT " + tabla + ".id FROM " + tabla + " WHERE "+nombreCampo+"='" + campo + "'");
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
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
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
        radioOld = new javax.swing.JRadioButton();
        radioNew = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        Enviar = new javax.swing.JButton();
        txtPrecioKwh = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cdp_tct = new javax.swing.JTextField();
        cdp_tctDos = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrecioKwhDos = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        c_estado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        c_municipio = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel6.setText("Luminaria");

        c_luminaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_luminariaActionPerformed(evt);
            }
        });

        jLabel7.setText("Watz");

        jLabel8.setText("Cantidad");

        txtWatz.setText("40");

        txtCantidad.setText("14");

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

        radioOld.setText("Municipio");

        radioNew.setText("Proyecto NOVA ELECTRIC");

        jRadioButton1.setText("TODOS");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

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
                        .addComponent(c_poblacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(c_luminaria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFecha))
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
                .addComponent(radioOld)
                .addGap(33, 33, 33)
                .addComponent(radioNew)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioOld)
                    .addComponent(radioNew))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_poblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jRadioButton1))
                .addGap(19, 19, 19)
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
                .addComponent(btnAddPoblacion)
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        Enviar.setText("Calcular");
        Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarActionPerformed(evt);
            }
        });

        txtPrecioKwh.setText("3.71");
        txtPrecioKwh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioKwhActionPerformed(evt);
            }
        });

        jLabel10.setText("Precio KHW");

        jLabel11.setText("CDP");

        cdp_tct.setText("20");
        cdp_tct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdp_tctActionPerformed(evt);
            }
        });

        cdp_tctDos.setText("10");
        cdp_tctDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdp_tctDosActionPerformed(evt);
            }
        });

        jLabel12.setText("CDP");

        txtPrecioKwhDos.setText("3.71");
        txtPrecioKwhDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioKwhDosActionPerformed(evt);
            }
        });

        jLabel13.setText("Precio KHW");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(Enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecioKwh, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cdp_tct))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecioKwhDos, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cdp_tctDos)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrecioKwhDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(cdp_tctDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cdp_tct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrecioKwh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Enviar)
                .addContainerGap())
        );

        c_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_estadoActionPerformed(evt);
            }
        });

        jLabel2.setText("Estado");

        jLabel1.setText("Municipio");

        c_municipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_municipioActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(c_municipio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_municipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_estadoActionPerformed
        c_municipio.removeAllItems();
        String estado = c_estado.getSelectedItem().toString();
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
        Consulta con = new Consulta();
        String estado = c_estado.getSelectedItem().toString();
        String municipio = c_municipio.getSelectedItem().toString();
        String poblacion = c_poblacion.getSelectedItem().toString();
        String luminaria = c_luminaria.getSelectedItem().toString();
        int watz = Integer.parseInt(txtWatz.getText());
        String fecha = txtFecha.getText();
        int cantidad = Integer.parseInt(txtCantidad.getText());
        float preciok = Float.parseFloat(txtPrecioKwh.getText());
        float carga_watz=1, carga_25=1, total_carga=1, consumo_promedio=1, total_lamparas=1;
        int tipo=0;
                
        
        
        
        
        if(radioOld.isSelected()){tipo=0;}else if(radioNew.isSelected()){tipo=1;}
        int luminariaId = regresaID("luminarias", luminaria,"nombre");
        Pedido pedido = new Pedido(luminariaId, cantidad);
        int mun_poblacion = regresaPoblacion(estado, municipio, poblacion);
        
        
         //Proyectos( carga_watz, carga_25, total_carga, consumo_promedio, total_lamparas) {
   
        Insertar in = new Insertar();

        try {
            in.insertarPedido(pedido);
            Cotizacion cotizacion = new Cotizacion(fecha, watz, mun_poblacion, ultimaID("pedido"),tipo);
            in.insertarCot(cotizacion);
            
        carga_watz=con.cargaWatz(estado, municipio, poblacion, tipo);
        carga_25=con.caraVeintiCinco(estado, municipio, poblacion, tipo);
        total_carga=carga_25+carga_watz;
        consumo_promedio=(total_carga/1000)*12;
        total_lamparas=con.totalLuminarias(estado, municipio, poblacion, tipo);
        /*********************************************/
        System.out.println("poblacion: "+ poblacion);
        System.out.println("luminaria: " + luminaria);
        System.out.println("carga_watz: " + carga_watz);
        System.out.println("carga_25: " + carga_25);
        System.out.println("total_carga: " + total_carga);
        System.out.println("consumo_promedio: " + consumo_promedio);
        System.out.println("total_lamparas: " + total_lamparas);
        System.out.println("");
            System.out.println(" - - - - - - - - - - - - - - - - - - - - ");
        Proyectos proyecto = new Proyectos(ultimaID("cotizaciones"), preciok,carga_watz, carga_25, total_carga, consumo_promedio, total_lamparas);
       
            in.insertarProyecto(proyecto);
            tablaUno();
            tablaDos();
        } catch (SQLException ex) {
            Logger.getLogger(Calculos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAddPoblacionActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarActionPerformed
        String estado = c_estado.getSelectedItem().toString();
        String municipio = c_municipio.getSelectedItem().toString();
        float cdp = Float.parseFloat(cdp_tct.getText());
        float costoK = Float.parseFloat(txtPrecioKwh.getText());
        
        float cdpdos = Float.parseFloat(cdp_tctDos.getText());
        float costoKdos = Float.parseFloat(txtPrecioKwhDos.getText());
        float importe=cdpdos*31*costoKdos;
        float importedos=cdp*31*costoK;
        System.out.println("cdp: " + cdp + " x 31 = " + cdp*31 + " por "+costoK + " = "+cdp*31*costoK   );
        Resultado res = new Resultado();
        Consulta con = new Consulta();
        res.totalLuminariasFinalUno(estado, municipio, 1);
        res.importeTot(importe,importedos,estado,municipio);
        res.setVisible(true);
        
        this.hide();
    }//GEN-LAST:event_EnviarActionPerformed

    private void txtPrecioKwhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioKwhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioKwhActionPerformed

    private void cdp_tctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdp_tctActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdp_tctActionPerformed

    private void cdp_tctDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdp_tctDosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdp_tctDosActionPerformed

    private void txtPrecioKwhDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioKwhDosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioKwhDosActionPerformed

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
    private javax.swing.JButton Enviar;
    private javax.swing.JButton btnAddPoblacion;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> c_estado;
    private javax.swing.JComboBox<String> c_luminaria;
    private javax.swing.JComboBox<String> c_municipio;
    private javax.swing.JComboBox<String> c_poblacion;
    private javax.swing.JTextField cdp_tct;
    private javax.swing.JTextField cdp_tctDos;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JRadioButton radioNew;
    private javax.swing.JRadioButton radioOld;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtPrecioKwh;
    private javax.swing.JTextField txtPrecioKwhDos;
    private javax.swing.JTextField txtWatz;
    // End of variables declaration//GEN-END:variables

    /* - - - - - functions - - - -- - */
}
