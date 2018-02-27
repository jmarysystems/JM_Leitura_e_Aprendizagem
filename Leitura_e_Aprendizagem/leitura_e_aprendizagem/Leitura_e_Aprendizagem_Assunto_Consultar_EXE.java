
package leitura_e_aprendizagem;

import br.com.jmary.home.Home;
import br.com.jmary.home.beans.Assunto;
import br.com.jmary.home.beans.Categoria;
import br.com.jmary.home.beans.Materia;
import br.com.jmary.home.beans.Subcategoria;
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
import br.com.jmary.utilidades.Exportando;
import br.com.jmary.utilidades.JOPM;
import imagens.Imagens_Menu_Norte;
import imagens_internas.Imagens_Internas;
import java.awt.Color;
import java.net.URL;
import java.util.List;
import javax.persistence.Query;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author AnaMariana
 */
public class Leitura_e_Aprendizagem_Assunto_Consultar_EXE extends javax.swing.JPanel {
    
    Home Home;
    
    /** Creates new form SombraVendas
     * @param Home2 */
    public Leitura_e_Aprendizagem_Assunto_Consultar_EXE( Home Home2 ) {
        initComponents();
        
        Home = Home2;
        
        jpExcluirSelecionado.setVisible(false);
        
        setarUrl_e_ImageIcon_Seta_Inicio();
        tabelaInicio();
        
        setarCBMateria("tudo",0);
    }
    
    private ListSelectionModel lsmPesquisa;
    public DefaultTableModel   tmPesquisa = new DefaultTableModel( null, new String[]{ 
        "ID", "MATERIA", "ENDEREÇO DO ARQUIVO HTML"        
    } );
    
    private void setarCBMateria( String comando, int idParaSelecionar ){        
        /*new Thread() {   @Override public void run() {*/ try {  
            switch(comando){
                
                case "tudo":
                    if( cbMateria.getItemCount() > 0 ) { cbMateria.removeAllItems(); }
                    
                    List<Materia> Lista_Book = null;
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE MATERIA LIKE ?", Materia.class );
                        q.setParameter(1, "%"+""+"%");            
                        Lista_Book = q.getResultList();
                    }catch(Exception e){}
                
                    String material_do_db = ""; try{ material_do_db = Lista_Book.get(0).getMateria(); }catch( Exception e ){ material_do_db = ""; }
                    
                    if( !material_do_db.equals("") ){
                                                            
                        for (int i = 0; i < Lista_Book.size(); i++){ 
                            cbMateria.addItem( new BookInfo2( 
                                    Lista_Book.get(i).getId(),
                                    Lista_Book.get(i).getMateria()
                                )
                            );    
                        }
                        
                        int id_materia = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                            id_materia = obj.ID;
                        }catch(Exception e){}
                        setarCBCategoria("id_materia",id_materia);
                    }
                    else{
                        
                        if(cbCategoria.getItemCount() > 0){ cbCategoria.removeAllItems(); }
                        //if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break;  
                
                case "id_materia":
                    if( cbMateria.getItemCount() > 0 ) { cbMateria.removeAllItems(); }
                    
                    List<Materia> Lista_Book2 = null;
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                        q.setParameter(1, idParaSelecionar);            
                        Lista_Book2 = q.getResultList();
                    }catch(Exception e){}
                
                    String material_do_db2 = ""; try{ material_do_db2 = Lista_Book2.get(0).getMateria(); }catch( Exception e ){ material_do_db2 = ""; }
                    
                    if( !material_do_db2.equals("") ){
                                                            
                        for (int i = 0; i < Lista_Book2.size(); i++){ 
                            cbMateria.addItem( new BookInfo2( 
                                    Lista_Book2.get(i).getId(),
                                    Lista_Book2.get(i).getMateria()
                                )
                            );    
                        }
                        
                        for (int i = 0; i < cbMateria.getItemCount(); i++){
                
                            int id_materia = 0;
                            try{
                                BookInfo2 obj = (BookInfo2) cbMateria.getItemAt(i);
                                id_materia = obj.ID;
                            }catch(Exception e){}
                            //System.out.println( "I: " + i + " - " +  "obj:" + obj.ID + " -- Assunto.getIdMateria(): " + Assunto.getIdMateria() );
                            if( id_materia == idParaSelecionar ){
                    
                                cbMateria.setSelectedIndex(i);
                                break;
                            }
                        }
                        
                        int id_materia = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                            id_materia = obj.ID;
                        }catch(Exception e){}
                        setarCBCategoria("id_materia",id_materia);
                    }
                    else{
                        
                        if(cbCategoria.getItemCount() > 0){ cbCategoria.removeAllItems(); }
                        //if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
                
                case "selecionar":
                    
                    if( cbMateria.getItemCount() > 0 ){
                        
                        for (int i = 0; i < cbMateria.getItemCount(); i++){
                
                            
                            int id_materia = 0;
                            try{
                                BookInfo2 obj = (BookInfo2) cbMateria.getItemAt(i);
                                id_materia = obj.ID;
                            }catch(Exception e){}
                            //System.out.println( "I: " + i + " - " +  "obj:" + obj.ID + " -- Assunto.getIdMateria(): " + Assunto.getIdMateria() );
                            if( id_materia == idParaSelecionar ){
                    
                                cbMateria.setSelectedIndex(i);
                                break;
                            }
                        }
                        
                        int id_materia = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                            id_materia = obj.ID;
                        }catch(Exception e){}
                        setarCBCategoria("id_materia",id_materia);
                    }
                    else{
                        
                        if(cbCategoria.getItemCount() > 0){ cbCategoria.removeAllItems(); }
                        //if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
            }
        } catch( Exception e ){e.printStackTrace();} //} }.start();       
    }
    
    private void setarCBCategoria( String comando, int idParaSelecionar ){        
        /*new Thread() {   @Override public void run() {*/ try {  
            switch(comando){
                
                case "tudo":
                    if( cbCategoria.getItemCount() > 0 ) { cbCategoria.removeAllItems(); }
                    
                    List<Categoria> Lista_Book = null;
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.CATEGORIA WHERE CATEGORIA LIKE ?", Categoria.class );
                        q.setParameter(1, "%"+""+"%");            
                        Lista_Book = q.getResultList();
                    }catch(Exception e){}
                
                    String material_do_db = ""; try{ material_do_db = Lista_Book.get(0).getCategoria(); }catch( Exception e ){ material_do_db = ""; }
                    
                    if( !material_do_db.equals("") ){
                                                            
                        for (int i = 0; i < Lista_Book.size(); i++){ 
                            cbCategoria.addItem( new BookInfo2( 
                                    Lista_Book.get(i).getId(),
                                    Lista_Book.get(i).getCategoria()
                                )
                            );    
                        }
                        
                        int id_categoria = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbCategoria.getSelectedItem();
                            id_categoria = obj.ID;
                        }catch(Exception e){}
                        setarCBSubcategoria("id_categoria",id_categoria);
                    }
                    else{
                        
                        if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break;  
                
                case "id_materia":
                    if( cbCategoria.getItemCount() > 0 ) { cbCategoria.removeAllItems(); }
                    
                    List<Categoria> Lista_Book2 = null;
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.CATEGORIA WHERE ID_MATERIA = ?", Categoria.class );
                        q.setParameter(1, idParaSelecionar);            
                        Lista_Book2 = q.getResultList();
                    }catch(Exception e){}
                
                    String material_do_db2 = ""; try{ material_do_db2 = Lista_Book2.get(0).getCategoria(); }catch( Exception e ){ material_do_db2 = ""; }
                    
                    if( !material_do_db2.equals("") ){
                                                            
                        for (int i = 0; i < Lista_Book2.size(); i++){ 
                            cbCategoria.addItem( new BookInfo2( 
                                    Lista_Book2.get(i).getId(),
                                    Lista_Book2.get(i).getCategoria()
                                )
                            );    
                        }
                        
                        for (int i = 0; i < cbCategoria.getItemCount(); i++){
                            
                            int id_categoria = 0;
                            try{
                                BookInfo2 obj = (BookInfo2) cbCategoria.getItemAt(i);
                                id_categoria = obj.ID;
                            }catch(Exception e){}
                            //System.out.println( "I: " + i + " - " +  "obj:" + obj.ID + " -- Assunto.getIdMateria(): " + Assunto.getIdMateria() );
                            if( id_categoria == idParaSelecionar ){
                    
                                cbCategoria.setSelectedIndex(i);
                                break;
                            }
                        }
                        
                        int id_categoria = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbCategoria.getSelectedItem();
                            id_categoria = obj.ID;
                        }catch(Exception e){}
                        setarCBSubcategoria("id_categoria",id_categoria);
                    }
                    else{
                        
                        if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
                
                case "selecionar":
                    
                    if( cbCategoria.getItemCount() > 0 ){
                        
                        for (int i = 0; i < cbCategoria.getItemCount(); i++){
                
                            int id_categoria = 0;
                            try{
                                BookInfo2 obj = (BookInfo2) cbCategoria.getItemAt(i);
                                id_categoria = obj.ID;
                            }catch(Exception e){}
                            //System.out.println( "I: " + i + " - " +  "obj:" + obj.ID + " -- Assunto.getIdMateria(): " + Assunto.getIdMateria() );
                            if( id_categoria == idParaSelecionar ){
                    
                                cbCategoria.setSelectedIndex(i);
                                break;
                            }
                        }
                        
                        int id_categoria = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbCategoria.getSelectedItem();
                            id_categoria = obj.ID;
                        }catch(Exception e){}
                        setarCBSubcategoria("id_categoria",id_categoria);
                    }
                    else{
                        
                        if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
            }
        } catch( Exception e ){} //} }.start();       
    }
    
    private void setarCBSubcategoria( String comando, int idParaSelecionar ){        
        /*new Thread() {   @Override public void run() {*/ try {  
            switch(comando){
                
                case "tudo":
                    if( cbSubcategoria.getItemCount() > 0 ) { cbSubcategoria.removeAllItems(); }
                    
                    List<Subcategoria> Lista_Book = null;
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.SUBCATEGORIA WHERE SUBCATEGORIA LIKE ?", Subcategoria.class );
                        q.setParameter(1, "%"+""+"%");            
                        Lista_Book = q.getResultList();
                    }catch(Exception e){}
                
                    String material_do_db = ""; try{ material_do_db = Lista_Book.get(0).getSubcategoria(); }catch( Exception e ){ material_do_db = ""; }
                    
                    if( !material_do_db.equals("") ){
                                                            
                        for (int i = 0; i < Lista_Book.size(); i++){ 
                            cbSubcategoria.addItem( new BookInfo2( 
                                    Lista_Book.get(i).getId(),
                                    Lista_Book.get(i).getSubcategoria()
                                )
                            );    
                        }
                        
                        int id_subcategoria = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbSubcategoria.getSelectedItem();
                            id_subcategoria = obj.ID;
                        }catch(Exception e){}
                        //setarCBAssunto("id_subcategoria",id_subcategoria);
                    }
                    else{
                        
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break;  
                
                case "id_categoria":
                    if( cbSubcategoria.getItemCount() > 0 ) { cbSubcategoria.removeAllItems(); }
                    
                    List<Subcategoria> Lista_Book2 = null;
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.SUBCATEGORIA WHERE ID_CATEGORIA = ?", Subcategoria.class );
                        q.setParameter(1, idParaSelecionar);            
                        Lista_Book2 = q.getResultList();
                    }catch(Exception e){}
                
                    String material_do_db2 = ""; try{ material_do_db2 = Lista_Book2.get(0).getSubcategoria(); }catch( Exception e ){ material_do_db2 = ""; }
                    
                    //System.out.println(material_do_db2 + " - setarCBSubcategoria( String comando, int idParaSelecionar )");
                    if( !material_do_db2.equals("") ){
                                
                        //System.out.println(material_do_db2 + " -2 setarCBSubcategoria( String comando, int idParaSelecionar )");
                        for (int i = 0; i < Lista_Book2.size(); i++){ 
                            cbSubcategoria.addItem( new BookInfo2( 
                                    Lista_Book2.get(i).getId(),
                                    Lista_Book2.get(i).getSubcategoria()
                                )
                            );    
                        }
                        
                        for (int i = 0; i < cbSubcategoria.getItemCount(); i++){
                            
                            int id_subcategoria = 0;
                            try{
                                BookInfo2 obj = (BookInfo2) cbSubcategoria.getItemAt(i);
                                id_subcategoria = obj.ID;
                            }catch(Exception e){}
                            //System.out.println( "I: " + i + " - " +  "obj:" + obj.ID + " -- Categoria.getIdMateria(): " + Categoria.getIdMateria() );
                            if( id_subcategoria == idParaSelecionar ){
                    
                                cbSubcategoria.setSelectedIndex(i);
                                break;
                            }
                        }
                        
                        int id_subcategoria = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbSubcategoria.getSelectedItem();
                            id_subcategoria = obj.ID;
                        }catch(Exception e){}
                        //setarCBAssunto("id_subcategoria",id_subcategoria);
                    }
                    else{
                        
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
                
                case "selecionar":
                    
                    if( cbSubcategoria.getItemCount() > 0 ){
                        
                        for (int i = 0; i < cbSubcategoria.getItemCount(); i++){
                
                            int id_subcategoria = 0;
                            try{
                                BookInfo2 obj = (BookInfo2) cbSubcategoria.getItemAt(i);
                                id_subcategoria = obj.ID;
                            }catch(Exception e){}
                            //System.out.println( "I: " + i + " - " +  "obj:" + obj.ID + " -- Categoria.getIdMateria(): " + Categoria.getIdMateria() );
                            if( id_subcategoria == idParaSelecionar ){
                    
                                cbSubcategoria.setSelectedIndex(i);
                                break;
                            }
                        }
                        
                        int id_subcategoria = 0;
                        try{
                            BookInfo2 obj = (BookInfo2) cbSubcategoria.getSelectedItem();
                            id_subcategoria = obj.ID;
                        }catch(Exception e){}
                        //setarCBAssunto("id_subcategoria",id_subcategoria);
                    }
                    else{
                        
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
            }
        } catch( Exception e ){} //} }.start();       
    }
    
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
                
                jpExcluirSelecionado.setVisible(true);
                
                
                procurar_valor_tabela_selecionada();
                
            } else{

                Assunto = null;
                
                jpExcluirSelecionado.setVisible(false);
                
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
        jButton7 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        tfPesquisarPeloNome = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        bt_criar_exe1 = new javax.swing.JLabel();
        cbSubcategoria = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cbMateria = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jpExcluirSelecionado = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btAlterar1 = new javax.swing.JButton();
        btAlterar2 = new javax.swing.JButton();
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

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/pesquisar.gif"))); // NOI18N
        jButton7.setText("Pesquisar");
        jButton7.setPreferredSize(new java.awt.Dimension(127, 26));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton7MousePressed(evt);
            }
        });

        tfPesquisarPeloNome.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tfPesquisarPeloNome.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfPesquisarPeloNome.setPreferredSize(new java.awt.Dimension(206, 26));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Nome Para Pesquisar - Assunto");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tfPesquisarPeloNome, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPesquisarPeloNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bt_criar_exe1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/olho.png"))); // NOI18N
        bt_criar_exe1.setText("Listar Todos");
        bt_criar_exe1.setToolTipText("Mostrar Todos na Tabela");
        bt_criar_exe1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bt_criar_exe1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bt_criar_exe1MousePressed(evt);
            }
        });

        cbSubcategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSubcategoriaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Subcategoria");

        cbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Categoria");

        cbMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMateriaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Matéria");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSubcategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_criar_exe1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(2, 2, 2)
                        .addComponent(cbMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8)
                        .addGap(2, 2, 2)
                        .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel12)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSubcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_criar_exe1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Opções do Assunto Selecionado");

        btAlterar1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btAlterar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/java.gif"))); // NOI18N
        btAlterar1.setText("Fazer Exercícios");
        btAlterar1.setPreferredSize(new java.awt.Dimension(109, 26));
        btAlterar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btAlterar1MousePressed(evt);
            }
        });

        btAlterar2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btAlterar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/novo.gif"))); // NOI18N
        btAlterar2.setText("Criar Exercícios ");
        btAlterar2.setPreferredSize(new java.awt.Dimension(109, 26));
        btAlterar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btAlterar2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jpExcluirSelecionadoLayout = new javax.swing.GroupLayout(jpExcluirSelecionado);
        jpExcluirSelecionado.setLayout(jpExcluirSelecionadoLayout);
        jpExcluirSelecionadoLayout.setHorizontalGroup(
            jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addGroup(jpExcluirSelecionadoLayout.createSequentialGroup()
                .addGroup(jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAlterar2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpExcluirSelecionadoLayout.setVerticalGroup(
            jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpExcluirSelecionadoLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(btAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btAlterar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jp_Opcoes_TabelaLayout = new javax.swing.GroupLayout(jp_Opcoes_Tabela);
        jp_Opcoes_Tabela.setLayout(jp_Opcoes_TabelaLayout);
        jp_Opcoes_TabelaLayout.setHorizontalGroup(
            jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpExcluirSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        jp_Opcoes_TabelaLayout.setVerticalGroup(
            jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addGroup(jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpExcluirSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Leitura e Aprendizagem - Exercícios");

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

        jTabbedPane1.addTab("Exercícios", Pergunta);

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

    Assunto Assunto = null;
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
    
    int id_SubcategoriaPesquisa = 0;
    boolean emexec = false;
    private void tfPesquisarPeloNome() {                                                       
        try{
            
            control = true;
            emexec  = true;
                                    
            if( tfPesquisarPeloNome.getText().trim().length() > 2 ){
                                
                try{ 
                    if( cbSubcategoria.getItemCount() > 0 ) {
                        BookInfo2 obj = (BookInfo2) cbSubcategoria.getSelectedItem();
                        id_SubcategoriaPesquisa = obj.ID;
                    }
                }catch(Exception e){}
                
if( id_SubcategoriaPesquisa > 0 ){                
                //Tabela_Pesquisa_BD_Estabelecimento.setarCabecalhoTabelaGenerica();
                
                new Thread() {   @Override public void run() { try { Thread.sleep( 100 );
                
                List<Assunto> Lista_Assunto = null;
                try{
                    Query q2 = JPAUtil.em().createNativeQuery("SELECT * FROM JM.ASSUNTO WHERE ASSUNTO LIKE ? AND ID_SUBCATEGORIA = ?", Assunto.class );
                    q2.setParameter( 1, tfPesquisarPeloNome.getText().trim().toUpperCase() ); 
                    q2.setParameter(2, id_SubcategoriaPesquisa ); 
                    Lista_Assunto = q2.getResultList();
                }catch( Exception e ){}      
                                    
                String rbusca = ""; try{ rbusca = Lista_Assunto.get(0).getAssunto(); }catch( Exception e ){}
                                
                if( !rbusca.equals("") ){
                    while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); } 
                    tmPesquisa = new DefaultTableModel( null, new String[]{ 
                        "ID", "ASSUNTO", "ENDEREÇO HTML ASSUNTO" } );
                    tbPesquisa.setModel(tmPesquisa);                    
                    tabela_PreferedSize();
                    
                    int id = 0;
                    String assunto = "";
                    String assunto_html = "";
                    
                    Exportando = new Exportando("PROCURANDO DADOS...");
                    Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
                    Exportando.pbg.setMaximum( Lista_Assunto.size() );

                    for (int i = 0; i < Lista_Assunto.size(); i++){                        
                        Exportando.pbg.setValue( i );                                                
////////////////////////////////////////////////////////////////////////////////                        
                        if(emexec == false){
                            tfPesquisarPeloNome();
                            Exportando.fechar(); break;
                        } 
////////////////////////////////////////////////////////////////////////////////                       
                        
                        try{ id             = Lista_Assunto.get(i).getId();                  }catch( Exception e ){}
                        try{ assunto      = Lista_Assunto.get(i).getAssunto();           }catch( Exception e ){}
                        try{ assunto_html = Lista_Assunto.get(i).getAssuntopaginahtml(); }catch( Exception e ){}

                        tmPesquisa.addRow(new Object[]{ id, assunto, assunto_html });
                    }
                    
                    Exportando.fechar();
                }
                else{
                    
                    while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); } 
                    tmPesquisa = new DefaultTableModel( null, new String[]{ 
                        "ID", "ASSUNTO", "ENDEREÇO HTML ASSUNTO" } );
                    tbPesquisa.setModel(tmPesquisa);                    
                    tabela_PreferedSize();
                }
                
                    control = false;                  
  
                }catch( Exception e ){ 
                    control = false;
                    e.printStackTrace(); 
                } } }.start();        
}
else{

    Exportando.fechar();
    ////////////////////////////////////////////////////////////////////
    Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
    JOPM JOptionPaneMod = new JOPM( 1, "PESQUISAR ASSUNTO\n"
            + "\nSTATUS DA PESQUISAR"
            + "\nNENHUM ASSUNTO LOCALIZADO!\n"
            + "\nSELECIONE UMA SUBCATEGORIA PRIMEIRO.\n"    
            + "\nOK PARA PROSSEGUIR."
            ,"Class: " + this.getClass().getName(),
            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
    ////////////////////////////////////////////////////////////////////
    cbMateria.requestFocus();
}
            
            }       
        
        } catch( Exception e ){ System.out.println( "tfPesquisarPeloNomeProdutoKeyReleased" ); }
    }
    
    private void jButton7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MousePressed
        tfPesquisarPeloNomeControl();
    }//GEN-LAST:event_jButton7MousePressed

    private void cbMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMateriaActionPerformed

        /*new Thread() {   @Override public void run() {*/ try {

            //MeuObjeto obj = (MeuObjeto) combobox.getSelectedItem();
            //BookInfoMateria obj = (BookInfoMateria) cbMateria.getSelectedItem();
            //System.out.println( "obj.ID "+obj.ID );
            int id_materia = 0;
                try{
                    BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                    id_materia = obj.ID;
                    System.out.println("id_materia = "+id_materia);
                }catch(Exception e){}
                setarCBCategoria("id_materia",id_materia);
        } catch( Exception e ){ } //} }.start();
    }//GEN-LAST:event_cbMateriaActionPerformed

    String materia_X = "";
    String subcaegoria_X = "";
    String assunto_X = "";
    private void btAlterar1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterar1MousePressed
        String assunto_t = materia_X + " > " + subcaegoria_X + " > " + assunto_X.toUpperCase();
        Home.fazer_Exercicios(assunto_t);
    }//GEN-LAST:event_btAlterar1MousePressed

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed
        new Thread() {   @Override public void run() { try {
            
            int id_categoria = 0;            
            try{
                BookInfo2 obj = (BookInfo2) cbCategoria.getSelectedItem();
                id_categoria = obj.ID;
                System.out.println("idcategoria = "+id_categoria);
                setarCBSubcategoria("id_categoria",id_categoria);
            }catch(Exception e){}
        } catch( Exception e ){ } } }.start();
    }//GEN-LAST:event_cbCategoriaActionPerformed

    private void cbSubcategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSubcategoriaActionPerformed
        new Thread() {   @Override public void run() { try {
            
            int idsubcategoria = 0;            
            try{
                BookInfo2 obj = (BookInfo2) cbSubcategoria.getSelectedItem();
                idsubcategoria = obj.ID;
                System.out.println("idsubcategoria = "+idsubcategoria);
            }catch(Exception e){}
        } catch( Exception e ){ } } }.start();
    }//GEN-LAST:event_cbSubcategoriaActionPerformed

    
    private void verificarAssunto( int id_x ){ 
        try {         
            
            List<Assunto> ListAssunto = null;            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.ASSUNTO WHERE ID = ?", Assunto.class );
                q.setParameter( 1, id_x );           
                ListAssunto = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListAssunto.get(0).getAssunto(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
            
                 assunto_X = ListAssunto.get(0).getAssunto();
                 verificarSubcategoria( ListAssunto.get(0).getIdSubcategoria() );
            }
            
        } catch ( Exception e ) {}
    }
    
    private void verificarSubcategoria( int id_x ){ 
        try {         
            
            List<Subcategoria> ListSubcategoria = null;                    
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.SUBCATEGORIA WHERE ID = ?", Subcategoria.class );
                q.setParameter( 1, id_x );            
                ListSubcategoria = q.getResultList();
            }catch(Exception e ){}
                    
            String rbuscaS = ""; try{ rbuscaS = ListSubcategoria.get(0).getSubcategoria(); }catch( Exception e ){}
                    
            if( !rbuscaS.equals("") ){
                    
                subcaegoria_X = ListSubcategoria.get(0).getSubcategoria();
                verificarMateria( ListSubcategoria.get(0).getIdMateria());
            }
            
        } catch ( Exception e ) {}
    }
    
    private void verificarMateria( int id_x ){ 
        try {         
            
            List<Materia> ListMateria = null;            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                q.setParameter(1, id_x );            
                ListMateria = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListMateria.get(0).getMateria(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
                
                materia_X = ListMateria.get(0).getMateria();
            }
            
        } catch ( Exception e ) {}
    }
    
    private void bt_criar_exe1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_criar_exe1MousePressed
        //copiar_Dados_Do_JTextPane("JTEXTPANE");
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 );

            Exportando = new Exportando("PESQUISANDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );
            
            int id_Materia = 0;
            try{ 
                if( cbMateria.getItemCount() > 0 ) {
                    BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                    id_Materia = obj.ID;
                }
            }catch(Exception e){}
            
if( id_Materia > 0 ){
    
            int id_Categoria = 0;
            try{ 
                if( cbCategoria.getItemCount() > 0 ) {
                    BookInfo2 obj = (BookInfo2) cbCategoria.getSelectedItem();
                    id_Categoria = obj.ID;
                }
            }catch(Exception e){}
    
if( id_Categoria > 0 ){      
    
    
            int id_subcategoria = 0;
            try{ 
                if( cbSubcategoria.getItemCount() > 0 ) {
                    BookInfo2 obj = (BookInfo2) cbSubcategoria.getSelectedItem();
                    id_subcategoria = obj.ID;
                }
            }catch(Exception e){}
            
if( id_subcategoria > 0 ){ 
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                List<Assunto> Lista_Assunto = null;
                try{
                    Query q2 = JPAUtil.em().createNativeQuery("SELECT * FROM JM.ASSUNTO WHERE ID_SUBCATEGORIA = ? AND ID_CATEGORIA = ? AND ID_MATERIA = ?", Assunto.class );
                    q2.setParameter( 1, id_subcategoria ); 
                    q2.setParameter( 2, id_Categoria ); 
                    q2.setParameter( 3, id_Materia ); 
                    Lista_Assunto = q2.getResultList();
                }catch( Exception e ){}      
                                    
                String rbusca = ""; try{ rbusca = Lista_Assunto.get(0).getAssunto(); }catch( Exception e ){}
                                
                if( !rbusca.equals("") ){
                    while ( tmPesquisa.getRowCount() > 0){ tmPesquisa.removeRow(0); } 
                    tmPesquisa = new DefaultTableModel( null, new String[]{ 
                        "ID", "ASSUNTO", "ENDEREÇO HTML ASSUNTO" } );
                    tbPesquisa.setModel(tmPesquisa);                    
                    tabela_PreferedSize();
                    
                    int id = 0;
                    String assunto = "";
                    String assunto_html = "";
                    
                    Exportando.fechar();
                    
                    Exportando = new Exportando("PROCURANDO DADOS...");
                    Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
                    Exportando.pbg.setMaximum( Lista_Assunto.size() );

                    for (int i = 0; i < Lista_Assunto.size(); i++){                        
                        Exportando.pbg.setValue( i );                                                                     
                        
                        try{ id           = Lista_Assunto.get(i).getId();                }catch( Exception e ){}
                        try{ assunto      = Lista_Assunto.get(i).getAssunto();           }catch( Exception e ){}
                        try{ assunto_html = Lista_Assunto.get(i).getAssuntopaginahtml(); }catch( Exception e ){}

                        tmPesquisa.addRow(new Object[]{ id, assunto, assunto_html });
                    }
                    
                    Exportando.fechar();
                }
                
                Exportando.fechar();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////    
                    
} 
else{

    Exportando.fechar();
    ////////////////////////////////////////////////////////////////////
    Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
    JOPM JOptionPaneMod = new JOPM( 1, "PESQUISANDO ASSUNTO\n"
            + "\nSTATUS DA PESQUISA"
            + "\nNENHUMA SUBCATEGORIA SELECIONADA!\n"
            + "\nSELECIONE UMA SUBCATEGORIA.\n"    
            + "\nOK PARA PROSSEGUIR."
            ,"Class: " + this.getClass().getName(),
            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
    ////////////////////////////////////////////////////////////////////
    cbSubcategoria.requestFocus();
}
            
            
}
else{

    Exportando.fechar();
    ////////////////////////////////////////////////////////////////////
    Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
    JOPM JOptionPaneMod = new JOPM( 1, "PESQUISANDO ASSUNTO\n"
            + "\nSTATUS DA PESQUISA"
            + "\nNENHUMA CATEGORIA SELECIONADA!\n"
            + "\nSELECIONE UMA CATEGORIA PARA PESQUISAR.\n"    
            + "\nOK PARA PROSSEGUIR."
            ,"Class: " + this.getClass().getName(),
            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
    ////////////////////////////////////////////////////////////////////
    cbCategoria.requestFocus();
}


}     
else{

    Exportando.fechar();
    ////////////////////////////////////////////////////////////////////
    Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
    JOPM JOptionPaneMod = new JOPM( 1, "PESQUISANDO ASSUNTO\n"
            + "\nSTATUS DA PESQUISA"
            + "\nNENHUMA MATÉRIA SELECIONADA!\n"
            + "\nSELECIONE UM MATÉIA.\n"    
            + "\nOK PARA PROSSEGUIR."
            ,"Class: " + this.getClass().getName(),
            new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
    ////////////////////////////////////////////////////////////////////
    cbMateria.requestFocus();
}
            
        } catch( Exception e ){ Exportando.fechar(); } } }.start(); 
    }//GEN-LAST:event_bt_criar_exe1MousePressed

    private void btAlterar2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterar2MousePressed
        String assunto_t = materia_X + " > " + subcaegoria_X + " > " + assunto_X.toUpperCase();
        Home.criar_exercicios(assunto_t);
    }//GEN-LAST:event_btAlterar2MousePressed
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pergunta;
    private javax.swing.JButton btAlterar1;
    private javax.swing.JButton btAlterar2;
    private javax.swing.JLabel bt_criar_exe1;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbMateria;
    private javax.swing.JComboBox cbSubcategoria;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpExcluirSelecionado;
    private javax.swing.JPanel jp_Opcoes_Tabela;
    public javax.swing.JTable tbPesquisa;
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
                                    
            List<Assunto> Lista_Book = null;
            try{ 
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.ASSUNTO WHERE ID = ?", Assunto.class );
                q.setParameter(1, id );
                Lista_Book = q.getResultList();
            }catch( Exception e ){ }
            
            String assunto_do_db = ""; try{ assunto_do_db = Lista_Book.get(0).getAssunto(); }catch( Exception e ){ assunto_do_db = ""; }
            if( !assunto_do_db.equals("") ){
                Assunto = Lista_Book.get(0);               
                //setarCBMateria("id_materia",Assunto.getIdMateria());                                   
                verificarAssunto( Assunto.getId() );
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