package italo.explab.util;

import italo.explab.ErroMSGs;
import italo.explab.InfoMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExObj;
import italo.explab.arvore.Executor;
import italo.explab.arvore.busca.var.VarBuscaResult;
import italo.explab.arvore.estruturas.node.Capture;
import italo.explab.arvore.estruturas.node.TenteCapture;
import italo.explab.codigo.Codigo;
import italo.explab.msg.ExceptionErro;
import italo.explab.msg.FuncOuConstrutor;
import italo.explab.msg.LanceErro;
import italo.explab.msg.MSGManager;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.var.ConstanteException;
import italo.explab.var.ObjetoVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;

public class TenteCaptureUtil {      
    
    private final InterAplic aplic;

    public TenteCaptureUtil(InterAplic aplic) {
        this.aplic = aplic;
    }
    
    public LanceErro excecaoCapturadaOuTratada( ExObj exObj, FuncOuConstrutor fOuC, Codigo codigo, BlocoNo bno ) {
        Objeto obj = exObj.getExeceptionObj().getObjeto();
            
        String exLancadaClasseNome = obj.getClasse().getNomeCompleto();
        String runtimeExClasseNome = aplic.getConfig().getClasseRuntimeException();
                
        if ( !obj.instanciaDe( runtimeExClasseNome ) ) {        
            TenteCapture tc = bno.tenteCaptureBlocoNo();
            String[] exceptionClasses = fOuC.getExceptionClasses();
            if ( this.verificaSeExcecaoTratada( codigo, exLancadaClasseNome, exceptionClasses ) )
                return null; 
                
            if ( tc != null )            
                if ( this.verificaSeClasseCapturada( codigo, tc, exLancadaClasseNome ) )
                    return null;            
            
            
            int i = ( exObj.getLance() == null ? bno.getI() : exObj.getLance().getI() );
            
            return new LanceErro( this.getClass(), codigo, i, ErroMSGs.EXCECAO_CAPTURA_OU_TRATAMENTO_ESPERADA, exLancadaClasseNome );
        }
        
        return null;
    }

    public boolean verificaSeExcecaoTratada( Codigo codigo, String exclasseNome, String[] exClassesNomes ) {
        ClasseManager classeManager = aplic.getClasseManager();
        if ( exClassesNomes != null ) {
            if ( exClassesNomes.length > 0 ) {
                Classe exclasse = classeManager.buscaClasse( exclasseNome );                
                for( String ex : exClassesNomes ) {
                    Classe exclasseTratada = classeManager.buscaClasse( ex );                
                    if ( exclasse.isIgualOuFilhaNome( exclasseTratada ) )
                        return true;
                }
            }
        }        
        return false;
    }
    
    public boolean verificaSeClasseCapturada( Codigo codigo, TenteCapture tc, String exlancadaClasseNome ) {                        
        ClasseManager classeManager = aplic.getClasseManager();
        
        Classe exlancadaClasse = classeManager.buscaClasse( exlancadaClasseNome );
        
        Capture cap = null;
        int len = tc.getCaptures().length;
        for( int i = 0; cap == null && i < len; i++ ) {
            Capture c = tc.getCaptures()[ i ];
            int len2 = c.getExClassesNomes().length;
            for( int j = 0; cap == null && j < len2; j++ ) {
                String exCapClasseNome = c.getExClassesNomes()[ j ];
                Classe exCapClasse = classeManager.buscaClasse( exCapClasseNome );

                if ( exlancadaClasse.isIgualOuFilhaNome( exCapClasse ) )
                    return true;
            }
        }

        return false;
    }     

    public ExceptionErro setEXMensagem( BlocoNo bno, Executor executor, ObjetoVar exObj, FuncOuConstrutor fOuC ) {                       
        MSGManager msgManager = executor.getAplic().getMSGManager();
        
        String mensagem = msgManager.getInfoMSG( InfoMSGs.LANCAMENTO_DE_EXCECAO_MSG_PADRAO );

        VarBuscaResult vbresult = executor.getBuscaManager().getEscopoVarBusca().busca( bno, executor, "mensagem" );                
        if ( vbresult != null )
            if ( vbresult.getVariavel().getVar().getTipo() == Var.STRING )
                mensagem = ((StringVar)vbresult.getVariavel().getVar()).getValor();                
                                    
        try {  
            bno.getBloco().getRecursos().getVarLista().criaOuAltera( "mensagem", new StringVar( mensagem ) );
        } catch ( ConstanteException ex ) {
            
        }   
                
        return new ExceptionErro( this.getClass(), fOuC, exObj, bno.getCodigo(), bno.getI(), mensagem );
    }    
    
    
    private String getEXMensagem( Executor executor, BlocoNo bno ) {    
        MSGManager msgManager = executor.getAplic().getMSGManager();
        
        String msgPadrao = msgManager.getInfoMSG( InfoMSGs.LANCAMENTO_DE_EXCECAO_MSG_PADRAO );

        VarBuscaResult vbresult = executor.getBuscaManager().getEscopoVarBusca().busca( bno, executor, "mensagem" );                
        if ( vbresult != null )
            if ( vbresult.getVariavel().getVar().getTipo() == Var.STRING )
                return ((StringVar)vbresult.getVariavel().getVar()).getValor();                
                            
        return msgPadrao;
    }   
    
    private String formataEXClasses( String[] exceptionClasses ) {
        String exclasses = exceptionClasses[ 0 ];
        int size = exceptionClasses.length;
        for( int k = 1; k < size; k++ )
            exclasses += ", " + exceptionClasses[ k ];        
        return exclasses;
    }

}
