package sistema_tree;

import br.com.jmary.home.Home;
import br.com.jmary.home.beans.Assunto;
import br.com.jmary.home.beans.Categoria;
import br.com.jmary.home.beans.Materia;
import br.com.jmary.home.beans.Subcategoria;
import br.com.jmary.home.jpa.JPAUtil;
import sistema_iconestrees.IconesTrees;
import java.util.List;
import javax.persistence.Query;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import leitura_e_aprendizagem.BookInfo;
import leitura_e_aprendizagem.JPHtmlPrincipal;
import leitura_e_aprendizagem.JPHtmlPrincipal_Inicio;

public class Sistema_CriarNodes_ParaTree2 {
    
    Home            Home;
    JPHtmlPrincipal JPHtmlPrincipal;    
    DefaultMutableTreeNode tree_Materia = new DefaultMutableTreeNode( "Matéria" );
    
    BookInfo BookInfo;

    public Sistema_CriarNodes_ParaTree2( Home Home2, JPHtmlPrincipal JPHtml, BookInfo BookInfo2 ) {
        
        Home            = Home2;
        JPHtmlPrincipal = JPHtml;
        BookInfo        = BookInfo2;
        
        verificarTipoNoBanco();
    }
            
    private void verificarTipoNoBanco() {
        /*new Thread() {   @Override public void run() {*/ try { Thread.sleep( 1 ); DefaultTreeModel treeModel;
        
            switch( BookInfo.tipoNoBanco ){
                
                case "MATERIA":    
                    verificarMateria(BookInfo.idNoBanco); 
                    
                    treeModel = new DefaultTreeModel( tree_Materia );
                    JPHtmlPrincipal.tree2.setModel(treeModel); 
                    break;
                case "CATEGORIA":  
                    ////////////////////////////////////////////////////////////
                    Categoria Categoria = null;
            
                    List<Categoria> ListCategoria = null;
                    try{                        
                        Query q2 = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.CATEGORIA WHERE ID = ?", Categoria.class );
                        q2.setParameter( 1, BookInfo.idNoBanco );            
                        ListCategoria = q2.getResultList();
                    }catch(Exception e ){}
                    
                    String rbusca = ""; try{ rbusca = ListCategoria.get(0).getCategoria(); }catch( Exception e ){}
                    
                    if( !rbusca.equals("") ){
                    
                        for (int i = 0; i < ListCategoria.size(); i++){ 
                            Categoria = ListCategoria.get(i); 
                            String endCategoria = System.getProperty("user.dir") + ListCategoria.get(i).getCategoriapaginahtml(); 
                
                            BookInfo categoria_1 = new BookInfo( ListCategoria.get(i).getCategoria(), endCategoria, "CATEGORIA", ListCategoria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    
                            
                            tree_Materia = new DefaultMutableTreeNode( categoria_1 );
                            
                            //////////////////////////////////////////////////////////////////////////////
                            Materia Materia = null;
                            List<Materia> ListMateria = null;            
                            try{
                                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                                q.setParameter(1, Categoria.getIdMateria() );            
                                ListMateria = q.getResultList();
                            }catch(Exception e ){}            
                            String rbuscaM = ""; 
                            try{ rbuscaM = ListMateria.get(0).getMateria(); }catch( Exception e ){}
                            if( !rbuscaM.equals("") ){
                                Materia = ListMateria.get(0);
                            }
                            //////////////////////////////////////////////////////////////////////////////
                            
                            setarSubcategorias( Materia, Categoria, tree_Materia );
                    
                            treeModel = new DefaultTreeModel( tree_Materia );
                            JPHtmlPrincipal.tree2.setModel(treeModel);                                                      
                            break;
                        }            
                    }
                    ////////////////////////////////////////////////////////////
                                
                break; 
                case "SUBCATEGORIA":
                    ////////////////////////////////////////////////////////////
                    Subcategoria Subcategoria = null;
            
                    List<Subcategoria> ListSubcategoria = null;                    
                    try{
                        Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.SUBCATEGORIA WHERE ID = ?", Subcategoria.class );
                        q.setParameter( 1, BookInfo.idNoBanco );            
                        ListSubcategoria = q.getResultList();
                    }catch(Exception e ){}
                    
                    String rbuscaS = ""; try{ rbuscaS = ListSubcategoria.get(0).getSubcategoria(); }catch( Exception e ){}
                    
                    if( !rbuscaS.equals("") ){
                        
                        for (int i = 0; i < ListSubcategoria.size(); i++){ Subcategoria = ListSubcategoria.get(i);
                    
                            String endSubcategoria = System.getProperty("user.dir") + ListSubcategoria.get(i).getSubcategoriapaginahtml(); 
                          
                            BookInfo subcategoria_1 = new BookInfo( ListSubcategoria.get(i).getSubcategoria(), endSubcategoria, "SUBCATEGORIA", ListSubcategoria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    

                            tree_Materia = new DefaultMutableTreeNode( subcategoria_1 );
                        
                            setarAssunto( Subcategoria, tree_Materia );
                    
                            treeModel = new DefaultTreeModel( tree_Materia );
                            JPHtmlPrincipal.tree2.setModel(treeModel);                                                      
                            break;
                        }
                    }
                    ////////////////////////////////////////////////////////////
                break;    
                    
                default: administrador_CriarNodes(); break;   
            }
                       
        } catch( Exception e ){ administrador_CriarNodes(); } //} }.start();          
    }
    
    private void verificarMateria( int id ) {
        try { 
            
            List<Materia> ListMateria = null;            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                q.setParameter(1, id );            
                ListMateria = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListMateria.get(0).getMateria(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
                
                for (int i = 0; i < ListMateria.size(); i++){  
                
                    String endMateria = System.getProperty("user.dir") + ListMateria.get(i).getMateriapaginahtml();
                
                    BookInfo rootMateria = new BookInfo( ListMateria.get(i).getMateria(), endMateria, "MATERIA", ListMateria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );
                    tree_Materia = new DefaultMutableTreeNode( rootMateria );
                
                    JPHtmlPrincipal_Inicio setarJEditorPane_Inicio = new JPHtmlPrincipal_Inicio( Home, BookInfo, JPHtmlPrincipal );
                }
                
                setarCategorias( ListMateria.get(0) );    
            }
            else{
                
                administrador_CriarNodes();
            }
        } catch ( Exception e ) {}               
    }
    
    private void setarCategorias( Materia Materia ){ 
        try {         
            Categoria Categoria = null;
            
            List<Categoria> ListCategoria = null;            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.CATEGORIA WHERE ID_MATERIA = ?", Categoria.class );
                q.setParameter( 1, Materia.getId() );            
                ListCategoria = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListCategoria.get(0).getCategoria(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
            
                for (int i = 0; i < ListCategoria.size(); i++){ Categoria = ListCategoria.get(i);
            
                    String endCategoria = System.getProperty("user.dir") + ListCategoria.get(i).getCategoriapaginahtml(); 
                    
                    BookInfo categoria_1 = new BookInfo( ListCategoria.get(i).getCategoria(), endCategoria, "CATEGORIA", ListCategoria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    
                    DefaultMutableTreeNode categoria = new DefaultMutableTreeNode( categoria_1 );
                    tree_Materia.add( categoria );    
                
                    setarSubcategorias( Materia, Categoria, categoria );
                }         
            }
            
        } catch ( Exception e ) {}
    }
    
    private void setarSubcategorias( Materia Materia, Categoria Categoria, DefaultMutableTreeNode categoria ){ 
        try {         
            Subcategoria Subcategoria = null;
            
            List<Subcategoria> ListSubcategoria = null;
            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.SUBCATEGORIA WHERE ID_MATERIA = ? AND ID_CATEGORIA = ?", Subcategoria.class );
                q.setParameter( 1, Categoria.getIdMateria() );
                q.setParameter( 2, Categoria.getId() );            
                ListSubcategoria = q.getResultList();
            }catch(Exception e ){}
            
            for (int i = 0; i < ListSubcategoria.size(); i++){ Subcategoria = ListSubcategoria.get(i);
            
                String endSubcategoria = System.getProperty("user.dir") + ListSubcategoria.get(i).getSubcategoriapaginahtml(); 

                BookInfo subcategoria_1 = new BookInfo( ListSubcategoria.get(i).getSubcategoria(), endSubcategoria, "SUBCATEGORIA", ListSubcategoria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    

                DefaultMutableTreeNode subcategoria = new DefaultMutableTreeNode( subcategoria_1 );
                categoria.add( subcategoria );         
            }         
            
        } catch ( Exception e ) {}
    }
    
    private void setarAssunto( Subcategoria Subcategoria, DefaultMutableTreeNode categoria ){ 
        try {         
            
            List<Assunto> ListAssunto = null;            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.ASSUNTO WHERE ID_MATERIA = ? AND ID_CATEGORIA = ? AND ID_SUBCATEGORIA = ?", Assunto.class );
                q.setParameter( 1, Subcategoria.getIdMateria() );
                q.setParameter( 2, Subcategoria.getIdCategoria() );
                q.setParameter( 3, Subcategoria.getId() );            
                ListAssunto = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListAssunto.get(0).getAssunto(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
            
                for (int i = 0; i < ListAssunto.size(); i++){ 
            
                    String endSAssunto = System.getProperty("user.dir") + ListAssunto.get(i).getAssuntopaginahtml(); 
                
                    BookInfo assunto_1 = new BookInfo( ListAssunto.get(i).getAssunto(), endSAssunto, "ASSUNTO", ListAssunto.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    
                    DefaultMutableTreeNode assunto = new DefaultMutableTreeNode( assunto_1 );
                    categoria.add( assunto );         
                }   
            }
            
        } catch ( Exception e ) {}
    }
        
    ////////////////////////////////////////////////////////////////////////////
    public void administrador_CriarNodes() {
        
        String modelo = System.getProperty("user.dir") + "//" + "00_Externo" + "//" + "modelo.html";
                
        BookInfo fonemaRoot = new BookInfo( "CATEGORIA", modelo, "CATEGORIA", 0, IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );
            tree_Materia = new DefaultMutableTreeNode( fonemaRoot );
        
        BookInfo fonema_1 = new BookInfo( "ASSUNTO", modelo, "ASSUNTO", 0, IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );
            DefaultMutableTreeNode fonema_11 = new DefaultMutableTreeNode( fonema_1 );
            tree_Materia.add( fonema_11 );    
        
        /* JTree Customization node */
        DefaultTreeModel treeModel = new DefaultTreeModel( tree_Materia );
        JPHtmlPrincipal.tree2.setModel(treeModel); 
    }
}