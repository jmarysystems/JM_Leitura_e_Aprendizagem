package leitura_e_aprendizagem;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AnaMariana
 */
public class BookInfo2 {
    
        public int    ID;    
        public String NOME; 

        public BookInfo2(int ID2, String NOME2 ) {
            ID   = ID2     ;    
            NOME = NOME2; 
        }
        
        @Override
        public String toString() {
            return NOME;
        }

}
