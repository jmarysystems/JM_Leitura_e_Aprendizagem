
package leitura_e_aprendizagem;

import br.com.jmary.home.Home;
import br.com.jmary.home.beans.Materia;
import br.com.jmary.home.jpa.JPAUtil;
import br.com.jmary.utilidades.Exportando;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import br.com.jmary.utilidades.JOPM;
import imagens.Imagens_Menu_Norte;
import java.net.URL;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JDialog;
/**
 *
 * @author AnaMariana
 */
public class Leitura_e_Aprendizagem_Materia_Consultar_EXE extends javax.swing.JPanel {
    
    Home Home;
    public JDialog Dialog;
    
    /** Creates new form SombraVendas
     * @param Home2
     * @param Dialog2 */
    public Leitura_e_Aprendizagem_Materia_Consultar_EXE( Home Home2, JDialog Dialog2 ) {
        initComponents();
        
        Home = Home2;
        Dialog = Dialog2;
        
        //jpExcluirSelecionado.setVisible(false);
        
        setarUrl_e_ImageIcon_Seta_Inicio();
        
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
                        //setarCBCategoria("id_materia",id_materia);
                    }
                    else{
                        
                        //if(cbCategoria.getItemCount() > 0){ cbCategoria.removeAllItems(); }
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
                        //setarCBCategoria("id_materia",id_materia);
                    }
                    else{
                        
                        //if(cbCategoria.getItemCount() > 0){ cbCategoria.removeAllItems(); }
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
                        //setarCBCategoria("id_materia",id_materia);
                    }
                    else{
                        
                        //if(cbCategoria.getItemCount() > 0){ cbCategoria.removeAllItems(); }
                        //if(cbSubcategoria.getItemCount() > 0){ cbSubcategoria.removeAllItems(); }
                        //if(cbAssunto.getItemCount() > 0){ cbAssunto.removeAllItems(); }
                    }
                break; 
            }
        } catch( Exception e ){e.printStackTrace();} //} }.start();       
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jp_Opcoes_Tabela = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        cbMateria = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jpExcluirSelecionado = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btAlterar1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(531, 227));

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 30)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Selecionar Matéria Para Leitura");

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

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cbMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMateriaActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Matéria");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Click Em Abrir Matéria");

        btAlterar1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btAlterar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/java.gif"))); // NOI18N
        btAlterar1.setText("Abrir Matéria");
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
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jpExcluirSelecionadoLayout.createSequentialGroup()
                .addComponent(btAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 47, Short.MAX_VALUE))
        );
        jpExcluirSelecionadoLayout.setVerticalGroup(
            jpExcluirSelecionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpExcluirSelecionadoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel16)
                .addGap(1, 1, 1)
                .addComponent(btAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpExcluirSelecionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(2, 2, 2)
                .addComponent(cbMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpExcluirSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens_leitura_e_aprendizagem/ciências.jpg"))); // NOI18N

        javax.swing.GroupLayout jp_Opcoes_TabelaLayout = new javax.swing.GroupLayout(jp_Opcoes_Tabela);
        jp_Opcoes_Tabela.setLayout(jp_Opcoes_TabelaLayout);
        jp_Opcoes_TabelaLayout.setHorizontalGroup(
            jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
        );
        jp_Opcoes_TabelaLayout.setVerticalGroup(
            jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_Opcoes_TabelaLayout.createSequentialGroup()
                .addGroup(jp_Opcoes_TabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jp_Opcoes_Tabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_Opcoes_Tabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
               
    private URL       imgURL; 
    private ImageIcon icon;            
    private URL       imgURL2;
    private ImageIcon icon2;
    private boolean cimabaixo = true; 
    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        /*
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
        */
    }//GEN-LAST:event_jLabel3MousePressed
        
    private void cbMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMateriaActionPerformed

        /*new Thread() {   @Override public void run() {*/ try {

            //MeuObjeto obj = (MeuObjeto) combobox.getSelectedItem();
            //BookInfoMateria obj = (BookInfoMateria) cbMateria.getSelectedItem();
            //System.out.println( "obj.ID "+obj.ID );
            int id_materia = 0;
                try{
                    BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                    id_materia = obj.ID;
                }catch(Exception e){}
                //setarCBCategoria("id_materia",id_materia);
        } catch( Exception e ){ } //} }.start();
    }//GEN-LAST:event_cbMateriaActionPerformed

    Exportando Exportando;
    private void btAlterar1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlterar1MousePressed
        new Thread() {   @Override public void run() { try { 
            Exportando = new Exportando("ABRINDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );
            
            int id_materia = 0;
            String materia = "";
                        
            List<Materia> Lista_Materia = null;
            try{
                BookInfo2 obj = (BookInfo2) cbMateria.getSelectedItem();
                id_materia = obj.ID;
                    
                Query q2 = JPAUtil.em().createNativeQuery("SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                q2.setParameter( 1, "MATEMÁTICA" ); 
                Lista_Materia = q2.getResultList();
            }catch( Exception e ){}      
                                    
            String rbusca = ""; try{ rbusca = Lista_Materia.get(0).getMateria(); }catch( Exception e ){}
                                
            if( !rbusca.equals("") ){
                id_materia      = Lista_Materia.get(0).getId();
                materia = Lista_Materia.get(0).getMateria();
            }
            
            Home.ControleTabs.AddTabsAoHome("Leitura", "/imagens_internas/livroTp.gif", 
                    new JPHtmlPrincipal(Home, materia, id_materia) ); 

            Dialog.dispose();
            Exportando.fechar();
                  
        } catch( Exception e ){ Dialog.dispose(); Exportando.fechar(); e.printStackTrace(); } } }.start();
    }//GEN-LAST:event_btAlterar1MousePressed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar1;
    private javax.swing.JComboBox cbMateria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel jpExcluirSelecionado;
    private javax.swing.JPanel jp_Opcoes_Tabela;
    // End of variables declaration//GEN-END:variables
       
}