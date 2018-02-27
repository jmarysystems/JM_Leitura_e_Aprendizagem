
package leitura_e_aprendizagem;

import br.com.jmary.home.Home;
import br.com.jmary.home.beans.Materia;
import br.com.jmary.home.jpa.DAOGenericoJPA;
import br.com.jmary.home.jpa.JPAUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import br.com.jmary.utilidades.Exportando;
import br.com.jmary.utilidades.ExtensionFileFilter;
import br.com.jmary.utilidades.JOPM;
import imagens.Imagens_Menu_Norte;
import imagens_internas.Imagens_Internas;
import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author AnaMariana
 */
public class Leitura_e_Aprendizagem_Materia_Consultar extends javax.swing.JPanel {
    
    Home Home;
    
    /** Creates new form SombraVendas
     * @param Home2 */
    public Leitura_e_Aprendizagem_Materia_Consultar( Home Home2 ) {
        initComponents();
        
        Home = Home2;
        
        jpExcluirSelecionado.setVisible(false);
        
        setarUrl_e_ImageIcon_Seta_Inicio();
        tabelaInicio();       
    }
    
    private ListSelectionModel lsmPesquisa;
    public DefaultTableModel   tmPesquisa = new DefaultTableModel( null, new String[]{ 
        "ID", "MATERIA", "ENDEREÇO DO ARQUIVO HTML"        
    } );
    
    private void setarUrl_e_ImageIcon_Seta_Inicio(){
        try{                       
            
            imgURL  = Imagens_Menu_Norte.class.getResource("seta_para_baixo.png");
            icon    = new ImageIcon( imgURL );  
            
            imgURL2  = Imagens_Menu_Norte.class.getResource("seta_para_cima.png");
            icon2   = new ImageIcon( imgURL2 ); 
            
        } catch( Exception e ){ JOPM JOptionPaneMod = new JOPM( 2, "setarUrl_e_ImageIcon(), \n"
                + e.getMessage() + "\n", "Alterar_Seta_Submenu" ); }
    }
    
    private void tabelaInicio(){
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
                    
            tbPesquisa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lsmPesquisa = tbPesquisa.getSelectionModel();
            
            tbPesquisa.setAutoCreateRowSorter(true);

            tabelaModoDeSelecao( "Multiple Interval Selection", false, false, false );
            
            tabela_PreferedSize(); 
            
            ////////////////////////////////////////////////////////////////////
            lsmPesquisa.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if ( !e.getValueIsAdjusting() ){ 
                        tbPesquisaLinhaSelecionada(); 
                    }
                }
            });
            ////////////////////////////////////////////////////////////////////
            
        } catch( InterruptedException e ){ JOPM JOptionPaneMod = new JOPM( 2, "tabelaInicio(), \n"
                + e.getMessage() + "\n", this.getClass().getSimpleName() ); } } }.start();              
    }
    
    private void tabela_PreferedSize(){        
        try{
            DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
            rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);
        
            DefaultTableCellRenderer rendererDireita = new DefaultTableCellRenderer();
            rendererDireita.setHorizontalAlignment(SwingConstants.RIGHT);
            
            tbPesquisa.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbPesquisa.getColumnModel().getColumn(0).setResizable(true);
            tbPesquisa.getColumnModel().getColumn(0).setCellRenderer( rendererCentro );
            
            tbPesquisa.getColumnModel().getColumn(1).setPreferredWidth(130);
            tbPesquisa.getColumnModel().getColumn(1).setResizable(true);
            tbPesquisa.getColumnModel().getColumn(1).setCellRenderer( rendererCentro );
            
            tbPesquisa.getColumnModel().getColumn(2).setPreferredWidth(290);
            tbPesquisa.getColumnModel().getColumn(2).setResizable(true);
            tbPesquisa.getColumnModel().getColumn(2).setCellRenderer( rendererCentro );
                
            ///////////////////////////////////////////////////////////////////////////
            tbPesquisa.setRowHeight(30);
            tbPesquisa.setSelectionBackground(Color.YELLOW);
            tbPesquisa.setSelectionForeground(Color.BLUE);
            
            tbPesquisa.getTableHeader().setReorderingAllowed(true);
            //tbPesquisa.getTableHeader().setResizingAllowed(true);            
            tbPesquisa.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        } catch( Exception e ){}
    }
    
    public void tabelaModoDeSelecao( String command, boolean linhaCheck, boolean colunaCheck, boolean selecionar_celula ) {
        
        if ("Row Selection".equals(command)) {
            tbPesquisa.setRowSelectionAllowed(linhaCheck);
            //In MIS mode, column selection allowed must be the
            //opposite of row selection allowed.
            if (!selecionar_celula) {
                tbPesquisa.setColumnSelectionAllowed(!linhaCheck);
            }
        } else if ("Column Selection".equals(command)) {
            tbPesquisa.setColumnSelectionAllowed(colunaCheck);
            //In MIS mode, row selection allowed must be the
            //opposite of column selection allowed.
            if (!selecionar_celula) {
                tbPesquisa.setRowSelectionAllowed(!colunaCheck);
            }

        } else if ("Cell Selection".equals(command)) {
            
            tbPesquisa.setCellSelectionEnabled(selecionar_celula);
        } else if ("Multiple Interval Selection".equals(command)) { 
            tbPesquisa.setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //If cell selection is on, turn it off.
            if (selecionar_celula) {
                selecionar_celula = false;
                tbPesquisa.setCellSelectionEnabled(false);
            }
            //And don't let it be turned back on.
            selecionar_celula = false;
        } else if ("Single Interval Selection".equals(command)) {
            tbPesquisa.setSelectionMode(
                    ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Cell selection is ok in this mode.
            selecionar_celula = true;
        } else if ("Single Selection".equals(command)) { 
            tbPesquisa.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION);
            //Cell selection is ok in this mode.
            selecionar_celula = true;                           
        }      
    }
         
    private void tbPesquisaLinhaSelecionada() { 
        try{
            
            if ( tbPesquisa.getSelectedRow() != -1){

                btAlterar.setEnabled(true);
                btBuscarHtml.setEnabled(true);
                
                tfMateria.setEditable(true);
                
                jpExcluirSelecionado.setVisible(true);
                
                procurar_valor_tabela_selecionada();
                
                jLabel10.setEnabled(true);
                lbFiltro1.setEnabled(true);
                jLabel9.setEnabled(true);
                lbLinha_Tabela.setEnabled(true);
                jLabel14.setEnabled(true);
                lbFiltro2.setEnabled(true);
                jLabel6.setEnabled(true);
                lbColuna_Tabela.setEnabled(true);
            } else{

                btAlterar.setEnabled(false);
                btBuscarHtml.setEnabled(false);
                
                tfMateria.setEditable(false);
                
                tfMateria.setText("");
                tfPaHtml.setText("");
                Materia = null;
                
                jpExcluirSelecionado.setVisible(false);
                
                jLabel10.setEnabled(false);
                lbFiltro1.setEnabled(false);
                jLabel9.setEnabled(false);
                lbLinha_Tabela.setEnabled(false);
                jLabel14.setEnabled(false);
                lbFiltro2.setEnabled(false);
                jLabel6.setEnabled(false);
                lbColuna_Tabela.setEnabled(false);
            }
        } catch( Exception e ) {}
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        Pergunta = new javax.swing.JPanel();
        jp_Opcoes_Tabela = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel15 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfMateria = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        btAlterar = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        tfPaHtml = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btBuscarHtml = new javax.swing.JButton();
        jp_Opcoes = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lb_android = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbFiltro1 = new javax.swing.JLabel();
        lbFiltro2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbLinha_Tabela = new javax.swing.JLabel();
        lbColuna_Tabela = new javax.swing.JLabel();
        lbImportacao = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel18 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        tfPesquisarPeloNome = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jpExcluirSelecionado = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btAlterar1 = new javax.swing.JButton();
        bt_criar_exe1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPesquisa = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(812, 499));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nome da Matéria");

        tfMateria.setEditable(false);
        tfMateria.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfMateria.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfMateria.setPreferredSize(new java.awt.Dimension(206, 26));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(tfMateria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        btAlterar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/salvar.gif"))); // NOI18N
        btAlterar.setText(" Alterar");
        btAlterar.setEnabled(false);
        btAlterar.setPreferredSize(new java.awt.Dimension(109, 26));
        btAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btAlterarMousePressed(evt);
            }
        });

        tfPaHtml.setEditable(false);
        tfPaHtml.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfPaHtml.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfPaHtml.setPreferredSize(new java.awt.Dimension(206, 26));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Página HTML");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPaHtml, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPaHtml, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btBuscarHtml.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btBuscarHtml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/pesquisar.gif"))); // NOI18N
        btBuscarHtml.setText("Buscar");
        btBuscarHtml.setEnabled(false);
        btBuscarHtml.setPreferredSize(new java.awt.Dimension(105, 26));
        btBuscarHtml.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btBuscarHtmlMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btBuscarHtml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAlterar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btBuscarHtml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jp_Opcoes.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(0, 153, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("IMPORTAR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(255, 0, 51));
        jPanel10.setPreferredSize(new java.awt.Dimension(130, 43));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("OPÇÕES");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(253, 254, 247));

        lb_android.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_android.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/android_24.png"))); // NOI18N
        lb_android.setToolTipText("IMPORTAR DA MEMÓRIA");
        lb_android.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lb_android.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lb_androidMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_android, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(lb_android))
        );

        jPanel13.setBackground(new java.awt.Color(253, 254, 247));

        jLabel10.setForeground(new java.awt.Color(0, 102, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/xlsx.png"))); // NOI18N
        jLabel10.setToolTipText("EXPORTAR PARA EXCEL");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.setEnabled(false);
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(0, 102, 0));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/printv.png"))); // NOI18N
        jLabel14.setToolTipText("IMPRIMIR ");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.setEnabled(false);
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel14MousePressed(evt);
            }
        });

        lbFiltro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/filtro2.png"))); // NOI18N
        lbFiltro1.setToolTipText("FILTRAR COLUNA - PELO ITEM SELECIONADO");
        lbFiltro1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbFiltro1.setEnabled(false);
        lbFiltro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbFiltro1MousePressed(evt);
            }
        });

        lbFiltro2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/filtro2reverso.png"))); // NOI18N
        lbFiltro2.setToolTipText("EXCLUIR TODOS DA COLUNA = ITEM SELECIONADO");
        lbFiltro2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbFiltro2.setEnabled(false);
        lbFiltro2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbFiltro2MousePressed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/exluir.gif"))); // NOI18N
        jLabel6.setToolTipText("EXCLUIR COLUNA SELECIONADA");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.setEnabled(false);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel6MousePressed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/zoom_out.gif"))); // NOI18N
        jLabel9.setToolTipText("PROCURAR E EXCLUIR");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.setEnabled(false);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
            }
        });

        lbLinha_Tabela.setForeground(new java.awt.Color(0, 102, 0));
        lbLinha_Tabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/tabela_horizontal.png"))); // NOI18N
        lbLinha_Tabela.setToolTipText("EXPORTAR PARA EXCEL");
        lbLinha_Tabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbLinha_Tabela.setEnabled(false);
        lbLinha_Tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbLinha_TabelaMousePressed(evt);
            }
        });

        lbColuna_Tabela.setForeground(new java.awt.Color(0, 102, 0));
        lbColuna_Tabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/tabela_vertical.png"))); // NOI18N
        lbColuna_Tabela.setToolTipText("EXPORTAR PARA EXCEL");
        lbColuna_Tabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColuna_Tabela.setEnabled(false);
        lbColuna_Tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbColuna_TabelaMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFiltro2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbColuna_Tabela)
                    .addComponent(lbLinha_Tabela)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFiltro1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLinha_Tabela, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFiltro2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColuna_Tabela, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbImportacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/xls.png"))); // NOI18N
        lbImportacao.setToolTipText("IMPORTAR DO EXCEL");
        lbImportacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbImportacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbImportacaoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jp_OpcoesLayout = new javax.swing.GroupLayout(jp_Opcoes);
        jp_Opcoes.setLayout(jp_OpcoesLayout);
        jp_OpcoesLayout.setHorizontalGroup(
            jp_OpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_OpcoesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jp_OpcoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jp_OpcoesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbImportacao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jp_OpcoesLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jp_OpcoesLayout.setVerticalGroup(
            jp_OpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_OpcoesLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_OpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbImportacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jp_OpcoesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel10, jPanel3});

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Nome Para Pesquisar");

        tfPesquisarPeloNome.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfPesquisarPeloNome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfPesquisarPeloNome.setPreferredSize(new java.awt.Dimension(206, 26));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tfPesquisarPeloNome, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPesquisarPeloNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/pesquisar.gif"))); // NOI18N
        jButton7.setText("Pesquisar");
        jButton7.setPreferredSize(new java.awt.Dimension(127, 26));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton7MousePressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Excluir Matéria Selecionada");

        btAlterar1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btAlterar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/exluir.gif"))); // NOI18N
        btAlterar1.setText("Excluir");
        btAlterar1.setPreferredSize(new java.awt.Dimension(109, 26));
        btAlterar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btAlterar1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jpExcluirSelecionadoLayout = new javax.swing.GroupLayout(jpExcluirSelecionado);
        jpExcluirSelecionado.setLayout(jpExcluirSelecionadoLayout);
        jpExcluirSelecionadoLayout.setHorizontalGroup(
            jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpExcluirSelecionadoLayout.createSequentialGroup()
                .addGroup(jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jpExcluirSelecionadoLayout.setVerticalGroup(
            jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpExcluirSelecionadoLayout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bt_criar_exe1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/olho.png"))); // NOI18N
        bt_criar_exe1.setText("Listar Todas");
        bt_criar_exe1.setToolTipText("Listar Todas As Matérias");
        bt_criar_exe1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bt_criar_exe1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bt_criar_exe1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jp_Opcoes_TabelaLayout = new javax.swing.GroupLayout(jp_Opcoes_Tabela);
        jp_Opcoes_Tabela.setLayout(jp_Opcoes_TabelaLayout);
        jp_Opcoes_TabelaLayout.setHorizontalGroup(
            jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                        .addGroup(jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpExcluirSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_criar_exe1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jp_Opcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jp_Opcoes_TabelaLayout.setVerticalGroup(
            jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addGroup(jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jp_Opcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(bt_criar_exe1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpExcluirSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Consultar - Matéria - Leitura");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/seta_para_cima.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jScrollPane2.setBorder(null);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(350, 112));

        tbPesquisa.setModel(tmPesquisa);
        tbPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPesquisaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbPesquisa);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PerguntaLayout = new javax.swing.GroupLayout(Pergunta);
        Pergunta.setLayout(PerguntaLayout);
        PerguntaLayout.setHorizontalGroup(
            PerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerguntaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_Opcoes_Tabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PerguntaLayout.setVerticalGroup(
            PerguntaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerguntaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_Opcoes_Tabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Consultar", Pergunta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void abrir_Resposta(String icon){
        try { 
            Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
            JOptionPane pane = new JOptionPane( new ImageIcon( clazzHome.getResource(icon)) );
            final JDialog dialog = pane.createDialog( Home, "" ); 
            dialog.setModal(true);
            Timer timer = new Timer(2 * 1000, new ActionListener() {  
                @Override
                public void actionPerformed(ActionEvent ev) {  
                    dialog.dispose();   
                }  
            });  
            timer.setRepeats(false);  
            timer.start();  
            dialog.show();  
            timer.stop(); 
        } catch( Exception e ){ }
    }
        
    Exportando Exportando;                                            
    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MousePressed
    
        if( jLabel10.isEnabled() == true ) { 

            acesso_nao_autorizado();
        }
        else{
                
            acesso_nao_autorizado();
        }
        /*
    new Thread() { @Override public void run() { 
        
        int numLinhas = -1; try{ numLinhas = tbPesquisa.getRowCount();    } catch( Exception e ){}
        int numColuna = -1; try{ numColuna = tbPesquisa.getColumnCount(); } catch( Exception e ){}
        if ( numLinhas != -1){
            try {         
                Arquivo_Ou_Pasta.criarPasta(System.getProperty("user.dir"), "Arquivos_Exportados");
                Thread.sleep( 1 );
                String entrada = System.getProperty("user.dir") + "//"+ "Arquivos" + "//" + 
                        "Modelo_Promocionais_Evento_Elenco_Consulta" + ".xlsx";   
                String saida = System.getProperty("user.dir") + "//"+ "Arquivos_Exportados" + "//" + 
                        "Promocionais_Evento_Elenco_Consulta" + ".xlsx";  
                Arquivo_Ou_Pasta.deletar(new File(saida));
                Thread.sleep( 1 );
                            
                Exportando = new Exportando("EXPORTANDO DADOS...");
                Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
                                                               
                FileInputStream fileIn = null;
                FileOutputStream fileOut = null;                            
                            
                try{
                                
                    fileIn = new FileInputStream( entrada );
                    XSSFWorkbook wb = new XSSFWorkbook(fileIn); 
                    XSSFSheet aba = wb.getSheetAt(0);   
                                
                    Exportando.pbg.setMaximum( numLinhas );
                                                                                    
                    XSSFCell cell;  
                    for (int i = -1; i < numLinhas; i++) { 
                        int linhaParaescrever = i+2;
                        XSSFRow linha = aba.getRow(linhaParaescrever);
                        //XSSFRow linha = aba.createRow(i+1);
                                    
                        for (int j = 0; j < numColuna ; j++) { 
                                        
                            cell = linha.getCell(j); 
                            if (cell == null) cell = linha.createCell(j);
                                        
                                try{ cell.setCellType(XSSFCell.CELL_TYPE_STRING); }catch(Exception e){}
                                try{ cell.setCellValue(XSSFCell.CELL_TYPE_STRING); }catch(Exception e){}
                                        
                                if(i==-1){
                                    cell.setCellValue(String.valueOf(tbPesquisa.getColumnName(j)));
                                }else{
                                            
                                    cell.setCellValue(String.valueOf(tbPesquisa.getValueAt(i, j)));
                                }
                        }
                        
                        Exportando.pbg.setValue( i );                                
                    }
                                                                
                    wb.setForceFormulaRecalculation(true);
                    fileOut = new FileOutputStream( saida );
                    wb.write(fileOut);
                                
                    try{
                        fileOut.close(); 
                        fileIn.close(); 
                    } catch(Exception e) {}
                                    
                    java.awt.Desktop.getDesktop().open( new File( saida ) ); 
                } catch(Exception e) {}
                                                                    
                Exportando.fechar();                   
            }catch( Exception e ){ e.printStackTrace(); }
        }    
        else{
            
            Class<imagens_internas.Imagens_Internas> clazzHome = imagens_internas.Imagens_Internas.class;
                    JOPM JOptionPaneMod = new JOPM( 1, "EXPORTAR PARA EXCEL\n"
                            + "\nSTATUS DA EXPORTAÇÃO:"
                            + "\nPARA EXPORTAR É NECESSÁRIO QUE A TABELA NÃO ESTEJA VAZIA!\n"
                            + "\nOK Para Prosseguir"
                            ,"Class: " + this.getClass().getName(), 
                            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
        }
    } }.start();
    */
    }//GEN-LAST:event_jLabel10MousePressed

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MousePressed
        acesso_nao_autorizado();
    }//GEN-LAST:event_jLabel14MousePressed

    private void lbFiltro2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFiltro2MousePressed
        if ( tbPesquisa.getSelectedRow() != -1){

            int c = tbPesquisa.getSelectedColumn();

            String str = String.valueOf( tbPesquisa.getValueAt(tbPesquisa.getSelectedRow(), tbPesquisa.getSelectedColumn()) );
            filtroReverso( str, c );
        }
        else{

            Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
            JOPM JOptionPaneMod = new JOPM( 1, "FILTRO REVERSO TABELA\n"

                + "\nPara filtrar dados da tabela 1º selecione uma célula\n"
                + "\nOK Para Prosseguir"
                ,"Class: " + this.getClass().getName(),
                new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
        }
    }//GEN-LAST:event_lbFiltro2MousePressed

    private void lbFiltro1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFiltro1MousePressed
        if ( tbPesquisa.getSelectedRow() != -1){

            int c = tbPesquisa.getSelectedColumn();

            String str = String.valueOf( tbPesquisa.getValueAt(tbPesquisa.getSelectedRow(), tbPesquisa.getSelectedColumn()) );
            filtro( str, c );
        }
        else{

            Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
            JOPM JOptionPaneMod = new JOPM( 1, "FILTRA TABELA\n"

                + "\nPara filtrar dados da tabela 1º selecione uma célula\n"
                + "\nOK Para Prosseguir"
                ,"Class: " + this.getClass().getName(),
                new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
        }
    }//GEN-LAST:event_lbFiltro1MousePressed

    String filtro = "";
    int    coluna = 1;
    public void filtro( String f, int c ) { filtro = f; coluna = c;    
        try { filtro = f.trim(); } catch( Exception e ){}
        
        new Thread() {   @Override public void run() { try { 
            Exportando Exportando = new Exportando("APLICANDO O FILTRO...");
            Exportando.setVisible(true);
            Exportando.pbg.setMinimum(0);
            
            int numFila=tmPesquisa.getRowCount();
            
            Exportando.pbg.setMaximum( numFila );
            
            Thread.sleep( 1 );
                                                
            for (int i = 0; i < numFila; i++) {     Exportando.pbg.setValue( i );
                
                try{
                    
                    String str = String.valueOf( tmPesquisa.getValueAt(i, coluna) ).trim();  
                    
                    if( !str.equals(filtro) ){
                        
                        tmPesquisa.removeRow( i );       
                        i = -1;
                        //System.out.println( str + "  -------------  " + i + "  " + coluna);
                    }
                } catch( Exception e ){ }
                //System.out.println( "++++++++++++++  " + i + "  " + coluna);
            }

            Exportando.fechar();
            
        } catch( Exception e ){ System.out.println("Exportar - "); e.printStackTrace(); } } }.start();  
    }
    
    String filtroReverso = "";
    int    colunaReverso = 1;
    public void filtroReverso( String f, int c ) { colunaReverso = c;    
        try { filtroReverso = f.trim(); } catch( Exception e ){}
        
        new Thread() {   @Override public void run() { try { 
            Exportando Exportando = new Exportando("APLICANDO O FILTRO...");
            Exportando.setVisible(true);
            Exportando.pbg.setMinimum(0);
            
            int numFila=tmPesquisa.getRowCount();
            
            Exportando.pbg.setMaximum( numFila );
            
            Thread.sleep( 1 );
                                                
            for (int i = 0; i < numFila; i++) {     Exportando.pbg.setValue( i );
                
                try{
                    
                    String str = String.valueOf( tmPesquisa.getValueAt(i, colunaReverso) ).trim();  
                    
                    if( str.equals(filtroReverso) ){
                        
                        tmPesquisa.removeRow( i );       
                        i = -1;
                        //System.out.println( str + "  -------------  " + i + "  " + coluna);
                    }
                } catch( Exception e ){ }
                //System.out.println( "++++++++++++++  " + i + "  " + coluna);
            }

            Exportando.fechar();
            
        } catch( InterruptedException e ){ System.out.println("Exportar - "); e.printStackTrace(); } } }.start();  
    }
    
    private void jLabel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MousePressed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );

            if ( tbPesquisa.getSelectedRow() != -1){
                int coluna = tbPesquisa.getSelectedColumn();
                TableColumn TColuna = tbPesquisa.getColumnModel().getColumn( coluna );
                tbPesquisa.removeColumn( TColuna );
            }
            else{
                
                Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "EXCLUIR COLUNA DA TABELA\n"
                        + "\nPARA EXCLUIR UMA COLUNA\n"
                        + "\nPRIMEIRO SELECIONE UMA\n"
                        + "\nOK Para Prosseguir"
                        ,"Class: " + this.getClass().getName(),
                        new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
            }
            
        } catch( InterruptedException e ){  } } }.start();
    }//GEN-LAST:event_jLabel6MousePressed

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed
    
        if( jLabel9.isEnabled() == true ) { 

            acesso_nao_autorizado();
        }
        else{
                
            acesso_nao_autorizado();
        }
        /*
    if ( tbPesquisa.getSelectedRow() != -1){
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
        
            Exportando = new Exportando("Procurando e Excluindo");
            Exportando.setVisible(true);
            Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );                            
            ////////////////////////////////////////////////////////////////
            String contentsX = jTextPane1.getText().trim();
            
        if(!contentsX.equals("")){    
                
            StringTokenizer st1=new StringTokenizer(contentsX,"\n");
 
            int contadorElenco;
            for (int i = 0; st1.hasMoreTokens(); i++) {
                contadorElenco = 0;
                                            
                String rowstring = st1.nextToken();
//              //System.out.println( "rowstring - " + rowstring );
                try{                            
                    String[] dados = rowstring.split("\t");                        
                    contadorElenco++;
                    if(contadorElenco < 2){
                        String material_procurado = dados[0].trim(); 
                        Exportando.pbg.setValue( tmPesquisa.getRowCount() );
///////////////////////////////////////////////////////////////////////////////////////////////////////////
                        for( int L_i=0; L_i < tmPesquisa.getRowCount(); L_i++ ){
                            Exportando.pbg.setValue(L_i);
                            
                            for( int C_i=0; C_i < tmPesquisa.getColumnCount(); C_i++ ){ try{
                                String strn = tmPesquisa.getColumnName(C_i);
                                
                                if( strn.trim().equalsIgnoreCase("MATERIAL") ){
                                    String sap = String.valueOf( tmPesquisa.getValueAt(L_i, C_i) ).trim();
                                
                                    if( material_procurado.equals(sap) ){
                                    
                                        tmPesquisa.removeRow( L_i );
                                    }
                                }
                            } catch( Exception e ){  } }    
                        }                     
/////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                    }                                
                } catch( Exception e ){ e.printStackTrace(); }
                                                
            }   
                
            Exportando.fechar();
            Class<imagens_internas.Imagens_Internas> clazzHome = imagens_internas.Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "EXCLUSÃO DE DADOS\n"
                        + "\nSTATUS DA EXCLUSÃO:"
                        + "\nFINALIZADA COM SUCESSO!\n"
                        + "\nOK Para Prosseguir"
                        ,"Class: " + this.getClass().getName(), 
                        new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
        }     
        else{
            Class<imagens_internas.Imagens_Internas> clazzHome = imagens_internas.Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "EXCLUSÃO DE DADOS\n"
                        + "\nSTATUS DA EXCLUSÃO:"
                        + "\nNÃO HÁ DADOS INFORMADO PARA PROCURA!\n"
                        + "\nOK Para Prosseguir"
                        ,"Class: " + this.getClass().getName(), 
                        new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
        }
        } catch( InterruptedException e ){ Exportando.fechar(); } } }.start(); 
    }
    else{
                
        Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
        JOPM JOptionPaneMod = new JOPM( 1, "EXCLUSÃO DE DADOS\n"
                + "\nPARA EXCLUIR DADOS DA TABELA\n"
                + "\nPRIMEIRO INFORME O NÚMERO DOS MATERIAIS\n"
                + "\nOK Para Prosseguir"
                ,"Class: " + this.getClass().getName(),
                new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
        }
    */
    }//GEN-LAST:event_jLabel9MousePressed

    private void lbLinha_TabelaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLinha_TabelaMousePressed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );

            if ( tbPesquisa.getSelectedRow() != -1){
                tabelaModoDeSelecao( "Column Selection",true, false, false );
            }
            else{
                
                Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "ALTERAR MODO DE SELEÇÃO\n"
                        + "\nPARA ALTERAR O MODO DE SELEÇÃO\n"
                        + "\nPRIMEIRO SELECIONE UMA CÉLULA\n"
                        + "\nOK Para Prosseguir"
                        ,"Class: " + this.getClass().getName(),
                        new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
            }
            
        } catch( InterruptedException e ){  } } }.start();           
    }//GEN-LAST:event_lbLinha_TabelaMousePressed

    private void lbColuna_TabelaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbColuna_TabelaMousePressed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );

            if ( tbPesquisa.getSelectedRow() != -1){
                tabelaModoDeSelecao( "Row Selection",false, true, false );
            }
            else{
                
                Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "ALTERAR MODO DE SELEÇÃO\n"
                        + "\nPARA ALTERAR O MODO DE SELEÇÃO\n"
                        + "\nPRIMEIRO SELECIONE UMA CÉLULA\n"
                        + "\nOK Para Prosseguir"
                        ,"Class: " + this.getClass().getName(),
                        new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
            }
            
        } catch( InterruptedException e ){  } } }.start();   
    }//GEN-LAST:event_lbColuna_TabelaMousePressed

    private void lb_androidMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_androidMousePressed
        if( lb_android.isEnabled() == true ) { 

            acesso_nao_autorizado();
        }
        else{
                
            acesso_nao_autorizado();
        }       
    }//GEN-LAST:event_lb_androidMousePressed

    private void lbImportacaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbImportacaoMousePressed
        
        new Thread() {   @Override public void run() { try { 

            if( lbImportacao.isEnabled() == true ) { 

                acesso_nao_autorizado();
            }
            else{
                
                acesso_nao_autorizado();
            }
        } catch( Exception e ){} } }.start();
    }//GEN-LAST:event_lbImportacaoMousePressed
     
    private URL       imgURL; 
    private ImageIcon icon;            
    private URL       imgURL2;
    private ImageIcon icon2;
    private boolean cimabaixo = true; 
    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        try{

            if( cimabaixo == false ){
                cimabaixo = true;
                
                jLabel3.setToolTipText( "Ocultar Submenu" );
                jLabel3.setIcon( icon2 );              
                
                jp_Opcoes_Tabela.setVisible(true);
            } else if( cimabaixo == true ){
                cimabaixo = false;
                                
                jLabel3.setToolTipText( "Mostrar Submenu" );
                jLabel3.setIcon( icon );  
                
                jp_Opcoes_Tabela.setVisible(false);
            }
            
        } catch( Exception e ){ JOPM JOptionPaneMod = new JOPM( 2, "tabJanelaSubmenu, \n"
                + e.getMessage() + "\n", "Alterar_Seta_Submenu" ); }
    }//GEN-LAST:event_jLabel3MousePressed

    private void tbPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPesquisaKeyReleased
        try{

            if ( tbPesquisa.getSelectedRow() != -1){

                if( evt.getKeyCode() == KeyEvent.VK_DELETE )  {

                    int linhaC  = tbPesquisa.getSelectedRow();
                    int colunaC = tbPesquisa.getSelectedColumn();

                    tbPesquisa.setValueAt( "", linhaC, colunaC );
                }
            }
        } catch( Exception e ){}

    }//GEN-LAST:event_tbPesquisaKeyReleased

    private void btBuscarHtmlMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btBuscarHtmlMousePressed
        new Thread() {   @Override public void run() { try {
        if( btBuscarHtml.isEnabled() == true ){ 
            JFileChooser JFileChooserJMPasta = new JFileChooser();
            
            ///////////////////////////////////////////////////////////////////////////
            FileFilter filter = new ExtensionFileFilter ( "HTML", "html" ) ;
            JFileChooserJMPasta.setFileFilter ( filter ) ;
            JFileChooserJMPasta.setFileSelectionMode(JFileChooser.FILES_ONLY);
            ///////////////////////////////////////////////////////////////////////////
            
            int status = JFileChooserJMPasta.showSaveDialog(null);
                
            if( status == javax.swing.JFileChooser.CANCEL_OPTION ){ 

                tfPaHtml.setText("");
            }else if ( status == javax.swing.JFileChooser.APPROVE_OPTION ){
                    
                File arquivo = JFileChooserJMPasta.getSelectedFile();
                    
                String tempX = arquivo.getAbsolutePath().replace("\\", "/");
                
                String str[] = tempX.split("/");
                if( !str.equals("") ){
                        
                    for (int i = 0; i < str.length; i++){
                            
                        String opcao = str[i].trim();
                        if(opcao.equals("00_Externo")){
                                
                            StringBuilder temp = new StringBuilder();
                            temp.append("//");
                            int ctemp = 0;
                            for (int j = i; j < str.length; j++){
                                    
                                temp.append( str[j].trim() ); 
                                    
                                ctemp = str.length - 1;
                                if(j < ctemp){
                                        
                                    temp.append("//");
                                }
                            }

                            tfPaHtml.setText( temp.toString() );
                            break;
                        }
                    }
                }
            }
        }
        }catch( Exception e ){ e.printStackTrace(); } } }.start();
    }//GEN-LAST:event_btBuscarHtmlMousePressed

    Materia Materia = null;
    private void btAlterarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterarMousePressed
        //copiar_Dados_Do_JTextPane("JTEXTPANE");
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );

            Exportando = new Exportando("ALTERANDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );

            if( !tfMateria.getText().trim().equals("") ){

                if( !tfPaHtml.getText().trim().equals("") ){

                    List<Materia> Lista_Book = null;
                    try{ 
                        
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE MATERIA = ?", Materia.class );
                        q.setParameter(1, tfMateria.getText().trim().toUpperCase() );
                        Lista_Book = q.getResultList();
                    }catch( Exception e ){ }
                    
                    String material_do_db = ""; try{ material_do_db = Lista_Book.get(0).getMateria(); }catch( Exception e ){ material_do_db = ""; }
                    
                    if( material_do_db.equals("") ){
                        //Materia Materia = new Materia();
                        Materia.setMateria( tfMateria.getText().trim().toUpperCase() );
                        Materia.setMateriapaginahtml( tfPaHtml.getText().trim() );
                        ////////////////////////////////////////////////////////////////////

                        ////////////////////////////////////////////////////////////////////
                        DAOGenericoJPA DAOGenericoJPA = new DAOGenericoJPA(Materia, JPAUtil.em());
                        DAOGenericoJPA.gravanovoOUatualizar();
                    
                        Exportando.fechar();
                        ////////////////////////////////////////////////////////////////////
                        Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                        JOPM JOptionPaneMod = new JOPM( 1, "ALTERANDO MATÉRIA\n"
                            + "\nSTATUS DA ALTERAÇÃO"
                            + "\nMATÉRIA ALTERADA COM SUCESSO!\n"
                            + "\nOK PARA PROSSEGUIR."
                            ,"Class: " + this.getClass().getName(),
                            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
                        ////////////////////////////////////////////////////////////////////  
                    }
                    else{
                        
                        Exportando.fechar();
                        ////////////////////////////////////////////////////////////////////
                        Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                        JOPM JOptionPaneMod = new JOPM( 1, "ALTERANDO MATÉRIA\n"
                            + "\nSTATUS DA ALTERAÇÃO"
                            + "\nMATÉRIA NÃO ALTERADA!\n"
                            + "\nMATÉRIA JÁ EXISTE CADASTRADA\n"
                            + "\nOK PARA PROSSEGUIR."
                            ,"Class: " + this.getClass().getName(), 
                            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
                        ////////////////////////////////////////////////////////////////////
                    }
                }
                else{

                    Exportando.fechar();
                    ////////////////////////////////////////////////////////////////////
                    Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                    JOPM JOptionPaneMod = new JOPM( 1, "CRIANDO MATÉRIA\n"
                        + "\nSTATUS DA CRIAÇÃO"
                        + "\nPARA CRIAR A MATÉRIA INFORME O ENDEREÇO!\n"
                        + "\nOK PARA PROSSEGUIR."
                        ,"Class: " + this.getClass().getName(),
                        new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
                    ////////////////////////////////////////////////////////////////////
                    tfPaHtml.requestFocus();
                }
            }
            else{

                Exportando.fechar();
                ////////////////////////////////////////////////////////////////////
                Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "ALTERAÇÃO MATÉRIA\n"
                    + "\nSTATUS DA ALTERAÇÃO"
                    + "\nNENHUMA MATÉRIA SELECIONADA!\n"
                    + "\nSELECIONE UM MATÉRIA NA TABELA.\n"    
                    + "\nOK PARA PROSSEGUIR."
                    ,"Class: " + this.getClass().getName(),
                    new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
                ////////////////////////////////////////////////////////////////////
                tfMateria.requestFocus();
            }

        } catch( Exception e ){ Exportando.fechar(); } } }.start();
    }//GEN-LAST:event_btAlterarMousePressed

    boolean control = false;
    private void tfPesquisarPeloNomeControl() {  
        try{
            
            if(control == false){
                
                tfPesquisarPeloNome();
                //System.out.println("ENTER");                
            }
            else{
                
                emexec = false;
            }
            
        } catch( Exception e ){ e.printStackTrace(); }
    }
    
    boolean emexec = false;
    private void tfPesquisarPeloNome() {                                                       
        try{
            
            control = true;
            emexec  = true;
                                    
            if( tfPesquisarPeloNome.getText().trim().length() > 2 ){
                
                //Tabela_Pesquisa_BD_Estabelecimento.setarCabecalhoTabelaGenerica();
                
                new Thread() {   @Override public void run() { try { Thread.sleep( 100 );
                
                List<Materia> Lista_Materia = null;
                try{
                    Query q2 = JPAUtil.em().createNativeQuery("SELECT * FROM JM.MATERIA WHERE MATERIA LIKE ?", Materia.class );
                    q2.setParameter( 1, tfPesquisarPeloNome.getText().trim().toUpperCase() ); 
                    Lista_Materia = q2.getResultList();
                }catch( Exception e ){}      
                                    
                String rbusca = ""; try{ rbusca = Lista_Materia.get(0).getMateria(); }catch( Exception e ){}
                                
                if( !rbusca.equals("") ){
                    while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); } 
                    tmPesquisa = new DefaultTableModel( null, new String[]{ 
                        "ID", "MATERIA", "ENDEREÇO HTML MATERIA" } );
                    tbPesquisa.setModel(tmPesquisa);
                    
                    tabela_PreferedSize();
                    
                    int id = 0;
                    String materia = "";
                    String materia_html = "";
                    
                    Exportando = new Exportando("PROCURANDO DADOS...");
                    Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
                    Exportando.pbg.setMaximum( Lista_Materia.size() );

                    for (int i = 0; i < Lista_Materia.size(); i++){                        
                        Exportando.pbg.setValue( i );                                                
////////////////////////////////////////////////////////////////////////////////                        
                        if(emexec == false){
                            tfPesquisarPeloNome();
                            Exportando.fechar(); break;
                        } 
////////////////////////////////////////////////////////////////////////////////                       
                        
                        try{ id           = Lista_Materia.get(i).getId();                }catch( Exception e ){}
                        try{ materia      = Lista_Materia.get(i).getMateria();           }catch( Exception e ){}
                        try{ materia_html = Lista_Materia.get(i).getMateriapaginahtml(); }catch( Exception e ){}

                        tmPesquisa.addRow(new Object[]{ id, materia, materia_html });
                    }
                    
                    Exportando.fechar();
                }
                else{
                    
                    while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); } 
                    tmPesquisa = new DefaultTableModel( null, new String[]{ 
                        "ID", "CATEGORIA", "ENDEREÇO HTML CATEGORIA" } );
                    tbPesquisa.setModel(tmPesquisa);                    
                    tabela_PreferedSize();
                }
                
                    control = false;                  
  
                }catch( Exception e ){ 
                    control = false;
                    e.printStackTrace(); 
                } } }.start();        
            }
            
        } catch( Exception e ){ System.out.println( "tfPesquisarPeloNomeProdutoKeyReleased" ); }
    }
    
    private void jButton7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MousePressed
        tfPesquisarPeloNomeControl();
    }//GEN-LAST:event_jButton7MousePressed

    private void btAlterar1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterar1MousePressed
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
        
            int response = JOptionPane.showConfirmDialog(null, ""
                    + "**CONFIRMAR EXCLUSÃO**"
                    + "\n"
                    + "Deseja Excluir Esta Materia?");
            if( response == JOptionPane.YES_OPTION){ 
                
                try {
                    DAOGenericoJPA DAOGenericoJPA2 = new DAOGenericoJPA( Materia, JPAUtil.em()); 
                    DAOGenericoJPA2.excluir(); 
                }catch( Exception e ){} 
                
                ////////////////////////////////////////////////////////////////////
                Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "EXCLUINDO MATÉRIA\n"
                    + "\nSTATUS DA EXCLUSÃO"
                    + "\nMATÉRIA EXCLUÍDA COM SUCESSO!\n"
                    + "\nOK PARA PROSSEGUIR."
                    ,"Class: " + this.getClass().getName(), 
                    new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
                ////////////////////////////////////////////////////////////////////
                
                while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); } 
                tmPesquisa = new DefaultTableModel( null, new String[]{ 
                    "ID", "CATEGORIA", "ENDEREÇO HTML CATEGORIA" } );
                tbPesquisa.setModel(tmPesquisa);                    
                tabela_PreferedSize();
            }            
        } catch( Exception e ){ e.printStackTrace(); } } }.start();
    }//GEN-LAST:event_btAlterar1MousePressed

    private void bt_criar_exe1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_criar_exe1MousePressed
        //copiar_Dados_Do_JTextPane("JTEXTPANE");
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );

            Exportando = new Exportando("PESQUISANDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                List<Materia> Lista_Materia = null;
                try{
                    Query q2 = JPAUtil.em().createNativeQuery("SELECT * FROM JM.MATERIA", Materia.class );
                    Lista_Materia = q2.getResultList();
                }catch( Exception e ){}

                String rbusca = ""; try{ rbusca = Lista_Materia.get(0).getMateria(); }catch( Exception e ){}

                if( !rbusca.equals("") ){
                    while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); }
                    tmPesquisa = new DefaultTableModel( null, new String[]{
                        "ID", "MATERIA", "ENDEREÇO HTML MATERIA" } );
                tbPesquisa.setModel(tmPesquisa);
                tabela_PreferedSize();

                int id = 0;
                String materia = "";
                String materia_html = "";

                Exportando.fechar();

                Exportando = new Exportando("PROCURANDO DADOS...");
                Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
                Exportando.pbg.setMaximum( Lista_Materia.size() );

                for (int i = 0; i < Lista_Materia.size(); i++){
                    Exportando.pbg.setValue( i );

                    try{ id           = Lista_Materia.get(i).getId();                }catch( Exception e ){}
                    try{ materia      = Lista_Materia.get(i).getMateria();           }catch( Exception e ){}
                    try{ materia_html = Lista_Materia.get(i).getMateriapaginahtml(); }catch( Exception e ){}

                    tmPesquisa.addRow(new Object[]{ id, materia, materia_html });
                }

                Exportando.fechar();
            }

            Exportando.fechar();
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        } catch( Exception e ){ Exportando.fechar(); } } }.start();
    }//GEN-LAST:event_bt_criar_exe1MousePressed
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pergunta;
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btAlterar1;
    private javax.swing.JButton btBuscarHtml;
    private javax.swing.JLabel bt_criar_exe1;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpExcluirSelecionado;
    private javax.swing.JPanel jp_Opcoes;
    private javax.swing.JPanel jp_Opcoes_Tabela;
    private javax.swing.JLabel lbColuna_Tabela;
    public javax.swing.JLabel lbFiltro1;
    public javax.swing.JLabel lbFiltro2;
    private javax.swing.JLabel lbImportacao;
    private javax.swing.JLabel lbLinha_Tabela;
    private javax.swing.JLabel lb_android;
    public javax.swing.JTable tbPesquisa;
    private javax.swing.JTextField tfMateria;
    private javax.swing.JTextField tfPaHtml;
    private javax.swing.JTextField tfPesquisarPeloNome;
    // End of variables declaration//GEN-END:variables
       
    private void copiar_Dados_Do_JTextPane(final String daonde){ 
    new Thread() {   @Override public void run() { try { Thread.sleep( 1 );   
        
        try { 
            String contentsX = "";
            
            if( daonde.equalsIgnoreCase("JTEXTPANE") ){
                    
                //contentsX = jTextPane1.getText().trim();
            }
            else if( daonde.equalsIgnoreCase("MEMORIA") ){  
                    
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
                if ( hasTransferableText ) {
                    try { 
                        contentsX = (String)contents.getTransferData(DataFlavor.stringFlavor);
                    }catch (Exception e){}
                }     
            }
            ////////////////////////////////////////////////////////////////////
            int qtd_linhas = 0;
            try { 
                listar_dados_na_tabela( contentsX );
                qtd_linhas = 0; try{ qtd_linhas = tbPesquisa.getRowCount(); }catch(Exception e ){} 
            }catch( Exception e ){ }
            
            Exportando.fechar();
        } catch( Exception e ){ Exportando.fechar(); } //} }.start();
        
        //////////////////////////////////////////////////////////////////////////////////////////
        tabela_PreferedSize(); 
        
    } catch( InterruptedException e ){ } } }.start();  
    }
    
    private void listar_dados_na_tabela( String contentsX ){                  
        try {                                                             
                Exportando = new Exportando("Listando Dados");
                Exportando.setVisible(true);
                Exportando.pbg.setMinimum(0);
                Exportando.pbg.setMaximum( 100 );
                Exportando.pbg.setValue( 50 );
                
                while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); }                   
                ////////////////////////////////////////////////////////////////                
                StringTokenizer stX=new StringTokenizer(contentsX,"\n");                
                ///////////////////////////////////////////////////////////////
                
                int countColuna = 0;
                for( int i=0; stX.hasMoreTokens(); i++ ){               
                                        
                    String rowstring = stX.nextToken();
                    StringTokenizer st2 = new StringTokenizer(rowstring,"\t");
                    for( int j=0; st2.hasMoreTokens(); j++ ){
                        
                        if( i > 0 ){ break; }
                        
                        countColuna += 1;
                        String cellstring = st2.nextToken();
                    }
                                                                                                    
                }

                StringTokenizer st1=new StringTokenizer(contentsX,"\n");
                
                boolean primeiraVez = false;    
                for (int i = 0; st1.hasMoreTokens(); i++) {
                                            
                    String rowstring = st1.nextToken();

                    try{
                        
                        if( primeiraVez == false ){
                            primeiraVez = true;
                        
                            String[] dados = new String[countColuna];
                        
                            StringTokenizer st2 = new StringTokenizer(rowstring,"\t");
                            for( int j=0; st2.hasMoreTokens(); j++ ){
                            
                                String cellstring = ""; try{ cellstring = st2.nextToken(); } catch( Exception e ){ }
                                dados [j] = cellstring;
                            }                        
                        
                            tmPesquisa = new DefaultTableModel( null, dados );
                            tbPesquisa.setModel(tmPesquisa);
                        
                        }                        
                        else{
                            
                            String[] dados = new String[countColuna];
                                     dados = rowstring.split("\t");                                                      
                                                                                                            
                            tmPesquisa.addRow( dados ); 
                        }
                    } catch( Exception e ){ e.printStackTrace(); }
                                                
                }   
                
                Exportando.fechar();
        }catch(Exception e){ Exportando.fechar(); } //} }.start();
    }
    
    private void procurar_valor_tabela_selecionada() {                                              
        try {  
            
            int selecionada = tbPesquisa.getSelectedRow();
            String id = ""; try{ id = String.valueOf( tbPesquisa.getValueAt(selecionada, 0) ).trim(); }catch(Exception e){}
                                    
            List<Materia> Lista_Book = null;
            try{
                
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                q.setParameter(1, id );
                Lista_Book = q.getResultList();
            }catch( Exception e ){ }
            
            String material_do_db = ""; try{ material_do_db = Lista_Book.get(0).getMateria(); }catch( Exception e ){ material_do_db = ""; }
            
            if( !material_do_db.equals("") ){
                Materia = Lista_Book.get(0);
                
                tfMateria.setText( Materia.getMateria() );
                tfPaHtml.setText( Materia.getMateriapaginahtml() );
            }       
        }catch(Exception e){} 
    }
    
    private void acesso_nao_autorizado() {                                      
        Class<imagens_internas.Imagens_Internas> clazzHome = imagens_internas.Imagens_Internas.class;
        JOPM JOptionPaneMod = new JOPM( 1, "ACESSO NÃO AUTORIZADO\n"
                + "\nSTATUS DO ACESSO:"
                + "\nVOCÊ NÃO TEM ACESSO A ESTA SOLICITAÇÃO!\n"
                + "\nOK Para Prosseguir"
                ,"Class: " + this.getClass().getName(), 
                new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
    }
       
}