/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitura_e_aprendizagem;

import br.com.jmary.home.Home;
import br.com.jmary.utilidades.Exportando;
import br.com.jmary.utilidades.JOPM;
import br.com.jmary.visualizador_imagens.Visualizador_Externo2;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Ana
 */
public class JPHtmlPrincipal_Inicio{
    
    Home             Home;
    String           url;
    JPHtmlPrincipal  JPHtmlPrincipal;
    BookInfo         BookInfo;
    
    BarraJEditorPane BarraJEditorPane;    
    
    public JPHtmlPrincipal_Inicio( Home Home2, BookInfo BookInfo2, JPHtmlPrincipal JPHtmlPrincipal2 ) {

        Home             = Home2;
        BookInfo         = BookInfo2;
        url              = BookInfo.bookURL;
        JPHtmlPrincipal  = JPHtmlPrincipal2;
        
        try{
            
            File file = new File( BookInfo.bookURL );
            if ( file.isFile() == true ) {
                
                System.out.println( "BookInfo.bookURL - file.isFile() - " + BookInfo.bookURL ); 
                inicio();
            }
            else if ( file.isDirectory() == true ) {                
                
                System.out.println( "BookInfo.bookURL - file.isDirectory() - " + BookInfo.bookURL ); 
                abrir_Livro();
            }
        }catch( Exception e ){
            System.out.println( "BookInfo.bookURL -}catch( Exception e ){ - " + BookInfo.bookURL ); 
            inicio();
        }
    }       //MediaFX
     
    private void inicio(){
                        
        /*new Thread() {   @Override public void run() {*/ try { Thread.sleep( 1 ); 
               
            JPanel p = new JPanel( new BorderLayout() );  
            HtmlPrincipal HtmlPrincipal = new HtmlPrincipal( Home );
            BarraJEditorPane = new BarraJEditorPane( Home, JPHtmlPrincipal, HtmlPrincipal, BookInfo );
            p.add( BarraJEditorPane, BorderLayout.NORTH );
                        
            p.add( HtmlPrincipal, BorderLayout.CENTER ); 
                                                            
            JPHtmlPrincipal.jSplitPaneHtmlPrincipal.setRightComponent(p);
            
            try{ 
                File File = new File(url);
                if( File.exists() == true ){
                    
                    //System.out.println( "OK JPHtmlPrincipal_Inicio URL: " + url );
                    //System.out.println( "OK JPHtmlPrincipal_Inicio URL: " + new File(url).toURI().toURL() );
                    //System.out.println( "-----------------------------------------------------------" );
                    HtmlPrincipal.htmlPanePrincipal.setPage( new File(url).toURI().toURL() ); 
                    
                    //HtmlPrincipal.alterarTamanhoFonte(22); 
                }
                else{
                    
                    //System.out.println( "ERRO JPHtmlPrincipal_Inicio URL: " + url );
                    //System.out.println( "-----------------------------------------------------------" );
                }
            }catch( Exception e ){ 
                
                System.out.println( "catch( Exception e ) - JPHtmlPrincipal_Inicio URL: " + url ); 
                e.printStackTrace();
            } 
            
            //System.out.println( "-----------------------------------------------------------" );
            int divl = JPHtmlPrincipal.jSplitPaneHtmlPrincipal.getDividerLocation();
            //System.out.println( "jSplitPaneHtmlPrincipal.setDividerLocation: " + divl );
            //System.out.println( "-----------------------------------------------------------" );
            //JPHtmlPrincipal.jSplitPaneHtmlPrincipal.setDividerLocation( divl );
            if(JPHtmlPrincipal.boosetDividerLocation == false){
                JPHtmlPrincipal.setarDividerLocation_Trees( 260 );
                JPHtmlPrincipal.boosetDividerLocation = true;
            }
            else{
                JPHtmlPrincipal.setarDividerLocation_Trees( divl );
            }
        }catch( Exception e ){ JOPM JOptionPaneMod = new JOPM( 2, "inicio(), \n"
                + e.getMessage() + "\n", "JPHtmlPrincipal_Inicio" ); } //} }.start(); 
    }
    
    Exportando Exportando;
    private void abrir_Livro() {                                           
        /*new Thread() {   @Override public void run() {*/ try { 
            Exportando = new Exportando("ABRINDO...");
            Exportando.setVisible(true);Exportando.pbg.setMinimum(0);
            Exportando.pbg.setMaximum( 100 );
            Exportando.pbg.setValue( 50 );
            
            int img_Atual = 0;
            String nome_do_Pacote_ajuda = BookInfo.bookURL+"\\";   
            
            JScrollPane sc = new JScrollPane();
            sc.setBorder( null );
            JPHtmlPrincipal.jSplitPaneHtmlPrincipal.setRightComponent(sc);
                                    
            JPanel p = new JPanel( new BorderLayout() );  
            p.add( new Visualizador_Externo2(Home,img_Atual,nome_do_Pacote_ajuda,0,0,JPHtmlPrincipal), BorderLayout.CENTER );
            
            sc.setViewportView( p );
            
            Exportando.fechar();
            
            Thread.sleep( 100 ); 
            int divl = JPHtmlPrincipal.jSplitPaneHtmlPrincipal.getDividerLocation();
            JPHtmlPrincipal.setarDividerLocation_Trees( divl );
        } catch( Exception e ){ Exportando.fechar(); e.printStackTrace(); } //} }.start();
    }
    
}
