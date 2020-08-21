package italo.explab.msg;

import italo.explab.config.msg.MSGErro;
import italo.explab.InterStream;
import italo.explab.codigo.Codigo;
import italo.explab.config.msg.MSGConfig;
import italo.explab.recursos.classe.Classe;
import java.util.List;

public class MSGManager {
    
    private final InterStream stream = (texto) -> {
        System.out.print( texto );
    };        
    
    private final int INI_ERRO_MSG_POSIC = -10;
    private final int FIM_ERRO_MSG_POSIC = 20;    
    
    private InterStream errStream = stream;
    private InterStream outStream = stream;
    private final MSGConfig config;    
    
    public MSGManager( MSGConfig config ) {
        this.config = config;
    }
            
    public MSGErro getErro( String chave ) {
        return config.getErros().get( chave );
    }
    
    public String getErroValor( String chave ) {
        MSGErro erro = config.getErros().get( chave );
        if ( erro != null )
            return erro.getValor();
        return null;
    }
    
    public String getInfoValor( String chave ) {
        return config.getInfos().get( chave );
    }
    
    public String getEXClasseNome( String chave ) {
        MSGErro erro = config.getErros().get( chave );
        if ( erro != null )
            return erro.getExceptionClasseNome();        
        return null;
    }
    
    public void envia( String texto ) {        
        outStream.envia( texto ); 
    }
        
    public String getInfoMSG( String chave, String... params ) {
        String msg = this.getInfoValor( chave );
        if ( msg == null )
            return "Chave (INFO) não encontrada: "+chave;
        if ( params.length > 0 ) {
            int i = 1;
            for( String p : params ) {
                msg = msg.replace( "%"+i, p );
                i++;
            }
        }
        return msg;
    }
    
    public String getErroMSG( String chave, String... params ) {
        String msg = this.getErroValor( chave );
        if ( msg == null )
            return "Chave (ERRO) não encontrada: "+chave;
        if ( params.length > 0 ) {
            int i = 1;
            for( String p : params ) {
                msg = msg.replace( "%"+i, p );
                i++;
            }
        }
        return msg;
    }
            
    public void enviaInfo( String chave, String... params ) {        
        outStream.envia( this.getInfoMSG( chave, params ) );
    }
    
    public void enviaErro( String chave, String... params ) {        
        errStream.envia( this.getErroMSG( chave, params ) );  
    }
            
    public void enviaPilhaErros( List<Erro> pilha ) {                       
        boolean prim = true;
        int size = pilha.size();
        for( int i = 0; i < size; i++ ) {
            Erro e = pilha.get( i ); 
            
            if ( !prim )
                errStream.envia( "\t    " );
            prim = false;
                    
            this.enviaErro( e );    
            errStream.envia( "\n" );                
        }
    }
            
    public void enviaErro( Erro e ) {
        if ( e instanceof CodigoErro ) {             
            if ( e instanceof CodigoMSGErro ) {
                if ( e instanceof ExceptionErro ) {
                    Classe classe = ((ExceptionErro)e).getExceptionObj().getObjeto().getClasse();
                    String classeNome = classe != null ? classe.getNomeCompleto()+"." : "";
                    String funcOuConstrutorNome = ((ExceptionErro)e).getFuncOuConstrutor().getNome();                 
                    int quantParams = ((ExceptionErro)e).getFuncOuConstrutor().getQuantParametros();

                    CodigoErro ce = (CodigoErro)e;                
                    Codigo codigo = ce.getCodigo();
                    String arq = ce.getCodigo().getArquivo();
                    int[] lincol = this.lincol( codigo, ce.getPos() );
                    errStream.envia(classeNome+funcOuConstrutorNome+"("+quantParams+") - ["+arq+": "+lincol[0]+","+lincol[1]+"]: " );                                              
                }
                errStream.envia( ((CodigoMSGErro)e).getMensagem() );
            } else {
                errStream.envia( "\n"+this.geraMSG( (CodigoErro)e ) );         
            }
        } else {
            errStream.envia( this.getErroMSG( e.getChave(), e.getParams() ) ); 
        }
    }
                
    public String geraMSG( CodigoErro erro ) {        
        String codigo = erro.getCodigo().getCodigo();
        String arquivo = erro.getCodigo().getArquivo(); 
        
        int linha = 1;
        int coluna = 0;
    
        int posic = erro.getPos();
        String chave = erro.getChave();
        String[] params = erro.getParams();
        
        int codlen = codigo.length();
        for( int i = 0; i <= posic && i < codlen; i++ ) {
            if ( codigo.charAt( i ) == '\n' ) {
                linha++;
                coluna = 0;
            } else {
                coluna++;
            }
        }
                
        String erroMSG = this.getErroValor( chave );
        StringBuilder erroSB = new StringBuilder();
        erroSB.append( "Erro " );
        if ( arquivo != null ) {
            erroSB.append( "\"" );
            erroSB.append( arquivo );
            erroSB.append( "\" " );            
        }
        erroSB.append( "(" );
        erroSB.append( linha );
        erroSB.append( "," );
        erroSB.append( coluna );
        erroSB.append( ")" );
        
        erroSB.append( ": " );
        
        if ( erroMSG == null ) {
            erroSB.append( "Mensagem de erro não encontrado pela chave: " ); 
            erroSB.append( chave ); 
        } else {
            if ( params.length > 0 ) {
                int i = 1;
                for( String p : params ) {
                    erroMSG = erroMSG.replace( "%"+i, p );
                    i++;
                }
            }            
            erroSB.append( erroMSG );
            erroSB.append( "\n" );

            int iniErrPosic = -INI_ERRO_MSG_POSIC;            
            int ini = posic+INI_ERRO_MSG_POSIC;
            int fim = posic+FIM_ERRO_MSG_POSIC;
            int len = codigo.length();
            if ( fim > len )
                fim = len; 
            if ( ini <= 0 ) {
                iniErrPosic = posic;
                ini = 0;
            }

            erroSB.append( '\t' );
            erroSB.append( codigo.substring( ini, fim ).replaceAll( "\n", " " ) );  
            erroSB.append( '\n' );

            erroSB.append( '\t' );
            for( int i = 0; i < iniErrPosic; i++ )
                erroSB.append( ' ' );
            erroSB.append( '^' );                   
        }
                
        return erroSB.toString();
    }
    
    public int[] lincol( Codigo codigo, int posic ) {
        int linha = 1;
        int coluna = 1;
        
        int codlen = codigo.getCodlen();
        for( int i = 0; i <= posic && i < codlen; i++ ) {
            if ( codigo.getSEGCH( i ) == '\n' ) {
                linha++;
                coluna = 0;
            } else {
                coluna++;
            }
        }
        
        return new int[] { linha, coluna };
    }
        
    public InterStream getErrStream() {
        return errStream;
    }
    
    public void setErrStream( InterStream stream ) {
        this.errStream = stream;
    }

    public InterStream getOutStream() {
        return outStream;
    }

    public void setOutStream(InterStream outStream) {
        this.outStream = outStream;
    }
    
}
