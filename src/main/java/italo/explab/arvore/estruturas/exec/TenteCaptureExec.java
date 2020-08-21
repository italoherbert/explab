package italo.explab.arvore.estruturas.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.estruturas.node.Capture;
import italo.explab.arvore.estruturas.node.TenteCapture;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.LanceErro;
import italo.explab.msg.MSGManager;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.var.ConstanteException;
import italo.explab.var.ObjetoVar;

public class TenteCaptureExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        TenteCapture tc = (TenteCapture)no;        
                        
        Codigo codigo = no.getCodigo();        
        MSGManager msgManager = executor.getAplic().getMSGManager();
        ClasseManager classeManager = executor.getAplic().getClasseManager();
        
        ObjetoVar exObjVar = null;
        ExecResult erroResult = null;
        
        ExecResult er = executor.exec( tc.getBloco() );        
        if ( er.getExObj() != null ) {
            exObjVar = er.getExObj().getExeceptionObj();
        } else if ( er.getErro() != null ) {         
            if ( er.getErro() instanceof LanceErro ) {
                erroResult = er;                
            } else if ( er.getErro().getChave() != null ) {
                String chave = er.getErro().getChave();
                String exClasseNome = msgManager.getEXClasseNome( chave );
                if ( exClasseNome == null ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.EXCECAO_NAO_ENCONTRADA_PELA_CHAVE, String.valueOf( chave ) );
                    return new ExecResult( erro );
                }

                Classe exClasse = classeManager.buscaClasse( exClasseNome );
                exObjVar = classeManager.novoObjeto( exClasse );                                                                                                                                                
            }
        }
             
        if ( exObjVar != null ) {            
            Capture[] captures = tc.getCaptures();                                    
            boolean achou = false;
            if ( captures != null ) {                
                for( int i = 0; !achou && i < captures.length; i++ ) {
                    Capture cap = captures[ i ];
                    String[] exClassesNomes = cap.getExClassesNomes();
                    for( int j = 0; !achou && j < exClassesNomes.length; j++ ) {
                        String exClasseNome = exClassesNomes[ j ];
                        Classe classe = classeManager.buscaClasse( exClasseNome );                           
                        
                        if ( exObjVar.getObjeto().getClasse().isIgualOuFilhaNome( classe ) ) {                                                                                                                                     
                            int nomeI = cap.getObjetoExNomeI();
                            String nome = cap.getObjetoExNome();                                                           
                            
                            try {                                
                                cap.getBloco().getRecursos().getVarLista().criaOuAltera( nome, exObjVar );
                            } catch ( ConstanteException e ) {
                                CodigoErro erro = new CodigoErro( this.getClass(), codigo, nomeI, ErroMSGs.VAR_TENTATIVA_DE_ALTERAR_CONSTANTE, nome );
                                return new ExecResult( erro );
                            }                            
                            
                            ExecResult exER = executor.exec( cap.getBloco() );
                            if ( exER.getErro() != null || exER.isFinalizarBloco() )
                                erroResult = exER;                                                                

                            achou = true;
                        }
                    }
                }                                        
            }                        
            
            if ( !achou )
                erroResult = er;
        }                
        
        Grupo finalize = tc.getFinalize();
        if ( finalize != null ) {
            ExecResult er2 = executor.exec( finalize );
            er2.setRetornada( er.getRetornada() ); 
                        
            if ( er2.getErro() != null || er2.isFinalizarBloco() )
                return er2;                        
        }
        
        if ( erroResult != null )
            if ( erroResult.getErro() != null || erroResult.isFinalizarBloco() )
                return erroResult;
        
        ExecResult er2 = new ExecResult( er.getRetornada() );
        er2.setRetornada( er2.getRetornada() );         
        return er2; 
    }
    
}
