package italo.explab.inter.exp.mat.el;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.matriz.MatrizExp;
import italo.explab.arvore.exp.matriz.indice.ExpMatIDs;
import italo.explab.arvore.exp.matriz.indice.ExpMatrizIndice;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.exp.mat.result.MatrizIndicesInterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class MatELAcessoInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        ContadorUtil contUtil = aplic.getContUtil();                 
                   
        MatrizExp mexp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoMatrizExp( i, no, codigo );                                                                                        
        
        int j = 0;        
        int acesso = ExpRecurso.NORMAL;
                
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.ESTE );
        int cont2 = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == cont2 && cont > 0 ) {
            acesso = ExpRecurso.ESTE;        
        } else {
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.SUPER );
            if ( cont == cont2 && cont > 0 )
                acesso = ExpRecurso.SUPER;                
        }
        
        if ( acesso == ExpRecurso.ESTE || acesso == ExpRecurso.SUPER ) {
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            if ( codigo.getSEGCH( i+j ) != '.' ) {                              
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_ESPERADO );
                return new InterResult( erro );                
            }
            j++;
        }
        
        j += contUtil.contaEsps( codigo, i+j );
                                
        cont = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == 0 )
            return new InterResult();
                        
        String matVarNome = codigo.getCodigo().substring( i+j, i+j+cont );
                                                            
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
        
        MatIDs ids = this.indices( mexp, aplic, codigo, i+j, i2 );
        if ( ids.getErro() != null )
            return new InterResult( ids.getErro() );
        
        j += ids.getJ();
                 
        mexp.setExpMatIDs( ids.getExpMatIDs() ); 
        mexp.setNome( matVarNome );
        mexp.setAcesso( acesso ); 
                           
        return new InterResult( mexp, j );
    }
    
    public MatIDs indices( ExecNo mexp, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;
        
        MatrizIndicesInterResult result = (MatrizIndicesInterResult)manager.getChamadaMatIndicesInter().interpreta( mexp, mexp, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return new MatIDs( result.getErro() );        
                               
        j += result.getJ();
                
        ExpMatrizIndice[] indices = result.paramsIDIJ();
        ExpMatIDs expMatIDs = new ExpMatIDs( indices );
        
        int k = j;
        k += contUtil.contaEsps( codigo, i+k );
        
        if ( codigo.getSEGCH( i+k ) == '(' ) {
            j = k;
           
            MatIDs ids = this.indices( mexp, aplic, codigo, i+j, i2 );
            if ( ids.getErro() != null )
                return ids;
            
            expMatIDs.setProx( ids.getExpMatIDs() ); 
            
            j += ids.getJ();
        }
        
        return new MatIDs( expMatIDs, j );
    }
            
}
