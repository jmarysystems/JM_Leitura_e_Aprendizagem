/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jmary.home;

//import home_controle.ButtonTabComponentHome;
//import sistema.Sistema_Home;
//import home_controle.ControleTabs;
//import home_controle.PnTabImagemHome;
//import home_usuario_logado.Bean_Usuario_Logado;
import Sons.Som;
import br.com.jmary.home.jpa.JPAUtil;
import br.com.jmary.utilidades.Exportando;
import br.com.jmary.utilidades.JOPM;
import home_controle.ButtonTabComponentHome;
import home_controle.ControleTabs;
import home_controle.PnTabImagemHome;
import home_controle_menus_norte.Controle_de_Alterar_Menu_Norte;
import imagens_internas.Imagens_Internas;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javax.persistence.Query;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
//import utilidades.Administrador;
//import utilidades.JOPM;
//import utilidades_imagens.Imagens;

/**
 *
 * @author AnaMariana
 */
public class Home extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;
    
    public Controle_de_Alterar_Menu_Norte Controle_de_Alterar_Menu_Norte;
    public Som tocarSon;
    public ControleTabs ControleTabs;

    public JFrame frame;
    
    public Component Menu_Atual;
    
    /**
     * Creates new form PnHomeDesigner
     * @param frame2
     */
    public Home( JFrame frame2 ) {         
        initComponents();
        frame = frame2; 
        //frame.setTitle( "JMARYSYSTEMS - " + Bean_Usuario_Logado.LOGIN.toUpperCase() + " - Suporte: 85 9.9139.2441" );
        ((BasicInternalFrameUI)Home_Frame_Interno.getUI()).setNorthPane(null); //retirar o painel superior
        Home_Frame_Interno.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
                        
        Home_JPanel_Norte.setLayout( new BorderLayout() );
                        
        inicio();  
        
        ////////////////////////////////////////////////////////////////////////
        //AbrirTabInicial.inicio();
        //new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
        
            Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
            ImageIcon icon = new ImageIcon( clazzHome.getResource("casahome.gif") );
            tabHome.addTab( " Home ", icon, new PnTabImagemHome(), "Home" );
            tabHome.setTabComponentAt( 0, new ButtonTabComponentHome( tabHome, "Home", icon ) );
        //} catch( Exception e ){ JOPM JOptionPaneMod = new JOPM( 2, "Home(), \n"
        //        + e.getMessage() + "\n", this.getClass().getSimpleName() ); } } }.start();
        //////////////////////////////////////////////////////////////////////// 
    }
    
    private void inicio(){
        
        //Administrador.setarAcessoUsuario();
        
        //Sistema_Home Sistema_Home = new Sistema_Home( this );

        Controle_de_Alterar_Menu_Norte = new Controle_de_Alterar_Menu_Norte(this);
        tocarSon = new Som();
        ControleTabs = new ControleTabs( this );
        
        new Thread() {   @Override public void run() { try { Thread.sleep( 1 ); 
        
            Platform.setImplicitExit(false);  
      
            //CRIA BANCO DE DADOS SE NÃO ENCONTRAR
            /*String endereco_db     = System.getProperty("user.dir") + System.getProperty("file.separator") + "JMarySystems";  
            ControleThread_db t1 = new ControleThread_db( endereco_db );         
            t1.setName("ControleThread_db");           
            t1.start(); */
            
        } catch( Exception e ){ 
            //JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
                //+ e.getMessage() + "\n", "Home" ); 
        } } }.start(); 
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Home_Frame_Interno = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        Home_JPanel_Centro = new javax.swing.JPanel();
        tabHome = new javax.swing.JTabbedPane();
        Home_JPanel_Norte = new javax.swing.JPanel();
        Home_Barra_Menu = new javax.swing.JMenuBar();
        menu_Ajuda = new javax.swing.JMenu();

        Home_Frame_Interno.setBorder(null);
        Home_Frame_Interno.setVisible(true);

        javax.swing.GroupLayout Home_JPanel_NorteLayout = new javax.swing.GroupLayout(Home_JPanel_Norte);
        Home_JPanel_Norte.setLayout(Home_JPanel_NorteLayout);
        Home_JPanel_NorteLayout.setHorizontalGroup(
            Home_JPanel_NorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        Home_JPanel_NorteLayout.setVerticalGroup(
            Home_JPanel_NorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Home_JPanel_CentroLayout = new javax.swing.GroupLayout(Home_JPanel_Centro);
        Home_JPanel_Centro.setLayout(Home_JPanel_CentroLayout);
        Home_JPanel_CentroLayout.setHorizontalGroup(
            Home_JPanel_CentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabHome, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(Home_JPanel_Norte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Home_JPanel_CentroLayout.setVerticalGroup(
            Home_JPanel_CentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Home_JPanel_CentroLayout.createSequentialGroup()
                .addComponent(Home_JPanel_Norte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabHome, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home_JPanel_Centro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home_JPanel_Centro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu_Ajuda.setText("Ajuda");
        Home_Barra_Menu.add(menu_Ajuda);

        Home_Frame_Interno.setJMenuBar(Home_Barra_Menu);

        javax.swing.GroupLayout Home_Frame_InternoLayout = new javax.swing.GroupLayout(Home_Frame_Interno.getContentPane());
        Home_Frame_Interno.getContentPane().setLayout(Home_Frame_InternoLayout);
        Home_Frame_InternoLayout.setHorizontalGroup(
            Home_Frame_InternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Home_Frame_InternoLayout.setVerticalGroup(
            Home_Frame_InternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home_Frame_Interno)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home_Frame_Interno)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Home_Barra_Menu;
    public javax.swing.JInternalFrame Home_Frame_Interno;
    public javax.swing.JPanel Home_JPanel_Centro;
    public javax.swing.JPanel Home_JPanel_Norte;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu menu_Ajuda;
    public javax.swing.JTabbedPane tabHome;
    // End of variables declaration//GEN-END:variables
    
    Exportando Exportando;
    public void fazer_Exercicios(String assunto_recebido) {                                         
        /*new Thread() {   @Override public void run() {*/ try { Thread.sleep( 1 );
            Exportando = new Exportando("ABRINDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );
            
            boolean criar_Exercicios = false;
            
            int numeroExercicio = 0;            
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            int intCerto = 0;
            int intErrado = 0;
            ////////////////
            numeroExercicio++;
            String nome_do_tab_exercicio = "Exercicio - " + numeroExercicio;
            
            ///////////////////////////////////////
            List<br.com.jmary.home.beans.Exercicios> Exercicios_Beans = null;
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.EXERCICIOS WHERE ASSUNTO = ?", br.com.jmary.home.beans.Exercicios.class );
                q.setParameter(1, assunto_recebido );
                Exercicios_Beans = q.getResultList();
            }catch(Exception e ){} 
            
            String rbusca = ""; try{ rbusca = Exercicios_Beans.get(0).getPergunta(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
                List<Integer> lista_Ids = new ArrayList<Integer>();
                for (int i = 0; i < Exercicios_Beans.size(); i++){
                    
                    lista_Ids.add( Exercicios_Beans.get(i).getId() );
                }
                
                int id_atual = Exercicios_Beans.get(0).getId();
                
                /////////////////////////////////////////////////////////////////////////////////////////
                Exercicios_Beans.get(0).setAssunto( assunto_recebido );
            
                Class Class_Ajuda_Interna = null; try{ Class_Ajuda_Interna = Exercicios_Beans.get(0).getClass(); }catch( Exception e){}                              
                /////////////////////////////////////////////////////////////////////////////////////////  
                exercicios_todos.Exercicios Exercicios = new exercicios_todos.Exercicios( this,
                            intCerto, intErrado, numeroExercicio,
                            Class_Ajuda_Interna, Exercicios_Beans.get(0),
                            criar_Exercicios,
                            lista_Ids, id_atual
                    );        
                //ControleTabs.removerTabSelecionado();
                ControleTabs.AddTabSemSCControleNovo( tabHome, nome_do_tab_exercicio, "/imagens_internas/livroTp.gif", Exercicios);
            }else{
                
                Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
                JOPM JOptionPaneMod = new JOPM( 1, "SEM EXERCÍCIOS\n"
                    + "\nSTATUS: "
                    + "\nNÃO HÁ EXERCÍCIOS CADASTRADOS PARA"
                    + "\n"+assunto_recebido 
                    ,"SEM EXERCÍCIOS", 
                new ImageIcon( clazzHome.getResource("logocangaco2.png")) );
            }

            Exportando.fechar();
        } catch( Exception e ){ Exportando.fechar(); e.printStackTrace(); } //} }.start();
    }
    
    public void criar_exercicios(String assunto_recebido) {                                   
        /*new Thread() {   @Override public void run() {*/ try { Thread.sleep( 1 );
            Exportando = new Exportando("ABRINDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );
            
            boolean criar_Exercicios = true;
            
            int numeroExercicio = 0;            
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            List<Integer> lista_Ids = null;
            int id_atual = 0;     
            
            int intCerto = 0;
            int intErrado = 0;
            ////////////////
            numeroExercicio++;
            String nome_do_tab_exercicio = "Criar Exercicios";
            
            br.com.jmary.home.beans.Exercicios Exercicios_Beans = new br.com.jmary.home.beans.Exercicios();
            Exercicios_Beans.setAssunto( assunto_recebido );
            
            Class Class_Ajuda_Interna = null;                                
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////           
            exercicios_todos.Exercicios Exercicios = new exercicios_todos.Exercicios( this, 
                        intCerto, intErrado, numeroExercicio,
                        Class_Ajuda_Interna, Exercicios_Beans,
                        criar_Exercicios,
                        lista_Ids, id_atual
            );
                    
            //ControleTabs.removerTabSelecionado();
            Class<Imagens_Internas> clazzHome = Imagens_Internas.class;
            ImageIcon icon = new ImageIcon( clazzHome.getResource("casahome.gif") );
            ControleTabs.AddTabSemSCControleNovo( tabHome, nome_do_tab_exercicio, "/imagens_internas/livroTp.gif", Exercicios );

            Exportando.fechar();
        } catch( Exception e ){ Exportando.fechar(); e.printStackTrace(); } //} }.start();
    }
    
}
