/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leitura_e_aprendizagem;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author AnaMariana
 */
public class BookInfo {
    
        public String bookName = " ";
        public String bookURL = " ";
        public String tipoNoBanco = " "; 
        public int    idNoBanco = 0; 
        
        public String treeRoot = " ";
        public String treePastaNoExpande = " ";
        public String treePastaExpande = " ";
        public String treeNod = " ";
        public String tree1Nod = " ";

        public BookInfo(String nome, String endereco, String tipoNoBanco2, int idNoBanco2, String root, String pastaNoExpande, String pastaExpande, String nod, String nodTree1 ) {
            bookName           = nome;  
            tipoNoBanco        = tipoNoBanco2;
            idNoBanco          = idNoBanco2;
            treeRoot           = root;
            treePastaNoExpande = pastaNoExpande;
            treePastaExpande   = pastaExpande;
            treeNod            = nod;
            tree1Nod           = nodTree1;
            
            bookURL            = endereco;   
        }
        
        @Override
        public String toString() {
            return bookName;
        }
        
        private static DefaultMutableTreeNode book;    
    public static void livro( DefaultMutableTreeNode livroParaAdd, String nome, String url, String tipoNoBanco, int idNoBanco, String root, String pastaNoExpande, String pastaExpande, String nod, String nodTree1 ) {
        try{ 
            book = new DefaultMutableTreeNode(new BookInfo ( nome, url, tipoNoBanco, idNoBanco, root, pastaNoExpande, pastaExpande, nod, nodTree1 ) );
        } catch( Exception e ) { 
            JOptionPane.showMessageDialog( null, "BookInfo - Método: livro \n" + e.getMessage() ); 
        }
        livroParaAdd.add( book );
    } 

}
