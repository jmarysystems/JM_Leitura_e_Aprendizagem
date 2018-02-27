
package sistema_tree;

import br.com.jmary.home.Home;
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

public class Sistema_CriarNodes_ParaTree1 {
    
    Home            Home;
    JPHtmlPrincipal JPHtmlPrincipal;        
    DefaultMutableTreeNode tree_Materia = new DefaultMutableTreeNode( "Matéria" );
    BookInfo BookInfo;

    public Sistema_CriarNodes_ParaTree1( Home Home2, JPHtmlPrincipal JPHtml, BookInfo BookInfo2 ){
        
        Home            = Home2;
        JPHtmlPrincipal = JPHtml;
        BookInfo        = BookInfo2;
        verificarMateria();
    }

    private void verificarMateria(){ 
        try { 
            
            Materia Materia = new Materia();
            
            List<Materia> ListMateria = null;
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.MATERIA WHERE ID = ?", Materia.class );
                q.setParameter(1, JPHtmlPrincipal.idNoBanco );            
                ListMateria = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListMateria.get(0).getMateria(); }catch( Exception e ){rbusca = "";}
            
            if( !rbusca.equals("") ){
            
                for (int i = 0; i < ListMateria.size(); i++){ Materia = ListMateria.get(i); 
                
                    String endMateria = System.getProperty("user.dir") + ListMateria.get(i).getMateriapaginahtml();
                
                    BookInfo rootMateria = new BookInfo( Materia.getMateria(), endMateria, "MATERIA", Materia.getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );
                    tree_Materia = new DefaultMutableTreeNode( rootMateria );
                
                    JPHtmlPrincipal_Inicio setarJEditorPane_Inicio = new JPHtmlPrincipal_Inicio( Home, BookInfo, JPHtmlPrincipal );
                }
                
                setarCategorias( Materia );
                
                DefaultTreeModel treeModel = new DefaultTreeModel( tree_Materia );
                JPHtmlPrincipal.tree1.setModel( treeModel );
            }
            else{
                
                paginaIncial(); 
                administrador_CriarNodes();
            }
        }catch(Exception e){}
    }
    
    private void setarCategorias( Materia Materia ){ 
        try {  
            
            List<Categoria> ListCategoria = null;
            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.CATEGORIA WHERE ID_MATERIA = ?", Categoria.class );
                q.setParameter( 1, Materia.getId() );            
                ListCategoria = q.getResultList();
            }catch(Exception e ){}
            
            String rbusca = ""; try{ rbusca = ListCategoria.get(0).getCategoria(); }catch( Exception e ){}
            
            if( !rbusca.equals("") ){
                
                for (int i = 0; i < ListCategoria.size(); i++){ 
            
                    String endCategoria = System.getProperty("user.dir") + ListCategoria.get(i).getCategoriapaginahtml(); 
                
                    BookInfo categoria_1 = new BookInfo( ListCategoria.get(i).getCategoria(), endCategoria, "CATEGORIA", ListCategoria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    
                    DefaultMutableTreeNode categoria = new DefaultMutableTreeNode( categoria_1 );
                    tree_Materia.add( categoria );    
                
                    setarSubcategorias( Materia, ListCategoria.get(i), categoria );
                }  
            }
            
        } catch ( Exception e ) {}
    }
    
    private void setarSubcategorias( Materia Materia, Categoria Categoria, DefaultMutableTreeNode categoria ){ 
        try {         
            
            List<Subcategoria> ListSubcategoria = null;
            
            try{
                Query q = JPAUtil.em().createNativeQuery( "SELECT * FROM JM.SUBCATEGORIA WHERE ID_MATERIA = ? AND ID_CATEGORIA = ?", Subcategoria.class );
                q.setParameter( 1, Categoria.getIdMateria() );
                q.setParameter( 2, Categoria.getId() );            
                ListSubcategoria = q.getResultList();
            }catch(Exception e ){}
            
            String rbuscaS = ""; try{ rbuscaS = ListSubcategoria.get(0).getSubcategoria(); }catch( Exception e ){}
                    
            if( !rbuscaS.equals("") ){
                
                for (int i = 0; i < ListSubcategoria.size(); i++){ 
            
                    String endSubcategoria = System.getProperty("user.dir") + ListSubcategoria.get(i).getSubcategoriapaginahtml(); 
                
                    BookInfo subcategoria_1 = new BookInfo( ListSubcategoria.get(i).getSubcategoria(), endSubcategoria, "SUBCATEGORIA", ListSubcategoria.get(i).getId(), IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    
                    DefaultMutableTreeNode subcategoria = new DefaultMutableTreeNode( subcategoria_1 );
                    categoria.add( subcategoria );         
                }  
            }
            
        } catch ( Exception e ) {}
    }
    
    ////////////////////////////////////////////////////////////////////////////
    private void paginaIncial(){ 
        try {
            String modelo = System.getProperty("user.dir") + "//" + "00_Externo" + "//" + "modelo.html";
                    
            JPHtmlPrincipal_Inicio setarJEditorPane_Inicio = new JPHtmlPrincipal_Inicio( Home, BookInfo, JPHtmlPrincipal );
        } catch ( Exception e ) {}
    }
            
    private void administrador_CriarNodes() {
        
        String modelo = System.getProperty("user.dir") + "//" + "00_Externo" + "//" + "modelo.html";
        
        BookInfo fonemaRoot = new BookInfo( "MATERIA", modelo, "MATERIA", 0, IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );
            tree_Materia = new DefaultMutableTreeNode( fonemaRoot );
            
        BookInfo fonema_1 = new BookInfo( "CATEGORIA", modelo, "CATEGORIA", 0, IconesTrees.root, IconesTrees.pastaFechada, IconesTrees.pastaAberta, IconesTrees.nod, IconesTrees.nodPastaAberta );    
            DefaultMutableTreeNode fonema_11 = new DefaultMutableTreeNode( fonema_1 );
            tree_Materia.add( fonema_11 );  
        
        //Simbologia_CriarNodes_ParaTree1 Simbologia_ParaTree1 = new Simbologia_CriarNodes_ParaTree1( JPHtmlPrincipal, tree_Materia, adicionarList ); 
        
        /* JTree Customization node */
        DefaultTreeModel treeModel = new DefaultTreeModel( tree_Materia );
        JPHtmlPrincipal.tree1.setModel(treeModel); 
    }
}