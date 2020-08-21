package italo.explab.util;

import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class InterUtil {               
          
    public boolean verificaSeFuncVoid( Codigo codigo, int i, int i2 ) {
        int j = 0;    
        int retPRLen = PalavrasReservadas.RETORNE.length();
        if ( retPRLen == 0 )
            return false;
        
        char retorneCHIni = Character.toLowerCase( PalavrasReservadas.RETORNE.charAt( 0 ) );
        while( i+j < i2 ) {
            char ch = codigo.getSEGCH( i+j );
            if ( Character.toLowerCase( ch ) == retorneCHIni ) {
                boolean nao = false;
                for( int k = 0; !nao && k < retPRLen; k++ ) {
                    ch = codigo.getSEGCH( i+j+k );
                    char ch2 = PalavrasReservadas.RETORNE.charAt( k );
                    if ( Character.toLowerCase( ch ) != Character.toLowerCase( ch2 ) )
                        nao = true;                                        
                }
                
                if ( !nao ) {
                    j += retPRLen;
                    
                    boolean ehvoid = false;
                    while( !ehvoid && i+j < i2 ) {
                        ch = codigo.getSEGCH( i+j );
                        switch( ch ) {
                            case ' ':
                            case '\r':
                            case '\t':
                            case '\n':
                                j++;
                                break;
                            case ';':
                                ehvoid = true;
                            default:
                                
                        }
                    }
                    return ehvoid;
                }
            }
            j++;
        }
        return true;
    }
        
    public boolean isContemCH( char[] charsFIM, char ch ) {
        for( char charFIM : charsFIM )
            if ( ch == charFIM ) 
                return true;
        return false;
    }       
    
    public String formataCHsEspeciais( Codigo codigo, int i, int i2 ) {        
        StringBuilder sb = new StringBuilder();
        for( int k = i; k < i2; k++ ) {
            char ch = codigo.getCodigo().charAt( k );
            if ( ch == '\\' && k+1 < i2 ) {
                boolean inc = true;
                switch( codigo.getCodigo().charAt( k+1 ) ) {
                    case 'r':
                        sb.append( '\r' );
                        break;
                    case 'n':
                        sb.append( '\n' );
                        break;
                    case 't':
                        sb.append( '\t' );
                        break;
                    case '\'':
                        sb.append( '\'' );
                        break;
                    case '\"':
                        sb.append( '\"' );
                        break;                                
                    case '0':
                        sb.append( "\\0" );
                        break;            
                    default:
                        sb.append( "\\" );        
                        break;
                }
                if ( inc )
                    k++;
            } else {
                sb.append( ch );                            
            }                
        }
        return sb.toString();
    }
    
    public static void main( String[] args ) {
        InterAplic aplic = new InterAplic();
        String str = "\"\\ \n \t a b c\"";        
        Codigo codigo = new Codigo( aplic, str );
        String res = new InterUtil().formataCHsEspeciais( codigo, 1, str.length()-1 );
        System.out.println( str );
        System.out.println( res );
    }
    
}
